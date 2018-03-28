package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedPair.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPairs.ALICE_AND_BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.testutil.Assert;

public class XmlAdaptedPairTest {
    private static final String INVALID_STUDENT_NAME = "R@chel";
    private static final String INVALID_TUTOR_NAME = "H@nmilt0n";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRICE = "-50";
    private static final String INVALID_LEVEL = "kindergarden";
    private static final String INVALID_SUBJECT = "fake news";

    private static final String VALID_STUDENT_NAME = ALICE_AND_BENSON.getStudentName().toString();
    private static final String VALID_TUTOR_NAME = ALICE_AND_BENSON.getTutorName().toString();
    private static final String VALID_SUBJECT = ALICE_AND_BENSON.getSubject().toString();
    private static final String VALID_LEVEL = ALICE_AND_BENSON.getLevel().toString();
    private static final String VALID_PRICE = ALICE_AND_BENSON.getPrice().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ALICE_AND_BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPairDetails_returnsPair() throws Exception {
        XmlAdaptedPair pair = new XmlAdaptedPair(ALICE_AND_BENSON);
        assertEquals(ALICE_AND_BENSON, pair.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(INVALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullStudentName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(null, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullTutorName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, null,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, pair::toModelType);
    }

    @Test
    public void toModelType_invalidLevel_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, INVALID_LEVEL, VALID_PRICE, VALID_TAGS);
        String expectedMessage = Level.MESSAGE_LEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullLevel_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, null, VALID_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                INVALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS);
        String expectedMessage = Subject.MESSAGE_SUBJECT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                null, VALID_LEVEL, VALID_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, INVALID_PRICE, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }


    @Test
    public void testXmlAdaptedPairEquality() {
        XmlAdaptedPair alice = new XmlAdaptedPair(ALICE_AND_BENSON);
        XmlAdaptedPair copy = new XmlAdaptedPair(ALICE_AND_BENSON);
        assertTrue(alice.equals(copy)); //check equality if values are equal
        assertFalse(alice.equals(ALICE_AND_BENSON)); //check not equal if type is different
    }

}
