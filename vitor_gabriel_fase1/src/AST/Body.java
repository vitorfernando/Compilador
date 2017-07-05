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
public class Body {

    private final ArrayList<Stmt> stmtList;
    private final Declaration declaration;

    public Body(Declaration declaration, ArrayList<Stmt> stmtList) {
        this.stmtList = stmtList;
        this.declaration = declaration;
    }

    public boolean checkReturnType(Symbol type) {
        for (int i = 0; i < stmtList.size(); i++) {
            if (stmtList.get(i) instanceof ReturnStmt) {
                ReturnStmt rst = (ReturnStmt) stmtList.get(i);
                if ((rst.getE() == null) && (type == Symbol.VOID)) {
                    return true;
                } else if (rst.getE() == null) {
                    return false;
                } else {
                    return rst.getE().getType(type);
                }
            }
        }
        return false;
    }

    public boolean checkReturn() {
        if (stmtList.get(stmtList.size() - 1) instanceof ReturnStmt) {
            return true;
        }
        return false;
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

        for (Stmt st : stmtList) {
            st.genC(pw);
        }
    }
}
