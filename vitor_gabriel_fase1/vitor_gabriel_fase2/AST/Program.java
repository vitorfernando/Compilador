/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package AST;

public class Program {

    private String name;
    private Body body;

    public Program(String name, Body body_st) {
        this.name = name;
        this.body = body_st;
    }

    public void genC(PW pw) {
        pw.out.println("#include <stdio.h>");
        if (body != null) {
            if (body.checkStringType()) {
                pw.out.println("#include <string.h>");
            }
        }
        pw.out.println("\nint main(){");
        if (body != null){
            body.genC(pw);
        }
        pw.out.println("\treturn 0;");
        pw.out.println("}");
    }

}
