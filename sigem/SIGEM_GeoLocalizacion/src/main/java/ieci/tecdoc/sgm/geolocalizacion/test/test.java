package ieci.tecdoc.sgm.geolocalizacion.test;

import ieci.tecdoc.sgm.geolocalizacion.GeoLocalizacionManager;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			GeoLocalizacionManager.validarVia("BODEGA", 34083);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
