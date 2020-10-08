package blog.mazleo.ruvacant.model;

public class Option {
    int semesterMonth;
    int semesterYear;
    String schoolCampusCode;
    String levelCode;

    public Option(int semesterMonth, int semesterYear, String schoolCampusCode, String levelCode) {
        this.semesterMonth = semesterMonth;
        this.semesterYear = semesterYear;
        this.schoolCampusCode = schoolCampusCode;
        this.levelCode = levelCode;
    }

    public int getSemesterMonth() {
        return semesterMonth;
    }

    public void setSemesterMonth(int semesterMonth) {
        this.semesterMonth = semesterMonth;
    }

    public int getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(int semesterYear) {
        this.semesterYear = semesterYear;
    }

    public String getSchoolCampusCode() {
        return schoolCampusCode;
    }

    public void setSchoolCampusCode(String schoolCampusCode) {
        this.schoolCampusCode = schoolCampusCode;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }
}
