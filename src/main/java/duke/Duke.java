package duke;

import duke.task.TaskList;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents an intelligent chat robot that helps a person to keep track of various things with encouraging quotes.
 */
public class Duke {
    private static final String SAVING_ERROR_MSG = "⚠ Oops! Something wrong when closing:(";
    private static final String BYE_MSG = "Bye!\n\"Beware the barrenness of a busy life.\"";
    private Parser parser;
    private Storage storage;
    private TaskList tasks;
    private Stage stage;

    /**
     * Initializes the chat robot. Establishes task list and parser.
     */
    public Duke(Stage stage) {
        this.stage = stage;
        storage = new Storage();
        tasks = new TaskList();
        try {
            tasks.addTasks(storage.loadFile());
        } catch (Exception e) {
            // do nothing, no file to load
        }
        parser = new Parser(tasks);
    }

    /**
     * Returns the response of the chat robot to the user input.
     *
     * @param input the user input.
     * @return the response of the chat robot.
     */
    public String getResponse(String input) {
        try {
            String output = parser.parse(input);
            if (!parser.isRunning()) {
                return exit();
            }
            return output;
        } catch (DukeException e) {
            return handleException(e);
        }
    }

    private String exit() {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            return SAVING_ERROR_MSG;
        }
        return BYE_MSG;
    }

    public void close() {
        this.stage.close();
    }

    private String handleException(DukeException e) {
        String message = e.getMessage();
        String warning;
        switch (message) {
        case "todo error":
            warning = "⚠ Oops! Need description for the todo:(";
            break;
        case "deadline error":
            warning = "⚠ Oops! Need description and valid by date for the deadline:(";
            break;
        case "event error":
            warning = "⚠ Oops! Need description, valid from and to dates for the event:(";
            break;
        case "task not found":
            warning = "⚠ Oops! Cannot find task:(";
            break;
        case "undefined":
            warning = "⚠ Sorry! I am not able to understand you. Try another language:D";
            break;
        default:
            warning = "⚠ Oops! Something went wrong:(";
            break;
        }
        return warning;
    }

}

