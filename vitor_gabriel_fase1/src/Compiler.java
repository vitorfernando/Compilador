/*
 Author(s): Vitor Fernando Souza Silva   RA: 552488
 Gabriel Piovani              RA: 552216 
 */

import AST.*;
import Error.CompilerError;
import Lexer.Lexer;
import Lexer.Symbol;
import java.util.*;

/**
 *
 * @author vitor
 */
public class Compiler {

    private Lexer lexer;
    private CompilerError error;
    private char[] input;
    public Hashtable<String, VariableExpr> varTable;
    public Hashtable<String, FuncDef> funcTable;
    // contains the keywords
    static private Hashtable<String, Symbol> keywordsTable;
    // this code will be executed only once for each program execution

    private final String nameInput;

    Compiler(String nameInput) {
        this.nameInput = nameInput;
    }

    public Program compile(char[] p_input) {
        input = p_input;
        funcTable = new Hashtable<String, FuncDef>();
        error = new CompilerError(nameInput);
        lexer = new Lexer(input, error);
        error.setLexer(lexer);
        lexer.nextToken();
        Program result = program();
        if (lexer.token != Symbol.EOF) {
            error.signal("end of file not identify");
        }
        return result;
    }

    //’Program ::= ’program’ Name ’:’ FuncDef {FuncDef} ’end’ 
    private Program program() {
        String name = "";
        ArrayList<FuncDef> arrayFunc = new ArrayList<FuncDef>();

        if (lexer.token == Symbol.PROGRAM) {
            lexer.nextToken();
            if (lexer.token == Symbol.IDENT) {
                name = name();
                if (lexer.token == Symbol.TP) {
                    lexer.nextToken();
                    while (lexer.token == Symbol.DEF) {
                        arrayFunc.add(funcDef());
                    }
                    if (lexer.token == Symbol.END) {
                        lexer.nextToken();
                        return new Program(name, arrayFunc);
                    } else {
                        error.signal("Reached end of file while parsing");
                    }
                } else {
                    error.signal("An : is expected");
                }
            }
        }
        return null;
    }

    //ReturnStmt ::= ’return’ [OrTest]’;’ 
    private Stmt returnStmt() {
        Expr e = null;
        if (lexer.token == Symbol.RETURN) {
            lexer.nextToken();
            if (lexer.token == Symbol.NOT || lexer.token == Symbol.PLUS
                    || lexer.token == Symbol.MINUS || lexer.token == Symbol.IDENT
                    || lexer.token == Symbol.NUMBER || lexer.token == Symbol.STRING
                    || lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE) {
                e = orTest();
            }
            if (lexer.token != Symbol.SEMICOLON) {
                error.signal("missing ;");
            } else {
                lexer.nextToken();
            }
        }
        return new ReturnStmt(e);
    }

    //FuncStmt ::= Name’(’ [OrList] ')';’ 
    private Stmt funcStmt() {
        String name = name();
        Expr e = null;
        if (lexer.token == Symbol.LEFTPAR) {
            lexer.nextToken();
            if (lexer.token == Symbol.NOT || lexer.token == Symbol.PLUS
                    || lexer.token == Symbol.MINUS || lexer.token == Symbol.IDENT
                    || lexer.token == Symbol.NUMBER || lexer.token == Symbol.STRING
                    || lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE) {
                e = orList();
            }
            if (lexer.token == Symbol.RIGHTPAR) {
                lexer.nextToken();
                if (lexer.token == Symbol.SEMICOLON) {
                    lexer.nextToken();
                } else {
                    error.signal("missing ;");
                }
            } else {
                error.signal("missing )");
            }
        }
        return new FuncStmt(name, e);
    }

