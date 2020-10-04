package blog.mazleo.ruvacant.service.deserializer;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import blog.mazleo.ruvacant.model.Subject;

public class SubjectListDeserializer implements JsonDeserializer<ArrayList> {
    @Override
    public ArrayList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        assert json.isJsonArray();
        JsonArray subjectsArray = json.getAsJsonArray();

        ArrayList<Subject> subjects = new ArrayList<>();

        addAllSubjects(subjectsArray, subjects);

        return subjects;
    }

    private static void addAllSubjects(JsonArray subjectsArray, ArrayList<Subject> subjects) {
        Iterator<JsonElement> subjectsIterator = subjectsArray.iterator();
        while (subjectsIterator.hasNext()) {
            JsonElement subjectElement = subjectsIterator.next();

            assert subjectElement.isJsonObject();
            JsonObject subjectObject = subjectElement.getAsJsonObject();

            String title = getMemberStringFromSubjectObject(subjectObject, "description");
            String code = getMemberStringFromSubjectObject(subjectObject, "code");

            String capitalizedTitle = "";
            capitalizedTitle = capitalizeTitle(title, capitalizedTitle);

            subjects.add(new Subject(code, capitalizedTitle));
        }
    }

    private static String getMemberStringFromSubjectObject(JsonObject subjectObject, String memberName) {
        String memberString;
        JsonElement element = subjectObject.get(memberName);
        assert element.isJsonPrimitive();
        assert !(element instanceof JsonNull);
        memberString = element.getAsJsonPrimitive().getAsString();
        return memberString;
    }

    private static String capitalizeTitle(String title, String capitalizedTitle) {
        String[] splitTitleArray = title.split(" ");
        if (splitTitleArray.length > 0) {
            for (int index = 0; index < splitTitleArray.length; index++) {
                String splitTitle = splitTitleArray[index];
                if (splitTitle.length() > 0) {
                    capitalizedTitle += String.valueOf(splitTitle.charAt(0)).toUpperCase()
                            + splitTitle.substring(1).toLowerCase();
                    if (index < splitTitleArray.length - 1) {
                        capitalizedTitle += " ";
                    }
                }
            }
        }
        return capitalizedTitle;
    }
}
