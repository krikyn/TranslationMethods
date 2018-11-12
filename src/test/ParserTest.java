import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void test1() {
        test("a");
    }

    @Test
    public void test2() {
        test("a&a&v|f|v|e^e^e");
    }

    @Test
    public void test3() {
        test("(!a|b)&a&(a|!(b^c))");
    }

    @Test
    public void test4() {
        test("a|b");
    }

    @Test
    public void test5() {
        test("(a)^(b)");
    }

    @Test
    public void test6() {
        test("a^b|a|f&t");
    }
    @Test
    public void tset7() {
        test("((a)|b)|c");
    }

    private void test(String what) {
        Parser parser = new Parser();
        try {
            Tree t = parser.parse(what);
            assertEquals(what, t.toString());
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }
}