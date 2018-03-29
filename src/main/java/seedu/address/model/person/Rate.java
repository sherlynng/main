package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents a Person's rating in the address book.
 * Guarantees: immutable;
 */
public class Rate {

    public double value = 3;
    private int count = 0;

    public static final String RATE_VALIDATION_REGEX = "[0-5]";
    public static final String RATE_VALIDATION_REGEX_ABSOLUTE = "[0-5]" + "-";
    public static final String MESSAGE_RATE_CONSTRAINTS =
            "Rate must be an integer between 0 and 5 (inclusive)";

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rate(int rating, boolean isAbsolute) {
        requireNonNull(rating);
        checkArgument(isValidRate(Integer.toString(rating)), MESSAGE_RATE_CONSTRAINTS);

        if (isAbsolute) {
            this.value = rating;
        }
        else {
            double accumulatedValue = value * count;
            this.value = (accumulatedValue + rating) / (count + 1);
        }
        count++;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidRate(String test) {
        return test.equals("") || test.matches(RATE_VALIDATION_REGEX) || test.matches(RATE_VALIDATION_REGEX_ABSOLUTE);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rate // instanceof handles nulls
                && this.value == ((Rate) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
