import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

// Student class represents each student with name, address, and GPA
class Student implements Comparable<Student> {
    private String name;    // Student's name
    private String address; // Student's address
    private double GPA;     // Student's GPA

    // Constructor to initialize student details
    public Student(String name, String address, double GPA) {
        this.name = name;
        this.address = address;
        this.GPA = GPA;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Getter for GPA
    public double getGPA() {
        return GPA;
    }

    // compareTo method for sorting students by name in ascending order (ignores case)
    @Override
    public int compareTo(Student other) {
        return this.name.compareToIgnoreCase(other.name); // Compare names ignoring case sensitivity
    }

    // toString method for easy display of student details
    @Override
    public String toString() {
        return "Name: " + name + ", Address: " + address + ", GPA: " + GPA;
    }
}

public class StudentList {
    public static void main(String[] args) {
        // Create a LinkedList to store Student objects
        LinkedList<Student> studentList = new LinkedList<>();
        
        // Collect student data from the user
        collectStudentData(studentList);
        
        // Sort the list of students by name
        Collections.sort(studentList);
        
        // Write the sorted student list to a text file
        writeStudentsToFile(studentList, "StudentList.txt");
    }

    // Method to collect student data from the user via console input
    private static void collectStudentData(LinkedList<Student> studentList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student data. Type 'done' when finished.");
        
        // Infinite loop to collect multiple students' data
        while (true) {
            System.out.print("Enter student's name (or 'done' to finish): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) { // Exit loop if user types 'done'
                break;
            }

            System.out.print("Enter student's address: ");
            String address = scanner.nextLine();

            // Prompt user for a valid GPA
            double GPA = getValidGPA(scanner);

            // Add new Student object to the LinkedList
            studentList.add(new Student(name, address, GPA));
        }

        scanner.close(); // Close the scanner to avoid resource leak
    }

    // Method to prompt user for a valid GPA and handle incorrect input
    private static double getValidGPA(Scanner scanner) {
        double GPA = -1; // Initialize GPA with an invalid value to enter the loop
        while (true) {
            System.out.print("Enter student's GPA (0.0 - 4.0): ");
            if (scanner.hasNextDouble()) { // Check if input is a valid double
                GPA = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character
                if (GPA >= 0.0 && GPA <= 4.0) { // Check if GPA is within the valid range
                    break; // Valid GPA, exit loop
                } else {
                    System.out.println("GPA must be between 0.0 and 4.0.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value for GPA.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return GPA; // Return the validated GPA
    }

    // Method to write the sorted list of students to a text file
    private static void writeStudentsToFile(LinkedList<Student> studentList, String filename) {
        try (FileWriter writer = new FileWriter(filename)) { // Initialize FileWriter with try-with-resources
            for (Student student : studentList) {
                writer.write(student.toString() + System.lineSeparator()); // Write each student's details to the file
            }
            System.out.println("Student list successfully saved to " + filename);
        } catch (IOException e) { // Catch any IOExceptions that occur
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
