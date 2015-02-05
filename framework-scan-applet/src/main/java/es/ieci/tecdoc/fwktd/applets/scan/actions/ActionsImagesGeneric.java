package es.ieci.tecdoc.fwktd.applets.scan.actions;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import es.ieci.tecdoc.fwktd.applets.scan.applet.IdocScanInterface;
import es.ieci.tecdoc.fwktd.applets.scan.key.KeysUtils;
import es.ieci.tecdoc.fwktd.applets.scan.key.MessageKeys;
import es.ieci.tecdoc.fwktd.applets.scan.utils.ClipMover;
import es.ieci.tecdoc.fwktd.applets.scan.utils.Cropping;
import es.ieci.tecdoc.fwktd.applets.scan.utils.FileUtils;
import es.ieci.tecdoc.fwktd.applets.scan.utils.ImageUtils;
import es.ieci.tecdoc.fwktd.applets.scan.utils.Messages;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ElementVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.FileVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ImageVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.OptionsUIVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ParametrosVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;

public class ActionsImagesGeneric {

//	public static ImageIcon getIcon(String path) {
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(new File(path));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return new ImageIcon(image);
//	}
//
	private static BufferedImage createBufferedImage(Image image,int type) {
		try{
			int w = image.getWidth(null);
			int h = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(w, h, type);
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(image, 0, 0, null);
			return bufferedImage;
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}	
	}

