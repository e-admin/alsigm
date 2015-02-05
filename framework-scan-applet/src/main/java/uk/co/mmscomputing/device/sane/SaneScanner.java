package uk.co.mmscomputing.device.sane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import uk.co.mmscomputing.device.sane.option.Descriptor;
import uk.co.mmscomputing.device.sane.option.FixedDesc;
import uk.co.mmscomputing.device.sane.option.FixedRange;

import es.ieci.tecdoc.fwktd.applets.scan.key.KeysUtils;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.Scanner;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.ScannerIOException;
import es.ieci.tecdoc.fwktd.applets.scan.utils.JsaneConfig;

@SuppressWarnings("unused")
public class SaneScanner extends Scanner{
	//static private int no=1;
	protected SaneDeviceManager   manager=null;
	
	private float xResolution;
	
	private int supportedSizes;
	private int pixelType;
	private float contrast;
	private float bright;
	private boolean enableUI;
	private boolean duplexEnabled;
	private boolean compressJPEG;
	private int jPGQuality;
	private int units;
	private boolean adfEnabled;
	
	public SaneScanner(){
	    super();
	    //metadata=new SaneIOMetadata();
	}

	@Override
	public int acquireToFile(String code, boolean b, String pathFile, boolean c) throws SaneIOException {
		enableUI=c;
		
		ArrayList<BufferedImage> images = jsane.acquire(this);
		if(images.size()==0){
			//No tiene imagenes
			return 1;
		}
		
		int cont=1;
		for (BufferedImage bufferedImage : images) {
			BufferedImage image=bufferedImage;
			String path = pathFile + System.getProperty("file.separator") + code + cont + "." + KeysUtils.BMP;
			File file = new File(path);

			try {
				ImageIO.write(image, "bmp", file);
			} catch (IOException e) {
				e.printStackTrace();

			}
			cont++;
		}
		return images.size()+1;
	}

	@Override
	public void closeDS() throws ScannerIOException {
		manager.getDevice().getCancel();
		//jsane.setCancel(this, true);
	}
	
	@Override
	public void closeDSM() throws ScannerIOException {
		manager = null;
	}
	
