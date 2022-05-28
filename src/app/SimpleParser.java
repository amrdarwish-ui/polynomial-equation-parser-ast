/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author M&G
 */
public class SimpleParser {
     private String msg;
    private int lookhead;
    public  class Token{
        String symbol;
        Type type;
        public Token(String symbol,Type type){
            this.symbol = symbol;
            this.type = type;
        }
        
    }
    public static  enum Type{NUM,X,ADD,MIN,HAT,EOF}
    public SimpleParser(String msg){
        this.msg = msg;
        this.lookhead = 0;
    }
    public Token getNextToken()throws Exception{
        if(lookhead >= msg.length())
            return  new Token("", Type.EOF);
        char ch =msg.charAt(lookhead);
        if(ch>='0'&&ch<='9')
            return  new Token(ch+"", Type.NUM);
        if(ch=='x')
            return  new Token("x", Type.X);
        if(ch=='+')
            return  new Token("+", Type.ADD);
        if(ch=='-')
            return  new Token("-", Type.MIN);
        if(ch=='^')
            return  new Token("^", Type.HAT);
        Error("unexpected token");
        return  null;
    }
    public void Error(String msg) throws Exception{
        throw new Exception(msg);
    }
    
    
    // Expr ---> E $
    public AST.Expr Expr() throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null;
        switch(nt.type){
            case NUM:
                root = E();
                eat(Type.EOF);
                break;
            case X:
                root = E();
                eat(Type.EOF);
                break;
            case MIN:
                root = E();
                eat(Type.EOF);
                break;
            default:
                Error("unexpected token");
        }
        return root;
    }
    
    
    // E --> T E` || E--> - E
    public AST.Expr E() throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null, left = null;
        switch(nt.type){
            case NUM:
                left = T();
                root = E_prime(left);
                break;
            case X:
                left = T();
                root = E_prime(left);
                break;
            case MIN:
                eat(Type.MIN);
                root = E();
                root = new AST.MinExpr(left, root);
                break;
            default:
                System.out.println("The token is:" + nt.symbol);
                Error("unexpected token");
        }
        return root;
    }


    // E` --> + T E` || E` --> - T E` || E` --> $ (lamda)
    public AST.Expr E_prime(AST.Expr left) throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null, right = null;
        switch(nt.type){
            case ADD:
                eat(Type.ADD);
                right = T();
                left = new AST.AddExpr(left, right);
                root = E_prime(left);
                break;
            case MIN:
                eat(Type.MIN);
                right = T();
                left = new AST.MinExpr(left, right);
                root = E_prime(left);
                break;
            case EOF:
                root = left;
                break;
            default:
                Error("unexpected token");
        }
        return root;
    }


    // T --> F T`
    public AST.Expr T() throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null, left = null;
        switch(nt.type){
            case NUM:
                left = F();
                root = T_prime(left);
                break;
            case X:
                left = F();
                root = T_prime(left);
                break;
            default:
                Error("unexpected token");
        }
        return root;
    }

      // T` --> F T` || T` --> $
      public AST.Expr T_prime(AST.Expr left) throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null, right = null;
        switch(nt.type){
            case NUM:
                right = F();
                left = new AST.NumExpr(nt.symbol);
                root = T_prime(left);
                break;
            case X:
                right = F();
                left = new AST.XExpr(left, right);
                root = T_prime(left);
                break;
            case ADD:
                root = left;
                break;
            case MIN:
                root = left;
                break;
            case EOF:
                root = left;
                break;
            default:
                Error("unexpected token");
        }
        return root;
    }
    
    // F --> D || F --> x F`
    public AST.Expr F() throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null, left = null;
        switch(nt.type){
            case NUM:
                left = D();
                root = left;
                break;
            case X:
                eat(Type.X);
                left = new AST.NumExpr(nt.symbol);
                root = F_prime(left);
                break;
            default:
                Error("unexpected token");
        }
        return root;
    }
    
    // F` --> ^ D || F` --> $
    public AST.Expr F_prime(AST.Expr left) throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null, right = null;
        switch(nt.type){
            case NUM:
                root = left;
                break;
            case X:
                root = left;
                break;
            case ADD:
                root = left;
                break;
            case MIN:
                root = left;
                break;
            case HAT:
                eat(Type.HAT);
                right = D();
                root = new AST.HatExpr(left, right);
                break;
            case EOF:
                root = left;
                break;
            default:
                Error("unexpected token");
        }
        return root;
    }
    // D --> {0, 1, ..., 9}
    public AST.Expr D() throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null;
        switch(nt.type){
            case NUM:
                eat(Type.NUM);
                root = new AST.NumExpr(nt.symbol);
                break;
            default:
                Error("unexpected token");
        }
        return root;
    }
    
    
     public void eat(Type type) throws Exception{
        Token nt = getNextToken();
        if(nt.type == type)
            lookhead++;
        else
            Error("unexpected token");
        
    }
       
}
