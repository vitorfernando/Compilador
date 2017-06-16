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
        pw.print("for("+name+" = ");
        e1.genC(pw);
        pw.out.print(";");
        NumberExpr n1 = (NumberExpr) e1;
        NumberExpr n2 = (NumberExpr) e2;
        
        if(Integer.valueOf(n1.getValue()) < Integer.valueOf(n2.getValue()) ){
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
        pw.incrementTS();
        for(int i=0 ; i < stmtList.size(); i++){
            stmtList.get(i).genC(pw);
        }
        pw.decrementTS();
        pw.println("}");
    }
}
