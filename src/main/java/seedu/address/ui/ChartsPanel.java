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

/**
 * The Browser Panel of the App.
 */
public class ChartsPanel extends UiPart<Region> {

    private static final String FXML = "ChartsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private PieChart RoleDistribution;


    public ChartsPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
    }

    /**
     * Loads a {@code person}'s details into the browser panel.
     */
    public void loadChartsDetails() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Tutor", 10),
                new PieChart.Data("student", 15));
        RoleDistribution.setData(pieChartData);
    }

    @Subscribe
    private void handleShowChartsEvent(ShowChartsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadChartsDetails();
    }
}
