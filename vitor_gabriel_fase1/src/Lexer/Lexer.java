/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
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
    public String numberValue;
    private static final int MaxValueInteger = 32768;
    private int lineNumber = 1;
    private Symbol lastToken;
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
        keywordsTable.put("void", Symbol.VOID);
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
        keywordsTable.put("True", Symbol.TRUE);
        keywordsTable.put("False", Symbol.FALSE);
        keywordsTable.put("def", Symbol.DEF);
        keywordsTable.put("return", Symbol.RETURN);
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
            lastToken = token;
            token = Symbol.EOF;
        } else {
            //ignore the coments
            if (ch == '#') {
                // comment found
                while (input[tokenPos] != '\0' && input[tokenPos] != '\n') {
                    tokenPos++;
                }
                if (input[tokenPos] == '\n') {
                    lineNumber++;
                }
                nextToken();
            } //is not a coment
            else {
                if (Character.isLetter(ch)) {
                    StringBuffer ident = new StringBuffer();
                    // is input[tokenPos] a letter ?
                    // isLetter is a static method of class Character
                    if (Character.isLetter(input[tokenPos])) {
                        // add a character to ident
                        ident.append(input[tokenPos]);
                        tokenPos++;
                        while (Character.isLetter(input[tokenPos]) || Character.isDigit(input[tokenPos])) {
                            // add a character to ident
                            ident.append(input[tokenPos]);
                            tokenPos++;
                        }
                        stringValue = ident.toString();
                        Symbol value = keywordsTable.get(stringValue);
                        if (value == null) {
                            switch (stringValue) {
                                case "True":
                                    lastToken = token;
                                    token = Symbol.TRUE;
                                    break;
                                case "False":
                                    lastToken = token;
                                    token = Symbol.FALSE;
                                    break;
                                default:
                                    token = Symbol.IDENT;
                                    break;
                            }
                        } else {
                            lastToken = token;
                            token = value;
                        }
                    }else{
                        error.signal("identifier must be iniciate by a letter");
                    }

                } else if (Character.isDigit(input[tokenPos])) {
                    // get a number
                    StringBuffer number = new StringBuffer();
                    while (Character.isDigit(input[tokenPos])) {
                        number.append(input[tokenPos]);
                        tokenPos++;
                    }
                    lastToken = token;
                    token = Symbol.NUMBER;

                    numberValue = number.toString();

                    if (Character.isLetter(input[tokenPos])) {
                        error.signal("Number followed by a letter");
                    }
                } else {
                    tokenPos++;
                    switch (ch) {
                        case '^':
                            lastToken = token;
                            token = Symbol.POW;
                            break;
                        case '+':
                            lastToken = token;
                            token = Symbol.PLUS;
                            break;
                        case '-':
                            lastToken = token;
                            token = Symbol.MINUS;
                            break;
                        case '*':
                            lastToken = token;
                            token = Symbol.MULT;
                            break;
                        case '/':
                            lastToken = token;
                            token = Symbol.DIV;
                            break;
                        case '<':
                            lastToken = token;
                            switch (input[tokenPos]) {
                                case '=':
                                    tokenPos++;
                                    token = Symbol.LE;
                                    break;
                                case '>':
                                    tokenPos++;
                                    token = Symbol.LG;
                                    break;
                                default:
                                    token = Symbol.LT;
                                    break;
                            }
                            break;
                        case '>':
                            lastToken = token;
                            if (input[tokenPos] == '=') {
                                tokenPos++;
                                token = Symbol.GE;
                            } else {
                                token = Symbol.GT;
                            }
                            break;
                        case '=':
                            lastToken = token;
                            if (input[tokenPos] == '=') {
                                tokenPos++;
                                token = Symbol.EQ;
                            } else {
                                token = Symbol.ASSIGN;
                            }
                            break;
                        case ',':
                            lastToken = token;
                            token = Symbol.COMMA;
                            break;
                        case ';':
                            lastToken = token;
                            token = Symbol.SEMICOLON;
                            break;
                        case '\'':
                            lastToken = token;
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
                            lastToken = token;
                            token = Symbol.TP;
                            break;
                        case '.':
                            lastToken = token;
                            token = Symbol.DOT;
                            break;
                        case '[':
                            lastToken = token;
                            token = Symbol.LEFTBRACKET;
                            break;
                        case ']':
                            lastToken = token;
                            token = Symbol.RIGHTBRACKET;
                            break;
                        case '{':
                            lastToken = token;
                            token = Symbol.LEFTKEYS;
                            break;
                        case '}':
                            lastToken = token;
                            token = Symbol.RIGHTKEYS;
                            break;
                        case '(':
                            lastToken = token;
                            token = Symbol.LEFTPAR;
                            break;
                        case ')':
                            lastToken = token;
                            token = Symbol.RIGHTPAR;
                            break;
                        default:
                            error.signal("Invalid Character: ’" + ch + "’");
                    }
                }

            }
        }
        System.out.println(token.toString());
        lastTokenPos = tokenPos - 1;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void backToken() {
        token = lastToken;
        tokenPos = lastTokenPos;
    }

    public String getNumberValue2() {
        return numberValue;
    }

    public int getNumberValue() {
        try {
            return Integer.parseInt(numberValue);
        } catch (NumberFormatException e) {
            error.signal("Number out of limits");
            return 0;
        }
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
