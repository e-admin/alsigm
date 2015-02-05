package es.ieci.tecdoc.fwktd.applets.scan.utils.format;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;

import es.ieci.tecdoc.fwktd.applets.scan.vo.ImageVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;

public interface IFormatManager {
	void configureDefaultCompression(ImageVO image, ImageWriteParam param,PerfilVO perfil);
	IIOMetadata saveDpi(BufferedImage img, ImageWriter writer,int dpiX,int dpiY) throws IOException;
	// existe un bug en algunos ImageWriteer que impide que la memoria de la imagen no se libere hasta que 
	//el writer se libera. Al estar este en una lista interna del sistema sólo se libera al finalizar la JVM.
	//http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6331429
	
	//El motivo de este metodo, es para poder independizar la obtención del writer de la cadena extensión recibida
	//Permite obtener el Writer de la lógica original, o el necesario para el método alternativo.
	//public ImageWriter getImageWriter(boolean secondTry);
	public ImageWriter getImageWriter();
}
