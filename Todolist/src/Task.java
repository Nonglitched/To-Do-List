import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Task {
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private Priority priority;
    private String category;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public enum Priority {LOW, MEDIUM, HIGH}
    public Task(String description, LocalDate dueDate, Priority priority, String category) {
        this.description = description;
        this.completed = false;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
    }
    public String getDescription() {return description;}
    public boolean isCompleted() {return completed;}
    public void setCompleted(boolean completed) {this.completed = completed;}
    public LocalDate getDueDate() {return dueDate;}
    public Priority getPriority() {return priority;}
    public String getCategory() {return category;}
    public boolean isOverdue() {
        return !completed && dueDate != null && dueDate.isBefore(LocalDate.now());
    }
    @Override
    public String toString() {
        String status = completed ? "[X]" : isOverdue() ? "[!]" : "[ ]";
        String dateStr = dueDate != null ? dueDate.format(DATE_FORMATTER) : "Sin fecha";
        return String.format("%s %s - %s - %s - %s", status, description, dateStr, priority, category);
    }
}
