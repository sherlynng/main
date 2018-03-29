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

    /* Regex notation
    ^                   # Start of string
    (?:                 # Either match...
    5(?:\.0)?           # 5.0 (or 5)
    |                   # or
    [0-4](?:\.[0-9])?   # 0.0-4.9 (or 1-4)
    |                   # or
    0?\.[1-9]           # 0.1-0.9 (or .1-.9)
    )                   # End of alternation
    $                   # End of string
     */
    public static final String RATE_VALIDATION_REGEX = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])$";
    public static final String RATE_VALIDATION_REGEX_ABSOLUTE = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])" + "-";
    public static final String MESSAGE_RATE_CONSTRAINTS =
            "Rate must be a number between 0 and 5 (inclusive) with at most 1 decimal place";

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rate(double rating, boolean isAbsolute) {
        requireNonNull(rating);
        checkArgument(isValidRate(Double.toString(rating)), MESSAGE_RATE_CONSTRAINTS);

        if (isAbsolute) {
            this.value = Math.floor(rating * 10) / 10;
        }
        else {
            this.value = Math.floor(value * 10) / 10;
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
        DecimalFormat df = new DecimalFormat("0.0");
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
