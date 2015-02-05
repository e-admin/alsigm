package es.ieci.tecdoc.fwktd.applets.scan.jtwain;

import java.util.ArrayList;

import es.ieci.tecdoc.fwktd.applets.scan.scanner.Scanner;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.ScannerIOException;

public class TwainScanner extends Scanner{

	@Override
	public void closeDS()  throws ScannerIOException{
		JTwain.closeDS();
	}

	@Override
	public void closeDSM() throws ScannerIOException {
		JTwain.closeDSM();
	}

	@Override
	public void openDSM() throws ScannerIOException {
		JTwain.openDSM();
	}

	@Override
	public int acquireToFile(String code, boolean useCur, String pathFile, boolean ui)throws ScannerIOException {
		return JTwain.acquireNativeToFile(code, true, pathFile,ui);
	}

	@Override
	public String getDefaultDS() throws ScannerIOException {
		return JTwain.getDefaultDS();
	}

	@Override
	public boolean getDuplexSupport() throws ScannerIOException {
		return JTwain.getDuplexSupport();
	}

	@Override
	public int[] getPixelTypeList() throws ScannerIOException {
		return JTwain.getPixelTypeList();
	}

	@Override
	public int[] getSupportedSizes() throws ScannerIOException {
		return JTwain.getSupportedSizes();
	}

	@Override
	public float[] getXResolutionList() throws ScannerIOException {
		ArrayList<Float> suported = new ArrayList<Float>();
		float[] result = JTwain.getXResolutionList();
		
		if(result.length==2 && (result[1]-result[0])>100){
			float max = result[1];
			float min = result[0];
			
			if(100>=min && 100<=max){
				suported.add(new Float(100.0));
			}
			if(200>=min && 200<=max){
				suported.add(new Float(200.0));
			}
			if(300>=min && 300<=max){
				suported.add(new Float(300.0));
			}
			if(400>=min && 400<=max){
				suported.add(new Float(400.0));
			}
			if(600>=min && 600<=max){
				suported.add(new Float(600.0));
			}
			if(800>=min && 800<=max){
				suported.add(new Float(800.0));
			}
			if(1200>=min && 1200<=max){
				suported.add(new Float(1200.0));
			}
		}else {
			for (float f : result) {
				suported.add(f);
			}
		}
		
	    Object[] array = suported.toArray();
	    float[] valores = new float[array.length];
	        for (int i = 0; i < array.length; i++)
	        {
	         valores[i]=(Float)array[i];
	        }
	    
	    return valores;
	}

	@Override
	public void openDS(String device) throws ScannerIOException {
		JTwain.openDS(device);
	}

	@Override
	public void setPixelType(int parseInt) throws ScannerIOException {
		JTwain.setPixelType(parseInt);
	}

	@Override
	public void setUnits(int i) throws ScannerIOException {
		JTwain.setUnits(i);
	}

	@Override
	public void setXResolution(float floatValue) throws ScannerIOException {
		JTwain.setXResolution(floatValue);
		JTwain.setYResolution(floatValue);
	}

	@Override
	public void setBright(float bright) throws ScannerIOException {
		JTwain.setBright(bright);
	}

	@Override
	public void setCompressJPEG() throws ScannerIOException {
		JTwain.setCompressJPEG();
	}

	@Override
	public void setContrast(float contrast) throws ScannerIOException {
		JTwain.setContrast(contrast);
	}

	@Override
	public void setDuplexEnabled(boolean duplex) throws ScannerIOException {
		JTwain.setDuplexEnabled(duplex);
	}

	@Override
	public void setJPGQuality(int quality) throws ScannerIOException {
		JTwain.setJPGQuality(quality);
	}

	@Override
	public void setSupportedSizes(int parseInt) throws ScannerIOException {
		JTwain.setSupportedSizes(parseInt);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList getDeviceNames() throws ScannerIOException {
		final ArrayList srcNames = new ArrayList();
		try
	      {				          
	        srcNames.add (JTwain.getFirstDS());
	        String srcName;
	        while (!(srcName = JTwain.getNextDS()).equals (""))
	        	srcNames.add (srcName);	        	
	          	return srcNames;
	      	}
	      catch (ScannerIOException jte)
	      {	    
	    	try {
	    		JTwain.closeDSM();
			} catch (ScannerIOException e) {
				e.printStackTrace();
			}
	    	  return null;
	      }
	}

	@Override
	public boolean isAPIInstalled() {
		return JTwain.init();
	}

	@Override
	public boolean getADFSupport() throws ScannerIOException {
		return JTwain.getADFSupport();
	}

	@Override
	public void setADFEnabled(boolean adf) throws ScannerIOException {
		JTwain.setADFEnabled(adf);
	}
}
