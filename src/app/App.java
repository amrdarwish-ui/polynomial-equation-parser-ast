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
         String msg = "-x^6";
        SimpleParser simpleParser = new SimpleParser(msg);
        simpleParser.Expr();
        System.out.println("Success");
    }
    
}
