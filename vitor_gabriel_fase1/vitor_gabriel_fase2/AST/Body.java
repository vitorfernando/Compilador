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
public class Body{

    private ArrayList<Stmt> stmtList;
    private Declaration declaration;

    public Body(Declaration declaration, ArrayList<Stmt> stmtList) {
        this.stmtList = stmtList;
        this.declaration = declaration;
    }

    public boolean checkStringType(){
        return declaration.checkStringType();
    }
    
    public void genC(PW pw) {
        char type = ' ';
        int i = 0;
        
        if(!declaration.isEmpty()){
            declaration.genC(pw);
        }
        
        for (Stmt st : stmtList) {
            if (st instanceof PrintStmt) {
                st.genC(pw);
            } else {
                pw.out.print("\t");
                st.genC(pw);
            }
            pw.out.print("\t");
            pw.out.println("");
        }
    }
}
