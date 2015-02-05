package es.ieci.tecdoc.fwktd.applets.scan.jtwain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageReader;

import junit.framework.TestCase;
import es.ieci.tecdoc.fwktd.applets.scan.utils.ImageUtils;

public class TwainScannerTest extends TestCase{
	final String PREFIX_IMG="IMG_PRUEBA0001_";
	final String DEFAULT_DIR="c:/temp";
	final String SCANNER_ID="TWAIN_32 Sample Source";
	//final String SCANNER_ID="TW-Brother ADS-2100";
	
	public static TwainScanner twain=new TwainScanner();
	
	static{
		twain.isAPIInstalled();
	}
	
	public void setUp(){
		//twain=new TwainScanner();
		deleteLastScan();
		//twain.isAPIInstalled();
		try{
			twain.openDSM();
			twain.openDS(SCANNER_ID);
		}catch(Exception e){
			showException(e,false);
		
			try{ twain.closeDS(); } 
			catch(Exception ex){
				showException(ex,false);
			}finally{
				try{ twain.closeDSM(); }
				catch(Exception ex){
					showException(ex,false);
				}
			}
		}
	}
	
	public void tearDown(){
		try{ twain.closeDS(); } 
		catch(Exception ex){
			showException(ex,false);
		}finally{
			try{ twain.closeDSM(); }
			catch(Exception ex){
				showException(ex,false);
			}
		}
	}
	
	private void twainAcquireToFile(int pixelType,int imageType){
		try{
			//int []pixelTypes=twain.getPixelTypeList();
			twain.setPixelType(pixelType);
			if(SCANNER_ID.equals("TWAIN_32 Sample Source")){
				//twain.setXResolution(100.0F);
				twain.setSupportedSizes(3);
			}else if(SCANNER_ID.equals("TW-Brother ADS-2100")){
				twain.setXResolution(600.0F);
			}
			
			int numImage=twain.acquireToFile(PREFIX_IMG, true, DEFAULT_DIR,false)-1;
			//int numImage=(bNativo?
			//		twain.acquireNative(PREFIX_IMG, true, DEFAULT_DIR,false):
			//		twain.acquireToFile(PREFIX_IMG, true, DEFAULT_DIR,false))-1;
			
			File f=new File(DEFAULT_DIR+"/"+PREFIX_IMG+numImage+".BMP");
			assertTrue(f.exists());
			assertTrue(f.length()>0);
			ImageReader reader=ImageUtils.getImageReader(f);
			assertTrue(ImageUtils.getImageType(reader)==imageType);
		}catch(Exception e){
			showException(e);
		}
	}
	
	//solo uno a la vez
//	public void testTwainAcquireToFileBN(){
//		twainAcquireToFile(0,BufferedImage.TYPE_BYTE_BINARY);
//	}
	
	//public void testTwainAcquireToFileGray(){
	//	twainAcquireToFile(1,BufferedImage.TYPE_BYTE_GRAY);
	//}
	
//	public void testTwainAcquireToFile24bits(){
//		twainAcquireToFile(2,BufferedImage.TYPE_INT_RGB);
//	}
	
	public void testTwainAcquireBN(){
		twainAcquireToFile(0,BufferedImage.TYPE_BYTE_BINARY);
	}
	
	public void testTwainAcquireGray(){
		twainAcquireToFile(1,BufferedImage.TYPE_BYTE_GRAY);
	}
	
	public void testTwainAcquire24bits(){
		twainAcquireToFile(2,BufferedImage.TYPE_INT_RGB);
	}
	
	public void testGetDefaultDS() {
		try{
			String dsName=twain.getDefaultDS();
			assertNotNull(dsName);
			assertTrue(dsName.length()>0);
		}catch(Exception e){
			showException(e);
		}
	}

	public void testGetDeviceNames() {
		try{
			List deviceNames=twain.getDeviceNames();
			assertNotNull(deviceNames);
			assertTrue(deviceNames.size()>0);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testSetBright() {
		try{
			twain.setBright(50.0F);
			assertTrue(true);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testSetCompressJPEG() {
		try{
			twain.setCompressJPEG();
			assertTrue(true);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testSetContrast() {
		try{
			twain.setContrast(50.0F);
			assertTrue(true);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testSetJPGQuality() {
		try{
			twain.setJPGQuality(50);
			assertTrue(true);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testSetXYResolution() {
		try{
			twain.setXResolution(200.0F);
			assertTrue(true);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testSetUnits() {
		try{
			twain.setUnits(1);
			assertTrue(true);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testGetXResolutionList(){
		try{
			float[] resolutions=twain.getXResolutionList();
			assertNotNull(resolutions);
			assertTrue(resolutions.length>0);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testPixelType(){
		try{
			twain.setPixelType(2);
			int[] types=twain.getPixelTypeList();
			assertNotNull(types);
			assertTrue(types.length>0);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testDuplexSupport(){
		try{
			twain.setDuplexEnabled(true);
			boolean b=twain.getDuplexSupport();
			assertTrue(b);
			twain.setDuplexEnabled(false);	
			b=twain.getDuplexSupport();
			assertTrue(!b);						//fail! sólo funciona la primera vez
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testSupportedSizes(){
		try{
			twain.setSupportedSizes(1);
			int [] sizes=twain.getSupportedSizes();
			assertNotNull(sizes);
			assertTrue(sizes.length>0);
		}catch(Exception e){
			showException(e);
		}
	}
	
	public void testADF(){
		try{
			twain.setADFEnabled(true);
			boolean b=twain.getADFSupport();
			assertTrue(b);
			twain.setADFEnabled(false);
			b=twain.getADFSupport();
			assertTrue(!b);
		}catch(Exception e){
			showException(e);
		}
	}
	
	private void showException(Exception e){
		showException(e,true);
	}
	
	private void showException(Exception e,boolean fail){
		System.out.println(e.getMessage());
		//e.printStackTrace();
		if(fail) fail();
		
	}
	
	private void deleteLastScan(){
		File f=new File(DEFAULT_DIR+"/"+PREFIX_IMG+"1.bmp");
		//f.setWritable(true);
		System.gc(); System.gc();
		f.delete(); 
		f=null;
		//System.gc(); System.gc(); System.gc(); System.gc();
	}
		
}
