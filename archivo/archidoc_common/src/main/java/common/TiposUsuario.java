package common;

import java.util.ArrayList;
import java.util.List;

import es.archigest.framework.core.vo.PropertyBean;

public class TiposUsuario {

	public static final String INTERNO_VALUE="Interno";
	public static final String EXTERNO_VALUE="Externo";
	public static final String INTERNO_LABEL="Interno";
	public static final String EXTERNO_LABEL="Externo";
	
	public static final String INVESTIGADOR_VALUE="1";
	public static final String CIUDADANO_VALUE="2";
	public static final String ORGANO_EXTERNO_VALUE="3";
	
	public static final String INVESTIGADOR_LABEL="Investigador";
	public static final String CIUDADANO_LABEL="Ciudadano";
	public static final String ORGANO_EXTERNO_LABEL="Organo Externo";
	
	
	/**
	 * @return una lista de {@link PropertyBean}
	 * con los posibles tipos de usuarios para prestamos
	 */
	public static List getTiposUsuarioPrestamos()
	{
		List list=new ArrayList();
		list.add(makePropertyBeanInterno());
		list.add(makePropertyBeanExterno());
		
		return list;
	}
	
	/**
	 * @return una lista de {@link PropertyBean}
	 * con los posibles tipos de usuarios para consultas
	 */
	public static List getTiposUsuarioConsultas()
	{
		List list=new ArrayList();
		list.add(makePropertyBeanInvestigador());
		list.add(makePropertyBeanCiudadano());
		list.add(makePropertyBeanOrganoExterno());
		
		return list;
	}
	
	private static PropertyBean makePropertyBeanInterno()
	{
		PropertyBean propertyBean=new PropertyBean();
		propertyBean.setValue(INTERNO_VALUE);
		propertyBean.setLabel(INTERNO_LABEL);
		
		return propertyBean;
	}
	
	private static PropertyBean makePropertyBeanExterno()
	{
		PropertyBean propertyBean=new PropertyBean();
		propertyBean.setValue(EXTERNO_VALUE);
		propertyBean.setLabel(EXTERNO_LABEL);
		
		return propertyBean;
	}
	
	private static PropertyBean makePropertyBeanInvestigador()
	{
		PropertyBean propertyBean=new PropertyBean();
		propertyBean.setValue(INVESTIGADOR_VALUE);
		propertyBean.setLabel(INVESTIGADOR_LABEL);
		
		return propertyBean;
	}
	
	private static PropertyBean makePropertyBeanCiudadano()
	{
		PropertyBean propertyBean=new PropertyBean();
		propertyBean.setValue(CIUDADANO_VALUE);
		propertyBean.setLabel(CIUDADANO_LABEL);
		
		return propertyBean;
	}
	
	private static PropertyBean makePropertyBeanOrganoExterno()
	{
		PropertyBean propertyBean=new PropertyBean();
		propertyBean.setValue(ORGANO_EXTERNO_VALUE);
		propertyBean.setLabel(ORGANO_EXTERNO_LABEL);
		
		return propertyBean;
	}
}
