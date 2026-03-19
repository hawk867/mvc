package org.danielesteban.task.model;

import org.danielesteban.task.esceptions.TaskException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class TaskRepository {
    List<Task> tasks = new ArrayList<>();

    void save(Task task) {
        throwException(task);
        tasks.add(task);
    }

    Task findById(String id) {
        return tasks.stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    void remove(String id) {
        Task task = findById(id);
        tasks.remove(task);
    }

    void remove(Task task) {
        throwException(task);
        if (!tasks.contains(task))
            throw new TaskException("La tarea no existe en la lista");
        tasks.remove(task);
    }

    List<Task> findAll() {
        if (tasks.isEmpty())
            throw new TaskException("La lista esta vacía");
        return tasks;
    }

    int findIndexById(String id) {
        return IntStream.range(0, tasks.size())
                .filter(i -> tasks.get(i).getId().equals(id))
                .findFirst()
                .orElse(-1);
    }

    void updateTask(Task updateTask) {
        throwException(updateTask);
        int index = findIndexById(updateTask.getId());
        if (index == -1)
            throw new TaskException("No se encontro la tarea");

        tasks.set(index, updateTask);
    }

    private static void throwException(Task task) {
        if (task == null)
            throw new TaskException("La tarea no puede ser nula");
    }
}
