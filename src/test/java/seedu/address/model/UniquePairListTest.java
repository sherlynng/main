package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPairs.ALICE_AND_BENSON;
import static seedu.address.testutil.TypicalPairs.CARL_AND_DANIEL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.pair.UniquePairList;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.pair.exceptions.PairNotFoundException;

public class UniquePairListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePairList uniquePairList = new UniquePairList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePairList.asObservableList().remove(0);
    }

    @Test
    public void checkContainPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(ALICE_AND_BENSON);
        assertTrue(uniquePairList.contains(ALICE_AND_BENSON));
        assertFalse(uniquePairList.contains(CARL_AND_DANIEL));
    }

    @Test
    public void addPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(ALICE_AND_BENSON);
        assertTrue(uniquePairList.contains(ALICE_AND_BENSON));
    }

    @Test
    public void setPair() throws DuplicatePairException, PairNotFoundException {
        UniquePairList aliceAndBobList = new UniquePairList();
        aliceAndBobList.add(ALICE_AND_BENSON);
        UniquePairList carlAndDanielList = new UniquePairList();
        carlAndDanielList.add(CARL_AND_DANIEL);
        aliceAndBobList.setPair(ALICE_AND_BENSON, CARL_AND_DANIEL);
        assertEquals(aliceAndBobList, carlAndDanielList);

    }

    @Test
    public void setPairs() throws DuplicatePairException, PairNotFoundException {
        UniquePairList aliceAndBobList = new UniquePairList();
        aliceAndBobList.add(ALICE_AND_BENSON);
        UniquePairList carlAndDanielList = new UniquePairList();
        carlAndDanielList.add(CARL_AND_DANIEL);
        aliceAndBobList.setPairs(carlAndDanielList);
        assertEquals(aliceAndBobList, carlAndDanielList);
    }

}
