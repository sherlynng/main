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
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_RATE + "4.5";

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
        //PersonCard personCardChanged = new PersonCard(editedPerson, targetIndex.getOneBased());
        //EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(personCardChanged));
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
        Set<PairHash> pairHashes = personToEdit.getPairHashes();

        Rate oldRate = personToEdit.getRate();

        if (newRate.getIsAbsolute()) {
            newRate.setCount(1); // reset count when set absolute
        } else {
            newRate = Rate.accumulatedValue(oldRate, newRate);
        }

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());

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
                          attributeTags, remark, newRate, pairHashes);
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
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "Hardworking student"
            + "\t\t OR \t\t" + COMMAND_WORD + " 1 edit";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Added Remark to %1$s: " + "%2$s";
    public static final String MESSAGE_EDIT_REMARK_SUCCESS = "Editing Remark of %1$s...";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private Remark newRemark;
    private boolean isEditRemark;

    private Person personToEdit;
    private Person editedPerson;

    public RemarkCommand(Index targetIndex, Remark newRemark) {
        requireNonNull(targetIndex);
        requireNonNull(newRemark);

        this.targetIndex = targetIndex;
        this.newRemark = newRemark;
        this.isEditRemark = false;
    }

    public RemarkCommand(Index targetIndex, Remark newRemark, boolean isEditRemark) {
        requireNonNull(targetIndex);
        requireNonNull(newRemark);

        this.targetIndex = targetIndex;
        this.newRemark = newRemark;
        this.isEditRemark = isEditRemark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (!isEditRemark) {
            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            //PersonCard personCardChanged = new PersonCard(editedPerson, targetIndex.getOneBased());
            //EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(personCardChanged));
            return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS,
                    editedPerson.getName(), editedPerson.getRemark()));
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_REMARK_SUCCESS, personToEdit.getName()));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (isEditRemark) {
            EventsCenter.getInstance().post(new EditRemarkEvent(COMMAND_WORD + " "
                    + targetIndex.getOneBased() + " " + PREFIX_REMARK + personToEdit.getRemark().toString()));
        } else {
            editedPerson = createPersonWithNewRemark(personToEdit, newRemark);
        }
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
        Set<PairHash> pairHashes = personToEdit.getPairHashes();

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());

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
                          attributeTags, newRemark, rate, pairHashes);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemarkCommand) other).targetIndex)
                && this.newRemark.equals(((RemarkCommand) other).newRemark)
                && this.isEditRemark == ((RemarkCommand) other).isEditRemark);

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
    public static Optional<Remark> parseRemark(Optional<String> remark) {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(parseRemark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String rate} into a {@code Rate}.
     * Leading and trailing whitespaces will be trimmed.
     * Checks if user wants absolute or cumulative rating.
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
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
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
        boolean isEditCommand;

        isEditCommand = argMultimap.getPreamble().contains("edit");

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK) && !isEditCommand) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
        }

        try {
            if (isEditCommand) {
                index = ParserUtil.parseIndex(argMultimap.getPreamble().replace("edit", ""));
            } else {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        }

        Remark remark;
        if (isEditCommand) {
            remark = ParserUtil.parseRemark((String) null);

            return new RemarkCommand(index, remark, isEditCommand);
        } else {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
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
     * Creates a default rating.
     * @return {@code Rate} with default value of 3.0 and count 1.
     */
    public static Rate getDefaultRate() {
        Rate defaultRate = new Rate(3, true);
        defaultRate.setCount(1);

        return defaultRate;
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
     * Returns true if a given string is a valid person rate.
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

    public boolean getIsAbsolute() {
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
        boolean isFirstTime = false; // check for commands that have different behaviors between first and other tabs

        // first time tab is pressed
        switch (input) {
        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(AddCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_STATUS + " " + PREFIX_ROLE);
            isFindNextField = true;
            isMatchCommand = false;
            break;
        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(EditCommand.COMMAND_WORD + " 1 " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_STATUS + " " + PREFIX_ROLE);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(RemarkCommand.COMMAND_WORD + " 1 " + PREFIX_REMARK);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case RateCommand.COMMAND_WORD:
        case RateCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(RateCommand.COMMAND_WORD + " 1 " + PREFIX_RATE);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(SelectCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(DeleteCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case UnmatchCommand.COMMAND_WORD:
        case UnmatchCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(UnmatchCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case MatchCommand.COMMAND_WORD:
        case MatchCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(MatchCommand.COMMAND_WORD + " 1 2");
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            break;
        default:
            // no autofill
        }

        // subsequent times tab is pressed
        if (isFindNextField) {
            nextCaretPosition = findNextField();
            if (nextCaretPosition != -1) {
                commandTextField.positionCaret(nextCaretPosition);
            }
        }

        if (isMatchCommand) {
            selectIndexToEdit();
        }

        if (isFirstTime) {
            if (commandTextField.getText().length() >= 5
                && commandTextField.getText().substring(0, 5).equals("match")) { // match command
                isMatchCommand = true;
            } else { // all other commands that have different behavior between first and other tabs
                isFindNextField = true;
            }
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
        int caretPosition = commandTextField.getCaretPosition();
        int indexPosition = -1;

        for (int i = caretPosition; i < text.length(); i++) {
            Character character = text.charAt(i);
            if (Character.isDigit(character)) {
                indexPosition = i;
                break;
            }
        }

        commandTextField.positionCaret(indexPosition);
        if (indexPosition != -1) {
            commandTextField.selectForward();
        }
    }
```
