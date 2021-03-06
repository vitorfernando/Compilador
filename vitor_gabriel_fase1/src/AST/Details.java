/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import Lexer.Symbol;

/**
 *
 * @author vitor
 */
public class Details extends Expr {

    private Expr e;

    public Details(Expr e) {
        this.e = e;
    }

    @Override
    public void genC(PW pw) {
        if (e instanceof OrList) {
            e.genC(pw);
        } else {
            pw.out.print("[");
            e.genC(pw);
            pw.out.print("]");
        }
    }

    @Override
    public boolean getType(Symbol type) {
        return e.getType(type);
    }

}
