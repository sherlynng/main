package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.tag.Tag;

//@@author aussiroth
public class XmlAdaptedTagTest {

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        Tag expectedTag = new Tag("math", Tag.AllTagTypes.SUBJECT);
        XmlAdaptedTag xmlExpectedTag = new XmlAdaptedTag(expectedTag);
        assertEquals(expectedTag, xmlExpectedTag.toModelType());
    }

    @Test
    public void toModelType_noTagType_returnsWithDefaultType() throws Exception {
        Tag expectedTag = new Tag("math");
        XmlAdaptedTag xmlTag = new XmlAdaptedTag("math");
        assertEquals(expectedTag, xmlTag.toModelType());
    }

    @Test
    public void toModelType_unknownTagType_returnsWithDefaultType() throws Exception {
        Tag expectedTag = new Tag("math");
        XmlAdaptedTag xmlTag = new XmlAdaptedTag("math", "nonexistenttype");
        assertEquals(expectedTag, xmlTag.toModelType());
    }
}
