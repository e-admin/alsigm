package ieci.tecdoc.sgm.registro.terceros.ws;



public class ServicioRegistroTelematicoTercerosConnectorWebService {

	public TerceroVO buscarTercero(String identificador) {
		TerceroVO tercero = new TerceroVO();
		DireccionTerceroVO direccion = new DireccionTerceroVO();
		direccion.setCodigoPostal("33007");

		tercero.setNombre("david");
		tercero.setDirecciones(new DireccionTerceroVO[]{direccion});
		return tercero;
	}
	public TerceroVO buscarTerceroPorEntidad(String entidad,String identificador) {
		TerceroVO tercero = new TerceroVO();
		DireccionTerceroVO direccion = new DireccionTerceroVO();
		direccion.setCodigoPostal("33007");

		tercero.setNombre("david");
		tercero.setDirecciones(new DireccionTerceroVO[]{direccion});
		return tercero;
	}

}
