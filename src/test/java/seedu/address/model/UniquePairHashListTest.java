package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.pair.PairHash;
import seedu.address.model.pair.UniquePairHashList;

//@@author alexawangzi
public class UniquePairHashListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePairHashList uniquePairHashList = new UniquePairHashList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePairHashList.asObservableList().remove(0);
    }

    @Test
    public void checkEquality() throws UniquePairHashList.DuplicatePairHashException {
        UniquePairHashList uniquePairHashListA = new UniquePairHashList();
        UniquePairHashList uniquePairHashListB = new UniquePairHashList();
        uniquePairHashListA.add(new PairHash(1234567));
        assertFalse(uniquePairHashListA.equals(uniquePairHashListB));
        uniquePairHashListB.add(new PairHash(1234567));
        assertTrue(uniquePairHashListA.equals(uniquePairHashListB));

    }

}

