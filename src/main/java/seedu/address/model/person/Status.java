package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Person's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String[] STATUS_VALUES = new String[] { "Matched", "NotMatched"};
    public static final HashSet<String> SET_ALL_STATUS = new HashSet<>(Arrays.asList(STATUS_VALUES));

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Subject should be one of: \n"
            + SET_ALL_STATUS.toString()
            + "\n";

    public final String value;

    /**
     * Constructs an {@code Status}.
     *
     * @param status A valid statust description.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_STATUS_CONSTRAINTS);
        this.value = status;
    }

    /**
     * Returns if a given string is a valid status description.
     */
    public static boolean isValidStatus(String test) {

        return SET_ALL_STATUS.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
