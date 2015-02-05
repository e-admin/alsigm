package ieci.tdw.ispac.ispaclib.bean;

public class BeanPropertySimpleFmt extends BeanPropertyFmt {

	public BeanPropertySimpleFmt() {
		super();
	}
	
	public Object format(Object value) {
		if (value == null) { 
			return "";
		}
		
		return value.toString();
		
		/*
		String ret = value.toString();
		ret = StringUtils.replace(ret, "\'", "&#39;");
		
		return ret;
		*/
	}
}
