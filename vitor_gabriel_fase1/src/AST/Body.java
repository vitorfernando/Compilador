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
public class Body {

    private final ArrayList<Stmt> stmtList;
    private final Declaration declaration;

    public Body(Declaration declaration, ArrayList<Stmt> stmtList) {
        this.stmtList = stmtList;
        this.declaration = declaration;
    }

    public boolean checkStringType() {
        return declaration.checkStringType();
    }

    public void genC(PW pw) {
        char type = ' ';
        int i = 0;

        if (!declaration.isEmpty()) {
            declaration.genC(pw);
        }
        pw.incrementTS();
        for (Stmt st : stmtList) {
            st.genC(pw);
        }
        pw.decrementTS();
    }
}
