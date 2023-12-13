/**
 * Ciaran Engelbrecht 23169641
 * Equation represents a Bydysawd chemical equation. 
 * An equation can have multiple formulas on each side, 
 * e.g. X3 + Y2Z2 = ZX + Y2X2 + Z. 
 *
 * @author Lyndon While
 * @version 2021
 */
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class Equation
{
    // the two sides of the equation 
    // there can be multiple formulas on each side 
    private ArrayList<Formula> lhs, rhs;

    /**
     * Parses s to construct an equation. s will contain a 
     * syntactically legal equation, e.g. X3 + Y2Z = ZX + Y2X4. 
     * s may contain whitespace between formulas and symbols. 
     */
    public Equation(String s)
    {
        String s1 = s.replaceAll(" \\= ","\\=");
        int eq = s1.indexOf('=');
        lhs = parseSide(s1.substring(0, eq));
        rhs = parseSide(s1.substring(eq + 1));
    }

    /**
     * Returns the left-hand side of the equation.
     */
    public ArrayList<Formula> getLHS()
    { 
        return lhs;
    }

    /**
     * Returns the right-hand side of the equation.
     */
    public ArrayList<Formula> getRHS()
    {
        return rhs;
    }
    
    /**
     * Returns the indices at which x occurs in s, 
     * e.g. indicesOf("ax34x", 'x') returns <1,4>. 
     */
    public static ArrayList<Integer> indicesOf(String s, char x)
    {
        ArrayList<Integer> indice = new ArrayList<Integer>();
        for(int i = 0; i < s.length(); i++){
        if(s.charAt(i) == x){
        indice.add(i);}
        }
       return indice;
    }
    
    /**
     * Parses s as one side of an equation. 
     * s will contain a series of formulas separated by pluses, 
     * and it may contain whitespace between formulas and symbols. 
     */
    public static ArrayList<Formula> parseSide(String s)
    {
          ArrayList<Formula> side = new ArrayList<>();
          String s1 = s.replaceAll(" \\+ ","\\+");
          String[] array = s1.split("\\+");
          for(int i = 0; i < array.length; i++){
              side.add(new Formula(array [i]));}
       return side;
    }

    /**
     * Returns true iff the equation is balanced, i.e. it has the 
     * same number of atoms of each Bydysawd element on each side. 
     */
    public boolean isValid()
    {
        ArrayList<Term> l = new ArrayList<>();
        ArrayList<Term> r = new ArrayList<>();
        for (Formula f : lhs)
            l.addAll(f.getTerms());
        for (Formula f : rhs)
            r.addAll(f.getTerms());
        Formula left = new Formula(l); Formula right = new Formula(r);
        left.standardise(); right.standardise();
          if ((left.display()).equals(right.display())) {
              return true;}
        else return false;
    }

    /**
     * Returns the equation as a String.
     */
    public String display()
    {
        String sl = "";
        for (Formula f : lhs)
            sl += " + " + f.display();
        String sr = ""; 
        for (Formula f : rhs)
            sr += " + " + f.display();
        return sl.substring(3) + " = " + sr.substring(3);
    }
}
