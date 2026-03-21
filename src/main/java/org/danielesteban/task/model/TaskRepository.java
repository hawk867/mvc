package org.danielesteban.task.model;

import org.danielesteban.task.esceptions.TaskException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class TaskRepository {
    List<Task> tasks = new ArrayList<>();

    public void save(Task task) throws TaskException {
        throwException(task);
        tasks.add(task);
    }

    public Task findById(String id) {
        return tasks.stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void remove(String id) {
        Task task = findById(id);
        tasks.remove(task);
    }

    public void remove(Task task) throws TaskException {
        throwException(task);
        if (!tasks.contains(task))
            throw new TaskException("La tarea no existe en la lista");
        tasks.remove(task);
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
    }

    private static void throwException(Task task) throws TaskException {
        if (task == null)
            throw new TaskException("La tarea no puede ser nula");
    }
}
