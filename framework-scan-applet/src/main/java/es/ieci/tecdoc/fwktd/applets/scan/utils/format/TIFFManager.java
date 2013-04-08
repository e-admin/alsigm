package es.ieci.tecdoc.fwktd.applets.scan.utils.format;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;

import uk.co.mmscomputing.imageio.tiff.TIFFImageWriteParam;
import es.ieci.tecdoc.fwktd.applets.scan.utils.Capabilities;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ImageVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;

@SuppressWarnings("rawtypes")
public class TIFFManager implements IFormatManager{
	private static HashMap map = Capabilities.getColorCombo(0, 1);
	
	public void configureDefaultCompression(ImageVO image,ImageWriteParam param,PerfilVO perfil){
		param.setCompressionMode(TIFFImageWriteParam.MODE_EXPLICIT);
		if(image.getColor().equals(map.get("0"))){
			param.setCompressionType("t6mmr");
		}else {
			param.setCompressionType("jpeg");
		}
	}
	
	//No se usa
	public IIOMetadata saveDpi(BufferedImage img, ImageWriter writer,int width,int height) throws IOException{	
		return null;
	}
	
	public ImageWriter getImageWriter(){
		return ImageIO.getImageWritersByFormatName("TIFF").next();
	}
}
