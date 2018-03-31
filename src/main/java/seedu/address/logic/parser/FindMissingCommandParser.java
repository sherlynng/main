package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindMissingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FindMissingCommandParser implements Parser<FindMissingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMissingCommand
     * and returns an FindMissingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMissingCommand parse(String args) {
        String trimmedArgs = args.trim();
        String[] fieldKeywords = trimmedArgs.split("\\s+");
        if (fieldKeywords.length == 0) {
            fieldKeywords = Arrays.copyOf(FindMissingCommand.ATTRIBUTE_VALUES,
                    FindMissingCommand.ATTRIBUTE_VALUES.length);
        }
        return new FindMissingCommand(null);
    }

    /**
     * Combines all the predicates in the predicateList into a single Predicate, using logical OR
     * @param predicateList a list of non-empty predicates
     * @return a single Predicate combining all the predicates in the predicateList
     */
    private Predicate<Person> combineAllPredicates(List<Predicate<Person>> predicateList) {
        assert(predicateList.size() >= 1);
        Predicate<Person> allPredicates = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            allPredicates.or(predicateList.get(i));
        }
        return allPredicates;
    }
}
