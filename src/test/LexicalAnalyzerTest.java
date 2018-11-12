import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LexicalAnalyzerTest {

    @Test
    public void oneCharTest() {
        test("a", Arrays.asList(Token.VAR));
    }

    @Test
    public void test2() {
        test("(!a|b)", Arrays.asList(Token.LB, Token.NOT, Token.VAR, Token.OR, Token.VAR, Token.RB));
    }

    @Test
    public void test3() {
        test("((a)|b)|c", Arrays.asList(Token.LB, Token.LB, Token.VAR, Token.RB, Token.OR, Token.VAR, Token.RB, Token.OR, Token.VAR));
    }

    private void test(String input, List<Token> answer) {
        try {
            LexicalAnalyzer lex = new LexicalAnalyzer(input);
            for (Token t : answer) {
                assertEquals(t, lex.nextToken());
            }
            assertEquals(Token.END, lex.nextToken());
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }
}