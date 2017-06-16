/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package AST;

import Lexer.Symbol;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author vitor
 */
public class Declaration {

    private ArrayList<VariableExpr> arrayVar;
 
    public Declaration() {
        this.arrayVar = new ArrayList<VariableExpr>();
    }

    public void setArrayVar(ArrayList<VariableExpr> arrayVar) {
        this.arrayVar = arrayVar;
    }

    public boolean isEmpty() {
        if (arrayVar.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean checkStringType() {
        if (arrayVar != null) {
            for (int i = 0; i < arrayVar.size(); i++) {
                if (arrayVar.get(i).getType() == Symbol.STRING) {
                    return true;
                }
            }
        }
        return false;
    }

    public void genC(PW pw) {
        Symbol type;
        int size;
        for (int i = 0; i < arrayVar.size(); i++) {
            type = arrayVar.get(i).getType();
            if (type == Symbol.INT) {
                pw.out.print("\tint ");
                pw.out.print(arrayVar.get(i).getName());
                i++;
                while (i < arrayVar.size() && (type = arrayVar.get(i).getType()) == Symbol.INT) {
                    pw.out.print("," + arrayVar.get(i).getName());
                    if ((size = arrayVar.get(i).getSize()) > 0) {
                        pw.out.print("[" + size + "]");
                    }
                    i++;
                }
                pw.out.println(";");
            } else if (type == Symbol.FLOAT) {
                pw.out.print("\tfloat ");
                pw.out.print(arrayVar.get(i).getName());
                i++;
                while (i < arrayVar.size() &&(type = arrayVar.get(i).getType()) == Symbol.FLOAT) {
                    pw.out.print("," + arrayVar.get(i).getName());
                    if ((size = arrayVar.get(i).getSize()) > 0) {
                        pw.out.print("[" + size + "]");
                    }
                    i++;
                }
                pw.out.println(";");
            } else if (type == Symbol.BOOLEAN) {
                pw.out.print("bool ");
                pw.out.print(arrayVar.get(i).getName());
                i++;
                while (i < arrayVar.size() &&(type = arrayVar.get(i).getType()) == Symbol.BOOLEAN) {
                    pw.out.print("," + arrayVar.get(i).getName());
                    if ((size = arrayVar.get(i).getSize()) > 0) {
                        pw.out.print("[" + size + "]");
                    }
                    i++;
                }
                pw.out.println(";");
            } else if (type == Symbol.STRING) {
                pw.out.print("\tchar ");
                pw.out.print(arrayVar.get(i).getName());
                i++;
                while (i < arrayVar.size() &&(type = arrayVar.get(i).getType()) == Symbol.STRING) {
                    pw.out.print("," + arrayVar.get(i).getName());
                    if ((size = arrayVar.get(i).getSize()) > 0) {
                        pw.out.print("[" + size + "]");
                    }
                    i++;
                }
                pw.out.println(";");
            }
        }
    }
}