package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.ieci.tecdoc.idoc.utils.Base64Util;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoDatosFirmaVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentoAnexoEnumVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoValidezDocumentoAnexoEnumVO;

/**
*
* @author IECISA
*
*/


@ContextConfiguration({ "/beans/transactionTest-beans.xml",
		"/beans/datasourceTest-beans.xml",
		"/beans/ISicres-Api/documentoElectronico/dao-documentoElectronico-beans.xml"
		})
public class IbatisDocumentoElectronicoAnexoDAOImplTest extends
AbstractDbUnitTransactionalJUnit4SpringContextTests{

	// Members
	@Autowired
	protected DocumentoElectronicoAnexoDAO documentoElectronicoAnexoDAO;


	protected DocumentoElectronicoAnexoVO generateDocumento(Long idDocumento,Long idDatosFirma,Long idDocumentoAFirmar){
		DocumentoElectronicoAnexoVO documento=null;
		documento= new DocumentoElectronicoAnexoVO();

		IdentificadorDocumentoElectronicoAnexoVO id= new IdentificadorDocumentoElectronicoAnexoVO();
		id.setId(idDocumento);
		id.setIdLibro(new Long(1));
		id.setIdRegistro(new Long(2));
		id.setIdPagina(new Long(3));
		documento.setId(id);

		documento.setCodeName("codeName"+idDocumento);

		documento.setComentario("comentario"+idDocumento);

		documento.setMimeType("tipoMime"+idDocumento);

		documento.setName("nombre"+idDocumento);

		documento.setTipoDocumentoAnexo(TipoDocumentoAnexoEnumVO.FICHERO_TECNICO);

		documento.setTipoValidez(TipoValidezDocumentoAnexoEnumVO.ORIGINAL);

		DocumentoElectronicoAnexoDatosFirmaVO datosFirma= new DocumentoElectronicoAnexoDatosFirmaVO();
		datosFirma.setAlgFirma("A"+idDatosFirma);
		datosFirma.setCertificado("certificado"+idDatosFirma);
		datosFirma.setFirma("Firma"+idDatosFirma);
		datosFirma.setFormatoFirma("FF");
		datosFirma.setHash("hash"+idDatosFirma);
		datosFirma.setHashAlg("H"+idDatosFirma);
		datosFirma.setId(idDatosFirma);
		datosFirma.setIdAttachment(idDocumento);
		datosFirma.setIdAttachmentFirmado(idDocumentoAFirmar);
		datosFirma.setOcspValidation("ocspValidation");
		datosFirma.setSelloTiempo("selloTiempo");
		documento.setDatosFirma(datosFirma);

		return documento;
	}


	@Test
	public void testSaveGet() {
		//MultiEntityContextHolder debe tener valor, sino falla.
		MultiEntityContextHolder.setEntity("");
		Long idDocumentoElectronicoAnexo=new Long(1);
		DocumentoElectronicoAnexoVO documento=generateDocumento(idDocumentoElectronicoAnexo,new Long(2),idDocumentoElectronicoAnexo);
		//guardamos el documetno
		documento= getDocumentoElectronicoAnexoDAO().save(documento);
		Assert.assertNotNull(documento);

		// recuperamos el documetno
		DocumentoElectronicoAnexoVO documentoRetrieve = this.getDocumentoElectronicoAnexoDAO().get(new Long(1));
		Assert.assertNotNull(documentoRetrieve);

		testEqual(documento, documentoRetrieve);

	}


	@Test
	public void testSaveGetIdentificadorDocumentoElectronicoAnexo() {

		Long idDocumentoElectronicoAnexo=new Long(1);
		DocumentoElectronicoAnexoVO documento=generateDocumento(idDocumentoElectronicoAnexo,new Long(2),idDocumentoElectronicoAnexo);
		documento= getDocumentoElectronicoAnexoDAO().save(documento);

		Long idLibro=new Long(1);
		Long idRegistro= new Long(2);
		Long idPagina= new Long(3);

		IdentificadorDocumentoElectronicoAnexoVO id= new IdentificadorDocumentoElectronicoAnexoVO();
		id.setId(new Long(8));
		id.setIdLibro(idLibro);
		id.setIdRegistro(idRegistro);
		id.setIdPagina(idPagina);

		//guardamos el documento

		Assert.assertNotNull(documento);

		//select
		id= new IdentificadorDocumentoElectronicoAnexoVO();
		id.setId(null);
		id.setIdLibro(idLibro);
		id.setIdRegistro(idRegistro);
		id.setIdPagina(idPagina);

		DocumentoElectronicoAnexoVO documentoRetrieve = this.getDocumentoElectronicoAnexoDAO().get(id);

		testEqual(documento, documentoRetrieve);

	}

	@Test
	public void getDatosFirmaByIdDocumentoElectronicoTest(){

		//Long idDocumentoElectronicoAnexo=new Long(60);
		//DocumentoElectronicoAnexoVO documento=generateDocumento(idDocumentoElectronicoAnexo,new Long(2),idDocumentoElectronicoAnexo);
		//documento= getDocumentoElectronicoAnexoDAO().save(documento);

		Long idDocumentoElectronicoAnexo=new Long(60);

		DocumentoElectronicoAnexoDatosFirmaVO datosFirmaRetrieve = getDocumentoElectronicoAnexoDAO().getDatosFirmaByIdDocumentoElectronico(idDocumentoElectronicoAnexo);

		//testEqual(documento.getDatosFirma(),datosFirmaRetrieve);
		Assert.assertNotNull(datosFirmaRetrieve);
	}

	@Test
	public void base64Text(){
		String cadena="holaMundo";
		byte[] cadenaByte = cadena.getBytes();
		//--------------

		String cadena64;
		cadena64 = Base64.encodeBase64String(cadenaByte);
		//Base64.

		try {
			//byte[] tal = Base64Util.decode(cadena);

			byte[] tal = Base64.decodeBase64(cadena);
			String result = Base64.encodeBase64String(tal);
			System.out.println(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	public void getDatosFirmaByIdTest(){

		Long idDocumentoElectronicoAnexo=new Long(1);
		Long idDatosFirma=new Long(2);
		DocumentoElectronicoAnexoVO documento=generateDocumento(idDocumentoElectronicoAnexo,idDatosFirma,idDocumentoElectronicoAnexo);
		documento= getDocumentoElectronicoAnexoDAO().save(documento);

		DocumentoElectronicoAnexoDatosFirmaVO datosFirmaRetrieve = getDocumentoElectronicoAnexoDAO().getDatosFirmaById(idDatosFirma);
		Assert.assertNotNull(documento.getDatosFirma());
		Assert.assertNotNull(datosFirmaRetrieve);
		testEqual(documento.getDatosFirma(),datosFirmaRetrieve);
	}

	@Test
	public void getFirmasIdTest(){

		Long idDocumentoElectronicoAnexo=new Long(1);
		Long idDatosFirma=new Long(2);

		DocumentoElectronicoAnexoVO documento=generateDocumento(idDocumentoElectronicoAnexo,idDatosFirma,idDocumentoElectronicoAnexo);
		documento= getDocumentoElectronicoAnexoDAO().save(documento);

		for (int i=1;i<7;i++){

			documento=generateDocumento(idDocumentoElectronicoAnexo+Long.valueOf(i),idDatosFirma+Long.valueOf(i),idDocumentoElectronicoAnexo);
			documento= getDocumentoElectronicoAnexoDAO().save(documento);

			List<DocumentoElectronicoAnexoVO> result = getDocumentoElectronicoAnexoDAO().getFirmas(idDocumentoElectronicoAnexo);
			Assert.assertEquals(i, result.size());
		}

	}

	@Test
	public void getDocumentoElectronicoAnexoByRegistroTest(){
		Long idDocumentoElectronicoAnexo=new Long(1);
		Long idDatosFirma=new Long(2);

		DocumentoElectronicoAnexoVO documento=generateDocumento(idDocumentoElectronicoAnexo,idDatosFirma,idDocumentoElectronicoAnexo);
		documento= getDocumentoElectronicoAnexoDAO().save(documento);

		for (int i=1;i<7;i++){

			documento=generateDocumento(idDocumentoElectronicoAnexo+Long.valueOf(i),idDatosFirma+Long.valueOf(i),idDocumentoElectronicoAnexo);
			documento= getDocumentoElectronicoAnexoDAO().save(documento);

			List<DocumentoElectronicoAnexoVO> result = getDocumentoElectronicoAnexoDAO().getDocumentosElectronicoAnexoByRegistro(new Long(1), new Long(2));
			//+1 del documento origen
			Assert.assertEquals(i+1, result.size());

		}
	}

	@Test
	public void getIdentificadorDocumentoElectronicoAnexoByRegistroTest(){
		Long idDocumentoElectronicoAnexo=new Long(1);
		Long idDatosFirma=new Long(2);

		DocumentoElectronicoAnexoVO documento=generateDocumento(idDocumentoElectronicoAnexo,idDatosFirma,idDocumentoElectronicoAnexo);
		documento= getDocumentoElectronicoAnexoDAO().save(documento);

		for (int i=1;i<7;i++){

			documento=generateDocumento(idDocumentoElectronicoAnexo+Long.valueOf(i),idDatosFirma+Long.valueOf(i),idDocumentoElectronicoAnexo);
			documento= getDocumentoElectronicoAnexoDAO().save(documento);

			List<IdentificadorDocumentoElectronicoAnexoVO> result = getDocumentoElectronicoAnexoDAO().getIdentificadoresDocumentoElectronicoAnexoByRegistro(new Long(1), new Long(2));
			//+1 del documento origen
			Assert.assertEquals(i+1, result.size());

		}
	}

	@Test
	public void getDocumentoFirmadoTest(){
		Long idDocumentoElectronicoAnexo=new Long(1);
		Long idDatosFirma=new Long(2);
		DocumentoElectronicoAnexoVO documentoOrigen=generateDocumento(idDocumentoElectronicoAnexo,idDatosFirma,idDocumentoElectronicoAnexo);
		documentoOrigen= getDocumentoElectronicoAnexoDAO().save(documentoOrigen);

		//insertamos al documento anterior firmas
		for (int i=1;i<7;i++){

			DocumentoElectronicoAnexoVO documento=generateDocumento(documentoOrigen.getId().getId()+Long.valueOf(i),idDatosFirma+Long.valueOf(i),documentoOrigen.getId().getId());
			documento= getDocumentoElectronicoAnexoDAO().save(documento);

			List<IdentificadorDocumentoElectronicoAnexoVO> result = getDocumentoElectronicoAnexoDAO().getIdentificadoresDocumentoElectronicoAnexoByRegistro(new Long(1), new Long(2));
			//+1 del documento origen
			Assert.assertEquals(i+1, result.size());

			//obtenemos el fichero al que firma que tiene q ser el mismo que el origen
			DocumentoElectronicoAnexoVO documentoFirmado = getDocumentoElectronicoAnexoDAO().getDocumentoFirmado(documento.getId().getId());

			testEqual(documentoOrigen, documentoFirmado);

		}

	}




	protected void testEqual(DocumentoElectronicoAnexoVO documento,DocumentoElectronicoAnexoVO documentoRetrieve){

		Assert.assertEquals(documento.getId().getId(), documentoRetrieve.getId().getId());
		Assert.assertEquals(documento.getId().getIdLibro(), documentoRetrieve.getId().getIdLibro());
		Assert.assertEquals(documento.getId().getIdRegistro(), documentoRetrieve.getId().getIdRegistro());
		Assert.assertEquals(documento.getId().getIdPagina(), documentoRetrieve.getId().getIdPagina());
		Assert.assertEquals(documento.getCodeName(), documentoRetrieve.getCodeName());
		Assert.assertEquals(documento.getComentario(), documentoRetrieve.getComentario());
		Assert.assertEquals(documento.getMimeType(), documentoRetrieve.getMimeType());
		Assert.assertEquals(documento.getName(), documentoRetrieve.getName());
		Assert.assertEquals(documento.getTipoValidez(), documentoRetrieve.getTipoValidez());
		Assert.assertEquals(documento.getTipoDocumentoAnexo(), documentoRetrieve.getTipoDocumentoAnexo());

		testEqual(documento.getDatosFirma(),documentoRetrieve.getDatosFirma());
	}



	protected void testEqual(DocumentoElectronicoAnexoDatosFirmaVO datosFirma,DocumentoElectronicoAnexoDatosFirmaVO datosFirmaRetrieve){
		Assert.assertEquals(datosFirma.getAlgFirma(),datosFirmaRetrieve.getAlgFirma());
		Assert.assertEquals(datosFirma.getCertificado(),datosFirmaRetrieve.getCertificado());
		Assert.assertEquals(datosFirma.getFirma(),datosFirmaRetrieve.getFirma());
		Assert.assertEquals(datosFirma.getFormatoFirma(),datosFirmaRetrieve.getFormatoFirma());
		Assert.assertEquals(datosFirma.getHash(),datosFirmaRetrieve.getHash());
		Assert.assertEquals(datosFirma.getHashAlg(),datosFirmaRetrieve.getHashAlg());
		Assert.assertEquals(datosFirma.getOcspValidation(),datosFirmaRetrieve.getOcspValidation());
		Assert.assertEquals(datosFirma.getSelloTiempo(),datosFirmaRetrieve.getSelloTiempo());
		Assert.assertEquals(datosFirma.getId(),datosFirmaRetrieve.getId());
		Assert.assertEquals(datosFirma.getIdAttachment(),datosFirmaRetrieve.getIdAttachment());
		Assert.assertEquals(datosFirma.getIdAttachmentFirmado(),datosFirmaRetrieve.getIdAttachmentFirmado());
	}


	public DocumentoElectronicoAnexoDAO getDocumentoElectronicoAnexoDAO() {
		return documentoElectronicoAnexoDAO;
	}

	public void setDocumentoElectronicoAnexoDAO(
			DocumentoElectronicoAnexoDAO documentoElectronicoAnexoDAO) {
		this.documentoElectronicoAnexoDAO = documentoElectronicoAnexoDAO;
	}




	/**
	 * metodo prueba utilidad de ejemplo de crear dataset
	 * @param pathDataSet
	 * @throws IOException
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 */
	protected void insertDataSet(String pathDataSet) throws IOException, DatabaseUnitException, SQLException{
		Resource dataSetResource = new ClassPathResource(pathDataSet);
		FlatXmlDataSetBuilder dsBuilder = new FlatXmlDataSetBuilder();
	    IDataSet dataSet = dsBuilder.build(dataSetResource.getInputStream());
	    IDataSet rds = new ReplacementDataSet(dataSet);
	    ((ReplacementDataSet)rds).addReplacementObject("[NULL]", null);
	    dataSet=rds;
	    DataSource dataSource= (DataSource) this.applicationContext.getBean("datasourceDbUnit");
		IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
	    DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}

	/**
	 * metodo prueba utilidad de ejemplo de crear dataset
	 */
	protected void generateDataSet()
	  {

		try{
			DataSource dataSource= (DataSource) this.applicationContext.getBean("datasourceDbUnit");
			IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());

		    QueryDataSet dataSet = new QueryDataSet(connection);
		    dataSet.addTable("SCR_ATTACHMENT", "SELECT * FROM SCR_ATTACHMENT");
		    dataSet.addTable("SCR_ATTACHMENT_SIGN_INFO", "SELECT * FROM SCR_ATTACHMENT_SIGN_INFO");

		    FlatXmlDataSet.write(dataSet, new FileOutputStream("c:\\temp\\dataSet.xml"));
			}catch (Exception ex){
				System.out.println("MAAAAAAAAAAAAAAAAAALLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
			}


	  }

}
