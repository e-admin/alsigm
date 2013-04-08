package ieci.tdw.ispac.ispacweb.comparators;

import ieci.tdw.ispac.ispaclib.utils.DateUtil;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.displaytag.model.Cell;

public class DateComparatorWithOutYear implements Comparator {
	
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
	private Collator collator;
    public DateComparatorWithOutYear()
    {
        this(Collator.getInstance());
    }

    public DateComparatorWithOutYear(Collator collatorToUse)
    {
        collator = collatorToUse;
        collator.setStrength(0);
    }
	public int compare(Object object1, Object object2) {
		if((object1 instanceof Date) && (object2 instanceof Date)){
        	Date init = (Date)object1;
    		Date end = (Date)object2;
    		try {
    			return DateUtil.compare(init, end);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if((object1 instanceof Cell) && (object2 instanceof Cell)){
    		try {
	        	Date init = (Date)formatter.parse(((Cell)object1).getStaticValue().toString()+"/2000");
	        	Date end = (Date)formatter.parse(((Cell)object2).getStaticValue().toString()+"/2000");
    			return DateUtil.compare(init, end);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if((object1 instanceof String) && (object2 instanceof String)){
    		try {
	        	Date init = (Date)formatter.parse((String)object1+"/2000");
	        	Date end = (Date)formatter.parse((String)object2+"/2000");
    			return DateUtil.compare(init, end);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        }else if((object1 instanceof Comparable) && (object2 instanceof Comparable))
        	return ((Comparable)object1).compareTo(object2);
        else 
        	return collator.compare(object1.toString(), object2.toString());
		return 0;
	}

}
