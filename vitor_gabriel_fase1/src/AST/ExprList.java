/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package AST;

import Lexer.Symbol;
import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class ExprList extends Expr{
    private ArrayList<Expr> arrayExpr;

    public ExprList(ArrayList<Expr> arrayExpr) {
        this.arrayExpr = arrayExpr;
    }
    @Override
    public void genC(PW pw) {
        pw.out.print("[");
        arrayExpr.get(0).genC(pw);
        for(int i = 1 ; i < arrayExpr.size(); i++){
            pw.out.print(",");
            arrayExpr.get(i).genC(pw);
        }
        pw.out.print("]");
    }
    
    public int getSize(){
        return arrayExpr.size();
    }
    @Override
    public boolean getType(Symbol type) {
        for(int i = 0 ; i < arrayExpr.size(); i++){
            if( !arrayExpr.get(i).getType(type))
                return false;
        }
        return true;
    }
    
}
