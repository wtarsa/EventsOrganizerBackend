package pl.edu.agh.io.eventsOrganizer.generator;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.ClassesForm;
import pl.edu.agh.io.eventsOrganizer.model.ClassesType;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class Generator {
    Faker faker = new Faker(new Locale("pl"));
    Random random = new Random();

    private String normalize(String str) {
        List<Character> original = List.of('Ą', 'ą', 'Ć', 'ć', 'Ę', 'ę', 'Ł', 'ł', 'Ń', 'ń', 'Ó', 'ó', 'Ś', 'ś', 'Ź', 'ź', 'Ż', 'ż');
        List<Character> normalized = List.of('A', 'a', 'C', 'c', 'E', 'e', 'L', 'l', 'N', 'n', 'O', 'o', 'S', 's', 'Z', 'z', 'Z', 'z');

        StringBuilder builder = new StringBuilder();
        for (Character c : str.toCharArray()) {
            if (original.contains(c)) {
                int index = original.indexOf(c);
                builder.append(normalized.get(index));
            } else builder.append(c);
        }
        return builder.toString();
    }

    public List<Instructor> generateInstructors(int count) {
        List<Instructor> instructors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = normalize(firstName + "." + lastName + "@exampleMail.com").toLowerCase();

            instructors.add(new Instructor(firstName, lastName, email));
        }
        return instructors;
    }

    public List<String> generateEvents(int count) {
        List<String> events = new ArrayList<>();
        List<String> eventList = List.of(
                "Computer Science",
                "Computer Engineering",
                "Information Systems",
                "New Media",
                "Information Technology (IT)",
                "Information Science",
                "Web technology",
                "Internetworking",
                "Computer science",
                "Computer information systems",
                "PC hardware",
                "Web page design and development",
                "Programming",
                "Computer studies education",
                "Theory of computation",
                "Information and coding theory",
                "Data structures and algorithms",
                "Programming language theory and formal methods",
                "Electronics",
                "Electronics and Telecommunications"
        );
        for (int i = 0; i < count; i++) {
            events.add(eventList.get(random.nextInt(eventList.size())));
        }
        return events;
    }

    public List<Classes> generateClasses(List<Instructor> instructors, List<String> events, int count) {
        List<Classes> classes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm");

            int appointmentNumber = random.nextInt(5) + 1;
            int numberOfHours = random.nextInt(5) + 1;
            Date startDate = faker.date().future(30, TimeUnit.DAYS);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.HOUR_OF_DAY, numberOfHours);

            String startTime = dateFormat.format(startDate);
            String endTime = dateFormat.format(calendar.getTime());

            String name = getName();
            String studentsGroup = getStudentsGroup();
            Instructor instructor = instructors.get(random.nextInt(instructors.size()));
            ClassesType classesType = ClassesType.values()[random.nextInt(ClassesType.values().length - 1)];
            ClassesForm classesForm = ClassesForm.values()[random.nextInt(ClassesForm.values().length - 1)];
            String classroom = getClassroom();
            String event = events.get(random.nextInt(events.size()));

            classes.add(new Classes(appointmentNumber, startTime, endTime, name, studentsGroup, instructor, classesType, numberOfHours, classesForm, classroom, event));
        }
        return classes;
    }

    private String getClassroom() {
        List<String> classrooms = List.of("1.38", "2.41", "1.22", "3.33", "4.22", "4.12", "3.21", "3.32", "2.15", "1.12", "2.32", "3.22", "3.34", "4.12", "4.22");
        return classrooms.get(random.nextInt(classrooms.size()));
    }

    private String getStudentsGroup() {
        List<String> studentsGroup = List.of("Grupa A", "Grupa B", "Grupa C", "Grupa D", "Grupa E", "Grupa F");
        return studentsGroup.get(random.nextInt(studentsGroup.size()));
    }

    private String getName() {
        List<String> subjects = List.of(
                "Mathematics",
                "Algebra",
                "Geometry",
                "Science",
                "Introduction to UNIX System",
                "Introduction to Computer Science",
                "Interpersonal Skills",
                "Mathematical Analysis 1",
                "Discrete Mathematics",
                "Data Structures and Algorithms",
                "Imperative Programming",
                "Mathematical Analysis 2",
                "Protection of Intellectual Property",
                "Computer Science",
                "Physics 1",
                "Mathematical Logic",
                "Object Oriented Programming",
                "Introduction to Databases",
                "Functional programming",
                "Physics 2",
                "Differential and Difference Equations",
                "Computation Methods for Science and Technology",
                "Operating Systems",
                "Automata and Formal Languages Theory",
                "Digital technology",
                "Microprocessor Techniques",
                "Computer Networks",
                "Theory of Concurrency",
                "Computation and Complexity Theory",
                "Cryptography",
                "Object-oriented design",
                "Database Systems",
                "Computer Architecture",
                "Distributed Systems",
                "Embedded Systems",
                "Complex Digital Systems",
                "Introduction to ERP systems",
                "Software Engineering"
        );
        return subjects.get(random.nextInt(subjects.size()));

    }
}
