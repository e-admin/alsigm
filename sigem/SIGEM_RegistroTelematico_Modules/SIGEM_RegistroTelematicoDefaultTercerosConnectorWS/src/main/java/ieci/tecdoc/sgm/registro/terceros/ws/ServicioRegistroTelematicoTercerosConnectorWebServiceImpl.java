package ieci.tecdoc.sgm.registro.terceros.ws;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal;
import ieci.tecdoc.sgm.core.services.terceros.dto.Tercero;

import java.util.List;

import org.apache.log4j.Logger;

public class ServicioRegistroTelematicoTercerosConnectorWebServiceImpl
		implements ServicioRegistroTelematicoTercerosConnectorWebService {

	private static final Logger logger = Logger.getLogger(ServicioRegistroTelematicoTercerosConnectorWebServiceImpl.class);

	public TerceroVO buscarTercero(String identificador) {
		return null;
	}

	public TerceroVO buscarTerceroPorEntidad(String entidad,
			String identificador) {
		TerceroVO terceroResult = null;
		try {

			ServicioTerceros servicioTerceros = LocalizadorServicios
					.getServicioTerceros();
			List listaTerceros = servicioTerceros
					.lookup(entidad, identificador, false);
			if (listaTerceros != null && listaTerceros.size() > 0) {
				Tercero tercero = (Tercero) listaTerceros.get(0);
				terceroResult = new TerceroVO();
				terceroResult.setTerceroId(tercero.getIdExt());
				terceroResult.setNombre(tercero.getNombre());
				terceroResult.setPrimerApellido(tercero.getPrimerApellido());
				terceroResult.setSegundoApellido(tercero.getSegundoApellido());
				terceroResult.setDirecciones(getDireccionesTercero(tercero));

			}
		} catch (Exception e) {
			logger.warn("ServicioRegistroTelematicoTercerosConnectorWS : Error al obtener los datos y direcciones del tercero: "+identificador,e);
		}
		return terceroResult;
	}

	private ArrayOfDireccionTerceroVO getDireccionesTercero(Tercero tercero) {
		ArrayOfDireccionTerceroVO direcciones = new ArrayOfDireccionTerceroVO();
		DireccionPostal porDefecto = tercero.getDireccionPostalPredeterminada();
		if (tercero.getDireccionesPostales() != null)
			for (int i = 0; i < tercero.getDireccionesPostales().length; i++) {
				DireccionPostal direccion = tercero.getDireccionesPostales()[i];
				DireccionTerceroVO direccionTercero = new DireccionTerceroVO();
				direccionTercero.setCodigoPostal(direccion.getCodigoPostal());
				direccionTercero.setIdDireccion(direccion.getId());
				direccionTercero.setLocalidad(direccion.getPoblacion());
				direccionTercero.setProvincia(direccion.getProvincia());
				direccionTercero.setMunicipio(direccion.getMunicipio());
				direccionTercero.setPiso(direccion.getPiso());
				direccionTercero.setPortal(direccion.getBloque());
				direccionTercero.setPuerta(direccion.getPuerta());
				direccionTercero.setVia(direccion.getVia());
				direccionTercero.setTipoVia(direccion.getTipoVia());
				direccionTercero.setDireccionCompleta(direccion.getDireccionPostal());
				if(direccion.getPoblacion()==null)
				{
					direccionTercero.setLocalidad(direccion.getMunicipio());
				}
				if (direccion.getId().equals(porDefecto.getId())) {
					direccionTercero.setPorDefecto(true);
				}
				direcciones.getItem().add(direccionTercero);
			}
		return direcciones;
	}

}
