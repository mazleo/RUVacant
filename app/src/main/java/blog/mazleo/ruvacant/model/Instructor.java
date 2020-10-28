package blog.mazleo.ruvacant.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Instructor {
    private String lastName;
    private String firstName;

    public Instructor(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public int hashCode() {
        return (this.lastName + "," + this.firstName).hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Instructor)) return false;

        Instructor instructorCompared = (Instructor) obj;

        if (this.firstName == null && this.lastName != null) {
            return instructorCompared.firstName == null && this.lastName.equals(instructorCompared.lastName);
        }
        else if (this.firstName != null && this.lastName == null) {
            return instructorCompared.lastName == null && this.firstName.equals(instructorCompared.firstName);
        }
        else if (this.firstName != null && this.lastName != null) {
            return (this.firstName.equals(instructorCompared.firstName))
                    && (this.lastName.equals(instructorCompared.lastName));
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
