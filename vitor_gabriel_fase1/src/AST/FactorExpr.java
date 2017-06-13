/*
 Author(s): Vitor Fernando Souza Silva   RA: 552488
 Gabriel Piovani              RA: 552216 
 */
package AST;

import Lexer.Symbol;
import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class FactorExpr extends Expr {

    private Expr left;
    private Expr right;
    private Symbol op;

    public FactorExpr(Symbol op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public void genC(PW pw) {
        if (op != null) {
            pw.out.print(" " + op.toString() + " ");
        }
        left.genC(pw);
        if (right != null) {
            pw.out.print(" ^ ");
            right.genC(pw);
        }
    }

    @Override
    public boolean getType(Symbol type) {
        if (left.getType(type)) {
            if(right != null){
                if (right.getType(type)) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}
