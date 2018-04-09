# sherlynng
###### \java\seedu\address\logic\commands\RateCommand.java
``` java
/**
 * Adds a remark to person to the address book.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";
    public static final String COMMAND_WORD_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds rating to person identified by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer), RATE (must be an integer between 0 and 5 (inclusive)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_RATE;

    public static final String MESSAGE_RATE_PERSON_SUCCESS = "Added Rating to %1$s: " + "%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private Rate newRate;

    private Person personToEdit;
    private Person editedPerson;

    public RateCommand(Index targetIndex, Rate newRate) {
        requireNonNull(targetIndex);
        requireNonNull(newRate);

        this.targetIndex = targetIndex;
        this.newRate = newRate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RATE_PERSON_SUCCESS,
                                editedPerson.getName(), newRate));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = createPersonWithNewRate(personToEdit, newRate);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}.
     */
    private static Person createPersonWithNewRate(Person personToEdit, Rate newRate) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Price price = personToEdit.getPrice();
        Subject subject = personToEdit.getSubject();
        Level level = personToEdit.getLevel();
        Status status = personToEdit.getStatus();
        Role role = personToEdit.getRole();
        Remark remark = personToEdit.getRemark();
        PairHash pairHash = personToEdit.getPairHash();

        Rate oldRate = personToEdit.getRate();

        if (newRate.getisAbsolute()) {
            newRate.setCount(1); // reset count when set absolute
        } else {
            newRate = Rate.accumulatedValue(oldRate, newRate);
        }

        Set<Tag> updatedTags = personToEdit.getTags();

        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //clean out old person's attribute tags, then add the new ones

        //ignore if attribute is empty (not entered yet by user)
        if (!personToEdit.getPrice().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getPrice().toString(), Tag.AllTagTypes.PRICE));
        }
        if (!personToEdit.getLevel().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getLevel().toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!personToEdit.getSubject().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getSubject().toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!personToEdit.getStatus().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getStatus().toString(), Tag.AllTagTypes.STATUS));
        }
        if (!personToEdit.getRole().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getRole().toString(), Tag.AllTagTypes.ROLE));
        }

        return new Person(name, phone, email, address, price, subject, level, status, role,
                          attributeTags, remark, newRate, pairHash);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RateCommand // instanceof handles nulls
                && this.targetIndex.equals(((RateCommand) other).targetIndex)
                && this.newRate.equals(((RateCommand) other).newRate));
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Adds a remark to person to the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_WORD_ALIAS = "rk";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a remark to person identified by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK;

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Added Remark to %1$s: " + "%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private Remark newRemark;

    private Person personToEdit;
    private Person editedPerson;

    public RemarkCommand(Index targetIndex, Remark newRemark) {
        requireNonNull(targetIndex);
        requireNonNull(newRemark);

        this.targetIndex = targetIndex;
        this.newRemark = newRemark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS,
                                editedPerson.getName(), editedPerson.getRemark()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = createPersonWithNewRemark(personToEdit, newRemark);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}.
     */
    private static Person createPersonWithNewRemark(Person personToEdit, Remark newRemark) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Price price = personToEdit.getPrice();
        Subject subject = personToEdit.getSubject();
        Level level = personToEdit.getLevel();
        Status status = personToEdit.getStatus();
        Role role = personToEdit.getRole();
        Rate rate = personToEdit.getRate();
        PairHash pairHash = personToEdit.getPairHash();

        Set<Tag> updatedTags = personToEdit.getTags();

        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //clean out old person's attribute tags, then add the new ones

        //ignore if attribute is empty (not entered yet by user)
        if (!personToEdit.getPrice().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getPrice().toString(), Tag.AllTagTypes.PRICE));
        }
        if (!personToEdit.getLevel().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getLevel().toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!personToEdit.getSubject().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getSubject().toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!personToEdit.getStatus().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getStatus().toString(), Tag.AllTagTypes.STATUS));
        }
        if (!personToEdit.getRole().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getRole().toString(), Tag.AllTagTypes.ROLE));
        }

        return new Person(name, phone, email, address, price, subject, level, status, role,
                          attributeTags, newRemark, rate, pairHash);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemarkCommand) other).targetIndex)
                && this.newRemark.equals(((RemarkCommand) other).newRemark));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        if (remark == null) {
            remark = ""; // set it as empty string if there is no user input
        }
        String trimmedRemark = remark.trim();

        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(parseRemark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String rate} into a {@code Rate}.
     * Leading and trailing whitespaces will be trimmed.
     * Checks if user wants absolute or cummulative rating.
     */
    public static Rate parseRate(String rate) throws IllegalValueException {
        requireNonNull(rate);

        if (rate.equals("")) {
            throw new IllegalValueException(Rate.MESSAGE_RATE_CONSTRAINTS);
        }

        Character lastChar = rate.charAt(rate.length() - 1);
        boolean isAbsolute = false;

        // user wants absolute rate value
        if (lastChar.equals('-')) {
            rate = rate.substring(0, rate.length() - 1);
            isAbsolute = true;
        }
        String trimmedRate = rate.trim();
        if (!Rate.isValidRate(rate)) {
            throw new IllegalValueException(Rate.MESSAGE_RATE_CONSTRAINTS);
        }

        return new Rate(Double.parseDouble(trimmedRate), isAbsolute);
    }

    /**
     * Parses a {@code Optional<String> rate} into an {@code Optional<Rate>} if {@code rate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Rate> parseRate(Optional<String> rate) throws IllegalValueException {
        requireNonNull(rate);
        return rate.isPresent() ? Optional.of(parseRate(rate.get())) : Optional.empty();
    }
}
```
###### \java\seedu\address\logic\parser\RateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RateCommand object
 */
