package ieci.tdw.ispac.ispacpublicador.business.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;


/**
 * VO para almacenar la información de una acción.
 *
 */
public class ActionVO extends BaseVO implements DbInitializable {

	private static final long serialVersionUID = 1L;
	
    private int id;
    private String nombre;
    private String clase;
    private int activa;
    private String descripcion;
    private int tipo;
    

//==================================================================
//  CONSTRUCTORES
//==================================================================       

    /**
     * Construye un nuevo ActionVO inicializando todos sus atributos.
     */
    public ActionVO(){
        this(-1,null, null,-1, null, -1);
    }
    
    /**
     * Construye un nuevo ActionVO inicializando todos sus atributos.
     * @param id
     * @param nombre
     * @param clase
     * @param activa
     * @param descripcion
     * @param tipo
     */
    public ActionVO(int id, String nombre, String clase, int activa, String descripcion, int tipo){
        setId(id);
        setNombre(nombre);
        setClase(clase);
        setActiva(activa);
        setDescripcion(descripcion);
        setTipo(tipo);
    }
    
    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
		    setId(dbq.getInt("ID"));
		    setNombre(dbq.getString("NOMBRE"));
		    setClase(dbq.getString("CLASE"));
		    setActiva(dbq.getInt("ACTIVA"));
		    setDescripcion(dbq.getString("DESCRIPCION"));
		    setTipo(dbq.getInt("TIPO"));
		}
    }
    
//==================================================================
//  METODOS PARA LA SERIALIZACION
//==================================================================
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append(" Id: ");
        buf.append(getId());
        
        buf.append(" Nombre: ");
        buf.append(getNombre());
        
        buf.append(" Clase: ");
        buf.append(getClase());
        
        buf.append(" Activa: ");
        buf.append(getActiva());
        
        buf.append(" Descripción: ");
        buf.append(getDescripcion());
        
        buf.append(" Tipo: ");
        buf.append(getTipo());

        return buf.toString();    } 
    
    public String toXML() {
        StringBuffer buf = new StringBuffer("<accion>\n");

        buf.append("\t<id>");
        buf.append(getId());
        buf.append("</id>\n");
  
        buf.append("\t<nombre>");
        buf.append(getNombre());
        buf.append("</nombre>\n");        
        
        buf.append("\t<clase>");
        buf.append(getClase());
        buf.append("</clase>\n");
        
        buf.append("\t<activa>");
        buf.append(getActiva());
        buf.append("</activa>\n");
        
        buf.append("\t<descripcion>");
        buf.append(getDescripcion());
        buf.append("</descripcion>\n");
        
        buf.append("\t<tipo>");
        buf.append(getTipo());
        buf.append("</tipo>\n");

        buf.append("</accion>");

        return buf.toString();
    }       

//==================================================================
//    GETTERS   y   SETTERS
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
    /**
     * @return Returns the tipo.
     */
    public int getTipo() {
        return tipo;
    }
    /**
     * @param tipo The tipo to set.
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
