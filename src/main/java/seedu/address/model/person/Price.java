package seedu.address.model.person;

/**
 * Represents a Person's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {
    public static final String MESSAGE_PRICE_CONSTRAINTS = "Price should be a positive integer\n";

    public final String value;


    public Price(String value) {
        this.value = value;
    }

    /**
     * Returns if a given string is a valid status description.
     */
    public static boolean isValidPrice(String test) {
        return (Integer.parseInt(test) > 0);
    }

    @Override
    public String toString() {
        return this.value;
    }

}
