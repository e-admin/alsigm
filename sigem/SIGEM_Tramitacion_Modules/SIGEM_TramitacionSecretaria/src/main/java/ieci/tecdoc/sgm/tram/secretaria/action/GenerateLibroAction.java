package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.gendoc.converter.DocumentConverter;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispacmgr.action.BaseAction;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.sign.SigemSignConnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;




public abstract class GenerateLibroAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(GenerateLibroAction.class);
	protected ClientContext ctx=null;

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		ctx=session.getClientContext();
		 ///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
   	    IState currentState = managerAPI.currentState(getStateticket(request));
		Object connectorSession=null;
		IInvesflowAPI invesflowAPI =ctx.getAPI();
		IEntitiesAPI  entitiesAPI = invesflowAPI.getEntitiesAPI();
		IGenDocAPI genDocAPI = invesflowAPI.getGenDocAPI();
		// Ejecución en un contexto transaccional

		String idTpDocLibro=getIdTpDocLibro();
		String idTpDocDiligenciaApertura=getIdTpDocDiligenciaApertura();
		String idTpDocDiligenciaCierre=getIdTpDocDiligenciaCierre();
		String idTpDocContenido=getIdTpDocContenido();
		String [] idTpDocContenidoAdicional=getIdTpDocContenidoAdicional();
		int idTramite=currentState.getTaskId();
		String numexp = currentState.getNumexp();
		IItem documento=null;
		IItem documentoAdicional=null;
		int indiceBeginContenidoAdicional=0;
		DbCnt cnt=null;
		int num_hoja_ini=0;
		int i=0;
		int anio=0;



		//Obtenemos el registro de la entidad del libro
		IItemCollection itemcol= entitiesAPI.getEntities(SecretariaConstants.ENTITY_LIBRO, numexp);
		IItem libro=itemcol.value();
		if(!StringUtils.isBlank(libro.getString(SecretariaConstants.FIELD_LIBRO_NUM_HOJA_INI))){
			boolean bCommit = false;
			try {
					 // Abrir transacción para que no se pueda generar un documento sin fichero
			     ctx.beginTX();
			    cnt=ctx.getConnection();
				num_hoja_ini=libro.getInt(SecretariaConstants.FIELD_LIBRO_NUM_HOJA_INI);
				anio=libro.getInt(SecretariaConstants.FIELD_LIBRO_ANIO);
				if(logger.isDebugEnabled()){
					logger.debug("Se va a generar el libro para el año "+anio);
				}

				//Obtener la diligencia apertura y cierre firmada y los descargamos en el temporal
				IItemCollection itemCollectionApertura = entitiesAPI.queryEntities(
						ISPACEntities.DT_ID_DOCUMENTOS,
						"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) +"' AND ID_TPDOC="+idTpDocDiligenciaApertura+
						" AND ESTADOFIRMA='"+DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)+"'");
				IItemCollection itemCollectionCierre = entitiesAPI.queryEntities(
					ISPACEntities.DT_ID_DOCUMENTOS,
					"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) +"' AND ID_TPDOC="+idTpDocDiligenciaCierre+
					" AND ESTADOFIRMA='"+DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)+"' AND ID_TRAMITE="+idTramite);

				connectorSession = genDocAPI.createConnectorSession();
				IItem diligenciaApertura=itemCollectionApertura.value();
				IItem diligenciaCierre= itemCollectionCierre.value();

				if(logger.isDebugEnabled()){
					logger.debug("Diligencia de apertura y cierre obtenidas");
				}


				//Obtener el listado  pertenecientes al ejercicio actual
				GregorianCalendar initDayGregorianCalendar = new GregorianCalendar();
				initDayGregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,
						initDayGregorianCalendar.getMinimum(GregorianCalendar.DAY_OF_MONTH));
				initDayGregorianCalendar.set(GregorianCalendar.MONTH,
						initDayGregorianCalendar.getMinimum(GregorianCalendar.MONTH));
				initDayGregorianCalendar.set(GregorianCalendar.HOUR,
						initDayGregorianCalendar.getMinimum(GregorianCalendar.HOUR));
				initDayGregorianCalendar.set(GregorianCalendar.MINUTE,
						initDayGregorianCalendar.getMinimum(GregorianCalendar.MINUTE));
				initDayGregorianCalendar.set(GregorianCalendar.SECOND,
						initDayGregorianCalendar.getMinimum(GregorianCalendar.SECOND));
				initDayGregorianCalendar.set(GregorianCalendar.YEAR,anio);
				String initDay= DBUtil.getToDateByBD(cnt, initDayGregorianCalendar.getTime());

				GregorianCalendar finDayGregorianCalendar = new GregorianCalendar();
				finDayGregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,
						finDayGregorianCalendar.getMaximum(GregorianCalendar.DAY_OF_MONTH));
				finDayGregorianCalendar.set(GregorianCalendar.MONTH,
						finDayGregorianCalendar.getMaximum(GregorianCalendar.MONTH));
				finDayGregorianCalendar.set(GregorianCalendar.HOUR,
						finDayGregorianCalendar.getMaximum(GregorianCalendar.HOUR));
				finDayGregorianCalendar.set(GregorianCalendar.MINUTE,
						finDayGregorianCalendar.getMaximum(GregorianCalendar.MINUTE));
				finDayGregorianCalendar.set(GregorianCalendar.SECOND,
						finDayGregorianCalendar.getMaximum(GregorianCalendar.SECOND));
				finDayGregorianCalendar.set(GregorianCalendar.YEAR,anio);
				Timestamp ts =  new Timestamp( finDayGregorianCalendar.getTimeInMillis());
				String endDay= DBUtil.getToTimestampByBD(cnt, ts);


				TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
				tableJoinFactoryDAO.addTable("SPAC_DT_DOCUMENTOS","SPAC_DT_DOCUMENTOS");
				if(StringUtils.isNotEmpty(getTableNameToAddQuery())){
					tableJoinFactoryDAO.addTable(getTableNameToAddQuery(),getTableNameToAddQuery());
				}
				IItemCollection contenido = tableJoinFactoryDAO.queryTableJoin(ctx.getConnection(),
					getQueryContenido(initDay,
									  endDay,
									  idTpDocContenido,
									  numexp)).disconnect();



		        List idsDocsLibro  =  new ArrayList();
		        idsDocsLibro.add(diligenciaApertura.getKey());

		        Iterator itr = contenido.iterator();
		        while (itr.hasNext()){
		        	documento=(IItem) itr.next();
		        	idsDocsLibro.add(documento.get("SPAC_DT_DOCUMENTOS:ID"));
		        	 IItem conj= entitiesAPI.createEntity(SecretariaConstants.ENTITY_CONJUNTO_CONTENIDOS, numexp);
					 conj.set(SecretariaConstants.FIELD_CONJUNTO_CONTENIDOS_NOMBRE,
							 		 documento.get(getFieldName()));
					 conj.set(SecretariaConstants.FIELD_CONJUNTO_CONTENIDOS_FECHA,
							 		 documento.getDate(getFieldFecha()));
					 conj.set(SecretariaConstants.FIELD_CONJUNTO_CONTENIDOS_NUM,
							 		documento.get(getFieldNum()));
					 conj.store(ctx);
					 //Buscamos los documentos adicionales
						tableJoinFactoryDAO = new TableJoinFactoryDAO();
						tableJoinFactoryDAO.addTable("SPAC_DT_DOCUMENTOS","SPAC_DT_DOCUMENTOS");
						if(StringUtils.isNotEmpty(getTableNameToAddQueryAdicional())){
							tableJoinFactoryDAO.addTable(getTableNameToAddQueryAdicional(),getTableNameToAddQueryAdicional());
						}
						IItemCollection contenidoAdicional = tableJoinFactoryDAO.queryTableJoin(ctx.getConnection(),
								getQueryContenidoAdicional(initDay,
														   endDay,
														   idTpDocContenidoAdicional,
														   documento.getString("SPAC_DT_DOCUMENTOS:NUMEXP"))).disconnect();

						Iterator itrAdicional=contenidoAdicional.iterator();
						while(itrAdicional.hasNext()){
							IItem itemDocAdicional=(IItem)itrAdicional.next();
							idsDocsLibro.add((itemDocAdicional).get("SPAC_DT_DOCUMENTOS:ID"));

						}


		        }

		        //La diligencia de cierre
		        idsDocsLibro.add(diligenciaCierre.getKey());


				//Concatener diligencia apertura+decretos+cierre en un solo documento y guardarlo
				String urlFileConcatenate = DocumentConverter.concatenate2PDF(session.getAPI(), getIds(idsDocsLibro), true);


				//contar paginas y numerar pdf

				float margen = Float.parseFloat(ISPACConfiguration.getInstance()
						.getProperty(SecretariaConstants.MARGIN_BAND_LIBRO));
				String font = ISPACConfiguration.getInstance().getProperty(SigemSignConnector.FONT_BAND);
				String encoding = ISPACConfiguration.getInstance().getProperty(SigemSignConnector.ENCODING_BAND);
				float fontSize = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(SigemSignConnector.FONTSIZE_BAND));
				float bandSize = Float.parseFloat(ISPACConfiguration.getInstance()
						.getProperty(SigemSignConnector.SIZE_INICIAL_BAND));
				PdfReader reader = new PdfReader(urlFileConcatenate);
				int pageCount = reader.getNumberOfPages();
				int ancho = (int) reader.getPageSize(pageCount).getWidth();
				Document document = new Document();
				String urlLibro=FileTemporaryManager.getInstance().getFileTemporaryPath()
				+ "/temp.pdf";
				FileOutputStream fileOut = new FileOutputStream(
						urlLibro ,true);
				PdfWriter writer = PdfWriter.getInstance(document, fileOut);
				document.open();
				Rectangle r = document.getPageSize();

				for(i=0;i<pageCount;i++){
					PdfImportedPage page = writer.getImportedPage(reader, i+1);
					Image image = Image.getInstance(page);
					image.setAbsolutePosition(bandSize, 0.0F);
					image.scaleAbsoluteWidth(r.getWidth() - bandSize);
					image.scaleAbsoluteHeight(r.getHeight());
					document.add(image);
					PdfContentByte  pdfContentByte = writer.getDirectContent();
					BaseFont bf = BaseFont.createFont(font, encoding, false);
					pdfContentByte.beginText();
					pdfContentByte.setFontAndSize(bf, fontSize);
					pdfContentByte.setTextMatrix( ancho-margen, margen);
					pdfContentByte.showText(num_hoja_ini+i+"");
					pdfContentByte.endText();
					document.newPage();

				}

				document.close();


				if(logger.isDebugEnabled()){
					logger.debug("Número total de paginas del libro  "+urlFileConcatenate);
				}
				//Anexamos el documento al trámite actual
				int index = urlFileConcatenate.lastIndexOf(".");
				String sExtension = urlFileConcatenate.substring(index + 1, urlFileConcatenate.length());
				String sMimeType = MimetypeMapping.getMimeType(sExtension);
				IItem documentoLibro=genDocAPI.createTaskDocument(idTramite, Integer.parseInt(idTpDocLibro), 0, 0);
				documentoLibro.set("EXTENSION", sExtension);
				documentoLibro.store(ctx);
				reader = new PdfReader(urlLibro);
				InputStream in = new FileInputStream(urlLibro);
				genDocAPI.attachTaskInputStream(connectorSession, idTramite, documentoLibro.getKeyInt(), in, (int) reader.getFileLength() , sMimeType, documentoLibro.getString("NOMBRE"));

				//Actualizamos el numero de hojas fin
				libro.set(SecretariaConstants.FIELD_LIBRO_NUM_HOJA_FIN, num_hoja_ini+pageCount-1);
				libro.store(ctx);

				if(logger.isDebugEnabled()){
					logger.debug("El libro del año "+anio+" termina en la página "+(num_hoja_ini+pageCount-1));
				}
				bCommit=true;
			} catch (Exception e) {
				logger.error(e);
				throw new ISPACException(e);

			}finally{
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
				ctx.endTX(bCommit);
				if(cnt!=null){
	        		ctx.releaseConnection(cnt);
	        	}
				FileUtils.deleteDirContents(new File (FileTemporaryManager.getInstance().getFileTemporaryPath()));
			}
			if(bCommit){
				//throw new ISPACInfo(getMessage(request, ctx.getLocale(), "libro.generate"));
				return NextActivity.refresh(currentState, mapping);
			}
		}
		else{
			if(logger.isDebugEnabled()){
				logger.debug("El tramitador no ha indicado la hoja de inicio del libro");
			}
			throw new ISPACInfo(getMessage(request, ctx.getLocale(), "numHojaIni.empty"));

		}



		return null;
	}



	protected String getIdTpDocDiligenciaCierre() throws ISPACException{
		return ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_DILIGENCIA_CIERRE);
	}

	protected String getIdTpDocDiligenciaApertura() throws ISPACException{
		return ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_DILIGENCIA_APERTURA);
	}

	protected String getIdTpDocLibro() throws ISPACException{
		return ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_LIBRO_DECRETOS);
	}

	protected String getFieldFecha(){
		return "SPAC_DT_DOCUMENTOS:FFIRMA";
	}
	protected String getFieldName(){
		return "SPAC_DT_DOCUMENTOS:NOMBRE";
	}
	protected String getFieldNum(){
		return "SPAC_DT_DOCUMENTOS:NUMEXP";
	}
	protected String []  getIdTpDocContenidoAdicional() throws ISPACException{return null;}

	public String getTableNameToAddQuery(){return "SPAC_EXPEDIENTES";}

	public String getTableNameToAddQueryAdicional(){return "SPAC_EXPEDIENTES";}

	// se ha de devolver el documento  sus correcciones de error ordenadas por
	protected String  getQueryContenidoAdicional(String initDate,
												String finDate ,
											    String [] idTpDocContenidoAdicional,
												String numexp)throws ISPACException{
		return null;
	}

	public abstract String getIdTpDocContenido() throws ISPACException;

	public abstract String getQueryContenido(String initDate,
											String finDate ,
											String idTpDocContenido,
											String numexp)throws Exception;


	private int [] getIds(List idsDocs){

		int [] ids = new int [idsDocs.size()];
		int i=0;
		for(i=0; i<idsDocs.size(); i++){
			ids[i]=Integer.parseInt(idsDocs.get(i).toString());
		}
		return ids;

	}

}
