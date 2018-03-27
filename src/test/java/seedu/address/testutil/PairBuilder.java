package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.pair.Pair;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Pair objects.
 */
public class PairBuilder {

    public static final String DEFAULT_STUDENT_NAME = "Harry Potter";
    public static final String DEFAULT_TUTOR_NAME = "Severus Snape";
    public static final String DEFAULT_SUBJECT = "English";
    public static final String DEFAULT_LEVEL = "Lower Sec";
    public static final String DEFAULT_PRICE = "100";
    public static final String DEFAULT_TAG_SUBJECT = "English";
    public static final String DEFAULT_TAG_LEVEL = "Lower Sec";
    public static final String DEFAULT_TAG_PRICE = "100";

    public String studentName;
    public String tutorName;
    public String subject;
    public String level;
    public String price;
    private Set<Tag> tags;

    public PairBuilder() {
        studentName = DEFAULT_STUDENT_NAME;
        tutorName =  DEFAULT_TUTOR_NAME;
        subject = DEFAULT_SUBJECT;
        level = DEFAULT_LEVEL;
        price = DEFAULT_PRICE;
        tags = SampleDataUtil.getTagSet(DEFAULT_TAG_SUBJECT, DEFAULT_TAG_LEVEL, DEFAULT_TAG_PRICE);
    }

    /**
     * Initializes the PairBuilder with the data of {@code pairToCopy}.
     */
    public PairBuilder(Pair pairToCopy) {
        studentName = pairToCopy.getStudentName();
        tutorName = pairToCopy.getTutorName();
        price = pairToCopy.getPrice();
        subject = pairToCopy.getSubject();
        level = pairToCopy.getLevel();
        tags = new HashSet<>(pairToCopy.getTags());
    }

    /**
     * Sets the {@code studentName} of the {@code Pair} that we are building.
     */
    public PairBuilder withStudentName(String name) {
        this.studentName = name;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Pair} that we are building.
     */
    public PairBuilder withTutorName(String name) {
        this.tutorName = name;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Pair} that we are building.
     */
    public PairBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withLevel(String level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withPrice(String price) {
        this.price = price;
        return this;
    }


    /**
     * Sets the required attribute tags for the pair
     */
    private void setTags() {

        tags.add(new Tag(price.toString(), Tag.AllTagTypes.PRICE));
        tags.add(new Tag(subject.toString(), Tag.AllTagTypes.SUBJECT));
        tags.add(new Tag(level.toString(), Tag.AllTagTypes.LEVEL));
    }

    /**
     * Builds a pair based off the attributes in this class
     * @return Pair with set attributes
     */
    public Pair build() {
        setTags();
        return new Pair(studentName, tutorName, subject, level, price, tags);
    }

}
