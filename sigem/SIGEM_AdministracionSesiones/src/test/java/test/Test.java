package test;

import ieci.tecdoc.sgm.sesiones.SesionManager;

public class Test {
	
	public static void main(String[] args) {
		String key1 = SesionManager.nuevaSesion("jose", "00001");
		String key2 = SesionManager.nuevaSesion("sigem", "00002");
		SesionManager.caducarSesion(key1);
		boolean val1 = SesionManager.validarSesion(key2);
		boolean val2 = SesionManager.validarSesion(key1);
		
		System.out.println(key1 + "-" + key2 + "-" + val1 + "-" + val2);
	}
	
}
