import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class LexicalAnalyzer {
    private final InputStream is;
    private Token curToken;
    private int curPos, curChar;
    private String curStr;

    public LexicalAnalyzer(String s) throws ParseException {
        this.is = new ByteArrayInputStream(s.getBytes());
        nextChar();
    }


    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        nextChar();
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    Token nextToken() throws ParseException {
        while (Character.isWhitespace(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '|':
                nextChar();
                curToken = Token.OR;
                break;
            case '(':
                nextChar();
                curToken = Token.LB;
                break;
            case ')':
                nextChar();
                curToken = Token.RB;
                break;
            case '^':
                nextChar();
                curToken = Token.XOR;
                break;
            case '&':
                nextChar();
                curToken = Token.AND;
                break;
            case '!':
                nextChar();
                curToken = Token.NOT;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                if (Character.isLetterOrDigit(curChar())) {
                    curToken = Token.VAR;
                    curStr = Character.toString(curChar());
                    nextChar();
                } else {
                    throw new ParseException("Illegal character '" + (char) curChar + "'", curPos);
                }
                break;
        }
        return curToken;
    }

    private char curChar() {
        return (char) curChar;
    }

    int curPos() {
        return curPos;
    }

    Token curToken() {
        return curToken;
    }

    String curStr() {
        return curStr;
    }
}