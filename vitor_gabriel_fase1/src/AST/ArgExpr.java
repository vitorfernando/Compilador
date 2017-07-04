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
         }

    @Override
    public boolean getType(Symbol type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
