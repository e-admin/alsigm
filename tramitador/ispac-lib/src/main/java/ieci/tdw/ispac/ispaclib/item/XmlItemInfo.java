package ieci.tdw.ispac.ispaclib.item;



public class XmlItemInfo
{
	String mitemsxpath;
	String mpropertyxpath;
	String mpropnamexpath;
	String mvaluexpath;
	String mpropkey;
	
	public XmlItemInfo(String itemsxpath, String propertyxpath,String propnamexpath, String valuexpath,String propkey)
	{
		mitemsxpath=itemsxpath;
		mpropertyxpath=propertyxpath;
		mpropnamexpath=propnamexpath;
		mvaluexpath=valuexpath;
		mpropkey=propkey;
	}
	
	public String getPropKey()
	{
		return mpropkey;
	}

	public String getItemsXPath()
	{
		return mitemsxpath;
	}
	
	public String getPropertyXPath()
	{
		return mpropertyxpath;
	}
	
	public String getPropNameXPath()
	{
		return mpropnamexpath;
	}
	
	public String getValueXPath()
	{
		return mvaluexpath;
	}
}
