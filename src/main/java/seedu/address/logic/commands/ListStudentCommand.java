package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListStudentCommand extends Command {

    public static final String COMMAND_WORD = "listStudent";
    public static final String COMMAND_WORD_ALIAS = "ls";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
