package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_UNMATCHED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.exceptions.DuplicatePairException;

/**
 * A utility class containing a list of {@code Pair} objects to be used in tests.
 */
public class TypicalPairs {

    public static final Pair ALICE_AND_BENSON = new PairBuilder().withStudentName("Alice Pauline")
            .withTutorName("Benson Meier").withPrice("50").withSubject("Math").withLevel("Lower Sec")
            .build();
    public static final Pair CARL_AND_DANIEL = new PairBuilder().withStudentName("Carl Kurz")
            .withTutorName("Daniel Meier").withPrice("80").withSubject("Physics").withLevel("Upper Sec")
            .build();

    public static final Pair ELLE_AND_FIONA = new PairBuilder().withStudentName("Elle Meyer")
            .withTutorName("Fiona Kunz").withPrice("100").withSubject("English").withLevel("Upper Pri")
            .build();



    private TypicalPairs() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical pairs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Pair pair : getTypicalPairs()) {
            try {
                ab.addPair(pair);
            } catch (DuplicatePairException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Pair> getTypicalPairs() {
        return new ArrayList<>(Arrays.asList(ALICE_AND_BENSON, CARL_AND_DANIEL, ELLE_AND_FIONA));
    }
}
