package blog.mazleo.ruvacant.model;

public class Room {
    private String buildingCode;
    private String number;

    public Room(String buildingCode, String number) {
        this.buildingCode = buildingCode;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Room{" +
                "buildingCode='" + buildingCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
