package ieci.tecdoc.sgm.registro.terceros.connector.impl;

import java.util.ArrayList;

import ieci.tecdoc.sgm.registro.terceros.connector.ServicioRegistroTelematicoTercerosConnector;
import ieci.tecdoc.sgm.registro.terceros.connector.vo.DireccionTerceroVO;
import ieci.tecdoc.sgm.registro.terceros.connector.vo.TerceroVO;

public class TercerosConnectorMockImpl implements ServicioRegistroTelematicoTercerosConnector{

	public TerceroVO buscarTercero(String identificador) {
		TerceroVO tercero = new TerceroVO();
		DireccionTerceroVO direccion = new DireccionTerceroVO();
		direccion.setCodigoPostal("33006");
		direccion.setLocalidad("Oviedo");
		direccion.setDireccionCompleta("Calle Asturcón, Nº 22B, 1C");
		direccion.setMunicipio("Oviedo");
		direccion.setProvincia("Asturias");
		direccion.setIdDireccion("1");
		DireccionTerceroVO direccion2 = new DireccionTerceroVO();
		direccion2.setCodigoPostal("33970");
		direccion2.setLocalidad("Barredos");
		direccion2.setMunicipio("Laviana");
		direccion2.setProvincia("Asturias");
		direccion2.setPorDefecto(true);
		direccion2.setIdDireccion("2");
		direccion2.setDireccionCompleta("Calle Peña Mayor, Nº 21, 3D");
		direccion2.setPorDefecto(true);
		ArrayList<DireccionTerceroVO> direcciones = new ArrayList<DireccionTerceroVO>();
		direcciones.add(direccion);
		direcciones.add(direccion2);
		tercero.setNombre("david");
		tercero.setDirecciones(direcciones);

		return tercero;
	}
	public TerceroVO buscarTerceroPorEntidad(String entidad,String identificador) {
		TerceroVO tercero = new TerceroVO();
		DireccionTerceroVO direccion = new DireccionTerceroVO();
		direccion.setCodigoPostal("33006");
		direccion.setLocalidad("Oviedo");
		direccion.setDireccionCompleta("Calle Asturcón, Nº 22B, 1C");
		direccion.setMunicipio("Oviedo");
		direccion.setProvincia("Asturias");
		direccion.setIdDireccion("1");
		DireccionTerceroVO direccion2 = new DireccionTerceroVO();
		direccion2.setCodigoPostal("33970");
		direccion2.setLocalidad("Barredos");
		direccion2.setMunicipio("Laviana");
		direccion2.setProvincia("Asturias");
		direccion2.setPorDefecto(true);
		direccion2.setIdDireccion("2");
		direccion2.setDireccionCompleta("Calle Peña Mayor, Nº 21, 3D");
		direccion2.setPorDefecto(true);
		ArrayList<DireccionTerceroVO> direcciones = new ArrayList<DireccionTerceroVO>();
		direcciones.add(direccion);
		direcciones.add(direccion2);
		tercero.setNombre("david");
		tercero.setDirecciones(direcciones);

		return tercero;
	}
}
