/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package AST;

import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class WhileStmt extends Stmt {

    private ArrayList<Expr> orTestList;
    private ArrayList<Stmt> stmtList;

    public WhileStmt(ArrayList<Expr> e, ArrayList<Stmt> stmtList) {
        this.orTestList = e;
        this.stmtList = stmtList;
    }

    public void genC(PW pw) {
        pw.print("while(");
        for (int i = 0; i < orTestList.size(); i++) {
            orTestList.get(i).genC(pw);
        }
        pw.out.println("){");
        pw.incrementTS();
        if (!stmtList.isEmpty()) {
            for (int i = 0; i < stmtList.size(); i++) {
                if(this.stmtList.get(i) instanceof ExprStmt){
                    this.stmtList.get(i).genC(pw);
                }
                else{
                    this.stmtList.get(i).genC(pw);
                }
            }
        }
        pw.decrementTS();
        pw.println("}");
        
    }
}
