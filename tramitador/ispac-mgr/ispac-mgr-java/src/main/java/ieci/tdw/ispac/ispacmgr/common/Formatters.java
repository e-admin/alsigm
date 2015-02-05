package ieci.tdw.ispac.ispacmgr.common;

import java.util.HashMap;

import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import javax.servlet.ServletContext;

public class Formatters {
	private static Formatters fmtsInstance = null;
	private ServletContext serverCtx = null;
	private HashMap map = null;
	
	//Metodo exclusivo para la inicializacion de la clase
	protected static void init(ServletContext serverCtx){
		if (fmtsInstance==null){
			fmtsInstance = new Formatters(serverCtx);
		}
	}
	
	public static Formatters getInstance(){
		if (fmtsInstance!=null){
			return fmtsInstance;
		
		}else{
			throw new RuntimeException("Formateadores no inicializados - Es necesario inicializarlos al arrancar la aplicacion!!!");
		}
	}
	
	public final static String TASK_FORMATTER_NAME = "TaskFormatter";
	public final static String PROCEDURES_FORMATTER_NAME = "ProceduresFormatter";
	public final static String BATCH_TASK_LIST_FORMATTER_NAME = "BatchTaskListFormatter";
	public final static String BATCH_TASK_FORMATTER_NAME = "BatchTaskFormatter";
	
	private Formatters(ServletContext serverCtx){
		this.serverCtx = serverCtx;
		map = new HashMap();
		map.put(TASK_FORMATTER_NAME, getFormatter("/digester/proceduresformatter.xml"));
		map.put(PROCEDURES_FORMATTER_NAME, getFormatter("/digester/tasksformatter.xml"));
		map.put(BATCH_TASK_LIST_FORMATTER_NAME, getFormatter("/digester/batchtaskslistformatter.xml"));
		map.put(BATCH_TASK_FORMATTER_NAME, getFormatter("/digester/batchtaskformatter.xml"));
		
	}
	
	private String getISPACPath(String relativepath) {
	    ISPACRewrite rewrite=new ISPACRewrite(this.serverCtx);
		return rewrite.rewritePath(relativepath);
	}
	
	private BeanFormatter getFormatter(String formatterPath) {
		CacheFormatterFactory factory;
		try{
		factory = CacheFormatterFactory.getInstance();
		return  factory.getFormatter(getISPACPath(formatterPath));
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public HashMap getMap() {
		return map;
	}
	



}
