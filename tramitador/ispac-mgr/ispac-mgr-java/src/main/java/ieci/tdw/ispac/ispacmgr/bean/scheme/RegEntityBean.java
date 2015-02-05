package ieci.tdw.ispac.ispacmgr.bean.scheme;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegEntityBean extends ItemBean{
	
	private static final String IMG_DEFAULT = "img/pixel.gif";
	
	private String murl;
	/**
	 * Parámetros para el enlace.
	 */
	private Map mparams;
	/**
	 * Imagen del registro de la entidad.
	 */
	private String mimg = IMG_DEFAULT;
	
	private String clase="";
	
	private List ltRegEntityBean;
	
	public void addRegEntityBean(RegEntityBean regEntityBean) {
		
		if (ltRegEntityBean == null) {
			ltRegEntityBean = new ArrayList();
		}
		
		ltRegEntityBean.add(regEntityBean);
	}
	
	/**
	 * @return Returns the ltRegEntityBean.
	 */
	public List getLtRegEntityBean() {
		return ltRegEntityBean;
	}

	/**
	 * @param ltRegEntityBean The ltRegEntityBean to set.
	 */
	public void setLtRegEntityBean(List ltRegEntityBean) {
		this.ltRegEntityBean = ltRegEntityBean;
	}

	/**
	 * Constructor por defecto
	 */
	public RegEntityBean()
	{
		murl = null;
		mparams = new HashMap();
	}
	
	/**
	 * Constructor con todos los atributos.
	 * 
	 * @param url	 acción a ejecutarse al pinchar en el registro seleccionado.
	 * @param params objeto que contiene los parámetros a pasar por querystring. 
	 * @param img	 imagen a mostrar del registro de entidad.
	 */
	public RegEntityBean(String url, Map params, String img)
	{
		murl = url;
		mparams = params;
		mimg = img;
	}
	
	/**
	 * Constructor con atributos.
	 * 
	 * @param url	 acción a ejecutarse al pinchar en el registro seleccionado.
	 * @param params objeto que contiene los parámetros a pasar por querystring.
	 */
	public RegEntityBean(String url, Map params)
	{
		this(url, params, IMG_DEFAULT);
	}
	
	/**
	 * Constructor con los atributos más necesarios.
	 * 
	 * @param url	 acción a ejecutarse al pinchar en el registro seleccionado.
	 */
	public RegEntityBean(String url)
	{
		this(url, new HashMap());
	}
	
	/**
	 * Añade el identificador del registro de la entidad al objeto Map.
	 */
	public void addParamsId() 
	throws ISPACException
	{
		
		mparams.put(ManagerState.PARAM_ENTREGID, mitem.get("SCHEME_ID"));
	}
	
	/**
	 * Añade un mapa a los parámetros.
	 * 
	 * @param map mapa que incluimos. 
	 */
	public void addParams(Map map)
	{
		mparams.putAll(map);
	}
	
	/**
	 * Añade un nuevo parámetro a través de una etiqueta y valor.
	 * 
	 * @param label nombre del parámetro.
	 * @param value valor del parámetro.
	 */
	public void addParams(String label, Object value)
	{
		mparams.put(label, value);
	}
	
	/**
	 * Obtiene la imagen según la entidad.
	 * @param entityId identificador de la entidad.
	 * @throws ISPACException
	 */
	public void getImage(int entityId) 
	throws ISPACException 
	{
		switch (entityId) {
			case ISPACEntities.DT_ID_INTERVINIENTES:
			{
				mimg = "img/user.gif";
				break;
			}
			case ISPACEntities.DT_ID_DOCUMENTOS:
			{
				String estado = mitem.getString("ESTADO");
				if (StringUtils.equals(estado, "FINALIZADO"))
				//if (estado.equals("FINALIZADO"))
					mimg = "img/document3.gif";
				else 
					mimg = "img/document.gif";
				break;
			}
			case ISPACEntities.DT_ID_TRAMITES:
			{
				int estado = mitem.getInt("ESTADO");
				
				if (StringUtils.isEmpty(mitem.getString("ID_SUBPROCESO"))) {
					
					// Trámite
					switch (estado) {
						case ISPACEntities.TASKSTATUS_OPEN:
						{
							mimg = "img/tramiteestado1.gif";
							clase="tramite_abierto";
							break;
						}
						case ISPACEntities.TASKSTATUS_DELEGATE:
						{
							mimg = "img/tramiteestado2.gif";
							clase="tramite_delegado";
							break;
						}
						case ISPACEntities.TASKSTATUS_CLOSE:
						{
							mimg = "img/tramiteestado3.gif";
							clase="tramite_cerrado";
							break;
						}
					}
				}
				else {
					// Subproceso
					clase="subproceso menu";
					switch (estado) {
						case ISPACEntities.TASKSTATUS_OPEN:
						{
							mimg = "img/procedimiento.gif";
							
						
							break;
						}
						case ISPACEntities.TASKSTATUS_DELEGATE:
						{
							mimg = "img/tramiteestado2.gif";
							break;
						}
						case ISPACEntities.TASKSTATUS_CLOSE:
						{
							mimg = "img/tramiteestado3.gif";
							clase="tramite_cerrado";
							break;
						}
					}
				}
				break;
			}
			default:
			{
				mimg = IMG_DEFAULT;
				break;
			}
		}
	}
	
	/* métodos get y set */
	/**
	 * @return Returns the img.
	 */
	public String getImg() {
		return mimg;
	}
	
	public String getClase(){
		return clase;
	}
	/**
	 * @param img The img to set.
	 */
	public void setImg(String img) {
		this.mimg = img;
	}
	/**
	 * @return Returns the params.
	 */
	public Map getParams() {
		return mparams;
	}
	/**
	 * @param params The params to set.
	 */
	public void setParams(Map params) {
		this.mparams = params;
	}
	
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return murl;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.murl = url;
	}
}
