package ieci.tdw.ispac.ispacmgr.bean.scheme;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author marisa
 */
public class SchemeEntityBean extends ItemBean{

	private static final String ENTITYIMGON = "img/arrow_down_sch.gif";
	private static final String ENTITYIMGOFF = "img/arrow_right_sch.gif";
	
	/**
	 * Lista de registros de la entidad.
	 */
	private List /*<RegEntity>*/ mregs = null;
	/**
	 * Imagen que acompaña al título de la entidad en el esquema, cuando muestra los registros.
	 */
	private String mimgOn = ENTITYIMGON;
	
	/**
	 * Imagen que acompaña al título de la entidad en el esquema, cuando oculta los registros.
	 */
	private String mimgOff = ENTITYIMGOFF;
	
	/**
	 * Constructor por defecto.
	 */
	public SchemeEntityBean()
	{
		mregs = new ArrayList();
	}
	
	/**
	 * Constructor con todos los atributos.
	 *  
	 * @param regs	 lista con las definiciones de los registros de la entidad.
	 * @param imgOn	 imagen al mostrar en la entidad cuando muestra todos sus registros.
	 * @param imgOff imagen al mostrar en la entidad cuando oculta todos sus registros.
	 */
	public SchemeEntityBean(List regs, String imgOn, String imgOff)
	{
		mregs = regs;
		mimgOn = imgOn;
		mimgOff = imgOff;
	}
	
	/**
	 * Constructor con los atributos más necesarios.
	 * 
	 * @param regs	lista con las definiciones de los registros de la entidad.
	 */
	public SchemeEntityBean(int id, String title, List regs)
	{
		this(regs, ENTITYIMGON, ENTITYIMGOFF);
	}
	
	/**
	 * Añade un objeto RegEntity a la lista.
	 * 
	 * @param regEntity Objeto con la definición del registro de la entidad.
	 */
	public void addRegEntity(RegEntityBean regEntity)
	{
		mregs.add(regEntity);
	}
	
	/**
	 * Añade el identificador de la entidad, al objeto Map 'Params' 
	 * de todos los elementos de la lista.
	 *
	 */
	public void addParameterId() 
	throws ISPACException
	{
		if (mregs == null)
			mregs = new ArrayList();
		
		for (Iterator ite = mregs.iterator(); ite.hasNext();)
		{
			RegEntityBean regEnt = (RegEntityBean)ite.next();
			Map params = regEnt.getParams();
			params.put(ManagerState.PARAM_ENTITYID, mitem.getKey());
			regEnt.setParams(params);
		}
	}
	
	/**
	 * Añade un nuevo parámetro con valor a todos los elementos de la lista.
	 * 
	 * @param label valor de la etiqueta 
	 * @param value objeto valor
	 */
	public void addParameter(String label, Object value)
	{
		if (mregs == null)
			mregs = new ArrayList();
		
		for (Iterator ite = mregs.iterator(); ite.hasNext();)
		{
			RegEntityBean regEnt = (RegEntityBean)ite.next();
			Map params = regEnt.getParams();
			params.put(label,value);
			regEnt.setParams(params);
		}
	}
	/* métodos get y set */
	
	/**
	 * @return Returns the img.
	 */
	public String getImgOn() {
		return mimgOn;
	}
	/**
	 * @param img The img to set.
	 */
	public void setImgOn(String img) {
		this.mimgOn = img;
	}
	/**
	 * @return Returns the img.
	 */
	public String getImgOff() {
		return mimgOff;
	}
	/**
	 * @param img The img to set.
	 */
	public void setImgOff(String img) {
		this.mimgOff = img;
	}
	/**
	 * @return Returns the regs.
	 */
	public List getRegs() {
		return mregs;
	}
	/**
	 * @param regs The regs to set.
	 */
	public void setRegs(List regs) {
		this.mregs = regs;
	}
	
}
