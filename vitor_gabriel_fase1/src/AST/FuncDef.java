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
    private final ArrayList<ArgExpr> argsList;
    private final Symbol returntype;
    private final Body body;

    public FuncDef(String name, ArrayList<ArgExpr> argsList, Symbol returntype, Body body) {
        this.name = name;
        this.argsList = argsList;
        this.returntype = returntype;
        this.body = body;
    }

    
    public boolean checkStringType() {
        return body.checkStringType();
    }
    
    void genC(PW pw){
    }
}
