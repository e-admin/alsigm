package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbInitializable;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispacpublicador.business.vo.BaseVO;

/**
 * Interesado de un expediente.
 *
 */
public class InteresadoVO extends BaseVO implements DbInitializable {

	/** Número del expediente. */
	private String cnumexp = null;
	
	/** NIF del interesado. */
	private String cnif = null;
	
	/** Nombre del interesado. */
	private String cnom = null;
	
	
	/** 
	 * Especifica si el interesado es el interesado principal del expediente.
	 * Valores: S / N
	 */
	private String cprincipal = null;
	
	/** Información auxiliar del interesado. Documento XML. */
	private String cinfoaux = null;
	
	
	/**
	 * Constructor.
	 *
	 */
	public InteresadoVO() {
		super();
	}

    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
			setCnumexp(dbq.getString("NUMEXP"));
			setCnif(dbq.getString("NDOC"));
			setCnom(dbq.getString("NOMBRE"));
			setCprincipal("INT".equals(dbq.getString("ROL")) ? "S" : "N");
			setCinfoaux(null);
		}
    }


	public String getCinfoaux() {
		return cinfoaux;
	}


	public void setCinfoaux(String cinfoaux) {
		this.cinfoaux = cinfoaux;
	}


	public String getCnif() {
		return cnif;
	}


	public void setCnif(String cnif) {
		this.cnif = cnif;
	}


	public String getCnom() {
		return cnom;
	}


	public void setCnom(String cnom) {
		this.cnom = cnom;
	}


	public String getCnumexp() {
		return cnumexp;
	}


	public void setCnumexp(String cnumexp) {
		this.cnumexp = cnumexp;
	}


	public String getCprincipal() {
		return cprincipal;
	}


	public void setCprincipal(String cprincipal) {
		this.cprincipal = cprincipal;
	}
}
