package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

/**
 * Clase  que representa la unidad de tramitación correspondiente con el directorio comun
 * Incialmente lo mapearemos en nuestro modelo de datos en la tabla scr_tramunit
 * @author Iecisa
 *
 */
public class UnidadTramitacionIntercambioRegistralVO extends
		BaseIntercambioRegistralVO {

	/**
	 * idetificador unico
	 */
	protected String id;


	/**
	 * codigo correspondiente a la entidad registral en el del directorio comun
	 */
	protected String codeEntity;

	/**
	 * nombre correspondiente a la entidad registral en el del directorio comun
	 */
	protected String nameEntity;

	/**
	 * codigo correspondiente a la unidad de tramitación en el directorio común
	 */
	protected String codeTramunit;
	/**
	 * nombre correspondiente a la unidad de tramitación en el directorio común
	 */
	protected String nameTramunit;


	/**
	 * identificador de mapeo correpondiente con el id de la tabla scr_orgs
	 */
	protected String idOrgs;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeEntity() {
		return codeEntity;
	}

	public void setCodeEntity(String code) {
		this.codeEntity = code;
	}

	public String getNameEntity() {
		return nameEntity;
	}

	public void setNameEntity(String name) {
		this.nameEntity = name;
	}

	public String getIdOrgs() {
		return idOrgs;
	}

	public void setIdOrgs(String idOrgs) {
		this.idOrgs = idOrgs;
	}

	public String getCodeTramunit() {
		return codeTramunit;
	}

	public void setCodeTramunit(String codeTramunit) {
		this.codeTramunit = codeTramunit;
	}

	public String getNameTramunit() {
		return nameTramunit;
	}

	public void setNameTramunit(String nameTramunit) {
		this.nameTramunit = nameTramunit;
	}


}
