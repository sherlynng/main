package seedu.address.model;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TypicalPersons;

public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    @Test
    public void editAndDelete_noMatchingPerson_throwsPersonNotFoundException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        Person person = TypicalPersons.ALICE;
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(person));
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(person, person));
    }
}
