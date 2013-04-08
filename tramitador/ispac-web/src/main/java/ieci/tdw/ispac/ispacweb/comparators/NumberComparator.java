package ieci.tdw.ispac.ispacweb.comparators;

import java.text.Collator;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.displaytag.model.Cell;

public class NumberComparator implements Comparator {

	public static final int NUMERO_MAYOR = 1;
	public static final int NUMERO_MENOR = -1;
	public static final int NUMERO_IGUAL = 0;

	private Collator collator;
    public NumberComparator()
    {
        this(Collator.getInstance());
    }

    public NumberComparator(Collator collatorToUse)
    {
        collator = collatorToUse;
        collator.setStrength(0);
    }
	public int compare(Object object1, Object object2) {

        Number n1 = null;
        Number n2 = null;

        // Obtención de los números a partir de los objetos recibidos
		if ((object1 instanceof Cell) && (object2 instanceof Cell)){
			String value1 = ((Cell)object1).getStaticValue().toString();
			String value2 = ((Cell)object2).getStaticValue().toString();
			if (StringUtils.isBlank(value1)){
				n1 = new Double(-Double.MAX_VALUE);
			}else{
				n1 = new Double(value1);
			}
			if (StringUtils.isBlank(value2)){
				n2 = new Double(-Double.MAX_VALUE);
			}else{
				n2 = new Double(value2);
			}
		}else if ((object1 instanceof Number) && (object2 instanceof Number)){
			try {
		        n1 = (Number)object1;
		        n2 = (Number)object2;
			} catch (Exception e) {
			}
		}else if ((object1 instanceof String) && (object2 instanceof String)){
			try {
		        n1 = Integer.valueOf((String) object1);
		        n2 = Integer.valueOf((String) object2);
			} catch (Exception ie) {
				try {
			        n1 = Double.valueOf((String) object1);
			        n2 = Double.valueOf((String) object2);
				} catch (Exception de) {
				}
			}
		}

		// Comparación de los números
		if ( (n1 == null) || (n2 == null) )
			return NUMERO_IGUAL;
		if (n1.doubleValue() > n2.doubleValue())
			return NUMERO_MAYOR;
		else if(n1.doubleValue() < n2.doubleValue())
			return NUMERO_MENOR;
		else
			return NUMERO_IGUAL;
	}
}
