package es.ieci.tecdoc.fwktd.applets.scan.applet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import es.ieci.tecdoc.fwktd.applets.scan.actions.ActionScan;
import es.ieci.tecdoc.fwktd.applets.scan.actions.ActionsImagesGeneric;
import es.ieci.tecdoc.fwktd.applets.scan.key.IconsKeys;
import es.ieci.tecdoc.fwktd.applets.scan.key.KeysUtils;
import es.ieci.tecdoc.fwktd.applets.scan.key.MessageKeys;
import es.ieci.tecdoc.fwktd.applets.scan.ui.FormatExtApplet;
import es.ieci.tecdoc.fwktd.applets.scan.ui.InformationApplet;
import es.ieci.tecdoc.fwktd.applets.scan.ui.PerfilesApplet;
import es.ieci.tecdoc.fwktd.applets.scan.ui.SelectDeviceApplet;
import es.ieci.tecdoc.fwktd.applets.scan.utils.Cropping;
import es.ieci.tecdoc.fwktd.applets.scan.utils.FileUtils;
import es.ieci.tecdoc.fwktd.applets.scan.vo.FileVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ImageVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.OptionsUIVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ParametrosVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;

/**
 * @author angel_castro@ieci.es - 22/02/2011
 */
public class IdocApplet extends JApplet implements ActionListener, IdocScanInterface{
	private static final long serialVersionUID = 1L;

