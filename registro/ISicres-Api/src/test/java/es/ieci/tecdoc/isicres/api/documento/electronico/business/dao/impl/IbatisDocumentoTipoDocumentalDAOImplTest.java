package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl;

import junit.framework.Assert;

import org.jgroups.demos.Topology;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoTipoDocumentalSicresDAO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoTipoDocumentalSicresVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentalSicresEnumVO;

@ContextConfiguration({ "/beans/transactionTest-beans.xml",
	"/beans/datasourceTest-beans.xml",
	"/beans/ISicres-Api/documentoElectronico/dao-documentoTipoDocumentalSicres-beans.xml",
	"/beans/ISicres-Api/documentoElectronico/dao-documentoElectronico-beans.xml"
	})
public class IbatisDocumentoTipoDocumentalDAOImplTest extends
				AbstractDbUnitTransactionalJUnit4SpringContextTests{
	
	// Members
	@Autowired
	protected DocumentoTipoDocumentalSicresDAO documentoTipoDocumentalSicresDAO;

	protected DocumentoTipoDocumentalSicresVO generateDocumentoTipoDocumentalSicres(String idDocumento, TipoDocumentalSicresEnumVO idTipoDocumentalSicres){
		DocumentoTipoDocumentalSicresVO documento= new DocumentoTipoDocumentalSicresVO();
		
		IdentificadorDocumentoElectronicoAnexoVO id= new IdentificadorDocumentoElectronicoAnexoVO();
		id.setIdLibro(new Long(1));
		id.setIdRegistro(new Long(2));
		id.setIdPagina(new Long(3));
		id.setId(Long.parseLong(""+id.getIdLibro()+id.getIdRegistro()+id.getIdPagina()));
		documento.setId(id);
		documento.setIdDocumento(idDocumento);
		documento.setTipoDocumentalSicres(idTipoDocumentalSicres);
		return documento;
	}
	
	//para el correcto funcionamiento del test, en la tabla SCR_PAGETYPE debe existir una fila (0,'Sin tipo')
	@Test
	public void mainTest() {
		//MultiEntityContextHolder debe tener valor, sino falla.
		MultiEntityContextHolder.setEntity("");
		
		DocumentoTipoDocumentalSicresVO documento=
			generateDocumentoTipoDocumentalSicres("1",TipoDocumentalSicresEnumVO.SIN_TIPO);
		//guardamos el documetno
		documento= getDocumentoTipoDocumentalSicresDAO().save(documento);

		Assert.assertNotNull(documento);
		
		
		// recuperamos el documento
		DocumentoTipoDocumentalSicresVO documentoRetrieve = 
			this.getDocumentoTipoDocumentalSicresDAO().get(documento.getId());
		Assert.assertNotNull(documentoRetrieve);

		testEqual(documento, documentoRetrieve);
		
		// recuperamos el documento (metodo 2)
		documentoRetrieve = this.getDocumentoTipoDocumentalSicresDAO().get(
				documento.getId().getIdLibro(),documento.getId().getIdRegistro(),documento.getId().getIdPagina());
		Assert.assertNotNull(documentoRetrieve);

		testEqual(documento, documentoRetrieve);
		
		//recuperamos el id del documento en invesdoc
		String idDocumento = this.getDocumentoTipoDocumentalSicresDAO().getIdDocumento(documento.getId());
		Assert.assertNotNull(idDocumento);

		Assert.assertEquals(documento.getIdDocumento(), idDocumento);
		
		//recuperamos el enumerado con el tipo documental
		TipoDocumentalSicresEnumVO tipoDocumental = this.getDocumentoTipoDocumentalSicresDAO()
			.getTipoDocumentalSicres(documento.getId());
		Assert.assertNotNull(tipoDocumental);

		Assert.assertEquals(documento.getTipoDocumentalSicres().getValue(), tipoDocumental.getValue());
		
		updateTest();
	}
	
	//para el correcto funcionamiento del test, en la tabla SCR_PAGETYPE debe existir una fila (1,'Documento a compulsar')
	public void updateTest() {
		DocumentoTipoDocumentalSicresVO documento=
			generateDocumentoTipoDocumentalSicres("1",TipoDocumentalSicresEnumVO.SIN_TIPO);
		
		TipoDocumentalSicresEnumVO tipoDocumentalSicres=TipoDocumentalSicresEnumVO.DOCUMENTO_A_COMPULSAR;
		DocumentoTipoDocumentalSicresVO documentoRetrieve=this.getDocumentoTipoDocumentalSicresDAO().
			update(documento.getId(),tipoDocumentalSicres);
		
		testEqual(documentoRetrieve.getId(),documento.getId());
		Assert.assertEquals(documentoRetrieve.getTipoDocumentalSicres().getValue(),tipoDocumentalSicres.getValue());
	}
	
	
	protected void testEqual(IdentificadorDocumentoElectronicoAnexoVO identificador,IdentificadorDocumentoElectronicoAnexoVO identificadorRetrieve){
		Assert.assertEquals(identificador.getIdLibro(),identificadorRetrieve.getIdLibro());
		Assert.assertEquals(identificador.getIdRegistro(),identificadorRetrieve.getIdRegistro());
		Assert.assertEquals(identificador.getIdPagina(),identificadorRetrieve.getIdPagina());
	}
	
	protected void testEqual(DocumentoTipoDocumentalSicresVO documento,DocumentoTipoDocumentalSicresVO documentoRetrieve){
		testEqual(documento.getId(),documentoRetrieve.getId());
		Assert.assertEquals(documento.getIdDocumento(),documentoRetrieve.getIdDocumento());
		Assert.assertEquals(documento.getTipoDocumentalSicres().getValue(),documentoRetrieve.getTipoDocumentalSicres().getValue());
	}
	
	public DocumentoTipoDocumentalSicresDAO getDocumentoTipoDocumentalSicresDAO(){
		return documentoTipoDocumentalSicresDAO;
	}
	public void setDocumentoTipoDocumentalSicresDAO(
			DocumentoTipoDocumentalSicresDAO documentoTipoDocumentalSicresDAO) {
		this.documentoTipoDocumentalSicresDAO = documentoTipoDocumentalSicresDAO;
	}
}
