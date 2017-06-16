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
public class ExprStmt extends Stmt {

    private ArrayList<Expr> arrayExpr;
    private String name;
    private int index;

    public ExprStmt(String name, int index, ArrayList<Expr> array) {
        this.name = name;
        this.arrayExpr = array;
        this.index = index;
    }

    public void genC(PW pw) {
        pw.print(name);
        if (index != -1) {
            pw.out.print("[" + index + "]");
        }
        pw.out.print(" = ");
        for (int i = 0; i < arrayExpr.size(); i++) {
            if (arrayExpr.get(i) instanceof StringExpr) {
                pw.out.print("'");
                arrayExpr.get(i).genC(pw);
                pw.out.print("'");
            } else {
                arrayExpr.get(i).genC(pw);
            }
        }
        pw.out.println(";");
    }

}