	//Set Look & Feel
	{
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	String tipoDoc=null;
	String resolucion=null;
	String tamano=null;
	String color=null;
	String dummy=null;
	String servlet=null;


	private JLabel jLabelPage;
	private JTextField jTextFuente;

	private JButton jButtonScan;
	private JButton jButtonNext;
	private JButton jButtonPrevious;
	private JButton jButtonScale;
	private JButton jButtonScaleHorizontal;
	private JButton jButtonScaleVertical;
	private JButton jButtonRote;
	private JButton jButtonZoom;
	private JButton jButtonOutZoom;
	private JButton jButtonDelete;
	private JButton jButtonOriginal;
	private JButton jButtonSalir;
	private JButton jButtonCrop;
	private JButton jButtonOK;
	private JButton jButtonCancel;

	private JMenuBar jMenuBarScan;
	private JMenu jMenuOpen;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemOpenFile;
	private JMenuItem menuItemNewFile;
	private JMenuItem menuItemAddFile;

	private JMenu jMenuImage;
	private JMenuItem menuItemInZoom;
	private JMenuItem menuItemOutZoom;
	private JMenuItem menuItemScale;
	private JMenuItem menuItemOriginal;
	private JMenuItem menuItemRotate;
	private JMenuItem menuItemScaleVertical;
	private JMenuItem menuItemScaleHorizontal;
	private JMenuItem menuItemCrop;
	private JMenuItem menuItemExit;

	private JPanel jPanelBackground;
	private JToolBar jToolBarImages;
	private JScrollPane jScrollPaneIcon;

	private JMenu jMenuHelp;
	private JMenuItem menuItemAcercaDe;


	private JMenu jMenuConfig;
	private JMenuItem menuItemProfile;
	private JMenuItem menuItemSource;

	private JMenu jMenuCapture;
	private JMenuItem menuItemScan;
	private JMenuItem menuItemScanReplace;
	private JMenuItem menuItemScanAdd;
	private JMenuItem menuItemScanFinish;
	private JMenuItem menuItemScanDelete;

	private JSeparator jSeparator;

	private FileVO fileVO;
	private OptionsUIVO optionsUI;
	private PerfilesVO perfiles;
	private Properties messages;
	private ParametrosVO parametrosVO;

	public Cropping crop = null;


	@Override
	public void init() {
		System.out.println("Init Applet");
		//TIPO PDF
		tipoDoc = getParameter("tipoDoc");

		//200.0
		resolucion = getParameter("resolucion");

		//A4 -> 1 // US LETTER -> 3 //A3 -> 11
		tamano = getParameter("tamano");

		//Blanco Negro -> 0 // Escala Grises -> 1 // Color ->2
		color = getParameter("color");

		//Comandos habilitados -> 0 // Comandos deshabilitados -> 1
		dummy = getParameter("dummy");

		//Comandos habilitados -> 0 // Comandos deshabilitados -> 1
		servlet = getParameter("servlet");
	}

	public void start() {
		System.out.println("Start Applet");
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
            		parametrosVO = new ParametrosVO();
            		if(tipoDoc != null){
            			parametrosVO.setTipoDoc(tipoDoc);
            		}
            		if(resolucion != null){
            			parametrosVO.setResolucion(resolucion);
            		}
            		if(tamano != null){
            			parametrosVO.setTamano(tamano);
            		}
            		if(color != null){
            			parametrosVO.setColor(color);
            		}
            		if(dummy != null){
            			parametrosVO.setDummy(dummy);
            		}
            		if(servlet != null){
            			parametrosVO.setServlet(servlet);
            		}
            		initGUI();
            		System.out.println("Tipo Documento: " + tipoDoc);
            		System.out.println("Resolucion: " + resolucion);
            		System.out.println("Tamano: " + tamano);
            		System.out.println("Color: " + color);
            		System.out.println("Dummy: " + dummy);
            		System.out.println("Servlet: " + servlet);
                }
            });
        } catch (Exception e) {
            System.err.println("Error inicializando applet");
        }
	}

	public void stop(){
		System.out.println("Saliendo Applet");
		ActionScan.close();
		ActionsImagesGeneric.clearTemp(IdocApplet.this);
	}


	private void initGUI() {
		try {
			// Opciones del applet
			optionsUI = new OptionsUIVO();

			perfiles = new PerfilesVO();

			String userhome = System.getProperty("user.home");
			System.out.println("User.home = "+userhome);
			if(userhome.startsWith("%") || userhome.startsWith("$"))
			{
				userhome = System.getenv(userhome.substring(1, userhome.length()-1));
				System.out.println("Leida variable de entorno "+userhome);
			}
			perfiles.setUserHome(userhome);

			String xml = null;
			try{
				xml = FileUtils.readPerfiles(perfiles);
			}catch (Exception e) {
				System.out.println("No existe el fichero de perfiles");
			}

			if(xml!=null){
				perfiles = perfiles.fromXml(xml);
			}else{
				//TODO Exigir perfil
			}


			String pathFile = perfiles.getUserHome();
			File file = new File(pathFile + optionsUI.getPathTemp());
			if(!(file.exists())){
				file.mkdir();
				System.out.println("Creado fichero: " + file.getPath());
			}
			File convertDir = new File(pathFile + optionsUI.getPathConvert());
			if(!(convertDir.exists())){
				convertDir.mkdir();
				System.out.println("Creado directorio: " + convertDir.getPath());
			}
			// Se limpian los residuos de la carpeta temporal
			ActionsImagesGeneric.clearTemp(IdocApplet.this);

			System.out.println("Inicializando Scanner");
			ActionScan.init();
			chargeLocale();
			fileVO = new FileVO();

			setLayout(null);
			//setSize(new Dimension(500,500));
			//setPreferredSize(new Dimension(500,500));
			setMinimumSize(new Dimension(500,500));
			//ImageIcon logo = new ImageIcon(this.getClass().getClassLoader().getResource(IconsKeys.LOGO));
			//setTitle("IdocScan - Herramienta de Digitalizaciï¿½n");

			//setIconImage(logo.getImage());
			//setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanelBackground = new JPanel();
				getContentPane().add(getJPanelBackground(), BorderLayout.CENTER);
				jPanelBackground.setLayout(null);
				jPanelBackground.setSize(new Dimension(getWidth(),getHeight()));
				jPanelBackground.setMinimumSize(new Dimension(500,500));
				{
					jToolBarImages = new JToolBar();
					jPanelBackground.add(jToolBarImages);
					jToolBarImages.setBounds(0, 0, getWidth(), 30);
					jToolBarImages.setFloatable(false);
					toolBar();
				}
				{
					jScrollPaneIcon = new JScrollPane();
					jPanelBackground.add(jScrollPaneIcon);
					jScrollPaneIcon.setBounds(0, 30, getWidth(), getHeight()-100);
					jScrollPaneIcon.setBorder(null);
				}
				{
					jTextFuente = new JTextField();
					jPanelBackground.add(jTextFuente);
					//jTextFuente.setPreferredSize(new Dimension(500,20));
					jTextFuente.setBounds(5,getHeight()-60, getWidth()-10, 30);
					jTextFuente.setEditable(false);
					PerfilVO perfil = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					if(perfil!=null){
						jTextFuente.setText(messages.getProperty(MessageKeys.PROFILE_SELECTED) + ": " +
								perfil.getName() + "."  +
								" - " + perfil.getDevice());
					}else{
						String sourceName = ActionScan.getSourceDefautl();
						perfiles.setSourceDefault(sourceName);
						jTextFuente.setText(messages.getProperty(MessageKeys.SOURCE_SELECTED) +
								": " + sourceName);
					}
				}
			}

			{
				jMenuBarScan = new JMenuBar();
				setJMenuBar(jMenuBarScan);
				menu();
			}
			//pack();

			addComponentListener(new ComponentAdapter(){
				public void componentResized(ComponentEvent e){
					super.componentResized(e);
					resizeApplet();
				}
				});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeStateMenus(boolean state){
		getMenuItemScanAdd().setEnabled(state);
		getMenuItemScanDelete().setEnabled(state);
		getMenuItemScanFinish().setEnabled(state);
		getMenuItemScanReplace().setEnabled(state);
		getJButtonNext().setEnabled(state);
		getJButtonNext().setVisible(state);
		getJButtonPrevious().setEnabled(state);
		getJButtonPrevious().setVisible(state);
		getJButtonScale().setEnabled(state);
		getJButtonScaleHorizontal().setEnabled(state);
		getJButtonScaleVertical().setEnabled(state);
		getJButtonRote().setEnabled(state);
		getJButtonZoom().setEnabled(state);
		getJButtonOutZoom().setEnabled(state);
		getJButtonDelete().setEnabled(state);
		getJButtonOriginal().setEnabled(state);
		getMenuItemInZoom().setEnabled(state);
		getMenuItemOutZoom().setEnabled(state);
		getMenuItemScale().setEnabled(state);
		getMenuItemOriginal().setEnabled(state);
		getMenuItemRotate().setEnabled(state);
		getMenuItemScaleVertical().setEnabled(state);
		getMenuItemScaleHorizontal().setEnabled(state);
		getMenuItemSave().setEnabled(state);
		getMenuItemAddFile().setEnabled(state);
		getMenuItemCrop().setEnabled(state);
		getJButtonCrop().setEnabled(state);
	}

	private void resizeApplet(){
		int w = getWidth();
		int h = getHeight();

		setMinimumSize(new Dimension(500,500));
		jPanelBackground.setSize(new Dimension(w,h));
		jPanelBackground.setMinimumSize(new Dimension(500,500));
		jScrollPaneIcon.setBounds(0, 30, w, h-100);
		jToolBarImages.setBounds(0, 0, w, 30);
		jTextFuente.setBounds(5,h-60, w-10, 30);

		setVisible(true);
	}


	public void actionPerformed(ActionEvent evt) {

		if(crop!=null){
			crop.showClip=false;
			getJButtonCancel().setVisible(false);
			getJButtonOK().setVisible(false);
		}
	// Scanner
		// Capturar
		if((evt.getSource().equals(getMenuItemScan()))||(evt.getSource().equals(getJButtonScan()))){
			this.acquire();
		}// Capturar nueva pagina
		else if(evt.getSource().equals(getMenuItemScanAdd())){
			this.acquireNew();
		}// Reempleza una imagen escaneada por otra
		else if(evt.getSource().equals(getMenuItemScanReplace())){
			this.acquireReplace();
		}// Finaliza el proceso de escaneo subiendo una imagen al servidor
		else if(evt.getSource().equals(getMenuItemScanFinish())){
			//TODO modificado para que solo guarde en local
			this.acquireFinalize();
			//this.saveFile();
		}

	// Configuracion
		if(evt.getSource().equals(getMenuItemProfile())){
			this.profiles();
		}// Capturar nueva pagina
		else if(evt.getSource().equals(getMenuItemSource())){
			this.sources();
		}

	// Ayuda
		if(evt.getSource().equals(getMenuItemAcercaDe())){
			this.infoApplet();
		}

	// Open
		// Abrir una imagen de local
		if(evt.getSource().equals(getMenuItemOpenFile())){
			this.openFile();
		}// Aï¿½adir una imagen de local
		else if(evt.getSource().equals(getMenuItemAddFile())){
			this.addFile();
		}// Salvar la imagen en local
		else if(evt.getSource().equals(getMenuItemSave())){
			this.saveFile(false);
		}

	// Imagenes
		// Ajusta tamaï¿½o al original
		if((evt.getSource().equals(getMenuItemOriginal()))||(evt.getSource().equals(getJButtonOriginal()))){
			ActionsImagesGeneric.original(this);
		}// Ajustar tamaï¿½o a la pantalla
		else if((evt.getSource().equals(getMenuItemScale()))||(evt.getSource().equals(getJButtonScale()))){
			ActionsImagesGeneric.scale(this);
		}// Ajusta tamaï¿½o al ancho de la pantalla
		else if((evt.getSource().equals(getMenuItemScaleHorizontal()))||(evt.getSource().equals(getJButtonScaleHorizontal()))){
			ActionsImagesGeneric.scaleHorizontal(this);
		}// Ajusta tamaï¿½o al alto de la pantalla
		else if((evt.getSource().equals(getMenuItemScaleVertical()))||(evt.getSource().equals(getJButtonScaleVertical()))){
			ActionsImagesGeneric.scaleVertical(this);
		}// Mas zoom a la imagen
		else if((evt.getSource().equals(getMenuItemInZoom()))||(evt.getSource().equals(getJButtonZoom()))){
			ActionsImagesGeneric.inZoom(this);
		}// Menos zoom a la imagen
		else if((evt.getSource().equals(getMenuItemOutZoom()))||(evt.getSource().equals(getJButtonOutZoom()))){
			ActionsImagesGeneric.outZoom(this);
		}// Rota la imagen 90ï¿½ a la derecha
		else if((evt.getSource().equals(getMenuItemRotate()))||(evt.getSource().equals(getJButtonRote()))){
			ActionsImagesGeneric.rotate(this);
			this.view();
		}// Borra la imagen de la pantalla
		else if((evt.getSource().equals(getMenuItemScanDelete()))||(evt.getSource().equals(getJButtonDelete()))){
			this.delete();
		}// Muestra la siguiente pagina
		else if(evt.getSource().equals(getJButtonNext())){
			ActionsImagesGeneric.next(this);
			this.view();
		}// Muesta la pagina anterior
		else if(evt.getSource().equals(getJButtonPrevious())){
			ActionsImagesGeneric.previous(this);
			this.view();
		}// Crop imagen
		else if((evt.getSource().equals(menuItemCrop))||(evt.getSource().equals(getJButtonCrop()))){
			this.crop();
		}
		else if(evt.getSource().equals(getJButtonOK())){
			this.cropAccept();
		}
		else if(evt.getSource().equals(getJButtonCancel())){
			this.cropCancel();
		}
		// Salir
		else  if(evt.getSource().equals(getMenuItemExit())){
			ActionsImagesGeneric.clearTemp(IdocApplet.this);
			//TODO SALIR
		}
	}

	// Abre una imagen de local
	private void openFile(){
		File fileOrig = ActionsImagesGeneric.openFile(this);
		if(fileOrig != null){
			File fileNew = new File(perfiles.getUserHome()+optionsUI.getPathTemp() + "\\" + fileOrig.getName());
			try {
				FileInputStream in = new FileInputStream(fileOrig);
				FileOutputStream out = new FileOutputStream(fileNew);

				int c;
				while( (c = in.read() ) != -1)
					out.write(c);

				in.close();
				out.close();

				fileVO.createImage(fileNew);
				this.view();
				this.changeStateMenus(true);
				getMenuItemScan().setEnabled(false);
				getMenuItemOpenFile().setEnabled(false);
				getJButtonScan().setEnabled(false);
			} catch(IOException e) {
			}
		}
	}

	// Aï¿½ade una imagen de local
	private void addFile(){
		File fileOrig = ActionsImagesGeneric.openFile(this);
		if(fileOrig != null){
			File fileNew = new File(perfiles.getUserHome()+optionsUI.getPathTemp() + "\\" + fileOrig.getName());
			try {
				FileInputStream in = new FileInputStream(fileOrig);
				FileOutputStream out = new FileOutputStream(fileNew);

				int c;
				while( (c = in.read() ) != -1)
					out.write(c);

				in.close();
				out.close();

				fileVO.addImage(fileNew);
				this.view();
			} catch(IOException e) {
			}
		}
	}

	// Muestra rectangulo para crop de imagen
	private void crop(){
		ActionsImagesGeneric.cropping(this);
		crop.showClip=true;
		getJButtonOK().setVisible(true);
		getJButtonCancel().setVisible(true);
		repaint();
	}

	// Muestra rectangulo para crop de imagen
	private void cropAccept(){
//		ImageVO imageVO = (ImageVO) fileVO.getListImage().get(
//				fileVO.getImageSelectIndex());

//		   int w = crop.clip.width;
//           int h = crop.clip.height;
//           int x0 = (getWidth()  - crop.size.width)/2;
//           int y0 = (getHeight() - crop.size.height)/2;
//           int x = crop.clip.x - x0;
//           int y = crop.clip.y - y0;
//
//		try {
//			ActionsImagesGeneric.crop(imageVO.getImage(), imageVO.getImage(), x,y,w,h);
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
		BufferedImage img = crop.clipImage();
		ImageVO imageVO = (ImageVO) fileVO.getListImage().get(
				fileVO.getImageSelectIndex());
		File file = new File(imageVO.getImage());
		try {
			ImageIO.write(img, "bmp", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getJButtonOK().setVisible(false);
		getJButtonCancel().setVisible(false);
		crop = null;
		this.view();
	}

	// Muestra rectangulo para crop de imagen
	private void cropCancel(){
		crop.showClip=false;
		getJButtonOK().setVisible(false);
		getJButtonCancel().setVisible(false);
		crop = null;
		repaint();
	}

	// Salva una imagen en local
	private void saveFile(final boolean resetUI){
		PerfilVO perfilVO = (PerfilVO)perfiles.getHashPerfiles().get(perfiles.getSelectName());
		if((perfiles.getSelectName()== null || perfiles.getHashPerfiles().size()==0) ||
				(perfilVO!=null && perfilVO.isEnableUI())){
			final FormatExtApplet app = new FormatExtApplet(true,messages);
			app.pack();
			app.setTitle(messages.getProperty(MessageKeys.FORMAT));
			app.setLocationRelativeTo(null);
			app.setResizable(false);
			app.setVisible(true);

			app.addWindowListener(new WindowAdapter() {

				@Override
				public void windowDeactivated(WindowEvent e) {
					//Se pulsó o Aceptar, o Cancelar, o la X

					//Si no se pulsó aceptar
					if(!app.isAccept())
					{
						app.dispose();
					}
					else
					{
						//Si se pulsó aceptar

						String extension = "";
						extension = app.getJComboBoxPaper().getSelectedItem().toString();
						Boolean seGuardo =ActionsImagesGeneric.save(IdocApplet.this, extension, parametrosVO);
						if(seGuardo.booleanValue() && resetUI)
						{
							//Si se guardó realmente y hay que resetar UI

							changeStateMenus(false);
							getMenuItemScan().setEnabled(true);
							jButtonScan.setEnabled(true);
							ActionsImagesGeneric.deleteALLImage(IdocApplet.this);
							ActionsImagesGeneric.clearTemp(IdocApplet.this);
							view();
						}
					}

					app.removeWindowListener(this);
				}

			});
		}else{

			Boolean seGuardo = ActionsImagesGeneric.save(this, perfilVO.getFormat(), parametrosVO);
			if(seGuardo.booleanValue() && resetUI)
			{
				//Si se guardó realmente y hay que resetar UI
				changeStateMenus(false);
				getMenuItemScan().setEnabled(true);
				jButtonScan.setEnabled(true);
				ActionsImagesGeneric.deleteALLImage(IdocApplet.this);
				ActionsImagesGeneric.clearTemp(IdocApplet.this);
				view();
			}
		}
	}

	// Applet que muestra el listado de perfiles
	private void profiles(){
		final PerfilesApplet app = new PerfilesApplet(true,perfiles, messages, parametrosVO);
		app.setTitle(messages.getProperty(MessageKeys.CONFIG) + " > " + messages.getProperty(MessageKeys.PROFILE));
		app.pack();
		app.setLocationRelativeTo(null);
		app.setResizable(false);
		app.setVisible(true);
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(perfiles.getHashPerfiles().size() == 0){
					perfiles.setSelectName(null);
					String source = ActionScan.getSourceDefautl();
					jTextFuente.setText(messages.getProperty(MessageKeys.SOURCE_SELECTED) + ": " + source);
				}else{
					PerfilVO perfil = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					jTextFuente.setText(messages.getProperty(MessageKeys.PROFILE_SELECTED) + ": " + perfil.getName() + "."  +  " - " + perfil.getDevice());
				}
			}
			public void windowClosed(WindowEvent e) {
				if(perfiles.getHashPerfiles().size() == 0){
					perfiles.setSelectName(null);
					String source = ActionScan.getSourceDefautl();
					jTextFuente.setText(messages.getProperty(MessageKeys.SOURCE_SELECTED) + ": " + source);
				}else{
					PerfilVO perfil = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					jTextFuente.setText(messages.getProperty(MessageKeys.PROFILE_SELECTED) + ": " +	perfil.getName() + "."  + " - " + perfil.getDevice());
				}
			}
		});
	}

	// Applet de auda que muestra Acerca de
	private void infoApplet(){
		final InformationApplet app = new InformationApplet(true, messages);
		app.pack();
		app.setTitle(messages.getProperty(MessageKeys.HELP) + " > " + messages.getProperty(MessageKeys.ACERCA));
		app.setLocationRelativeTo(null);
		//app.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SUPPORT)).getImage());
		app.setResizable(false);
		app.setVisible(true);
	}

	// Applet que muestra el listado de fuentes
	private void sources(){
		final SelectDeviceApplet app = new SelectDeviceApplet(true);
		app.pack();
		app.setLocationRelativeTo(null);
		app.setResizable(false);
		app.setTitle(messages.getProperty(MessageKeys.CONFIG) + " > " + messages.getProperty(MessageKeys.SOURCE));
		app.setVisible(true);
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
				String sourceName = (String) app.getJComboBoxSource().getSelectedItem();
				if(sourceName!=null)
				{
					perfiles.setSourceDefault(sourceName);
					perfiles.setSelectName(null);
					jTextFuente.setText(messages.getProperty(MessageKeys.SOURCE_SELECTED)+": " + sourceName);
				}
			}
		});
	}

	// Elimina una pagina
	private void delete(){
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.DELETE_IMAGE));

	    Object stringArray[] = { messages.getProperty(MessageKeys.YES), messages.getProperty(MessageKeys.NO) };
	    int i = JOptionPane.showOptionDialog(new JDesktopPane(), messages.getProperty(MessageKeys.DELETE_SHOW), messages.getProperty(MessageKeys.DELETE),
	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, stringArray,
	        stringArray[0]);
	    if(i==0){
	    	ActionsImagesGeneric.deletePage(this);
			this.view();
			if(fileVO.getListImage().size()==0){
				changeStateMenus(false);
				this.menuItemScan.setEnabled(true);
				this.menuItemOpenFile.setEnabled(true);
				this.jButtonScan.setEnabled(true);
			}
	    }
	}

	// Escanea una pagina
	@SuppressWarnings("unchecked")
	private void acquire(){
		int numImages;
		String code = "img_" + String.valueOf(System.currentTimeMillis()) + "_";
		File file = new File(perfiles.getUserHome()+optionsUI.getPathTemp());
		if((perfiles.getSelectName()==null)||perfiles.getHashPerfiles().size()==0){
			numImages =  ActionScan.scanUI(perfiles,code,file.getAbsolutePath(), parametrosVO);
		}else{
			numImages =  ActionScan.scan(perfiles,code,file.getAbsolutePath(), parametrosVO);
		}
		if(numImages!=1){
			List images = this.getFileVO().getListImage();
			if (images == null)
				images = new LinkedList();
			ImageVO imageVO;
			for(int i=1;i<numImages;i++){
				imageVO = new ImageVO();
				imageVO.setName(code+i);
				imageVO.setExtension(KeysUtils.BMP);
				imageVO.setPath(file.getAbsolutePath());
				if((perfiles.getSelectName()==null)||perfiles.getHashPerfiles().size()==0){
					imageVO.setColor("Escala de grises (8 bits)");
				}else{
					PerfilVO perfilVO = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					imageVO.setColor(perfilVO.getColor());
				}
				images.add(imageVO);
			}
			fileVO.setImageSelectIndex(images.size()-1);
			this.view();
			this.changeStateMenus(true);
			getMenuItemScan().setEnabled(false);
			getJButtonScan().setEnabled(false);
			this.menuItemOpenFile.setEnabled(false);
		}
	}

	// Aï¿½ade una nueva pagina desde el escaner
	@SuppressWarnings("unchecked")
	private void acquireNew(){
		int numImages;
		File file = new File(perfiles.getUserHome()+optionsUI.getPathTemp());
		String code = "img_" + String.valueOf(System.currentTimeMillis()) + "_";
		if(perfiles.getSelectName()==null){
			numImages =  ActionScan.scanUI(perfiles,code,file.getAbsolutePath(), parametrosVO);
		}else{
			numImages =  ActionScan.scan(perfiles,code,file.getAbsolutePath(), parametrosVO);
		}
		if(numImages!=1){
			List images = this.getFileVO().getListImage();
			if (images == null)
				images = new LinkedList();
			ImageVO imageVO;
			for(int i=1;i<numImages;i++){
				imageVO = new ImageVO();
				imageVO.setName(code+i);
				imageVO.setExtension(KeysUtils.BMP);
				imageVO.setPath(file.getAbsolutePath());
				if((perfiles.getSelectName()==null)||perfiles.getHashPerfiles().size()==0){
					imageVO.setColor("Escala de grises (8 bits)");
				}else{
					PerfilVO perfilVO = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					imageVO.setColor(perfilVO.getColor());
				}
				images.add(imageVO);
			}
			fileVO.setImageSelectIndex(images.size()-1);

			this.changeStateMenus(true);
			this.view();
		}
	}

	// Reemplaza una pagina escanenada
	@SuppressWarnings("unchecked")
	private void acquireReplace(){
		ActionsImagesGeneric.deletePage(this);
		int numImages;
		String code = "img_" + String.valueOf(System.currentTimeMillis()) + "_";
		File file = new File(perfiles.getUserHome()+optionsUI.getPathTemp());
		if(perfiles.getSelectName()==null){
			numImages =  ActionScan.scanUI(perfiles,code,file.getAbsolutePath(), parametrosVO);
		}else{
			numImages =  ActionScan.scan(perfiles,code,file.getAbsolutePath(), parametrosVO);
		}
		if(numImages!=1){
			List images = this.getFileVO().getListImage();
			if (images == null)
				images = new LinkedList();

			ImageVO imageVO;
			for(int i=1;i<numImages;i++){
				imageVO = new ImageVO();
				imageVO.setName(code+i);
				imageVO.setExtension(KeysUtils.BMP);
				imageVO.setPath(file.getAbsolutePath());
				if((perfiles.getSelectName()==null)||perfiles.getHashPerfiles().size()==0){
					imageVO.setColor("Escala de grises (8 bits)");
				}else{
					PerfilVO perfilVO = (PerfilVO) perfiles.getHashPerfiles().get(perfiles.getSelectName());
					imageVO.setColor(perfilVO.getColor());
				}
				images.add(imageVO);
			}
			fileVO.setImageSelectIndex(images.size()-1);

			this.changeStateMenus(true);
			this.getMenuItemScan().setEnabled(false);
			this.view();
		}
	}

	//TODO Finaliza la captura y envia los datos al servidor
	private void acquireFinalize(){
		//action.save(this);
		if((perfiles.getSelectName()== null)||(perfiles.getHashPerfiles().size()==0)){
			final FormatExtApplet app = new FormatExtApplet(true,messages);
			app.pack();
			app.setLocationRelativeTo(null);
			app.setResizable(false);
			app.setVisible(true);
			app.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
				}
				public void windowClosed(WindowEvent e) {
					String extension = "";
					extension = app.getJComboBoxPaper().getSelectedItem().toString();
					ActionsImagesGeneric.sendFile(IdocApplet.this, extension, parametrosVO);
					ActionsImagesGeneric.deleteALLImage(IdocApplet.this);
					view();
					changeStateMenus(false);
					getMenuItemScan().setEnabled(true);
					jButtonScan.setEnabled(true);
				}
			});
		}
		else{
			PerfilVO perfilVO = (PerfilVO)perfiles.getHashPerfiles().get(perfiles.getSelectName());
			ActionsImagesGeneric.sendFile(IdocApplet.this, perfilVO.getFormat(), parametrosVO);
			ActionsImagesGeneric.deleteALLImage(this);
			this.view();
			changeStateMenus(false);
			this.getMenuItemScan().setEnabled(true);
			this.jButtonScan.setEnabled(true);
		}
	}

	private void view(){
		ActionsImagesGeneric.view(this);
	}

	private void toolBar(){
		{
			jButtonScan = new JButton();
			jToolBarImages.add(getJButtonScan());
			jButtonScan.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SCANNER)));
			jButtonScan.setToolTipText(messages.getProperty(MessageKeys.SCAN));
			jButtonScan.addActionListener(this);
			jButtonScan.setBounds(20, 0, 80, 80);
			jButtonScan.setEnabled(true);
			jButtonScan.setBorderPainted(false);
		}
		{
			jButtonDelete = new JButton();
			jToolBarImages.add(getJButtonDelete());
			jButtonDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.DELETE_IMAGE)));
			jButtonDelete.setToolTipText(messages.getProperty(MessageKeys.DELETE_IMAGE));
			jButtonDelete.addActionListener(this);
			jButtonDelete.setBounds(50, 0, 30, 30);
			jButtonDelete.setEnabled(false);
			jButtonDelete.setBorderPainted(false);
		}
		{
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.BARRA)));
			jToolBarImages.add(label);
			label.setBounds(82, 0, 3, 28);
		}
		{
			jButtonRote = new JButton();
			jToolBarImages.add(getJButtonRote());
			jButtonRote.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ROTATE)));
			jButtonRote.setToolTipText(messages.getProperty(MessageKeys.ROTATE));
			jButtonRote.addActionListener(this);
			jButtonRote.setBounds(90, 0, 30, 30);
			jButtonRote.setEnabled(false);
			jButtonRote.setBorderPainted(false);
		}
		{
			jButtonScale = new JButton();
			jToolBarImages.add(getJButtonScale());
			jButtonScale.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SCALE)));
			jButtonScale.setToolTipText(messages.getProperty(MessageKeys.FIT));
			jButtonScale.addActionListener(this);
			jButtonScale.setBounds(120, 0, 30, 30);
			jButtonScale.setEnabled(false);
			jButtonScale.setBorderPainted(false);
		}
		{
			jButtonScaleHorizontal = new JButton();
			jToolBarImages.add(getJButtonScaleHorizontal());
			jButtonScaleHorizontal.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SCALE_HORIZONTAL)));
			jButtonScaleHorizontal.setToolTipText(messages.getProperty(MessageKeys.FIT_HORIZONTAL));
			jButtonScaleHorizontal.addActionListener(this);
			jButtonScaleHorizontal.setBounds(150, 0, 30, 30);
			jButtonScaleHorizontal.setEnabled(false);
			jButtonScaleHorizontal.setBorderPainted(false);
		}
		{
			jButtonScaleVertical = new JButton();
			jToolBarImages.add(getJButtonScaleVertical());
			jButtonScaleVertical.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SCALE_VERTICAL)));
			jButtonScaleVertical.setToolTipText(messages.getProperty(MessageKeys.FIT_VERTICAL));
			jButtonScaleVertical.addActionListener(this);
			jButtonScaleVertical.setBounds(180, 0, 30, 30);
			jButtonScaleVertical.setEnabled(false);
			jButtonScaleVertical.setBorderPainted(false);
		}
		{
			jButtonOriginal = new JButton();
			jToolBarImages.add(getJButtonOriginal());
			jButtonOriginal.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ORIGINAL)));
			jButtonOriginal.setToolTipText(messages.getProperty(MessageKeys.ORIGINAL));
			jButtonOriginal.addActionListener(this);
			jButtonOriginal.setBounds(210, 0, 30, 30);
			jButtonOriginal.setEnabled(false);
			jButtonOriginal.setBorderPainted(false);
		}
		{
			jButtonZoom = new JButton();
			jToolBarImages.add(getJButtonZoom());
			jButtonZoom.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ZOOM_IN)));
			jButtonZoom.setToolTipText(messages.getProperty(MessageKeys.ZOOM_IN));
			jButtonZoom.addActionListener(this);
			jButtonZoom.setBounds(240, 0, 30, 30);
			jButtonZoom.setEnabled(false);
			jButtonZoom.setBorderPainted(false);
		}
		{
			jButtonOutZoom = new JButton();
			jToolBarImages.add(getJButtonOutZoom());
			jButtonOutZoom.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ZOOM_OUT)));
			jButtonOutZoom.setToolTipText(messages.getProperty(MessageKeys.ZOOM_OUT));
			jButtonOutZoom.addActionListener(this);
			jButtonOutZoom.setBounds(270, 0, 30, 30);
			jButtonOutZoom.setEnabled(false);
			jButtonOutZoom.setBorderPainted(false);
		}
		{
			jButtonCrop = new JButton();
			jToolBarImages.add(getJButtonCrop());
			jButtonCrop.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.EDIT)));
			jButtonCrop.setToolTipText(messages.getProperty(MessageKeys.CROP));
			jButtonCrop.addActionListener(this);
			jButtonCrop.setBounds(270, 0, 30, 30);
			jButtonCrop.setEnabled(false);
			jButtonCrop.setBorderPainted(false);
			jButtonCrop.setVisible(true);
		}
		{
			jButtonOK = new JButton();
			jToolBarImages.add(getJButtonOK());
			jButtonOK.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.OK)));
			jButtonOK.addActionListener(this);
			jButtonOK.setBounds(270, 0, 30, 30);
			jButtonOK.setEnabled(true);
			jButtonOK.setBorderPainted(false);
			jButtonOK.setVisible(false);
		}
		{
			jButtonCancel = new JButton();
			jToolBarImages.add(getJButtonCancel());
			jButtonCancel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.CANCEL)));
			jButtonCancel.addActionListener(this);
			jButtonCancel.setBounds(270, 0, 30, 30);
			jButtonCancel.setEnabled(true);
			jButtonCancel.setBorderPainted(false);
			jButtonCancel.setVisible(false);
		}
		{
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.BARRA)));
			jToolBarImages.add(label);
			label.setBounds(303, 0, 3, 28);
		}
		{
			jButtonPrevious = new JButton();
			jToolBarImages.add(getJButtonPrevious());
			jButtonPrevious.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.PREVIOUS_IMAGE)));
			jButtonPrevious.setToolTipText(messages.getProperty(MessageKeys.PREVIOUS_IMAGE));
			jButtonPrevious.addActionListener(this);
			jButtonPrevious.setBounds(310, 0, 30, 30);
			jButtonPrevious.setEnabled(false);
			jButtonPrevious.setVisible(false);
			jButtonPrevious.setBorderPainted(false);
		}
		{
			JLabel space = new JLabel("    ");
			jToolBarImages.add(space);
			space.setMinimumSize(new Dimension(30,30));
			space.setPreferredSize(new Dimension(30,30));
		}
		{
			jLabelPage = new JLabel();
			jToolBarImages.add(jLabelPage);
			jLabelPage.setBounds(380, 5, 300, 15);
		}
		{
			JLabel space = new JLabel("    ");
			jToolBarImages.add(space);
			space.setMinimumSize(new Dimension(30,30));
			space.setPreferredSize(new Dimension(30,30));
		}
		{
			jButtonNext = new JButton();
			jToolBarImages.add(getJButtonNext());
			jButtonNext.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.NEXT_IMAGE)));
			jButtonNext.setToolTipText(messages.getProperty(MessageKeys.NEXT_IMAGE));
			jButtonNext.addActionListener(this);
			jButtonNext.setBounds(445, 0, 30, 30);
			//jButtonNext.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
			jButtonNext.setEnabled(false);
			jButtonNext.setVisible(false);
			jButtonNext.setBorderPainted(false);
		}
		/*{
			jButtonSalir = new JButton();
			jToolBar1.add(getJButtonSalir());
			jButtonSalir.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.EXIT)));
			jButtonSalir.setToolTipText(messages.getProperty(MessageKeys.EXIT));
			jButtonSalir.addActionListener(this);
			jButtonSalir.setEnabled(true);
			jButtonSalir.setBounds(450, 0, 30, 30);
			//jButtonPrevious.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
		}*/
	}

	private void menu(){
		{
			jMenuOpen = new JMenu();
			jMenuBarScan.add(jMenuOpen);
			//jMenuOpen.setText("Archivo");
			jMenuOpen.setText(messages.getProperty(MessageKeys.FILE));
			jMenuOpen.setVisible(optionsUI.isArchivo());
			{
				menuItemOpenFile = new JMenuItem();
				jMenuOpen.add(getMenuItemOpenFile());
				//menuItemOpenFile.setText("Abrir"+"...");
				menuItemOpenFile.setText(messages.getProperty(MessageKeys.OPEN)+"...");
				//openFileMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.OPEN)));
				menuItemOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
						ActionEvent.ALT_MASK));
				menuItemOpenFile.addActionListener(this);
			}
			{
				menuItemAddFile = new JMenuItem();
				jMenuOpen.add(getMenuItemAddFile());
				//menuItemAddFile.setText("Aï¿½adir"+"...");
				menuItemAddFile.setText(messages.getProperty(MessageKeys.ADD_IMAGE)+"...");
				menuItemAddFile.addActionListener(this);
				menuItemAddFile.setEnabled(false);
			}
			{
				menuItemSave = new JMenuItem();
				jMenuOpen.add(getMenuItemSave());
				//menuItemSave.setText("Salvar"+"...");
				menuItemSave.setText(messages.getProperty(MessageKeys.SAVE_AS)+"...");
				//saveAsMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SAVE)));
				menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
						ActionEvent.ALT_MASK));
				menuItemSave.addActionListener(this);
				menuItemSave.setEnabled(false);
			}
		}
		{
			jMenuCapture = new JMenu();
			jMenuBarScan.add(jMenuCapture);
			//jMenuCapture.setText("Captura");
			jMenuCapture.setText(messages.getProperty(MessageKeys.SCANNER));
			jMenuCapture.setVisible(optionsUI.isCapture());
			{
				menuItemScan = new JMenuItem();
				jMenuCapture.add(getMenuItemScan());
				//menuItemScan.setText("Iniciar Captura");
				menuItemScan.setText(messages.getProperty(MessageKeys.SCAN));
				menuItemScan.addActionListener(this);
				menuItemScan.setEnabled(true);
				menuItemScan.setVisible(optionsUI.isScan());
			}
			{
				menuItemScanFinish = new JMenuItem();
				jMenuCapture.add(getMenuItemScanFinish());
				//menuItemScanFinish.setText("Finalizar Captura");
				menuItemScanFinish.setText(messages.getProperty(MessageKeys.FINISH));
				//finishMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.UPLOAD)));
				menuItemScanFinish.addActionListener(this);
				menuItemScanFinish.setEnabled(false);
				menuItemScanFinish.setVisible(optionsUI.isScanFinish());
			}
			{
				jSeparator = new JSeparator();
				jMenuCapture.add(jSeparator);
			}
			{
				menuItemScanAdd = new JMenuItem();
				jMenuCapture.add(getMenuItemScanAdd());
				//menuItemScanAdd.setText("Aï¿½adir Pagina"+"...");
				menuItemScanAdd.setText(messages.getProperty(MessageKeys.SCAN_IMAGE)+"...");
				menuItemScanAdd.addActionListener(this);
				menuItemScanAdd.setEnabled(false);
				menuItemScanAdd.setVisible(optionsUI.isScanAdd());
			}
			{
				menuItemScanReplace = new JMenuItem();
				jMenuCapture.add(getMenuItemScanReplace());
				//menuItemScanReplace.setText("Reemplazar Pagina"+"...");
				menuItemScanReplace.setText(messages.getProperty(MessageKeys.SCAN_REPLACE)+"...");
				menuItemScanReplace.addActionListener(this);
				menuItemScanReplace.setEnabled(false);
				menuItemScanReplace.setVisible(optionsUI.isScanReplace());
			}
			{
				menuItemScanDelete = new JMenuItem();
				jMenuCapture.add(getMenuItemScanDelete());
				//menuItemScanDelete.setText("Eliminar Pagina"+"...");
				menuItemScanDelete.setText(messages.getProperty(MessageKeys.DELETE_IMAGE)+"...");
				//deletePageMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.DELETE_IMAGE)));
				menuItemScanDelete.addActionListener(this);
				menuItemScanDelete.setEnabled(false);
				menuItemScanDelete.setVisible(optionsUI.isScanDelete());
			}
		}
		{
			jMenuImage = new JMenu();
			jMenuBarScan.add(jMenuImage);
			//jMenuImage.setText("Imagenes");
			jMenuImage.setText(messages.getProperty(MessageKeys.IMAGES));
			{
				menuItemScale = new JMenuItem();
				menuItemScale.setEnabled(false);
				jMenuImage.add(menuItemScale);
				menuItemScale.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SCALE)));
				//menuItemScale.setText("Ajustar");
				menuItemScale.setText(messages.getProperty(MessageKeys.FIT));
				menuItemScale.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
						ActionEvent.ALT_MASK));
				menuItemScale.addActionListener(this);
			}
			{
				menuItemScaleVertical = new JMenuItem();
				menuItemScaleVertical.setEnabled(false);
				jMenuImage.add(menuItemScaleVertical);
				menuItemScaleVertical.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SCALE_VERTICAL)));
				//menuItemScaleVertical.setText("Ajuste Vertical");
				menuItemScaleVertical.setText(messages.getProperty(MessageKeys.FIT_VERTICAL));
				menuItemScaleVertical.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
						ActionEvent.ALT_MASK));
				menuItemScaleVertical.addActionListener(this);
			}
			{
				menuItemScaleHorizontal = new JMenuItem();
				menuItemScaleHorizontal.setEnabled(false);
				jMenuImage.add(menuItemScaleHorizontal);
				menuItemScaleHorizontal.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SCALE_HORIZONTAL)));
				//menuItemScaleHorizontal.setText("Ajustar Horizontal");
				menuItemScaleHorizontal.setText(messages.getProperty(MessageKeys.FIT_HORIZONTAL));
				menuItemScaleHorizontal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
						ActionEvent.ALT_MASK));
				menuItemScaleHorizontal.addActionListener(this);
			}
			{
				menuItemOriginal = new JMenuItem();
				menuItemOriginal.setEnabled(false);
				jMenuImage.add(menuItemOriginal);
				menuItemOriginal.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ORIGINAL)));
				//menuItemOriginal.setText("1:1");
				menuItemOriginal.setText(messages.getProperty(MessageKeys.ORIGINAL));
				menuItemOriginal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
						ActionEvent.ALT_MASK));
				menuItemOriginal.addActionListener(this);
			}
			{
				menuItemInZoom = new JMenuItem();
				menuItemInZoom.setEnabled(false);
				jMenuImage.add(menuItemInZoom);
				menuItemInZoom.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ZOOM_IN)));
				menuItemInZoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,
						ActionEvent.ALT_MASK));
				//menuItemInZoom.setText("Mas Zoom");
				menuItemInZoom.setText(messages.getProperty(MessageKeys.ZOOM_IN));
				menuItemInZoom.addActionListener(this);
			}
			{
				menuItemOutZoom = new JMenuItem();
				menuItemOutZoom.setEnabled(false);
				jMenuImage.add(menuItemOutZoom);
				menuItemOutZoom.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ZOOM_OUT)));
				menuItemOutZoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,
						ActionEvent.ALT_MASK));
				//menuItemOutZoom.setText("Menos zoom");
				menuItemOutZoom.setText(messages.getProperty(MessageKeys.ZOOM_OUT));
				menuItemOutZoom.addActionListener(this);
			}
			{
				menuItemRotate = new JMenuItem();
				menuItemRotate.setEnabled(false);
				jMenuImage.add(menuItemRotate);
				menuItemRotate.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.ROTATE)));
				//menuItemRotate.setText("Rotar");
				menuItemRotate.setText(messages.getProperty(MessageKeys.ROTATE));
				menuItemRotate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
						ActionEvent.ALT_MASK));
				menuItemRotate.addActionListener(this);
			}
			{
				menuItemCrop = new JMenuItem();
				menuItemCrop.setEnabled(false);
				jMenuImage.add(menuItemCrop);
				menuItemCrop.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.EDIT)));
				menuItemCrop.setText(messages.getProperty(MessageKeys.CROP));
				menuItemCrop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
						ActionEvent.ALT_MASK));
				menuItemCrop.addActionListener(this);
			}
		}
		{
			jMenuConfig = new JMenu();
			jMenuBarScan.add(jMenuConfig);
			//jMenuConfig.setText("Configuracion");
			jMenuConfig.setText(messages.getProperty(MessageKeys.CONFIG));
			{
				menuItemProfile = new JMenuItem();
				jMenuConfig.add(getMenuItemProfile());
				//menuItemProfile.setText("Perfiles"+"...");
				menuItemProfile.setText(messages.getProperty(MessageKeys.PROFILE)+"...");
				//selectPerfilMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.PROFILE)));
				menuItemProfile.setPreferredSize(new Dimension(90,20));
				menuItemProfile.addActionListener(this);
			}
			{
				menuItemSource = new JMenuItem();
				jMenuConfig.add(getMenuItemSource());
				//selectSourceMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SOURCE)));
				//menuItemSource.setText("Fuente"+"...");
				menuItemSource.setText(messages.getProperty(MessageKeys.SOURCE)+"...");
				menuItemSource.addActionListener(this);
			}
		}
		{
			jMenuHelp = new JMenu();
			jMenuBarScan.add(jMenuHelp);
			//jMenuHelp.setText("Ayuda");
			jMenuHelp.setText(messages.getProperty(MessageKeys.HELP));
			{
				menuItemAcercaDe = new JMenuItem();
				jMenuHelp.add(getMenuItemAcercaDe());
				menuItemAcercaDe.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SUPPORT)));
				//menuItemAcercaDe.setText("Acerca de"+"...");
				menuItemAcercaDe.setText(messages.getProperty(MessageKeys.ACERCA)+"...");
				menuItemAcercaDe.addActionListener(this);
			}
		}
		{
				//menuItemExit = new JMenuItem();
				//jMenuBarScan.add(getMenuItemExit());
				//menuItemExit.setText("Salir");
				//menuItemExit.setText(messages.getProperty(MessageKeys.EXIT));
				//exitMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.EXIT)));
				//menuItemExit.addActionListener(this);

		}
	}

	/**
	 * Se carga el idioma del applet
	 */
	private void chargeLocale(){
		Locale lc = this.getLocale();
		InputStream str = getClass().getClassLoader().getResourceAsStream ("resources/message_"+lc.getCountry()+".properties");
		if(str == null){
			str = getClass().getClassLoader().getResourceAsStream ("resources/message_ES.properties");
		}
		messages = new Properties();
		try {
			messages.load(str);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}


	// Getters and Setters
	public JPanel getJPanelBackground() {
		return jPanelBackground;
	}

	public JMenuItem getMenuItemExit() {
		return menuItemExit;
	}

	public JMenu getJMenuCapture() {
		return jMenuCapture;
	}

	public JMenuItem getMenuItemScan() {
		return menuItemScan;
	}

	public JMenuItem getMenuItemScanReplace() {
		return menuItemScanReplace;
	}

	public JMenuItem getMenuItemScanAdd() {
		return menuItemScanAdd;
	}

	public JMenuItem getMenuItemScanFinish() {
		return menuItemScanFinish;
	}

	public JMenuItem getMenuItemScanDelete() {
		return menuItemScanDelete;
	}

	public JMenuBar getJMenuBarScan() {
		return jMenuBarScan;
	}

	public JMenu getJMenuOpen() {
		return jMenuOpen;
	}

	public JMenu getJMenuImage() {
		return jMenuImage;
	}

	public JToolBar getJToolBarImages() {
		return jToolBarImages;
	}

	public JScrollPane getJScrollPaneIcon() {
		return jScrollPaneIcon;
	}

	public JMenu getJMenuHelp() {
		return jMenuHelp;
	}

	public JMenu getJMenuConfig() {
		return jMenuConfig;
	}

	public FileVO getFileVO() {
		return fileVO;
	}

	public PerfilesVO getPerfiles() {
		return perfiles;
	}

	public Properties getMessages() {
		return messages;
	}

	public JButton getJButtonScan() {
		return jButtonScan;
	}

	public JButton getJButtonNext() {
		return jButtonNext;
	}

	public JButton getJButtonPrevious() {
		return jButtonPrevious;
	}

	public JButton getJButtonScale() {
		return jButtonScale;
	}

	public JButton getJButtonScaleHorizontal() {
		return jButtonScaleHorizontal;
	}

	public JButton getJButtonScaleVertical() {
		return jButtonScaleVertical;
	}

	public JButton getJButtonRote() {
		return jButtonRote;
	}

	public JButton getJButtonZoom() {
		return jButtonZoom;
	}

	public JButton getJButtonOutZoom() {
		return jButtonOutZoom;
	}

	public JButton getJButtonDelete() {
		return jButtonDelete;
	}

	public JButton getJButtonOriginal() {
		return jButtonOriginal;
	}

	public JButton getJButtonSalir() {
		return jButtonSalir;
	}

	public JSeparator getJSeparator() {
		return jSeparator;
	}

	public JLabel getJLabelPage() {
		return jLabelPage;
	}

	public JTextField getJTextFuente() {
		return jTextFuente;
	}

	public JMenuItem getMenuItemSave() {
		return menuItemSave;
	}

	public JMenuItem getMenuItemOpenFile() {
		return menuItemOpenFile;
	}

	public JMenuItem getMenuItemNewFile() {
		return menuItemNewFile;
	}

	public JMenuItem getMenuItemAddFile() {
		return menuItemAddFile;
	}

	public JMenuItem getMenuItemInZoom() {
		return menuItemInZoom;
	}

	public JMenuItem getMenuItemOutZoom() {
		return menuItemOutZoom;
	}

	public JMenuItem getMenuItemScale() {
		return menuItemScale;
	}

	public JMenuItem getMenuItemOriginal() {
		return menuItemOriginal;
	}

	public JMenuItem getMenuItemRotate() {
		return menuItemRotate;
	}

	public JMenuItem getMenuItemScaleVertical() {
		return menuItemScaleVertical;
	}

	public JMenuItem getMenuItemScaleHorizontal() {
		return menuItemScaleHorizontal;
	}

	public JMenuItem getMenuItemAcercaDe() {
		return menuItemAcercaDe;
	}

	public JMenuItem getMenuItemProfile() {
		return menuItemProfile;
	}


	public JMenuItem getMenuItemCrop() {
		return menuItemCrop;
	}

	public Cropping getCrop() {
		return crop;
	}

	public JMenuItem getMenuItemSource() {
		return menuItemSource;
	}

	public OptionsUIVO getOptionsUI() {
		return optionsUI;
	}

	public JButton getJButtonCrop() {
		return jButtonCrop;
	}

	public JButton getJButtonOK() {
		return jButtonOK;
	}

	public JButton getJButtonCancel() {
		return jButtonCancel;
	}

	public ParametrosVO getParametrosVO() {
		return parametrosVO;
	}

	public void setParametrosVO(ParametrosVO parametrosVO) {
		this.parametrosVO = parametrosVO;
	}

	public void setCrop(Cropping crop) {
		this.crop = crop;
	}


}
