package es.ieci.tecdoc.fwktd.applets.scan.vo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.media.jai.PlanarImage;
import javax.swing.ImageIcon;

import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;

@SuppressWarnings("unchecked")
public class ElementVO {
	private List images;
	private int imageSelectIndex;
	private int numPages;
	
	public void createIcon(File file) {
		try {
			FileInputStream in = new FileInputStream(file.getAbsoluteFile());
			FileChannel channel = in.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
			channel.read(buffer);

			Image image = null;
			SeekableStream stream = new ByteArraySeekableStream(buffer.array());
			String[] names = ImageCodec.getDecoderNames(stream);
			
			ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream,	null);
			numPages = numPages + dec.getNumPages();
			for(int i=0; i<numPages; i++){
				RenderedImage im = dec.decodeAsRenderedImage(i);				
				image = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
				ImageVO imageVO = new ImageVO();
				ImageIcon icon = new ImageIcon(image);
				imageVO.setIcon(icon);
				imageVO.setHeight(icon.getIconHeight());
				imageVO.setWidth(icon.getIconWidth());
				
				imageVO.setName(file.getName().split("\\.")[0]);
				imageVO.setExtension(file.getName().split("\\.")[1]);
				imageVO.setPath(file.getAbsolutePath().split(file.getName())[0]);
				//imageVO.setColor("Escala de grises (8 bits)");
				
				images.add(imageVO);
				imageSelectIndex = images.size()-1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createIcon(BufferedImage bf){
		ImageVO imageVO = new ImageVO();
		imageVO.setIcon(new ImageIcon(bf));
		images.add(imageVO);
		imageSelectIndex = images.size()-1;
		numPages = images.size();
	}
	
	public void createIcon(Image im){
		ImageVO imageVO = new ImageVO();
		imageVO.setIcon(new ImageIcon(im));
		images.add(imageVO);
		imageSelectIndex = images.size()-1;
		numPages = images.size();
	}
	
	public List getImages() {
		return images;
	}

	public void setImages(List images) {
		this.images = images;
	}
	public int getImageSelectIndex() {
		return imageSelectIndex;
	}
	public void setImageSelectIndex(int imageSelectIndex) {
		this.imageSelectIndex = imageSelectIndex;
	}
	
	public int getNumPages() {
		return numPages;
	}
	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}
}
