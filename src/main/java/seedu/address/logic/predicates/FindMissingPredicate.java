package seedu.address.logic.predicates;

import java.util.function.Predicate;
import seedu.address.model.person.Person;

public class FindMissingPredicate implements Predicate<Person> {
    private final String attribute;

    public FindMissingPredicate(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean test(Person person) {
        switch (attribute) {
            case "email":
                return person.getEmail().equals("");
            case "phone":
                return person.getPhone().equals("");
            case "address":
                return person.getAddress().equals("");
            case "price":
                return person.getPrice().equals("");
            case "level":
                return person.getLevel().equals("");
            case "subject":
                return person.getSubject().equals("");
            case "role":
                return person.getRole().equals("");
            case "status":
                return person.getStatus().equals("");
        }
        return false; //fallthrough
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMissingPredicate // instanceof handles nulls
                && this.attribute.equals(((FindMissingPredicate) other).attribute)); // state check
    }
}
