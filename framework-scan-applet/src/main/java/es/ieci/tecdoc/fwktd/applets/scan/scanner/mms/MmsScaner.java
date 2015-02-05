package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms;

import java.util.ArrayList;

import es.ieci.tecdoc.fwktd.applets.scan.scanner.Scanner;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.ScannerIOException;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants.ScanModeConstants;

public class MmsScaner extends Scanner{

    public static int CHECK_INTERVAL = 2000;

    private uk.co.mmscomputing.device.scanner.Scanner scanner;
    private CapabilitiesValues capabilities = null;

    private String device = null;
    private int scanMode = ScanModeConstants.SCAN_MODE_NATIVE;
    private int generationMode = ScanModeConstants.SCAN_MODE_MEMORY_JPG_BLOCKS;
    private Float resolution = null;
    private Float brightness = null;
    private Float contrast = null;
    private boolean duplexEnabled = false;
    private Integer bpp = null;
    private Integer size = null;
    private int units;
    private boolean adfEnabled = true;
    private boolean uiEnabled = false;
    private int numPages = 0;
    private String path = null;
    private String fileName = null;
    private String scannedFile = null;

    private int memoryBlockProcessed = 0;
    private int memoryBlockTotal = 0;
    private int memoryRow = 0;
    private int memoryWidth = 0;
    private int memoryHeight = 0;
    private int numPage = 0;

    @Override
    public boolean isAPIInstalled() {
        scanner = uk.co.mmscomputing.device.scanner.Scanner.getDevice();
        return scanner.isAPIInstalled();
    }

    @Override
    public ArrayList getDeviceNames() throws ScannerIOException {
        ArrayList deviceNames = new ArrayList();
        try {
            String[] deviceNamesArray = scanner.getDeviceNames();
            for (int i=0;i<deviceNamesArray.length;i++){
            	deviceNames.add(deviceNamesArray[i]);
            }
        } catch (uk.co.mmscomputing.device.scanner.ScannerIOException e) {
            throw new ScannerIOException(e.getMessage());
        }
        return deviceNames;
    }

    @Override
    public String getDefaultDS() throws ScannerIOException {
        String deviceName = null;
        try {
            deviceName = scanner.getSelectedDeviceName();
        } catch (uk.co.mmscomputing.device.scanner.ScannerIOException e) {
            throw new ScannerIOException(e.getMessage());
        }
        return deviceName;
    }

    @Override
    public void openDSM() throws ScannerIOException {

    }

    @Override
    public void closeDSM() throws ScannerIOException {

    }

    @Override
    public void closeDS() throws ScannerIOException {

    }

    @Override
    public void openDS(String device) throws ScannerIOException {
        try {
            this.device = device;
            capabilities = null;
            CapabilitiesThread capabilitiesThread = new CapabilitiesThread(device);
            capabilitiesThread.start();
            while (true){
                Thread.sleep(CHECK_INTERVAL);
                if (capabilitiesThread.isScanError() || capabilitiesThread.isCapabilitiesFinished()){
                    break;
                }
            }

            if (capabilitiesThread.isScanError()||capabilitiesThread.isCapabilitiesFinished()) {
                capabilities = capabilitiesThread.getCapabilities();
            }
        } catch (InterruptedException e) {
            throw new ScannerIOException(e.getMessage());
        }
    }



    @Override
    public int acquireToFile(String prefix, boolean b, String pathFile, boolean c)
            throws ScannerIOException {
        // prefix = prefijo del fichero
        // b = si utiliza el directorio indicado en pathFile
        // pathFile = directorio de trabajo
        // c = si utiliza o no ui

        ScanThread scanThread = new ScanThread(
        		device,
        		scanMode,
                0,
                resolution,
                bpp,
                brightness,
                contrast,
                duplexEnabled,
                adfEnabled,
                size,
                pathFile,
                prefix,
                c);
        scanThread.start();
        try {
			while (true){
			    Thread.sleep(CHECK_INTERVAL);
			    if (scanThread.isScanError() || scanThread.isScanFinished()){
			        break;
			    }
			}
		} catch (InterruptedException e) {
			throw new ScannerIOException(e.getMessage());
		}

        if (scanThread.isScanError()) {
            return 0;
        } else if (scanThread.isScanFinished()) {
            return scanThread.getScannedPages();
        }

        return 0;
    }

    @Override
    public int[] getSupportedSizes() throws ScannerIOException {
        return capabilities.getSupportedSizes();
    }

    @Override
    public int[] getPixelTypeList() throws ScannerIOException {
        return capabilities.getPixelTypeList();
    }

    @Override
    public float[] getXResolutionList() throws ScannerIOException {
    	return capabilities.getxResolutionList();
    }

    @Override
    public void setUnits(int i) throws ScannerIOException {
        this.units = i;
    }

    @Override
    public boolean getDuplexSupport() throws ScannerIOException {
        return capabilities.isDuplexSupport();
    }

    @Override
    public boolean getADFSupport() throws ScannerIOException {
    	return capabilities.isAdfSupport();
    }

    @Override
    public void setXResolution(float floatValue) throws ScannerIOException {
        this.resolution = new Float(floatValue);
    }

    @Override
    public void setPixelType(int parseInt) throws ScannerIOException {
        this.bpp = new Integer(parseInt);

    }

    @Override
    public void setSupportedSizes(int parseInt) throws ScannerIOException {
        this.size = new Integer(parseInt);

    }

    @Override
    public void setDuplexEnabled(boolean duplex) throws ScannerIOException {
        this.duplexEnabled = duplex;

    }

    @Override
    public void setADFEnabled(boolean adf) throws ScannerIOException {
        this.adfEnabled = adf;
    }

    @Override
    public void setContrast(float contrast) throws ScannerIOException {
        this.contrast = new Float (contrast);
    }

    @Override
    public void setBright(float bright) throws ScannerIOException {
        this.brightness = new Float (bright);

    }

    @Override
    public void setCompressJPEG() throws ScannerIOException {
        // No tiene sentido, siempre se escanea en bmp
    }

    @Override
    public void setJPGQuality(int quality) throws ScannerIOException {
        // No tiene sentido, siempre se escanea en bmp
    }

}
