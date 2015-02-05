package fondos.vos;

import java.util.Date;
import java.util.List;

/**
 * Interfaz para el Value Object de Serie Documental
 */
public interface SerieVO extends ElementoCuadroClasificacionVO {

	public int getEstadoserie();

	public void setEstadoserie(int estadoserie);

	public Date getFextremafinal();

	public void setFextremafinal(Date fextremafinal);

	public Date getFextremainicial();

	public void setFextremainicial(Date fextremainicial);

	public String getDenominacion();

	public void setDenominacion(String denominacion);

	public Date getFechaestado();

	public void setFechaestado(Date fechaestado);

	public String getIdelementocf();

	public void setIdelementocf(String idelementocf);

	public String getIdentificacion();

	public void setIdentificacion(String identificacion);

	public String getIdusrgestor();

	public void setIdusrgestor(String idusrgestor);

	public int getNumuinstalacion();

	public void setNumuinstalacion(int numuinstalacion);

	public int getNumunidaddoc();

	public void setNumunidaddoc(int numunidaddoc);

	public String getObservaciones();

	public void setObservaciones(String observaciones);

	public int getUltimoestadoautoriz();

	public void setUltimoestadoautoriz(int ultimoestadoautoriz);

	public String getIdProcedimiento();

	public void setIdProcedimiento(String tipoProcedimiento);

	public int getTipoProcedimiento();

	public void setTipoProcedimiento(int tipoProcedimiento);

	public double getVolumen();

	public void setVolumen(double volumen);

	public String getIdRepEcmPrefUdoc(String idNivel);

	public String getIdFichaDescrPrefUdoc(String idNivel);

	public boolean getPermitidoIniciarValoracion();

	public void setInfoFichaUDocRepEcm(String infoFichaUDocRepEcm);

	public String getInfoFichaUDocRepEcm();

	public void setNivelesFichaUDocRepEcm(List nivelesFichaUDocRepEcm);

	public List getNivelesFichaUDocRepEcm();

	public String getIdFichaClfDocPrefUdoc(String idNivel);

	/**
	 * Comprueba si la serie está en estudio.
	 *
	 * @return
	 */
	public boolean isEnEstudio();

}
