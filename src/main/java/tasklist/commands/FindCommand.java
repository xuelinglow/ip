package tasklist.commands;

import java.util.ArrayList;

import tasklist.Storage;
import tasklist.TaskList;
import tasklist.Ui;
import tasklist.tasks.Task;

/** Represents a command to find tasks based on search text input. Implements the Command interface. */
public class FindCommand implements Command {
    protected String input;

    public FindCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        ArrayList<Task> matchedTasks = taskList.searchTask(input);
        String result = "Here are the results:\n";
        for (Task task : matchedTasks) {
            result += task.toString() + "\n";
        }
        return result;
    }
}
