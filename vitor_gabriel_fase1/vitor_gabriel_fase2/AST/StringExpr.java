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
public class StringExpr extends Expr{
    String str;

    public StringExpr(String str) {
        this.str = str;
    }
    
    public String getValue(){
        return this.str;
    }
    
    @Override
    public void genC(PW pw) {
        pw.out.print(str);
    }

    public boolean getType(Symbol type) {
        if(type == Symbol.STRING){
            return true;
        }
        return false;
    }
    
}
