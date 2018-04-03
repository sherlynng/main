# alexawangzi
###### \java\seedu\address\logic\commands\MatchCommand.java
``` java
    /**
     * @param indexA,of the person in the filtered person list to match
     */
    public MatchCommand(Index indexA, Index indexB) {
        requireAllNonNull(indexA, indexB);
        this.indexA = indexA;
        this.indexB = indexB;
    }


```
###### \java\seedu\address\logic\commands\MatchCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        model.addPair(student, tutor);
        return new CommandResult(String.format(MESSAGE_MATCH_SUCCESS, student.getName().fullName
                + " and " + tutor.getName().fullName));
    }

```
###### \java\seedu\address\logic\commands\MatchCommand.java
``` java
    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (indexA.getZeroBased() >= lastShownList.size() || indexB.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        student = lastShownList.get(indexA.getZeroBased());
        tutor = lastShownList.get(indexB.getZeroBased());

        //filter invalid matchings
        if (student.getRole().equals(tutor.getRole())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_ROLE));
        }
        if (student.isMatched() || tutor.isMatched()) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_STATUS));
        }
        if (!student.getSubject().equals(tutor.getSubject())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_SUBJECT));
        }
        if (!student.getLevel().equals(tutor.getLevel())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_LEVEL));
        }

        if (!student.getPrice().equals(tutor.getPrice())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_PRICE));
        }
        //standardize input order : person A is student, person B is tutor
        if (!student.getRole().value.equals("Student")) {
            Person temp = student;
            student = tutor;
            tutor = temp;
        }

    }


}
```
###### \java\seedu\address\logic\commands\UnmatchCommand.java
``` java
/**
 * Unmatch a pair listed in STUtor
 */
public class UnmatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unmatch";
    public static final String COMMAND_WORD_ALIAS = "um";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmatch an existing pair.\n "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNMATCH_PAIR_SUCCESS = "Unmatched Pair: %1$s";
    public static final String MESSAGE_NOT_UNMATCHED = "At least one field to unmatch must be provided.";

    private final Index targetIndex;
    private Pair pairToUnmatch;

    /**
     * @param targetIndex of the pair in the filtered pair list to unmatch
     */
    public UnmatchCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

