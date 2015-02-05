/*
 * Created on 09-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;


/**
 * Value Object con cuya información se describe una Aplicación.
 * 
 */
public class ApplicationVO extends BaseVO implements DbInitializable {

    /**
     * Identificador de la aplicación.
     */
    int id;
    /**
     * Nombre de la aplicación
     */
    String nombre;
    /**
     * Campo que indica si la aplicación está activa (1) o inactiva (0).
     */
    int activa;
    
//==================================================================
//  CONSTRUCTORES
//==================================================================    

    /**
     * Constructor.
     */
    public ApplicationVO() {
    	this(-1, null, -1);
    }
    
    /**
     * Construye un nuevo ApplicationVO inicializando todos sus atributos.
     * @param id
     * @param nombre
     * @param activa
     */
    public ApplicationVO(int id, String nombre, int activa) {
        setId(id);
        setNombre(nombre);
        setActiva(activa);
    }
    
    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
	        setId(dbq.getInt("ID"));
	        setNombre(dbq.getString("NOMBRE"));
	        setActiva(dbq.getInt("ACTIVA"));
		}
	}

//==================================================================
//  METODOS PARA LA SERIALIZACION
//==================================================================
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append("ID: ");
        buf.append(getId());

        buf.append(" Nombre: ");
        buf.append(getNombre());

        buf.append(" Activa: ");
        buf.append(getActiva());

        return buf.toString();    
    }

    public String toXML() {
        StringBuffer buf = new StringBuffer("<aplicacion>\n");

        buf.append("\t<id>");
        buf.append(getId());
        buf.append("</id>\n");

        buf.append("\t<nombre>");
        buf.append(getNombre());
        buf.append("</nombre>\n");
        
        buf.append("\t<activa>");
        buf.append(getActiva());
        buf.append("</activa>\n");
        
        
        buf.append("</aplicacion>");

        return buf.toString();
     }

    
//==================================================================
//  GETTERS   y   SETTERS
//==================================================================
     
    /**
     * @return Returns the activa.
     */
    public int getActiva() {
        return activa;
    }
    /**
     * @param activa The activa to set.
     */
    public void setActiva(int activa) {
        this.activa = activa;
    }
    /**
     * @return Returns the nombre.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @param nombre The nombre to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
 }
