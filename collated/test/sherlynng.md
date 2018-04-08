# sherlynng
###### \java\seedu\address\logic\commands\RateCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RateCommand.
 */
public class RateCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        new RateCommand(null, rate);
    }

    @Test
    public void constructor_nullRate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RateCommand(INDEX_FIRST_PERSON, null);
    }

    @Test
    public void execute_filteredListAbsoluteRate_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        rateCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_RATE_PERSON_SUCCESS,
                                 editedPerson.getName(), VALID_RATE_BOB);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListAccumulatedRate_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withRate(VALID_RATE_AMY, RATECOUNT_AMY).build();
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), false);
        rate.setCount(Integer.parseInt(RATECOUNT_AMY));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        rateCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_RATE_PERSON_SUCCESS,
                editedPerson.getName(), VALID_RATE_AMY);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds rate in filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
                .withPrice("50").withSubject("math").withStatus("Matched").withLevel("lower Sec")
                .withRole("Tutor").withRemark("Hardworking but slow learner.").withRate("3.0", "1").build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // add rate -> adds rate to first person
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person with rate added again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        // execution failed -> rateCommand not pushed into undoRedoStack
        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds a rate to a {@code Person} from a filtered list.
     * 2. Undo the adding of rate.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the adding of rate. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
                .withPrice("50").withSubject("math").withStatus("Matched").withLevel("lower Sec")
                .withRole("Student").withRemark("Not self motivated.").withRate("2.1", "2").build();
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // add rate -> adds rate to second person in unfiltered person list / first person in filtered person list
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> adds rate to same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        final RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);

        // same values -> returns true
        Person bob = new PersonBuilder().withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        RateCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, bob.getRate());
        assertTrue(rateCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(rateCommand.equals(rateCommand));

        // null -> returns false
        assertFalse(rateCommand.equals(null));

        // different types -> returns false
        assertFalse(rateCommand.equals(new ClearCommand()));

        // different index -> returns false
        bob = new PersonBuilder().withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        RateCommand commandWithDifferentIndex = prepareCommand(INDEX_SECOND_PERSON, bob.getRate());
        assertFalse(rateCommand.equals(commandWithDifferentIndex));

        // different rate -> returns false
        Person amy = new PersonBuilder().withRate(VALID_RATE_AMY, RATECOUNT_AMY).build();
        RateCommand commandWithDifferentPerson = prepareCommand(INDEX_FIRST_PERSON, amy.getRate());
        assertFalse(rateCommand.equals(commandWithDifferentPerson));
    }

    /**
     * Returns an {@code RateCommand} with parameters {@code index} and {@code rate}
     */
    private RateCommand prepareCommand(Index index, Rate rate) {
        RateCommand rateCommand = new RateCommand(index, rate);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Remark remark = new Remark(REMARK_BOB);
        new RemarkCommand(null, remark);
    }

    @Test
    public void constructor_nullRemark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemarkCommand(INDEX_FIRST_PERSON, null);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withRemark(REMARK_BOB).build();
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);
        remarkCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_REMARK_PERSON_SUCCESS,
                                 editedPerson.getName(), editedPerson.getRemark());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, remark);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds remark in filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, remark);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
                .withPrice("50").withSubject("math").withStatus("Matched").withLevel("lower Sec")
                .withRole("Tutor").withRemark(REMARK_BOB).build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // add remark -> adds remark to first person
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person with remark added again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, remark);

        // execution failed -> remarkCommand not pushed into undoRedoStack
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds a remark to a {@code Person} from a filtered list.
     * 2. Undo the adding of remark.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the adding of remark. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
                .withPrice("50").withSubject("Math").withStatus("Matched").withLevel("Lower Sec")
                .withRole("Student").withRemark(REMARK_BOB).build();
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // add remark -> adds remark to second person in unfiltered person list / first person in filtered person list
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> adds remark to same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Remark remark = new Remark(REMARK_BOB);
        final RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);

        // same values -> returns true
        Person bob = new PersonBuilder().withRemark(REMARK_BOB).build();
        RemarkCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, bob.getRemark());
        assertTrue(remarkCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(remarkCommand.equals(remarkCommand));

        // null -> returns false
        assertFalse(remarkCommand.equals(null));

        // different types -> returns false
        assertFalse(remarkCommand.equals(new ClearCommand()));

        // different index -> returns false
        bob = new PersonBuilder().withRemark(REMARK_BOB).build();
        RemarkCommand commandWithDifferentIndex = prepareCommand(INDEX_SECOND_PERSON, bob.getRemark());
        assertFalse(remarkCommand.equals(commandWithDifferentIndex));

        // different remark -> returns false
        Person amy = new PersonBuilder().withRemark(REMARK_AMY).build();
        RemarkCommand commandWithDifferentPerson = prepareCommand(INDEX_FIRST_PERSON, amy.getRemark());
        assertFalse(remarkCommand.equals(commandWithDifferentPerson));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + REMARK_AMY);
        Remark remark = new Remark(REMARK_AMY);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_remarkAliased() throws Exception {
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + REMARK_AMY);
        Remark remark = new Remark(REMARK_AMY);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_rate() throws Exception {
        RateCommand command = (RateCommand) parser.parseCommand(RateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + VALID_RATE_AMY);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), true);
        assertEquals(new RateCommand(INDEX_FIRST_PERSON, rate), command);
    }

    @Test
    public void parseCommand_rateAliased() throws Exception {
        RateCommand command = (RateCommand) parser.parseCommand(RateCommand.COMMAND_WORD_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + VALID_RATE_AMY);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), true);
        assertEquals(new RateCommand(INDEX_FIRST_PERSON, rate), command);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseRemark_null_returnsEmptyStringRemark() {
        Remark expectedRemark = new Remark("");
        assertEquals(expectedRemark, ParserUtil.parseRemark((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((Optional<String>) null));
    }

    @Test
    public void parseRemark_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemark_validValueWithoutWhitespace_returnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(VALID_REMARK));
        assertEquals(Optional.of(expectedRemark), ParserUtil.parseRemark(Optional.of(VALID_REMARK)));
    }

    @Test
    public void parseRemark_validValueWithWhitespace_returnsTrimmedRemark() throws Exception {
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(remarkWithWhitespace));
        assertEquals(Optional.of(expectedRemark), ParserUtil.parseRemark(Optional.of(remarkWithWhitespace)));
    }
}
```
###### \java\seedu\address\logic\parser\RateCommandParserTest.java
``` java
public class RateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT + RateCommand.MESSAGE_USAGE);

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        String userInput = PREFIX_RATE + INVALID_RATE_EXCEEDRANGE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        String userInput = "-5" + " " + PREFIX_RATE + VALID_RATE_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // zero index
        userInput = "0" + " " + PREFIX_RATE + VALID_RATE_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidRate_failure() {
        // exceed rate range
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + INVALID_RATE_EXCEEDRANGE;
        assertParseFailure(parser, userInput, MESSAGE_RATE_CONSTRAINTS);

        // negative rate
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + INVALID_RATE_NEGATIVE;
        assertParseFailure(parser, userInput, MESSAGE_RATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecifiedAbsoulteRate_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + VALID_RATE_AMY;

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), true);
        RateCommand expectedCommand = new RateCommand(targetIndex, rate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecifiedAccumulatedRate_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + VALID_RATE_BOB;

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        RateCommand expectedCommand = new RateCommand(targetIndex, rate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT + RemarkCommand.MESSAGE_USAGE);

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, REMARK_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + REMARK_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + REMARK_AMY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + REMARK_AMY;

        Remark remark = new Remark(REMARK_AMY);
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexFieldSpecifiedNullRemark_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;

        Remark remark = new Remark("");
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\model\person\RateTest.java
``` java
public class RateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rate((Double) null, true));
    }

    @Test
    public void checkRateAccumulatedValue() {
        Rate oldRate = new Rate(2, true);
        oldRate.setCount(2);
        Rate newRate = new Rate(3, true);
        Rate expectedRate = new Rate(2.3, true);
        expectedRate.setCount(3);

        Rate actualRate = Rate.accumulatedValue(oldRate, newRate);
        assertTrue(expectedRate.equals(actualRate));
    }

    @Test
    public void checkRateToString() {
        assertTrue(new Rate(3, true).toString().equals("3.0")); // Integer rating
        assertTrue(new Rate(2.1, true).toString().equals("2.1")); // Rating with decimal value
    }

    @Test
    public void isValidRate() {

        // invalid rate
        assertFalse(Rate.isValidRate("-1.0")); // negative numbers
        assertFalse(Rate.isValidRate("6.0")); // exceed 5

        // valid rate
        assertTrue(Rate.isValidRate("3.3"));
        assertTrue(Rate.isValidRate("1")); // single digit
    }

    @Test
    public void checkRateEquality() {
        //test rate against non-rate type
        assertFalse(new Rate(1, true).equals(null));
        assertFalse(new Rate(1, true).equals(new Tag("100")));
        //test correctly returns equal if rate string is the same
        assertTrue(new Rate(1, true).equals(new Rate(1, true)));
    }

    @Test
    public void checkRateHashCode() {
        Rate rate = new Rate(3, true);
        assertTrue(rate.hashCode() == rate.hashCode());
        rate = new Rate(2.1, true);
        assertTrue(rate.hashCode() == rate.hashCode());
        rate = new Rate(4.5, false);
        assertTrue(rate.hashCode() == rate.hashCode());
    }
}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void checkRemarkToString() {
        assertTrue(new Remark("Friendly and patient.").toString() == "Friendly and patient.");
    }

    @Test
    public void checkRemarkEquality() {
        //test remark against non-address type
        assertFalse(new Remark("Friendly and patient.").equals(null));
        assertFalse(new Remark("Friendly and patient.").equals(new Address("Friendly and patient.")));

        //test correctly returns equal if remark string is the same
        assertTrue(new Remark("Friendly and patient.").equals(new Remark("Friendly and patient.")));
    }

    @Test
    public void checkRemarkHashCode() {
        Remark remark = new Remark("Friendly and patient.");
        assertTrue(remark.hashCode() == remark.value.hashCode());
        remark = new Remark(" - ");
        assertTrue(remark.hashCode() == remark.value.hashCode());
        remark = new Remark("Late and impatient tutor.");
        assertTrue(remark.hashCode() == remark.value.hashCode());
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStubStudent;
    private PersonPanelSelectionChangedEvent selectionChangedEventStubTutor;
    //private PersonPanelSelectionChangedEvent selectionChangedEventStubPersonOnlyNameSpecified;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    //private Person personOnlyNameSpecified;

    @Before
    public void setUp() {
        /*personOnlyNameSpecified  = new PersonBuilder().withName("Hilda Lim")
                .withAddress(null).withEmail(null).withPhone(null)
                .withPrice(null).withSubject(null).withStatus(null).withLevel(null)
                .withTags(new String[0]).build();*/

        selectionChangedEventStubStudent = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        selectionChangedEventStubTutor = new PersonPanelSelectionChangedEvent(new PersonCard(BENSON, 0));
        //selectionChangedEventStubPersonOnlyNameSpecified =
        //        new PersonPanelSelectionChangedEvent(new PersonCard(personOnlyNameSpecified, 0));


        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() {
        // student
        Person student = ALICE;
        postNow(selectionChangedEventStubStudent);
        assertBrowserDisplay(student);

        // tutor
        Person tutor = BENSON;
        postNow(selectionChangedEventStubTutor);
        assertBrowserDisplay(tutor);

        // person with only name specified
        //postNow(selectionChangedEventStubTutor);
        //assertBrowserDisplay(personOnlyNameSpecified);
    }

    /**
     * Asserts that {@code browserPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertBrowserDisplay(Person expectedPerson) {
        guiRobot.pauseForHuman();

        // verify person details are displayed correctly
        assertBrowserDisplaysPerson(expectedPerson, browserPanelHandle);
    }
}
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_addCommandPressTab_autofill() {
        String expectedOutput = "add n/ p/ e/ a/ $/ sub/ lvl/ stat/ r/";

        // checks for add command word
        commandBoxHandle.setInput("add");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for add command word alias
        commandBoxHandle.setInput("a");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks if tab works correctly
        /*expectedOutput = "add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 $/50"
                + " sub/Math lvl/Lower Sec stat/Not Matched r/Student";
        actualOutput = enterPersonDetails();
        assertEquals(expectedOutput, actualOutput);*/
    }

    @Test
    public void handleKeyPress_addCommandPressDelete_removePreviousPrefix() {
        String expectedOutput = "add p/ e/ a/ $/ sub/ lvl/ stat/ r/";

        // checks for add command word
        commandBoxHandle.setInput("add");
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.DELETE);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_selectCommandPressTab_autofill() {
        String expectedOutput = "select 1";

        // checks for select command word
        commandBoxHandle.setInput("select");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for select command word alias
        commandBoxHandle.setInput("s");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_deleteCommandPressTab_autofill() {
        String expectedOutput = "delete 1";

        // checks for delete command word
        commandBoxHandle.setInput("delete");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for delete command word alias
        commandBoxHandle.setInput("d");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_editCommandPressTab_autofill() {
        String expectedOutput = "edit 1 n/ p/ e/ a/ $/ sub/ lvl/ stat/ r/";

        // checks for edit command word
        commandBoxHandle.setInput("edit");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for edit command word alias
        commandBoxHandle.setInput("e");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Enters Person details using GUI robot
     * @return String entered by GUI robot
     */
    private String enterPersonDetails() {
        commandBoxHandle.setInput("John Doe");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("98765432");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("johnd@example.com");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("311, Clementi Ave 2, #02-25");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("50");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("Math");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("Lower Sec");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("Not Matched");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.setInput("Student");

        return commandBoxHandle.getInput();
    }
}
```
