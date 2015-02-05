package es.ieci.tecdoc.fwktd.applets.fileChooser.applet;


import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.swing.JApplet;
import javax.swing.UIManager;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * @author 66596040
 *
 */
public class FileChooserApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 817475094979770865L;
	
	
	/**
	 * Nombre de la funcion javascript a invocar una vez finalizada la selecion del fichero
	 */
	protected String returnJSFunction;
	
	/**
	 * nombre de la funcion javascript a invocar una vez finalizada la carga del applet
	 */
	protected String finishLoadJSFunction;
	
	
	/**
	 * componente encargado de selecionar el fichero
	 */
	protected FileChooser fileChooser;
	
	
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		this.returnJSFunction=getParameter("returnJSFunction");
		
		this.finishLoadJSFunction=getParameter("finishLoadJSFunction");
		
		try {
		 javax.swing.SwingUtilities.invokeAndWait((new Runnable() {
		        public void run() {
		        	//inicializamos los componentes gráficos del applet
		        	initApplet();
			
		    //una  vez inicializado invocamos a la funcion javascript del navegaor    	
			if (finishLoadJSFunction!=null){
				try{
					JSObject win = JSObject.getWindow(FileChooserApplet.this);
					win.call(finishLoadJSFunction, null);
				}
				catch (Exception e) {
					System.out.println("No se ha podido invocar a la funcion callback finishLoadJSFunction.");
				}
			}
		        }
		    }));
		} catch (Exception e) {
		        System.err.println("initApplet didn't successfully complete");
		}
		
    }
		 
	
	/**
	 * metodo inicializador del applet con  el layout y componente de applet adecuado
	 */
	private void initApplet(){
		try {
			setLayout(null);
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
		   
		}
		fileChooser= new FileChooser(this);
		this.add(fileChooser);
		super.init();
	}
	
	
	/**
	 * método que invoca al método javascript indicado por la propieda  <code>returnJSFunction</code> pasándole como parámetro el fichero seleccionado
	 */
	public void returnFilesToJS(){
		System.out.println("ReturnFilestoJS");
		String filePath=fileChooser.getFilePath();
		
		if (returnJSFunction!=null||!"".equals(returnJSFunction)){
			JSObject win = JSObject.getWindow(this);
			
			try{
				win.call(returnJSFunction,new Object[]{filePath});
				System.out.println("Ficheros retornados.");
			}
			catch (JSException e) {
				System.out.println("No se ha podido invocar a la función JS de retorno de ficheros con nombre: "+returnJSFunction+e.getLocalizedMessage());
	
			}
		}
	}

	/**
	 * Método expuesto en el applet para selecionar un fichero del sistema de ficheros del equipo local
	 * @return
	 */
	public String selectFile(){
		return AccessController.doPrivileged(new PrivilegedAction<String>(){

			public String run() {
				String result=null;
				result =fileChooser.selectFile();
				return result;
			}
		});
	}


	
	/**
	 * getter
	 * @return
	 */
	public String getReturnJSFunction() {
		return returnJSFunction;
	}


	/**
	 * setter
	 * @param returnJSFunction
	 */
	public void setReturnJSFunction(String returnJSFunction) {
		this.returnJSFunction = returnJSFunction;
	}


	/**
	 * getter
	 * @return
	 */
	public String getFinishLoadJSFunction() {
		return finishLoadJSFunction;
	}


	/**
	 * setter
	 * @param finishLoadJSFunction
	 */
	public void setFinishLoadJSFunction(String finishLoadJSFunction) {
		this.finishLoadJSFunction = finishLoadJSFunction;
	}
			
	


}
