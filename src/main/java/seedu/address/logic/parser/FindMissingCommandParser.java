package seedu.address.logic.parser;

import java.util.Arrays;
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
        if (fieldKeywords[0].equals("")) {
            fieldKeywords = Arrays.copyOf(FindMissingCommand.ATTRIBUTE_VALUES,
                    FindMissingCommand.ATTRIBUTE_VALUES.length);
        }
        Predicate<Person> finalPredicate = new FindMissingPredicate(Arrays.asList(fieldKeywords));
        return new FindMissingCommand(finalPredicate);
    }
}
