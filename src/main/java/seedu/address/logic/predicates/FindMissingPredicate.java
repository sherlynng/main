package seedu.address.logic.predicates;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s specified {@code Attribute} as given in {@code keyword} is an empty string.
 */
public class FindMissingPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a new FindMissingPredicate based off given list.
     * @param keywords A non-empty ArrayList of keywords containing attributes.
     */
    public FindMissingPredicate(List<String> keywords) {
        assert(keywords.size() >= 1);
        //sort to ensure equality check passes
        Collections.sort(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for (String keyword : keywords) {
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
        return false;
    }

    @Override
    public String toString() {
        return this.keywords.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMissingPredicate // instanceof handles nulls
                && this.keywords.equals(((FindMissingPredicate) other).keywords)); // state check
    }
}
