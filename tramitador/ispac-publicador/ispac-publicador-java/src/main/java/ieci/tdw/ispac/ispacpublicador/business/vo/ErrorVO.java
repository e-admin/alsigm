package ieci.tdw.ispac.ispacpublicador.business.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;


/**
 * Value Object con cuya información se describe un Error.
 *
 */

public class ErrorVO extends BaseVO implements DbInitializable {

	private int idHito;
	private int idAplicacion;
	private String idSistema;
	private String idObjeto;
	private String descripcion;
	private int idError;
    
	

//==================================================================
//	  CONSTRUCTORES
//==================================================================       
	/**
	 * Construye un nuevo ErrorVO inicializando sus atributos. 
	 */
	public ErrorVO(){
	    setIdSistema(null);
	    setIdObjeto(null);
	    setDescripcion(null);
	}
	
	/**
	 * Construye un nuevo ErrorVO inicializando sus atributos con los parámetros pasados.
	 * @param idHito
	 * @param idAplicacion
	 * @param idSistema
	 * @param idObjeto
	 * @param descpricion
	 * @param idError
	 */
	public ErrorVO(int idHito, int idAplicacion, String idSistema, String idObjeto, String descripcion, int idError){
	    setIdHito(idHito);
	    setIdAplicacion(idAplicacion);
	    setIdSistema(idSistema);
	    setIdObjeto(idObjeto);
	    setDescripcion(descripcion);
	    setIdError(idError);
	}

    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
		    setIdHito(dbq.getInt("ID_HITO"));
		    setIdAplicacion(dbq.getInt("ID_APLICACION"));
		    setIdSistema(dbq.getString("ID_SISTEMA"));
		    setIdObjeto(dbq.getString("ID_OBJETO"));
		    setDescripcion(dbq.getString("DESCRIPCION"));
		    setIdError(dbq.getInt("ID_ERROR"));
		}
	}

//==================================================================
//  METODOS PARA LA SERIALIZACION
//==================================================================
 	
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append(" Id_Hito: ");
        buf.append(getIdHito());
        
        buf.append(" Id_Aplicacion: ");
        buf.append(getIdAplicacion());
        
        buf.append(" Id_Sistema: ");
        buf.append(getIdSistema());
        
        buf.append(" Id_Objeto: ");
        buf.append(getIdObjeto());
        
        buf.append(" Descripcion: ");
        buf.append(getDescripcion());
        
        buf.append(" Id_Error: ");
        buf.append(getIdError());

        return buf.toString();
    } 
    
    public String toXML() {
        StringBuffer buf = new StringBuffer("<error>\n");

        buf.append("\t<idHito>");
        buf.append(getIdHito());
        buf.append("</idHito>\n");
        
        buf.append("\t<idAplicacion>");
        buf.append(getIdAplicacion());
        buf.append("</idAplicacion>\n");        
  
        buf.append("\t<idSistema>");
        buf.append(getIdSistema());
        buf.append("</idSistema>\n");        
        
        buf.append("\t<idObjeto>");
        buf.append(getIdObjeto());
        buf.append("</idObjeto>\n");
        
        buf.append("\t<descripcion>");
        buf.append(getDescripcion());
        buf.append("</descripcion>\n");
        
        buf.append("\t<idError>");
        buf.append(getIdError());
        buf.append("</idError>\n");

        buf.append("</regla>");

        return buf.toString();
    } 


//==================================================================
//	    GETTERS   y   SETTERS
//==================================================================
	 	
	
    /**
     * @return Returns the descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * @param descripcion The descripcion to set.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * @return Returns the idAplicacion.
     */
    public int getIdAplicacion() {
        return idAplicacion;
    }
    /**
     * @param idAplicacion The idAplicacion to set.
     */
    public void setIdAplicacion(int idAplicacion) {
        this.idAplicacion = idAplicacion;
    }
    /**
     * @return Returns the idError.
     */
    public int getIdError() {
        return idError;
    }
    /**
     * @param idError The idError to set.
     */
    public void setIdError(int idError) {
        this.idError = idError;
    }
    /**
     * @return Returns the idHito.
     */
    public int getIdHito() {
        return idHito;
    }
    /**
     * @param idHito The idHito to set.
     */
    public void setIdHito(int idHito) {
        this.idHito = idHito;
    }
    /**
     * @return Returns the idObjeto.
     */
    public String getIdObjeto() {
        return idObjeto;
    }
    /**
     * @param idObjeto The idObjeto to set.
     */
    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto;
    }
    /**
     * @return Returns the idSistema.
     */
    public String getIdSistema() {
        return idSistema;
    }
    /**
     * @param idSistema The idSistema to set.
     */
    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }
}
