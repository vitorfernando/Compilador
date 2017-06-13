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
