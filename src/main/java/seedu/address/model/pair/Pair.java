package seedu.address.model.pair;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a pair (one student and one tutor) in STUtor.
 */
public class Pair  {

    public final String studentName;
    public final String tutorName;
    public final String subject;
    public final String level;
    public final String price;

    private final UniqueTagList tags;


    public Pair(String studentName, String tutorName, String subject, String level, String price, Set<Tag> tags) {
        requireAllNonNull(studentName, tutorName, subject, level, price, tags);
        this.studentName = studentName;
        this.tutorName = tutorName;
        this.subject = subject;
        this.level = level;
        this.price = price;

        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);

    }

    public String getStudentName() {
        return studentName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getSubject() {
        return subject;
    }

    public String getLevel() {
        return level;
    }

    public String getPrice() {
        return price;
    }

    public String getPairName() {
        return studentName + " /w " + tutorName;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * return a hashcode of the Pair object
     * @return
     */
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(studentName, tutorName, subject, level, price, tags);
    }

    /**
     * check if another object is equal to this pair
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Pair)) {
            return false;
        }


        Pair otherPair = (Pair) other;
        return otherPair.getStudentName().equals(this.getStudentName())
                && otherPair.getTutorName().equals(this.getTutorName())
                && otherPair.getSubject().equals(this.getSubject())
                & otherPair.getLevel().equals(this.getLevel())
                && otherPair.getPrice().equals(this.getPrice());
    }


}
