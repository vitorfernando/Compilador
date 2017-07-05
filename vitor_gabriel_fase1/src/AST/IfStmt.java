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

    private final ArrayList<Expr> orTestList;
    private final ArrayList<Stmt> ifStmtList;
    private final ArrayList<Stmt> elseStmtList;

    public IfStmt(ArrayList<Expr> orTestList, ArrayList<Stmt> stmtList1, ArrayList<Stmt> stmtList2) {
        this.orTestList = orTestList;
        this.elseStmtList = stmtList2;
        this.ifStmtList = stmtList1;
    }

    public void genC(PW pw) {
        if (!this.ifStmtList.isEmpty()) {
            pw.print("if(");
            for (Expr orTestList1 : this.orTestList) {
                orTestList1.genC(pw);
            }

            pw.out.println("){");
            pw.incrementTS();
            for (Stmt ifStmtList1 : this.ifStmtList) {            
                ifStmtList1.genC(pw);
            }
            pw.decrementTS();
            pw.println("}");
        }
        if (!this.elseStmtList.isEmpty()) {
            pw.println("else{");
            pw.incrementTS();
            for (Stmt elseStmtList1 : this.elseStmtList) {
                elseStmtList1.genC(pw);
            }
            pw.decrementTS();
            pw.println("}");
        }

    }
}
