package es.ieci.tecdoc.fwktd.applets.scan.scanner;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
abstract public class Scanner{
	//protected ScannerIOMetadata   metadata=null;                    // information structure
	//public abstract void       select(String name)throws ScannerIOException;
	//public abstract void       setCancel(boolean c)throws ScannerIOException;
	//public abstract void       select()throws ScannerIOException;	
    
	
	//Indica si existe la libreria
	public abstract boolean isAPIInstalled(); 

	//Listado de dispositivos disponibles
	public abstract ArrayList getDeviceNames()throws ScannerIOException;

	//Obtiene el dispositivo por defecto
	public abstract String getDefaultDS() throws ScannerIOException;
	
	
	//Abre el data source manager
	public abstract void openDSM() throws ScannerIOException;
	
	//Cierra el data source manager
	public abstract void closeDSM() throws ScannerIOException; 
	
	//Cierra el recurso del dispositivo
	public abstract void closeDS() throws ScannerIOException;
	
	//Asigna el dispositivo seleccionado al scanner
	public abstract void openDS(String device) throws ScannerIOException;
	
	//Escanea
	public abstract int acquireToFile(String code, boolean b, String pathFile, boolean c) throws ScannerIOException;
	
	public abstract int[] getSupportedSizes() throws ScannerIOException;
	public abstract int[] getPixelTypeList() throws ScannerIOException;
	public abstract float[] getXResolutionList() throws ScannerIOException;
	public abstract void setUnits(int i) throws ScannerIOException;
	public abstract boolean getDuplexSupport() throws ScannerIOException;
	public abstract boolean getADFSupport() throws ScannerIOException;
	public abstract void setXResolution(float floatValue) throws ScannerIOException;
	public abstract void setPixelType(int parseInt) throws ScannerIOException;
	public abstract void setSupportedSizes(int parseInt) throws ScannerIOException;
	public abstract void setDuplexEnabled(boolean duplex) throws ScannerIOException;
	public abstract void setADFEnabled(boolean adf) throws ScannerIOException;
	public abstract void setContrast(float contrast) throws ScannerIOException;
	public abstract void setBright(float bright) throws ScannerIOException;
	public abstract void setCompressJPEG() throws ScannerIOException;
	public abstract void setJPGQuality(int quality) throws ScannerIOException;
	
	
	//Selecciona Jtwain o Jsane
	static public Scanner getDevice(){	
		String osname    = System.getProperty("os.name");
	    String cn;
	    if(osname.startsWith("Linux")){
	      cn = "uk.co.mmscomputing.device.sane.SaneScanner";
	    }else if(osname.startsWith("Windows")){
	      cn = "es.ieci.tecdoc.fwktd.applets.scan.jtwain.TwainScanner";
	  //}else if(osname.startsWith("Mac")){
	    }else{
	      return null;
	    }                                             // use reflection here, because allows jar files with/out
	    try{                                          // twain and/or sane files
	      Scanner scanner   = (Scanner)Class.forName(cn).newInstance();

	      if(scanner.isAPIInstalled()){return scanner;}
	      //if(scanner.init()){return scanner;}
	      
	    }catch(IllegalAccessException iae){
	    	iae.printStackTrace();
	    }catch(InstantiationException ie){
	    	ie.printStackTrace();
	    }catch(ClassNotFoundException cnfe){
	    	cnfe.printStackTrace();
	    }
	    return null;
	  }
}