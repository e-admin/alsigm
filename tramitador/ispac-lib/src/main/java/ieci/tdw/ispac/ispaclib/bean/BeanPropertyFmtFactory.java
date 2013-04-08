package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.ispaclib.classloaderUtil.ClassloaderUtil;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.xml.sax.Attributes;

public class BeanPropertyFmtFactory extends AbstractObjectCreationFactory
{

	public Object createObject(Attributes attrs) throws Exception
	{
		String name = null;
		String sType = attrs.getValue("type");
		if (sType == null)
			name = "ieci.tdw.ispac.ispaclib.bean.BeanPropertySimpleFmt";
		else if (sType.indexOf('.') == -1)
			name = "ieci.tdw.ispac.ispaclib.bean." + sType;
		else
			name = sType;
		ClassloaderUtil cl = new ClassloaderUtil ();
		return cl.getInstance(name);
	}
}
