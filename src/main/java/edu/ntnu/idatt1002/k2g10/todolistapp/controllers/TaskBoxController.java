package edu.ntnu.idatt1002.k2g10.todolistapp.controllers;

import com.jfoenix.controls.JFXCheckBox;
import edu.ntnu.idatt1002.k2g10.todolistapp.Session;
import edu.ntnu.idatt1002.k2g10.todolistapp.factories.DialogFactory;
import edu.ntnu.idatt1002.k2g10.todolistapp.factories.FXMLLoaderFactory;
import edu.ntnu.idatt1002.k2g10.todolistapp.models.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for the taskbox component.
 *
 * @author jonathhl, trthingnes
 */
public class TaskBoxController {
    @FXML
    private HBox container;
    @FXML
    private Label titleLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private JFXCheckBox completedBox;

    private final Task task;
    private final TaskViewController parentController;

    /**
     * Creates a new instance of {@link TaskBoxController}. This instance cannot be added to a {@link Node} directly. To
     * add this custom module, use the {@link HBox} from {@link #getRootContainer}.
     *
     * @param task
     *            Task to display in box.
     *
     * @throws IOException
     *             If FXML file cannot be read.
     */
    public TaskBoxController(Task task, TaskViewController parentController) throws IOException {
        FXMLLoader loader = FXMLLoaderFactory.getFXMLLoader("taskbox");
        loader.setController(this);
        loader.load();

        this.parentController = parentController;
        this.task = task;
        updateLabels();

        // Set width to make the container always grow.
        container.setPrefWidth(Double.MAX_VALUE);
    }

    /**
     * Makes the parent controller show task details screen.
     * 
     * @see TaskViewController#showTaskDetails(Task)
     */
    @FXML
    public void showTaskDetails() {
        parentController.showTaskDetails(task);
    }

    /**
     * Updates the completed status of the given event on checkbox check.
     *
     * @param event
     *            Click event.
     */
    @FXML
    public void saveTaskCompletedStatus(Event event) {
        task.setCompleted(completedBox.selectedProperty().get());
        parentController.showTaskDetails(task);
        parentController.refreshAndFilterTaskList();
    }

    /**
     * Deletes the task if the user confirms deletion.
     */
    @FXML
    public void deleteTask() {
        String content = String.format("Are you sure you want to delete '%s'", task.getTitle());
        Dialog<ButtonType> dialog = DialogFactory.getYesNoDialog("Delete task?", content);
        Optional<ButtonType> buttonChoice = dialog.showAndWait();

        if (buttonChoice.isEmpty() || !buttonChoice.get().equals(ButtonType.YES)) {
            return;
        }

        Session.getActiveUser().getTaskList().getTasks().remove(task);
        parentController.refreshAndFilterTaskList();
    }

    /**
     * Update labels with latest information from {@link Task}.
     */
    public void updateLabels() {
        titleLabel.setText(task.getTitle());
        categoryLabel.setText(String.valueOf(task.getCategory().getIcon()));
        priorityLabel.setStyle(String.format("-fx-text-fill: %s !important", task.getPriority().getColor()));
        completedBox.selectedProperty().setValue(task.getCompleted());

        // Build and insert dates.
        StringBuilder dateBuilder = new StringBuilder();
        if (Objects.nonNull(task.getStartTime())) {
            dateBuilder.append(task.getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            dateBuilder.append(" until ");
        }
        dateBuilder.append(task.getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        dateLabel.setText(dateBuilder.toString());
    }

    /**
     * Get the {@link HBox} container of the {@link TaskBoxController}. This can be used to add the box to another
     * {@link Node}.
     *
     * @return {@link HBox} containing {@link TaskBoxController} content.
     */
    public HBox getRootContainer() {
        return container;
    }

    /**
     * Gets the task from the box.
     *
     * @return Task contained in box.
     */
    public Task getTask() {
        return task;
    }
}
