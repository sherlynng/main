package seedu.address.logic;

import java.util.HashSet;
import java.util.Set;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class AttributeTagSetter {

    public static Set<Tag> removePresentAttributeTags(Person person, Set<Tag> updatedTags) {
        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //ignore if attribute is empty (not entered yet by user)
        if (!person.getPrice().toString().equals("")) {
            attributeTags.remove(new Tag(person.getPrice().toString(), Tag.AllTagTypes.PRICE));
        }
        if (!person.getLevel().toString().equals("")) {
            attributeTags.remove(new Tag(person.getLevel().toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!person.getSubject().toString().equals("")) {
            attributeTags.remove(new Tag(person.getSubject().toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!person.getStatus().toString().equals("")) {
            attributeTags.remove(new Tag(person.getStatus().toString(), Tag.AllTagTypes.STATUS));
        }
        if (!person.getRole().toString().equals("")) {
            attributeTags.remove(new Tag(person.getRole().toString(), Tag.AllTagTypes.ROLE));
        }
        return new HashSet<>(attributeTags);
    }
}
