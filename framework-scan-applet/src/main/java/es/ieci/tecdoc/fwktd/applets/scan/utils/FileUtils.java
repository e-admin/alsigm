package es.ieci.tecdoc.fwktd.applets.scan.utils;

import java.awt.Dimension;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import uk.co.mmscomputing.imageio.tiff.TIFFImageWriteParam;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfICCBased;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.BmpImage;

import es.ieci.tecdoc.fwktd.applets.scan.utils.format.FormatManagerFactory;
import es.ieci.tecdoc.fwktd.applets.scan.utils.format.IFormatManager;
import es.ieci.tecdoc.fwktd.applets.scan.utils.format.JPGManager;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ImageVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;

public class FileUtils{
	//@SuppressWarnings("rawtypes")
	//private static HashMap map = Capabilities.getColorCombo(0, 1);
	
	/**
	 * Salva la imagen con todas su paginas como fichero tiff
	 * @param file
	 * @param images
	 * @param extension
	 */
	@SuppressWarnings("rawtypes")
	public static boolean saveAsMutipageTIFF(File file, List images, String extension, PerfilVO perfil) {
		return saveGeneric(file, images, extension, perfil);
//		try {
//			ImageWriter writer;
//			writer = (ImageWriter)(ImageIO.getImageWritersByFormatName("TIFF").next());
//			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
//			writer.setOutput(ios);
//			writer.prepareWriteSequence(null);
//
//			Iterator it = images.iterator();
//			while (it.hasNext()) {
//				ImageVO im = (ImageVO) it.next();
//
//				BufferedImage bimg = null;
//
//				Image image = BmpImage.getImage(new FileInputStream(new File(im.getImage())));
//				try {
//					bimg = ImageIO.read(new File(im.getImage()));
//				}
//				catch (Exception e) {
//					System.out.println("Error al leer la imagen para obtener su resolución y color");
//				}
//
//				ImageWriteParam writerParam = writer.getDefaultWriteParam();
//				HashMap map = Capabilities.getColorCombo(0, 1);
//				((TIFFImageWriteParam)writerParam).setXYResolution(Float.valueOf(image.getDpiX()), Float.valueOf(image.getDpiY()));
//				//Color es B/N
//				if(bimg.getColorModel().getPixelSize()==1){
//					writerParam.setCompressionMode(TIFFImageWriteParam.MODE_EXPLICIT);
//					writerParam.setCompressionType("t6mmr");
//
//
//				}else {
//					writerParam.setCompressionMode(TIFFImageWriteParam.MODE_EXPLICIT);
//					writerParam.setCompressionType("jpeg");
//
//				}
//
//				writer.writeToSequence(new IIOImage(bimg,null,null),writerParam);
//			}
//			writer.endWriteSequence();
//			((ImageOutputStream)writer.getOutput()).close();
//		} catch (IOException e) {
//		}
	}

