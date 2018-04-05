package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

/**
 * Represents edit remark command is called.
 */
public class EditRemarkEvent extends BaseEvent {


    private final String personRemark;

    public EditRemarkEvent(String personRemark) {
        this.personRemark = personRemark;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getPersonRemark() {
        return personRemark;
    }
}
