/**
 * Ciaran Engelbrecht 23169641
 * Formula represents a Bydysawd chemical formula. 
 * A formula is a sequence of terms, e.g. AX3YM67. 
 *
 * @author Lyndon While
 * @version 2021
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Formula
{
    // the constituent terms of the formula
    private ArrayList<Term> terms;

    /**
     * Makes a formula containing a copy of terms.
     */
    public Formula(ArrayList<Term> terms)
    {
        this.terms = (ArrayList<Term>) terms.clone();
    }

    /**
     * Parses s to construct a formula. s will be a legal sequence 
     * of terms with no whitespace, e.g. "AX3YM67" or "Z".  
     * The terms in the field must be in the same order as in s. 
     */
    public Formula(String s)
    {
        terms = new ArrayList<> ();
        int i = lastUC(s);
        terms.add(new Term(s.substring(i)));
        while (i > 0)
        {
            int z = lastUC(s.substring(0, i));
            terms.add(0, new Term(s.substring(z, i)));
            i = z;
        }
    }

    /**
     * Returns the terms of the formula.
     */
    public ArrayList<Term> getTerms()
    {
        return terms;
    }
    
    /**
     * Returns the index in s where the rightmost upper-case letter sits, 
     * e.g. lastTerm("AX3YM67") returns 4. 
     * Returns -1 if there are no upper-case letters. 
     */
    public static int lastUC(String s)
    {
       for (int i = s.length() - 1; i >= 0; i--)
            if (Character.isUpperCase(s.charAt(i)))
               return i;
        return -1;
    }
    
    /**
     * Returns the total number of atoms of element in terms. 
     * e.g. if terms = <<W,2>,<X,1>,<W,5>>, countElement('W') returns 7, 
     * countElement('X') returns 1, and countElement('Q') returns 0.
     */
    public int countElement(char element)
    {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for(Term term : terms){
        if(map.containsKey(term.getElement())){ // existing in map
        // add element count into existing and again add into map with updated value
        map.put(term.getElement(), map.get(term.getElement()) + term.getCount());
        }else // new element
        map.put(term.getElement(), term.getCount());}
        for(Map.Entry<Character, Integer> entry : map.entrySet()){     
        int count = entry.getValue();
           if (element == entry.getKey()) {
             count = entry.getValue();
            return count;}
            else if (!map.containsKey(element))
            return 0;}
        return element;
    }

    /**
     * Puts terms in standardised form, where each element present is 
     * represented by exactly one term, and terms are in alphabetical order.
     * e.g. <<C,3>,<D,1>,<B,2>,<D,2>,<C,1>> becomes <<B,2>,<C,4>,<D,3>>.
     */
    public void standardise()
    {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        ArrayList<Term> newTerm = new ArrayList<Term>();
        for(Term term : terms){
           if(map.containsKey(term.getElement())){
               map.put(term.getElement(), map.get(term.getElement()) + term.getCount());
           } else map.put(term.getElement(), term.getCount());
        }
        for(Map.Entry<Character, Integer> entry : map.entrySet()){
           newTerm.add(new Term(entry.getKey(), entry.getValue()));
        }
        terms = newTerm;
    }

    /**
     * Returns true iff this formula and other are isomers, 
     * i.e. they contain the same number of every Bydysawd element. 
     */
    public boolean isIsomer(Formula other)
    {
       this.standardise();
        other.standardise();
        return this.display().equals(other.display()); 
    }

    /**
     * Returns the formula as a String. 
     * e.g. if terms = <<B,22>,<E,1>,<D,3>>, it returns "B22ED3". 
     */
    public String display()
    {
         String formula = "";
        for(Term term : terms){
        formula = formula + term.getElement();
        if(term.getCount() > 1)
        formula = formula + term.getCount();}
        return formula;
    }
}
