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
    public void Expr() throws Exception{
        Token nt = getNextToken();
        switch(nt.type){
            case NUM:
                E();
                eat(Type.EOF);
                break;
            case X:
                E();
                eat(Type.EOF);
                break;
            case MIN:
                E();
                eat(Type.EOF);
                break;
            default:
                Error("unexpected token");

        }
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
                root = new AST.UnaryMin(root);
                break;
            default:
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

      // T` --> T || T` --> $
      public AST.Expr T_prime(AST.Expr left) throws Exception{
        Token nt = getNextToken();
        AST.Expr root = null, right = null;
        switch(nt.type){
            case NUM:
                T();
                break;
            case X:
                T();
                break;
            case ADD:
                break;
            case MIN:
                break;
            case EOF:
                break;
                
                
            default:
                Error("unexpected token");

        }
        
    }
    
    public void F() throws Exception{
        Token nt = getNextToken();
        switch(nt.type){
            case NUM:
                D();
                break;
            case X:
                eat(Type.X);
                F_prime();
                break;
            default:
                Error("unexpected token");

        }
        
    }
    
    public void F_prime() throws Exception{
        Token nt = getNextToken();
        switch(nt.type){
            case NUM:
                break;
            case X:
                break;
            case ADD:
                break;
            case MIN:
                break;
            case HAT:
                eat(Type.HAT);
                D();
            case EOF:
                break;
            default:
                Error("unexpected token");

        }
        
    }
    public void D() throws Exception{
        Token nt = getNextToken();
        switch(nt.type){
            case NUM:
                eat(Type.NUM);
                break;
            
            default:
                Error("unexpected token");

        }
        
    }
    
    
     public void eat(Type type) throws Exception{
        Token nt = getNextToken();
        if(nt.type == type)
            lookhead++;
        else
            Error("unexpected token");
        
    }
       
}
