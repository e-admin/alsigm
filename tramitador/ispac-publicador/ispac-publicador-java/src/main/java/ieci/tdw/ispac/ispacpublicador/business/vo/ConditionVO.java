package ieci.tdw.ispac.ispacpublicador.business.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;


/**
 * VO para la información de una condición.
 *
 */
public class ConditionVO extends BaseVO implements DbInitializable {


    private int id;
    private String nombre;
    private String clase;
    private String descripcion;

//==================================================================
//  CONSTRUCTORES
//==================================================================    
  
    /**
     * Construye un nuevo ActionVO inicializando todos sus atributos.
     */
    public ConditionVO(){
        this(-1,null, null, null);
    }
    
    /**
     * Construye un nuevo ConditionVO inicializando todos sus atributos.
     * @param id
     * @param nombre
     * @param clase
     * @param descripcion
     */
    public ConditionVO(int id, String nombre, String clase, String descripcion){
        setId(id);
        setNombre(nombre);
        setClase(clase);
        setDescripcion(descripcion);
    }
    
    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
            setId(dbq.getInt("ID"));
            setNombre(dbq.getString("NOMBRE"));
            setClase(dbq.getString("CLASE"));
            setDescripcion(dbq.getString("DESCRIPCION"));
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

        buf.append(" Clase: ");
        buf.append(getClase());

        buf.append(" Descripcion: ");
        buf.append(getDescripcion());
        
        return buf.toString();   
    
    }

    public String toXML() {

        StringBuffer buf = new StringBuffer("<condicion>\n");

        buf.append("\t<id>");
        buf.append(getId());
        buf.append("</id>\n");

        buf.append("\t<nombre>");
        buf.append(getNombre());
        buf.append("</nombre>\n");
        
        buf.append("\t<clase>");
        buf.append(getClase());
        buf.append("</clase>\n");
        
        buf.append("\t<descripcion>");
        buf.append(getClase());
        buf.append("</descripcion>\n");
        
        buf.append("</condicion>");

        return buf.toString();
        
    
    }

    
//==================================================================
//  GETTERS   y   SETTERS
//==================================================================
    
    /**
     * @return Returns the clase.
     */
    public String getClase() {
        return clase;
    }
    /**
     * @param clase The clase to set.
     */
    public void setClase(String clase) {
        this.clase = clase;
    }
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
}
