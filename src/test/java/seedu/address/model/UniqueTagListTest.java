package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }

    @Test
    public void checkEquality() throws UniqueTagList.DuplicateTagException {
        UniqueTagList uniqueTagListA = new UniqueTagList();
        UniqueTagList uniqueTagListB = new UniqueTagList();
        uniqueTagListA.add(new Tag("Not Matched"));
        assertFalse(uniqueTagListA.equals(uniqueTagListB));
        uniqueTagListB.add(new Tag("Not Matched"));
        assertTrue(uniqueTagListA.equals(uniqueTagListB));

    }
}
