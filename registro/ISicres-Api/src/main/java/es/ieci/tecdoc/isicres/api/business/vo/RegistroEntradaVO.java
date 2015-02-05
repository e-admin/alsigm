package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public class RegistroEntradaVO extends BaseRegistroVO {

	private static final long serialVersionUID = -7971158816868758749L;

	/**
	 * Datos del registro originario de este registro
	 */
	protected RegistroOriginalVO registroOriginal;

	protected String referenciaExpediente;

	public String getReferenciaExpediente() {
		return referenciaExpediente;
	}

	public void setReferenciaExpediente(String referenciaExpediente) {
		this.referenciaExpediente = referenciaExpediente;
	}

	/**
	 * @return el registroOriginal
	 */
	public RegistroOriginalVO getRegistroOriginal() {
		if (null == registroOriginal) {
			registroOriginal = new RegistroOriginalVO();
		}
		return registroOriginal;
	}

	/**
	 * @param registroOriginal
	 *            el registroOriginal a fijar
	 */
	public void setRegistroOriginal(RegistroOriginalVO registroOriginal) {
		this.registroOriginal = registroOriginal;
	}

}
