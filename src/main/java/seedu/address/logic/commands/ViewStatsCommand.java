package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

/**
 * Show statistical data from the address book.
 */
public class ViewStatsCommand extends Command {

    public static final String COMMAND_WORD = "viewStats";

    public static final String MESSAGE_VIEW_STATS_SUCCESS = "Opened statistic window";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(MESSAGE_VIEW_STATS_SUCCESS);

    }
}
