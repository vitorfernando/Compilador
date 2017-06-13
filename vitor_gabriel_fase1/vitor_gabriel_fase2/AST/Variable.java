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
public class Variable {
    private String name;
    private Symbol type;
    private int size;
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Symbol getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Variable(String name, Symbol type){
        this.name = name;
        this.type = type;
    }
}