	/**
	 * Muestra la imagen seleccionada de la lista en el visor del applet a
	 * escala real
	 * 
	 * @param ui
	 */
	//TODO REESCALA la imagen escaneada en el applet
	@SuppressWarnings({"unchecked","rawtypes"})
	public static void view(final IdocScanInterface ui) {
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				scale(ui);
//				JLabel jl;
//				ImageIcon icono;
//
//				FileVO fileVO = (FileVO) ui.getFileVO();
//				if (fileVO.getListImage().size() > 0) {
//					List lista = fileVO.getListImage();
//					int index = fileVO.getImageSelectIndex();
//					ImageVO imageVO = (ImageVO) lista.get(index);
//					String pathImg = imageVO.getImage();
//					icono = getIcon(pathImg);
//					Image img = icono.getImage();
//
//					int select = fileVO.getImageSelectIndex() + 1;
//
//					ui.getJLabelPage().setText("Pagina " + select + " de " + fileVO.getListImage().size());
//					ui.getJLabelPage().repaint();
//
//					jl = new JLabel(icono);
//
//					int w = ui.getJScrollPaneIcon().getWidth();
//					int h = ui.getJScrollPaneIcon().getHeight();
//					if (w > h) {
//						w = h;
//					} else {
//						h = w;
//					}
//					
//					float alto = icono.getIconHeight();
//					float ancho = icono.getIconWidth();
//					float nuevo_ancho = w - 20;
//					float nuevo_alto = h - 20;
//					
//					float proporcion = ancho / alto; 
//					nuevo_ancho = nuevo_alto * proporcion;
//					
//					img = img.getScaledInstance((int)nuevo_ancho, (int)nuevo_alto, java.awt.Image.SCALE_SMOOTH);
//					ImageIcon icon = new ImageIcon(img);
//					jl = new JLabel(icon);
//					jl.setBounds(1, 1, 100, 100 - 1);
//
//					try {
//						BufferedImage buffer = createBufferedImage(img);
//						ui.setCrop(new Cropping(buffer));
//						// ClipMover mover = new ClipMover(ui.getCrop());
//						// ui.getCrop().addMouseListener(mover);
//						// ui.getCrop().addMouseMotionListener(mover);
//					} catch (Exception e) {
//					}
//
//					ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//					ui.repaint();
//					imageVO.setWidth(icon.getIconWidth());
//					imageVO.setHeight(icon.getIconHeight());
//					return null;
//				} else {
//					jl = new JLabel();
//					ui.getJLabelPage().setText("");
//					ui.getJScrollPaneIcon().setViewportView(jl);
//					ui.repaint();
					return null;
//				}
			}
		});
		return;
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public static void cropping(final IdocScanInterface ui) {
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				loadImage(ui,0,0);
//				JLabel jl;
//				FileVO fileVO = (FileVO) ui.getFileVO();
//				if (fileVO.getListImage().size() > 0) {
//					ImageVO imageVO = (ImageVO) fileVO.getListImage().get(
//							fileVO.getImageSelectIndex());
//
//					ImageIcon icono = getIcon(imageVO.getImage());
//					Image img = icono.getImage();
//
//					int imageSelect = fileVO.getImageSelectIndex() + 1;
//					ui.getJLabelPage().setText("Pagina " + imageSelect + " de "	+ fileVO.getListImage().size());
//					ui.getJLabelPage().repaint();
//
//					jl = new JLabel(icono);
//					jl.setBounds(1, 1, icono.getIconWidth() - 1, icono
//							.getIconHeight() - 1);
//					try {
//						BufferedImage buffer = createBufferedImage(img);
//						ui.setCrop(new Cropping(buffer));
						ClipMover mover = new ClipMover(ui.getCrop());
						ui.getCrop().addMouseListener(mover);
						ui.getCrop().addMouseMotionListener(mover);
//					} catch (Exception e) {
//					}
//
//					ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//					ui.repaint();
//					imageVO.setWidth(icono.getIconWidth());
//					imageVO.setHeight(icono.getIconHeight());
//					return null;
//				} else {
//					jl = new JLabel();
//					ui.getJLabelPage().setText("");
//					ui.getJScrollPaneIcon().setViewportView(jl);
//					ui.repaint();
					return null;
//				}
			}
		});
		return;
	}

	/**
	 * Muestra la imagen seleccionada de la lista en el visor del applet a
	 * escala real
	 * 
	 * @param ui
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static void original(final IdocScanInterface ui) {
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				loadImage(ui,0,0);
//				JLabel jl;
//				FileVO fileVO = (FileVO) ui.getFileVO();
//				if (fileVO.getListImage().size() > 0) {
//					ImageVO imageVO = (ImageVO) fileVO.getListImage().get(fileVO.getImageSelectIndex());
//
//					ImageIcon icono = getIcon(imageVO.getImage());
//					Image img = icono.getImage();
//
//					int imageSelect = fileVO.getImageSelectIndex() + 1;
//					ui.getJLabelPage().setText("Pagina " + imageSelect + " de "	+ fileVO.getListImage().size());
//					ui.getJLabelPage().repaint();
//
//					jl = new JLabel(icono);
//					jl.setBounds(1, 1, icono.getIconWidth() - 1, icono
//							.getIconHeight() - 1);
//					// ui.getJScrollPaneIcon().setViewportView(jl);
//
//					try {
//						BufferedImage buffer = createBufferedImage(img);
//						ui.setCrop(new Cropping(buffer));
//						// ClipMover mover = new ClipMover(ui.getCrop());
//						// ui.getCrop().addMouseListener(mover);
//						// ui.getCrop().addMouseMotionListener(mover);
//					} catch (Exception e) {
//					}
//					ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//
//					ui.repaint();
//					imageVO.setWidth(icono.getIconWidth());
//					imageVO.setHeight(icono.getIconHeight());
//					return null;
//				} else {
//					jl = new JLabel();
//					ui.getJLabelPage().setText("");
//					ui.getJScrollPaneIcon().setViewportView(jl);
//					ui.repaint();
					return null;
//				}
			}
		});
		return;
	}

	/**
	 * Open File - Abre una imagen y la aï¿½ade a la lista de imagenes
	 * 
	 * @param ui
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static File openFile(final IdocScanInterface ui) {
		File file = (File) AccessController
				.doPrivileged(new PrivilegedAction() {
					public Object run() {
						JFileChooser fc = new JFileChooser();
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						File file = null;
						if (fc.showOpenDialog((Component)ui) == JFileChooser.APPROVE_OPTION) {
							file = fc.getSelectedFile();
						}
						return file;
					}
				});
		return file;
	}

	/**
	 * Muestra siguiente Imagen en el applet
	 */
	public static void next(final IdocScanInterface ui) {
		FileVO ele = (FileVO) ui.getFileVO();
		if (ele.getImageSelectIndex() + 2 > ele.getListImage().size()) {
			ele.setImageSelectIndex(0);
		} else {
			ele.setImageSelectIndex(ele.getImageSelectIndex() + 1);
		}
	}

	/**
	 * Muestra siguiente Pagina en el applet
	 */
	public static void nextImage(final IdocScanInterface ui) {
		ElementVO ele = (ElementVO) ui.getFileVO().getListImage().get(
				ui.getFileVO().getImageSelectIndex());
		if (ele.getImageSelectIndex() + 2 > ele.getImages().size()) {
			ele.setImageSelectIndex(0);
		} else {
			ele.setImageSelectIndex(ele.getImageSelectIndex() + 1);
		}
	}

	/**
	 * Muestra la imagen anterior en el applet
	 */
	public static void previous(final IdocScanInterface ui) {
		FileVO ele = (FileVO) ui.getFileVO();
		if (ele.getImageSelectIndex() - 1 < 0) {
			ele.setImageSelectIndex(ele.getListImage().size() - 1);
		} else {
			ele.setImageSelectIndex(ele.getImageSelectIndex() - 1);
		}
	}

	/**
	 * Muestra la pagina anterior en el applet
	 */
	public static void previousImage(final IdocScanInterface ui) {
		ElementVO ele = (ElementVO) ui.getFileVO().getListImage().get(
				ui.getFileVO().getImageSelectIndex());
		if (ele.getImageSelectIndex() - 1 < 0) {
			ele.setImageSelectIndex(ele.getNumPages() - 1);
		} else {
			ele.setImageSelectIndex(ele.getImageSelectIndex() - 1);
		}
	}

	/**
	 * Ajusta la imagen al tamaï¿½o del marco
	 */
	public static void scale(final IdocScanInterface ui) {
		loadImage(ui,0,ui.getJScrollPaneIcon().getHeight()-20);
//		FileVO file = (FileVO) ui.getFileVO();
//		ImageVO imageVO = (ImageVO) file.getListImage().get(
//				file.getImageSelectIndex());
//
//		ImageIcon icono = getIcon(imageVO.getImage());
//		Image img = icono.getImage();
//
//		int w = ui.getJScrollPaneIcon().getWidth();
//		int h = ui.getJScrollPaneIcon().getHeight();
//		if (w > h) {
//			w = h;
//		} else {
//			h = w;
//		}
//
//		float alto = icono.getIconHeight();
//		float ancho = icono.getIconWidth();
//		float nuevo_ancho = w - 20;
//		float nuevo_alto = h - 20;
//		float proporcion = ancho / alto; 
//		nuevo_ancho = nuevo_alto * proporcion;
//		
//		img = img.getScaledInstance((int)nuevo_ancho, (int)nuevo_alto, java.awt.Image.SCALE_SMOOTH);
//		//img = img.getScaledInstance(w - 20, h - 20, java.awt.Image.SCALE_SMOOTH);
//
//		ImageIcon icon = new ImageIcon(img);
//		JLabel jl = new JLabel(icon);
//		jl.setBounds(1, 1, icon.getIconWidth() - 1, icon.getIconHeight() - 1);
//		// ui.getJScrollPaneIcon().setViewportView(jl);
//
//		try {
//			BufferedImage buffer = createBufferedImage(img);
//			ui.setCrop(new Cropping(buffer));
//			// ClipMover mover = new ClipMover(ui.getCrop());
//			// ui.getCrop().addMouseListener(mover);
//			// ui.getCrop().addMouseMotionListener(mover);
//		} catch (Exception e) {
//		}
//		ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//		ui.repaint();
//
//		imageVO.setWidth(icon.getIconWidth());
//		imageVO.setHeight(icon.getIconHeight());
	}

	/**
	 * Ajusta la imagen al tamaï¿½o del marco de forma horizontal
	 */
	public static void scaleHorizontal(final IdocScanInterface ui) {
		loadImage(ui,ui.getJScrollPaneIcon().getWidth()-30,0);
//		FileVO file = (FileVO) ui.getFileVO();
//		ImageVO imageVO = (ImageVO) file.getListImage().get(
//				file.getImageSelectIndex());
//
//		ImageIcon icono = getIcon(imageVO.getImage());
//		Image img = icono.getImage();
//
//		int w = ui.getJScrollPaneIcon().getWidth();
//		int h = ui.getJScrollPaneIcon().getHeight();
//
//		//int tam;
//		if (imageVO.getWidth() > w) {
//			h = imageVO.getHeight() - (imageVO.getWidth() - w);
//		} else {
//			h = imageVO.getHeight() + (w - imageVO.getWidth());
//		}
//
//		img = img
//				.getScaledInstance(w - 30, h - 30, java.awt.Image.SCALE_SMOOTH);
//
//		ImageIcon icon = new ImageIcon(img);
//		JLabel jl = new JLabel(icon);
//		jl.setBounds(1, 1, icon.getIconWidth() - 1, icon.getIconHeight() - 1);
//		// ui.getJScrollPaneIcon().setViewportView(jl);
//
//		try {
//			BufferedImage buffer = createBufferedImage(img);
//			ui.setCrop( new Cropping(buffer));
//			// ClipMover mover = new ClipMover(ui.getCrop());
//			// ui.getCrop().addMouseListener(mover);
//			// ui.getCrop().addMouseMotionListener(mover);
//		} catch (Exception e) {
//		}
//		ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//		ui.repaint();
//
//		imageVO.setWidth(icon.getIconWidth());
//		imageVO.setHeight(icon.getIconHeight());
	}

	/**
	 * Ajusta la imagen al tamaï¿½o del marco de forma Vertical
	 */
	public static void scaleVertical(final IdocScanInterface ui) {
		loadImage(ui,0,ui.getJScrollPaneIcon().getHeight()-30);
//		FileVO file = (FileVO) ui.getFileVO();
//		ImageVO imageVO = (ImageVO) file.getListImage().get(
//				file.getImageSelectIndex());
//
//		ImageIcon icono = getIcon(imageVO.getImage());
//		Image img = icono.getImage();
//
//		int w = ui.getJScrollPaneIcon().getWidth();
//		int h = ui.getJScrollPaneIcon().getHeight();
//
//		//int tam;
//
//		if (imageVO.getHeight() > h) {
//			w = imageVO.getWidth() - (imageVO.getHeight() - h);
//		} else {
//			w = imageVO.getWidth() + (h - imageVO.getHeight());
//		}
//
//		img = img.getScaledInstance(w - 30, h - 30, java.awt.Image.SCALE_SMOOTH);
//
//		ImageIcon icon = new ImageIcon(img);
//		JLabel jl = new JLabel(icon);
//		jl.setBounds(1, 1, icon.getIconWidth() - 1, icon.getIconHeight() - 1);
//		// ui.getJScrollPaneIcon().setViewportView(jl);
//
//		try {
//			BufferedImage buffer = createBufferedImage(img);
//			ui.setCrop(new Cropping(buffer));
//			// ClipMover mover = new ClipMover(ui.getCrop());
//			// ui.getCrop().addMouseListener(mover);
//			// ui.getCrop().addMouseMotionListener(mover);
//		} catch (Exception e) {
//		}
//		ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//		ui.repaint();
//
//		imageVO.setWidth(icon.getIconWidth());
//		imageVO.setHeight(icon.getIconHeight());
	}

	/*
	 * escala una imagen en porcentaje. @param scale ejemplo: scale=0.6 (escala
	 * la imï¿½gen al 60%) @param srcImg una imagen BufferedImage @return un
	 * BufferedImage escalado
	 */