	/**
	 * Salva la imagen con todas su paginas como fichero tiff
	 * @param file
	 * @param images
	 * @param extension
	 */
	public static boolean  saveAsTIFF(File file, ImageVO im, String extension, PerfilVO perfil) {
		return saveGeneric(file,im,extension,perfil);
//		try {
//			ImageWriter writer;
//			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("tiff");
//			ImageWriter next = iter.next();
//			writer = next;
//
//			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
//			writer.setOutput(ios);
//			writer.prepareWriteSequence(null);
//
//			BufferedImage bimg = null;
//
//			Image image = BmpImage.getImage(new FileInputStream(new File(im.getImage())));
//			try {
//				bimg = ImageIO.read(new File(im.getImage()));
//			}
//			catch (Exception e) {
//				System.out.println("Error al leer la imagen para obtener su resolución y color");
//			}
//			ImageWriteParam writerParam = writer.getDefaultWriteParam();
//
//			((TIFFImageWriteParam)writerParam).setXYResolution(Float.valueOf(image.getDpiX()), Float.valueOf(image.getDpiY()));
//			HashMap map = Capabilities.getColorCombo(0, 1);
//
//			//Color es B/N
//			if(bimg.getColorModel().getPixelSize()==1){
//				writerParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//				writerParam.setCompressionType("t6mmr");
//			}else {
//				writerParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//				writerParam.setCompressionType("jpeg");
//			}
//
//			writer.writeToSequence(new IIOImage(bimg,null,null),writerParam);
//			writer.endWriteSequence();
//			((ImageOutputStream)writer.getOutput()).close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static boolean  saveAsJPG(File file, ImageVO im, String extension, PerfilVO perfil){
		return saveGeneric(file,im,extension,perfil);
		//if(!existsfile(file)){
		//	saveGeneric(file,im,"JPEG 2000",perfil);
		//}
//		try {
//			ImageWriter writer;
//			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
//			ImageWriter next = iter.next();
//			writer = next;

//			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
//			writer.setOutput(ios);
//			writer.prepareWriteSequence(null);
//			BufferedImage bimg = null;
//			try {
//				bimg = ImageIO.read(new File(im.getImage()));
//			}
//			catch (Exception e) {
//				System.out.println("Error al leer la imagen para obtener su resolución y color");
//			}
//			ImageWriteParam writerParam = writer.getDefaultWriteParam();
//			writerParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
//			writerParam.setCompressionType("JPEG");
//			float quality = 0.5f;
//			if(perfil!=null && perfil.getQuality()!=0)
//			{
//				System.out.println("Quality jpeg: "+perfil.getQuality());
//				quality =perfil.getQuality();
//			}
//			float q = quality/100;
//			writerParam.setCompressionQuality(q);
//			Image image = BmpImage.getImage(new FileInputStream(new File(im.getImage())));

			// Metadata (dpi)
//            IIOMetadata data = writer.getDefaultImageMetadata(new ImageTypeSpecifier(bimg), writerParam);
//            Element tree = (Element)data.getAsTree("javax_imageio_jpeg_image_1.0");
//            Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
//            jfif.setAttribute("Xdensity", new Integer(image.getDpiX()).toString());
//            jfif.setAttribute("Ydensity", new Integer(image.getDpiX()).toString());
//            jfif.setAttribute("resUnits", "1"); // density is dots per inch
//            data.setFromTree("javax_imageio_jpeg_image_1.0", tree);
//			writer.writeToSequence(new IIOImage(bimg,null,data),writerParam);
//			writer.endWriteSequence();
//			((ImageOutputStream)writer.getOutput()).close();
//
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Salva la imagen y todas sus paginas como fichero pdf
	 * @param file
	 * @param images
	 * @param inst
	 */
	@SuppressWarnings("rawtypes")
	public static void saveAsPDF(File file, List images) {
		Document document = new Document(PageSize.A4);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));

			//TODO PDF TIPO PDF/A1-b
			writer.setPDFXConformance(PdfWriter.PDFA1B);
			document.open();
			document.setPageSize(PageSize.A4);

			PdfDictionary outi = new PdfDictionary(PdfName.OUTPUTINTENT);
			outi.put(PdfName.OUTPUTCONDITIONIDENTIFIER, new PdfString("sRGB IEC61966-2.1"));
			outi.put(PdfName.INFO, new PdfString("sRGB IEC61966-2.1"));
			outi.put(PdfName.S, PdfName.GTS_PDFA1);

			InputStream inst = FileUtils.class.getClassLoader().getResourceAsStream("resources/sRGB.profile");
			ICC_Profile icc = ICC_Profile.getInstance(inst);

			PdfICCBased ib = new PdfICCBased(icc);
			ib.remove(PdfName.ALTERNATE);
			outi.put(PdfName.DESTOUTPUTPROFILE, writer.addToBody(ib).getIndirectReference());
			writer.getExtraCatalog().put(PdfName.OUTPUTINTENTS, new PdfArray(outi));

			Iterator it = images.iterator();
			while (it.hasNext()) {
				ImageVO im = (ImageVO) it.next();

				HashMap map = Capabilities.getColorCombo(0, 1);
				File fileTemp = null;
				//Color es B/N
				if(im.getColor().equals(map.get("0"))){
					//pasar a tiff
					fileTemp = saveAsTIFFTemp(im);
				}else {
					//pasar a jpeg
					fileTemp = saveAsJPGTemp(im);
				}

				if(!FileUtils.existsfile(fileTemp)){
					return;
				}
				Image image2 = Image.getInstance(fileTemp.getAbsolutePath());
				image2.setAbsolutePosition(0, 0);

				if ((image2.getWidth() > PageSize.A4.getWidth()) || (image2.getHeight() > PageSize.A4.getHeight())) {
					//escala la imagen si no cabe en A4
					image2.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
				}

				document.add(image2);
				document.newPage();
			}
			writer.createXmpMetadata();

			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	public static File saveAsJPGTemp(ImageVO im){
		File file=saveTempGeneric(im,"JPEG");
		return file;
//		File file=null;
//		try {
//			ImageWriter writer;
//			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
//			ImageWriter next = iter.next();
//			writer = next;
//
//			String path = im.getImage();
//			path = path.replace("BMP", "TMP");
//			file = new File(path);
//
//			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
//			writer.setOutput(ios);
//			writer.prepareWriteSequence(null);
//			ImageIcon icono = ImageUtils.getIcon(im.getImage());
//			BufferedImage bimg;
//
//			ImageWriteParam writerParam = writer.getDefaultWriteParam();
//			writerParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
//			writerParam.setCompressionType("JPEG");
//			writerParam.setCompressionQuality(0.5f);
//
//			//bimg = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_INT_RGB);
//			//bimg.getGraphics().drawImage(icono.getImage(), 0, 0, null);
//			//writer.writeToSequence(new IIOImage(bimg,null,null),writerParam);
//			writer.writeToSequence(new IIOImage((RenderedImage)icono.getImage(),null,null),writerParam);
//			writer.endWriteSequence();
//			((ImageOutputStream)writer.getOutput()).close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return file;
	}

