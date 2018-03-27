package seedu.address.model.pair;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
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
        requireAllNonNull(studentName,tutorName, subject, level, price, tags);
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

    public String getName() {
        return studentName +" /w "+tutorName;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }


}
