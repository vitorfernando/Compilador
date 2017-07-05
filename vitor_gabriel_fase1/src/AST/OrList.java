/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import Lexer.Symbol;
import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class OrList extends Expr {

    private ArrayList<Expr> arrayExpr;

    public OrList(ArrayList<Expr> arrayExpr) {
        this.arrayExpr = arrayExpr;
    }

    @Override
    public void genC(PW pw) {
        AtomExpr atom;

        if (arrayExpr.get(0) instanceof AtomExpr) {
            atom = (AtomExpr) arrayExpr.get(0);
            if (atom.getE() instanceof StringExpr) {
                pw.out.print("\"");
                arrayExpr.get(0).genC(pw);
                pw.out.print("\"");
            } else {
                arrayExpr.get(0).genC(pw);
            }
        } else {
            arrayExpr.get(0).genC(pw);
        }
        
        for (int i = 1; i < arrayExpr.size(); i++) {
            pw.out.print(",");
            if (arrayExpr.get(i) instanceof AtomExpr) {
                atom = (AtomExpr) arrayExpr.get(i);
                if (atom.getE() instanceof StringExpr) {
                    pw.out.print("\"");
                    arrayExpr.get(i).genC(pw);
                    pw.out.print("\"");
                } else {
                    arrayExpr.get(i).genC(pw);
                }
            } else {
                arrayExpr.get(i).genC(pw);
            }
        }
    }

    public int getSize() {
        return arrayExpr.size();
    }

    @Override
    public boolean getType(Symbol type) {
        for (int i = 0; i < arrayExpr.size(); i++) {
            if (!arrayExpr.get(i).getType(type)) {
                return false;
            }
        }
        return true;
    }
}
