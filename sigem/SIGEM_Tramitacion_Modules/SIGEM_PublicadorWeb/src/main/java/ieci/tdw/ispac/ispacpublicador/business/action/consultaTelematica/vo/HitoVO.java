package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo;

import ieci.tdw.ispac.ispacpublicador.business.vo.BaseVO;

import java.util.Date;

/**
 * Hito de un expediente.
 *
 */
public class HitoVO extends BaseVO {
	
	/** Número del expediente. */
	private String cnumexp = null;
	
	/** GUID del hito. */
	private String cguid = null;
	
	/** Código del hito. Reservado. */
	private String ccod = null;
	
	/** Fecha del hito. */
	private Date cfecha = null;
	
	/** Descripción del hito. */
	private String cdescr = null;
	
	/** Información auxiliar. Documento XML. */
	private String cinfoaux = null;
	
	
	/**
	 * Constructor.
	 *
	 */
	public HitoVO() {
		super();
	}


	public String getCcod() {
		return ccod;
	}


	public void setCcod(String ccod) {
		this.ccod = ccod;
	}


	public String getCdescr() {
		return cdescr;
	}


	public void setCdescr(String cdescr) {
		this.cdescr = cdescr;
	}


	public Date getCfecha() {
		return cfecha;
	}


	public void setCfecha(Date cfecha) {
		this.cfecha = cfecha;
	}


	public String getCguid() {
		return cguid;
	}


	public void setCguid(String cguid) {
		this.cguid = cguid;
	}


	public String getCinfoaux() {
		return cinfoaux;
	}


	public void setCinfoaux(String cinfoaux) {
		this.cinfoaux = cinfoaux;
	}


	public String getCnumexp() {
		return cnumexp;
	}


	public void setCnumexp(String cnumexp) {
		this.cnumexp = cnumexp;
	}

}
