# dannyngmx94
###### \java\seedu\address\commons\events\ui\ShowChartsEvent.java
``` java
/**
 * An event requesting to view charts in person panel.
 */
public class ShowChartsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter all persons whose tag contain any of "
            + "the specified predicate and displays them as a list with index numbers.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " math";

    private final Predicate<Person> predicate;

    public FilterCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ViewStatsCommand.java
``` java
/**
 * Show statistical data from the address book.
 */
public class ViewStatsCommand extends Command {

    public static final String COMMAND_WORD = "viewStats";

    public static final String MESSAGE_VIEW_STATS_SUCCESS = "Show chart";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowChartsEvent());
        return new CommandResult(MESSAGE_VIEW_STATS_SUCCESS);

    }
}
```
###### \java\seedu\address\logic\parser\FilterCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) {
        List<Predicate<Person>> predicateList = new ArrayList<>();
        predicateList.add(new KeywordPredicate(args.trim()));
        Predicate<Person> allPredicates = combineAllPredicates(predicateList);
        return new FilterCommand(allPredicates);
    }

    /**
     * Combines all the predicates in the predicateList into a single Predicate
     * @param predicateList a list of non-empty predicates
     * @return a single Predicate combining all the predicates in the predicateList
     */
    private Predicate<Person> combineAllPredicates(List<Predicate<Person>> predicateList) {
        assert(predicateList.size() >= 1);
        Predicate<Person> allPredicates = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            allPredicates.and(predicateList.get(i));
        }
        return allPredicates;
    }
}
```
###### \java\seedu\address\ui\ChartsPanel.java
``` java
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
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
/**
 * Container for both browser panel and charts panel
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

    public InfoPanel(ObservableList<Person> personList) {
        super(FXML);

        fillInnerParts(personList);

        browserPlaceholder.toFront();
        registerAsAnEventHandler(this);
    }

    /**
     * Helper method to fill UI placeholders
     */
    public void fillInnerParts(ObservableList<Person> personList) {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        pieChart = new ChartsPanel(personList);
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

        chartPlaceholder.toFront();
    }
}
```
