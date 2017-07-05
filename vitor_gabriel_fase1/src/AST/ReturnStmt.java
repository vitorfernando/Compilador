/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import Lexer.Symbol;

/**
 *
 * @author vitor
 */
public class ReturnStmt extends Stmt {

    private Expr e;

    public ReturnStmt(Expr e) {
        this.e = e;
    }

    public Expr getE() {
        return e;
    }

    public void genC(PW pw) {
        if (e == null) {
            pw.println("return ;");
        }else{
            pw.print("return ");
            e.genC(pw);
            pw.out.println(" ;");
        }
    }

}
