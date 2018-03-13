package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListTutorCommand extends Command {

    public static final String COMMAND_WORD = "listTutor";
    public static final String COMMAND_WORD_ALIAS = "lt";

    public static final String MESSAGE_SUCCESS = "Listed all tutors";


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_TUTORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
