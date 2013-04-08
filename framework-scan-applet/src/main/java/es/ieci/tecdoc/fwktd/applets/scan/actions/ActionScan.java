package es.ieci.tecdoc.fwktd.applets.scan.actions;

import java.util.ArrayList;
import java.util.HashMap;

import es.ieci.tecdoc.fwktd.applets.scan.jtwain.JTwain;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.Scanner;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.ScannerIOException;
import es.ieci.tecdoc.fwktd.applets.scan.utils.Capabilities;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ParametrosVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PropertiesVO;


public class ActionScan{
	static Scanner scanner = null;

	public static void init(){
		scanner = Scanner.getDevice();
		try {
			scanner.openDSM();
		} catch (ScannerIOException e) {
			System.out.println("INFO: Se intenta abrir el datasource manager pero ya esta abierto.");
		}
	}

	public static void close(){
		try {
			if(scanner!=null){
				scanner.closeDSM();
			}
		} catch (ScannerIOException e) {
			System.out.println("INFO: Se intenta cerrar el datasource manager pero ya esta cerrado.");
		}
	}

	public static String getSourceDefautl(){
		String source = null;
		try {
			source=scanner.getDefaultDS();
		} catch (Exception e) {
			System.out.println("No se ha podido obtener el DataSource por defecto.");
			e.printStackTrace();
		}
		return source;
	}

	/**
	 * Metodo que devuelve la lista de fuentes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSources(){
		try {
			return scanner.getDeviceNames();
		} catch (ScannerIOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Escaneo de imagenes con perfil
	 * @param parametrosVO
	 * @param ui
	 * @return
	 */
	public static int scan(PerfilesVO perfiles, String code, String pathFile, ParametrosVO parametrosVO){
		int numImages = -1;
		PerfilVO perfilVO = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
		try {
			try{
				scanner.closeDS();
			}catch(Exception e){}
			// Se abre la conexion
			scanner.openDS (perfilVO.getDevice());


			// Se escanea
			//numImages = Scanner.acquireToJPEG(code, true, pathFile,perfilVO.isEnableUI());
			boolean showUI = perfilVO.isEnableUI();
			if(parametrosVO.getDummy()!=null && parametrosVO.getDummy().equals("1")){
				showUI = false;
			}
			if(!showUI)
			{
				// Se establecen las propiedades
				chargeProperties(perfilVO);
			}
			
			while(numImages==-1){
				numImages = scanner.acquireToFile(code, true, pathFile,showUI);
			}

			// Se cierra la conexion
			scanner.closeDS();
			return numImages;
		} catch (ScannerIOException e) {
			try {
				scanner.closeDS();
			} catch (ScannerIOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Escaneo de imagenes sin perfil
	 * @param parametrosVO
	 * @param device
	 * @return
	 */
	public static int scanUI(PerfilesVO perfiles, String code, String pathFile, ParametrosVO parametrosVO){
		String device = perfiles.getSourceDefault();

		try {
			if(device ==  null){
				device = scanner.getDefaultDS();
			}
			try{
				scanner.closeDS();
			}catch(Exception e){}

			scanner.openDS (device);

			//int i = Scanner.acquireToJPEG(code, true, pathFile,true);
			boolean showUI = true;
			if(parametrosVO.getDummy()!=null && parametrosVO.getDummy().equals("1")){
				showUI = false;
			}
			int i = scanner.acquireToFile(code, true, pathFile,showUI);

			scanner.closeDS();
			return i;
		} catch (ScannerIOException e) {
			try {
				scanner.closeDS();
			} catch (ScannerIOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return 0;
		}
	}

	public static PropertiesVO getProperties(String srcName){
		//Scanner.init();
		PropertiesVO propertiesVO = new PropertiesVO();
		try {
			scanner.openDS(srcName);
			try{
				propertiesVO.setSupportSizes(scanner.getSupportedSizes());
			}catch (Exception e) {
				System.out.println(e);
			}

			try{
				propertiesVO.setPixTypes(scanner.getPixelTypeList());
			}catch (Exception e) {
				System.out.println(e);
			}

			try{
				//scanner.setUnits(0);
				propertiesVO.setResolutionTypes(scanner.getXResolutionList());
				//float[] f = JTwain.getXNativeResolutionList();
				//float[] j = JTwain.getYNativeResolutionList();
				//float[] p = JTwain.getYResolutionList();
				//int[] z = JTwain.getUnitsList();
				//int za = JTwain.getUnits();
				//System.out.println(f[0]);

			}catch (Exception e) {
				System.out.println(e);
			}

			// Se valida si puede ser ADF el escaneo
			try{
				propertiesVO.setSupportADF(scanner.getADFSupport());
			}catch (Exception e) {
				System.out.println(e);
			}

			// Se valida si puede ser duplex el escaneo (SOLO si adf es posible)
			try{
				propertiesVO.setSupportDuplex(scanner.getDuplexSupport());
			}catch (Exception e) {
				System.out.println(e);
			}

			scanner.closeDS();
		} catch (ScannerIOException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return propertiesVO;
	}

	@SuppressWarnings("unchecked")
	private static void chargeProperties(PerfilVO perfilVO){

		// Resolution
		if(perfilVO.getResolution()!=null){
			try {
				scanner.setXResolution(Float.valueOf(perfilVO.getResolution()).floatValue());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ScannerIOException e) {
				System.out.println("Resolución no admitida: " + perfilVO.getResolution());
			}
		}

		// Color
		if(perfilVO.getColor()!=null){
			HashMap map2 = Capabilities.getColorCombo(1, 0);
			String value2 = perfilVO.getColor();
			try {
				scanner.setPixelType(Integer.parseInt(map2.get(value2).toString()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ScannerIOException e) {
				System.out.println("Color no admitido: " + map2.get(value2));
			}
		}

		// Paper Size
		if(perfilVO.getSize()!=null){
			HashMap map = Capabilities.getPaperSizeCombo(1, 0);
			String value = perfilVO.getSize();
			try {
				scanner.setSupportedSizes(Integer.parseInt(map.get(value).toString()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ScannerIOException e) {
				System.out.println("Tamaño no admitido: " + map.get(value));
			}
		}

		// ADF
		if(perfilVO.isAdf()){
			try {
				scanner.setADFEnabled(perfilVO.isAdf());
			} catch (ScannerIOException e) {
				System.out.println("ADF no admitido");
			}
		}

		// Duplex
		if(perfilVO.isDuplex()){
			try {
				scanner.setDuplexEnabled(perfilVO.isDuplex());
			} catch (ScannerIOException e) {
				System.out.println("Duplex no admitido");
			}
		}

		// Contrast
		if(perfilVO.getContrast()!=0){
			try {
				scanner.setContrast(perfilVO.getContrast());
			} catch (ScannerIOException e) {
				System.out.println("Contraste no admitido");
			}
		}

		// Brigth
		if(perfilVO.getBright()!=0){
			try {
				scanner.setBright(perfilVO.getBright());
				//Scanner.setBright(1000);
			} catch (ScannerIOException e) {
				System.out.println("Brillo no admitido");
			}
		}

		// JPG Compresion
		if(perfilVO.isCompress()){
			try {
				scanner.setCompressJPEG();
			} catch (ScannerIOException e) {
				System.out.println("Compresion JPEG no admitido");
			}

			// JPGQuality
			try {
				scanner.setJPGQuality(perfilVO.getQuality());
			} catch (ScannerIOException e) {
				System.out.println("Calidad JPEG no admitido");
			}
		}
	}
}
