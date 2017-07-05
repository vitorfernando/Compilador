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
public class FuncDef {

    private final String name;

    public String getName() {
        return name;
    }

    public Symbol getReturntype() {
        return returntype;
    }
    private final ArrayList<ArgExpr> argsList;
    private final Symbol returntype;
    private final Body body;

    public Body getBody() {
        return body;
    }

    public FuncDef(String name, ArrayList<ArgExpr> argsList, Symbol returntype, Body body) {
        this.name = name;
        this.argsList = argsList;
        this.returntype = returntype;
        this.body = body;
    }

    public boolean checkStringType() {
        return body.checkStringType();
    }

    void genC(PW pw) {
        pw.out.print(returntype.toString() + " " + name + "(");
        if (!argsList.isEmpty()) {
            for (int i = 0; i < argsList.size() - 1; i++) {
                argsList.get(i).genC(pw);
                pw.out.print(" , ");
            }
            argsList.get(argsList.size() - 1).genC(pw);
        }
        pw.out.println("){");
        pw.incrementTS();
        if (body != null) {
            body.genC(pw);
        }
        pw.decrementTS();
        pw.println("}");
    }
}