    // [Declaration] {Stmt}
    private Body body() {
        ArrayList<Stmt> stmtList = new ArrayList<Stmt>();
        Declaration declaration = new Declaration();

        if (lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT
                || lexer.token == Symbol.BOOLEAN
                || lexer.token == Symbol.STRING) {
            declaration = declaration();
        } else if (lexer.token == Symbol.IDENT) {

            lexer.nextToken();
            if (lexer.token == Symbol.IDENT) {
                lexer.backToken();
                error.signal("Word " + lexer.getStringValue() + " nonexistent in language");
                while (lexer.token != Symbol.SEMICOLON) {
                    lexer.nextToken();
                }
                lexer.nextToken();
            }
        }

        stmtList.add(stmt());

        while (lexer.token == Symbol.WHILE || lexer.token == Symbol.FOR || lexer.token == Symbol.IF
                || lexer.token == Symbol.PRINT || lexer.token == Symbol.BREAK
                || lexer.token == Symbol.IDENT || lexer.token == Symbol.RETURN) {
            stmtList.add(stmt());
        }

        return new Body(declaration, stmtList);
    }

    //NameArray ::= Name[‘[’Number‘]’] 
    private VariableExpr nameArray(Symbol type) {
        Variable var = null;
        VariableExpr varExpr = null;
        String name = name();
        int size = -1;
        if (!name.isEmpty()) {
            if (varTable.get(name) != null) {
                error.signal("Variable " + name + " has already been declared");
            }

            var = new Variable(name, type);

            if (lexer.token == Symbol.LEFTBRACKET) {
                lexer.nextToken();
                if (lexer.token == Symbol.NUMBER) {
                    size = lexer.getNumberValue();
                    lexer.nextToken();
                    if (lexer.token != Symbol.RIGHTBRACKET) {
                        error.signal("An ] is expected");
                    } else {
                        lexer.nextToken();
                    }
                }
            }
        }
        var.setSize(size);
        varExpr = new VariableExpr(var);
        varTable.put(name, varExpr);
        return varExpr;
    }

