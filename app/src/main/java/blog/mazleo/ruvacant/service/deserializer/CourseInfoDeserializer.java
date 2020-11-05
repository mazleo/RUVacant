package blog.mazleo.ruvacant.service.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import blog.mazleo.ruvacant.model.Class;
import blog.mazleo.ruvacant.model.ClassInstructor;
import blog.mazleo.ruvacant.model.Course;
import blog.mazleo.ruvacant.model.CourseInfoCollection;
import blog.mazleo.ruvacant.model.Instructor;
import blog.mazleo.ruvacant.model.Meeting;
import blog.mazleo.ruvacant.utils.CoursesUtil;
import blog.mazleo.ruvacant.utils.NamingUtil;

public class CourseInfoDeserializer implements JsonDeserializer {
    @Override
    public CourseInfoCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            assert json != null;
            assert json instanceof JsonArray;
        }
        catch (AssertionError e) {
            e.printStackTrace();
            return null;
        }

        List<Course> courses = new ArrayList<>();
        List<Class> classes = new ArrayList<>();
        List<Instructor> instructors = new ArrayList<>();
        List<ClassInstructor> classesInstructors = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();

        populateCourseInfo(json, courses, classes, instructors, classesInstructors, meetings);

        return new CourseInfoCollection(courses, classes, instructors, classesInstructors, meetings);
    }

    private static void populateCourseInfo(JsonElement json, List<Course> courses, List<Class> classes, List<Instructor> instructors, List<ClassInstructor> classesInstructors, List<Meeting> meetings) {
        JsonArray coursesArray = json.getAsJsonArray();
        Iterator<JsonElement> coursesIterator = coursesArray.iterator();
        while (hasMoreCourses(coursesIterator)) {
            JsonElement courseElement = coursesIterator.next();

            if (courseElement instanceof JsonObject) {
                JsonObject courseObject = courseElement.getAsJsonObject();

                String subjectCode = getSubjectCode(courseObject);
                String courseCode = getCourseCode(courseObject);
                String title = getCourseTitle(courseObject);
                String expandedTitle = getExpandedTitle(courseObject);

                addNewCourseToList(courses, subjectCode, courseCode, title, expandedTitle);
                addAllCourseSectionsToList(classes, instructors, classesInstructors, meetings, courseObject, subjectCode, courseCode);
            }
        }
    }

    private static boolean hasMoreCourses(Iterator<JsonElement> coursesIterator) {
        return coursesIterator.hasNext();
    }

    private static String getExpandedTitle(JsonObject courseObject) {
        JsonElement expandedTitleElement = courseObject.get("expandedTitle");
        String expandedTitleUncapitalized = getUncapitalizedExpandedTitleIfNotNull(expandedTitleElement);
        String expandedTitle = null;
        if (expandedTitleUncapitalized != null) {
            List<String> splitExpandedTitleCapitalized = NamingUtil.getSeparatedCapitalizedNameList(expandedTitleUncapitalized);
            expandedTitle = NamingUtil.joinSplitNameArray(splitExpandedTitleCapitalized);
        }
        return expandedTitle;
    }

    private static String getUncapitalizedExpandedTitleIfNotNull(JsonElement expandedTitleElement) {
        return expandedTitleElement instanceof JsonPrimitive ? expandedTitleElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getCourseTitle(JsonObject courseObject) {
        JsonElement titleElement = courseObject.get("title");
        String titleUncapitalized = getUncapitalizedTitleIfNotNull(titleElement);
        List<String> splitTitleCapitalized = NamingUtil.getSeparatedCapitalizedNameList(titleUncapitalized);
        return NamingUtil.joinSplitNameArray(splitTitleCapitalized);
    }

    private static String getUncapitalizedTitleIfNotNull(JsonElement titleElement) {
        return titleElement instanceof JsonPrimitive ? titleElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getCourseCode(JsonObject courseObject) {
        JsonElement courseCodeElement = courseObject.get("courseNumber");
        return getCourseCodeIfNotNull(courseCodeElement);
    }

    private static String getCourseCodeIfNotNull(JsonElement courseCodeElement) {
        return courseCodeElement instanceof JsonPrimitive ? courseCodeElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getSubjectCode(JsonObject courseObject) {
        JsonElement subjectCodeElement = courseObject.get("subject");
        return getSubjectCodeIfNotNull(subjectCodeElement);
    }

    private static String getSubjectCodeIfNotNull(JsonElement subjectCodeElement) {
        return subjectCodeElement instanceof JsonPrimitive ? subjectCodeElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static void addNewCourseToList(List<Course> courses, String subjectCode, String courseCode, String title, String expandedTitle) {
        courses.add(new Course(subjectCode, courseCode, title, expandedTitle));
    }

    private static void addAllCourseSectionsToList(List<Class> classes, List<Instructor> instructors, List<ClassInstructor> classesInstructors, List<Meeting> meetings, JsonObject courseObject, String subjectCode, String courseCode) {
        JsonElement sectionsElement = courseObject.get("sections");
        if (sectionsElement instanceof JsonArray) {
            JsonArray sectionsArray = sectionsElement.getAsJsonArray();
            Iterator<JsonElement> sectionsIterator = sectionsArray.iterator();
            while (hasMoreSections(sectionsIterator)) {
                JsonElement sectionElement = sectionsIterator.next();
                if (sectionElement instanceof JsonObject) {
                    JsonObject sectionObject = sectionElement.getAsJsonObject();

                    String sectionIndex = getSectionIndex(sectionObject);
                    String sectionCode = getSectionCode(sectionObject);

                    addNewSectionToList(classes, subjectCode, courseCode, sectionIndex, sectionCode);

                    retrieveAllInstructorsInSection(instructors, classesInstructors, sectionObject, sectionIndex);
                    retrieveAllMeetingsInSection(meetings, sectionObject, sectionIndex);
                }
            }
        }
    }

    private static boolean hasMoreSections(Iterator<JsonElement> sectionsIterator) {
        return sectionsIterator.hasNext();
    }

    private static String getSectionCode(JsonObject sectionObject) {
        JsonElement sectionCodeElement = sectionObject.get("number");
        return getSectionCodeIfNotNull(sectionCodeElement);
    }

    private static String getSectionIndex(JsonObject sectionObject) {
        JsonElement sectionIndexElement = sectionObject.get("index");
        return getSectionIndexIfNotNull(sectionIndexElement);
    }

    private static String getSectionCodeIfNotNull(JsonElement sectionCodeElement) {
        return sectionCodeElement instanceof JsonPrimitive ? sectionCodeElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getSectionIndexIfNotNull(JsonElement sectionIndexElement) {
        return sectionIndexElement instanceof JsonPrimitive ? sectionIndexElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static void addNewSectionToList(List<Class> classes, String subjectCode, String courseCode, String sectionIndex, String sectionCode) {
        classes.add(new Class(sectionIndex, sectionCode, subjectCode, courseCode));
    }

    private static void retrieveAllMeetingsInSection(List<Meeting> meetings, JsonObject sectionObject, String sectionIndex) {
        JsonElement meetingsElement = sectionObject.get("meetingTimes");
        if (meetingsElement instanceof JsonArray) {
            JsonArray meetingsArray = meetingsElement.getAsJsonArray();

            Iterator<JsonElement> meetingsIterator = meetingsArray.iterator();
            while (meetingsIterator.hasNext()) {
                JsonElement meetingElement = meetingsIterator.next();
                if (meetingElement instanceof JsonObject) {
                    JsonObject meetingObject = meetingElement.getAsJsonObject();

                    String buildingCode = getBuildingCodeIfNotNull(meetingObject);
                    String roomNumber = getRoomNumberIfNotNull(meetingObject);
                    String meetingDay = getMeetingDayIfNotNull(meetingObject);
                    String startTimeUnparsed = getUnparsedStartTimeIfNotNull(meetingObject);
                    String endTimeUnparsed = getUnparsedEndTimeIfNotNull(meetingObject);
                    String pmCode = getPMCodeIfNotNull(meetingObject);

                    if (anyRequiredMeetingValueNotValid(buildingCode, roomNumber, meetingDay, startTimeUnparsed, endTimeUnparsed, pmCode)) {
                        continue;
                    }

                    int startTime = getParsedStartTime(startTimeUnparsed, pmCode);
                    int endTime = getParsedEndTime(endTimeUnparsed, startTime);

                    addNewMeetingToList(meetings, sectionIndex, buildingCode, roomNumber, meetingDay, startTime, endTime);
                }
            }
        }
    }

    private static void addNewMeetingToList(List<Meeting> meetings, String sectionIndex, String buildingCode, String roomNumber, String meetingDay, int startTime, int endTime) {
        meetings.add(new Meeting(sectionIndex, buildingCode, roomNumber, meetingDay, startTime, endTime));
    }

    private static int getParsedEndTime(String endTimeUnparsed, int startTime) {
        int endTimeAM = CoursesUtil.parseTimeDataToSecondOfDay(endTimeUnparsed, CoursesUtil.TimeCode.AM);
        int endTimePM = CoursesUtil.parseTimeDataToSecondOfDay(endTimeUnparsed, CoursesUtil.TimeCode.PM);

        if (endTimeAM >= startTime && endTimePM >= startTime) {
            return Math.abs(startTime - endTimeAM) < Math.abs(startTime - endTimePM) ? endTimeAM : endTimePM;
        }
        else if (endTimeAM >= startTime && endTimePM < startTime) {
            return endTimeAM;
        }
        else if (endTimePM >= startTime && endTimeAM < startTime) {
            return endTimePM;
        }
        else {
            return 0;
        }
    }

    private static int getParsedStartTime(String startTimeUnparsed, String pmCode) {
        return CoursesUtil.parseTimeDataToSecondOfDay(startTimeUnparsed, pmCode.equals("A") ? CoursesUtil.TimeCode.AM : CoursesUtil.TimeCode.PM);
    }

    private static boolean anyRequiredMeetingValueNotValid(String buildingCode, String roomNumber, String meetingDay, String startTimeUnparsed, String endTimeUnparsed, String pmCode) {
        return buildingCode == null
                || roomNumber == null
                || meetingDay == null
                || startTimeUnparsed == null
                || endTimeUnparsed == null
                || pmCode == null;
    }

    private static String getPMCodeIfNotNull(JsonObject meetingObject) {
        JsonElement pmCodeElement = meetingObject.get("pmCode");
        return pmCodeElement instanceof JsonPrimitive ? pmCodeElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getUnparsedEndTimeIfNotNull(JsonObject meetingObject) {
        JsonElement endTimeElement = meetingObject.get("endTime");
        return endTimeElement instanceof JsonPrimitive ? endTimeElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getUnparsedStartTimeIfNotNull(JsonObject meetingObject) {
        JsonElement startTimeElement = meetingObject.get("startTime");
        return startTimeElement instanceof JsonPrimitive ? startTimeElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getMeetingDayIfNotNull(JsonObject meetingObject) {
        JsonElement meetingDayElement = meetingObject.get("meetingDay");
        return meetingDayElement instanceof JsonPrimitive ? meetingDayElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getRoomNumberIfNotNull(JsonObject meetingObject) {
        JsonElement roomNumberElement = meetingObject.get("roomNumber");
        return roomNumberElement instanceof JsonPrimitive ? roomNumberElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getBuildingCodeIfNotNull(JsonObject meetingObject) {
        JsonElement buildingCodeElement = meetingObject.get("buildingCode");
        return buildingCodeElement instanceof JsonPrimitive ? buildingCodeElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static void retrieveAllInstructorsInSection(List<Instructor> instructors, List<ClassInstructor> classesInstructors, JsonObject sectionObject, String sectionIndex) {
        JsonElement instructorsElement = sectionObject.get("instructors");
        if (instructorsElement instanceof JsonArray) {
            JsonArray instructorsArray = instructorsElement.getAsJsonArray();
            Iterator<JsonElement> instructorsIterator = instructorsArray.iterator();
            while (hasMoreInstructors(instructorsIterator)) {
                addInstructorToAppropriateListsIfValid(instructors, classesInstructors, sectionIndex, instructorsIterator);
            }
        }
    }

    private static boolean hasMoreInstructors(Iterator<JsonElement> instructorsIterator) {
        return instructorsIterator.hasNext();
    }

    private static void addInstructorToAppropriateListsIfValid(List<Instructor> instructors, List<ClassInstructor> classesInstructors, String sectionIndex, Iterator<JsonElement> instructorsIterator) {
        JsonElement instructorElement = instructorsIterator.next();
        if (instructorElement instanceof JsonObject) {
            JsonObject instructorObject = instructorElement.getAsJsonObject();
            JsonElement instructorNameElement = instructorObject.get("name");

            String name = getUnparsedInstructorNameIfNotNull(instructorNameElement);
            List<String> capitalizedNameSplittedList = NamingUtil.getSeparatedCapitalizedNameList(name);

            String lastName = getLastNameFromCapitalizedNameList(capitalizedNameSplittedList);

            String firstName = null;
            firstName = getFirstNameIfExists(capitalizedNameSplittedList, firstName);

            Instructor newInstructor = new Instructor(lastName, firstName);
            addInstructorToListIfNotExists(instructors, lastName, firstName, newInstructor);
            addClassInstructorToList(classesInstructors, sectionIndex, lastName, firstName);
        }
    }

    private static void addClassInstructorToList(List<ClassInstructor> classesInstructors, String sectionIndex, String lastName, String firstName) {
        classesInstructors.add(new ClassInstructor(sectionIndex, lastName, firstName));
    }

    private static void addInstructorToListIfNotExists(List<Instructor> instructors, String lastName, String firstName, Instructor newInstructor) {
        if (!instructors.contains(newInstructor)) instructors.add(new Instructor(lastName, firstName));
    }

    private static String getUnparsedInstructorNameIfNotNull(JsonElement instructorNameElement) {
        return instructorNameElement instanceof JsonPrimitive ? instructorNameElement.getAsJsonPrimitive().getAsString() : null;
    }

    private static String getLastNameFromCapitalizedNameList(List<String> capitalizedNameSplittedList) {
        return capitalizedNameSplittedList.get(0);
    }

    private static String getFirstNameIfExists(List<String> capitalizedNameSplittedList, String firstName) {
        if (capitalizedNameSplittedList.size() > 1) {
            removeLastNameFromCapitalizedNameList(capitalizedNameSplittedList);
            removeCommaFromCapitalizedNameList(capitalizedNameSplittedList);
            firstName = joinFirstNameSpaceDelimiter(capitalizedNameSplittedList);
        }
        return firstName;
    }

    private static void removeCommaFromCapitalizedNameList(List<String> capitalizedNameSplittedList) {
        capitalizedNameSplittedList.remove(",");
    }

    private static void removeLastNameFromCapitalizedNameList(List<String> capitalizedNameSplittedList) {
        capitalizedNameSplittedList.remove(0);
    }

    private static String joinFirstNameSpaceDelimiter(List<String> capitalizedNameSplittedList) {
        String firstName;
        firstName = "";
        for (int n = 0; n < capitalizedNameSplittedList.size(); n++) {
            firstName+= capitalizedNameSplittedList.get(n);
            if (n != capitalizedNameSplittedList.size() - 1) {
                firstName+= " ";
            }
        }
        return firstName;
    }

}
