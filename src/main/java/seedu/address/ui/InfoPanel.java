package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowChartsEvent;

/**
 * Container for both browser panel and person information panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BrowserPanel browserPanel;
    private ChartsPanel pieChart;

    @FXML
    private StackPane infoPlaceHolder;
    @FXML
    private StackPane browserPlaceholder;
    @FXML
    private StackPane chartPlaceholder;

    public InfoPanel() {
        super(FXML);

        fillInnerParts();

        browserPlaceholder.toFront();
        registerAsAnEventHandler(this);
    }

    /**
     * Helper method to fill UI placeholders
     */
    public void fillInnerParts() {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        pieChart = new ChartsPanel();
        chartPlaceholder.getChildren().add(pieChart.getRoot());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPanel.loadPersonDetails(event.getNewSelection().person);

        browserPlaceholder.toFront();
    }

    @Subscribe
    private void handleShowChartsEvent(ShowChartsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        pieChart.loadChartsDetails();

        chartPlaceholder.toFront();
    }
}
