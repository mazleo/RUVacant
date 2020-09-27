package blog.mazleo.ruvacant.model;

public class Building {
    private String code;
    private String name;
    private String campusCode;
    private boolean isFavorite;

    public Building(String code, String name, String campusCode, boolean isFavorite) {
        this.code = code;
        this.name = name;
        this.campusCode = campusCode;
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return "Building{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", campusCode='" + campusCode + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
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

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
