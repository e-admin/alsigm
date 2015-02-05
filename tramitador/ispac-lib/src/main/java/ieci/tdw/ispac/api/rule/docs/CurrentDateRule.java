package ieci.tdw.ispac.api.rule.docs;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CurrentDateRule implements IRule{
	
	private final String PATTERN = "EEEE d 'de' MMMM 'de' yyyy";
	private static Map mMonths = null;
	private static Map mWeekDays = null;
	static{
		mMonths = new HashMap();
		mMonths.put("gl", new String[]{"xaneiro", "febreiro", "marzo", "abril", "maio", "xuño", "xullo", "agosto", "setembro", "outubro", "novembro", "decembro"});
		mMonths.put("eu", new String[]{"urtarrila", "otsaila", "martxoa", "apirila", "maiatza", "ekaina", "uztaila", "abuztua", "iraila", "urria", "azaroa", "abendua"});
		mMonths.put("ca", new String[]{"gener", "febrer", "març", "abril", "maig", "juny", "juliol", "agost", "setembre", "octubre", "novembre", "desembre"});

		mWeekDays = new HashMap();
		mWeekDays.put("gl", new String[]{"","domingo", "luns", "martes", "mércores", "xoves", "venres", "sábado" });
		mWeekDays.put("eu", new String[]{"","igandea","astelehena", "asteartea", "asteazkena", "osteguna", "ostirala", "larunbata"});
		mWeekDays.put("ca", new String[]{"","diumenge", "dilluns", "dimarts", "dimecres", "dijous", "divendres", "dissabte"});
	
	}
	
    public boolean init(IRuleContext rulectx) throws ISPACRuleException{
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException{
        return true;
    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException{
        try{
        	String pattern = rulectx.get("pattern");
        	if (StringUtils.isEmpty(pattern))
        		pattern = PATTERN;
        	String locale = rulectx.get("locale");
        	Date date=new Date();
        	SimpleDateFormat dateformat=null;
    		if (StringUtils.isEmpty(locale)){
    			dateformat=new SimpleDateFormat(pattern, DateUtil.defaultLocale);
    		}else{
    	    	if (mMonths.get(locale) == null)
    	    		throw new ISPACRuleException("Valor de locale '" + locale + "' desconocido");
                DateFormatSymbols dateFormat = getDateFormatSymbols(locale);
                dateformat=new SimpleDateFormat(pattern, dateFormat);
    		}
            return dateformat.format(date);
        } catch(Exception e) {
        	if (e instanceof ISPACRuleException)
			    throw new ISPACRuleException(e);
        	throw new ISPACRuleException("No se ha podido obtener la fecha actual",e);
        }
    }

    private DateFormatSymbols getDateFormatSymbols(String locale) {
    	
    	DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
    	dateFormatSymbols.setMonths((String[]) mMonths.get(locale));
    	dateFormatSymbols.setWeekdays((String[]) mWeekDays.get(locale));
    	return dateFormatSymbols;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException{
    }
}


