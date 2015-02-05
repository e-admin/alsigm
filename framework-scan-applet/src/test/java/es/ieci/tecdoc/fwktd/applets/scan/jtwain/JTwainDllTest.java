package es.ieci.tecdoc.fwktd.applets.scan.jtwain;

import junit.framework.TestCase;

public class JTwainDllTest extends TestCase {
	public void disabledTestAcquire(){
//		acquire
//		acquireUI
//		acquireToBMP
//		acquireToJPEG
//		acquireToFile
		
//		openDS
//		openDSM
//		closeDS
//		closeDSM
	} 
	
	public void setUp(){
		JTwain.init();
		try{
			JTwain.openDSM();
			showReturnCode("OPENDSM",0);
			JTwain.openDS("TWAIN_32 Sample Source");
			JTwain.openDS("TWAIN2 FreeImage Software Scanner");
			//JTwain.openDS("K2s");
			//JTwain.openDS("TW-Brother ADS-2100");
			showReturnCode("OPENDS");
		}catch(Exception ex){
			tratarExcepcion(false,ex);
			
			try{ JTwain.closeDS(); } 
			catch(Exception e){
				tratarExcepcion(false,e);
			}finally{
				showReturnCode("CLOSEDS");
				try{ JTwain.closeDSM(); }
				catch(Exception exc){
					tratarExcepcion(false,exc);
				}
				showReturnCode("CLOSEDSM",0);
			}
		}
	}
	
	public void tearDown(){
		try{ JTwain.closeDS(); } 
		catch(Exception e){
			tratarExcepcion(false,e);
		}finally{
			showReturnCode("CLOSEDS");
			try{ JTwain.closeDSM(); }
			catch(Exception ex){
				tratarExcepcion(false,ex);
			}
			showReturnCode("CLOSEDSM",0);
		}
	}
	
