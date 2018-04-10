# alexawangzi
###### \java\seedu\address\logic\commands\MatchCommand.java
``` java
/**
 * Match a tutor and a student in STUtor.
 */
public class MatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "match";
    public static final String COMMAND_WORD_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Match a student and a tutor for lesson.\n"
            + "Parameters: INDEX_A, INDEX_B (must be non-zero positive integers, one student and one tutor.) "
            + "Example: " + COMMAND_WORD + " 4 7 ";

    public static final String MESSAGE_MATCH_SUCCESS = "Created new match %1$s\n";
    public static final String MESSAGE_MATCH_FAILED = "Matching failed.\n %1$s";
    public static final String MESSAGE_MISMATCH_WRONG_ROLE = "Please provide indices of one student and one tutor.";
    public static final String MESSAGE_MISMATCH_WRONG_SUBJECT = "Not the same subject. ";
    public static final String MESSAGE_MISMATCH_WRONG_LEVEL = "Not the same level. ";
    public static final String MESSAGE_MISMATCH_WRONG_PRICE = "Not the same price. ";
    public static final String MESSAGE_MISMATCH_WRONG_STATUS = "Please provide indices of unmatched student and "
            + "unmatched tutor.";
    public static final String MESSAGE_MISMATCH_ALREADY_MATCHED = "The two persons are already matched.";

    private final Index indexA;
    private final Index indexB;
    private Person student;
    private Person tutor;


    /**
     * @param indexA,of the person in the filtered person list to match
     */
    public MatchCommand(Index indexA, Index indexB) {
        requireAllNonNull(indexA, indexB);
        this.indexA = indexA;
        this.indexB = indexB;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addPair(student, tutor);
        } catch (DuplicatePairException dpe) {
            throw new CommandException(MESSAGE_MISMATCH_ALREADY_MATCHED);
        }
        return new CommandResult(String.format(MESSAGE_MATCH_SUCCESS, student.getName().fullName
                + " and " + tutor.getName().fullName));
    }

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

    @Override
    public boolean equals(Object other) {
        return this == other //short circuit for same object
            || (other instanceof MatchCommand
            && indexA.equals(((MatchCommand) other).indexA) && indexB.equals(((MatchCommand) other).indexB));    }
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        try {
            indexB = ParserUtil.parseIndex(indices[1]);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        return new MatchCommand(indexA, indexB);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String pariHash} into a {@code PairHash}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code  pairHash} is invalid.
     */
    public static PairHash parsePairHash(String pairHash) throws IllegalValueException {
        requireNonNull(pairHash);
        String trimmedPairHash = pairHash.trim();
        if (!PairHash.isValidPairHashValue(trimmedPairHash)) {
            throw new IllegalValueException(PairHash.MESSAGE_PAIRHASH_CONSTRAINTS);
        }
        return new PairHash(trimmedPairHash);
    }


```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code Collection<String> pairHashes} into a {@code Set<PairHash>}.
     */
    public static Set<PairHash> parsePairHashes(Collection<String> pairHashes) throws IllegalValueException {
        requireNonNull(pairHashes);
        final Set<PairHash> pairHashSet = new HashSet<>();
        for (String pairHashValue : pairHashes) {
            pairHashSet.add(parsePairHash(pairHashValue));
        }
        return pairHashSet;
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
        addPairHash(student, pairHash);
        addPairHash(tutor, pairHash);
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
                if (person.getPairHashes().contains(pairHash)) {
                    removePairHash(person, pairHash);
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
     * add parihash to be the person
     * @param person
     * @param pairHash
     */
    private void addPairHash(Person person, PairHash pairHash) {
        Person editedPerson;
        Set<PairHash> pairHashSet = new HashSet<PairHash>();
        pairHashSet.addAll(person.getPairHashes());
        pairHashSet.add(pairHash);

        Set<Tag> attributeTags = new HashSet<Tag>();
        attributeTags.add(new Tag(person.getRole().value, Tag.AllTagTypes.ROLE));
        attributeTags.add(new Tag(person.getPrice().value, Tag.AllTagTypes.PRICE));
        attributeTags.add(new Tag(person.getSubject().value, Tag.AllTagTypes.SUBJECT));
        attributeTags.add(new Tag(person.getLevel().value, Tag.AllTagTypes.LEVEL));

        attributeTags.add(new Tag("Matched", Tag.AllTagTypes.STATUS));
        editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Matched"),
                    person.getRole(), attributeTags, person.getRemark(), person.getRate(), pairHashSet);

        try {
            updatePerson(person, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Should not have duplicates");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("Match exits means person must be in database.");
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * add parihash to be the person
     * @param person
     * @param pairHash
     */
    private void removePairHash(Person person, PairHash pairHash) {
        Person editedPerson;
        Set<PairHash> pairHashSet = new HashSet<PairHash>();
        pairHashSet.addAll(person.getPairHashes());
        pairHashSet.remove(pairHash);

        Set<Tag> attributeTags = new HashSet<Tag>();
        attributeTags.add(new Tag(person.getRole().value, Tag.AllTagTypes.ROLE));
        attributeTags.add(new Tag(person.getPrice().value, Tag.AllTagTypes.PRICE));
        attributeTags.add(new Tag(person.getSubject().value, Tag.AllTagTypes.SUBJECT));
        attributeTags.add(new Tag(person.getLevel().value, Tag.AllTagTypes.LEVEL));

        if (pairHashSet.isEmpty()) {
            attributeTags.add(new Tag("Not Matched", Tag.AllTagTypes.STATUS));
            editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Not Matched"),
                    person.getRole(), attributeTags, person.getRemark(), person.getRate(), pairHashSet);
        } else {
            attributeTags.add(new Tag("Matched", Tag.AllTagTypes.STATUS));
            editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Matched"),
                    person.getRole(), attributeTags, person.getRemark(), person.getRate(), pairHashSet);
        }

        try {
            updatePerson(person, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Should not have duplicates");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("Match exits means person must be in database.");
        }
    }

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
    public synchronized void addPair(Person student, Person tutor) throws DuplicatePairException {
        addressBook.addPair(student, tutor);
        updateFilteredPairList(PREDICATE_SHOW_ALL_PAIRS);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

```
###### \java\seedu\address\model\pair\Pair.java
``` java
/**
 * Represents a pair (one student and one tutor) in STUtor.
 */
public class Pair  {

    private final String studentName;
    private final String tutorName;
    private final String subject;
    private final String level;
    private final String price;
    private final PairHash pairHash;
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
        return studentName + " & " + tutorName;
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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Student: ")
                .append(getStudentName())
                .append(" Tutor: ")
                .append(getTutorName())
                .append(" Subject: ")
                .append(getSubject())
                .append(" Level: ")
                .append(getLevel())
                .append(" Price: ")
                .append(getPrice());
        return builder.toString();
    }


}
```
###### \java\seedu\address\model\pair\PairHash.java
``` java
/**
 * Represents a PairHash in the address book.
 * The PairHash is attached to a pair, its student and its tutor to facilitate match/unmatch operations.
 * Guarantees: immutable;
 */
public class PairHash {

    public static final String PAIRHASH_VALIDATION_REGEX = "-?[0-9]{0,10}";
    public static final String MESSAGE_PAIRHASH_CONSTRAINTS = "PairHash should be a signed integer.";

    public static final PairHash DEFAULT_PAIR_HASH = new PairHash(0);
    public final int value;


    public PairHash (Person student, Person tutor, Subject subject, Level level, Price price) {
        requireAllNonNull(student, tutor, subject, level, price);
        this.value = Objects.hash(student.toString(), tutor.toString(), subject, level, price);
    }

    public PairHash(String input) {
        this.value = Integer.parseInt(input);
    }

    public PairHash(int input) {
        this.value = input;
    }

    public static Set<PairHash> getDefaultPairHashSet() {
        Set<PairHash> defaultPairHashSet = new HashSet<PairHash>();
        return defaultPairHashSet;
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

    public int getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid pairHash
     */
    public static boolean isValidPairHashValue(String test) {
        return test.matches(PAIRHASH_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\exceptions\PersonMatchedCannotDeleteException.java
``` java
/**
 * Signals that the operation is invalid as the person is matched and cannot be deleted
 */
public class PersonMatchedCannotDeleteException extends Exception {
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
     * @param rate
     * @param tags
     * @param pairhashes
     */
    public Student(Name name, Phone phone, Email email, Address address,
                   Price price, Subject subject, Level level, Status status,
                   Set<Tag> tags, Remark remark, Rate rate,  Set<PairHash> pairhashes) {
        super(name, phone, email, address, price, subject, level, status, new Role("student"),
              tags, remark, rate, pairhashes);
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
     * @param rate
     * @param tags
     * @param pairhashes
     */
    public Tutor(Name name, Phone phone, Email email, Address address, Price price,
                 Subject subject, Level level, Status status, Set<Tag> tags, Remark remark,
                 Rate rate, Set<PairHash> pairhashes) {
        super(name, phone, email, address, price, subject, level, status, new Role("student"),
              tags, remark, rate, pairhashes);
    }
}
```
