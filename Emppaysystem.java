import java.io.*;
import java.util.*;

public class Emppaysystem {

    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "employees.txt";

    // Employee Class (Inner Class)
    static class Employee {
        private int empId;
        private String name;
        private double basicSalary;
        private double hra;
        private double da;
        private double netSalary;

        // Default Constructor
        public Employee() {
            empId = 0;
            name = "";
            basicSalary = 0.0;
        }

        // Parameterized Constructor
        public Employee(int empId, String name, double basicSalary) {
            this.empId = empId;
            this.name = name;
            this.basicSalary = basicSalary;
            calculateSalary();
        }

        // Method 1: Calculate salary normally
        public void calculateSalary() {
            hra = 0.20 * basicSalary;   // 20% HRA
            da = 0.10 * basicSalary;    // 10% DA
            netSalary = basicSalary + hra + da;
        }

        // Method Overloading (with bonus)
        public void calculateSalary(double bonus) {
            calculateSalary();
            netSalary += bonus;
        }

        public void display() {
            System.out.println("\nEmployee ID: " + empId);
            System.out.println("Name: " + name);
            System.out.println("Basic Salary: " + basicSalary);
            System.out.println("HRA (20%): " + hra);
            System.out.println("DA (10%): " + da);
            System.out.println("Net Salary: " + netSalary);
            System.out.println("---------------------------");
        }

        public String toFileString() {
            return empId + "," + name + "," + basicSalary;
        }
    }

    // Add Employee
    public static void addEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();   // FIX: consume newline

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Basic Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();   // FIX

            Employee emp = new Employee(id, name, salary);

            try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
                fw.write(emp.toFileString() + "\n");
            }

            System.out.println("Employee added successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct data type.");
            sc.nextLine(); // clear buffer
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    // Display All Employees
    public static void displayEmployees() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No records found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Employee emp = new Employee(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2])
                );

                emp.display();
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    // Search Employee by ID
    public static void searchEmployee() {
        try {
            System.out.print("Enter Employee ID to search: ");
            int searchId = sc.nextInt();
            sc.nextLine();   // FIX

            File file = new File(FILE_NAME);

            if (!file.exists()) {
                System.out.println("No records found.");
                return;
            }

            boolean found = false;

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0]);

                    if (id == searchId) {
                        Employee emp = new Employee(
                                id,
                                data[1],
                                Double.parseDouble(data[2])
                        );
                        emp.display();
                        found = true;
                        break;
                    }
                }
            }

            if (!found)
                System.out.println("Employee not found.");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numeric ID.");
            sc.nextLine(); // clear buffer
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    // Main Method
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Employee Payroll System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();   // FIX

                switch (choice) {
                    case 1:
                        addEmployee();
                        break;

                    case 2:
                        displayEmployees();
                        break;

                    case 3:
                        searchEmployee();
                        break;

                    case 4:
                        System.out.println("Exiting program...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                sc.nextLine(); // clear invalid input
            }
        }
    }
}
