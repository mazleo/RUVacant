package blog.mazleo.ruvacant.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Campus.class,
                        parentColumns = {
                                "code"
                        },
                        childColumns = {
                                "campus_code"
                        }
                )
        }
)
public class Building {
    @NonNull
    @PrimaryKey
    private String code;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "campus_code", index = true)
    private String campusCode;
    @ColumnInfo(name = "is_fave")
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

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean isEqual = false;
        if (obj instanceof Building) {
            Building buildingToCheck = (Building) obj;

            if (this.code.equals(buildingToCheck.code)) {
                isEqual = true;
            }
        }

        return isEqual;
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
