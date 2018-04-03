package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author dannyngmx94
/**
 * An event requesting to view charts in person panel.
 */
public class ShowChartsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
