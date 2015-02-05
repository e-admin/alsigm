package es.ieci.tecdoc.fwktd.applets.scan.utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtils {
	
	public static int getImageTypeFromImage(RenderedImage img){
		return getImageType(img.getColorModel());
	}
	
	public static int getImageType(ImageReader reader){
		int bitsPerPixel=24;
		try{
			reader.getHeight(0);
			Field f = reader.getClass().getDeclaredField("bitsPerPixel"); 
			f.setAccessible(true);
			bitsPerPixel=Integer.valueOf(String.valueOf(f.get(reader)));
			//return getImageType(reader.getImageTypes(0).next().getColorModel());
			return getImageType(bitsPerPixel);
		}catch(Exception e){
			e.printStackTrace();
		}
		return getImageType(bitsPerPixel);
	}
	
	public static int getImageType(ColorModel model){
		return getImageType(model.getPixelSize());
	}
	
	private static int getImageType(int pixelSize){
		int imgType=-1;
		switch(pixelSize){
		case 1:
				imgType=BufferedImage.TYPE_BYTE_BINARY;
				break;
		case 8:
				imgType=BufferedImage.TYPE_BYTE_GRAY;
				break;
		case 24:
				imgType=BufferedImage.TYPE_INT_RGB;
				break;
		case 32:
				imgType=BufferedImage.TYPE_INT_ARGB;
				break;
		}
		return imgType;
		
	}
	
	
	public static Dimension getBmpDpi(ImageReader reader){
		try{
			Object metadata=reader.getImageMetadata(0);
			
			Field xPixelsPerMeter = metadata.getClass().getDeclaredField("xPixelsPerMeter");
			xPixelsPerMeter.setAccessible(true);
			Integer value = (Integer)xPixelsPerMeter.get(metadata);
			int hDpi=(int)Math.ceil(value.intValue() / 100 * 2.54f);
			
			Field yPixelsPerMeter = metadata.getClass().getDeclaredField("yPixelsPerMeter");
			yPixelsPerMeter.setAccessible(true);
			value = (Integer)yPixelsPerMeter.get(metadata);
			int vDpi=(int)Math.ceil(value.intValue() / 100 * 2.54f);
	
			return new Dimension(vDpi,hDpi);
		}catch(Exception e){
			System.out.println("Error leyendo dpi: "+e.getClass()+" "+e.getMessage());
			e.printStackTrace();
		}
        return null;
	}
	
	public static Dimension getImageDimension(ImageReader reader){
		Dimension dim=null;
		try{
			int width=reader.getWidth(0);
			int height=reader.getHeight(0);
			dim=new Dimension(width,height);
		}catch(Exception e){
			System.out.println("Error leyendo dimension: "+e.getClass()+" "+e.getMessage());
			e.printStackTrace();
		}
		return dim;
	}
	
	public static ImageReader getImageReader(File f){
		ImageReader reader=null;
		try{
			ImageInputStream iis=ImageIO.createImageInputStream(f);
			Iterator<ImageReader> it=ImageIO.getImageReaders(iis);
			reader=it.next();
			reader.setInput(iis);	
		}catch(Exception e){
			System.out.println("Error leyendo dimension: "+e.getClass()+" "+e.getMessage());
			e.printStackTrace();
		}
		return reader;
	}
}
