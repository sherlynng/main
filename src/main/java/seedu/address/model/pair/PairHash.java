package seedu.address.model.pair;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Level;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;

//@@author alexawangzi
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

    @Override
    public int hashCode() {
        return this.value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Integer.toString(value).hashCode();
    }


    /**
     * Returns true if a given string is a valid pairHash
     */
    public static boolean isValidPairHashValue(String test) {
        return test.matches(PAIRHASH_VALIDATION_REGEX);
    }
}
