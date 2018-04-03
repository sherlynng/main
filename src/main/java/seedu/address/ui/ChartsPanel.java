package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowChartsEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

//@@author dannyngmx94
/**
 * The Chart Panel of the App.
 */
public class ChartsPanel extends UiPart<Region> {

    private static final String FXML = "ChartsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<Person> personList;

    @FXML
    private PieChart RoleDistribution;


    public ChartsPanel(ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;
        loadChartsDetails();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads chart details into the chart panel.
     */
    public void loadChartsDetails() {
        ObservableList<Person> tutorList = personList.filtered(person -> person.getRole().equals(new Role("Tutor")));
        ObservableList<Person> studentList =
                personList.filtered(person -> person.getRole().equals(new Role("Student")));
        int numTutor = tutorList.size();
        int numStudent = studentList.size();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Tutor", numTutor),
                new PieChart.Data("student", numStudent));
        RoleDistribution.setData(pieChartData);
    }

    @Subscribe
    private void handleShowChartsEvent(ShowChartsEvent event) {
        System.out.println(this);
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadChartsDetails();
    }
}
