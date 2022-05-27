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
public class App {
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
         String msg = "-2+3x-x^4+5";
        SimpleParser simpleParser = new SimpleParser(msg);
        //simpleParser.Expr();
        AST.Expr root = simpleParser.Expr();
        System.out.println("toString: " + root);
        System.out.println("prefix: " + root.prefix());
        System.out.println("postfix: " + root.postfix());
        System.out.println("Success");
    }
    
}
