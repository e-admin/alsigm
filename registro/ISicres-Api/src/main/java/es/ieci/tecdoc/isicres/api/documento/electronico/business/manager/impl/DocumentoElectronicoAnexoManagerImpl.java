package es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.contextholder.IsicresContextHolder;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoElectronicoAnexoManager;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.ConfiguracionCreateDocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;

public class DocumentoElectronicoAnexoManagerImpl implements
		DocumentoElectronicoAnexoManager {

	protected RegistroManager registroManager;
	protected DocumentoElectronicoAnexoDAO documentoElectronicoAnexoDAO;
	protected DataFieldMaxValueIncrementer documentoElectronicoAnexoIncrementer;
	protected DataFieldMaxValueIncrementer documentoElectronicoAnexoDatosFirmaIncrementer;

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoElectronicoAnexoManager#create(es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO, es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.ConfiguracionCreateDocumentoElectronicoAnexoVO)
	 */
	public DocumentoElectronicoAnexoVO create(DocumentoElectronicoAnexoVO documento,ConfiguracionCreateDocumentoElectronicoAnexoVO cfg) {

		// TODO mapear de VOs de DocumentoElectronico a documento de registro, obtener usuario de registro
		//el mapper tb seteará el nombre del documento documentoRegistro.setName(cfg.getClasificador());
		DocumentoRegistroVO documentoRegistro=null;

		documentoRegistro= new DocumentoRegistroVO();


		populateDocumentoRegistroVO(documento, cfg, documentoRegistro);

		//TODO setear el usuario en el contexto de la aplicación
		UsuarioVO usuarioRegistro=IsicresContextHolder.getContextoAplicacion().getUsuarioActual();


		IdentificadorRegistroVO idRegistro=documentoRegistro.getIdRegistro();
		//adjuntamos los documentos al registro
		documentoRegistro=registroManager.attachDocument(idRegistro, documentoRegistro, usuarioRegistro);


		//se obtienen los id para el documento y para la informacion de la firma
		Long idDocumento=documentoElectronicoAnexoIncrementer.nextLongValue();
		Long idDatosFirma=documentoElectronicoAnexoDatosFirmaIncrementer.nextLongValue();

		//seteamos el identificador del documento
		documento.getId().setId(idDocumento);

		// los documentos en isicres tienen paginas y estas paginas son los ficheros fisicos
		//queremos obtener el idPagina que se ha insertado, para eso buscamos por nombre
		String namePagina=documento.getName();
		Long idPagina=null;
		List paginasDocumento = documentoRegistro.getPaginas();
		for (Iterator iterator = paginasDocumento.iterator(); iterator.hasNext();) {
			PaginaDocumentoRegistroVO paginaIt = (PaginaDocumentoRegistroVO) iterator.next();
			if (paginaIt.getName().equals(namePagina)){
				idPagina=Long.parseLong(paginaIt.getId());
			}
		}

		documento.getId().setIdPagina(idPagina);
		documento.getDatosFirma().setIdAttachment(idDocumento);
		documento.getDatosFirma().setId(idDatosFirma);

		//si el IdAttachmentFirmado es distinto de vacio o nulo es q no es documento firma de ningun otro, es auto firmado
		//en caso contrario será seteado por el id del documento al que firma posteriormente
		if (null==documento.getDatosFirma().getIdAttachmentFirmado()){
			documento.getDatosFirma().setIdAttachmentFirmado(idDocumento);
		}
		//insertamos el documento anexo
		getDocumentoElectronicoAnexoDAO().save(documento);

		//insertamos las firmas, para ello se le indica que el id del documento firmado es el actual
		List<DocumentoElectronicoAnexoVO> firmas = documento.getFirmas();
		if (firmas!=null){
			for (Iterator iterator = firmas.iterator(); iterator.hasNext();) {
				DocumentoElectronicoAnexoVO documentoFirma = (DocumentoElectronicoAnexoVO) iterator
						.next();

				//seteamos a quien firma la firma
				documentoFirma.getDatosFirma().setIdAttachmentFirmado(documento.getId().getId());
				create(documentoFirma,cfg);
			}
		}

		return documento;
	}

	protected void populateDocumentoRegistroVO(DocumentoElectronicoAnexoVO documento,ConfiguracionCreateDocumentoElectronicoAnexoVO cfg,DocumentoRegistroVO documentoRegistro){
		IdentificadorRegistroVO idRegistro= new IdentificadorRegistroVO();
		idRegistro.setIdLibro(documento.getId().getIdLibro().toString());
		idRegistro.setIdRegistro(documento.getId().getIdRegistro().toString());

		documentoRegistro.setIdRegistro(idRegistro);
		documentoRegistro.setName(cfg.getClasificador());


		List paginas= new ArrayList<PaginaDocumentoRegistroVO>();
		PaginaDocumentoRegistroVO pagina= new PaginaDocumentoRegistroVO();
		String namePagina=documento.getName();
		pagina.setName(namePagina);

		DocumentoFisicoVO documentoFisico= new DocumentoFisicoVO();
		byte[] content=documento.getContenido().getContent();
		documentoFisico.setContent(content);
		String extension=documento.getExtension();
		documentoFisico.setExtension(extension);
		String location=documento.getContenido().getDocUID();
		documentoFisico.setLocation(location);
		documentoFisico.setExtension(extension);
		pagina.setDocumentoFisico(documentoFisico);
		paginas.add(pagina);

		documentoRegistro.setPaginas(paginas);

	}



	public DocumentoElectronicoAnexoVO retrieve(IdentificadorDocumentoElectronicoAnexoVO idDocumentoAnexo) {
		DocumentoElectronicoAnexoVO result=null;
		result=getDocumentoElectronicoAnexoDAO().get(idDocumentoAnexo);
		if (result!=null){
			List<DocumentoElectronicoAnexoVO> firmas = getDocumentoElectronicoAnexoDAO().getFirmas(result.getId().getId());
			result.setFirmas(firmas);
		}

		return result;
	}


	public DocumentoElectronicoAnexoVO getDocumentoFirmado(Long idDocumentoFirma){
		DocumentoElectronicoAnexoVO result=null;
		result=getDocumentoElectronicoAnexoDAO().getDocumentoFirmado(idDocumentoFirma);
		return result;
	}

	public List<DocumentoElectronicoAnexoVO> getDocumentosElectronicoAnexoByRegistro(
			Long idLibro, Long idRegistro) {
		return getDocumentoElectronicoAnexoDAO().getDocumentosElectronicoAnexoByRegistro(idLibro, idRegistro);
	}

	public RegistroManager getRegistroManager() {
		return registroManager;
	}

	public void setRegistroManager(RegistroManager registroManager) {
		this.registroManager = registroManager;
	}


	public DataFieldMaxValueIncrementer getDocumentoElectronicoAnexoIncrementer() {
		return documentoElectronicoAnexoIncrementer;
	}

	public void setDocumentoElectronicoAnexoIncrementer(
			DataFieldMaxValueIncrementer documentoElectronicoAnexoIncrementer) {
		this.documentoElectronicoAnexoIncrementer = documentoElectronicoAnexoIncrementer;
	}

	public DataFieldMaxValueIncrementer getDocumentoElectronicoAnexoDatosFirmaIncrementer() {
		return documentoElectronicoAnexoDatosFirmaIncrementer;
	}

	public void setDocumentoElectronicoAnexoDatosFirmaIncrementer(
			DataFieldMaxValueIncrementer documentoElectronicoAnexoDatosFirmaIncrementer) {
		this.documentoElectronicoAnexoDatosFirmaIncrementer = documentoElectronicoAnexoDatosFirmaIncrementer;
	}

	public DocumentoElectronicoAnexoDAO getDocumentoElectronicoAnexoDAO() {
		return documentoElectronicoAnexoDAO;
	}

	public void setDocumentoElectronicoAnexoDAO(
			DocumentoElectronicoAnexoDAO documentoElectronicoAnexoDAO) {
		this.documentoElectronicoAnexoDAO = documentoElectronicoAnexoDAO;
	}



}

