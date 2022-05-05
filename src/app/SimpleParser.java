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
            default:
                Error("unexpected token");

        }
    }
    
    
    public void E() throws Exception{
        Token nt = getNextToken();
        switch(nt.type){
            case NUM:
                T();
                E_prime();
                break;
            case X:
                T();
                E_prime();
                break;
            default:
                Error("unexpected token");

        }
        
    }
    
 
     public void E_prime() throws Exception{
        Token nt = getNextToken();
        switch(nt.type){
            case ADD:
                eat(Type.ADD);
                E();
                break;
            case MIN:
                eat(Type.MIN);
                E();
                break;
            case EOF:
                break;
            default:
                Error("unexpected token");

        }
        
    }
     
     
      public void T() throws Exception{
        Token nt = getNextToken();
        switch(nt.type){
            case NUM:
                F();
                T_prime();
                break;
            case X:
                F();
                T_prime();
                break;
            default:
                Error("unexpected token");

        }
        
    }
      
      //T_prime=>$        T_prime => *T
      public void T_prime() throws Exception{
        Token nt = getNextToken();
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
