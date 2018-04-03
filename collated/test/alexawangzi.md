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
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_STATUS));
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
###### \java\seedu\address\testutil\TypicalPairs.java
``` java
/**
 * A utility class containing a list of {@code Pair} objects to be used in tests.
 */
public class TypicalPairs {

    public static final Pair RANDOM_PAIR_A = new PairBuilder().withStudent(HENRY).withTutor(GEORGE)
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
