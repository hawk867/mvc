package org.danielesteban.task.view;

import org.danielesteban.task.controller.TaskController;
import org.danielesteban.task.esceptions.TaskException;
import org.danielesteban.task.esceptions.TaskValidationException;
import org.danielesteban.task.model.Task;
import org.jspecify.annotations.NonNull;

import java.util.Scanner;

public class TaskView {
    private final TaskController taskController;
    private final Scanner scanner;

    public TaskView(TaskController taskController) {
        this.taskController = taskController;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("Gestión de tareas");
            System.out.println("1. Agregar Tarea");
            System.out.println("2. Eliminar Tarea");
            System.out.println("3. Actualizar Tarea");
            System.out.println("4. Mostar Tareas");
            System.out.println("5. Actualizar estado de la tarea");
            System.out.println("6. Mostrar tareas completadas");
            System.out.println("7. Mostrar tareas pendientes");
            System.out.println("8. Salir");
            System.out.println("Seleccione una opción");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addTaskView();
                    break;
                case "2":
                    removeTaskView();
                    break;
                case "3":
                    updateTaskView();
                    break;
                case "4":
                    showTaskView();
                    break;
                case "5":
                    updateTaskCompletedView();
                    break;
                case "6":
                    showCompletedTasksView();
                    break;
                case "7":
                    showPendingTasksView();
                    break;
                case "8":
                    System.out.println("Saliendo del sistema");
                    return;
                default:
                    System.out.println("Opción invalida!");
            }
        }
    }

    public void addTaskView() {
        try {
            final Task task = getTaskInput();
            this.taskController.addTask(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted());
            System.out.println("Tarea agregada correctamente");

        } catch (TaskException | TaskValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeTaskView() {

        try {
            System.out.println("Ingrese el ID de la tarea a eliminar");
            String id = scanner.nextLine();
            this.taskController.removeTask(id);
            System.out.println("Tarea eliminada correctamente");
        } catch (TaskValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showTaskView() {
        try {
            System.out.println("Mostrando todas las tareas");
            this.taskController.showTasks();
        } catch (TaskValidationException | TaskException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateTaskView() {
        try {
            final Task task = getTaskInput();
            this.taskController.updateTask(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted());
            System.out.println("Tarea agregada correctamente");

        } catch (TaskException | TaskValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateTaskCompletedView() {
        try {
            System.out.println("Ingrese el ID de la tarea");
            String id = scanner.nextLine();
            Boolean completed = validateCompleted();
            this.taskController.updateTaskCompleted(id, completed);
            System.out.println("Estado de la tarea actualizado correctamente");
        } catch (TaskException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showCompletedTasksView() {
        try {
            System.out.println("Tareas completadas");
            this.taskController.showCompletedTasks();
        } catch (TaskValidationException | TaskException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showPendingTasksView() {
        try {
            System.out.println("Tareas pendientes");
            this.taskController.showPendingTasks();
        } catch (TaskValidationException | TaskException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Task getTaskInput() {

        String id;
        do {
            System.out.println("Ingresar ID");
            id = scanner.nextLine();
            if (id.isBlank()) System.out.println("El id no puede estar vacío");
        } while (id.isBlank());

        String title;
        do {
            System.out.println("Ingrese el Titulo");
            title = scanner.nextLine();
            if (title.isBlank())
                System.out.println("El titulo no puede estar vacío");
        } while (title.isBlank());

        String description;
        do {
            System.out.println("Ingrese la Descripción");
            description = scanner.nextLine();
            if (description.isBlank())
                System.out.println("La descripción no puede estar vacía");
        } while (description.isBlank());

        Boolean completed = validateCompleted();
        return new Task(id, title, description, completed);
    }

    private @NonNull Boolean validateCompleted() {
        Boolean completed = null;
        while (completed == null) {
            System.out.println("Esta completada la tarea? true / false");
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("true"))
                completed = true;
            else if (input.trim().equalsIgnoreCase("false"))
                completed = false;
            else
                System.out.println("El valor ingresado no es valido, ingrese 'true' o 'false'");
        }
        return completed;
    }
}
