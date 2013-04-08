package auditoria.vos;

import common.vos.BaseVO;

/**
 * VO que encapsula los datos sobre la criticidad de usuario y grupos de
 * usuarios.
 */
public class CritUsuarioVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Cte global para el tipo de usuario */
	public static final int TIPO_USUARIO = 0;
	public static final int TIPO_GRUPO = 1;
	public static final int TIPO_ROL = 2;

	/**
	 * Grupo de usuarios en el que se encapsula el nivel de log global de la
	 * aplicacion
	 */
	public static final String GLOBAL_GROUP = "0";
	/**
	 * Grupo de usuarios administradores en el que se encapsula el nivel de log
	 * global de la aplicacion
	 */
	public static final String GLOBAL_GROUP_ADM = "1";

	/** Identificador del tipo objeto que vamos a auditar */
	private String tipoAuditado = null;
	/** Identificador del sujeto de auditoría */
	private String idAuditado = null;
	/** Identificador del nivel de criticidad de auditoria */
	private int nivel = 0;
	/** Nombre del grupo */
	private String name = null;

	public String getIdAuditado() {
		return idAuditado;
	}

	public void setIdAuditado(String idAuditar) {
		this.idAuditado = idAuditar;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int idNivel) {
		this.nivel = idNivel;
	}

	public String getTipoAuditado() {
		return tipoAuditado;
	}

	public void setTipoAuditado(String tipoAuditar) {
		this.tipoAuditado = tipoAuditar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Redefinición del metodo equal.Son iguales si coinciden el tipo y el id
	 */
	public boolean equals(Object arg) {
		boolean resultado = false;

		if (arg != null && (arg instanceof CritUsuarioVO)) {
			CritUsuarioVO d = (CritUsuarioVO) arg;

			if (d.getIdAuditado() != null && this.idAuditado != null
					&& d.getIdAuditado().equals(this.idAuditado)
					&& d.getTipoAuditado() != null && this.tipoAuditado != null
					&& d.getTipoAuditado().equals(this.tipoAuditado))
				resultado = true;
			else
				resultado = false;
		} else
			resultado = false;

		return resultado;
	}
}
