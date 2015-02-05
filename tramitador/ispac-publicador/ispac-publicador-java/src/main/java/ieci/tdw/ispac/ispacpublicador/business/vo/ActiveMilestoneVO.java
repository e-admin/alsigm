package ieci.tdw.ispac.ispacpublicador.business.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.sql.Timestamp;


/**
 * Value Object con cuya información se describe un Hito Activo.
 * 
 */
public class ActiveMilestoneVO extends BaseVO implements DbInitializable {

	private static final long serialVersionUID = 1L;
	
    //Conjunto de posibles estados de un Hito Activo
    
    /**
     * Valore que representa el Estado Inicial de un Hito
     */
    public final static int ESTADO_INICIAL   	= 0;
    /**
     * Valore que representa el Estado Bloqueado de un Hito
     */
    public final static int ESTADO_BLOQUEADO 	= 1; 
    /**
     * Valore que representa el Estado Correcto de un Hito
     */
    public final static int ESTADO_CORRECTO  	= 2;
    /**
     * Valore que representa el Estado Error de un Hito
     */
    public final static int ESTADO_ERROR		= 3;    
    
    
    
    /**
     * Identificador del Hito 
     */
    private int idHito;
    
    /**
     * Identificador del Procedimiento con el que está asociado el objeto origen del hito.
     */
    private int idProcedimiento;
    /**
     * Identificador de la Fase con el que está asociado el objeto origen del hito.
     */
    private int idFase;
    /**
     * Identificador del Trámite con el que está asociado el objeto origen del hito.
     */
    private int idTramite;
    /**
     * Identificador del Documento con el que está asociado el objeto origen del hito.
     */
    private int tipoDoc;
    /**
     * Identificador único de un objeto que originó el hito.
     */
    private String idObjeto;
    
    /**
     * Identificador de la información auxiliar.
     */
    private int idInfo;
    
    /**
     * Información auxiliar que describe 
     * los datos que han producido el hito.
     */
    private String infoAux;
    
    /**
     * Identifica el tipo de suceso que genera al Hito.
     */
    private int idEvento;
    
    /**
     * Estado en el que se encuentra el Hito, pudiendo ser: Inicial, Bloqueado, Error, Tratado
     * 
     */
    private int estado;
    /**
     * Identificador de la aplicación que tiene bloqueado el Hito.
     */
    private int idAplicacion;
    /**
     * Dirección IP de la máquina cuya aplicación tiene bloqueado el Hito. 
     */
    private String IPMaquina;
    /**
     * Hora en la que se paso al estado indicado en <code>'estado'</code>. 
     * Este campo, entre otras cosas, será utilizado para
     * comprobar que la aplicación que tiene bloqueado el Hito no supera
     * un tiempo máximo de Bloqueo, en cuyo caso se volverá a situar en
     * estado Inicial para poder ser tratado por otra aplicación. 
     */
    private Timestamp timeStamp;
    
    
    /**
     * Identificador del sistema de externo, sistema en el que se ha recogido el hito
     */
    private String idSistema;
    
//==================================================================
//  CONSTRUCTORES
//==================================================================    
    /**
     * Construye un nuevo ActiveMilestoneVO inicilizando los atributos.
     */
    public ActiveMilestoneVO(){
        setInfoAux(null);
        setIPMaquina(null);
        setTimeStamp(null);
        setIdSistema(null);
    }
    
    /**
     *Construye un nuevo ActiveMilestoneVO inicializando todos sus atributos con los parámetros pasados al constructor.
     * @param idHito
     * @param idProcedimiento
     * @param idFase
     * @param idTramite
     * @param tipoDoc
     * @param idObjeto
     * @param infoAux
     * @param idEvento
     * @param estado
     * @param idAplicacion
     * @param IPMaquina
     * @param timeStamp
     * @param idSistema
     */
    public ActiveMilestoneVO(int idHito,int idProcedimiento,int idFase,
    		int idTramite,int tipoDoc,String idObjeto,int idInfo, 
    		String infoAux, int idEvento,int estado,int idAplicacion,
    		String IPMaquina,Timestamp timeStamp,String idSistema) {
        setIdHito(idHito);
        setIdProcedimiento(idProcedimiento);
        setIdFase(idFase);
        setIdTramite(idTramite);
        setTipoDoc(tipoDoc);
        setIdObjeto(idObjeto);
        setIdInfo(idInfo);
        setInfoAux(infoAux);
        setIdEvento(idEvento);
        setEstado(estado);
        setIdAplicacion(idAplicacion);
        setIPMaquina(IPMaquina);
        setTimeStamp(timeStamp);
        setIdSistema(idSistema);
    }


