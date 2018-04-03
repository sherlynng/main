# dannyngmx94
###### \java\seedu\address\logic\parser\FilterCommandParserTest.java
``` java
public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new KeywordPredicate("Alice"));
        assertParseSuccess(parser, "Alice", expectedFilterCommand);

        // multiple whitespaces before and after keyword
        assertParseSuccess(parser, " \n Alice \n ", expectedFilterCommand);
    }

}
```
