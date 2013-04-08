package transferencias.db;

import java.util.Collection;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import transferencias.vos.BusquedaPrevisionesVO;
import transferencias.vos.PrevisionVO;

import common.manager.ArchidocDBBaseTest;

/**
 * Implementación de los métodos de acceso a datos referidos a previsiones de
 * transferencia
 */
public class PrevisionDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IPrevisionDBEntity getDAO() {
		return getManager().getPrevisionDBEntity();
	}

	public void insertarPrevision() {
		getDAO().insertarPrevision(null);
	}

	@Test
	public void getInfoPrevision() {
		PrevisionVO previsionVO = getDAO().getInfoPrevision(idPrevision);
		Assert.assertNotNull(previsionVO);
	}

	@Test
	public void getPrevisionesXIds() {
		Collection<PrevisionVO> lista = getDAO().getPrevisionesXIds(
				new String[] { idPrevision });
		Assert.assertNotNull(lista);
		Assert.assertEquals(1, lista.size());
	}

	@Test
	public void getPrevisionesXIdOrgRemitenteYAno() {
		Collection<PrevisionVO> lista = getDAO()
				.getPrevisionesXIdOrgRemitenteYAno(idOrgRemitente, anioString);
		Assert.assertNotNull(lista);
		Assert.assertEquals(1, lista.size());

	}

	@Test
	public void getCountPrevisionesXArchivoReceptor() {
		getDAO().getCountPrevisionesXArchivoReceptor(
				new String[] { idArchivoReceptor },
				new int[] { estadoPrevision }, anioString);
	}

	 public void getPrevisionesXArchivoReceptor(String[] idArchivoReceptor,
	 int[] estados, String ano) {
	 }

	 public void getPrevisionesXIdOrgRemitenteYEstados(String[]
	 organoRemitente,
	 int[] estados) {
	 }


	 public void getPrevisionesXGestor(String idUserGestor, int[] estados,
	 String ano) {
	 }

	 public void getPrevisionesXGestorYTipos(String idUserGestor, int[]
	 estados,
	 int[] tipos, String ano) {
	 }


	 public void getPrevisionesXGestorYTipoTrans(String idUserGestor,
	 int tipotransferencia, int[] estados, String ano) {
	 }


	 public void getPrevisionesXAnio(int[] estados, String ano) {

	 }

	 public void getPrevisionesXAnioYArchivo(int[] estados, String ano,
	 String[] idsArchivo) {
	 }

	 public void getCountPrevisionesXGestor(String idUser,
	 int[] estadosPrevision, String ano) {
	 }


	 public void updatePrevision(PrevisionVO prevision) {
	 }

	 public void updateNUnidadesInstalacion(String idPrevision,
	 int nUnidadesInstalacion) {
	 }

	 public void updateEstado_FechaEstado_FechasTransf_motivoRechazo(){

	 }

	 public void updateUsrgestor(String[] codigosPrevisiones, String
	 idNewUser) {
	 }

	 public void updateEstadoAndFechaEstado(String idPrevision, int
	 nuevoEstado,
	 Date nuevaFecha) {
	 }

	 public void updateEstado_FechaEstado_observaciones(String idPrevision,
	 int estado, Date fechaEstado, String observaciones) {
	 }

	 public void updateEstado_FechaEstado_motivoRechazo(String idPrevision,
	 int estado, Date fechaEstado, String motivoRechazo) {

	 }

	 public void deletePrevision(String idPrevision) {

	 }

	 public void cerrarPrevision(String codigoPrevision) {
	 }


	@Test
	public void getPrevisiones() throws Exception {
		getDAO().getPrevisiones(new BusquedaPrevisionesVO());
	}

	@Test
	public void getGestoresConPrevision() {
		getDAO().getGestoresConPrevision(idOrgRemitente,
				new int[] { tipoTransferencia });
	}


	@Test
	public void getPrevisionesCaducadas() {
		getDAO().getPrevisionesCaducadas();
	}

	@Test
	public void getPrevisionByVO() {
		PrevisionVO previsionMock = getPrevisionVOElectronica();
		PrevisionVO previsionBD = getDAO().getPrevisionByVO(previsionMock);
		Assert.assertNotNull(previsionBD);
	}

}