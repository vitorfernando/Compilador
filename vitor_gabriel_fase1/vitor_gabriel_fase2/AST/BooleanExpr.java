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
public class BooleanExpr extends Expr{
    private String value;

    public String getValue() {
        return value;
    }

    public BooleanExpr(String value) {
        this.value = value;
    }

    @Override
    public void genC(PW pw) {
        pw.out.print(value);
    }

    public boolean getType(Symbol type) {
        if(type == Symbol.BOOLEAN){
            return true;
        }
        return false;
    }
}
