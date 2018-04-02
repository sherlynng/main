package seedu.address.model.pair.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Pair objects.
 */
public class DuplicatePairException extends DuplicateDataException {
    public DuplicatePairException() {
        super("Operation would result in duplicate pairs");
    }
}
