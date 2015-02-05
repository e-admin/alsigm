package ieci.tdw.ispac.ispacweb.comparators;

import java.text.Collator;
import java.util.Comparator;

import org.displaytag.model.Cell;

public class NumexpComparator implements Comparator {
	
	public static final int NUMERO_MAYOR = 1;
	public static final int NUMERO_MENOR = -1;
	public static final int NUMERO_IGUAL = 0;	
	
	private Collator collator;

    public NumexpComparator()
    {
        this(Collator.getInstance());
    }

    public NumexpComparator(Collator collatorToUse)
    {
        collator = collatorToUse;
        collator.setStrength(0);
    }
    
	public int compare(Object object1, Object object2) {
		
		String numexp1 = null;
		String numexp2 = null;
		
		if ((object1 instanceof Cell) && (object2 instanceof Cell)) {
			
			numexp1 = ((Cell) object1).getStaticValue().toString();
			numexp2 = ((Cell) object2).getStaticValue().toString();
		}
		else {
			numexp1 = (String) object1;
			numexp2 = (String) object2;
		}
		
		Long lnumexp1 = new Long(numexp1.replaceAll("\\D", ""));
		Long lnumexp2 = new Long(numexp2.replaceAll("\\D", ""));
		
		/*
		Long lnumexp1 = new Long(onlyNumbersNumexp(numexp1));
		Long lnumexp2 = new Long(onlyNumbersNumexp(numexp2));
		*/
		
		if (lnumexp1.longValue() > lnumexp2.longValue())
			return NUMERO_MAYOR;
		else if(lnumexp1.longValue() < lnumexp2.longValue())
			return NUMERO_MENOR;
		else
			return NUMERO_IGUAL;
	}
	
	/**
	 * Utilizar cuando a la numeración de expedientes no se le ha puesto un tamaño fijo -> $NM{6}$
	 * para que los expedientes salgan bien ordenados por NUMEXP
	 * 
	private String onlyNumbersNumexp(String numexp) {
		
		String onlyNumbers = "";
		
		String[] sNumexp = numexp.split("\\D");
		for (int i = 0; i < sNumexp.length; i++) {
			
			String number = sNumexp[i];
			if (!number.equals("")) {
				
				for (int j = 0; j < (6 -number.length()); j++) {
					
					onlyNumbers += "0";
				}
				
				onlyNumbers += number;
			}
		}
		
		return onlyNumbers;
	}
	*/

}