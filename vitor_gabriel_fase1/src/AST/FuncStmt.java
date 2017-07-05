/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author vitor
 */
public class FuncStmt extends Stmt {

    private String name;
    private Expr e;

    public FuncStmt(String name, Expr e) {
        this.name = name;
        this.e = e;
    }

    public void genC(PW pw) {
        pw.print(name);
        if (e != null) {
            e.genC(pw);
            pw.out.println(";");
        } else {
            pw.out.println("();");
        }
    }

}