	public static File saveAsTIFFTemp(ImageVO im) {
		return saveTempGeneric(im,"TIFF");
//		File file=null;
//		try {
//			ImageWriter writer;
//			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("tiff");
//			ImageWriter next = iter.next();
//			writer = next;
//
//			String path = im.getImage();
//			path = path.replace("BMP", "TMP");
//			file = new File(path);
//
//			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
//			writer.setOutput(ios);
//			writer.prepareWriteSequence(null);
//			ImageIcon icono = ImageUtils.getIcon(im.getImage());
//			BufferedImage bimg;
//
//			ImageWriteParam writerParam = writer.getDefaultWriteParam();
//			HashMap map = Capabilities.getColorCombo(0, 1);
//			//Color es B/N
//			if(im.getColor().equals(map.get("0"))){
//				writerParam.setCompressionMode(TIFFImageWriteParam.MODE_EXPLICIT);
//				writerParam.setCompressionType("t6mmr");
//				bimg = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_BYTE_BINARY);
//
//			}else {
//				writerParam.setCompressionMode(TIFFImageWriteParam.MODE_EXPLICIT);
//				writerParam.setCompressionType("jpeg");
//				writerParam.setCompressionQuality(0.5f);
//				bimg = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_INT_RGB);
//			}
//
//			bimg.getGraphics().drawImage(icono.getImage(), 0, 0, null);
//			writer.writeToSequence(new IIOImage(bimg,null,null),writerParam);
//			writer.endWriteSequence();
//			((ImageOutputStream)writer.getOutput()).close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return file;
	}


	/**
	 * Crea un byte[] de un icono
	 * @param icon
	 * @return
	 */
	@SuppressWarnings("unused")
	private static byte[] createByteArray(ImageIcon icon) {
		java.awt.Image image = icon.getImage();
		try {
			int[] pix = new int[image.getWidth(null) * image.getHeight(null)];
			PixelGrabber pg = new PixelGrabber(image, 0, 0, image.getWidth(null), image.getHeight(null), pix, 0, image.getWidth(null));
			pg.grabPixels();

			byte[] pixels = new byte[image.getWidth(null) * image.getHeight(null)];
			for (int j = 0; j < pix.length; j++) {
				pixels[j] = new Integer(pix[j]).byteValue();
			}

			return pixels;
		} catch (InterruptedException ex) {
			return null;
		}
	}

	public static void savePerfiles(PerfilesVO perfiles){

		String ruta = perfiles.getUserHome();
		File dir = new File(ruta+File.separatorChar+"iecisa");
		if(!(dir.exists())){
			dir.mkdir();
		}
		File file = new File(dir.getAbsolutePath()+File.separatorChar+"config.xml");
		System.out.println("Guardado el fichero de perfiles en: "+file.getAbsolutePath());
		//FileOutputStream fileOutput;
		try {
			OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file),Charset.forName("UTF-8"));
			fileWriter.write(perfiles.toXml());
			fileWriter.flush();
			fileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readPerfiles(PerfilesVO perfiles) throws Exception{
		String ruta = perfiles.getUserHome();
		File dir = new File(ruta+File.separatorChar+"iecisa");
		if(!(dir.exists())){
			dir.mkdir();
		}
		String xml = null;
		File file = new File(dir.getAbsolutePath()+File.separatorChar+"config.xml");
		byte[] buffer = new byte[(int)file.length()];
		BufferedInputStream bufferInputStream=null;
		try{
			bufferInputStream = new BufferedInputStream(new FileInputStream(file));
			bufferInputStream.read(buffer);
			xml = new String(buffer, "UTF-8");
		}finally {
			if(bufferInputStream!=null){
				bufferInputStream.close();
			}
		}
		return xml;
	}
	
	
	
