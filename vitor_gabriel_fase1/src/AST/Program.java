/*
    Author(s): Vitor Fernando Souza Silva   RA: 552488
               Gabriel Piovani              RA: 552216 
*/
package AST;

import java.util.ArrayList;

public class Program {

    private String name;
    private ArrayList<FuncDef> arrayFunc;

    public Program(String name,ArrayList<FuncDef> arrayFunc) {
        this.name = name;
        this.arrayFunc = arrayFunc;
    }

    public void genC(PW pw) {
        pw.out.println("#include <stdio.h>");
        if (arrayFunc != null) {
            for(int i = 0; i < arrayFunc.size(); i++){
                if(arrayFunc.get(i).checkStringType()){
                    pw.out.println("#include <string.h>");
                    break;
                }
            }
        }
        for(int i = 0; i < arrayFunc.size(); i++){
            arrayFunc.get(i).genC(pw);
        }/*
        pw.out.println("\nint main(){");
        if (arrayFunc != null){
            arrayFunc.genC(pw);
        }
        pw.out.println("\treturn 0;");
        pw.out.println("}");*/
    }

}
