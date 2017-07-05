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
        AtomExpr atomExpr;
        pw.print("printf(\"");
        for (int i = 0; i < arrayExpr.size(); i++) {
            if (arrayExpr.get(i) instanceof CompositeExpr) {
                CompositeExpr cexp = (CompositeExpr) arrayExpr.get(i);
                atomExpr = (AtomExpr) cexp.getLeft();
            } else {
                atomExpr = (AtomExpr) arrayExpr.get(i);
            }
            if (atomExpr.getE() instanceof StringExpr) {
                arrayExpr.get(i).genC(pw);
            } else if (atomExpr.getE() instanceof NumberExpr) {
                NumberExpr ne = (NumberExpr) atomExpr.getE();
                if (ne.getType(Symbol.INT)) {
                    pw.out.print("%d");
                } else if (ne.getType(Symbol.FLOAT)) {
                    pw.out.print("%f");
                }
            } else if (atomExpr.getE() instanceof FuncExpr) {
                FuncExpr funcExpr = (FuncExpr) atomExpr.getE();
                if (atomExpr.getType(Symbol.INT)) {
                    pw.out.print("%d");
                } else if (atomExpr.getType(Symbol.FLOAT)) {
                    pw.out.print("%f");
                } else if (atomExpr.getType(Symbol.STRING)) {
                    pw.out.print("%c");
                }
            } else {
                VariableExpr varExpr = (VariableExpr) atomExpr.getE();
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
            if (arrayExpr.get(i) instanceof CompositeExpr) {
                CompositeExpr cexp = (CompositeExpr) arrayExpr.get(i);
                atomExpr = (AtomExpr) cexp.getLeft();
            } else {
                atomExpr = (AtomExpr) arrayExpr.get(i);
            }
            if (atomExpr.getE() instanceof FuncExpr) {
                pw.out.print(",");
                atomExpr.genC(pw);
                pw.out.print("()");
            } else if (!(atomExpr.getE() instanceof StringExpr)) {
                pw.out.print(",");
                atomExpr.genC(pw);
            }
        }
        pw.out.println(");");
    }
}
