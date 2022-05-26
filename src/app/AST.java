/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author osman
 */
public class AST {
    public static abstract class Expr {
        
    }
    
    public static class NumExpr extends Expr {
        int val;
        public NumExpr(int val) {
            this.val = val;
        }
    }
    
    public static class AddExpr extends Expr {
        Expr left, right;
        public AddExpr(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class MinExpr extends Expr {
        Expr left, right;
        public MinExpr(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class HatExpr extends Expr {
        String left;
        int right;
        public HatExpr(String left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class UnaryMin extends Expr {
        Expr right;
        public UnaryMin(Expr right) {
            this.right = right;
        }
    }
}