	public void testGetDefaulDS() {
		try{
			String defaultDS=JTwain.getDefaultDS();
			assertNotNull(defaultDS);
			assertTrue(defaultDS.length()>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetFirstDS() {
		try{
			String firstDS=JTwain.getFirstDS();
			assertNotNull(firstDS);
			assertTrue(firstDS.length()>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}	
	}
	
	public void testGetNextDS() {
		try{
			String nextDS=JTwain.getFirstDS();
			nextDS=JTwain.getNextDS();
			assertNotNull(nextDS);
			assertTrue(nextDS.length()>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetPhysicalHeight() {	
		try{
			float height=JTwain.getPhysicalHeight();
			showReturnCode("getPhysicalHeight");
			assertTrue(height>0);	
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetPhysicalWidth() {
		try{
			float width=JTwain.getPhysicalWidth();
			showReturnCode("getPhysicalWidth");
			assertTrue(width>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testPixelType() {
		final int PIXEL_TYPE=0;
		try{
			JTwain.setPixelType(PIXEL_TYPE);
			showReturnCode("setPixelType");

			int retPixelType=JTwain.getPixelType();
			assertTrue(PIXEL_TYPE==retPixelType);
			
			JTwain.setPixelType(PIXEL_TYPE+1);
			showReturnCode("setPixelType");

			retPixelType=JTwain.getPixelType();
			assertTrue(PIXEL_TYPE+1==retPixelType);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetPixelTypeList() {
		try{
			int [] retTypes=JTwain.getPixelTypeList();
			assertNotNull(retTypes);
			assertTrue(retTypes.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testUnits() {
		final int UNITS=1;
		int retUnit;
		try{
			JTwain.setUnits(UNITS);
			showReturnCode("setUnits");
		
			retUnit=JTwain.getUnits();
			assertTrue(retUnit==UNITS);
			
			JTwain.setUnits(UNITS-1);
			showReturnCode("setUnits");
		
			retUnit=JTwain.getUnits();
			assertTrue(retUnit==UNITS-1);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
		
	public void testGetUnitsDefault() {
		final int INIT_VALUE=99;
		int retUnit=INIT_VALUE;
		try{
			retUnit=JTwain.getUnitsDefault();
			assertTrue(retUnit!=INIT_VALUE);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetUnitsList() {
		try{
			int [] retTypes=JTwain.getUnitsList();
			assertNotNull(retTypes);
			assertTrue(retTypes.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}

	public void testSupportedSizes() {
		final int SIZES=3;
		try{
			JTwain.setSupportedSizes(SIZES);
			showReturnCode("setSupportedSizes");
		
			int [] retSizes=JTwain.getSupportedSizes();
			assertNotNull(retSizes);
			assertTrue(retSizes.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}

	private void showReturnCode(String metodo) {
		showReturnCode(metodo,1);
	}
	
	private void showReturnCode(String metodo,int type) {
		try{
			int result=JTwain.getRC();
			System.out.print("["+metodo+"] rc="+result);
			if(result>0){
				System.out.print(" cc="+JTwain.getCC(type));
			}
			System.out.println();
		}catch(Exception e){}
	}
	
	public void testGetXNativeResolution() {
		float fRet;
		try{
			fRet=JTwain.getXNativeResolution();
			showReturnCode("getXNativeResolution");
			assertTrue(fRet>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}	
	}
	
	public void testGetXNativeResolutionList() {
		try{
			float[] fRets=JTwain.getXNativeResolutionList();
			showReturnCode("getXNativeResolutionList");
			assertNotNull(fRets);
			assertTrue(fRets.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetYNativeResolution() {
		try{
			float fRet=JTwain.getYNativeResolution();
			showReturnCode("getYNativeResolution");
			assertTrue(fRet>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetYNativeResolutionList() {
		try{
			float[] fRets=JTwain.getYNativeResolutionList();
			showReturnCode("getYNativeResolutionList");
			assertNotNull(fRets);
			assertTrue(fRets.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetXResolutionList() {
		try{
			float [] fRets=JTwain.getXResolutionList();
			showReturnCode("getXResolutionList");
			assertNotNull(fRets);
			assertTrue(fRets.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetYResolutionList() {
		try{
			float [] fRets=JTwain.getYResolutionList();
			showReturnCode("getYResolutionList");
			assertNotNull(fRets);
			assertTrue(fRets.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testXResolution() {	//fail!
		final float RES=100.0f;
		float res;
		try{
			JTwain.setXResolution(RES);
			showReturnCode("setXResolution");
		
			res=JTwain.getXResolution();
			showReturnCode("getXResolution");
			assertTrue(res==RES);
			
			JTwain.setXResolution(RES/2);
			showReturnCode("setXResolution");
		
			res=JTwain.getXResolution();
			showReturnCode("getXResolution");
			assertTrue(res==RES/2);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testYResolution(){
		final float RES=100.0f;
		float res;
		try{
			JTwain.setYResolution(RES);
			showReturnCode("setYResolution");

			res=JTwain.getYResolution();
			showReturnCode("getYResolution");
			assertTrue(res==RES);
			
			JTwain.setYResolution(RES/2);
			showReturnCode("setYResolution");

			res=JTwain.getYResolution();
			showReturnCode("getYResolution");
			assertTrue(res==RES/2);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetXScalingDefault() {		 //fail!
		final float INIT_VALUE=99.0F;
		float res=INIT_VALUE;
		try{
			res=JTwain.getXScalingDefault();
			showReturnCode("getXScalingDefault");
			assertTrue(res!=INIT_VALUE);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testGetYScalingDefault() {
		final float INIT_VALUE=99.0F;
		float res=INIT_VALUE;
		try{
			res=JTwain.getYScalingDefault();
			showReturnCode("getYScalingDefault");
			assertTrue(res!=INIT_VALUE);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}

	public void testXScaling(){
		final float SCALE=100.0f;
		try{
			JTwain.setXScaling(SCALE);
			showReturnCode("setXScaling");

			float res=JTwain.getXScaling();
			showReturnCode("getXScaling");
			assertTrue(res==SCALE);
			
			JTwain.setXScaling(SCALE/2);
			showReturnCode("setXScaling");

			res=JTwain.getXScaling();
			showReturnCode("getXScaling");
			assertTrue(res==SCALE/2);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
		
	public void testYScaling(){
		final float SCALE=100.0f;
		try{
			JTwain.setYScaling(SCALE);
			showReturnCode("setYScaling");
	
			float res=JTwain.getYScaling();
			showReturnCode("getYScaling");
			assertTrue(res==SCALE);
			
			JTwain.setYScaling(SCALE/2);
			showReturnCode("setYScaling");
	
			res=JTwain.getYScaling();
			showReturnCode("getYScaling");
			assertTrue(res==SCALE/2);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testDuplex() {		//fail!
		boolean b;
		try{
			JTwain.setDuplexEnabled(true);
			showReturnCode("setDuplexEnabled");
			b=JTwain.getDuplexSupport();
			showReturnCode("getDuplexSupport");
			assertTrue(b);
			
			JTwain.setDuplexEnabled(false);
			showReturnCode("setDuplexEnabled");
			b=JTwain.getDuplexSupport();
			showReturnCode("getDuplexSupport");
			assertTrue(!b);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
		
	public void testGetDuplexList(){	//fail!
		try{
			int[] bs=JTwain.getDuplexList();
			showReturnCode("getDuplexList");
			assertNotNull(bs);
			assertTrue(bs.length>0);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testADF() {
		try{
			JTwain.setADFEnabled(true);
			showReturnCode("setADFEnabled");
			boolean b=JTwain.getADFSupport();
			showReturnCode("getADFSupport");
			assertTrue(b);
			
			JTwain.setADFEnabled(false);		//fail!
			showReturnCode("setADFEnabled");
			b=JTwain.getADFSupport();
			showReturnCode("getADFSupport");
			assertTrue(!b);
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}

	public void testSetContrast() {		//fail!
		try{
			JTwain.setContrast(10);
			showReturnCode("setContrast");
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
		
	public void testSetBright(){
		try{
			JTwain.setBright(20);
			showReturnCode("setBright");
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
		
	public void testSetJPGQuality(){
		try{
			JTwain.setJPGQuality(50);
			showReturnCode("setJPGQuality");
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	public void testSetCompressJPEG(){
		try{
			JTwain.setCompressJPEG();
			showReturnCode("setCompressJPEG");
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
		
	public void testSetAutoDiscardBlankPages(){
		try{
			JTwain.setAutoDiscardBlankPages(1);
			showReturnCode("setAutoDiscardBlankPages");
		}catch(Exception e){
			tratarExcepcion(e);
		}
	}
	
	private void tratarExcepcion(Exception e){
		tratarExcepcion(true,e);
	}
	
	private void tratarExcepcion(boolean fail,Exception e){
		System.out.println(e.getMessage());
		//e.printStackTrace();
		if(fail) fail();
	}
}
