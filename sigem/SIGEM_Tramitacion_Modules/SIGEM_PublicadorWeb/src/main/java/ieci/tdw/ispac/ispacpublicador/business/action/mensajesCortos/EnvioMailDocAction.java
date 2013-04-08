package ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;


/**
 *
 * Clase que ejecuta la accion de enviar email. La regla que tenga asociada esta acción en el campo xml de attributos deberá tener un xml
 * semejante al que se indica a continuación
 *


 *
 */
public class EnvioMailDocAction extends SigemBaseAction{



	/** Logger de la clase. */
    private static final Logger logger =
    	Logger.getLogger(EnvioMailDocAction.class);

    /** Contexto del cliente. */
    public ClientContext clientContext = null;


    /**
     * Constructor
     */
    public EnvioMailDocAction() {
    	super();
    	clientContext = new ClientContext();
    	clientContext.setAPI(new InvesflowAPI(clientContext));
    }


	public boolean execute(RuleContext rulectx, AttributeContext attctx)
			throws ActionException {


		  if (logger.isInfoEnabled()) {
	            logger.info("Acción [" + this.getClass().getName() + "] en ejecución");
	        }

		  try {
			IGenDocAPI genDocAPI= clientContext.getAPI().getGenDocAPI();
			String from = (String)rulectx.getProperties().get("sender");
			String subject = (String)rulectx.getProperties().get("subject");
			String content = (String)rulectx.getProperties().get("content");

			//Obtener la lista de documentos a adjuntar
			String documentos=(String) rulectx.getProperties().get("documents");
			documentos=StringUtils.unescapeXml(documentos);
			XmlFacade xml = new XmlFacade(documentos);
			NodeIterator documentNodeIt = xml
			.getNodeIterator("/documents/document");
			//Obtengo en documento
			Object connectorSession = genDocAPI.createConnectorSession();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();


			ArrayList attachments= new ArrayList();

			for (Node documentNode = documentNodeIt.nextNode(); documentNode != null; documentNode = documentNodeIt
			.nextNode()) {
				String infopagRde =  XmlFacade.getNodeValue(documentNode);
				infopagRde= StringUtils.substringBetween(infopagRde, "<infopagRde>", "</infopagRde>");
				String nameDocument=XmlFacade.get(documentNode, "nameDocument");

				genDocAPI.getDocument(connectorSession, infopagRde, baos);
				Attachment attachment=new Attachment(nameDocument,baos.toByteArray());
				attachments.add(attachment);


			}
			//Obtengo la lista de destinatarios
			String destinatarios=(String) rulectx.getProperties().get("addressees");
			destinatarios=StringUtils.unescapeXml(destinatarios);
			xml = new XmlFacade(destinatarios);
			NodeIterator addrNodeIt = xml
			.getNodeIterator("/addressees/addressee");
			ArrayList listaDestinatarios=new ArrayList();
			for (Node addrNode = addrNodeIt.nextNode(); addrNode != null; addrNode = addrNodeIt
			.nextNode()) {

				String obj=(XmlFacade.getNodeValue(addrNode));
				listaDestinatarios.add(obj);
			}

			String [] arrayDestinatarios = new String[listaDestinatarios.size()];
			int i=0;
			for(i=0; i<listaDestinatarios.size(); i++){
				arrayDestinatarios[i]=(String) listaDestinatarios.get(i);
			}
			Attachment [] arrayDocs = new Attachment[attachments.size()];
			attachments.toArray(arrayDocs);
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			servicio.sendMail(from, arrayDestinatarios, null,null, subject, content, arrayDocs);
		} catch (SigemException e) {
			setInfo("Error en el envio de email"+ e.toString());
            throw new ActionException(e);
		} catch (ISPACException e) {
			setInfo("Error al obtener el documento firmado para enviar por email : " + e.toString());
            throw new ActionException(e);
		}
		return true;
	}








}
