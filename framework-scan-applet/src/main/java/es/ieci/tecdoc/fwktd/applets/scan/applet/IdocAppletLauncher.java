package es.ieci.tecdoc.fwktd.applets.scan.applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import es.ieci.tecdoc.fwktd.applets.scan.actions.ActionScan;
import es.ieci.tecdoc.fwktd.applets.scan.key.KeysUtils;
import es.ieci.tecdoc.fwktd.applets.scan.key.MessageKeys;
import es.ieci.tecdoc.fwktd.applets.scan.ui.PerfilesApplet;
import es.ieci.tecdoc.fwktd.applets.scan.utils.FileUtils;
import es.ieci.tecdoc.fwktd.applets.scan.vo.FileVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ImageVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.OptionsUIVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ParametrosVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;

public class IdocAppletLauncher extends JApplet implements ActionListener{

	public static  String tipoDoc=null;
	public static  String resolucion=null;
	public static  String tamano=null;
	public static  String color=null;
	public static  String dummy=null;
	public static String servlet=null;
	public static String returnJSFunction=null;
	public static Boolean permitirGuardarComo=true;
	public static Boolean obligarPerfil=false;
	public static Boolean mostrarUI=true;
	private static IdocFrame idocScanFrame = null;

	private static final int CODE_OK = 0;

	//Errores partir del 1000
	private static final int ERROR_CODE_INESPERADO = 1001;
	private static final int ERROR_CODE_NO_INICIALIZADO = 1001;
	private static final int ERROR_CODE_NO_PERFIL = 1002;


	//Necesario para lanzar la config por separado
	private static OptionsUIVO optionsUI;
	private static PerfilesVO perfiles;
	private static Properties messages;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		System.out.println("Init Applet");

		//Color -> Blanco Negro -> 0 // Escala Grises -> 1 // Color ->2
		String colorValue = getParameter("color");
		if(colorValue!=null && (colorValue.equals("0") || colorValue.equals("1") || colorValue.equals("2")))
		{
			color = getParameter("color");
		}
		else if(colorValue!=null && !"".equals(colorValue)){
			System.out.println("El parametro color es incorrecto. Ha de ser 0,1 ó 2");
		}

		//TIPO de documento -> PDF, JPEG, TIFF, TIFF_MULTIPAGE
		String tipoDocumento =  getParameter("tipoDoc");
		if(tipoDocumento!=null &&
				(tipoDocumento.equals(KeysUtils.PDF) ||
					tipoDocumento.equals(KeysUtils.BMP) ||
					tipoDocumento.equals(KeysUtils.JPEG) ||
					tipoDocumento.equals(KeysUtils.TIFF) ||
					tipoDocumento.equals(KeysUtils.TIFF_Multipage)))
		{
			if(tipoDocumento.equals(KeysUtils.JPEG) && color!=null && color.equals("0"))
			{
				//Si han puesto jpeg y blanco y negro es incompatible
				System.out.println("El parametro tipoDoc no puede ser JPEG si el color es 0 (B/N)");
			}
			else
			{
				tipoDoc = getParameter("tipoDoc");
			}
		}
		else if(tipoDocumento!=null && !"".equals(tipoDocumento))
		{
			System.out.println("El parametro tipoDoc es incorrecto. Ha de ser PDF,JPEG,BMP,TIFF O TIFF_MULTIPAGE");
		}
		//Resolución -> 100.0, 200.0, 300.0, 400.0,600.0, 800.0 y 1200.0
		resolucion = getParameter("resolucion");

		//Tipo de página
		tamano = getParameter("tamano");

		//Comandos habilitados -> 0 // Comandos deshabilitados -> 1
		dummy = getParameter("dummy");

		//servlet = getParameter("servlet");

		returnJSFunction = getParameter("returnJSFunction");

		if(getParameter("permitirGuardarComo")!=null && !"".equals(getParameter("permitirGuardarComo")))
		{
			permitirGuardarComo = Boolean.parseBoolean(getParameter("permitirGuardarComo"));
		}
		if(getParameter("obligarPerfil")!=null && !"".equals(getParameter("obligarPerfil")))
		{
			obligarPerfil = Boolean.parseBoolean(getParameter("obligarPerfil"));
		}
		//Llamamos a la funcion JS "finishLoadJSFunction", que hace callback para avisar de que el applet está listo

		try{
			JSObject win = JSObject.getWindow(this);
			win.call("finishLoadJSFunction", null);
		}
		catch (Exception e) {
			System.out.println("No se ha podido invocar a la funcion callback finishLoadJSFunction.");
		}