	@Override
	public String getDefaultDS() throws ScannerIOException {
		String[] names = new String[0];
	    try{
	      jsane.init();
	      try{
	        names=jsane.getDeviceManager().getDevices();
	      }finally{
	        jsane.exit();
	      }
	    }catch(Exception e){
	      //metadata.setException(e);
	      //fireListenerUpdate(metadata.EXCEPTION);
	    } 
	    return names[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList getDeviceNames() throws ScannerIOException {
		String[] names = new String[0];
	    try{
	      jsane.init();
	      try{
	        names=jsane.getDeviceManager().getDevices();
	      }finally{
	        jsane.exit();
	      }
	    }catch(Exception e){
	      //metadata.setException(e);
	      //fireListenerUpdate(metadata.EXCEPTION);
	    }
	    
	    final ArrayList srcNames = new ArrayList();
	    srcNames.addAll(Arrays.asList(names)); 
	    return srcNames;
	}
	
	//Metodos de Jsane
	void negotiateOptions(SaneDevice source){     
		SaneDevice sd= source;                   // SANE & TWAIN
		
		sd.setShowUserInterface(enableUI);
		sd.setShowProgressBar(enableUI);                          // Twain: works only if user interface is inactive
		try{			
			sd.setResolution(xResolution);							// set resolution in dots per inch
		}catch(Exception e){
			System.out.println(e.getMessage());
			//sd.setCancel(true);                              // cancel scan if we can't set it up our way
		}
				
		try{
			if(pixelType==2){
				for (String string2 : JsaneConfig.getString("SANE_MODE_COLOR")) {
					try{
						sd.setOption("mode",string2);
						sd.setOption("depth","16");
					}catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}else if(pixelType==1){
				for (String string2 : JsaneConfig.getString("SANE_MODE_GRIS")) {
					try{
						sd.setOption("mode",string2);
						sd.setOption("depth","8");
					}catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}else if ((pixelType==0)) {
				for (String string2 : JsaneConfig.getString("SANE_MODE_BN")) {
					try{
						sd.setOption("mode",string2);
						sd.setOption("depth","1");
					}catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			//sd.setCancel(true);                              // cancel scan if we can't set it up our way
		} 
			
		try{
			if(adfEnabled){
				for (String string2 : JsaneConfig.getString("SANE_SOURCE_ADF")) {
					try{
						sd.setOption("source",string2);
					}catch (Exception e) {
					}
				}
				sd.setADFMode(true);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			//sd.setCancel(true);                              // cancel scan if we can't set it up our way
		} 
		
		try{
			if(duplexEnabled){
				try{
					sd.setOption("source","ADF Duplex");
				}catch (Exception e) {
					sd.setOption("adf-mode","Duplex");
				}
				sd.setADFMode(true);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			//sd.setCancel(true);                              // cancel scan if we can't set it up our way
		} 
			
		try{		
			if(supportedSizes==1){
				sd.setOption("br-x",new Double(210));
				sd.setOption("br-y",new Double(297));
			}else if(supportedSizes==11){
				sd.setOption("br-x",new Double(297));
				sd.setOption("br-y",new Double(420));
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			//sd.setCancel(true);                              // cancel scan if we can't set it up our way
		}
		
		try{	
			if(bright!=0){
				//TODO twain -999 a 999 // sane -126 a 126				
				int brillo = (int) ((bright * 126) / 999);
				sd.setOption("brightness",brillo);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			//sd.setCancel(true);                              // cancel scan if we can't set it up our way
		}
		
		try{		
			if(contrast!=0){
				//TODO twain -999 a 999 // sane -126 a 126
				int contraste = (int) ((contrast * 126) / 999);
				sd.setOption("contrast",contraste);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			//sd.setCancel(true);                              // cancel scan if we can't set it up our way
		}
			//sd.setOption("bright", bright);
			//sd.setOption("contrast", contrast);
	}
  
	void setState(SaneDevice source){
	}
  
	void signalException(Exception e){
		System.out.println(e.getMessage());
	}

	public void setImage(BufferedImage image) {
	}
	
	@Override
	public boolean getDuplexSupport() throws ScannerIOException {
		return jsane.getDuplexSupport(this);
	}
	
	@Override
	public int[] getPixelTypeList() throws ScannerIOException {
		return jsane.getPixelTypeList(this);
	}
	
	@Override
	public int[] getSupportedSizes() throws ScannerIOException {
		return jsane.getSupportedSizes(this);
	}
	
	@Override
	public float[] getXResolutionList() throws ScannerIOException {
		return jsane.getXResolutionList(this);
	}
	
	@Override
	public boolean isAPIInstalled() {
		  return jsane.isInstalled();
	}
	
	@Override
	public void openDS(String device) throws ScannerIOException {
		//manager.selectDevice(device);
		jsane.select(this, device);
	}
	
	@Override
	public void openDSM() throws ScannerIOException {
		manager = new SaneDeviceManager();
	}
	
	@Override
	public void setBright(float bright) throws ScannerIOException {
		this.bright = bright;
	}
	
	@Override
	public void setCompressJPEG() throws ScannerIOException {
		this.compressJPEG= true;
	}
	
	@Override
	public void setContrast(float contrast) throws ScannerIOException {
		this.contrast=contrast;
	}
	
	@Override
	public void setDuplexEnabled(boolean duplex) throws ScannerIOException {
		this.duplexEnabled=duplex;
	}
	
	@Override
	public void setJPGQuality(int quality) throws ScannerIOException {
		this.jPGQuality=quality;
	}
	
	@Override
	public void setPixelType(int parseInt) throws ScannerIOException {
		this.pixelType=parseInt;
	}
	
	@Override
	public void setSupportedSizes(int parseInt) throws ScannerIOException {
		this.supportedSizes=parseInt;
	}
	
	@Override
	public void setUnits(int i) throws ScannerIOException {
		this.units=i;
	}
	
	@Override
	public void setXResolution(float floatValue) throws ScannerIOException {
		xResolution=floatValue;
	}

	@Override
	public boolean getADFSupport() throws ScannerIOException {
		return jsane.getADFSupport(this);
	}

	@Override
	public void setADFEnabled(boolean adf) throws ScannerIOException {
		this.adfEnabled=adf;
	}
}

