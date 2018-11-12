import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;

public class Parser {
    private LexicalAnalyzer lex;

    private Tree Formula() throws ParseException {
        return new Tree("Formula", Xor(), FormulaHatch());
    }

    private Tree Xor() throws ParseException {
        return new Tree("Xor", And(), XorHatch());
    }

    private Tree And() throws ParseException {
        return new Tree("And", Negation(), AndHatch());
    }

    private Tree Negation() throws ParseException {
        switch (lex.curToken()) {
            case NOT:
                lex.nextToken();
                return new Tree("Negation", new Tree("!"), SubFormula());
            default:
                return new Tree("Negation", SubFormula());
        }
    }

    private Tree SubFormula() throws ParseException {
        switch (lex.curToken()) {
            case LB:
                lex.nextToken();
                Tree formula = Formula();
                if (lex.curToken() != Token.RB) {
                    throw new ParseException(") expected", lex.curPos());
                }
                lex.nextToken();
                return new Tree("SubFormula", new Tree("("), formula, new Tree(")"));
            default:
                Tree t = new Tree("SubFormula", new Tree(lex.curStr()));
                lex.nextToken();
                return t;
        }
    }

    private Tree FormulaHatch() throws ParseException {
        switch (lex.curToken()) {
            case OR:
                lex.nextToken();
                return new Tree("FormulaHatch", new Tree("|"), Formula());
            default:
                return new Tree("FormulaHatch", Tree.epsilon());
        }
    }

    private Tree XorHatch() throws ParseException {
        switch (lex.curToken()) {
            case XOR:
                lex.nextToken();
                return new Tree("XorHatch", new Tree("^"), Xor());
            default:
                return new Tree("XorHatch", Tree.epsilon());
        }
    }

    private Tree AndHatch() throws ParseException {
        switch (lex.curToken()) {
            case AND:
                lex.nextToken();
                return new Tree("AndHatch", new Tree("&"), And());
            default:
                return new Tree("AndHatch", Tree.epsilon());
        }
    }

    public Tree parse(String s) throws ParseException {
        return parse(new ByteArrayInputStream(s.getBytes()));
    }

    public Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        Tree t = Formula();
        if(lex.curToken() != Token.END) {
            throw new ParseException("Unexpected end of input", lex.curPos());
        }
        return t;
    }
}