//	public static ImageIcon scale(double scale, ImageIcon icono) {
//		BufferedImage srcImg = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.SCALE_SMOOTH);
//		srcImg.getGraphics().drawImage(icono.getImage(), 0, 0, null);
//
//		if (scale == 1) {
//			return new ImageIcon(srcImg);
//		}
//		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null);
//		return new ImageIcon(op.filter(srcImg, null));
//
//	}

	/**
	 * Aumento de zoom de la imagen mostrada
	 */
	public static void inZoom(final IdocScanInterface ui) {
		loadImageZoom(ui,0.1f);
//		FileVO file = (FileVO) ui.getFileVO();
//		ImageVO imageVO = (ImageVO) file.getListImage().get(file.getImageSelectIndex());
//
//		ImageIcon icono = getIcon(imageVO.getImage());
//		Image img = icono.getImage();
//
//		int w = imageVO.getWidth();
//		int h = imageVO.getHeight();
//
//		int wZoom = (int) (w * 0.1);
//		int hZoom1 = (int) (h * 0.1);
//
//		img = img.getScaledInstance(w + wZoom, h + hZoom1, java.awt.Image.SCALE_SMOOTH);
//
//		ImageIcon icon = new ImageIcon(img);
//		JLabel jl = new JLabel(icon);
//		jl.setBounds(1, 1, icon.getIconWidth() - 1, icon.getIconHeight() - 1);
//		// ui.getJScrollPaneIcon().setViewportView(jl);
//
//		try {
//			BufferedImage buffer = createBufferedImage(img);
//			ui.setCrop( new Cropping(buffer));
//			// ClipMover mover = new ClipMover(ui.getCrop());
//			// ui.getCrop().addMouseListener(mover);
//			// ui.getCrop().addMouseMotionListener(mover);
//		} catch (Exception e) {
//		}
//
//		ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//		ui.repaint();
//
//		imageVO.setWidth(icon.getIconWidth());
//		imageVO.setHeight(icon.getIconHeight());
	}

	/**
	 * Disminuciï¿½n de zoom de la imagen mostrada
	 */
	public static void outZoom(final IdocScanInterface ui) {
		loadImageZoom(ui,-0.1f);
//		FileVO file = (FileVO) ui.getFileVO();
//		ImageVO imageVO = (ImageVO) file.getListImage().get(file.getImageSelectIndex());
//
//		ImageIcon icono = getIcon(imageVO.getImage());
//		Image img = icono.getImage();
//
//		int w = imageVO.getWidth();
//		int h = imageVO.getHeight();
//
//		int wZoom = (int) (w * 0.1);
//		int hZoom1 = (int) (h * 0.1);
//
//		img = img.getScaledInstance(w - wZoom, h - hZoom1, java.awt.Image.SCALE_SMOOTH);
//
//		ImageIcon icon = new ImageIcon(img);
//		JLabel jl = new JLabel(icon);
//		jl.setBounds(1, 1, icon.getIconWidth() - 1, icon.getIconHeight() - 1);
//		// ui.getJScrollPaneIcon().setViewportView(jl);
//
//		try {
//			BufferedImage buffer = createBufferedImage(img);
//			ui.setCrop( new Cropping(buffer));
//			// ClipMover mover = new ClipMover(ui.getCrop());
//			// ui.getCrop().addMouseListener(mover);
//			// ui.getCrop().addMouseMotionListener(mover);
//		} catch (Exception e) {
//		}
//		ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
//		ui.repaint();
//		imageVO.setWidth(icon.getIconWidth());
//		imageVO.setHeight(icon.getIconHeight());
	}

	/**
	 * Rota la imagen 90ï¿½ a la derecha
	 */
	public static void rotate(final IdocScanInterface ui) {
		FileVO file = (FileVO) ui.getFileVO();
		
		
		ImageVO imageVO = (ImageVO) file.getListImage().get(file.getImageSelectIndex());

		Image img=loadImage(imageVO.getImage());
		//ImageIcon icono = getIcon(imageVO.getImage());
		//Image img = icono.getImage();

		//int w = icono.getIconWidth();
		//int h = icono.getIconHeight();
		int w=img.getWidth(null);
		int h=img.getHeight(null);
				
		
		int type = ((BufferedImage)img).getType();
		BufferedImage image = new BufferedImage(h, w, type);
		Graphics2D g2 = image.createGraphics();

		double x = (h - w) / 2.0;
		double y = (w - h) / 2.0;
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);

		at.rotate(Math.toRadians(90), w / 2.0, h / 2.0);

		g2.drawImage(img, at, (ImageObserver)ui);
		g2.dispose();

		try {
			ImageIO.write(image, "bmp", new File(imageVO.getImage()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void crop(String sourceFilename, String destFilename,
			float x, float y, float width, float height)
			throws FileNotFoundException, IOException {
		// Begin by assuming that the output format will be JPEG...
		String outputType = "BMP";
		// ... but look at the destination filename to see if the extension
		// tells us what the format should be
		int i = destFilename.lastIndexOf('.');
		if (i > -1)
			outputType = destFilename.substring(i + 1);

		// Load the original image as a PlanarImage...
		PlanarImage image = JAI.create("fileload", sourceFilename);

		// ... and create parameters for the crop operation
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		// Params are added in x, y, width, height order
		pb.add(x);
		pb.add(y);
		pb.add(width);
		pb.add(height);

		// Now crop into a new PlanarImage
		PlanarImage cropped = JAI.create("crop", pb, null);

		// Now, if it's a JPEG image, write it as a BufferedImage, otherwise
		// as-is:
		if ("bmp".indexOf(outputType.toLowerCase()) > -1) {
			BufferedImage bufferedImage = cropped.getAsBufferedImage();
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(destFilename)));
			ImageIO.write(bufferedImage, "BMP", output);
			output.close();
		} else {
			ImageIO.write(cropped, outputType, new File(destFilename));
		}
	}

	/**
	 * Salva las imagenes en temporal
	 */
	/*
	@SuppressWarnings("unchecked")
	public static String saveTemp(final IdocScanInterface ui, final String extension) {
		String path = (String) AccessController.doPrivileged(new PrivilegedAction() {
				public Object run() {
					//String dir = System.getProperty("user.home");
					String name = "Ieci_" + System.currentTimeMillis();
					// File file = new File(dir + "\\" + "ieci\\" + name +
					// "." + extension);
					File file = null;
					if (extension.equals(KeysUtils.TIFF_Multipage)) {
						file = new File(name + "." + KeysUtils.TIFF);
					} else {
						file = new File(name + "." + extension);
					}
					try {
						file = File.createTempFile(name, "");
						file.deleteOnExit();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// File file = new File(dir + "\\" + "ieci\\" + name +
					// "." + extension);
					FileVO fileVO = ui.getFileVO();
					//OptionsUIVO optionsUI = ui.getOptionsUI();
	
					ImageVO imageVO = (ImageVO) fileVO.getListImage().get(fileVO.getImageSelectIndex());
					PerfilesVO perfiles = ui.getPerfiles();
					PerfilVO perfil = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					if (extension.equals(KeysUtils.TIFF)) {
						FileUtils.saveAsTIFF(file, imageVO, extension,perfil);
					} else if (extension.equals(KeysUtils.TIFF)) {
						FileUtils.saveAsMutipageTIFF(file, fileVO.getListImage(), extension,perfil);
					} else if (extension.equals(KeysUtils.PDF)) {
						FileUtils.saveAsPDF(file, fileVO.getListImage());
					} else if (extension.equals(KeysUtils.JPG)) {
						FileUtils.saveAsJPG(file, imageVO, extension,perfil);
					}
	
					return file.getAbsolutePath();
				}
			});
		return path;
	}*/

	/**
	 * Salva las imagenes en local
	 * @param parametrosVO 
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static Boolean save(final IdocScanInterface ui, final String extension, final ParametrosVO parametrosVO) {
		return (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				Boolean result = Boolean.TRUE;
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				String name = "Ieci_" + System.currentTimeMillis();
				File f;
				if (extension.equals(KeysUtils.TIFF_Multipage)) {
					f = new File(name + "." + KeysUtils.TIFF);
				} else {
					f = new File(name + "." + extension);
				}

				fc.setSelectedFile(f);
				if (fc.showSaveDialog((Component)ui) == JFileChooser.APPROVE_OPTION) {
					File file;
					if (fc.getSelectedFile().getName().contains(".")) {
						file = fc.getSelectedFile();
					} else {
						file = new File(fc.getSelectedFile() + "." + extension);
					}
					FileVO fileVO = ui.getFileVO();
					//OptionsUIVO optionsUI = ui.getOptionsUI();
					PerfilesVO perfiles = ui.getPerfiles();
					PerfilVO perfil = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					//ImageVO imageVO = (ImageVO) fileVO.getListImage().get(fileVO.getImageSelectIndex());
					List<ImageVO> listaImagenes = fileVO.getListImage();
					ImageVO imageVO = null;
					if (extension.equals(KeysUtils.TIFF)) {
						for(int i=0;i<listaImagenes.size();i++)
						{
							imageVO = (ImageVO) fileVO.getListImage().get(i);
							String fileName = file.getAbsolutePath();
							if(i!=0)
							{
								int pointIndex = fileName.lastIndexOf(".");
								String prefix = fileName.substring(0,pointIndex);
								prefix = prefix+"_"+i;
								fileName = prefix+fileName.substring(pointIndex);
								file = new File(fileName);
							}
							
							FileUtils.saveAsTIFF(file, imageVO, extension,perfil);
						}
					} else if (extension.equals(KeysUtils.PDF)) {
						FileUtils.saveAsPDF(file, fileVO.getListImage());
					} else if (extension.equals(KeysUtils.JPG)) {
						for(int i=0;i<listaImagenes.size();i++)
						{
							imageVO = (ImageVO) fileVO.getListImage().get(i);
							String fileName = file.getAbsolutePath();
							if(i!=0)
							{
								int pointIndex = fileName.lastIndexOf(".");
								String prefix = fileName.substring(0,pointIndex);
								prefix = prefix+"_"+i;
								fileName = prefix+fileName.substring(pointIndex);
								file = new File(fileName);
							}
							
							FileUtils.saveAsJPG(file, imageVO, extension,perfil);
						}
						
					} else if (extension.equals(KeysUtils.TIFF_Multipage)) {
						FileUtils.saveAsMutipageTIFF(file, fileVO.getListImage(), extension,perfil);
					}
					if(checkImagenGuardadaCorrectamente(ui,file)){
						//TODO nombre del fichero guardado
						parametrosVO.setFile(file.getPath());
						System.out.println("File: " + parametrosVO.getFile());
					}else{
						result = Boolean.FALSE;
					}
				}
				else
				{
					result = Boolean.FALSE;
				}
				return result;
			}
		});
	}

	/**
	 * Evento que borra la foto escaneada.
	 */
	public static void deletePage(final IdocScanInterface ui) {
		FileVO ele = (FileVO) ui.getFileVO();
		List<ImageVO> listImage=ele.getListImage();
		if(listImage!=null && listImage.size()>0 && ele.getImageSelectIndex()>-1){
			ImageVO imageVO = (ImageVO) listImage.get(ele.getImageSelectIndex());
			listImage.remove(ele.getImageSelectIndex());
		 	ele.setImageSelectIndex(0);
		 	delete(imageVO.getImage());
		}
	}

	public static void clearTemp(final IdocScanInterface ui) {
		PerfilesVO perfiles = ui.getPerfiles();
		File dir = new File(perfiles.getUserHome() + ui.getOptionsUI().getPathTemp());
		String[] ficheros = dir.list();
		for (int x = 0; x < ficheros.length; x++) {
			delete(dir + System.getProperty("file.separator") + ficheros[x]);
		}
	}

	/**
	 * Evento que borra la foto escaneada.
	 */
	private static void delete(String path) {
		File file = new File(path);
		file.delete();
	}

	/**
	 * Evento que borra la foto escaneada.
	 */
	public static void deleteImage(final IdocScanInterface ui) {
		FileVO ele = (FileVO) ui.getFileVO();
		ele.getListImage().remove(ele.getImageSelectIndex());
		ele.setImageSelectIndex(0);
	}

	/**
	 * Evento que borra la foto escaneada.
	 */
	@SuppressWarnings("unchecked")
	public static void deleteALLImage(final IdocScanInterface ui) {
		FileVO ele = (FileVO) ui.getFileVO();
		ele.getListImage().removeAll(ele.getListImage());
		ele.setImageSelectIndex(0);
	}

	//TODO Manda imagen al servidor
	public static void sendFile(final IdocScanInterface ui, final String extension, ParametrosVO parametrosVO) {
		FileVO fileVO = ui.getFileVO();
		OptionsUIVO optionsUI = ui.getOptionsUI();
		File file = null;
		PerfilesVO perfiles = ui.getPerfiles();
		PerfilVO perfil = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
		if ((extension.equals(KeysUtils.JPEG)) || (extension.equals(KeysUtils.JPG))) {
			String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathTemp() + System.getProperty("file.separator");
			String code = "img_" + String.valueOf(System.currentTimeMillis()) + ".jpeg";
			String name = path + code;
			file = new File(name);
			ImageVO imageVO = (ImageVO) fileVO.getListImage().get(fileVO.getImageSelectIndex());
			FileUtils.saveAsJPG(file, imageVO, extension,perfil);
		} else if (extension.equals(KeysUtils.TIFF_Multipage)) {
			String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathTemp() + System.getProperty("file.separator");
			String code = "img_" + String.valueOf(System.currentTimeMillis()) + ".tiff";
			String name = path + code;
			file = new File(name);
			FileUtils.saveAsMutipageTIFF(file, fileVO.getListImage(), extension,perfil);
		} else if (extension.equals(KeysUtils.TIFF)) {
			String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathTemp() + System.getProperty("file.separator");
			String code = "img_" + String.valueOf(System.currentTimeMillis()) + ".tiff";
			String name = path + code;
			file = new File(name);
			ImageVO imageVO = (ImageVO) fileVO.getListImage().get(fileVO.getImageSelectIndex());
			FileUtils.saveAsTIFF(file, imageVO, extension,perfil);
		} else if (extension.equals(KeysUtils.PDF)) {
			String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathTemp() + System.getProperty("file.separator");
			String code = "img_" + String.valueOf(System.currentTimeMillis()) + ".pdf";
			String name = path + code;
			file = new File(name);
			FileUtils.saveAsPDF(file, fileVO.getListImage());
		}
		
		if(checkImagenGuardadaCorrectamente(ui,file)){
			//TODO nombre del fichero guardado
			parametrosVO.setFile(file.getPath());
			System.out.println("File: " + parametrosVO.getFile());
		}else{
			return;
		}
		
		//TODO Nombre del fichero en servidor

		
		//String toservlet = optionsUI.getUrlServlet() + file.getName();
		String code = "img_" + String.valueOf(System.currentTimeMillis())+ ".zip";
		
		String direccionServlet = null;
		if(parametrosVO.getServlet()!=null){
			direccionServlet = parametrosVO.getServlet();
		}else{
			direccionServlet = optionsUI.getUrlServlet();
		}
		
		String toservlet = direccionServlet + System.getProperty("file.separator") +code;
		
		String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathTemp();
		compress(file,path,code);
		File compressFile = new File(path+ System.getProperty("file.separator") +code);
		//sendServlet(compressFile, toservlet);
		upload(compressFile, toservlet);
	}

	@SuppressWarnings("unused")
	private static void sendServlet(File file, String toservlet) {
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			byte[] buf = new byte[in.available()];
			int bytesread = 0;

			// String toservlet =
			// optionsUI.getUrlServlet()+file.getName();//+"."+extension;
			// String toservlet =
			// "http://localhost:8080/SignDocument/servlet/MyServlet?name="+file.getName();//+"."+extension;

			URL servleturl = new URL(toservlet);
			URLConnection servletconnection = servleturl.openConnection();
			servletconnection.setDoInput(true);
			servletconnection.setDoOutput(true);
			servletconnection.setUseCaches(false);
			servletconnection.setDefaultUseCaches(false);

			DataOutputStream out = new DataOutputStream(servletconnection.getOutputStream());

			while ((bytesread = in.read(buf)) > -1) {
				out.write(buf, 0, bytesread);
			}

			out.flush();
			out.close();
			in.close();

			DataInputStream inputFromClient = new DataInputStream(servletconnection.getInputStream());
			// get what you want from servlet
			// .......
			inputFromClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void upload(File file, String toservlet) {
		  HttpURLConnection conn = null;
		  DataOutputStream dos = null;
		  String exsistingFileName = file.getAbsolutePath();
		  String lineEnd = "\r\n";
		  String twoHyphens = "--";
		  String boundary =  "*****";
		  int bytesRead, bytesAvailable, bufferSize;
		  byte[] buffer;
		  int maxBufferSize = 1*1024*1024;
		  String urlString = toservlet;

		  try
		  {// ------------------ CLIENT REQUEST
		   FileInputStream fileInputStream = new FileInputStream(new File(exsistingFileName) );

		   // open a URL connection to the Servlet
		   URL url = new URL(urlString);

		   // Open a HTTP connection to the URL
		   conn = (HttpURLConnection) url.openConnection();

		   // Allow Inputs
		   conn.setDoInput(true);

		   // Allow Outputs
		   conn.setDoOutput(true);

		   // Don't use a cached copy.
		   conn.setUseCaches(false);

		   // Use a post method.
		   conn.setRequestMethod("POST");
		   conn.setRequestProperty("Connection", "Keep-Alive");
		   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

		   dos = new DataOutputStream( conn.getOutputStream() );
		   dos.writeBytes(twoHyphens + boundary + lineEnd);
		   dos.writeBytes("Content-Disposition: form-data; name=\"upload\";" + " filename=\"" + exsistingFileName +"\"" + lineEnd);
		   dos.writeBytes(lineEnd);

		   // create a buffer of maximum size
		   bytesAvailable = fileInputStream.available();
		   bufferSize = Math.min(bytesAvailable, maxBufferSize);
		   buffer = new byte[bufferSize];

		   // read file and write it into form...
		   bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		   while (bytesRead > 0)
		   {
		    dos.write(buffer, 0, bufferSize);
		    bytesAvailable = fileInputStream.available();
		    bufferSize = Math.min(bytesAvailable, maxBufferSize);
		    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		   }

		   // send multipart form data necesssary after file data...
		   dos.writeBytes(lineEnd);
		   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

		   // close streams
		   fileInputStream.close();
		   dos.flush();
		   dos.close();
		  }
		  catch (MalformedURLException ex)
		  {
			  mensaje("Información", "Fallo en el envío");
		   System.out.println("From ServletCom CLIENT REQUEST:"+ex);
		  }

		  catch (IOException ioe)
		  {
			  mensaje("Información", "Fallo en el envío");
		   System.out.println("From ServletCom CLIENT REQUEST:"+ioe);
		  }
		  
		  // ------------------ read the SERVER RESPONSE
		  try {
		   //inStream = new DataInputStream ( conn.getInputStream() );
		   BufferedReader inStream = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		   String str;
		   while (( str = inStream.readLine()) != null)
		   {
		    System.out.println("Server response is: "+str);
		    System.out.println("");
		   }
		   inStream.close();
		   mensaje("Información", "Documento enviado correctamente");
		   
		  }
		  catch (IOException ioex)
		  {
			  mensaje("Información", "Fallo en el envío");
		   System.out.println("From (ServerResponse): "+ioex);
		  }		  
	}
	
	private static void compress(File file, String path, String code) {
		try {
			int BUFFER = 2048;
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(path + System.getProperty("file.separator") + code);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

			byte data[] = new byte[BUFFER];

			//System.out.println("Adding: " + file.getName());
			FileInputStream fi = new FileInputStream(file);
			origin = new BufferedInputStream(fi, BUFFER);
			ZipEntry entry = new ZipEntry(file.getName());
			out.putNextEntry(entry);
			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			
			origin.close();
			out.close();
		} catch (Exception e) {
		}
	}


	@SuppressWarnings("unused")
	private static void compress2(File file, String path, String code) {
		try {
			int BUFFER = 2048;
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(path + System.getProperty("file.separator") + code);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			// out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];
			// obtenemos la lista de los archivos del directorio actual
			File f = new File("C:\\sample");
			String files[] = f.list();
			for (int i = 0; i < files.length; ++i) {
				System.out.println("Adding: " + file.getName());
				//File f1 = new File("C:\\css-cheat-sheet-v2.png");
				FileInputStream fi = new FileInputStream(file);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(file.getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Metodo que lanza un mensaje de informaciï¿½n por pantalla
	 * 
	 * @param titulo
	 * @param mensaje
	 */
	private static void mensaje(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, Messages.getString(mensaje), Messages.getString(titulo), JOptionPane.INFORMATION_MESSAGE);
	}
	
	@SuppressWarnings({"unchecked"})
	public static void convertImages(IdocScanInterface ui, String extension,
			ParametrosVO parametrosVO) {
		FileVO fileVO = ui.getFileVO();
		List images=fileVO.getListImage();
		if(images==null || images.size()==0){
			return;
		}
		
		OptionsUIVO optionsUI = ui.getOptionsUI();
		File file = null;
		PerfilesVO perfiles = ui.getPerfiles();
		PerfilVO perfil = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
		if ((extension.equals(KeysUtils.JPEG)) || (extension.equals(KeysUtils.JPG))) {
			for(int i=0;i<fileVO.getListImage().size();i++)
			{
				ImageVO imageVO = (ImageVO) fileVO.getListImage().get(i);
				String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathConvert() + System.getProperty("file.separator");
				String fileName = "img_" + String.valueOf(System.currentTimeMillis());
				String fileNameWithExtension = fileName + ".jpeg";
				String name = path + fileNameWithExtension;
				file = new File(name);
				
				FileUtils.saveAsJPG(file, imageVO, extension,perfil);
				fileVO.getListImage().remove(i);
				if(checkImagenGuardadaCorrectamente(ui,file,900)){
					imageVO.setExtension("jpeg");
					imageVO.setPath(path);
					imageVO.setName(fileName);
					fileVO.getListImage().add(i,imageVO);
				}
				
			}
		} else if (extension.equals(KeysUtils.TIFF_Multipage)) {
			String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathConvert() + System.getProperty("file.separator");
			String fileName = "img_" + String.valueOf(System.currentTimeMillis());
			String fileNameWithExtension = fileName+".tiff";
			String name = path + fileNameWithExtension;
			file = new File(name);
			FileUtils.saveAsMutipageTIFF(file, fileVO.getListImage(), extension,perfil);
			fileVO.getListImage().clear();
			if(checkImagenGuardadaCorrectamente(ui,file,900)){
				ImageVO imageFinal = new ImageVO();
				imageFinal.setExtension("tiff");
				imageFinal.setPath(path);
				imageFinal.setName(fileName);
				fileVO.getListImage().add(imageFinal);
			}
		} else if (extension.equals(KeysUtils.TIFF)) {
			for(int i=0;i<fileVO.getListImage().size();i++){
				String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathConvert() + System.getProperty("file.separator");
				String fileName = "img_" + String.valueOf(System.currentTimeMillis());
				String fileNameWithExtension = fileName + ".tiff";
				String name = path + fileNameWithExtension;
				file = new File(name);
				ImageVO imageVO = (ImageVO) fileVO.getListImage().get(i);
				FileUtils.saveAsTIFF(file, imageVO, extension,perfil);
				fileVO.getListImage().remove(i);
				if(checkImagenGuardadaCorrectamente(ui,file)){
					imageVO.setExtension("tiff");
					imageVO.setPath(path);
					imageVO.setName(fileName);
					fileVO.getListImage().add(i,imageVO);
				}
			}
		} else if (extension.equals(KeysUtils.PDF)) {
			String path = ui.getPerfiles().getUserHome() + System.getProperty("file.separator")	+ optionsUI.getPathConvert() + System.getProperty("file.separator");
			String fileName = "img_" + String.valueOf(System.currentTimeMillis());
			String fileNameWithExtension = fileName+".pdf";
			String name = path + fileNameWithExtension;
			file = new File(name);
			FileUtils.saveAsPDF(file, fileVO.getListImage());
			fileVO.getListImage().clear();
			if(checkImagenGuardadaCorrectamente(ui,file)){
				ImageVO imageFinal = new ImageVO();
				imageFinal.setExtension("pdf");
				imageFinal.setPath(path);
				imageFinal.setName(fileName);
				fileVO.getListImage().add(imageFinal);
			}
		}		
	}
	
	/**
	 * Muestra la imagen seleccionada de la lista en el visor del applet a
	 * escala real
	 * 
	 * @param ui
	 */
	private static BufferedImage loadImage(final IdocScanInterface ui,int finalWidth,int finalHeight) {
		return loadImage(ui,finalWidth,finalHeight,0);
		
	}
	
	private static BufferedImage loadImageZoom(final IdocScanInterface ui,float scaleFactor) {
		return loadImage(ui,0,0,scaleFactor);
	}
	
	@SuppressWarnings("rawtypes")
	private static BufferedImage loadImage(final IdocScanInterface ui,int finalWidth,int finalHeight,float scaleFactor) {
		FileVO fileVO = (FileVO) ui.getFileVO();
		if (fileVO.getListImage().size() > 0) {
			int imageSelect = fileVO.getImageSelectIndex() + 1;
			ui.getJLabelPage().setText("Pagina " + imageSelect + " de "	+ fileVO.getListImage().size());
			ui.getJLabelPage().repaint();
			
			List lista = fileVO.getListImage();
			int index = fileVO.getImageSelectIndex();
			ImageVO imageVO = (ImageVO) lista.get(index);
			String pathImg = imageVO.getImage();
			BufferedImage image=null;
			Image img = loadImage(pathImg);
			
			if(img==null){
				showMessage(ui,Messages.getString(MessageKeys.ERROR_CARGA_IMAGEN_ESCANEADA,
						new Object[]{pathImg}));
			}else{
				int imgWidth=img.getWidth(null);
				int imgHeight=img.getHeight(null);
				if(finalWidth==0 && finalHeight==0){
					finalWidth=imgWidth;
					finalHeight=imgHeight;
				}else{
					
					if(finalWidth==0){
						float proporcion = 1.0f*imgWidth / imgHeight; 
						finalWidth = (int)(finalHeight * proporcion);
					}else{
						if(finalHeight==0){
							float proporcion = 1.0f*imgHeight / imgWidth; 
							finalHeight = (int)(finalWidth * proporcion);
						}
					}
				}
				
				if(scaleFactor!=0){
					finalWidth=imageVO.getWidth()+(int)(imageVO.getWidth()*scaleFactor);
					finalHeight=imageVO.getHeight()+(int)(imageVO.getHeight()*scaleFactor);
				}
				
				int imageType=((BufferedImage)img).getType();
				img = img.getScaledInstance(finalWidth, finalHeight, java.awt.Image.SCALE_SMOOTH);
				image=createBufferedImage(img,imageType);
				
				if(image==null){
					showMessage(ui,Messages.getString(MessageKeys.ERROR_SIN_RECURSOS));
				}else{
					ui.setCrop(new Cropping(image));
					ui.getJScrollPaneIcon().setViewportView(ui.getCrop());
					ui.repaint();
					imageVO.setWidth(finalWidth);
					imageVO.setHeight(finalHeight);
				}
			}
			
			return null;
		} else {
			JLabel jl = new JLabel();
			ui.getJLabelPage().setText("");
			ui.getJScrollPaneIcon().setViewportView(jl);
			ui.repaint();
			return null;
		}
	}
	
	private static boolean checkImagenGuardadaCorrectamente(IdocScanInterface ui, File image){
		return checkImagenGuardadaCorrectamente(ui, image,0);
	}
	
	private static boolean checkImagenGuardadaCorrectamente(IdocScanInterface ui, File image,int noSize){
		if(!FileUtils.existsfile(image,noSize)){
			showMessage(ui,Messages.getString(MessageKeys.ERROR_SIN_RECURSOS));
			return false;
		}
		return true;
	}
	
	private static void showMessage(IdocScanInterface ui,String mensaje){
		showMessage(ui,Messages.getString("applet.error.title"),mensaje);
	}
	
	private static void showMessage(IdocScanInterface ui,String titulo, String mensaje){
			JOptionPane.showMessageDialog( (JFrame)ui,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
	}
	
	private static BufferedImage loadImage(String imagePath){
		BufferedImage img=null;
		try{
			img=ImageIO.read(new File(imagePath));
		}catch(Throwable e){
			System.out.println("No se pudo cargar la imagen: "+imagePath+". "+e.getMessage());
			e.printStackTrace();
		}
		return img;
	}
}
