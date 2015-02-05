package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms;

import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.twain.TwainCapability;
import uk.co.mmscomputing.device.twain.TwainIOException;
import uk.co.mmscomputing.device.twain.TwainIOMetadata;
import uk.co.mmscomputing.device.twain.TwainScanner;
import uk.co.mmscomputing.device.twain.TwainSource;

public class CapabilitiesThread extends Thread implements ScannerListener {

	private Scanner scanner;
	private CapabilitiesValues capabilitiesValues;

	private boolean isError = false;
	private boolean isProcessFinished = false;

	// Definicion de parametros
	private String device = null;

	/**
	 * Constructor del thread
	 * @param device nombre del origen twain
	 */
	public CapabilitiesThread(final String device) {
		super();
		this.device = device;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		scanner = Scanner.getDevice();
		try {
			scanner.select(device);
			scanner.getDeviceNames();
			TwainScanner twain = (TwainScanner) scanner;
			twain.addListener(this);
			twain.acquire();
		} catch (ScannerIOException e) {
			e.printStackTrace();
			endProcess(true, false);
		}
	}

	/**
	 * Devuelve si se ha producido un error en el escaneo
	 * @return si se ha producido un error en el escaneo
	 */
	public synchronized boolean isScanError() {
		return isError;
	}

	/**
	 * Devuelve si la obtencion de capabilities ha finalizado
	 * @return si la obtencion de capabilities ha finalizado
	 */
	public synchronized boolean isCapabilitiesFinished() {
		return isProcessFinished;
	}

	/**
	 * Permite marcar el escaneo como finalizado o como escaneo erroneo
	 * @param error indica si se ha producido un error
	 * @param finished indica si el escaneo ha finalizado
	 */
	private synchronized void endProcess(final boolean error, final boolean finished) {
		if (error){
			isError = true;
		} else if (finished) {
			isProcessFinished = true;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.mmscomputing.device.scanner.ScannerListener#update(uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type, uk.co.mmscomputing.device.scanner.ScannerIOMetadata)
	 */
	public void update(final ScannerIOMetadata.Type type, final ScannerIOMetadata metadata) {
		if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
			try {
				if (metadata instanceof TwainIOMetadata) {
					TwainSource source = ((TwainIOMetadata) metadata).getSource();
					TwainCapability[] capabilities = source.getCapabilities();
					capabilitiesValues = processCapabilities(capabilities);
				}
			} catch (ScannerIOException e) {
				e.printStackTrace();
				endProcess(true, false);
			}

			metadata.setCancel(true);
			endProcess(false, true);
		} else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
			if (metadata.isFinished()) {
				System.out.println("Get capabilities process finished");
				try {
					endProcess(false, true);
				} catch (Exception e) {
					e.printStackTrace();
					endProcess(true, false);
				}
			}
		} else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
			metadata.getException().printStackTrace();
			endProcess(true, false);
		}
	}

	 /**
     * Procesa las capacidades del escaner para quedarnos solo con las necesarias
     */
    private CapabilitiesValues processCapabilities (TwainCapability [] capabilities) throws TwainIOException{
    	TwainCapability capability = null;
    	CapabilitiesValues values = new CapabilitiesValues();
    	for (int i=0;i<capabilities.length;i++){
    		capability = capabilities[i];
    		if ("ICAP_SUPPORTEDSIZES".equals(capability.getName())){
    			values.setSupportedSizes(transformIntegerArray(capability.getItems()));
    		} else if ("ICAP_XRESOLUTION".equals(capability.getName())){
    			values.setxResolutionList(transformDoubleArray(capability.getItems()));
    		} else if ("ICAP_PIXELTYPE".equals(capability.getName())){
    			values.setPixelTypeList(transformIntegerArray(capability.getItems()));
    		} else if ("CAP_DUPLEX".equals(capability.getName())){
    			values.setDuplexSupport(capability.booleanValue());
    		} else if ("CAP_FEEDERENABLED".equals(capability.getName())){
    			values.setAdfSupport(capability.booleanValue());
    		}
    	}

    	return values;
    }

    /**
     * Transforma un array de Integer en un array de int
     * @param integerObjectArray Array de Integer
     * @return array de int
     */
    private int[] transformIntegerArray(Object[] integerObjectArray){
    	int [] intArray = null;
    	if (integerObjectArray!=null){
    		intArray = new int[integerObjectArray.length];
    		for (int i=0;i<integerObjectArray.length;i++){
    			intArray[i] = ((Integer)integerObjectArray[i]).intValue();
    		}
    	}
    	return intArray;
    }

    /**
     * Transforma un array de Double en un array de float
     * @param doubleObjectArray array de Double
     * @return array de float
     */
    private float[] transformDoubleArray(Object[] doubleObjectArray){
    	float [] floatArray = null;
    	if (doubleObjectArray!=null){
    		floatArray = new float[doubleObjectArray.length];
    		for (int i=0;i<doubleObjectArray.length;i++){
    			floatArray[i] = ((Double)doubleObjectArray[i]).floatValue();
    		}
    	}
    	return floatArray;
    }

	/**
	 * Permite obtener las capacidades del escaner
	 * @return capacidades del escaner
	 */
	public CapabilitiesValues getCapabilities() {
		return capabilitiesValues;
	}
}
