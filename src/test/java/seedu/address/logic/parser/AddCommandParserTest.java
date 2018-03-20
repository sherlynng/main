package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddCommand.MESSAGE_USAGE;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_LOWER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_UPPER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_LOWER;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_UPPER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_UNMATCHED;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_UNMATCHED;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ECONOMICS;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_ECONOMICS;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_STUDENT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED
                + PRICE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_STUDENT, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC
                + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_STUDENT, new AddCommand(expectedPerson));


        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC
                + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_STUDENT, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC
                + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_STUDENT, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC
                + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_STUDENT, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(SUBJECT_ECONOMICS)
                .withPrice(PRICE_BOB).withLevel(LEVEL_UPPER).withStatus(STATUS_UNMATCHED)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND, VALID_TAG_STUDENT).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ECONOMICS + PRICE_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + TAG_DESC_STUDENT, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_fieldsMissing_failure() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSubject(SUBJECT_ENGLISH)
                .withPrice(PRICE_AMY).withLevel(LEVEL_LOWER).withStatus(STATUS_UNMATCHED).withTags().build();
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + PRICE_DESC_AMY + SUBJECT_DESC_AMY + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED,
                MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ENGLISH + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ENGLISH + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ENGLISH + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + SUBJECT_DESC_ENGLISH + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);

        //missing price prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_BOB,
                expectedMessage);

        //missing subject prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_ENGLISH + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);

        //missing level prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + VALID_LEVEL_BOB + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + SUBJECT_DESC_ENGLISH + VALID_LEVEL_BOB + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_STUDENT, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_STUDENT, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_STUDENT, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_STUDENT, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + INVALID_PRICE_DESC
                + TAG_DESC_STUDENT, Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_SUBJECT_DESC + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_STUDENT, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        // invalid level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + INVALID_LEVEL_DESC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + TAG_DESC_STUDENT, Level.MESSAGE_LEVEL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC + INVALID_STATUS_DESC + PRICE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Status.MESSAGE_STATUS_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_ECONOMICS + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED
                        + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE));
    }
}
