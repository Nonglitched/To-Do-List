import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
public class TodoList {
    private List<Task> tasks;
    private static final String FILE_NAME = "tasks.txt";
    public TodoList() {
        tasks = new ArrayList<>();
        loadTasks();
    }
    public void addTask(String description, LocalDate dueDate, Task.Priority priority, String category) {
        tasks.add(new Task(description, dueDate, priority, category));
        saveTasks();
    }
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
        }
    }
    public void toggleTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setCompleted(!task.isCompleted());
            saveTasks();
        }
    }
    public List<Task> searchTasks(String keyword) {
        return tasks.stream()
            .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }
    public List<Task> filterByCategory(String category) {
        return tasks.stream()
            .filter(task -> task.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
    }
    public List<Task> getOverdueTasks() {
        return tasks.stream()
            .filter(Task::isOverdue)
            .collect(Collectors.toList());
    }
    public void displayTasks(List<Task> taskList) {
        if (taskList.isEmpty()) {
            System.out.println("No hay tareas para mostrar.");
            return;
        }
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }
    public void displayTasks() {
        displayTasks(tasks);
    }
    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(String.format("%s,%s,%s,%s,%s",
                    task.getDescription(),
                    task.getDueDate(),
                    task.getPriority(),
                    task.getCategory(),
                    task.isCompleted()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Task task = new Task(
                        parts[0],
                        LocalDate.parse(parts[1]),
                        Task.Priority.valueOf(parts[2]),
                        parts[3]);
                    task.setCompleted(Boolean.parseBoolean(parts[4]));
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }
}