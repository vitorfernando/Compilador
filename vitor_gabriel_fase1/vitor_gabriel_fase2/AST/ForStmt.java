/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class ForStmt extends Stmt {

    private String name;
    private Expr e2, e1;
    private ArrayList<Stmt> stmtList;

    public ForStmt(String name, Expr e1, Expr e2, ArrayList<Stmt> stmtList) {
        this.name = name;
        this.e2 = e2;
        this.e1 = e1;
        this.stmtList = stmtList;
    }

    public void genC(PW pw){
        pw.out.print("for("+name+" = ");
        e1.genC(pw);
        pw.out.print(";");
        NumberExpr n1 = (NumberExpr) e1;
        NumberExpr n2 = (NumberExpr) e2;
        if(Integer.getInteger(n1.getValue()) < Integer.getInteger(n2.getValue()) ){
            e1.genC(pw);
            pw.out.print(" < ");
            e2.genC(pw);
            pw.out.println("; "+ name + "++){");
        }
        else{
            e1.genC(pw);
            pw.out.print(" > ");
            e2.genC(pw);
            pw.out.println("; "+ name + "--){");
        }
        
        for(int i=0 ; i < stmtList.size(); i++){
            stmtList.get(i).genC(pw);
        }
        pw.out.println("}");
    }
}
