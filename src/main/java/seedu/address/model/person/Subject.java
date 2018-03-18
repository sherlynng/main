package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Person's subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String[] SUBJECT_VALUES = new String[] { "english", "math",
        "physics", "chemistry", "economics" };
    public static final HashSet<String> SET_ALL_SUBJECT = new HashSet<>(Arrays.asList(SUBJECT_VALUES));

    public static final String MESSAGE_SUBJECT_CONSTRAINTS = "Subject should be one of: \n"
            + SET_ALL_SUBJECT.toString()
            + "\n";

    public final String value;

    /**
     * Constructs an {@code Subject}.
     *
     * @param subject A valid subject description.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        checkArgument(isValidSubject(subject), MESSAGE_SUBJECT_CONSTRAINTS);
        this.value = subject;
    }

    /**
     * Returns if a given string is a valid subject description.
     */
    public static boolean isValidSubject(String test) {

        return SET_ALL_SUBJECT.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.value.equals(((Subject) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
