/*
 Author(s): Vitor Fernando Souza Silva   RA: 552488
 Gabriel Piovani              RA: 552216 
 */
package AST;

import Lexer.Symbol;

/**
 *
 * @author vitor
 */
public class NumberExpr extends Expr {

    private int dig1;
    private Symbol signal;
    private int dig2;
    private Symbol type;

    public NumberExpr(Symbol signal, int n1) {
        this.dig1 = n1;
        this.signal = signal;
        this.dig2 = 0;
        this.type = Symbol.INT;
    }

    public NumberExpr(Symbol signal, int n1, int n2) {
        this.dig1 = n1;
        this.signal = signal;
        this.dig2 = n2;
        this.type = Symbol.FLOAT;
    }

    public boolean getType(Symbol type) {
        if (type == this.type) {
            return true;
        }
        return false;
    }

    public boolean isInt() {
        if (type == Symbol.INT) {
            return true;
        }
        return false;
    }

    public String getValue() {
        if (type == Symbol.INT) {
            if (signal == Symbol.PLUS) {
                return "" + dig1;
            }
            return signal.toString() + dig1;
        }
        if (signal == Symbol.PLUS) {
            return "" + dig1 + "." + dig2;
        }
        return signal.toString() + dig1 + "." + dig2;
    }

    public void genC(PW pw) {
        if (this.signal == Symbol.MINUS) {
            if (this.dig2 == 0) {
                pw.out.print(signal + "" + dig1);
            } else {
                pw.out.print(signal + "" + dig1 + "." + dig2);
            }
        } else {
            if (this.dig2 == 0) {
                pw.out.print(dig1);
            } else {
                pw.out.print(dig1 + "." + dig2);
            }
        }
    }
}
