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
public class IfStmt extends Stmt {

    private ArrayList<Expr> orTestList = new ArrayList<Expr>();
    private ArrayList<Stmt> ifStmtList = new ArrayList<Stmt>();
    private ArrayList<Stmt> elseStmtList = new ArrayList<Stmt>();

    public IfStmt(ArrayList<Expr> orTesteList, ArrayList<Stmt> stmtList1, ArrayList<Stmt> stmtList2) {
        this.orTestList = orTestList;
        this.elseStmtList = stmtList2;
        this.ifStmtList = stmtList1;
    }

    public void genC(PW pw) {
        if (!this.ifStmtList.isEmpty()) {
            pw.out.print("if(");
            
            for (int i = 0; i < this.orTestList.size(); i++) {
                this.orTestList.get(i).genC(pw);
            }
            
            pw.out.println("){");
            pw.out.print("\t");
            for (int i = 0; i < this.ifStmtList.size(); i++) {
                if(this.ifStmtList.get(i) instanceof ExprStmt){
                    pw.out.print("\t");
                    this.ifStmtList.get(i).genC(pw);
                }
                else{
                    this.ifStmtList.get(i).genC(pw);
                }
                pw.out.print("\t");
            }
            pw.out.print("}");
        }
        if (!this.elseStmtList.isEmpty()) {
            pw.out.print("else{");
            pw.out.print("\t");
            for (int i = 0; i < this.elseStmtList.size(); i++) {
                if(this.elseStmtList.get(i) instanceof ExprStmt){
                    pw.out.print("\t");
                    this.elseStmtList.get(i).genC(pw);
                }
                else{
                    this.elseStmtList.get(i).genC(pw);
                }
                pw.out.print("\t");
            }
            pw.out.print("}");
        }

    }
}
