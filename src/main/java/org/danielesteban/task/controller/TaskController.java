package org.danielesteban.task.controller;

import org.danielesteban.task.esceptions.TaskException;
import org.danielesteban.task.esceptions.TaskValidationException;
import org.danielesteban.task.model.Task;
import org.danielesteban.task.model.TaskRepository;

import java.util.List;

public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(String id, String title, String description, Boolean completed)
            throws TaskValidationException, TaskException {

        validateTaskData(id, title, description, completed);
        Task task = new Task(id, title, description, completed);
        this.taskRepository.save(task);
        System.out.println("La tarea fue agregada correctamente.");
    }

    public void removeTask(String id) throws TaskValidationException {
        if (id == null || id.isBlank())
            throw new TaskValidationException("El id no puede estar vacío,");

        this.taskRepository.remove(id);
    }

    public void showTasks() throws TaskValidationException, TaskException {
        List<Task> tasks = this.taskRepository.findAll();
        if (tasks.isEmpty())
            throw new TaskValidationException("La lista no puede estar vacía");
        tasks.forEach(System.out::println);
    }

    public void showCompletedTasks() throws TaskValidationException, TaskException {
        List<Task> tasks = this.taskRepository.findCompletedTasks();
        if (tasks.isEmpty())
            throw new TaskValidationException("La lista no puede estar vacía");
        tasks.forEach(System.out::println);
    }

    public void showPendingTasks() throws TaskValidationException, TaskException {
        List<Task> tasks = this.taskRepository.findPendingTasks();
        if (tasks.isEmpty())
            throw new TaskValidationException("La lista no puede estar vacía");
        tasks.forEach(System.out::println);
    }

    public void updateTask(String id, String title, String description, Boolean completed)
            throws TaskValidationException, TaskException {

        validateTaskData(id, title, description, completed);
        Task updateTask = new Task(id, title, description, completed);
        this.taskRepository.updateTask(updateTask);
    }

    public void updateTaskCompleted(String id, Boolean completed) throws TaskException {
        this.taskRepository.updateTaskCompleted(id, completed);
    }

    private void validateTaskData(String id, String title, String description, Boolean completed)
            throws TaskValidationException {

        if (id == null || id.trim().isEmpty())
            throw new TaskValidationException("El id no puede estar vacío.");

        if (title == null || title.trim().isEmpty())
            throw new TaskValidationException("El titulo no puede estar vacío.");

        if (description == null || description.trim().isEmpty())
            throw new TaskValidationException("La descripción no puede estar vacía.");

        if (completed == null)
            throw new TaskValidationException("El estado no puede estar vacío.");
    }
}
