package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.twain.TwainConstants;
import uk.co.mmscomputing.device.twain.TwainIOMetadata;
import uk.co.mmscomputing.device.twain.TwainScanner;
import uk.co.mmscomputing.device.twain.TwainSource;
import uk.co.mmscomputing.device.twain.TwainTransfer;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

import es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants.ScanBitsPerPixelConstants;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants.ScanDuplexConstants;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants.ScanModeConstants;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants.ScanResolutionConstants;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants.ScanSizeConstants;

public class ScanThread extends Thread implements ScannerListener {

	private Scanner scanner;
	private List tempFiles = new ArrayList();
	private List allFiles = new ArrayList();

	private boolean isError = false;
	private boolean isProcessFinished = false;

	// Definicion de parametros
	private String device = null;
	private int scanMode = ScanModeConstants.SCAN_MODE_NATIVE;
	private int generationMode = ScanModeConstants.SCAN_MODE_MEMORY_JPG_BLOCKS;
	private Float resolution = null;
	private Float brightness = null;
	private Float contrast = null;
	private boolean duplex = false;
	private boolean adf = false;
	private boolean showUI = false;
	private Integer bpp = null;
	private Integer size = null;
	private String path = null;
	private String prefix = null;

	private int memoryBlockProcessed = 0;
	private int memoryBlockTotal = 0;
	private int memoryRow = 0;
	private int memoryWidth = 0;
	private int memoryHeight = 0;
	private int numPage = 0;

