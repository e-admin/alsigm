/**
 *
 */
package transferencias.vos;

import common.view.IVisitedRowVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IParteUnidadDocumentalVO extends IVisitedRowVO {

	public abstract String getIdRelentrega();

	public abstract String getIdUinstalacionRe();

	public abstract String getIdUnidadDoc();

	public abstract String getNotasCotejo();

	public abstract void setNotasCotejo(String notasCotejo);

	public abstract int getNumParteUdoc();

	public abstract int getPosUdocEnUI();

	public abstract String getSignaturaUdoc();

	public abstract void setSignaturaUdoc(String signaturaUdoc);

	public abstract String getUdocCompleta();

	public abstract int getEstadoCotejo();

	public abstract void setEstadoCotejo(int estadoCotejo);

	public abstract void setAsunto(String asunto);

	public abstract String getAsunto();

	public abstract String getExpediente();

	public abstract void setExpediente(String expediente);

	public abstract boolean isConErrores();

	public abstract boolean isRevisada();

	public abstract String getDescContenido();

	public abstract String getValorFechaFin();

	public abstract String getValorFechaInicio();

	public abstract int getTotalPartes();

	public abstract String getParteExp();

	public abstract int getPosCaja();

}