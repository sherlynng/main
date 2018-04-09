# aussiroth
###### \java\seedu\address\logic\commands\FindMissingCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindMissingCommand}.
 */
public class FindMissingCommandTest {
    private Model missingAttributesModel = new ModelManager(getMissingAttributesAddressBook(), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equalsTest() {
        FindMissingPredicate firstPredicate =
                new FindMissingPredicate(Collections.singletonList("first"));
        FindMissingPredicate secondPredicate =
                new FindMissingPredicate(Collections.singletonList("second"));

        FindMissingCommand firstCommand = new FindMissingCommand(firstPredicate);
        FindMissingCommand secondCommand = new FindMissingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        // same values -> returns true
        FindMissingCommand firstCommandCopy = new FindMissingCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));
        // different types -> returns false
        assertFalse(firstCommand.equals("A String"));
        // null -> returns false
        assertFalse(firstCommand.equals(null));
        // different input list -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_noMissingFields_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        //special prepare command/assert command success using the typical persons model
        FindMissingCommand command = new FindMissingCommand(
                new FindMissingPredicate(Arrays.asList("phone")));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    @Test
    public void execute_oneField_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindMissingCommand command = prepareCommand("phone");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(JAMES));
        command = prepareCommand("email");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(JAMES));
        command = prepareCommand("address");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(KEN));
        command = prepareCommand("subject");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(KEN));
        command = prepareCommand("level");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(LENNY));
        command = prepareCommand("price");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(LENNY));
        command = prepareCommand("status");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(MISTER));
        command = prepareCommand("role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(MISTER));
    }

    @Test
    public void execute_multipleField_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindMissingCommand command = prepareCommand("email address");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(JAMES, KEN));
    }

    /**
     * Parses {@code userInput} into a {@code FindMissingCommand}.
     */
    private FindMissingCommand prepareCommand(String userInput) {
        FindMissingCommand command = new FindMissingCommand(
                new FindMissingPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(missingAttributesModel, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindMissingCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(missingAttributesModel.getAddressBook());
        CommandResult result = command.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedList, missingAttributesModel.getFilteredPersonList());
        assertEquals(expectedAddressBook, missingAttributesModel.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        //Only name is compulsory now
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ENGLISH + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        //All non-name fields are optional
        //phone
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone("")
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //email
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail("").withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress("").withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //subject
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject("")
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //level
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel("").withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + STATUS_DESC_UNMATCHED
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //Status
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus("").withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //price
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice("")
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //role
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole("").withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED
                + PRICE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //all missing but name
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone("")
                .withEmail("").withAddress("").withSubject("")
                .withLevel("").withStatus("").withPrice("")
                .withRole("").build();
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB, new AddCommand(expectedPerson));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_CHINESE + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + INVALID_PRICE_DESC
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_SUBJECT_DESC + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        // invalid level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + INVALID_LEVEL_DESC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Level.MESSAGE_LEVEL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_CHINESE + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_CHINESE + LEVEL_DESC_UPPER_SEC + INVALID_STATUS_DESC + PRICE_DESC_BOB
                + ROLE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Status.MESSAGE_STATUS_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + SUBJECT_DESC_CHINESE + INVALID_LEVEL_DESC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB + ROLE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_CHINESE + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED
                        + ROLE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE));
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findMissing() throws Exception {
        List<String> keywords = Arrays.asList("address");
        FindMissingPredicate targetP = new FindMissingPredicate(keywords);
        FindMissingCommand command = (FindMissingCommand) parser.parseCommand(
                FindMissingCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindMissingCommand(targetP), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addAliased() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommandAliased(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clearAliased() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteAliased() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editAliased() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_findMissingAliased() throws Exception {
        List<String> keywords = Arrays.asList("address");
        Predicate<Person> targetP = new FindMissingPredicate(keywords);
        FindMissingCommand command = (FindMissingCommand) parser.parseCommand(
                FindMissingCommand.COMMAND_WORD_ALIAS + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindMissingCommand(targetP), command);
    }

    @Test
    public void parseCommand_historyAliased() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_listAliased() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_selectAliased() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWordAliased_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("r 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWordAliased_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("u 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_viewStats() throws Exception {
        assertTrue(parser.parseCommand(ViewStatsCommand.COMMAND_WORD) instanceof ViewStatsCommand);
    }

    @Test
    public void parseCommand_match() throws Exception {
        String matchCommandString = MatchCommand.COMMAND_WORD + " 1 2";
        MatchCommand targetCommand = new MatchCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        MatchCommand parsedCommand = (MatchCommand) parser.parseCommand(matchCommandString);
        assertEquals(targetCommand, parsedCommand);
    }

    @Test
    public void parseCommand_unmatch() throws Exception {
        UnmatchCommand targetCommand = new UnmatchCommand(Index.fromOneBased(1));
        UnmatchCommand parsedCommand = (UnmatchCommand) parser.parseCommand(UnmatchCommand.COMMAND_WORD + " 1");
        assertEquals(targetCommand, parsedCommand);
    }

```
###### \java\seedu\address\logic\parser\FindMissingCommandParserTest.java
``` java
public class FindMissingCommandParserTest {

    private FindMissingCommandParser parser = new FindMissingCommandParser();

    @Test
    public void parse_validArgs_returnsFindMissingCommand() {
        //setup predicate correctly
        String[] keywords = {"email", "address"};
        Predicate<Person> finalPredicate = new FindMissingPredicate(Arrays.asList(keywords));
        FindMissingCommand expectedFindMissingCommand = new FindMissingCommand(finalPredicate);

        //single whitespace
        assertParseSuccess(parser, "email address", expectedFindMissingCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "email \t  address \t", expectedFindMissingCommand);
        //check case insensitive
        assertParseSuccess(parser, "eMAIl aDdReSS", expectedFindMissingCommand);

        //check parse if empty user input
        keywords = Arrays.copyOf(FindMissingCommand.ATTRIBUTE_VALUES, FindMissingCommand.ATTRIBUTE_VALUES.length);
        finalPredicate = new FindMissingPredicate(Arrays.asList(keywords));
        expectedFindMissingCommand = new FindMissingCommand(finalPredicate);
        assertParseSuccess(parser, "", expectedFindMissingCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedParseFailureMessage = FindMissingCommand.MESSAGE_INVALID_ATTRIBUTE;
        assertParseFailure(parser, "abcdefg", String.format(expectedParseFailureMessage, "abcdefg"));
        assertParseFailure(parser, "addres phon", String.format(expectedParseFailureMessage, "addres"));
    }
}
```
###### \java\seedu\address\model\person\EmailTest.java
``` java
    @Test
    public void checkEmailEquality() {
        //test email against non-email type
        assertFalse(new Email("test@abc.com").equals(null));
        assertFalse(new Email("test@abc.com").equals(new Address("test@abc.com")));
        //test correctly returns equal if email string is the same
        assertTrue(new Email("test@abc.com").equals(new Email("test@abc.com")));
    }

    @Test
    public void checkEmailHashCode() {
        Email email = new Email("PeterJack_1190@example.com");
        assertTrue(email.hashCode() == email.value.hashCode());
        email = new Email("test@localhost");
        assertTrue(email.hashCode() == email.value.hashCode());
        email = new Email("peter_jack@very-very-very-long-example.com");
        assertTrue(email.hashCode() == email.value.hashCode());
    }
}
```
###### \java\seedu\address\model\person\LevelTest.java
``` java
public class LevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Level(null));
    }

    @Test
    public void isValidLevel() {
        // null level
        Assert.assertThrows(NullPointerException.class, () -> Level.isValidLevel(null));

        // invalid levels
        assertFalse(Level.isValidLevel("JC")); // not accepted levels
        assertFalse(Level.isValidLevel("middle Sec"));

        // valid levels
        assertTrue(Level.isValidLevel("upper Sec"));
        assertTrue(Level.isValidLevel("lower Pri"));
        assertTrue(Level.isValidLevel("Upper pri")); //check case insensitive
        assertTrue(Level.isValidLevel("Lower sec"));
    }

    @Test
    public void checkLevelEquality() {
        //test level against non-level type
        assertFalse(new Level("upper Sec").equals(null));
        assertFalse(new Level("upper Sec").equals(new Tag("upper Sec")));
        //test correctly returns equal if level string is the same
        assertTrue(new Level("upper Sec").equals(new Level("upper Sec")));
    }

    @Test
    public void checkLevelHashCode() {
        Level level = new Level("upper sec");
        assertTrue(level.hashCode() == level.value.hashCode());
        level = new Level("lower sec");
        assertTrue(level.hashCode() == level.value.hashCode());
        level = new Level("lower pri");
        assertTrue(level.hashCode() == level.value.hashCode());
    }
}
```
###### \java\seedu\address\model\person\NameTest.java
``` java
    @Test
    public void checkNameEquality() {
        //test name against non-name type
        assertFalse(new Name("Stutor").equals(null));
        assertFalse(new Name("Stutor").equals(new Address("Stutor")));
        //test correctly returns equal if name string is the same
        assertTrue(new Name("Stutor").equals(new Name("Stutor")));
    }

    @Test
    public void checkNameHashCode() {
        Name name = new Name("peter jack");
        assertTrue(name.hashCode() == name.fullName.hashCode());
        name = new Name("12345");
        assertTrue(name.hashCode() == name.fullName.hashCode());
        name = new Name("Capital Tan");
        assertTrue(name.hashCode() == name.fullName.hashCode());
    }
}
```
###### \java\seedu\address\model\person\PriceTest.java
``` java
public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void isValidPrice() {

        // invalid prices
        assertFalse(Price.isValidPrice("-5")); // negative numbers
        assertFalse(Price.isValidPrice("-100"));

        // valid prices
        assertTrue(Price.isValidPrice("25"));
        assertTrue(Price.isValidPrice("5")); // single digit
        assertTrue(Price.isValidPrice("123456")); // large number
    }

