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
public class ReturnStmt extends Stmt{
    private Expr e;

    public ReturnStmt(Expr e) {
        this.e = e;
    }
    
}
