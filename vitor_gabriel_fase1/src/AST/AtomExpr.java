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
public class AtomExpr extends Expr {

    public Expr e, d;

    public AtomExpr(Expr e, Expr d) {
        this.e = e;
        this.d = d;
    }

    @Override
    public void genC(PW pw) {
        e.genC(pw);
        if (d != null) {
            d.genC(pw);
        }
    }

    public Expr getE() {
        return e;
    }

    public Expr getD() {
        return d;
    }

    @Override
    public boolean getType(Symbol type) {
        return e.getType(type);
    }

}
