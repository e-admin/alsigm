package es.ieci.tecdoc.fwktd.applets.scan.utils.format;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

import org.w3c.dom.Element;

import es.ieci.tecdoc.fwktd.applets.scan.vo.ImageVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;

public class JPGManager implements IFormatManager{
	public void configureDefaultCompression(ImageVO image,ImageWriteParam param,PerfilVO perfil){
		param.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
		param.setCompressionType("JPEG");
		float quality = 0.5f;
		if(perfil!=null && perfil.getQuality()!=0)
		{
			System.out.println("Quality jpeg: "+perfil.getQuality());
			quality =perfil.getQuality();
			quality = quality/100;
		}
		param.setCompressionQuality(quality);
	}
	
	public IIOMetadata saveDpi(BufferedImage img, ImageWriter writer,int dpiX,int dpiY) throws IOException{
		// Metadata (dpi)
        IIOMetadata data = writer.getDefaultImageMetadata(new ImageTypeSpecifier(img), null);
        Element tree = (Element)data.getAsTree("javax_imageio_jpeg_image_1.0");
        Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
        jfif.setAttribute("Xdensity", new Integer(dpiX).toString());
        jfif.setAttribute("Ydensity", new Integer(dpiY).toString());
        jfif.setAttribute("resUnits", "1"); // density is dots per inch
        data.setFromTree("javax_imageio_jpeg_image_1.0", tree);
        return data;
	}
	
	public ImageWriter getImageWriter(){
		return ImageIO.getImageWritersByFormatName("JPEG").next();
	}
}
