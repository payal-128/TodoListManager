import java.io.*;
import java.util.*;

public class TodoListManager {
    private static final String FILE_NAME = "todo.txt";
    private static List<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== To-Do List Menu ====");
            System.out.println("1. View Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Remove Task");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewTasks();
                case 2 -> addTask(scanner);
                case 3 -> removeTask(scanner);
                case 4 -> saveTasks();
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            // File might not exist yet — that’s OK
        }
    }

    private static void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your to-do list is empty.");
        } else {
            System.out.println("\nYour Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter task to add: ");
        String task = scanner.nextLine();
        tasks.add(task);
        System.out.println("Task added.");
    }

    private static void removeTask(Scanner scanner) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }

        viewTasks();
        System.out.print("Enter the task number to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index >= 1 && index <= tasks.size()) {
            tasks.remove(index - 1);
            System.out.println("Task removed.");
        } else {
            System.out.println("Invalid task number.");
        }
    }
}
