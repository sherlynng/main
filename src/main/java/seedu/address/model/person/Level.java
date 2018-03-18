package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Person's level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLevel(String)}
 */
public class Level {

    public static final String[] LEVEL_VALUES =
            new String[] { "lowerSec", "upperSec", "lowerPri", "upperPri" };
    public static final HashSet<String> SET_ALL_LEVEL = new HashSet<>(Arrays.asList(LEVEL_VALUES));

    public static final String MESSAGE_LEVEL_CONSTRAINTS = "Person Level should be "
            + "of the format <grade><education> "
            + "and adhere to the following constraints:\n"
            + "1. The education should be one of the education system listed in.\n"
            + "2. This is followed by a whitespace and then a number to represent the grade. "
            + "The grade must be consistent with the specific education system indicated earlier.\n";

    public final String value;

    /**
     * Constructs an {@code Level}.
     *
     * @param level A valid level description.
     */
    public Level(String level) {
        requireNonNull(level);
        checkArgument(isValidLevel(level), MESSAGE_LEVEL_CONSTRAINTS);
        this.value = level;
    }

    /**
     * Returns if a given string is a valid level description.
     */
    public static boolean isValidLevel(String test) {

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
