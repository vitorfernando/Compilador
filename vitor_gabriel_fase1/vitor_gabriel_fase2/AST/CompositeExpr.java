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
public class CompositeExpr extends Expr {

    private Expr left, right;
    private Symbol oper;

    public CompositeExpr(Expr pleft, Symbol poper, Expr pright) {
        left = pleft;
        oper = poper;
        right = pright;
    }

    public void genC(PW pw) {
        left.genC(pw);
        pw.out.print(" " + oper.toString() + " ");
        right.genC(pw);
    }

    @Override
    public boolean getType(Symbol type) {
        if(left.getType(type) != right.getType(type))
            return false;
        return left.getType(type);
    }
}
