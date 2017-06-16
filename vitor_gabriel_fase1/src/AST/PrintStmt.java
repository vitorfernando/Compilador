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
public class PrintStmt extends Stmt {

    private ArrayList<Expr> arrayExpr;

    public PrintStmt(ArrayList<Expr> arrayExpr) {
        this.arrayExpr = arrayExpr;
    }

    public void genC(PW pw) {
        pw.print("printf(\"");
        for (int i = 0; i < arrayExpr.size(); i++) {
            if (arrayExpr.get(i) instanceof StringExpr) {
                arrayExpr.get(i).genC(pw);
            } else if (arrayExpr.get(i) instanceof NumberExpr) {
                NumberExpr ne = (NumberExpr) arrayExpr.get(i);
                if (ne.getType(Symbol.INT)) {
                    pw.out.print("%d");
                }
                if (ne.getType(Symbol.FLOAT)) {
                    pw.out.print("%f");
                }
            } else {
                VariableExpr varExpr = (VariableExpr) arrayExpr.get(i);
                if (varExpr.getType(Symbol.INT)) {
                    pw.out.print("%d");
                } else if (varExpr.getType(Symbol.FLOAT)) {
                    pw.out.print("%f");
                } else if (varExpr.getType(Symbol.STRING)) {
                    if (varExpr.getSize() != 1) {
                        pw.out.print("%s");
                    } else {
                        pw.out.print("%c");
                    }
                }
            }
            if ((i + 1) != arrayExpr.size()) {
                pw.out.print(" ");
            }
        }
        pw.out.print("\"");
        for (int i = 0; i < arrayExpr.size(); i++) {
            if (!(arrayExpr.get(i) instanceof StringExpr)) {
                pw.out.print(",");
                arrayExpr.get(i).genC(pw);
            }
        }
        pw.out.println(");");
    }
}
