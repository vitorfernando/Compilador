/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import Error.CompilerError;
import java.util.Hashtable;

/**
 *
 * @author vitor
 */
public class Lexer {

    private char[] input;
    private CompilerError error;

    public Lexer(char[] input, CompilerError error) {
        this.input = input;
        input[input.length - 1] = '\0';
        lineNumber = 1;
        tokenPos = 0;
        this.error = error;
    }

    public Symbol token;
    private int tokenPos;
    private int lastTokenPos;
    private String stringValue;
    public int numberValue;
    private static final int MaxValueInteger = 32768;
    private int lineNumber = 1;

    static private Hashtable<String, Symbol> keywordsTable;

    static {
        keywordsTable = new Hashtable<String, Symbol>();

        keywordsTable.put("end", Symbol.END);
        keywordsTable.put("if", Symbol.IF);
        keywordsTable.put("else", Symbol.ELSE);
        keywordsTable.put("elif", Symbol.ELIF);
        keywordsTable.put("and", Symbol.AND);
        keywordsTable.put("or", Symbol.OR);
        keywordsTable.put("int", Symbol.INT);
        keywordsTable.put("string", Symbol.STRING);
        keywordsTable.put("float", Symbol.FLOAT);
        keywordsTable.put("print", Symbol.PRINT);
        keywordsTable.put("for", Symbol.FOR);
        keywordsTable.put("while", Symbol.WHILE);
        keywordsTable.put("boolean", Symbol.BOOLEAN);
        keywordsTable.put("not", Symbol.NOT);
        keywordsTable.put("program", Symbol.PROGRAM);
        keywordsTable.put("break", Symbol.BREAK);
        keywordsTable.put("in", Symbol.IN);
        keywordsTable.put("notin", Symbol.NOTIN);
        keywordsTable.put("inrange", Symbol.INRANGE);
        keywordsTable.put("true", Symbol.TRUE);
        keywordsTable.put("false", Symbol.FALSE);
    }

    public void nextToken() {
        char ch;
        while ((ch = input[tokenPos]) == ' ' || ch == '\n'
                || ch == '\t' || ch == '\r') {
            if (ch == '\n') {
                lineNumber++;
            }
            tokenPos++;
        }
        if (ch == '\0') {
            token = Symbol.EOF;
        } else {
            //ignore the coments
            if (ch == '#') {
                // comment found
                while (input[tokenPos] != '\0' && input[tokenPos] != '\n') {
                    tokenPos++;
                }
                nextToken();
            } //is not a coment
            else {
                if (Character.isLetter(ch)) {
                    StringBuffer ident = new StringBuffer();
                    // is input[tokenPos] a letter ?
                    // isLetter is a static method of class Character
                    while (Character.isLetter(input[tokenPos])) {
                        // add a character to ident
                        ident.append(input[tokenPos]);
                        tokenPos++;
                    }
                    stringValue = ident.toString();
                    Symbol value = keywordsTable.get(stringValue);
                    if (value == null) {
                        switch (stringValue) {
                            case "True":
                                token = Symbol.TRUE;
                                break;
                            case "False":
                                token = Symbol.FALSE;
                                break;
                            default:
                                token = Symbol.IDENT;
                                break;
                        }
                    } else {
                        token = value;
                    }

                    if (Character.isDigit(ch)) {
                        error.signal("Word followed by a number");
                    }
                } else if (Character.isDigit(input[tokenPos])) {
                    // get a number
                    StringBuffer number = new StringBuffer();
                    while (Character.isDigit(input[tokenPos])) {
                        number.append(input[tokenPos]);
                        tokenPos++;
                    }
                    token = Symbol.NUMBER;
                    try {
                        numberValue = Integer.valueOf(number.toString()).intValue();
                    } catch (NumberFormatException e) {
                        error.signal("Number out of limits");
                    }
                    if (numberValue >= MaxValueInteger) {
                        error.signal("Number out of limits");
                    }
                    if (Character.isLetter(input[tokenPos])) {
                        error.signal("Number followed by a letter");
                    }
                } else {
                    tokenPos++;
                    switch (ch) {
                        case '+':
                            token = Symbol.PLUS;
                            break;
                        case '-':
                            token = Symbol.MINUS;
                            break;
                        case '*':
                            token = Symbol.MULT;
                            break;
                        case '/':
                            token = Symbol.DIV;
                            break;
                        case '<':
                            if (input[tokenPos] == '=') {
                                tokenPos++;
                                token = Symbol.LE;
                            } else if (input[tokenPos] == '>') {
                                tokenPos++;
                                token = Symbol.LG;
                            } else {
                                token = Symbol.LT;
                            }
                            break;
                        case '>':
                            if (input[tokenPos] == '=') {
                                tokenPos++;
                                token = Symbol.GE;
                            } else {
                                token = Symbol.GT;
                            }
                            break;
                        case '=':
                            if (input[tokenPos] == '=') {
                                tokenPos++;
                                token = Symbol.EQ;
                            } else {
                                token = Symbol.ASSIGN;
                            }
                            break;
                        case ',':
                            token = Symbol.COMMA;
                            break;
                        case ';':
                            token = Symbol.SEMICOLON;
                            break;
                        case '\'':
                            // get a string
                            StringBuffer str = new StringBuffer();
                            while (input[tokenPos] != '\'') {
                                str.append(input[tokenPos]);
                                tokenPos++;
                            }
                            tokenPos++;
                            token = Symbol.STRING;
                            stringValue = str.toString();
                            break;
                        case ':':
                            token = Symbol.TP;
                            break;
                        case '.':
                            token = Symbol.DOT;
                            break;
                        case '[':
                            token = Symbol.LEFTBRACKET;
                            break;
                        case ']':
                            token = Symbol.RIGHTBRACKET;
                            break;
                        case '{':
                            token = Symbol.LEFTKEYS;
                            break;
                        case '}':
                            token = Symbol.RIGHTKEYS;
                            break;
                        case '(':
                            token = Symbol.LEFTPAR;
                            break;
                        case ')':
                            token = Symbol.RIGHTPAR;
                            break;
                        default:
                            error.signal("Invalid Character: ’" + ch + "’");
                    }
                }

            }
        }
        lastTokenPos = tokenPos - 1;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