    //IdList ::= NameArray {’,’ NameArray} 
    private ArrayList<VariableExpr> idList(Symbol type) {
        ArrayList<VariableExpr> varArray = new ArrayList<VariableExpr>();

        varArray.add(nameArray(type));
        if ((lexer.token != Symbol.COMMA) && (lexer.token != Symbol.SEMICOLON)) {
            error.signal("symbol " + lexer.token.toString() + " invalid");
            while (lexer.token != Symbol.SEMICOLON) {
                lexer.nextToken();
            }

        }
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            varArray.add(nameArray(type));
        }
        return varArray;
    }

    //Name [ ‘[’Number‘]’ ] {’,’ Name [ ‘[’Number‘]’ ]}
    /*private ArrayList<VariableExpr> idList(Symbol type) {
        ArrayList<VariableExpr> varArray = new ArrayList<VariableExpr>();
        Variable var;
        VariableExpr varExpr;
        String name = name();

        if (!name.isEmpty()) {
            int size = -1;
            if (varTable.get(name) != null) {
                error.signal("Variable " + name + " has already been declared");
            }
            var = new Variable(name, type);
            varExpr = new VariableExpr(var);
            if (lexer.token == Symbol.LEFTBRACKET) {
                lexer.nextToken();
                if (lexer.token == Symbol.NUMBER) {
                    size = lexer.getNumberValue();
                    lexer.nextToken();
                    if (lexer.token != Symbol.RIGHTBRACKET) {
                        error.signal("An ] is expected");
                    } else {
                        lexer.nextToken();
                    }
                }
            }
            var.setSize(size);
            varTable.put(name, varExpr);
            varArray.add(varExpr);

            if (lexer.token == Symbol.COMMA) {
                lexer.nextToken();
                while (lexer.token != Symbol.SEMICOLON) {
                    size = -1;
                    name = name();
                    if (!name.isEmpty()) {
                        if (varTable.get(name) != null) {
                            error.signal("Variable " + name + " has already been declared");
                        }
                        var = new Variable(name, type);
                        varExpr = new VariableExpr(var);

                        if (lexer.token == Symbol.LEFTBRACKET) {
                            lexer.nextToken();
                            if (lexer.token == Symbol.NUMBER) {
                                size = lexer.getNumberValue();
                                var.setSize(size);
                                lexer.nextToken();
                                if (lexer.token != Symbol.RIGHTBRACKET) {
                                    error.signal("An ] is expected");
                                } else {
                                    lexer.nextToken();
                                }
                            }
                        }
                        var.setSize(size);
                        varTable.put(name, varExpr);
                        varArray.add(varExpr);
                    }
                    if (lexer.token == Symbol.COMMA) {
                        lexer.nextToken();
                    }
                }
            }
        }
        return varArray;
    }*/
    //Type IdList {’;’ Type IdList} ’;’
    private Declaration declaration() {
        Declaration declaration = new Declaration();
        ArrayList<VariableExpr> arrayVar = new ArrayList<VariableExpr>();

        Symbol type;

        if (lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT
                || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.STRING) {
            type = type();

            arrayVar = idList(type);

            while (lexer.token == Symbol.SEMICOLON) {
                lexer.nextToken();
                if (lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT
                        || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.STRING) {
                    type = type();
                    arrayVar.addAll(idList(type));
                    if (lexer.token != Symbol.SEMICOLON) {
                        error.signal("An ; is expected");
                    }
                }
            }
            declaration.setArrayVar(arrayVar);
        }

        return declaration;
    }

    // ExprStmt | PrintStmt | BreakStmt | ReturnStmt | FuncStmt 
    private Stmt simpleStmt() {
        if (null != lexer.token) {
            switch (lexer.token) {
                case PRINT:
                    return printStmt();
                case BREAK:
                    return breakStmt();
                case IDENT:
                    lexer.nextToken();
                    if (lexer.token == Symbol.LEFTPAR) {
                        lexer.backToken();
                        return funcStmt();
                    } else {
                        lexer.backToken();
                        return exprStmt();
                    }
                case RETURN:
                    return returnStmt();
                default:
                    break;
            }
        }
        return null;
    }

    // SimpleStmt | CompoundStmt 
    private Stmt stmt() {
        if (null == lexer.token) {
            error.signal("An statment is expected");
        } else //simpleStmt
        {
            switch (lexer.token) {
                case WHILE:
                case FOR:
                case IF:
                    // compound
                    return compoundStmt();
                case PRINT:
                case BREAK:
                case IDENT:
                case RETURN:
                    return simpleStmt();
                default:
                    error.signal("An statment is expected");
                    break;
            }
        }
        return null;
    }

    // ’if’ OrTest ’{’ {Stmt} ’}’ [’else’ ’{’ {Stmt} ’}’]
    private Stmt iftStmt() {
        ArrayList<Stmt> ifStmtList = new ArrayList<Stmt>();
        ArrayList<Stmt> elseStmtList = new ArrayList<Stmt>();
        ArrayList<Expr> orTestList = new ArrayList<Expr>();
        Stmt st1 = null, st2 = null;

        if (lexer.token == Symbol.IF) {
            lexer.nextToken();
            orTestList.add(orTest());
            if (lexer.token == Symbol.LEFTKEYS) {
                lexer.nextToken();
                while (lexer.token == Symbol.WHILE || lexer.token == Symbol.FOR || lexer.token == Symbol.IF
                        || lexer.token == Symbol.PRINT || lexer.token == Symbol.BREAK
                        || lexer.token == Symbol.IDENT) {
                    st1 = stmt();
                    if (st1 != null) {
                        ifStmtList.add(st1);
                    }
                }
                if (lexer.token == Symbol.RIGHTKEYS) {
                    lexer.nextToken();
                    if (lexer.token == Symbol.ELSE) {
                        lexer.nextToken();
                        if (lexer.token == Symbol.LEFTKEYS) {
                            lexer.nextToken();
                            while (lexer.token == Symbol.WHILE || lexer.token == Symbol.FOR || lexer.token == Symbol.IF
                                    || lexer.token == Symbol.PRINT || lexer.token == Symbol.BREAK
                                    || lexer.token == Symbol.IDENT) {
                                st2 = stmt();
                                if (st2 != null) {
                                    elseStmtList.add(st2);
                                }
                            }
                            if (lexer.token == Symbol.RIGHTKEYS) {
                                lexer.nextToken();
                            } else {
                                error.signal("An { is expected");
                            }
                        } else {
                            error.signal("An { is expected");
                        }
                    }
                } else {
                    error.signal("An } aqui is expected");
                }
            } else {
                error.signal("An } is expected");
            }
        }

        return new IfStmt(orTestList, ifStmtList, elseStmtList);
    }

    // ’print’ OrTest {’,’ OrTest}’;’
    private Stmt printStmt() {
        ArrayList<Expr> arrayExpr = new ArrayList<Expr>();
        if (lexer.token == Symbol.PRINT) {
            lexer.nextToken();
            arrayExpr.add(orTest());
            while (lexer.token == Symbol.COMMA) {
                lexer.nextToken();
                arrayExpr.add(orTest());
            }
            if (lexer.token != Symbol.SEMICOLON) {
                error.signal("An ; is expected");
            }
            lexer.nextToken();
            return new PrintStmt(arrayExpr);
        } else {
            error.signal("missing print statment");
        }
        return null;
    }

    //  ’break’ ’;’ 
    private Stmt breakStmt() {
        if (lexer.token == Symbol.BREAK) {
            lexer.nextToken();
            if (lexer.token != Symbol.SEMICOLON) {
                error.signal("An ; is expected");
            } else {
                lexer.nextToken();
            }
        }
        return new BreakStmt();
    }

    //  IfStmt | WhileStmt | ForStmt
    private Stmt compoundStmt() {
        if (lexer.token == Symbol.IF) {
            return iftStmt();
        } else if (lexer.token == Symbol.WHILE) {
            return whileStmt();
        }
        return forStmt();
    }

    // ’for’ Name ’inrange’ ’(’ Number ’,’ Number ’)’ ’{’ {Stmt} ’}’
    private Stmt forStmt() {
        ArrayList<Stmt> stmtList = new ArrayList<Stmt>();
        Stmt st;
        String name = null;
        NumberExpr e1 = null, e2 = null;
        if (lexer.token == Symbol.FOR) {
            lexer.nextToken();
            name = name();
            if (lexer.token != Symbol.INRANGE) {
                error.signal("Inrange is expected");
            } else {
                lexer.nextToken();
            }
            if (lexer.token != Symbol.LEFTPAR) {
                error.signal("missing (");
            } else {
                lexer.nextToken();
            }
            e1 = number();
            if (!e1.isInt()) {
                error.signal("An integer is expected");
            }
            if (lexer.token == Symbol.COMMA) {
                error.signal("missing ,");
            } else {
                lexer.nextToken();
            }
            e2 = number();
            if (!e2.isInt()) {
                error.signal("An integer is expected");
            }
            if (lexer.token != Symbol.RIGHTPAR) {
                error.signal("An ( is expected");
            } else {
                lexer.nextToken();
            }
            if (lexer.token != Symbol.LEFTKEYS) {
                error.signal("An { is expected");
            } else {
                lexer.nextToken();
            }
            while (lexer.token == Symbol.WHILE || lexer.token == Symbol.FOR || lexer.token == Symbol.IF
                    || lexer.token == Symbol.PRINT || lexer.token == Symbol.BREAK
                    || lexer.token == Symbol.IDENT) {
                st = stmt();
                if (st != null) {
                    stmtList.add(st);
                }
            }
            if (lexer.token != Symbol.RIGHTKEYS) {
                error.signal("An } is expected");
            } else {
                lexer.nextToken();
            }
        }
        return new ForStmt(name, e1, e2, stmtList);
    }

    // ’while’ OrTest ’{’ {Stmt} ’}’
    private Stmt whileStmt() {
        ArrayList<Stmt> stmtList = new ArrayList<Stmt>();
        ArrayList<Expr> orTestList = new ArrayList<Expr>();
        Stmt st = null;
        if (lexer.token == Symbol.WHILE) {
            lexer.nextToken();
            orTestList.add(orTest());

            if (lexer.token == Symbol.LEFTKEYS) {
                lexer.nextToken();
                while (lexer.token == Symbol.WHILE || lexer.token == Symbol.FOR || lexer.token == Symbol.IF
                        || lexer.token == Symbol.PRINT || lexer.token == Symbol.BREAK
                        || lexer.token == Symbol.IDENT) {
                    st = stmt();
                    if (st != null) {
                        stmtList.add(st);
                    }
                }
                if (lexer.token == Symbol.RIGHTKEYS) {
                    lexer.nextToken();
                    return new WhileStmt(orTestList, stmtList);
                } else {
                    error.signal("An } is expected");
                }
            } else {
                error.signal("An { is expected");
            }
        }
        return null;
    }

    //Name[ ‘[’Number‘]’ ] ’=’ (OrTest | ’[’ OrList ’]’) ’;’
    private Stmt exprStmt() {
        ArrayList<Expr> arrayExpr = new ArrayList<Expr>();
        int size;
        String name = null;
        int index = -1;

        if (lexer.token != Symbol.IDENT) {
            error.signal("An identifier is expected");
        } else {
            name = name();
            if (lexer.token == Symbol.LEFTBRACKET) {
                lexer.nextToken();
                if (lexer.token == Symbol.NUMBER) {
                    index = lexer.getNumberValue();
                    if ((size = varTable.get(name).getSize()) <= index) {
                        error.signal("Index" + index + " out of bounds of " + name + "[" + size + "]");
                    }
                    lexer.nextToken();
                    if (lexer.token != Symbol.RIGHTBRACKET) {
                        error.signal("missing ]");
                    } else {
                        lexer.nextToken();
                    }
                }
            }
            if (varTable.get(name) == null) {
                error.signal("Variable " + name + "not declared or word nonexistent in language");
            }

            if (lexer.token == Symbol.ASSIGN) {
                lexer.nextToken();
                if (lexer.token == Symbol.LEFTBRACKET) {
                    lexer.nextToken();
                    arrayExpr.add(orList());
                    if (lexer.token == Symbol.RIGHTBRACKET) {
                        lexer.nextToken();
                    } else {
                        error.signal("missing ]");
                    }
                } else {

                    arrayExpr.add(orTest());
                }
                if (lexer.token == Symbol.SEMICOLON) {
                    lexer.nextToken();
                } else {
                    error.signal("missing ;");
                }
            } else {
                error.signal("missing =");
            }
        }
        for (int i = 0; i < arrayExpr.size(); i++) {
            if (!arrayExpr.get(i).getType(varTable.get(name).getType())) {
                error.signal("Different types can not be attributed");
            }
        }
        return new ExprStmt(name, index, arrayExpr);
    }

    //ExprList ::= Expr {, Expr}
    private ExprList exprList() {
        ArrayList<Expr> exprArray = new ArrayList<Expr>();
        Expr e = expr();

        if (e != null) {
            exprArray.add(e);
        }
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            e = expr();
            if (e != null) {
                exprArray.add(e);
            }
        }

        return new ExprList(exprArray);
    }

    //OrTest ::= AndTest {’or’ AndTest}
    private Expr orTest() {
        Expr left, right = null;
        Symbol op;

        left = andTest();
        while ((op = lexer.token) == Symbol.OR) {
            lexer.nextToken();
            right = andTest();
            left = new CompositeExpr(left, op, right);
        }

        return left;
    }

    //AndTest ::= NotTest {’and’ NotTest}
    private Expr andTest() {
        Expr left, right = null;
        Symbol op;
        left = notTest();
        while ((op = lexer.token) == Symbol.AND) {
            lexer.nextToken();
            right = notTest();
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    //NotTest ::= [’not’] Comparison
    private Expr notTest() {
        if (lexer.token == Symbol.NOT) {
            lexer.nextToken();
        }

        return comparison();
    }

    // " . "’ 
    private StringExpr string() {
        String str = lexer.getStringValue();
        lexer.nextToken();
        return new StringExpr(str);
    }

    // Expr [CompOp Expr] 
    private Expr comparison() {
        Expr left, right = null;
        Symbol op;

        left = expr();
        if (lexer.token == Symbol.LT || lexer.token == Symbol.GT || lexer.token == Symbol.EQ
                || lexer.token == Symbol.GE || lexer.token == Symbol.LE || lexer.token == Symbol.LG) {
            op = compOP();
            right = expr();
            left = new CompositeExpr(left, op, right);
        }
        if(lexer.token == Symbol.INVALID){
            op = lexer.token;
            lexer.nextToken();
            right = expr();
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    // Term {(’+’ | ’-’) Term} 
    private Expr expr() {
        Symbol op = null;
        Expr left, right;
        left = term();
        while ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) {
            lexer.nextToken();
            right = term();
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    //Factor {(’*’ | ’/’) Factor} 
    private Expr term() {
        Symbol op = null;
        Expr left, right;

        left = factor();
        while ((op = lexer.token) == Symbol.MULT || op == Symbol.DIV) {
            lexer.nextToken();
            right = factor();
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    //AtomExpr ::= Atom [Details] 
    private AtomExpr atomExpr() {
        Expr e = atom(), d = null;

        if (lexer.token == Symbol.LEFTBRACKET || lexer.token == Symbol.LEFTPAR) {
            d = details(e);
        }

        return new AtomExpr(e, d);
    }

    //Atom ::= Name | Number | String | ’True’ | ’False’
    private Expr atom() {
        Details d = null;
        switch (lexer.token) {
            case IDENT:
                String name = name();
                if (varTable.get(name) != null) {
                    return varTable.get(name);
                } else {
                    if (funcTable.get(name) != null) {
                        return new FuncExpr(funcTable.get(name));
                    }
                }
                return null;
            case NUMBER:
                return number();
            case STRING:
                return string();
            case TRUE:
            case FALSE:
                BooleanExpr bexp = new BooleanExpr(lexer.getStringValue());
                lexer.nextToken();
                return bexp;
            default:
                break;
        }
        return null;
    }

    //Details ::= ‘[’(Number | Name)‘]’ | ’(’ [OrList] ’)’ 
    private Expr details(Expr e) {
        Details d = null;

        if (lexer.token == Symbol.LEFTBRACKET) {
            lexer.nextToken();
            if (lexer.token == Symbol.NUMBER) {
                NumberExpr numberExpr = number();
                d = new Details(numberExpr);
                VariableExpr varExpr = (VariableExpr) e;
                if (varExpr.getSize() < Integer.parseInt(numberExpr.getValue())) {
                    error.signal("out of bounds");
                }
            } else if (lexer.token == Symbol.IDENT) {
                d = new Details(new StringExpr(name()));
            }

            if (lexer.token != Symbol.RIGHTBRACKET) {
                error.signal("missing ]");
            } else {
                lexer.nextToken();
            }
        } else if (lexer.token == Symbol.LEFTPAR) {
            lexer.nextToken();
            if (lexer.token == Symbol.NOT || lexer.token == Symbol.PLUS
                    || lexer.token == Symbol.MINUS || lexer.token == Symbol.IDENT
                    || lexer.token == Symbol.NUMBER || lexer.token == Symbol.STRING
                    || lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE) {
                d = new Details(orList());
            }
            if (lexer.token != Symbol.RIGHTPAR) {
                error.signal("missing )");
            } else {
                lexer.nextToken();
            }
        }

        return d;
    }

    //OrList ::= OrTest {’,’ OrTest}
    private Expr orList() {
        ArrayList<Expr> orArray = new ArrayList<Expr>();
        Expr e = orTest();

        if (e != null) {
            orArray.add(e);
        }
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            e = orTest();
            if (e != null) {
                orArray.add(e);
            }
        }

        return new OrList(orArray);
    }

    //  Factor ::= [Signal] AtomExpr {’^’ Factor}   
    private Expr factor() {
        Expr left, right = null;

        Symbol op = null;
        if (lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS) {
            op = lexer.token;
            lexer.nextToken();
        }

        left = atomExpr();
        while (lexer.token == Symbol.POW) {
            lexer.nextToken();
            right = factor();
            left = new FactorExpr(op, left, right);
        }
        return left;
    }

    //FuncDef ::= ’def’ Name ’(’ [ArgsList] ’)’ : Type ’{’Body’}’ 
    private FuncDef funcDef() {
        FuncDef funcDef = null;
        ArrayList<ArgExpr> argsList = new ArrayList<ArgExpr>();
        varTable = new Hashtable<String, VariableExpr>();
        Symbol type;
        if (lexer.token == Symbol.DEF) {
            lexer.nextToken();
            String name = name();
            if (lexer.token == Symbol.LEFTPAR) {
                lexer.nextToken();
                if (lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT
                        || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.VOID
                        || lexer.token == Symbol.STRING) {
                    argsList = argsList();
                }
                if (lexer.token != Symbol.RIGHTPAR) {
                    error.signal("missing )");
                } else {
                    lexer.nextToken();
                    if (lexer.token != Symbol.TP) {
                        error.signal("missing :");
                    } else {
                        lexer.nextToken();
                        if (lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT
                                || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.VOID
                                || lexer.token == Symbol.STRING) {
                            type = type();
                            if (lexer.token != Symbol.LEFTKEYS) {
                                error.signal("missing {");
                            } else {
                                lexer.nextToken();
                            }
                            Body body = body();
                            if (!body.checkReturn()) {
                                error.signal("function " + name + " should have a return statment");
                            } else if (!body.checkReturnType(type)) {
                                error.signal("incompative types between return statment and return type of function " + name);
                            }
                            if (lexer.token != Symbol.RIGHTKEYS) {
                                error.signal("missing }");
                            } else {
                                lexer.nextToken();
                            }

                            funcDef = new FuncDef(name, argsList, type, body);
                            funcTable.put(name, funcDef);
                        }
                    }

                }

            }
        } else {
            error.signal("missing");
        }
        return funcDef;
    }

    //ArgsList ::= Type NameArray {’,’ Type NameArray} 
    private ArrayList<ArgExpr> argsList() {
        ArrayList<ArgExpr> argsList = new ArrayList<ArgExpr>();
        Symbol type = type();
        VariableExpr vexpr = nameArray(type);

        argsList.add(new ArgExpr(type, vexpr));
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            type = type();
            vexpr = nameArray(type);
            argsList.add(new ArgExpr(type, vexpr));
        }
        return argsList;
    }

    // Type ::= ’int’ | ’ﬂoat’ | ’string’ | ’boolean’ | ’void’
    private Symbol type() {
        Symbol type;
        if (lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT
                || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.VOID
                || lexer.token == Symbol.STRING) {
            type = lexer.token;
            lexer.nextToken();
        } else {
            error.signal("unrecognized type");
            return null;
        }
        return type;
    }

    // CompOp ::= ’<’ | ’>’ | ’==’ | ’>=’ | ’<=’ | ’<>’
    private Symbol compOP() {
        Symbol op = null;
        if (lexer.token == Symbol.LT || lexer.token == Symbol.GT || lexer.token == Symbol.EQ
                || lexer.token == Symbol.GE || lexer.token == Symbol.LE || lexer.token == Symbol.LG) {
            op = lexer.token;
            lexer.nextToken();
        } else {
            error.signal("oper not recognized");
        }
        return op;
    }

    // [Signal] Digit{Digit} [’.’ Digit{Digit}] 
    private NumberExpr number() {
        Symbol signal = Symbol.PLUS;
        if (lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS) {
            signal = signal();
        }
        if (lexer.token != Symbol.NUMBER) {
            error.signal("Number is expected");
        } else {
            //store the first digit of the number
            String dig1 = "" + digit();
            lexer.nextToken();
            while (lexer.token == Symbol.NUMBER) {
                dig1 = dig1 + digit();
                lexer.nextToken();
            }

            if (lexer.token == Symbol.DOT) {
                lexer.nextToken();
                String dig2 = "" + digit();
                lexer.nextToken();
                while (lexer.token == Symbol.NUMBER) {
                    dig2 = dig2 + digit();
                    lexer.nextToken();
                }
                return new NumberExpr(signal, Integer.valueOf(dig1), Integer.valueOf(dig2));
            } else {
                return new NumberExpr(signal, Integer.valueOf(dig1));
            }
        }
        return null;
    }

    //= Letter{Letter | Digit}//
    private String name() {
        String name = "";
        if (lexer.token == Symbol.IDENT) {
            name = lexer.getStringValue();
            lexer.nextToken();
            while (lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER) {
                if (lexer.token == Symbol.IDENT) {
                    name = name + lexer.getStringValue();
                } else {
                    name = name + lexer.getNumberValue2();
                }
                lexer.nextToken();
            }
        }
        return name;
    }

    //Signal ::= ’+’ | ’-’
    private Symbol signal() {
        if (lexer.token == Symbol.PLUS) {
            lexer.nextToken();
            return Symbol.PLUS;
        }
        lexer.nextToken();
        return Symbol.MINUS;
    }

    //’0’ | ... | ’9’
    private int digit() {
        if (lexer.token != Symbol.NUMBER) {
            error.signal("expect a digit");
            return 0;
        } else {
            return lexer.getNumberValue();
        }
    }
}