public class RateCommandParser implements Parser<RateCommand> {

    /**
     * Parses the given {@code String} of rates in the context of the RateCommand
     * and returns a RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATE);

        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_RATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE));
        }

        Rate rate;
        try {
            rate = ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE)).get();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new RateCommand(index, rate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of remarks in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE));
        }


        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE));
        }

        Remark remark;
        try {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new RemarkCommand(index, remark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
```
###### \java\seedu\address\model\person\Rate.java
``` java
/**
 * Represents a Person's rating in the address book.
 * Guarantees: immutable;
 */
public class Rate {

    /* Regex notation
    ^                   # Start of string
    (?:                 # Either match...
    5(?:\.0)?           # 5.0 (or 5)
    |                   # or
    [0-4](?:\.[0-9])?   # 0.0-4.9 (or 1-4)
    |                   # or
    0?\.[1-9]           # 0.1-0.9 (or .1-.9)
    )                   # End of alternation
    $                   # End of string
     */
    public static final String RATE_VALIDATION_REGEX = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])$";
    public static final String RATE_VALIDATION_REGEX_ABSOLUTE = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])" + "-";
    public static final String MESSAGE_RATE_CONSTRAINTS =
            "Rate must be a number between 0 and 5 (inclusive) with at most 1 decimal place";

    private double value;
    private int count;
    private boolean isAbsolute;

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rate (double rating, boolean isAbsolute) {
        requireNonNull(rating);
        checkArgument(isValidRate(Double.toString(rating)), MESSAGE_RATE_CONSTRAINTS);

        this.value = rating;
        this.isAbsolute = isAbsolute;
    }

    /**
     * Calculates the accumulated value of a person's rating
     * @param oldRate
     * @param newRate
     * @return {@code Rate} that contains updated value and count
     */
    public static Rate accumulatedValue (Rate oldRate, Rate newRate) {
        double value;
        double newValue;

        value = oldRate.getValue() * oldRate.getCount();
        newValue = (value + newRate.getValue()) / (oldRate.getCount() + 1);
        newValue = Math.floor(newValue * 10) / 10;

        newRate = new Rate(newValue, true);
        newRate.setCount(oldRate.getCount() + 1);

        return newRate;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidRate(String test) {
        return test.equals("") || test.matches(RATE_VALIDATION_REGEX) || test.matches(RATE_VALIDATION_REGEX_ABSOLUTE);
    }

    public double getValue() {
        return this.value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getisAbsolute() {
        return isAbsolute;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rate // instanceof handles nulls
                && this.value == ((Rate) other).value
                && this.count == ((Rate) other).count); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable;
 */
public class Remark {

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GridPane grid;
    @FXML
    private HBox ratingBox;
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
    private Label price;
    @FXML
    private Label remark;
    @FXML
    private Label rating;
    @FXML
    private Label rateCount;

    public BrowserPanel() {
        super(FXML);

        name.setText("");
        grid.setVisible(false);
        ratingBox.setVisible(false);

        registerAsAnEventHandler(this);
    }

    /**
     * Loads a {@code person}'s details into the browser panel.
     */
    public void loadPersonDetails(Person person) {
        grid.setVisible(true);
        ratingBox.setVisible(true);

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        status.setText(person.getStatus().value);
        subject.setText(person.getSubject().value);
        level.setText(person.getLevel().value);
        price.setText("$" + person.getPrice().value + " / hr");
        role.setText(person.getRole().value);
        remark.setText(person.getRemark().value);
        rating.setText(Double.toString(person.getRate().getValue()));
        rateCount.setText(Integer.toString(person.getRate().getCount()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case TAB:
            keyEvent.consume();
            autofillCommand();
            break;
        case DELETE:
            keyEvent.consume();
            deletePreviousPrefix();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Sets {@code CommandBox}'s text field with input format and
     * if next field is present, it positions the caret to the next field.
     */
    private void autofillCommand() {
        String input = commandTextField.getText();
        int nextCaretPosition = -1;
        boolean isFirstTime = false; // set this to check for edit command

        // first time tab is pressed
        switch (input) {
        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(AddCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_STATUS + " " + PREFIX_ROLE);
            canTab = true;
            break;
        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(EditCommand.COMMAND_WORD + " 1 " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_STATUS + " " + PREFIX_ROLE);
            selectIndexToEdit();
            canTab = false;
            isFirstTime = true;
            break;
        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(SelectCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            canTab = false;
            break;
        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(DeleteCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            canTab = false;
            break;
        default:
            // no autofill
        }

        // subsequent times tab is pressed
        if (canTab) {
            nextCaretPosition = findNextField();
            if (nextCaretPosition != -1) {
                commandTextField.positionCaret(nextCaretPosition);
            }
        }
        if (isFirstTime) {
            canTab = true;
        }
    }

    /**
     * Deletes the previous prefix from current caret position and
     * if next field is present, it positions the caret to the next field.
     */
    private void deletePreviousPrefix() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int deleteStart = text.lastIndexOf(" ", caretPosition - 1);

        if (deleteStart != -1) {
            commandTextField.deleteText(deleteStart, caretPosition);
            commandTextField.positionCaret(findNextField());
        }
    }

    /**
     * Finds the next input field from current caret position and
     * if next field is present, it positions the caret to the next field.
     */
    private int findNextField() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int nextFieldPosition = text.indexOf("/", caretPosition);

        return nextFieldPosition + 1;
    }

    /**
     * Positions the caret to index position
     * and selects the index to be edited.
     */
    private void selectIndexToEdit() {
        String text = commandTextField.getText();
        int indexPosition = text.indexOf("1") + 1;

        commandTextField.positionCaret(indexPosition);
        commandTextField.selectBackward();
    }
```
###### \resources\view\BrowserPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Font?>


<StackPane styleClass="browser_stackPane_odd" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <AnchorPane fx:id="browserPane" maxHeight="-Infinity" maxWidth="900" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="606.0">
         <children>
            <Label fx:id="name" layoutX="14.0" layoutY="56.0" prefHeight="59.0" prefWidth="462.0" styleClass="browser_big_label" text="Alex Yeoh" AnchorPane.leftAnchor="0.0" AnchorPane.topAnchor="40.0">
               <font>
                  <Font name="System Bold" size="32.0" />
               </font>
            </Label>
            <GridPane fx:id="grid" layoutX="-1.0" layoutY="160.0" maxHeight="270.0" prefHeight="267.0" prefWidth="614.0" AnchorPane.bottomAnchor="3.0" AnchorPane.leftAnchor="-1.0" AnchorPane.rightAnchor="-7.0" AnchorPane.topAnchor="130.0">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="270.0" minWidth="10.0" prefWidth="171.0" />
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="640" minWidth="10.0" prefWidth="379.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="60.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
               <children>
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.rowIndex="1" />
                  <StackPane prefHeight="52.0" prefWidth="373.0" styleClass="browser_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="browser_stackPane_even" GridPane.rowIndex="2" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="browser_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="browser_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="4" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="browser_stackPane_even" GridPane.rowIndex="4" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="browser_stackPane_even" GridPane.rowIndex="6" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="browser_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="6" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="browser_stackPane_even" GridPane.rowIndex="8" />
                  <StackPane prefHeight="50.0" prefWidth="347.0" styleClass="browser_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="8" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.rowIndex="3" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.rowIndex="5" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="5" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.rowIndex="7" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="7" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.rowIndex="9" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="browser_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="9" />
                  <Label fx:id="remark" prefHeight="60.0" prefWidth="400.0" styleClass="browser_label_details" text="\$remark" wrapText="true" GridPane.columnIndex="1" GridPane.rowIndex="9" />
                  <Label styleClass="browser_label" text="Phone:" GridPane.rowIndex="1">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label styleClass="browser_label" text="Address:" GridPane.rowIndex="2">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="browser_label" text="Email:" GridPane.rowIndex="3">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="browser_label" text="Role:" GridPane.rowIndex="4">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="browser_label" text="Status:" GridPane.rowIndex="5">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="browser_label" text="Subject:" GridPane.rowIndex="6">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="browser_label" text="Level:" GridPane.rowIndex="7">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="browser_label" text="Price:" GridPane.rowIndex="8">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label fx:id="phone" styleClass="browser_label_details" text="\$phone" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                  <Label fx:id="address" styleClass="browser_label_details" text="\\$address" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                  <Label fx:id="email" styleClass="browser_label_details" text="\\$email" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                  <Label fx:id="role" styleClass="browser_label_details" text="\$role" GridPane.columnIndex="1" GridPane.rowIndex="4" />
                  <Label fx:id="status" styleClass="browser_label_details" text="\$status" GridPane.columnIndex="1" GridPane.rowIndex="5" />
                  <Label fx:id="subject" styleClass="browser_label_details" text="\$subject" GridPane.columnIndex="1" GridPane.rowIndex="6" />
                  <Label fx:id="level" styleClass="browser_label_details" text="\$level" GridPane.columnIndex="1" GridPane.rowIndex="7" />
                  <Label fx:id="price" styleClass="browser_label_details" text="\\$price" GridPane.columnIndex="1" GridPane.rowIndex="8" />
                  <Label styleClass="browser_label" text="Remark:" GridPane.rowIndex="9">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
               </children>
               <padding>
                  <Insets bottom="5.0" top="5.0" />
               </padding>
            </GridPane>
         </children>
         <padding>
            <Insets bottom="5.0" top="5.0" />
         </padding>
      <HBox fx:id="ratingBox" layoutX="297.0" layoutY="95.0" prefHeight="25.0" prefWidth="193.0">
         <children>
            <Label styleClass="browser_label" text="Rating:">
               <HBox.margin>
                  <Insets />
               </HBox.margin>
            </Label>
            <Label fx:id="rating" styleClass="browser_label_details" text="3.9">
               <padding>
                  <Insets left="15.0" />
               </padding>
               <HBox.margin>
                  <Insets top="1.0" />
               </HBox.margin>
            </Label>
            <Label styleClass="browser_label_details" text="/ 5.0">
               <opaqueInsets>
                  <Insets />
               </opaqueInsets>
               <HBox.margin>
                  <Insets top="1.0" />
               </HBox.margin>
               <padding>
                  <Insets left="5.0" />
               </padding>
            </Label>
            <Label fx:id="rateCount" styleClass="browser_label_details" text="3">
               <HBox.margin>
                  <Insets left="30.0" top="8.0" />
               </HBox.margin>
            </Label>
            <ImageView fitHeight="26.0" fitWidth="27.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../../../../docs/images/rateCount.png" />
               </image>
               <HBox.margin>
                  <Insets left="5.0" />
               </HBox.margin>
            </ImageView>
         </children>
      </HBox>
      </AnchorPane>
   </children>
</StackPane>
```
###### \resources\view\DarkTheme.css
``` css
.browser_big_label {
     -fx-font-family: "Segoe UI";
     -fx-font-size: 40px;
     -fx-text-fill: #fffacd;
     -fx-text-weight: bold
 }

.browser_label {
     -fx-font-family: "Segoe UI";
     -fx-font-size: 15px;
     -fx-text-fill: #ffffff;
     -fx-text-weight: bold
 }

.browser_label_details {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 14px;
    -fx-text-fill: #ffffff;
}

.browser_stackPane_odd {
    -fx-background-color: #333333
}

.browser_stackPane_even {
    -fx-background-color: #4C4C4C
}
```
###### \resources\view\DarkTheme.css
``` css

.anchor-pane {
     -fx-background-color: derive(#1d1d1d, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#1d1d1d, 20%);
     -fx-border-color: derive(#1d1d1d, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
}

.context-menu {
    -fx-background-color: derive(#1d1d1d, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#tags .teal {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
}

#tags .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: yellow;
}

#tags .blue {
    -fx-text-fill: white;
    -fx-background-color: blue;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#tags .brown {
    -fx-text-fill: white;
    -fx-background-color: brown;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: green;
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: black;
    -fx-background-color: black;
}

#tags .grey {
    -fx-text-fill: black;
    -fx-background-color: grey;
}
```