```
###### \java\seedu\address\model\person\PriceTest.java
``` java
    @Test
    public void checkPriceEquality() {
        //test price against non-price type
        assertFalse(new Price("100").equals(null));
        assertFalse(new Price("100").equals(new Tag("100")));
        //test correctly returns equal if price string is the same
        assertTrue(new Price("100").equals(new Price("100")));
    }

    @Test
    public void checkPriceHashCode() {
        Price price = new Price("25");
        assertTrue(price.hashCode() == price.value.hashCode());
        price = new Price("5");
        assertTrue(price.hashCode() == price.value.hashCode());
        price = new Price("123456");
        assertTrue(price.hashCode() == price.value.hashCode());
    }
}
```
###### \java\seedu\address\model\person\StatusTest.java
``` java
public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void isValidStatus() {

        // invalid statuses
        assertFalse(Status.isValidStatus("notastatus")); // not listed statuses
        assertFalse(Status.isValidStatus("somewhatmatched"));

        // valid status
        assertTrue(Status.isValidStatus("not Matched"));
        assertTrue(Status.isValidStatus("matched"));
    }

    @Test
    public void checkStatusEquality() {
        //test status against non-status type
        assertFalse(new Status("matched").equals(null));
        assertFalse(new Status("matched").equals(new Tag("matched")));
        //test correctly returns equal if status string is the same
        assertTrue(new Status("matched").equals(new Status("matched")));
    }

    @Test
    public void checkStatusHashCode() {
        Status status = new Status("not Matched");
        assertTrue(status.hashCode() == status.value.hashCode());
        status = new Status("matched");
        assertTrue(status.hashCode() == status.value.hashCode());
    }

}
```
###### \java\seedu\address\model\person\SubjectTest.java
``` java
public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void isValidPrice() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject
        assertFalse(Subject.isValidSubject("computer science")); //subjects not in list
        assertFalse(Subject.isValidSubject("malay"));

        // valid subjects
        assertTrue(Subject.isValidSubject("math"));
        assertTrue(Subject.isValidSubject("English")); // check that case doesn't matter
        assertTrue(Subject.isValidSubject("chemistrY"));
    }

    @Test
    public void checkSubjectEquality() {
        //test name against non-name type
        assertFalse(new Subject("math").equals(null));
        assertFalse(new Subject("math").equals(new Address("math")));
        //test correctly returns equal if name string is the same
        assertTrue(new Subject("math").equals(new Subject("math")));
    }

    @Test
    public void checkSubjectHashCode() {
        Subject subject = new Subject("math");
        assertTrue(subject.hashCode() == subject.value.hashCode());
        subject = new Subject("english");
        assertTrue(subject.hashCode() == subject.value.hashCode());
        subject = new Subject("chemistry");
        assertTrue(subject.hashCode() == subject.value.hashCode());
    }
}
```
###### \java\seedu\address\model\tag\TagTest.java
``` java
    @Test
    public void isValidTagType() {
        //incorrect string
        assertFalse(Tag.isValidTagType("NOTATYPE"));
        //correct type
        assertTrue(Tag.isValidTagType("SUBJECT"));
        assertTrue(Tag.isValidTagType("STATUS"));
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedTagTest.java
``` java
public class XmlAdaptedTagTest {

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        Tag expectedTag = new Tag("math", Tag.AllTagTypes.SUBJECT);
        XmlAdaptedTag xmlExpectedTag = new XmlAdaptedTag(expectedTag);
        assertEquals(expectedTag, xmlExpectedTag.toModelType());
    }

    @Test
    public void toModelType_noTagType_returnsWithDefaultType() throws Exception {
        Tag expectedTag = new Tag("math");
        XmlAdaptedTag xmlTag = new XmlAdaptedTag("math");
        assertEquals(expectedTag, xmlTag.toModelType());
    }

    @Test
    public void toModelType_unknownTagType_returnsWithDefaultType() throws Exception {
        Tag expectedTag = new Tag("math");
        XmlAdaptedTag xmlTag = new XmlAdaptedTag("math", "nonexistenttype");
        assertEquals(expectedTag, xmlTag.toModelType());
    }

    @Test
    public void testXmlAdaptedTagEquality() {
        XmlAdaptedTag tagMath = new XmlAdaptedTag("math", "SUBJECT");
        XmlAdaptedTag copy = new XmlAdaptedTag("math", "SUBJECT");
        assertTrue(tagMath.equals(tagMath));
        //check equality if values are equal
        assertTrue(tagMath.equals(copy));
        //check not equal if type is different
        assertFalse(tagMath.equals(new Tag("math", Tag.AllTagTypes.SUBJECT)));
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBookTest.java
``` java
    @Test
    public void testSerializableAddressBookEquality() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableAddressBook.class);
        XmlSerializableAddressBook copy = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        assertTrue(dataFromFile.equals(dataFromFile));
        assertTrue(dataFromFile.equals(copy)); //assert equality is true if values are equal
        assertFalse(dataFromFile.equals(addressBookFromFile)); //assert equality is false if class is different
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid price -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_PRICE_DESC + SUBJECT_DESC_AMY + STATUS_DESC_UNMATCHED + LEVEL_DESC_AMY + ROLE_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid subject -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + PRICE_DESC_AMY + INVALID_SUBJECT_DESC + STATUS_DESC_UNMATCHED + LEVEL_DESC_AMY + ROLE_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        /* Case: invalid level -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + PRICE_DESC_AMY + SUBJECT_DESC_AMY + STATUS_DESC_UNMATCHED + INVALID_LEVEL_DESC + ROLE_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, Level.MESSAGE_LEVEL_CONSTRAINTS);

        /* Case: invalid role -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + PRICE_DESC_AMY + SUBJECT_DESC_AMY + STATUS_DESC_UNMATCHED + LEVEL_DESC_AMY + INVALID_ROLE_DESC
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, Role.MESSAGE_ROLE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + PRICE_DESC_AMY + SUBJECT_DESC_AMY + STATUS_DESC_UNMATCHED + LEVEL_DESC_AMY + ROLE_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid price -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_PRICE_DESC,
                Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid subject -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_SUBJECT_DESC,
                Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        /* Case: invalid level -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_LEVEL_DESC,
                Level.MESSAGE_LEVEL_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a person with new values same as another person's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(BOB));
        assertTrue(getModel().getAddressBook().getPersonList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPersonList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PRICE_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED + SUBJECT_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PRICE_DESC_BOB + LEVEL_DESC_BOB + STATUS_DESC_UNMATCHED + SUBJECT_DESC_BOB
                + ROLE_DESC_BOB;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
```
