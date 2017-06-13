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
public class VariableExpr extends Expr {

    private Variable var;

    public VariableExpr(Variable var) {
        this.var = var;
    }

    public void genC(PW pw) {
        pw.out.print(var.getName());
    }

    public int getSize() {
        return var.getSize();
    }

    public String getName() {
        return var.getName();
    }

    public Symbol getType() {
        return var.getType();
    }

    @Override
    public boolean getType(Symbol type) {
        if(var.getType() != type){
            return false;
        }
        return true;
    }
}
