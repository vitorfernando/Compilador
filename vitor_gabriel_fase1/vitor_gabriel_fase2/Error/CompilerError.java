/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Error;
import Lexer.*;
import java.io.*;

public class CompilerError {

    public CompilerError(String fileName) { // output of an error is done in out
        this.fileName = fileName;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    public void signal(String strMessage) {
        System.out.println(fileName + "," + lexer.getLineNumber() + ", " + strMessage);
        if (System.out.checkError()) {
            System.out.println("Error in signaling an error");
        }
    }
    private Lexer lexer;
    PrintWriter out;
    private String fileName;
}