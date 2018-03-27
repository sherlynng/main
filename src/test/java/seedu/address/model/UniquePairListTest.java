package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.pair.UniquePairList;

public class UniquePairListTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePairList uniquePairList = new UniquePairList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePairList.asObservableList().remove(0);
    }
}
