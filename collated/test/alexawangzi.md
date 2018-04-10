# alexawangzi
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_invalidIndexForPairAUnfilteredList_throwsCommandException() throws Exception {
        Index indexA = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index indexB = Index.fromOneBased(model.getFilteredPersonList().size());
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_invalidIndexforPairBUnfilteredList_throwsCommandException() {
        Index indexA = Index.fromOneBased(model.getFilteredPersonList().size());
        Index indexB = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_incompatibleSameRole_throwsCommandException() {
        Index indexA = Index.fromOneBased(7);
        Index indexB = Index.fromOneBased(8);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_ROLE));
    }

```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_incompatibleAlreadyMatched_throwsCommandException() {
        Index indexA = Index.fromOneBased(1);
        Index indexB = Index.fromOneBased(2);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, MESSAGE_MISMATCH_ALREADY_MATCHED);
    }

```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_incompatibleDifferentSubject_throwsCommandException() {
        Index indexA = Index.fromOneBased(8);
        Index indexB = Index.fromOneBased(9);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_SUBJECT));
    }

```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_incompatibleDifferentLevel_throwsCommandException() {
        Index indexA = Index.fromOneBased(9);
        Index indexB = Index.fromOneBased(10);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_LEVEL));
    }

```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_incompatibleDifferentPrice_throwsCommandException() {
        Index indexA = Index.fromOneBased(10);
        Index indexB = Index.fromOneBased(11);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_PRICE));
    }

```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
    @Test
    public void execute_pairAcceptedByModel_matchSuccessful() throws Exception {
        Index indexA = Index.fromOneBased(12);
        Index indexB = Index.fromOneBased(13);

        CommandResult commandResult = getMatchCommand(indexA, indexB, model).execute();

        assertEquals(String.format(MatchCommand.MESSAGE_MATCH_SUCCESS,
                LISA.getName().fullName + " and " + MARY.getName().fullName),
                commandResult.feedbackToUser);
    }

    /**
    * Returns a {@code MatchCommand} with the parameter {@code indexA}, {@code indexB}.
    */
    private MatchCommand prepareCommand(Index indexA, Index indexB) {
        MatchCommand matchCommand = new MatchCommand(indexA, indexB);
        matchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return matchCommand;
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private MatchCommand getMatchCommand(Index indexA, Index indexB, Model model) {
        MatchCommand command = new MatchCommand(indexA, indexB);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\model\UniquePairHashListTest.java
``` java
public class UniquePairHashListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePairHashList uniquePairHashList = new UniquePairHashList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePairHashList.asObservableList().remove(0);
    }

    @Test
    public void checkEquality() throws UniquePairHashList.DuplicatePairHashException {
        UniquePairHashList uniquePairHashListA = new UniquePairHashList();
        UniquePairHashList uniquePairHashListB = new UniquePairHashList();
        uniquePairHashListA.add(new PairHash(1234567));
        assertFalse(uniquePairHashListA.equals(uniquePairHashListB));
        uniquePairHashListB.add(new PairHash(1234567));
        assertTrue(uniquePairHashListA.equals(uniquePairHashListB));

    }

}

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void checkContainPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(RANDOM_PAIR_A);
        assertTrue(uniquePairList.contains(RANDOM_PAIR_A));
        assertFalse(uniquePairList.contains(RANDOM_PAIR_B));
    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void addPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(RANDOM_PAIR_A);
        assertTrue(uniquePairList.contains(RANDOM_PAIR_A));
    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void setPair() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        UniquePairList listB = new UniquePairList();
        listB.add(RANDOM_PAIR_B);
        listA.setPair(RANDOM_PAIR_A, RANDOM_PAIR_B);
        assertEquals(listA, listB);

    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void setPairs() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        UniquePairList listB = new UniquePairList();
        listB.add(RANDOM_PAIR_B);
        listA.setPairs(listB);
        assertEquals(listA, listB);
    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void removePair() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        UniquePairList listB = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        listA.add(RANDOM_PAIR_B);
        listB.add(RANDOM_PAIR_B);
        listA.remove(RANDOM_PAIR_A);
        assertEquals(listA, listB);
    }

}
```
###### \java\seedu\address\testutil\PairBuilder.java
``` java
/**
 * A utility class to help with building Pair objects.
 */
