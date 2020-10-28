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

        JsonArray coursesArray = json.getAsJsonArray();
        Iterator<JsonElement> coursesIterator = coursesArray.iterator();
        while (coursesIterator.hasNext()) {
            JsonElement courseElement = coursesIterator.next();
            try {
                assert courseElement instanceof JsonObject;
            }
            catch (AssertionError e) {
                e.printStackTrace();
                return null;
            }

            JsonObject courseObject = courseElement.getAsJsonObject();

            JsonElement subjectCodeElement = courseObject.get("subject");
            JsonElement courseCodeElement = courseObject.get("courseNumber");
            JsonElement titleElement = courseObject.get("title");
            JsonElement expandedTitleElement = courseObject.get("expandedTitle");
            String subjectCode = subjectCodeElement instanceof JsonPrimitive ? subjectCodeElement.getAsJsonPrimitive().getAsString() : null;
            String courseCode = courseCodeElement instanceof JsonPrimitive ? courseCodeElement.getAsJsonPrimitive().getAsString() : null;
            String titleUncapitalized = titleElement instanceof JsonPrimitive ? titleElement.getAsJsonPrimitive().getAsString() : null;
            String expandedTitleUncapitalized = expandedTitleElement instanceof JsonPrimitive ? expandedTitleElement.getAsJsonPrimitive().getAsString() : null;

            List<String> splitTitleCapitalized = NamingUtil.getSeparatedCapitalizedNameList(titleUncapitalized);
            String title = NamingUtil.joinSplitNameArray(splitTitleCapitalized);
            String expandedTitle = null;
            if (expandedTitleUncapitalized != null) {
                List<String> splitExpandedTitleCapitalized = NamingUtil.getSeparatedCapitalizedNameList(expandedTitleUncapitalized);
                expandedTitle = NamingUtil.joinSplitNameArray(splitExpandedTitleCapitalized);
            }

            courses.add(new Course(subjectCode, courseCode, title, expandedTitle));

            JsonElement sectionsElement = courseObject.get("sections");
            if (sectionsElement instanceof JsonArray) {
                JsonArray sectionsArray = sectionsElement.getAsJsonArray();
                Iterator<JsonElement> sectionsIterator = sectionsArray.iterator();
                while (sectionsIterator.hasNext()) {
                    JsonElement sectionElement = sectionsIterator.next();
                    if (sectionElement instanceof JsonObject) {
                        JsonObject sectionObject = sectionElement.getAsJsonObject();

                        JsonElement sectionIndexElement = sectionObject.get("index");
                        JsonElement sectionCodeElement = sectionObject.get("number");
                        String sectionIndex = sectionIndexElement instanceof JsonPrimitive ? sectionIndexElement.getAsJsonPrimitive().getAsString() : null;
                        String sectionCode = sectionCodeElement instanceof JsonPrimitive ? sectionCodeElement.getAsJsonPrimitive().getAsString() : null;

                        classes.add(new Class(sectionIndex, sectionCode, subjectCode, courseCode));

                        JsonElement instructorsElement = sectionObject.get("instructors");
                        if (instructorsElement instanceof JsonArray) {
                            JsonArray instructorsArray = instructorsElement.getAsJsonArray();
                            Iterator<JsonElement> instructorsIterator = instructorsArray.iterator();
                            while (instructorsIterator.hasNext()) {
                                JsonElement instructorElement = instructorsIterator.next();
                                if (instructorElement instanceof JsonObject) {
                                    JsonObject instructorObject = instructorElement.getAsJsonObject();

                                    JsonElement instructorNameElement = instructorObject.get("name");
                                    String name = instructorNameElement instanceof JsonPrimitive ? instructorNameElement.getAsJsonPrimitive().getAsString() : null;
                                    List<String> capitalizedNameSplittedList = NamingUtil.getSeparatedCapitalizedNameList(name);

                                    String lastName = capitalizedNameSplittedList.get(0);
                                    String firstName = null;

                                    if (capitalizedNameSplittedList.size() > 1) {
                                        capitalizedNameSplittedList.remove(0);
                                        capitalizedNameSplittedList.remove(",");
                                        firstName = "";
                                        for (int n = 0; n < capitalizedNameSplittedList.size(); n++) {
                                            firstName+= capitalizedNameSplittedList.get(n);
                                            if (n != capitalizedNameSplittedList.size() - 1) {
                                                firstName+= " ";
                                            }
                                        }
                                    }

                                    Instructor newInstructor = new Instructor(lastName, firstName);
                                    if (!instructors.contains(newInstructor)) instructors.add(new Instructor(lastName, firstName));
                                    classesInstructors.add(new ClassInstructor(sectionIndex, lastName, firstName));
                                }
                            }
                        }

                        JsonElement meetingsElement = sectionObject.get("meetingTimes");
                        if (meetingsElement instanceof JsonArray) {
                            JsonArray meetingsArray = meetingsElement.getAsJsonArray();

                            Iterator<JsonElement> meetingsIterator = meetingsArray.iterator();
                            while (meetingsIterator.hasNext()) {
                                JsonElement meetingElement = meetingsIterator.next();
                                try {
                                    assert meetingElement instanceof JsonObject;
                                }
                                catch (AssertionError e) {
                                    continue;
                                }
                                JsonObject meetingObject = meetingElement.getAsJsonObject();

                                JsonElement buildingCodeElement = meetingObject.get("buildingCode");
                                JsonElement roomNumberElement = meetingObject.get("roomNumber");
                                JsonElement meetingDayElement = meetingObject.get("meetingDay");
                                JsonElement startTimeElement = meetingObject.get("startTime");
                                JsonElement endTimeElement = meetingObject.get("endTime");
                                JsonElement pmCodeElement = meetingObject.get("pmCode");

                                String buildingCode = buildingCodeElement instanceof JsonPrimitive ? buildingCodeElement.getAsJsonPrimitive().getAsString() : null;
                                String roomNumber = roomNumberElement instanceof JsonPrimitive ? roomNumberElement.getAsJsonPrimitive().getAsString() : null;
                                String meetingDay = meetingDayElement instanceof JsonPrimitive ? meetingDayElement.getAsJsonPrimitive().getAsString() : null;
                                String startTimeUnparsed = startTimeElement instanceof JsonPrimitive ? startTimeElement.getAsJsonPrimitive().getAsString() : null;
                                String endTimeUnparsed = endTimeElement instanceof JsonPrimitive ? endTimeElement.getAsJsonPrimitive().getAsString() : null;
                                String pmCode = pmCodeElement instanceof JsonPrimitive ? pmCodeElement.getAsJsonPrimitive().getAsString() : null;

                                if (
                                    buildingCode == null
                                    || roomNumber == null
                                    || meetingDay == null
                                    || startTimeUnparsed == null
                                    || endTimeUnparsed == null
                                    || pmCode == null
                                ) {
                                    continue;
                                }

                                int startTime = CoursesUtil.parseTimeDataToSecondOfDay(startTimeUnparsed, pmCode.equals("A") ? CoursesUtil.TimeCode.AM : CoursesUtil.TimeCode.PM);

                                int endTimeAM = CoursesUtil.parseTimeDataToSecondOfDay(endTimeUnparsed, CoursesUtil.TimeCode.AM);
                                int endTimePM = CoursesUtil.parseTimeDataToSecondOfDay(endTimeUnparsed, CoursesUtil.TimeCode.PM);

                                int endTime = Math.abs(startTime - endTimeAM) < Math.abs(startTime - endTimePM) ? endTimeAM : endTimePM;

                                meetings.add(new Meeting(sectionIndex, buildingCode, roomNumber, meetingDay, startTime, endTime));
                            }
                        }
                    }
                }
            }
        }

        return new CourseInfoCollection(courses, classes, instructors, classesInstructors, meetings);
    }

}
