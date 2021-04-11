package pl.edu.agh.io.eventsOrganizer.forms;

import lombok.Data;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.ClassesForm;
import pl.edu.agh.io.eventsOrganizer.model.ClassesType;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;

@Data
public class ClassesSubmitForm {

    public ClassesSubmitForm(int appointmentNumber, String startTime, String endTime, String name, String studentsGroup,
                             String firstName, String lastName, ClassesType classesType, int numberOfHours,
                             ClassesForm classesForm, String classroom) {
        this.appointmentNumber = appointmentNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.studentsGroup = studentsGroup;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classesType = classesType;
        this.numberOfHours = numberOfHours;
        this.classesForm = classesForm;
        this.classroom = classroom;
    }

    private int appointmentNumber; //numer zjazdu

    private String startTime;

    private String endTime;

    private String name;

    private String studentsGroup;

    private String firstName;

    private String lastName;

    private ClassesType classesType;

    private int numberOfHours;

    private ClassesForm classesForm;

    private String classroom;

    public Classes getClassesWithInstructor(Instructor instructor){
        return new Classes(appointmentNumber, startTime, endTime, name, studentsGroup, instructor, classesType,
                numberOfHours, classesForm, classroom);
    }

}