public class PairBuilder {

    public static final Person DEFAULT_STUDENT = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withPrice("50").withSubject("math").withStatus("not Matched").withLevel("upper Sec")
            .withRole("Student").build();
    public static final Person DEFAULT_TUTOR = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withPrice("50").withSubject("math").withStatus("not Matched").withLevel("upper Sec")
            .withRole("Tutor").withRemark("Impatient and poor in explanation.").build();
    public static final Subject DEFAULT_SUBJECT = new Subject("Math");
    public static final Level DEFAULT_LEVEL = new Level("Upper Sec");
    public static final Price DEFAULT_PRICE = new Price("50");
    public static final String DEFAULT_TAG_SUBJECT = "Math";
    public static final String DEFAULT_TAG_LEVEL = "Upper Sec";
    public static final String DEFAULT_TAG_PRICE = "50";

    private Person student;
    private Person tutor;
    private String subject;
    private String level;
    private String price;
    private Set<Tag> tags;

    public PairBuilder() {
        student = DEFAULT_STUDENT;
        tutor =  DEFAULT_TUTOR;
        subject = DEFAULT_SUBJECT.toString();
        level = DEFAULT_LEVEL.toString();
        price = DEFAULT_PRICE.toString();
        tags = SampleDataUtil.getTagSet(DEFAULT_TAG_SUBJECT, DEFAULT_TAG_LEVEL, DEFAULT_TAG_PRICE);
    }


    /**
     * Sets the {@code studentName} of the {@code Pair} that we are building.
     */
    public PairBuilder withStudent(Person student) {
        this.student = student;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Pair} that we are building.
     */
    public PairBuilder withTutor(Person tutor) {
        this.tutor = tutor;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Pair} that we are building.
     */
    public PairBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withLevel(String level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withPrice(String price) {
        this.price = price;
        return this;
    }


    /**
     * Sets the required attribute tags for the pair
     */
    private void setTags() {

        tags.add(new Tag(subject.toString(), Tag.AllTagTypes.SUBJECT));
        tags.add(new Tag(level.toString(), Tag.AllTagTypes.LEVEL));
        tags.add(new Tag(price.toString(), Tag.AllTagTypes.PRICE));
    }

    /**
     * Builds a pair based off the attributes in this class
     * @return Pair with set attributes
     */
    public Pair build() {
        setTags();
        return new Pair(student, tutor, student.getSubject(), student.getLevel(), student.getPrice());
    }

}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code pairhash} of the {@code Person} that we are building.
     */
    public PersonBuilder withPairhash(PairHash pairHash) {
        Set<PairHash> pairHashesSet = new HashSet<PairHash>();
        pairHashesSet.add(pairHash);
        this.pairHashes = pairHashesSet;
        return this;
    }

    /**
     * Builds a person based off the attributes in this class
     * @return Person with set attributes
     */
    public Person build() {
        setTags();
        return new Person(name, phone, email, address, price, subject, level, status, role, tags,
                          remark, rate, pairHashes);
    }

}
```
###### \java\seedu\address\testutil\TypicalPairs.java
``` java
/**
 * A utility class containing a list of {@code Pair} objects to be used in tests.
 */
public class TypicalPairs {

    public static final Pair RANDOM_PAIR_A = new PairBuilder().withStudent(BENSON).withTutor(ALICE)
            .build();

    public static final Pair RANDOM_PAIR_B = new PairBuilder().withStudent(DANIEL).withTutor(CARL)
            .build();

    public static final Pair RANDOM_PAIR_C = new PairBuilder().withStudent(FIONA).withTutor(ELLE)
            .build();



    private TypicalPairs() {} // prevents instantiation

    public static List<Pair> getTypicalPairs() {
        return new ArrayList<>(Arrays.asList(RANDOM_PAIR_A, RANDOM_PAIR_B, RANDOM_PAIR_C));
    }

}
```
