/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package AST;

/**
 *
 * @author vitor
 */
public class BreakStmt extends Stmt{
    public void genC(PW pw) {
        pw.print("break;\n");
    }
}
