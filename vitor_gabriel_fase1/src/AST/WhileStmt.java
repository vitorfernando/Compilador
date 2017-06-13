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
        pw.out.print("\twhile(");
        for (int i = 0; i < orTestList.size(); i++) {
            orTestList.get(i).genC(pw);
        }
        pw.out.println("){");
        pw.out.print("\t");
        if (!stmtList.isEmpty()) {
            for (int i = 0; i < stmtList.size(); i++) {
                if(this.stmtList.get(i) instanceof ExprStmt){
                    pw.out.print("\t");
                    this.stmtList.get(i).genC(pw);
                }
                else{
                    this.stmtList.get(i).genC(pw);
                }
                pw.out.print("\t");
            }
        }
        pw.out.println("}");
    }
}