	public static String getFileName(String filepath){
		//String filename=new File(filepath).getName();
		int extPos=filepath.lastIndexOf(".");
		return filepath.substring(0,extPos);
	}
	
	private static boolean saveGeneric(File file, ImageVO im, String extension, PerfilVO perfil) {
		return saveGeneric(file,Arrays.asList(new ImageVO[]{im}),extension,perfil,false);
	}
	
	@SuppressWarnings("rawtypes")
	private static boolean saveGeneric(File file, List images, String extension, PerfilVO perfil) {
		return saveGeneric(file,images,extension,perfil,false);
	}
	
	@SuppressWarnings("rawtypes")
	private static boolean saveGeneric(File file, List images, String extension, PerfilVO perfil,boolean isTemp) {
		try {
			IFormatManager format=FormatManagerFactory.getFormatManager(extension);
			ImageWriter writer;
			writer = format.getImageWriter();
			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
			writer.setOutput(ios);
			writer.prepareWriteSequence(null);
			
			Iterator it = images.iterator();
			ImageWriteParam writerParam = writer.getDefaultWriteParam();
			
			while (it.hasNext()) {
				ImageVO im = (ImageVO) it.next();

				BufferedImage img = null;
				File fImg=new File(im.getImage());
				if(!fImg.exists() || fImg.length()==0 ){ return false; }
				ImageInputStream iis=ImageIO.createImageInputStream(new FileInputStream(im.getImage()));
				ImageReader reader=ImageIO.getImageReaders(iis).next();
				reader.setInput(iis, false, false);
				
				IIOMetadata metadata=null;
				try {
					//Image image = BmpImage.getImage(new FileInputStream(new File(im.getImage())));
					//Dimension dim=new Dimension(image.getDpiX(),image.getDpiY());
					Dimension dim=ImageUtils.getBmpDpi(reader);
					
					img = ImageIO.read(new File(im.getImage()));
					if(!isTemp){
						if(format instanceof JPGManager){
							metadata=format.saveDpi(img, writer,
									(int)Math.round(dim.getWidth()),(int)Math.round(dim.getHeight()));
						}else{
							//para evitar un problema de classcastexception si hay varios classloader con esta clase
							Method m=writerParam.getClass().getMethod("setXYResolution", new Class[]{double.class,double.class});
							m.invoke(writerParam, new Object[]{dim.getWidth(), dim.getHeight()});
							//((TIFFImageWriteParam)writerParam).setXYResolution(
							//		dim.getWidth(), dim.getHeight());
						}
					}
					//image=null;
					
				}catch (Throwable e) {
					System.out.println("No hay suficiente memoria para realizar la operacion :"+e.getClass()+" "+e.getMessage());
					e.printStackTrace();
				}
				
				if(img==null){
					return false;
				}
						
				format.configureDefaultCompression(im,writerParam, perfil);
				
				//Provoca que falle con 300ppp
//				if(im.getColor().equals(map.get("0"))){
//					try{
//						BufferedImage bimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
//						bimg.getGraphics().drawImage(img, 0, 0, null);
//						img=bimg;
//					}catch(Throwable e){
//						e.printStackTrace();
//						return;
//					}
//				}
				
				writer.writeToSequence(new IIOImage(img,null,metadata),writerParam);
				img=null;
			}
			writer.endWriteSequence();
			((ImageOutputStream)writer.getOutput()).close();
			writer.reset();
			writer=null;
		} catch (IOException e) {
			System.out.println("Error saveGeneric: "+e.getClass()+" "+e.getMessage());
			e.printStackTrace();
			return false;
		}catch (Throwable e){
			System.out.println("No hay suficientes recursos para realizar la operacion: "+e.getClass()+" "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static File saveTempGeneric(ImageVO im,String idFormat){
		File file=null;
		
		String path = im.getImage();
		path = path.replace("BMP", "TMP");
		file = new File(path);
		
		saveGeneric(file,Arrays.asList(new ImageVO[]{im}),idFormat, null,true);
		return file;
	}	
	
	public static String getDirPath(String path){
		int pos=path.lastIndexOf(File.separator);
		return path.substring(0,pos);
	}
	
	public static boolean existsfile(File file){
		return existsfile(file,0);
	}
	
	public static boolean existsfile(File file,int noSize){
		if(file==null || !file.exists() || file.length()<=noSize){
			return false;
		}
		return true;
	}
}
