package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacpublicador.business.vo.BaseVO;

import java.util.Date;

/**
 * Información del expediente
 *
 */
public class ExpedienteVO extends BaseVO implements DbInitializable {

	/** Número del expediente. */
	private String cnum = null;
	
	/** Procedimiento del expediente. */
	private String cproc = null;
	
	/** Fecha y hora de inicio del expediente. */
	private Date cfhinicio = null;
	
	/** Número del registro que inicia el expediente. */
	private String cnumregini = null;
	
	/** Fecha y hora del registro que inicia el expediente. */
	private Date cfhregini = null;
	
	/** Información auxiliar del expediente. Documento XML. */
	private String cinfoaux = null;
	
	/** 
	 * Especifica si se pueden realizar aportaciones al expediente.
	 * Valores: S / N 
	 */
	private String caportacion = null;
	
	/** Código de presentación. */
	private String ccodpres = null;

	
	/**
	 * Constructor.
	 *
	 */
	public ExpedienteVO() {
		super();
	}

    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
			setCnum(dbq.getString("NUMEXP"));
			
			String proc = new StringBuffer()
				.append(StringUtils.nullToEmpty(dbq.getString("CODPROCEDIMIENTO")))
				.append(" ")
				.append(StringUtils.nullToEmpty(dbq.getString("NOMBREPROCEDIMIENTO")))
				.toString().trim();
			
			setCproc(proc);
			setCfhinicio(dbq.getDate("FAPERTURA"));
			setCnumregini(dbq.getString("NREG"));
			setCfhregini(dbq.getDate("FREG"));
			setCinfoaux(null);
			setCaportacion(null);
			setCcodpres(null);
		}
    }


	public String getCaportacion() {
		return caportacion;
	}


	public void setCaportacion(String caportacion) {
		this.caportacion = caportacion;
	}


	public String getCcodpres() {
		return ccodpres;
	}


	public void setCcodpres(String ccodpres) {
		this.ccodpres = ccodpres;
	}


	public Date getCfhinicio() {
		return cfhinicio;
	}


	public void setCfhinicio(Date cfhinicio) {
		this.cfhinicio = cfhinicio;
	}


	public Date getCfhregini() {
		return cfhregini;
	}


	public void setCfhregini(Date cfhregini) {
		this.cfhregini = cfhregini;
	}


	public String getCinfoaux() {
		return cinfoaux;
	}


	public void setCinfoaux(String cinfoaux) {
		this.cinfoaux = cinfoaux;
	}


	public String getCnum() {
		return cnum;
	}


	public void setCnum(String cnum) {
		this.cnum = cnum;
	}


	public String getCnumregini() {
		return cnumregini;
	}


	public void setCnumregini(String cnumregini) {
		this.cnumregini = cnumregini;
	}


	public String getCproc() {
		return cproc;
	}


	public void setCproc(String cproc) {
		this.cproc = cproc;
	}
}