		super.init();

	}

	/**
	 * Lanza sólo la ventana de configuración
	 */
	public void configure()
	{
		AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

			public Boolean run() {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
					//Cargamos config que puede venir en la definición del applet
					ParametrosVO parametrosVO = new ParametrosVO();
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

					//Iniciamos ficheros de config, perfiles, directorios...
					initConfig();
					//Arrancamos twain para poder ver los escáneres y sus opciones

					ActionScan.init();
					//Mostramos la ventana de perfiles y configuración
					final PerfilesApplet app = new PerfilesApplet(true,perfiles, messages, parametrosVO);
					app.setTitle(messages.getProperty(MessageKeys.CONFIG) + " > " + messages.getProperty(MessageKeys.PROFILE));
					app.pack();
					app.setLocationRelativeTo(null);
					app.setResizable(false);
					app.setVisible(true);
					//Añadimos listener para cerrar la conexión cuando cierre la pantalla
					app.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent ev) {
							ActionScan.close();
							app.removeWindowListener(this);
						}
					});
				}});

				return null;
			}
		});
	}

	/**
	 * Arranca el IDocFrame
	 * Posibles codigos de error:
	 * 1000 -> Error inesperado
	 * 1002 -> No hay perfiles creados (si se arranca sin UI)
	 * @param permitirGuardarComoBoolean
	 * @param urlServlet
	 * @return int Codigo de resultado. OK -> 0 , Errores -> 1000 en adelante
	 */
	public int startScanApplet(final boolean permitirGuardarComoBoolean, final boolean obligarPerfilBoolean,final boolean mostrarUI){

		System.out.println("Permitir guardar como:"+permitirGuardarComoBoolean);
		System.out.println("Obligar perfil:"+obligarPerfilBoolean);
		this.permitirGuardarComo = permitirGuardarComoBoolean;
		this.obligarPerfil = obligarPerfilBoolean;
		this.mostrarUI = mostrarUI;
		final Integer result = 0;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			Integer result = AccessController.doPrivileged(new PrivilegedAction<Integer>(){
				public Integer run() {

					int result = CODE_OK;
					ParametrosVO parametrosVO = new ParametrosVO();

					parametrosVO.setPermitirGuardarComo(permitirGuardarComo);
					parametrosVO.setObligarPerfil(obligarPerfil);
					parametrosVO.setLanzadoComoApplet(true);

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

					if(idocScanFrame==null)
					{

						idocScanFrame = new IdocFrame(parametrosVO);
						idocScanFrame.setLocationRelativeTo(null);
						idocScanFrame.setAppletLanzador(IdocAppletLauncher.this);
					}
					else
					{
						//Volvemos a leer los perfiles
						initConfig();
						//Inicalizamos el scanner (al salir la vez anterior se cerró el DS)
						ActionScan.init();
						//Seteamos los nuevos perfiles
						idocScanFrame.setPerfiles(perfiles);
						idocScanFrame.pack();

					}

					idocScanFrame.setVisible(mostrarUI);

					//Si no se muestra la UI y no hay perfil, retornar error
					if(!mostrarUI && (idocScanFrame.getPerfiles()==null
							|| idocScanFrame.getPerfiles().getHashPerfiles()==null
							|| idocScanFrame.getPerfiles().getHashPerfiles().size()==0))
					{

						idocScanFrame.dispose();
						JOptionPane.showMessageDialog(null, "Debe crear un perfil para poder escanear",
								"Aviso", JOptionPane.INFORMATION_MESSAGE);
						configure();
					}
					else if(!mostrarUI)
					{
						scanFile();
					}
					return result;
				}
				});
			}
		});

		return result;
	}

	/**
	 * Escanea un fichero con el perfil que haya por defecto y finaliza la acción, es decir,
	 * llama a la función <code>returnFilesToJS</code> tras convertirlo al formato final y dejarlo
	 * en USER.HOME/iecisa/final
	 * Posibles codigos de error:
	 * 1000 -> Error inesperado
	 * 1001 -> Applet no inicializado. Debe de llamar antes al startScanApplet
	 * 1002 -> No hay perfil creado para escanear
	 *  @return int Codigo de resultado: OK -> 0,  Errores -> 1000 en adelante.
	 */
	public int scanFile()
	{
		//Al venir de JS, lo metemos en doPrivileged

		Integer result = AccessController.doPrivileged(new PrivilegedAction<Integer>(){
			public Integer run() {
				int result = CODE_OK;
				if(idocScanFrame==null)
				{
					result =ERROR_CODE_NO_INICIALIZADO;
				}
				if(idocScanFrame.getPerfiles()==null
						|| idocScanFrame.getPerfiles().getHashPerfiles()==null
						|| idocScanFrame.getPerfiles().getHashPerfiles().size()==0)
				{
					result = ERROR_CODE_NO_PERFIL;
				}
				try{
					idocScanFrame.acquireWithoutUI();
				}
				catch (Exception e) {
					System.out.println("Error inesperado al escanear sin interfaz: "+e.getMessage());
					e.printStackTrace();
					result = ERROR_CODE_INESPERADO;
				}
				return result;
			}
		});
		return result;
	}


	/**
	 * Este método es llamada cuando se cumple lo siguiente:
	 *  - Se ha lanzado el IdocFrame como applet
	 *  - No se ha indicado una servletURL
	 *  - Se pulsa finalizar Captura.
	 *
	 *  Lo que hace es llamar a la funcion JS <code>returnJSFunction</code> que se haya indicado
	 *  en la definición para retornar los ficheros escaneados
	 */
	public void returnFilesToJS(){
		System.out.println("ReturnFilestoJS - Vamos a llamar a la funcion: "+returnJSFunction);
		JSObject win = JSObject.getWindow(this);
		String[] files=getFiles();
		try{
			win.call(returnJSFunction,new Object[]{files});
			System.out.println("Ficheros retornados.");
		}
		catch (JSException e) {
			System.out.println("No se ha podido invocar a la función JS de retorno de ficheros con nombre: "+returnJSFunction);
			e.printStackTrace(System.out);
			try{
				win.call(returnJSFunction+"String",new Object[]{toJavascriptString(files)});
			}catch(JSException ex) {
				System.out.println("No se ha podido invocar a la función JS de retorno de ficheros con nombre: "+returnJSFunction+"String");
				ex.printStackTrace(System.out);
			}

		}
	}

	public String[] getFiles(){
		FileVO file = idocScanFrame.getFileVO();
		int numFiles = 0;
		if(file!=null && file.getListImage()!=null && file.getListImage().size()>0){
			numFiles=file.getListImage().size();
		}
		String[] files = new String[(numFiles*2)];
		int j=0;
		for(int i=0;i<numFiles;i++)	{
			ImageVO image = (ImageVO)file.getListImage().get(i);
			String filepath=image.getImage();
			
			File fileToKnowSize = new File(filepath);
			String strFileSize=Long.toString(fileToKnowSize.length());
			System.out.println("Fichero listo para retornar: "+filepath+" "+strFileSize+" bytes");
			
			files[j]=filepath;
			files[j+1]=strFileSize;
			j=j+2;
		}
		return files;
	}
	
	public String toJavascriptString(final String [] arrayString){
		StringBuffer result= new StringBuffer();

		for (int i = 0; i < arrayString.length; i++) {
			result.append(arrayString[i]+"|");
		}
		result.deleteCharAt(result.lastIndexOf("|"));
		return result.toString();
	}
	
	/**
	 * Compatibilidad con JRE anteriores a JRE 1.6.0_04
	 * @param filesArray
	 * @return
	 */
	public int getNumFilesInFilesArray(final String [] filesArray){
		return filesArray.length;
	}
	
	public String getFileFromFilesArray(final String [] filesArray,int indice){
		if(filesArray.length-1>=indice){
			return filesArray[indice];
		}
		return null;
	}
	
	public String getFilesizeFromFilesArray(final String [] filesArray,int indice){
		if(filesArray.length-1>=indice+1){
			return filesArray[indice+1];
		}
		return null;
	}
	
	/**
	 *
	 */
	private void initConfig() {
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

		//por defecto se va a buscar el fichero config.xml en el directorio user.home
		//sin embargo, el propio xml que se carga, puede definir un nuevo userHome
		//para evitar que se pierdan los nuevos perfiles creados, una vez que se obtiene el userHome Correcto (el del config.xml)
		//hay que volver a cargar los perfiles.
		
		if(xml!=null){
			perfiles = perfiles.fromXml(xml);
			try{
				xml = FileUtils.readPerfiles(perfiles);
			}catch (Exception e) {
				System.out.println("No existe el fichero de perfiles");
			}
			perfiles = perfiles.fromXml(xml);
			
		}


		String pathFile = perfiles.getUserHome();
		File file = new File(pathFile + optionsUI.getPathTemp());
		if(!(file.exists())){
			file.mkdirs();
			System.out.println("Creado directorio: " + file.getPath());
		}
		File convertDir = new File(pathFile + optionsUI.getPathConvert());
		if(!(convertDir.exists())){
			convertDir.mkdirs();
			System.out.println("Creado directorio: " + convertDir.getPath());
		}
		chargeLocale();

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


	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void start() {
		System.out.println("JApplet:start()");
		super.start();
	}

	@Override
	public void stop() {
		ActionScan.close();
		if(idocScanFrame!=null){
			idocScanFrame.dispose();
			idocScanFrame=null;
		}
		System.out.println("JApplet:stop()");
		super.stop();
	}
}
