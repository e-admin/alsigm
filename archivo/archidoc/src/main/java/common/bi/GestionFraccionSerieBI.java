package common.bi;

import java.util.List;

import common.exceptions.ActionNotAllowedException;

import fondos.actions.UnidadDocumentalPO;
import fondos.forms.DivisionFraccionSerieForm;
import fondos.model.UnidadDocumental;
import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.UDocEnFraccionSerieVO;

/**
 * Interface del servicio de gestión de unidades documentales
 */
public interface GestionFraccionSerieBI {

	public void createUDocsFraccionSerie(DivisionFraccionSerieVO divisionFS,
			UnidadDocumental udoc, List udocsEnFraccionSerie);

	// public DivisionFraccionSerieVO createDivisionFS(DivisionFraccionSerieVO
	// divisionFSVO);
	public List createDivisionFS(DivisionFraccionSerieVO divisionFSVO,
			UnidadDocumentalPO udoc, DivisionFraccionSerieForm udocForm,
			GestionFraccionSerieBI fraccionSerieBI, List rangos);

	public DivisionFraccionSerieVO searchDivisionFS(String idFS);

	public List getUDocsEnDivisionFS(String idFS, boolean tieneDescripcion);

	public void deleteUDocEnDivisionFS(String idUDoc,
			boolean eliminarDescripcion);

	public void deleteUDocsEnDivisionFS(String[] idsUDoc,
			boolean eliminarDescripcion);

	public UDocEnFraccionSerieVO addUDocToDivisionFS(
			UDocEnFraccionSerieVO udocEnFS, DivisionFraccionSerieVO divisionFS,
			boolean mantenerInfo);

	public int getCountDivisionesFSEnElaboracion();

	public int getCountDivisionesFSFinalizadas();

	public List getDivisionesFSEnElaboracion();

	public List getDivisionesFSFinalizadas();

	public void deleteFraccionesSerie(String[] idsDivisionFS)
			throws ActionNotAllowedException;

	public UDocEnFraccionSerieVO getUDocEnDivisionFS(String idUDoc);

	public void updateUDocEnDivisionFS(
			UDocEnFraccionSerieVO udocEnFraccionSerieVO);

	public UDocEnFraccionSerieVO getUDocEnDivisionFSConInfoDesc(String idUDoc);

	public void conservarDescripcion(String idUDocOrigen, String idUDocDestino);

	public List getRangosUDoc(String idUDoc, String idCampoInicial,
			String idCampoFinal);

	/**
	 * Permite obtener la siguiente unidad documental a una pasada dentro de la
	 * division de fraccion de serie
	 * 
	 * @param idDivFs
	 *            Identificador de la division de fraccion de serie
	 * @param orden
	 *            Orden de la unidad documental en la division de fraccion de
	 *            serie
	 * @return {@link UDocEnFraccionSerieVO}
	 */
	public UDocEnFraccionSerieVO getNextUdocEnDivisionFs(String idDivFs,
			int orden);

	/**
	 * Permite obtener la anterior unidad documental a una pasada dentro de la
	 * division de fraccion de serie
	 * 
	 * @param idDivFs
	 *            Identificador de la division de fraccion de serie
	 * @param orden
	 *            Orden de la unidad documental en la division de fraccion de
	 *            serie
	 * @return {@link UDocEnFraccionSerieVO}
	 */
	public UDocEnFraccionSerieVO getPrevUdocEnDivisionFs(String idDivFs,
			int orden);
}
