package ieci.tdw.ispac.ispacpublicador.business.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;


/**
 * Value Object con cuya información se describe un Regla.
 *
 */
public class RuleVO extends BaseVO implements DbInitializable {

    /**
     * Valor que identifica de forma única a una regla 
     */
    private int id; 
    /**
     * Evento que debe coincidir con el evento del Hito Activo para que se procese la regla 
     */
    private int idEvento;
    
    /**
     * Identificador de la información auxiliar.
     */
    private int idInfo;

    /**
     * Condición a evaluar si se procesa la regla.
     */
    private int idCondicion;

    /**
     * Acción a ejecutar si se procesa la regla y la condición da resultado positivo.
     */
    private int idAccion;
    /**
     * Atributos a pasar a la Acción cuando ésta se deba ejecutar.
     */
    private String atributos;
    /**
     * Identificador de la Aplicación que debe coincidir con la aplicación asociada 
     * al Hito Activo para que la regla se procese.
     */
    private String idAplicacion;
    /**
     * Indica un orden de ejecución si existe más de una regla a procesar para
     * el mismo Hito Activo.
     */
    private int orden;

//==================================================================
//  CONSTRUCTORES
//==================================================================       

    /**
     * Constructor.
     * 
     */
    public RuleVO() {
    	this(-1, -1, -1, -1, -1, null, null, -1);
    }
    
    /**
     * Construye un nuevo RuleVO inicializando todos los atributos con los parámetros pasados.
     * @param id
     * @param idEvento
     * @param idAccion
     * @param idCondicion
     * @param atributos
     * @param idAplicacion
     * @param orden
     */
    public RuleVO(int id, int idEvento, int idInfo, int idAccion, int idCondicion, String atributos, String idAplicacion, int orden){
        setId(id);
        setIdEvento(idEvento);
        setIdInfo(idInfo);
        setIdAccion(idAccion);
        setIdCondicion(idCondicion);
        setAtributos(atributos);
        setIdAplicacion(idAplicacion);
        setOrden(orden);
    }
    
    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
	        setId(dbq.getInt("ID"));
	        setIdEvento(dbq.getInt("ID_EVENTO"));
	        setIdInfo(dbq.getInt("ID_INFO"));
	        setIdAccion(dbq.getInt("ID_ACCION"));
	        setIdCondicion(dbq.getInt("ID_CONDICION"));
	        setAtributos(dbq.getString("ATRIBUTOS"));
	        setIdAplicacion(dbq.getString("ID_APLICACION"));
	        setOrden(dbq.getInt("ORDEN"));
		}
    }

//==================================================================
//  METODOS PARA LA SERIALIZACION
//==================================================================
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append(" Id: ");
        buf.append(getId());
        
        buf.append(" Id_Evento: ");
        buf.append(getIdEvento());
        
        buf.append(" Id_Info: ");
        buf.append(getIdInfo());
        
        buf.append(" Id_Accion: ");
        buf.append(getIdAccion());
        
        buf.append(" Id_Condicion: ");
        buf.append(getIdCondicion());
        
        buf.append(" Atributos: ");
        buf.append(getAtributos());
        
        buf.append(" Id_Aplicacion: ");
        buf.append(getIdAplicacion());

        buf.append(" Orden: ");
        buf.append(getOrden());

        return buf.toString();
    } 
    
    public String toXML() {
        StringBuffer buf = new StringBuffer("<regla>\n");

        buf.append("\t<id>");
        buf.append(getId());
        buf.append("</id>\n");
        
        buf.append("\t<idEvento>");
        buf.append(getIdEvento());
        buf.append("</idEvento>\n");        
        
        buf.append("\t<idInfo>");
        buf.append(getIdInfo());
        buf.append("</idInfo>\n");        
        
        buf.append("\t<idAccion>");
        buf.append(getIdAccion());
        buf.append("</idAccion>\n");
        
        buf.append("\t<idCondicion>");
        buf.append(getIdCondicion());
        buf.append("</idCondicion>\n");
        
        buf.append("\t<atributos>");
        buf.append(getAtributos());
        buf.append("</atributos>\n");
        
        buf.append("\t<idAplicacion>");
        buf.append(getIdAplicacion());
        buf.append("</idAplicacion>\n");

        buf.append("\t<orden>");
        buf.append(getOrden());
        buf.append("</orden>\n");
        
        buf.append("</regla>");

        return buf.toString();
    }       

//==================================================================
//    GETTERS   y   SETTERS
//==================================================================
        
    /**
     * @return Returns the atributos.
     */
    public String getAtributos() {
        return atributos;
    }
    /**
     * @param atributos The atributos to set.
     */
    public void setAtributos(String atributos) {
        this.atributos = atributos;
    }
    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the idAccion.
     */
    public int getIdAccion() {
        return idAccion;
    }
    /**
     * @param idAccion The idAccion to set.
     */
    public void setIdAccion(int idAccion) {
        this.idAccion = idAccion;
    }
    /**
     * @return Returns the idCondicion.
     */
    public int getIdCondicion() {
        return idCondicion;
    }
    /**
     * @param idCondicion The idCondicion to set.
     */
    public void setIdCondicion(int idCondicion) {
        this.idCondicion = idCondicion;
    }
    /**
     * @return Returns the idEvento.
     */
    public int getIdEvento() {
        return idEvento;
    }
    /**
     * @param idEvento The idEvento to set.
     */
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
    /**
     * @return Returns the orden.
     */
    public int getOrden() {
        return orden;
    }
    /**
     * @param orden The orden to set.
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }
    /**
     * @return Returns the idAplicacion.
     */
    public String getIdAplicacion() {
        return idAplicacion;
    }
    /**
     * @param idAplicacion The idAplicacion to set.
     */
    public void setIdAplicacion(String idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

	public int getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(int idInfo) {
		this.idInfo = idInfo;
	}
}
