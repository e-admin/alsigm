/*
 * Created on 09-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.sql.Timestamp;

/**
 * Value Object con cuya información se describe un Hito.
 * @author Ildefonso Noreña
 */
public class MilestoneVO extends BaseVO implements DbInitializable {

    /**
     * Identificador del Hito.
     */
    private int idHito;
    
    private int idProcedimiento;

    /**
     * Si tiene valor, identifica a la Fase que interviene en
     */
    private int idFase;
    /**
     * Si tiene valor, identifica al Trámite que interviene en el origen del hito.
     */
    private int idTramite;
    
    /**
     * Identificador del Tipo de Documento que interviene en el orgien del hito.
     */
    private int tipoDoc;

    
    /**
     * Identificador único del objeto origen de la generación del hito.
     * Uso: Cuando se produce un error al procesar un hito, se insertará un registro
     * en la tabla de errores donde, entre otros campos, irá éste. 
     * A partir de ese momento hasta que no se elimine el error de la tabla 
     * no se procesará ningún hito que tenga este idObjeto, ya que existe un error asociado a él.
     */
    private String idObjeto;
    
    /**
     * Tipo de Evento que genera el Hito.
     */
    private int idEvento;
    /**
     * Fecha en la que se produce el Hito.
     */
    private Timestamp fecha;

    private Timestamp fechaLimite;
    private String info;
    private String autor;
    private String descripcion;
    private int idInfo;
    
//==================================================================
//  CONSTRUCTORES
//==================================================================    
    
    /**
     * Constructor.
     */
    public MilestoneVO() {
    	this(null, -1, -1, -1, -1, null, -1, null);
    }
    
    /**
     * Construye un nuevo MilestoneVO inicializando todos sus atributos con los parámetros pasados.
     * @param idHito
     * @param idProcedimiento
     * @param idFase
     * @param idTramite
     * @param tipoDoc
     * @param idObjeto
     * @param idEvento
     * @param fecha
     */
    public MilestoneVO(String idHito, int idProcedimiento, int idFase, int idTramite, int tipoDoc, String idObjeto, int idEvento,Timestamp fecha){
    }
    
    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
	        setIdHito(dbq.getInt("ID"));
	        setIdProcedimiento(dbq.getInt("ID_PCD"));
	        setIdFase(dbq.getInt("ID_FASE"));
	        setIdTramite(dbq.getInt("ID_TRAMITE"));
	        setIdObjeto(dbq.getString("NUMEXP"));
	        setIdEvento(dbq.getInt("HITO"));
	        setFecha(TypeConverter.toTimestamp(dbq.getDate("FECHA")));
	        setFechaLimite(TypeConverter.toTimestamp(dbq.getDate("FECHA_LIMITE")));
	        setInfo(dbq.getString("INFO"));
	        setAutor(dbq.getString("AUTOR"));
	        setDescripcion(dbq.getString("DESCRIPCION"));
	        setIdInfo(dbq.getInt("ID_INFO"));
	        
	        // TODO Obtener el tipo de documento.
	        //Este valor no se devuelve nunca, aunque la idea debe ser 
	        //devolverlo en los campos INFO e ID_INFO
        	setTipoDoc(0);
		}
    }
    
//==================================================================
//  METODOS PARA LA SERIALIZACION
//==================================================================    
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append(" Id_Hito: ");
        buf.append(getIdHito());
        
        buf.append(" IdProcedimiento: ");
        buf.append(getIdProcedimiento());
        
        buf.append(" Id_Fase: ");
        buf.append(getIdFase());

        buf.append(" Id_Tramite: ");
        buf.append(getIdTramite());
        
        buf.append(" Tipo_Doc: ");
        buf.append(getTipoDoc());
        
        buf.append(" Id_Objeto: ");
        buf.append(getIdObjeto());
        
        buf.append(" Id_Evento: ");
        buf.append(getIdEvento());
        
        buf.append(" Fecha: ");
        buf.append(getFecha());

        buf.append(" Fecha_Limite: ");
        buf.append(getFechaLimite());

        buf.append(" Info: ");
        buf.append(getInfo());

        buf.append(" Autor: ");
        buf.append(getAutor());

        buf.append(" Descripcion: ");
        buf.append(getDescripcion());

        buf.append(" Id_Info: ");
        buf.append(getIdInfo());

        return buf.toString();
    }

    public String toXML() {
        StringBuffer buf = new StringBuffer("<hito>\n");

        buf.append(toXMLFields());

        buf.append("</hito>");

        return buf.toString();
    }

    
    /**
     * @return un XML con la representación de los campos. Se ha separado
     * para poder obtener sólo la representación de los campos las clases que 
     * extiendan a ésta.
     *  
     */
    protected String toXMLFields(){

        StringBuffer buf = new StringBuffer();
        
        buf.append("\t<idHito>");
        buf.append(getIdHito());
        buf.append("</idHito>\n");
        
        buf.append("\t<idFase>");
        buf.append(getIdFase());
        buf.append("</idFase>\n");        
        
        buf.append("\t<idTramite>");
        buf.append(getIdTramite());
        buf.append("</idTramite>\n");
        
        buf.append("\t<tipoDoc>");
        buf.append(getTipoDoc());
        buf.append("</tipoDoc>\n");

        buf.append("\t<idObjeto>");
        buf.append(getIdObjeto());
        buf.append("</idObjeto>\n");
        
        buf.append("\t<idEvento>");
        buf.append(getIdEvento());
        buf.append("</idEvento>\n");
        
        buf.append("\t<fecha>");
        buf.append(getFecha());
        buf.append("</fecha>\n");

        buf.append("\t<fecha_limite>");
        buf.append(getFechaLimite());
        buf.append("</fecha_limite>\n");

        buf.append("\t<info>");
        buf.append(getInfo());
        buf.append("</info>\n");

        buf.append("\t<autor>");
        buf.append(getAutor());
        buf.append("</autor>\n");

        buf.append("\t<descripcion>");
        buf.append(getDescripcion());
        buf.append("</descripcion>\n");

        buf.append("\t<id_info>");
        buf.append(getIdInfo());
        buf.append("</id_info>\n");

        return buf.toString();
    }

//==================================================================
//  GETTERS   y   SETTERS
//==================================================================
    
    /**
     * @return Returns the fecha.
     */
    public Timestamp getFecha() {
        return fecha;
    }
    /**
     * @param fecha The fecha to set.
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
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
     * @return Returns the idFase.
     */
    public int getIdFase() {
        return idFase;
    }
    /**
     * @param idFase The idFase to set.
     */
    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }
    /**
     * @return Returns the idTramite.
     */
    public int getIdTramite() {
        return idTramite;
    }
    /**
     * @param idTramite The idTramite to set.
     */
    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
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
     * @return Returns the idProcedimiento.
     */
    public int getIdProcedimiento() {
        return idProcedimiento;
    }
    /**
     * @param idProcedimiento The idProcedimiento to set.
     */
    public void setIdProcedimiento(int idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }
    /**
     * @return Returns the tipoDoc.
     */
    public int getTipoDoc() {
        return tipoDoc;
    }
    /**
     * @param tipoDoc The tipoDoc to set.
     */
    public void setTipoDoc(int tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Timestamp fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public int getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(int idInfo) {
		this.idInfo = idInfo;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
