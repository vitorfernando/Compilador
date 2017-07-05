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
public class FuncExpr extends Expr {

    private FuncDef funcDef;
    private Expr d;
    
    public FuncExpr(FuncDef funcDef) {
        this.funcDef = funcDef;
    }

    public void setD(Expr d) {
        this.d = d;
    }

    @Override
    public void genC(PW pw) {
        pw.out.print(funcDef.getName());
    }

    @Override
    public boolean getType(Symbol type) {
        if (funcDef.getReturntype() == type) {
            return true;
        }
        return false;
    }

}
