package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindMissingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.predicates.FindMissingPredicate;
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
        //If user enters no parameters, the command is equivalent to entering ALL parameters.
        if (fieldKeywords.length == 0) {
            fieldKeywords = Arrays.copyOf(FindMissingCommand.ATTRIBUTE_VALUES,
                    FindMissingCommand.ATTRIBUTE_VALUES.length);
        }
        ArrayList<Predicate<Person>> predicateList = new ArrayList<>();
        for (int i = 0; i < fieldKeywords.length; i++) {
            //ensure case insensitive
            predicateList.add(new FindMissingPredicate(fieldKeywords[i].toLowerCase()));
        }
        Predicate<Person> finalPredicate = combineAllPredicates(predicateList);
        return new FindMissingCommand(finalPredicate);
    }

    /**
     * Combines all the predicates in the predicateList into a single Predicate, using logical OR
     * @param predicateList a list of non-empty predicates
     * @return a single Predicate logically equivalent to logical OR of all predicates in the predicateList
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
