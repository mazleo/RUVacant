package blog.mazleo.ruvacant.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(
        primaryKeys = {
                "month",
                "year"
        }
)
public class Semester {
    @ColumnInfo(name = "month")
    private int month;
    @ColumnInfo(name = "year")
    private int year;

    public Semester(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
