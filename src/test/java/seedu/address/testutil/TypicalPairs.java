package seedu.address.testutil;

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
            .withTutorName("Benson Meier").withSubject("Math").withLevel("Lower Sec").withPrice("50")
            .withTags("Math", "Lower Sec", "50")
            .build();
    public static final Pair CARL_AND_DANIEL = new PairBuilder().withStudentName("Carl Kurz")
            .withTutorName("Daniel Meier").withPrice("80").withSubject("Physics").withLevel("Upper Sec")
            .withTags("Physics", "Upper Sec", "80")
            .build();

    public static final Pair ELLE_AND_FIONA = new PairBuilder().withStudentName("Elle Meyer")
            .withTutorName("Fiona Kunz").withPrice("100").withSubject("English").withLevel("Upper Pri")
            .withTags("English", "Upper Pri", "100")
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