```
###### \java\seedu\address\logic\commands\UnmatchCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(pairToUnmatch);
        try {
            model.deletePair(pairToUnmatch);
        } catch (PairNotFoundException pnfe) {
            throw new AssertionError("The target pair cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_UNMATCH_PAIR_SUCCESS, pairToUnmatch));
    }

```
###### \java\seedu\address\logic\commands\UnmatchCommand.java
``` java
    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Pair> lastShownList = model.getFilteredPairList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);
        }

        pairToUnmatch = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmatchCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnmatchCommand) other).targetIndex) // state check
                && Objects.equals(this.pairToUnmatch, ((UnmatchCommand) other).pairToUnmatch));
    }
}
```
###### \java\seedu\address\logic\parser\MatchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns an MatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        String[] indices = trimmedArgs.split("\\s+");
        if (indices.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }
        Index indexA = null; // index of the first person
        Index indexB = null; // index of the second person
        try {
            indexA = ParserUtil.parseIndex(indices[0]);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    new String(MESSAGE_INVALID_COMMAND_FORMAT  + MatchCommand.MESSAGE_USAGE));
        }

        try {
            indexB = ParserUtil.parseIndex(indices[1]);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    new String(MESSAGE_INVALID_COMMAND_FORMAT  + MatchCommand.MESSAGE_USAGE));
        }

        return new MatchCommand(indexA, indexB);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a pair to the address book.
     * @param key
     * @throws seedu.address.model.pair.exceptions.DuplicatePairException if an equivalent pair already exists.
     */
    public void addPair(Pair key) throws DuplicatePairException {
        pairs.add(key);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a pair to the address book
     * @param student
     * @param tutor
     * @throws seedu.address.model.pair.exceptions.DuplicatePairException if an equivalent pair already exists.
     */
    public void addPair(Person student, Person tutor) throws DuplicatePairException {
        //  Pair pair = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any pair
        // in the pair list.
        Pair key = new Pair(student, tutor, student.getSubject(), student.getLevel(), student.getPrice());
        pairs.add(key);
        PairHash pairHash = key.getPairHash();
        flipStatus(student, pairHash);
        flipStatus(tutor, pairHash);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws seedu.address.model.pair.exceptions.PairNotFoundException if the {@code key} is not in this
     * {@code AddressBook}.gr
     */
    public boolean removePair(Pair key) throws PairNotFoundException {
        if (pairs.remove(key)) {
            PairHash pairHash = key.getPairHash();
            for (Person person : persons) {
                if (person.getPairHash().equals(pairHash)) {
                    flipStatus(person, PairHash.DEFAULT_PAIR_HASH);
                }
            }
            return true;
        } else {
            throw new PairNotFoundException();
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * flip the status of a person, update pairhash and tags accordingly
     * if the person is currently matched, update status to be "Not Matched" and pairhash to be 0,
     * otherwise update status to be "Matched" and parihash to be the new pairhash
     * @param person
     * @param pairhash
     */
    private void flipStatus(Person person, PairHash pairhash) {
        Person editedPerson;

        Set<Tag> attributeTags = new HashSet<Tag>();

        attributeTags.add(new Tag(person.getRole().value, Tag.AllTagTypes.ROLE));
        attributeTags.add(new Tag(person.getPrice().value, Tag.AllTagTypes.PRICE));
        attributeTags.add(new Tag(person.getSubject().value, Tag.AllTagTypes.SUBJECT));
        attributeTags.add(new Tag(person.getLevel().value, Tag.AllTagTypes.LEVEL));
        if (person.isMatched()) {
            attributeTags.add(new Tag("Not Matched", Tag.AllTagTypes.STATUS));
        } else {
            attributeTags.add(new Tag("Matched", Tag.AllTagTypes.STATUS));
        }
        if (person.isMatched()) {
            editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Not Matched"),
                    person.getRole(), attributeTags, person.getRemark(), PairHash.getDefaultPairHash());
        } else {
            editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Matched"),
                    person.getRole(), attributeTags, person.getRemark(), pairhash);

        }
        try {
            updatePerson(person, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Should not have duplicates");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("Match exits means person must be in database.");
        }
    }


    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<seedu.address.model.pair.Pair> getPairList() {
        return pairs.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

    /**
     * Removes {@code tag} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */

    public void removeTag(Tag tag) throws PersonNotFoundException {
        for (Person person : persons) {
            removeTagFromPerson(tag, person);
        }
    }

    /**
     *
     * Removes {@code tag} from {@code person} in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code person} is not in this {@code AddressBook}.
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deletePair(Pair target) throws PairNotFoundException {
        addressBook.removePair(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Add a pair to STUtor
     * @param student
     * @param tutor
     * @throws DuplicatePersonException
     */
    public synchronized void addPair(Person student, Person tutor)  {
        try {
            addressBook.addPair(student, tutor);
        } catch (DuplicatePairException e) {
            e.printStackTrace();
        }
        updateFilteredPairList(PREDICATE_SHOW_ALL_PAIRS);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }


    //=========== Filtered Pair List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Pair} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public ObservableList<Pair> getFilteredPairList() {
        return FXCollections.unmodifiableObservableList(filteredPairs);
    }

    @Override
    public void updateFilteredPairList(Predicate<Pair> predicate) {
        requireNonNull(predicate);
        filteredPairs.setPredicate(predicate);
    }





    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    @Override
    public void deleteTag (Tag tag)throws PersonNotFoundException {
        addressBook.removeTag(tag);
        indicateAddressBookChanged();
    }


}
```
###### \java\seedu\address\model\pair\Pair.java
``` java
/**
 * Represents a pair (one student and one tutor) in STUtor.
 */
public class Pair  {

    public final String studentName;
    public final String tutorName;
    public final String subject;
    public final String level;
    public final String price;
    public final PairHash pairHash;
    private final UniqueTagList tags;


    public Pair(String studentName, String tutorName, String subject, String level, String price,
                PairHash pairHash) {
        requireAllNonNull(studentName, tutorName, subject, level, price, pairHash);
        this.studentName = studentName;
        this.tutorName = tutorName;
        this.subject = subject;
        this.level = level;
        this.price = price;
        this.tags = new UniqueTagList();
        try {
            tags.add(new Tag(price, Tag.AllTagTypes.PRICE));
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
        }
        try {
            tags.add(new Tag(subject, Tag.AllTagTypes.SUBJECT));
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
        }
        try {
            tags.add(new Tag(level, Tag.AllTagTypes.LEVEL));
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
        }
        this.pairHash = pairHash;
    }

    public Pair(Person student, Person tutor, Subject subject, Level level, Price price) {
        this(student.getName().fullName, tutor.getName().fullName, subject.value,
                level.value, price.value, new PairHash(student, tutor, subject, level, price));
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getSubject() {
        return subject;
    }

    public String getLevel() {
        return level;
    }

    public String getPrice() {
        return price;
    }

    public String getPairName() {
        return studentName + " /w " + tutorName;
    }

    public PairHash getPairHash() {
        return pairHash;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
    */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * return a hashcode of the Pair object
     * @return
     */
    /*  public Pair hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return pairHash;
    }
    */

    /**
     * check if another object is equal to this pair
     * @param other
     * @return
    */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Pair)) {
            return false;
        }


        Pair otherPair = (Pair) other;
        return otherPair.getStudentName().equals(this.getStudentName())
                && otherPair.getTutorName().equals(this.getTutorName())
                && otherPair.getSubject().equals(this.getSubject())
                & otherPair.getLevel().equals(this.getLevel())
                && otherPair.getPrice().equals(this.getPrice())
                && otherPair.getPairHash().equals(this.getPairHash());
    }


}
```
###### \java\seedu\address\model\pair\PairHash.java
``` java
/**
 * Represents a pairHash in the address book.
 * The pariHash is attached to a pair, its student and its tutor to facilitate match/unmatch operations.
 * Guarantees: immutable;
 */
public class PairHash {

    public static final PairHash DEFAULT_PAIR_HASH = new PairHash(0);
    public final int value;


    public PairHash (Person student, Person tutor, Subject subject, Level level, Price price) {
        requireAllNonNull(student, tutor, subject, level, price);
        this.value = Objects.hash(student, tutor, subject, level, price);
    }

    public PairHash(int input) {
        this.value = input;
    }

    public static PairHash getDefaultPairHash() {
        return DEFAULT_PAIR_HASH;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PairHash // instanceof handles nulls
                && this.value == (((PairHash) other).value)); // state check
    }

}
```
###### \java\seedu\address\model\person\Level.java
``` java
    /**
     * format the input into proper case
     * @param level
     * @return
     */
    private String formatLevel(String level) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(level);
    }

