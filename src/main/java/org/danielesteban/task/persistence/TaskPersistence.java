package org.danielesteban.task.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.danielesteban.task.model.Task;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskPersistence {
    private final static String FILE_PATH = "tasks.json";
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveTasks(List<Task> tasks) {
        try(Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static List<Task> loadTasks() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try(Reader reader = new FileReader(FILE_PATH)) {
                Type listType = new TypeToken<List<Task>>() {}.getType();
                return gson.fromJson(reader, listType);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }
}
