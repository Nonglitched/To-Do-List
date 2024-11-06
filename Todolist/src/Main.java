import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Lista de Tareas ===");
            System.out.println("1. Ver todas las tareas");
            System.out.println("2. Agregar tarea");
            System.out.println("3. Eliminar tarea");
            System.out.println("4. Marcar tarea como completada");
            System.out.println("5. Buscar tareas");
            System.out.println("6. Filtrar por categoría");
            System.out.println("7. Ver tareas vencidas");
            System.out.println("8. Salir");
            System.out.print("Opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    todoList.displayTasks();
                    break;
                case 2:
                    System.out.print("Descripción: ");
                    String description = scanner.nextLine();
                    System.out.print("Fecha límite (dd/MM/yyyy) o Enter para ninguna: ");
                    String dateStr = scanner.nextLine();
                    LocalDate dueDate = dateStr.isEmpty() ? null : LocalDate.parse(dateStr, DATE_FORMATTER);
                    System.out.print("Prioridad (LOW/MEDIUM/HIGH): ");
                    Task.Priority priority = Task.Priority.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Categoría: ");
                    String category = scanner.nextLine();
                    todoList.addTask(description, dueDate, priority, category);
                    System.out.println("Tarea agregada.");
                    break;
                case 3:
                    todoList.displayTasks();
                    System.out.print("Número de tarea a eliminar: ");
                    todoList.removeTask(scanner.nextInt() - 1);
                    System.out.println("Tarea eliminada.");
                    break;
                case 4:
                    todoList.displayTasks();
                    System.out.print("Número de tarea a marcar/desmarcar: ");
                    todoList.toggleTask(scanner.nextInt() - 1);
                    System.out.println("Estado actualizado.");
                    break;
                case 5:
                    System.out.print("Palabra clave: ");
                    String keyword = scanner.nextLine();
                    List<Task> searchResults = todoList.searchTasks(keyword);
                    System.out.println("\nResultados de búsqueda:");
                    todoList.displayTasks(searchResults);
                    break;
                case 6:
                    System.out.print("Categoría a filtrar: ");
                    String categoryFilter = scanner.nextLine();
                    List<Task> filteredTasks = todoList.filterByCategory(categoryFilter);
                    System.out.println("\nTareas en categoría '" + categoryFilter + "':");
                    todoList.displayTasks(filteredTasks);
                    break;
                case 7:
                    List<Task> overdueTasks = todoList.getOverdueTasks();
                    System.out.println("\nTareas vencidas:");
                    todoList.displayTasks(overdueTasks);
                    break;
                case 8:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}