```
###### \java\seedu\address\model\person\Level.java
``` java
    /**
     * check validity of the level string supplied
     * @param level
     * @return string representing a valid level
     */
    private String validateLevel(String level) {
        level.toLowerCase();
        checkArgument(isValidLevel(level), MESSAGE_LEVEL_CONSTRAINTS);
        level = convertToFullLevel(level);
        return level;
    }

```
###### \java\seedu\address\model\person\Level.java
``` java
    /**
     * Convert a shortcut to full level name
     */
    public String convertToFullLevel(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("ls")) {
            cur = "lower sec";
        } else if (cur.equals("us")) {
            cur = "upper sec";
        } else if (cur.equals("lp")) {
            cur = "lower pri";
        } else if (cur.equals("up")) {
            cur = "upper pri";
        }
        return cur;
    }

    /**
     * Returns if a given string is a valid level description.
     */
    public static boolean isValidLevel(String test) {
        test = test.toLowerCase();
        return SET_ALL_LEVEL.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Level // instanceof handles nulls
                && this.value.equals(((Level) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
```
###### \java\seedu\address\model\person\ProperCaseConverter.java
``` java
/**
 * Helper class to change a string to proper class
 */
class ProperCaseConverter {

    /**
     * convert the value string to proper case
     */
    public String convertToProperCase(String original) {
        StringBuilder properCase = new StringBuilder();
        boolean nextProperCase = true;

        original = original.toLowerCase();

        for (char c : original.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextProperCase = true;
            } else if (nextProperCase) {
                c = Character.toUpperCase(c);
                nextProperCase = false;
            }
            properCase.append(c);
        }
        return properCase.toString();
    }
}
```
###### \java\seedu\address\model\person\Student.java
``` java
/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Student extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param price
     * @param subject
     * @param level
     * @param status
     * @param remark
     * @param tags
     * @param pairhash
     */
    public Student(Name name, Phone phone, Email email, Address address,
                   Price price, Subject subject, Level level, Status status,
                   Set<Tag> tags, Remark remark, PairHash pairhash) {
        super(name, phone, email, address, price, subject, level, status, new Role("student"), tags, remark, pairhash);
    }
}
```
###### \java\seedu\address\model\person\Tutor.java
``` java
/**
 * Represents a Tutor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutor extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param price
     * @param subject
     * @param level
     * @param status
     * @param remark
     * @param tags
     * @param pairhash
     */
    public Tutor(Name name, Phone phone, Email email, Address address, Price price,
                 Subject subject, Level level, Status status, Set<Tag> tags, Remark remark, PairHash pairhash) {
        super(name, phone, email, address, price, subject, level, status, new Role("student"), tags, remark, pairhash);
    }
}
```
