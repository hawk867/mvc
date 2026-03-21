package org.danielesteban;

import org.danielesteban.task.controller.TaskController;
import org.danielesteban.task.model.TaskRepository;
import org.danielesteban.task.view.TaskView;


public class Main {
    static void main() {
        TaskRepository taskRepository = new TaskRepository();
        TaskController taskController = new TaskController(taskRepository);
        TaskView taskView = new TaskView(taskController);

        taskView.showMenu();
    }
}
