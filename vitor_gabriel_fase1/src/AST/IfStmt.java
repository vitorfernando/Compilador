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
            pw.out.print("\tif(");

            for (Expr orTestList1 : this.orTestList) {
                orTestList1.genC(pw);
            }

            pw.out.println("){");
            for (Stmt ifStmtList1 : this.ifStmtList) {
                pw.out.print("\t");
                ifStmtList1.genC(pw);
            }
            pw.out.print("\t}");
        }
        if (!this.elseStmtList.isEmpty()) {
            pw.out.println("\telse{");
            for (Stmt elseStmtList1 : this.elseStmtList) {
                pw.out.print("\t");
                elseStmtList1.genC(pw);
            }
            pw.out.print("\t}");
        }

    }
}
