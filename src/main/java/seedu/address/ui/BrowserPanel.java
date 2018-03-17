package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GridPane grid;
    @FXML
    private Label details;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label role;
    @FXML
    private Label status;
    @FXML
    private Label subject;
    @FXML
    private Label level;
    @FXML
    private Label budget;

    public BrowserPanel() {
        super(FXML);

        details.setText("");
        grid.setVisible(false);

        registerAsAnEventHandler(this);
    }

    /**
     * Loads a {@code person}'s details into the browser panel.
     */
    private void loadPersonDetails(Person person) {
        details.setText("Details:");
        grid.setVisible(true);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        status.setText(person.getStatus().value);
        subject.setText(person.getSubject().value);
        level.setText(person.getLevel().value);
        budget.setText("$" + person.getPrice().value);

        if (person.getTags().contains(new Tag("Student"))
            || person.getTags().contains(new Tag("student"))) {
            role.setText("Student");
        }
        else {
            role.setText("Tutor");
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
