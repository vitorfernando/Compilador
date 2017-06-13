/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author vitor
 */
public enum Symbol {
    IDENT("identifier"),
    EOF("eof"),
    IF("if"),
    ELSE("else"),
    AND("and"),
    FOR("for"),
    INT("int"),
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
    NUMBER("Number"),
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
    RIGHTPAR(")");
    Symbol(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
    public String name;

}
