package seedu.address.logic.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s specified {@code Attribute} as given in {@code keyword} is an empty string.
 */
public class FindMissingPredicate implements Predicate<Person> {
    private final String keyword;

    public FindMissingPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        switch (keyword) {
        case "email":
            return person.getEmail().value.equals("");
        case "phone":
            return person.getPhone().value.equals("");
        case "address":
            return person.getAddress().value.equals("");
        case "price":
            return person.getPrice().value.equals("");
        case "level":
            return person.getLevel().value.equals("");
        case "subject":
            return person.getSubject().value.equals("");
        case "role":
            return person.getRole().value.equals("");
        case "status":
            return person.getStatus().value.equals("");
        default: //fallthrough
            return false;
        }
    }

    public String toString() {
        return this.keyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMissingPredicate // instanceof handles nulls
                && this.keyword.equals(((FindMissingPredicate) other).keyword)); // state check
    }
}