	/**
	 * Constructor del thread
	 * @param device nombre del origen twain
	 * @param scanMode modo de escaneo. Valores posibles:
	 * <p>
	 *   	{@link ScanModeConstants#SCAN_MODE_FILE}</br>
	 *   	{@link ScanModeConstants#SCAN_MODE_NATIVE}</br>
	 *   	{@link ScanModeConstants#SCAN_MODE_MEMORY}
	 * </p>
	 * @param generationMode modo de generacion de bloques de memoria. Valores posibles:
	 * <p>
	 *   	{@link ScanModeConstants#SCAN_MODE_MEMORY_SERIALIZED}</br>
	 *   	{@link ScanModeConstants#SCAN_MODE_MEMORY_JPG_BLOCKS}
	 * </p>
	 * @param resolution Resolucion. Valores posibles:
	 * <p>
	 *   	{@link ScanResolutionConstants#RESOLUTION_75}</br>
	 *   	{@link ScanResolutionConstants#RESOLUTION_100}</br>
	 *   	{@link ScanResolutionConstants#RESOLUTION_150}</br>
	 *   	{@link ScanResolutionConstants#RESOLUTION_200}</br>
	 *   	{@link ScanResolutionConstants#RESOLUTION_240}</br>
	 *   	{@link ScanResolutionConstants#RESOLUTION_300}</br>
	 *   	{@link ScanResolutionConstants#RESOLUTION_400}</br>
	 *   	{@link ScanResolutionConstants#RESOLUTION_600}
	 * </p>
	 * @param bpp Bits por pixel. Valores posibles:
	 * <p>
	 * 		{@link ScanBitsPerPixelConstants#BLACK_WHITE}</br>
	 * 		{@link ScanBitsPerPixelConstants#GRAYS}</br>
	 * 		{@link ScanBitsPerPixelConstants#COLOR}
	 * </p>
	 * @param brightness brillo del escaneo
	 * @param contrast contraste del escaneo
	 * @param duplex indica si se escanea a doble cara. Valores posibles:
	 * <p>
	 * 		{@link ScanDuplexConstants#DUPLEX_Y}</br>
	 * 		{@link ScanDuplexConstants#DUPLEX_N}
	 * </p>
	 * @param size tamanio del escaneo. Valores posibles:
	 * <p>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_NONE}</br>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_A4}</br>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_B5}</br>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_US_LETTER}</br>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_US_LEGAL}</br>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_A5}</br>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_A6}</br>
	 *   	{@link ScanSizeConstants#PAPER_SIZE_A8}
	 * </p>
	 * @param numPages numero de paginas a escanear
	 * @param path ruta para generacion del fichero a escanear y tambien para generar ficheros temporales
	 * @param fileName nombre del fichero a generar
	 */
	public ScanThread(final String device,
			final int scanMode,
			final int generationMode,
			final Float resolution,
			final Integer bpp,
			final Float brightness,
			final Float contrast,
			final boolean duplex,
			final boolean adf,
			final Integer size,
			final String path,
			final String prefix,
			final boolean showUI) {
		super();
		this.scanMode = scanMode;
		this.device = device;
		this.resolution = resolution;
		this.duplex = duplex;
		this.bpp = bpp;
		this.size = size;
		this.path = path;
		this.prefix = prefix;
		this.adf = adf;
		this.brightness = brightness;
		this.contrast = contrast;
		this.generationMode = generationMode;
		this.showUI = showUI;
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
	 * Devuelve si el escaneo ha finalizado
	 * @return si el escaneo ha finalizado
	 */
	public synchronized boolean isScanFinished() {
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

	/**
	 * Añade un fichero temporal a la lista de ficheros del escaneo
	 * @param file fichero a añadir
	 */
	private synchronized void addTempFile(final File file) {
		tempFiles.add(file);
	}

	/**
	 * Añade un fichero a la lista de ficheros del escaneo
	 * @param file fichero a añadir
	 */
	private synchronized void addScanFile(final File file) {
		allFiles.add(file);
	}

	/**
	 * Limpia la lista de ficheros temporales del escaneo
	 */
	private synchronized void clearTempFiles() {
		if ((tempFiles!=null)&&(tempFiles.size()>0)){
			Iterator it = tempFiles.iterator();
			File file = null;
			while (it.hasNext()){
				file = (File) it.next();
				file.delete();
				//FileUtils.deleteQuietly(file);
			}
			tempFiles.clear();
		}
	}

	/**
	 * Limpia la lista de ficheros del escaneo
	 */
	private synchronized void clearScanFiles() {
		if ((allFiles!=null)&&(allFiles.size()>0)){
			Iterator it = allFiles.iterator();
			File file = null;
			while (it.hasNext()){
				file = (File) it.next();
				file.delete();
				//FileUtils.deleteQuietly(file);
			}
			allFiles.clear();
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.mmscomputing.device.scanner.ScannerListener#update(uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type, uk.co.mmscomputing.device.scanner.ScannerIOMetadata)
	 */
	public void update(final ScannerIOMetadata.Type type, final ScannerIOMetadata metadata) {
		System.err.println(metadata.getStateStr());
		if (type.equals(ScannerIOMetadata.ACQUIRED)) {
			processBmpImage(metadata.getImage());
		} else if (type.equals(ScannerIOMetadata.INFO)) {
			System.out.println("info");
		} else if (type.equals(ScannerIOMetadata.MEMORY)) {
			processMemoryScan(metadata);
		} else if (type.equals(ScannerIOMetadata.FILE)) {
			addTempFile(metadata.getFile());
		} else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
			negotiateScan(metadata);
		} else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
			if (metadata.isFinished()) {
				System.out.println("Scan process finished");
				try {
					//clearScanFiles();
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
	 * Negocia los parametros del escaneo
	 * @param metadata metadatos del escaner
	 */
	private void negotiateScan(final ScannerIOMetadata metadata){
		ScannerDevice device = metadata.getDevice();
		try {
			device.setShowProgressBar(false);
			if (!showUI){
				device.setResolution(resolution);
				if (metadata instanceof TwainIOMetadata) {
					TwainSource source = ((TwainIOMetadata) metadata).getSource();
					source.setXferMech(scanMode);
					source.setImageFileFormat(TwainConstants.TWFF_BMP);

					source.setCapability(TwainConstants.CAP_DUPLEXENABLED,duplex==true?1:0);
					source.setCapability(TwainConstants.CAP_FEEDERENABLED,adf);
					source.setCapability(TwainConstants.ICAP_PIXELTYPE,bpp);
					source.setCapability(TwainConstants.ICAP_SUPPORTEDSIZES,size);
					source.setCapability(TwainConstants.ICAP_BRIGHTNESS, brightness);
					source.setCapability(TwainConstants.ICAP_CONTRAST,contrast);
				}
			}

			device.setShowUserInterface(showUI);
		} catch (ScannerIOException e) {
			e.printStackTrace();
			endProcess(true, false);
		}
	}

	/**
	 * Procesa el escaneo de una imagen en modo nativo
	 * @param image imagen a salvar
	 */
	private void processBmpImage(BufferedImage image){
		if (!isError){
			File outputFile = new File(path, prefix+ (numPage+1) + ".bmp");
			try {
				ImageIO.write(image, "bmp", outputFile);
				addTempFile(outputFile);
				addScanFile(outputFile);
				numPage++;
			} catch (IOException e) {
				e.printStackTrace();
				endProcess(true, false);
			}
		}
	}

	/**
	 * Permite obtener un {@link BufferedImage} de la informacion de la imagen para escaneos en color
	 * @param imageData informacion de la imagen
	 * @return informacion de la imagen
	 */
	private BufferedImage getBufferedColorImageFromData(ImageData imageData){
		byte[] twainbuf = imageData.getData();
		int width = imageData.getWidth();
		int height = imageData.getHeight();

		BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		int bpr = imageData.getBpr();

		// Cada elemento del buffer es una componente rgb, para componer el color rgb hay
		// que hacerlo con las 3 componentes r,g y b(un pixel de la imagen se compone con 3
		// posiciones del buffer), a cada componente se le aplica una mascara and y para
		// componer el color rgb se aplica un or binario de las 3 componentes desplazando
		// la r 16 bits a la izquierda y la g 8 bits a la izquierda
		int r, g, b, x, y , row = 0, pixel = 0;
		for (y = 0; y < height; y++) {
			for (x = 0; x < width; x++) {
				r = twainbuf[pixel++] & 0x00FF;
				g = twainbuf[pixel++] & 0x00FF;
				b = twainbuf[pixel++] & 0x00FF;
				bufferedImage.setRGB(x, y, (r << 16) | (g << 8) | b);
			}
			row += bpr;
			pixel = row;
		}
		bufferedImage.flush();

		return bufferedImage;
	}

	/**
	 * Permite obtener un {@link BufferedImage} de la informacion de la imagen para escaneos en escala de grises
	 * @param imageData informacion de la imagen
	 * @return informacion de la imagen
	 */
	private BufferedImage getBufferedGrayImageFromData(ImageData imageData){
		byte[] twainbuf = imageData.getData();
		int width = imageData.getWidth();
		int height = imageData.getHeight();

		width = imageData.getBpr();
		BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);

		int bpr = imageData.getBpr();

		// Cada elemento del buffer es una componente rgb que se utiliza para generar
		// el gris rgb poniendo a las 3 componentes el mismo valor (un pixel de la imagen se genera
		// a partir de una posicion del buffer), a la posicion del buffer se
		// le aplica una mascara and y para componer el color rgb se aplica un or binario de las 3
		// componentes desplazando la r 16 bits a la izquierda y la g 8 bits a la izquierda
		int r, g, b, x, y, row = 0, pixel = 0;
		for (y = 0; y < height; y++) {
			for (x = 0; x < width; x++) {
				r = twainbuf[pixel] & 0x00FF;
				g = twainbuf[pixel] & 0x00FF;
				b = twainbuf[pixel] & 0x00FF;
				pixel++;
				bufferedImage.setRGB(x, y, (r << 16) | (g << 8) | b);
			}
			row += bpr;
			pixel = row;
		}
		bufferedImage.flush();

		return bufferedImage;
	}

	/**
	 * Permite obtener los bits revertidos de un byte
	 * @param b byte del que se quiere obtener los bits
	 * @return bits revertidos del byte
	 * <p><b>
	 * Ejemplo:</b></br>
	 * b=11; en binario seria 00001011</br>
	 * el metodo devuelve 11010000</br>
	 * </p>
	 */
	private static int[] getReverseBitsFromByte(int b){
		int [] bits = new int[8];

		int i=7;
		for (byte m = 1; m != 0; m <<= 1) {
			bits[i--]=((b&m) != 0)?1:0;
		}
		return bits;
	}

	/**
	 * Permite obtener un {@link BufferedImage} de la informacion de la imagen para escaneos en blanco y negro
	 * @param imageData informacion de la imagen
	 * @return informacion de la imagen
	 */
	private BufferedImage getBufferedBwImageFromData(ImageData imageData){
		byte[] twainbuf = imageData.getData();
		int width = imageData.getWidth();
		int height = imageData.getHeight();

		int bpr = imageData.getBpr();
		width = bpr*8;
		BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);

		// Cada elemento del buffer se utiliza para generar los valores rgb de 8 pixeles de la imagen,
		// a la posicion del buffer se le aplica una mascara and para obtener los valores de los 8 últimos
		// bits, a esos 8 últimos bits se les aplica un reverse y cada una de los bits se utiliza para
		// generar un pixel de la imagen
		int x, y, row = 0, pixel = 0, bytePixel = 0, bit;
		int [] bits = null;
		for (y = 0; y < height; y++) {
			for (x = 0; x < bpr; x++) {
				bytePixel = twainbuf[pixel] & 0x00FF;
				bits = getReverseBitsFromByte(bytePixel);

				for (bit=0;bit<8;bit++){
					bufferedImage.setRGB((x*8)+bit, y, (bits[bit]==0)?0:0xFFFFFF);
				}
				pixel++;
			}
			row += bpr;
			pixel = row;
		}
		bufferedImage.flush();

		return bufferedImage;
	}

	/**
	 * Permite obtener un {@link BufferedImage} de la informacion de la imagen
	 * @param imageData informacion de la imagen
	 * @return informacion de la imagen
	 */
	private BufferedImage getBufferedImageFromData(ImageData imageData){

		if(bpp == ScanBitsPerPixelConstants.BLACK_WHITE){
			return getBufferedBwImageFromData(imageData);
		}else if(bpp == ScanBitsPerPixelConstants.GRAYS){
			return getBufferedGrayImageFromData(imageData);
		}else if(bpp == ScanBitsPerPixelConstants.COLOR){
			return getBufferedColorImageFromData(imageData);
		}

		return null;
	}

	/**
	 * Salva a un fichero serializado la informacion de la imagen
	 * @param imageData informacion de la imagen
	 * @param fileSerialize fichero a salvar
	 * @throws Exception
	 */
	private void saveMemoryBlockSerialized(ImageData imageData, File fileSerialize ) throws Exception{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(fileSerialize);
			oos = new ObjectOutputStream (fos);
			oos.writeObject ( imageData );

			addScanFile(fileSerialize);
		} catch (Exception e) {
			throw e;
		} finally {
			if (oos!=null){
				oos.close();
			}
			if (fos!=null){
				fos.close();
			}
		}

	}

	/**
	 * Salva la informacion de la imagen en un fichero jpg con la calidad indicada
	 * @param imageData informacion de la imagen
	 * @param file fichero a salvar
	 * @param quality calidad de la imagen 1.0 -> mejor calidad menos compresion, 0.0 -> peor calidad mayor compresion
	 */
	private void saveMemoryBlockJPG(ImageData imageData, File file, float quality ) throws Exception {
		BufferedImage bufferedImage = getBufferedImageFromData(imageData);
		Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = (ImageWriter)iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(quality);
		FileImageOutputStream output = null;
		try {
			output = new FileImageOutputStream(file);
			writer.setOutput(output);
			IIOImage image = new IIOImage(bufferedImage, null, null);
			writer.write(null, image, iwp);
			addScanFile(file);
		} catch (Exception e) {
			throw e;
		} finally {
			if (output!=null){
				output.close();
			}
		}
	}

	/**
	 * Procesa el escaneo de un bloque de memoria
	 * @param metadata datos devueltos por el escaner
	 */
	private void processMemoryScan(final ScannerIOMetadata metadata){
		if (!isError && (metadata instanceof TwainIOMetadata)) {
			try {
				TwainIOMetadata twaindata = (TwainIOMetadata) metadata;
				TwainTransfer.MemoryTransfer.Info info = twaindata.getMemory();

				byte[] twainbuf = info.getBuffer();
				int width = info.getWidth();
				int height = info.getHeight();
				int bpr = info.getBytesPerRow();

				ImageData imageData = new ImageData(width, height, bpr, twainbuf);

				//saveMemoryBlockPng(imageData,new File(path,"block"+memoryBlockTotal+".png"));
				if (generationMode == ScanModeConstants.SCAN_MODE_MEMORY_SERIALIZED) {
					saveMemoryBlockSerialized(imageData, new File(path,prefix+"-serialize"+memoryBlockTotal+".data"));
				} else if (generationMode == ScanModeConstants.SCAN_MODE_MEMORY_JPG_BLOCKS) {
					saveMemoryBlockJPG(imageData,new File(path,prefix+"-block"+memoryBlockTotal+".jpg"),(float)1.0);
				}

				imageData = null;

				memoryHeight+=height;
				memoryWidth=width;
				memoryBlockTotal++;

				if (metadata.isState(TwainConstants.STATE_TRANSFERRING)){
					if (generationMode == ScanModeConstants.SCAN_MODE_MEMORY_SERIALIZED){
						composePageSerialized(new File(path, prefix+"-page" + numPage + ".png"));
					} else if (generationMode == ScanModeConstants.SCAN_MODE_MEMORY_JPG_BLOCKS){
						composePageJPG(new File(path, prefix+"-page" + numPage + ".png"));
					}
					memoryBlockProcessed = memoryBlockTotal;
					memoryHeight=0;
					memoryRow=0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				endProcess(true, false);
			}
		}
	}

	/**
	 * Permite componer los diversos ficheros serializados correspondientes a cada bloque de memoria y
	 * genera un png para la pagina completa para escaneos en blanco y negro
	 * @param outputFile fichero a generar
	 * @throws Exception
	 */
	private void composePageSerializedBw(File outputFile) throws Exception {
		File fileSerialize = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ImageData imageData = null;
		int bpr, height, bit, i, row, pixel, x, bytePixel = 0;
		byte [] data;
		int [] bits = null;

		try {
			fileSerialize = new File(path,prefix+"-serialize"+memoryBlockProcessed+".data");

			fis = new  FileInputStream(fileSerialize);
			ois = new ObjectInputStream(fis);
			imageData = (ImageData)ois.readObject();


		} catch (Exception e) {
			throw e;
		} finally {
			if (ois!=null){
				ois.close();
			}
			if (fis!=null){
				fis.close();
			}
		}

		BufferedImage imageSerialized = new BufferedImage(imageData.getBpr()*8, memoryHeight, BufferedImage.TYPE_INT_RGB);

		for (i = memoryBlockProcessed; i< memoryBlockTotal;i++){
			try {
				fileSerialize = new File(path,prefix+"-serialize"+i+".data");

				fis = new  FileInputStream(fileSerialize);
				ois = new ObjectInputStream(fis);
				imageData = (ImageData)ois.readObject();

				ois.close();
				fis.close();
			} catch (Exception e) {
				throw e;
			} finally {
				if (ois!=null){
					ois.close();
				}
				if (fis!=null){
					fis.close();
				}
			}

			fileSerialize.delete();
			//FileUtils.deleteQuietly(fileSerialize);

			height = imageData.getHeight() + memoryRow;
			bpr = imageData.getBpr();
			data = imageData.getData();

			row = 0;
			pixel = 0;
			while(memoryRow < height) {
				for (x = 0; x < bpr; x++) {
					bytePixel = data[pixel] & 0x00FF;
					bits = getReverseBitsFromByte(bytePixel);

					for (bit=0;bit<8;bit++){
						imageSerialized.setRGB((x*8)+bit, memoryRow, (bits[bit]==0)?0:0xFFFFFF);
					}
					pixel++;

				}
				row += bpr;
				pixel = row;
				memoryRow++;
			}
		}
		imageSerialized.flush();

		numPage++;
		ImageIO.write(imageSerialized, "png", outputFile);
		addTempFile(outputFile);
		addScanFile(outputFile);
	}

	/**
	 * Permite componer los diversos ficheros serializados correspondientes a cada bloque de memoria y
	 * genera un png para la pagina completa para escaneos en escala de grises
	 * @param outputFile fichero a generar
	 * @throws Exception
	 */
	private void composePageSerializedGray(File outputFile) throws Exception {
		File fileSerialize = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ImageData imageData = null;
		int bpr, width, height, r, g, b, row, pixel, x, i;
		byte [] data = null;

		try {
			fileSerialize = new File(path,prefix+"-serialize"+memoryBlockProcessed+".data");

			fis = new  FileInputStream(fileSerialize);
			ois = new ObjectInputStream(fis);
			imageData = (ImageData)ois.readObject();

			ois.close();
			fis.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ois!=null){
				ois.close();
			}
			if (fis!=null){
				fis.close();
			}
		}

		BufferedImage imageSerialized = new BufferedImage(imageData.getBpr(), memoryHeight, BufferedImage.TYPE_BYTE_GRAY);

		for (i = memoryBlockProcessed; i< memoryBlockTotal;i++){
			try {
				fileSerialize = new File(path,prefix+"-serialize"+i+".data");

				fis = new  FileInputStream(fileSerialize);
				ois = new ObjectInputStream(fis);
				imageData = (ImageData)ois.readObject();

				ois.close();
				fis.close();
			} catch (Exception e) {
				throw e;
			} finally {
				if (ois!=null){
					ois.close();
				}
				if (fis!=null){
					fis.close();
				}
			}

			fileSerialize.delete();
			//FileUtils.deleteQuietly(fileSerialize);

			width = imageData.getWidth();
			height = imageData.getHeight() + memoryRow;
			bpr = imageData.getBpr();
			data = imageData.getData();

			row = 0;
			pixel = 0;
			while(memoryRow < height) {
				for (x = 0; x < width; x++) {
					r = data[pixel] & 0x00FF;
					g = data[pixel] & 0x00FF;
					b = data[pixel] & 0x00FF;
					pixel++;
					imageSerialized.setRGB(x, memoryRow, (r << 16) | (g << 8) | b);
				}
				row += bpr;
				pixel = row;
				memoryRow++;
			}
		}
		imageSerialized.flush();

		numPage++;
		ImageIO.write(imageSerialized, "png", outputFile);
		addTempFile(outputFile);
		addScanFile(outputFile);
	}

	/**
	 * Permite componer los diversos ficheros serializados correspondientes a cada bloque de memoria y
	 * genera un png para la pagina completa para escaneos en color
	 * @param outputFile fichero a generar
	 * @throws Exception
	 */
	private void composePageSerializedColor(File outputFile) throws Exception {
		File fileSerialize = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ImageData imageData = null;
		int bpr, width, height, r, g, b, row, pixel, x, i;
		byte [] data;

		BufferedImage imageSerialized = new BufferedImage(memoryWidth, memoryHeight, BufferedImage.TYPE_INT_RGB);

		for (i = memoryBlockProcessed; i< memoryBlockTotal;i++){
			try {
				fileSerialize = new File(path,prefix+"-serialize"+i+".data");

				fis = new  FileInputStream(fileSerialize);
				ois = new ObjectInputStream(fis);
				imageData = (ImageData)ois.readObject();

				ois.close();
				fis.close();
			} catch (Exception e) {
				throw e;
			} finally {
				if (ois!=null){
					ois.close();
				}
				if (fis!=null){
					fis.close();
				}
			}

			fileSerialize.delete();
			//FileUtils.deleteQuietly(fileSerialize);

			width = imageData.getWidth();
			height = imageData.getHeight() + memoryRow;
			bpr = imageData.getBpr();
			data = imageData.getData();

			row = 0;
			pixel = 0;
			while(memoryRow < height) {
				for (x = 0; x < width; x++) {
					r = data[pixel++] & 0x00FF;
					g = data[pixel++] & 0x00FF;
					b = data[pixel++] & 0x00FF;
					imageSerialized.setRGB(x, memoryRow, (r << 16) | (g << 8) | b);
				}
				row += bpr;
				pixel = row;
				memoryRow++;
			}
		}
		imageSerialized.flush();

		numPage++;
		ImageIO.write(imageSerialized, "png", outputFile);
		addTempFile(outputFile);
		addScanFile(outputFile);
	}

	/**
	 * Permite componer los diversos ficheros serializados correspondientes a cada bloque de memoria y genera un png para la pagina completa
	 * @param outputFile fichero a generar
	 * @throws Exception
	 */
	private void composePageSerialized(File outputFile) throws Exception {

		if(bpp == ScanBitsPerPixelConstants.BLACK_WHITE){
			composePageSerializedBw(outputFile);
		}else if(bpp == ScanBitsPerPixelConstants.GRAYS){
			composePageSerializedGray(outputFile);
		}else if(bpp == ScanBitsPerPixelConstants.COLOR){
			composePageSerializedColor(outputFile);
		}
	}

	/**
	 * Permite componer los diversos jpg correspondientes a cada bloque de memoria y genera un png para la pagina completa
	 * @param outputFile fichero a generar
	 * @throws Exception
	 */
	private void composePageJPG(File outputFile) throws Exception {
		File fileMemoryBlockJPG = null;
		BufferedImage imageBlocks = new BufferedImage(memoryWidth, memoryHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = imageBlocks.getGraphics();

		BufferedImage image = null;

		for (int i = memoryBlockProcessed; i< memoryBlockTotal;i++){
			fileMemoryBlockJPG = new File(path,prefix+"-block"+i+".jpg");
			image = ImageIO.read(fileMemoryBlockJPG);
			graphics.drawImage(ImageIO.read(fileMemoryBlockJPG),0,memoryRow,null);
			fileMemoryBlockJPG.delete();
			//FileUtils.deleteQuietly(fileMemoryBlockJPG);

			memoryRow+=image.getHeight();
		}
		imageBlocks.flush();
		graphics.dispose();

		numPage++;
		ImageIO.write(imageBlocks, "png", outputFile);
		addTempFile(outputFile);
		addScanFile(outputFile);
	}

	/**
	 * Permite procesar los ficheros escaneados y obtener el pdf final
	 * @param path directorio en el que se guarda el fichero, y que tambien se utilizara como directorio temporal
	 * @param fileName nombre del fichero
	 * @return nombre del fichero escaneado
	 * @throws Exception
	 */
	private String processScannedFiles(final String path, final String fileName) throws Exception {

		if (!isError){
			if (scanMode == ScanModeConstants.SCAN_MODE_FILE){
				if ((tempFiles!=null) && !tempFiles.isEmpty()){
					File file = (File)tempFiles.get(0);
					File newFile = new File(path,this.prefix);
					if (newFile.exists()){
						newFile.delete();
						//FileUtils.deleteQuietly(newFile);
					}
					file.renameTo(newFile);
					clearTempFiles();
					return fileName;
				}else{
					return null;
				}

			} else if ((scanMode == ScanModeConstants.SCAN_MODE_NATIVE) || (scanMode == ScanModeConstants.SCAN_MODE_MEMORY)) {
				if ((tempFiles!=null) && !tempFiles.isEmpty()){
					File newFile = new File(path,this.prefix);
					if (newFile.exists()){
						newFile.delete();
						//FileUtils.deleteQuietly(newFile);
					}

					FileOutputStream fos = null;
					Document document = null;
					try {
						fos = new FileOutputStream(newFile);
						document = new Document();
						PdfWriter.getInstance(document, fos);
						document.open();

						File fileProperties = (File)tempFiles.get(0);
						Image imageProperties = Image.getInstance(fileProperties.getPath());
						document.setPageSize(imageProperties);
						document.setMargins(0, 0, 0, 0);

						File file = null;
						Image image = null;
						Iterator it = tempFiles.iterator();
						while (it.hasNext()) {
							file = (File) it.next();
							document.newPage();
							image = Image.getInstance(file.getPath());
							document.add(image);
						}

					} catch (Exception e) {
						throw e;
					} finally{
						if (document!=null){
							document.close();
						}
						if (fos!=null){
							fos.close();
						}
					}
					clearTempFiles();

					return newFile.getPath();
				}else{
					return null;
				}
			}
		} else {
			clearTempFiles();
		}

		return null;
	}

	/**
	 * Permite obtener el nombre del fichero escaneado
	 * @return nombre del fichero escaneado, "error" si hubo algun error
	 */
	public int getScannedPages() {
		return (numPage>0)?numPage+1:0;
	}
}
