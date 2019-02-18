package polynomial;


import java.util.*;
public class Polynomial
{
 private LinkedList<Integer> coefficients;
 

 public Polynomial()
 {
   coefficients = new LinkedList<Integer>();
 }
  public Polynomial(int size)
 {
   coefficients = new LinkedList<Integer>();
   for (int p=0; p<=size; p++)
     coefficients.addLast(0);
 }
  public Polynomial(int[] coeffs)
 {
   coefficients = new LinkedList<Integer>();
   for (int i = 0; i<coeffs.length; i++)
     coefficients.addFirst(coeffs[i]);
 }

public int getSize()
 {
   return coefficients.size()-1;
 }



 private boolean isZero()
 {
   for (int i = getSize(); i>=0; i--)
   {
     if (coefficients.get(i) != 0)
       return false;
   }
   return true;
 }




 public void outputDecreasingOrder()
 {
   int pow = getSize();
   String output = "";
   while (pow >= 0)
   {
     if (coefficients.get(pow) == 0);
     else
     {
       if (coefficients.get(pow) >= 0)
         output += " + ";
       else
         output += " - ";
      
       if (pow == 0)
         output += (Math.abs(coefficients.get(pow)));
       else if (pow == 1)
         output += (Math.abs(coefficients.get(pow)) + "X");
       else
         output += (Math.abs(coefficients.get(pow)) + "X^" + pow);
     }
     pow--;
   }
  
   if (isZero())
     output = "0";
   else if (output.charAt(1) != '-')
     output = output.substring(3, output.length());
   else
     output = output.substring(1, output.length());   
   System.out.println(output);
 }


  public void outputIncreasingOrder()
 {
   int pow = 0;
   String output = "";
   while (pow <= getSize())
   {
     if (coefficients.get(pow) == 0);
     else
     {
       if (coefficients.get(pow) >= 0)
         output += " + ";
       else
         output += " - ";
      
       if (pow == 0)
         output += (Math.abs(coefficients.get(pow)));
       else if (pow == 1)
         output += (Math.abs(coefficients.get(pow)) + "X");
       else
         output += (Math.abs(coefficients.get(pow)) + "X^" + pow);
     }
     pow++;
   }
  
   if (isZero())
     output = "0";
   else if (output.charAt(1) != '-')
     output = output.substring(3, output.length());
   else
     output = output.substring(1, output.length());
  
   System.out.println(output);
 }


  public double evaluate(double x)
 {
   int pow = getSize();
   double result = 0.0;

   while (pow >= 0)
   {
     if (coefficients.get(pow) != 0);
     {
       result += (coefficients.get(pow) * Math.pow(x, pow));
     }
     pow--;
   }
  
  
   return result;
 }



 public static Polynomial sum(Polynomial poly1, Polynomial poly2)
 {
   Polynomial res = new Polynomial();
  
   int size1 = poly1.getSize();
   int size2 = poly2.getSize();
  
   int index1 = 0;
   while (size1 > size2)
   {
     res.coefficients.addLast(poly1.coefficients.get(index1));
     index1++;
     size1--;
   }
  
   int index2 = 0;
   while (size2 > size1)
   {
     res.coefficients.addLast(poly2.coefficients.get(index2));
     index2++;
     size2--;
   }
  
   size1 = poly1.getSize();
   while (index1 <= size1)
   {
     res.coefficients.addLast(poly1.coefficients.get(index1) + poly2.coefficients.get(index2));
     index1++;
     index2++;
   }
  
   return res;
 }
   public static Polynomial difference(Polynomial poly1, Polynomial poly2)
 {
   Polynomial res = new Polynomial();
  
   int size1 = poly1.getSize();
   int size2 = poly2.getSize();
  
   int index1 = 0;
   while (size1 > size2)
   {
     res.coefficients.addLast(poly1.coefficients.get(index1));
     index1++;
     size1--;
   }
  
   int index2 = 0;
   while (size2 > size1)
   {
     res.coefficients.addLast(-1 * poly2.coefficients.get(index2));
     index2++;
     size2--;
   }
  
   size1 = poly1.getSize();
   while (index1 <= size1)
   {
     res.coefficients.addLast(poly1.coefficients.get(index1) - poly2.coefficients.get(index2));
     index1++;
     index2++;
   }
  
   return res;
 }
 
 public static Polynomial multiply(Polynomial poly, int val)
 {
   Polynomial res = new Polynomial();
  
   int size = poly.getSize();
  
   int index = 0;
   while (index <= size)
   {
     res.coefficients.addLast(val * poly.coefficients.get(index));
     index++;
   }
  
   return res;
 }
}