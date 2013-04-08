package ieci.tdw.ispac.ispacweb.comparators;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.displaytag.model.Cell;

public class DateComparator implements Comparator {

    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
	private Collator collator;
    public DateComparator()
    {
        this(Collator.getInstance());
    }

    public DateComparator(Collator collatorToUse)
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
	        	Date init = (Date)formatter.parse(((Cell)object1).getStaticValue().toString().trim());
	        	Date end = (Date)formatter.parse(((Cell)object2).getStaticValue().toString().trim());
    			return DateUtil.compare(init, end);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if((object1 instanceof String) && (object2 instanceof String)){
    		try {
	        	Date init = (Date)formatter.parse(StringUtils.trim((String)object1));
	        	Date end = (Date)formatter.parse(StringUtils.trim((String)object2));
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
