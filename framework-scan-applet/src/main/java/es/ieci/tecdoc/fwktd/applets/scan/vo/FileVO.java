package es.ieci.tecdoc.fwktd.applets.scan.vo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class FileVO {
	private int imageSelectIndex;	
	private List listImage;

	public FileVO(){
		listImage = new LinkedList();
	}

	public void addImage(File file){
		ElementVO ele = new ElementVO();
		if(ele.getImages() == null){
			ele.setImages(new LinkedList());
		}		
		ele.createIcon(file);
		//listImage.add(ele);
		listImage.addAll(ele.getImages());
	}
	
	public void addImage(BufferedImage bf){
		ElementVO ele;		
		ele = (ElementVO)listImage.get(imageSelectIndex);
		ele.createIcon(bf);	
	}
	public void addImage(Image im){
		ElementVO ele;		
		ele = (ElementVO)listImage.get(imageSelectIndex);
		ele.createIcon(im);	
	}
	
	public void createImage(){
		ElementVO ele = new ElementVO();
		if(ele.getImages() == null){
			ele.setImages(new LinkedList());
		}		
		listImage.add(ele);
		imageSelectIndex = listImage.size()-1;
	}	
	
	public void createImage(File file){
		ElementVO ele = new ElementVO();
		if(ele.getImages() == null){
			ele.setImages(new LinkedList());
		}		
		ele.createIcon(file);
		//listImage.add(ele);
		listImage.addAll(ele.getImages());
		imageSelectIndex = listImage.size()-1;
	}	

	public void createImage(BufferedImage bf){
		ElementVO ele = new ElementVO();
		if(ele.getImages() == null){
			ele.setImages(new LinkedList());
		}
		ele.createIcon(bf);
		listImage.add(ele);
		imageSelectIndex = listImage.size()-1;
	}

	public void createImage(Image im){
		ElementVO ele = new ElementVO();
		if(ele.getImages() == null){
			ele.setImages(new LinkedList());
		}
		ele.createIcon(im);
		listImage.add(ele);
		imageSelectIndex = listImage.size()-1;
	}

	public int getImageSelectIndex() {
		return imageSelectIndex;
	}
	
	public void setImageSelectIndex(int imageSelectIndex) {
		this.imageSelectIndex = imageSelectIndex;
	}
	
	public List getListImage() {
		return listImage;
	}

	public void setListImage(List listImage) {
		this.listImage = listImage;
	}
}
