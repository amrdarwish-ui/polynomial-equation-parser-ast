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
        public abstract String prefix();
        public abstract String postfix();
    }

    public static class NumExpr extends Expr {
        String val;

        public NumExpr(String val) {
            this.val = val;
        }

        public String prefix() {
            return this.val + "";
        }

        public String postfix() {
            return this.val + "";
        }

        public String toString() {
            return this.val + "";
        }
    }

    public static class AddExpr extends Expr {
        Expr left, right;

        public AddExpr(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }

        public String prefix() {
            return "+ " + this.left.prefix() + " " + this.right.prefix();
        }

        public String postfix() {
            return this.left.postfix() + " " + this.right.postfix() + " +";
        }

        public String toString() {
            return this.left.toString() + "+" + this.right.toString();
        }
    }

    public static class MinExpr extends Expr {
        Expr left, right;

        public MinExpr(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }

        public String prefix() {
            if (left == null)
                return "- " + this.right.prefix();
            return "- " + this.left.prefix() + " " + this.right.prefix();
        }

        public String postfix() {
            if (left == null)
                return this.right.postfix() + " -";
            return this.left.postfix() + " " + this.right.postfix() + " -";
        }

        public String toString() {
            if (left == null)
                return "-" + this.right.toString();
            return this.left.toString() + "-" + this.right.toString();
        }
    }

    public static class HatExpr extends Expr {
        Expr left;
        Expr right;

        public HatExpr(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }

        public String prefix() {
            return "^ " + this.left.prefix() + " " + this.right.prefix();
        }

        public String postfix() {
            return this.left.postfix() + " " + this.right.postfix() + " ^";
        }

        public String toString() {
            return this.left.toString() + "^" + this.right.toString();
        }
    }

    public static class XExpr extends Expr {
        Expr left;
        Expr right;

        public XExpr(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }

        public String prefix() {
            return this.left.prefix() + " " + this.right.prefix();
        }

        public String postfix() {
            return this.left.postfix() + " " + this.right.postfix();
        }

        public String toString() {
            return this.left.toString() + "" + this.right.toString();
        }
    }
}
