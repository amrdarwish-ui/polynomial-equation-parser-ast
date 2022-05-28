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
        public abstract String firstDriv();
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

        public String firstDriv() {
            if(val == "x")
                return "1";
            return "0";
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

        public String firstDriv() {
            //return left.firstDriv()+"+"+right.firstDriv();

            String leftDriv = this.left.firstDriv();
            String rightDriv = this.right.firstDriv();
            if (leftDriv.equals("0") && !rightDriv.isEmpty()) {
                return rightDriv;
            } else if (!leftDriv.isEmpty() && rightDriv.equals("0")) {
                return leftDriv;
            }
            return left.firstDriv()+"+"+right.firstDriv();
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

        public String firstDriv(){

            if (left == null)
                return "-" + right.firstDriv();
            String leftDriv = this.left.firstDriv();
            String rightDriv = this.right.firstDriv();
            if (leftDriv.equals("0") && !rightDriv.isEmpty()) {
                return  "-" + rightDriv;
            } else if (!leftDriv.isEmpty() && rightDriv.equals("0")) {
                return leftDriv;
            }
            return left.firstDriv()+"-"+ right.firstDriv();
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

        public String firstDriv() {
            if (Integer.parseInt(right.toString()) == 2)
                return right.toString() + left ;
            return right.toString() + left + "^" + (Integer.valueOf(right.toString())-1) ;
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

        public String firstDriv() {
            if (left!= null) {
                //return String.valueOf(Integer.valueOf(left.toString()) * Integer.valueOf(this.right.firstDriv())) ;
                //return left + this.right.firstDriv() ;

                if (this.right.toString().contains("^")) {
                    String rightDriv = this.right.firstDriv();
                    int XCoefficient = Character.getNumericValue(rightDriv.charAt(0));
                    rightDriv = rightDriv.substring(1);
                    XCoefficient = (Integer.parseInt(left.toString()) * XCoefficient);
                    return XCoefficient + rightDriv;
                } else {
                    return String.valueOf(Integer.valueOf(left.toString()) * Integer.valueOf(this.right.firstDriv()));
                }
            }
            return this.right.firstDriv() ;
        }

        public String toString() {
            return this.left.toString() + "" + this.right.toString();
        }
    }
}
