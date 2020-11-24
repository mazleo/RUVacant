package blog.mazleo.ruvacant.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {
               "building_code",
               "number"
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Building.class,
                        parentColumns = {
                                "code"
                        },
                        childColumns = {
                                "building_code"
                        }
                )
        }
)
public class Room {
    @NonNull
    @ColumnInfo(name = "building_code")
    private String buildingCode;
    @NonNull
    @ColumnInfo(name = "number")
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

    @Override
    public int hashCode() {
        String roomCode = this.buildingCode + "-" + this.number;
        return roomCode.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean isEqual = false;

        if (obj instanceof Room) {
            Room roomToCheck = (Room) obj;

            if (
                    this.buildingCode.equals(roomToCheck.buildingCode)
                    && this.number.equals(roomToCheck.number)
            ) {
                isEqual = true;
            }
        }

        return isEqual;
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
