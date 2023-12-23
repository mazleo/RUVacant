package blog.mazleo.ruvacant.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Subject {
    @NonNull
    @PrimaryKey
    String code;
    @ColumnInfo(name = "title")
    String title;

    public Subject(String code, String title) {
        this.code = code;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Subject)) return false;

        Subject subjectToCompare = (Subject) obj;

        return code.equals(subjectToCompare.getCode()) && title.equals(subjectToCompare.getTitle());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
