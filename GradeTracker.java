import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class GradeTracker {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("--- Student Grade Tracker ---");
        while (true) {
            System.out.print("Enter student name (or type 'exit' to finish): ");
            String name = sc.nextLine();
            if (name.equalsIgnoreCase("exit")) {
                break;
            }
            
            System.out.print("Enter grade for " + name + ": ");
            double grade = -1;
            while (grade < 0 || grade > 100) {
                try {
                    grade = Double.parseDouble(sc.nextLine());
                    if (grade < 0 || grade > 100) {
                        System.out.print("Please enter a valid grade between 0 and 100: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Enter a numeric grade: ");
                }
            }
            
            students.add(new Student(name, grade));
        }
        
        if (students.isEmpty()) {
            System.out.println("No student records entered.");
            return;
        }
        
        // Calculations
        double total = 0;
        double highest = students.get(0).grade;
        double lowest = students.get(0).grade;
        String highestStudent = students.get(0).name;
        String lowestStudent = students.get(0).name;
        
        System.out.println("\n--- Summary Report ---");
        for (Student s : students) {
            System.out.printf("Student: %-15s | Grade: %.2f\n", s.name, s.grade);
            total += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
                highestStudent = s.name;
            }
            if (s.grade < lowest) {  
                lowest = s.grade;
                lowestStudent = s.name;
            }
        }
        
        double average = total / students.size();
        System.out.println("---------------------------------");
        System.out.printf("Total Students: %d\n", students.size());
        System.out.printf("Average Score:  %.2f\n", average);
        System.out.printf("Highest Score:  %.2f (%s)\n", highest, highestStudent);
        System.out.printf("Lowest Score:   %.2f (%s)\n", lowest, lowestStudent);
        sc.close();
    }
}