    public void init(DbQuery dbq) throws ISPACException {
    	if (dbq != null) {
			setIdHito(dbq.getInt("ID_HITO"));
			setIdProcedimiento(dbq.getInt("ID_PCD"));
			setIdFase(dbq.getInt("ID_FASE"));
			setIdTramite(dbq.getInt("ID_TRAMITE"));
			setTipoDoc(dbq.getInt("TIPO_DOC"));
			setIdObjeto(dbq.getString("ID_OBJETO"));
			setIdInfo(dbq.getInt("ID_INFO"));
			setInfoAux(dbq.getString("INFO_AUX"));
			setIdEvento(dbq.getInt("ID_EVENTO"));
			setEstado(dbq.getInt("ESTADO"));
			setIdAplicacion(dbq.getInt("ID_APLICACION"));
			setIPMaquina(dbq.getString("IP_MAQUINA"));
			setTimeStamp(TypeConverter.toTimestamp(dbq.getTimestamp("FECHA")));
			setIdSistema(dbq.getString("ID_SISTEMA"));
    	}
	}

//==================================================================
//   METODOS PARA LA SERIALIZACION
//==================================================================
    
    public String toString() {
        StringBuffer buf = new StringBuffer();

        
 
        
        buf.append(" Id_Hito: ");
        buf.append(getIdHito());

        buf.append(" Id_Procedimiento: ");
        buf.append(getIdProcedimiento());

        buf.append(" Id_Fase: ");
        buf.append(getIdFase());

        buf.append(" Id_Tramite: ");
        buf.append(getIdTramite());

        buf.append(" Tipo_Doc: ");
        buf.append(getTipoDoc());

        buf.append(" Id_Objeto: ");
        buf.append(getIdObjeto());

        buf.append(" Id_Info: ");
        buf.append(getIdInfo());
        
        buf.append(" Info_Aux: ");
        buf.append(getInfoAux());
        
        buf.append(" Id_Evento: ");
        buf.append(getIdEvento());
        
        buf.append(" Estado: ");
        buf.append(getEstado());
        
        buf.append(" IdAplicación: ");
        buf.append(getIdAplicacion());
        
        buf.append(" IPMáquina: ");
        buf.append(getIPMaquina());
        
        buf.append(" TimeStamp: ");
        buf.append(getTimeStamp());

        buf.append(" Id_Sistema: ");
        buf.append(getIdSistema());

        return buf.toString();
    }

    public String toXML() {
        StringBuffer buf = new StringBuffer("<hitoActivo>\n");

        buf.append("\t<idHito>");
        buf.append(getIdHito());
        buf.append("</idHito>\n");
        
        buf.append("\t<idProcedimiento>");
        buf.append(getIdProcedimiento());
        buf.append("</idProcedimiento>\n");

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

        buf.append("\t<idInfo>");
        buf.append(getIdInfo());
        buf.append("</idInfo>\n");

        buf.append("\t<infoAux>");
        buf.append(getInfoAux());
        buf.append("</infoAux>\n");

        buf.append("\t<idEvento>");
        buf.append(getIdEvento());
        buf.append("</idEvento>\n");        
        
        buf.append("\t<estado>");
        buf.append(getEstado());
        buf.append("</estado>\n");
        
        buf.append("\t<idAplicacion>");
        buf.append(getIdAplicacion());
        buf.append("</idAplicacion>\n");
        
        buf.append("\t<IPMaquina>");
        buf.append(getIPMaquina());
        buf.append("</IPMaquina>\n");
        
        buf.append("\t<timeStamp>");
        buf.append(getTimeStamp());
        buf.append("</timeStamp>\n");
        
        buf.append("\t<idSistema>");
        buf.append(getIdSistema());
        buf.append("</idSistema>\n");

        
        
        buf.append("</hitoActivo>");

        return buf.toString();
    }
    
//==================================================================
//     GETTERS   y   SETTERS
//==================================================================
    
    /**
     * @return Returns the estado.
     */
    public int getEstado() {
        return estado;
    }
    /**
     * @param estado The estado to set.
     */
    public void setEstado(int estado) {
        this.estado = estado;
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
     * @return Returns the IPMaquina.
     */
    public String getIPMaquina() {
        return IPMaquina;
    }
    /**
     * @param IPMaquina The IPMaquina to set.
     */
    public void setIPMaquina(String IPMaquina) {
        this.IPMaquina = IPMaquina;
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
     * @return Returns the timeStamp.
     */
    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    /**
     * @param timeStamp The timeStamp to set.
     */
    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
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
     * @return Returns the infoAux.
     */
    public String getInfoAux() {
        return infoAux;
    }
    /**
     * @param infoAux The infoAux to set.
     */
    public void setInfoAux(String infoAux) {
        this.infoAux = infoAux;
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

	public int getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(int idInfo) {
		this.idInfo = idInfo;
	}

}
