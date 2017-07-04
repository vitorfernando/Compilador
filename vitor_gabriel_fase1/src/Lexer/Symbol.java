/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package Lexer;

/**
 *
 * @author vitor
 */
public enum Symbol {
    IDENT("identifier"),
    RETURN("return"),
    EOF("eof"),
    IF("if"),
    ELSE("else"),
    AND("and"),
    FOR("for"),
    INT("int"),
    VOID("void"),
    PRINT("print"),
    WHILE("while"),
    BOOLEAN("boolean"),
    END("end"),
    NOT("not"),
    PROGRAM("program"),
    BREAK("break"),
    FALSE("false"),
    IN("in"),
    NOTIN("notin"),
    STRING("string"),
    ELIF("elif"),
    FLOAT("float"),
    INRANGE("inrange"),
    OR("or"),
    TRUE("true"),
    NUMBER("number"),
    PLUS("+"),
    MINUS("-"),
    MULT("*"),
    DIV("/"),
    POW("^"),
    LT("<"),
    LG("<>"),
    LE("<="),
    GT(">"),
    GE(">="),
    EQ("=="),
    ASSIGN("="),
    SEMICOLON(";"),
    COMMA(","),
    TP(":"),
    DOT("."),
    LEFTKEYS("{"),
    RIGHTKEYS("}"),
    LEFTBRACKET("["),
    RIGHTBRACKET("]"),
    LEFTPAR("("),
    DEF("def"),
    RIGHTPAR(")");
    Symbol(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
    public String name;

}
