/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package AST;

import Lexer.Symbol;

/**
 *
 * @author vitor
 */
abstract public class Expr {
    abstract public void genC(PW pw);
    abstract public boolean getType(Symbol type);
}
