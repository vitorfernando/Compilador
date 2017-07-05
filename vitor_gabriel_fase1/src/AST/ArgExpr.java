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
public class ArgExpr extends Expr{
    private Symbol type;
    private VariableExpr nameArray;

    public ArgExpr(Symbol type, VariableExpr nameArray) {
        this.type = type;
        this.nameArray = nameArray;
    }

    @Override
    public void genC(PW pw) {
        pw.out.print(type +" " + nameArray.getName());
    }

    @Override
    public boolean getType(Symbol type) {
        if(this.type == type){
            return true;
        }
        return false;
    }
}
