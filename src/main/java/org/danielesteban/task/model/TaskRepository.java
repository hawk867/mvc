package org.danielesteban.task.model;

import org.danielesteban.task.esceptions.TaskException;
import org.danielesteban.task.persistence.TaskPersistence;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class TaskRepository {
    List<Task> tasks;
    public TaskRepository() {
        this.tasks = TaskPersistence.loadTasks();
    }

    public void save(Task task) throws TaskException {
        throwException(task);
        tasks.add(task);
        TaskPersistence.saveTasks(this.tasks);
    }

    public Task findById(String id) {
        return tasks.stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public List<Task> findCompletedTasks() throws TaskException {
        return Optional.of(
                tasks.stream()
                        .filter(Task::getCompleted)
                        .toList()
            )
            .filter(list -> !list.isEmpty())
            .orElseThrow(() -> new TaskException("No hay tareas completadas"));
    }

    public List<Task> findPendingTasks() throws TaskException {
        return Optional.of(
                        tasks.stream()
                                .filter(t -> !t.getCompleted())
                                .toList()
                )
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new TaskException("No hay tareas pendientes"));
    }

    public void remove(String id) {
        Task task = findById(id);
        tasks.remove(task);
        TaskPersistence.saveTasks(this.tasks);
    }

    public List<Task> findAll() throws TaskException {
        if (tasks.isEmpty())
            throw new TaskException("La lista esta vacía");
        return tasks;
    }

    public int findIndexById(String id) {
        return IntStream.range(0, tasks.size())
                .filter(i -> tasks.get(i).getId().equals(id))
                .findFirst()
                .orElse(-1);
    }

    public void updateTask(Task updateTask) throws TaskException {
        throwException(updateTask);
        int index = findIndexById(updateTask.getId());
        if (index == -1)
            throw new TaskException("No se encontro la tarea");

        tasks.set(index, updateTask);
        TaskPersistence.saveTasks(this.tasks);
    }

    public void updateTaskCompleted(String id, Boolean completed) throws TaskException {
        int index = findIndexById(id);
        if (index == -1)
            throw new TaskException("No se encontro la tarea");

        tasks.get(index).setCompleted(completed);
        TaskPersistence.saveTasks(this.tasks);
    }

    private static void throwException(Task task) throws TaskException {
        if (task == null)
            throw new TaskException("La tarea no puede ser nula");
    }
}
