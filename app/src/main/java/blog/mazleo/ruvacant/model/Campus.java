package blog.mazleo.ruvacant.model;

public class Campus {
    private String code;
    private String name;
    private String schoolCampusCode;

    public Campus(String code, String name, String schoolCampusCode) {
        this.code = code;
        this.name = name;
        this.schoolCampusCode = schoolCampusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolCampusCode() {
        return schoolCampusCode;
    }

    public void setSchoolCampusCode(String schoolCampusCode) {
        this.schoolCampusCode = schoolCampusCode;
    }
}
