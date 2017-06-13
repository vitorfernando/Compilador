/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package Error;
import Lexer.*;
import java.io.*;

public class CompilerError {
    
    private Lexer lexer;
    PrintWriter out;
    private String fileName;
    
    public CompilerError(String fileName) { // output of an error is done in out
        this.fileName = fileName;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    public void signal(String strMessage) {
        System.out.println(fileName + "," + lexer.getLineNumber() + ", " + strMessage);
    }
    
}