package ieci.tecdoc.sgm.registro.terceros.connector.helper;

import java.util.ArrayList;
import java.util.List;

import ieci.tecdoc.sgm.registro.terceros.connector.vo.DireccionTerceroVO;
import ieci.tecdoc.sgm.registro.terceros.connector.vo.TerceroVO;

/**
 * Helper para convertir los VO de los servicios web a los VO del API
 * @author iecisa
 *
 */
public class AdapterTerceroHelper {

	public static TerceroVO getTerceroVO(ieci.tecdoc.sgm.registro.terceros.ws.TerceroVO tercero)
	{

		TerceroVO terceroVO = null;
		if(tercero!=null)
		{
			terceroVO = new TerceroVO();
			terceroVO.setNombre(tercero.getNombre());
			terceroVO.setPrimerApellido(tercero.getPrimerApellido());
			terceroVO.setSegundoApellido(tercero.getSegundoApellido());
			terceroVO.setIdentificador(tercero.getIdentificador());
			terceroVO.setDirecciones(AdapterTerceroHelper.getDireccionesTerceroVO(tercero.getDirecciones()));
		}
		return terceroVO;

	}

	public static List<DireccionTerceroVO> getDireccionesTerceroVO(
			ieci.tecdoc.sgm.registro.terceros.ws.DireccionTerceroVO[] direcciones) {
		ArrayList<DireccionTerceroVO> direccionesTercero = new ArrayList<DireccionTerceroVO>();
		for (ieci.tecdoc.sgm.registro.terceros.ws.DireccionTerceroVO direccion : direcciones) {
			DireccionTerceroVO direccionTercero = new DireccionTerceroVO();
			direccionTercero.setCodigoPostal(direccion.getCodigoPostal());
			direccionTercero.setDireccionCompleta(direccion.getDireccionCompleta());
			direccionTercero.setIdDireccion(direccion.getIdDireccion());
			direccionTercero.setLocalidad(direccion.getLocalidad());
			direccionTercero.setMunicipio(direccion.getMunicipio());
			direccionTercero.setPais(direccion.getPais());
			direccionTercero.setPiso(direccion.getPiso());
			direccionTercero.setPorDefecto(direccion.isPorDefecto());
			direccionTercero.setPortal(direccion.getPortal());
			direccionTercero.setProvincia(direccion.getProvincia());
			direccionTercero.setPuerta(direccion.getPuerta());
			direccionTercero.setTipoVia(direccion.getTipoVia());
			direccionTercero.setVia(direccion.getVia());
			direccionesTercero.add(direccionTercero);
		}
		return direccionesTercero;
	}

}
