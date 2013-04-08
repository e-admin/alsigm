package es.ieci.webservice.tester;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.io.IOUtils;

import _0.v2.type.pfirma.cice.juntadeandalucia.Authentication;
import _0.v2.type.pfirma.cice.juntadeandalucia.DocumentType;
import _0.v2.type.pfirma.cice.juntadeandalucia.ImportanceLevel;
import _0.v2.type.pfirma.cice.juntadeandalucia.Job;
import _0.v2.type.pfirma.cice.juntadeandalucia.RemitterList;
import _0.v2.type.pfirma.cice.juntadeandalucia.Request;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLine;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLineList;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLineType;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignType;
import _0.v2.type.pfirma.cice.juntadeandalucia.Signature;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignerList;
import _0.v2.type.pfirma.cice.juntadeandalucia.User;
import _0.v2.type.pfirma.cice.juntadeandalucia.UserJob;
import _0.v2.type.pfirma.cice.juntadeandalucia.UserList;
import es.ieci.webservice.portafirma.PortafirmasMinhapWebServiceClient;

public class PortafirmasMinhapWebServiceClientTest{

	private static String USERNAME = "SIGM_01";
	private static String USERNAME2 = "76957932R";
	private static String USERNAME3 = "71882675Q";
	private static String PASSWORD = "ADMIN";
	private static String APPLICATION = "SIGM_01";
	private static String QUERY_URL = "http://10.228.75.80:8080/portafirma/servicesv2/QueryService?WSDL";
	private static String MODIFY_URL = "http://10.228.75.80:8080/portafirma/servicesv2/ModifyService?WSDL";

	/**
	 * Formato por defecto para la fecha de la firma.
	 */
	protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			"dd/MM/yyyy");


	public static void testCreateRequest() throws Exception {
		
		Authentication authentication = new Authentication();
		authentication.setUserName(USERNAME);
		authentication.setPassword(PASSWORD);
		
		PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(QUERY_URL,
				MODIFY_URL, authentication);
		
		Request request = new Request();

		// Aplicacion
		request.setApplication(APPLICATION);

		// Usuario de envio
		UserList users = portafirmasWsClient.getUsers(USERNAME);
		RemitterList remitters = new RemitterList();
		remitters.setUser(users.getUser());
		request.setRemitterList(remitters);

		// Firmantes de la linea
		SignerList signers = null;
		_0.v2.type.pfirma.cice.juntadeandalucia.Signer signer = null;
		SignLine signLine = null;

		SignLineList signLineList = new SignLineList();
		SignLine[] signLineArray = new SignLine[2];

		signers = new SignerList();
		signer = new _0.v2.type.pfirma.cice.juntadeandalucia.Signer();
		signLine = new SignLine();

		users = portafirmasWsClient.getUsers(USERNAME2);
		signer.setUserJob(users.getUser()[0]);
		signers.setSigner(new _0.v2.type.pfirma.cice.juntadeandalucia.Signer [] {signer});
		signLine.setSignerList(signers);
		signLine.setType(SignLineType.FIRMA);
		signLineArray[0] = signLine;

		signers = new SignerList();
		signer = new _0.v2.type.pfirma.cice.juntadeandalucia.Signer();
		signLine = new SignLine();
		users = portafirmasWsClient.getUsers(USERNAME3);
		signer.setUserJob(users.getUser()[0]);
		signers.setSigner(new _0.v2.type.pfirma.cice.juntadeandalucia.Signer [] {signer});
		signLine.setSignerList(signers);
		signLine.setType(SignLineType.FIRMA);
		signLineArray[1] = signLine;
		
		signLineList.setSignLine(signLineArray);

		// Linea de firma

		request.setSignLineList(signLineList);

		// Objeto
		request.setSubject("Prueba 20");

		// Asunto
		request.setText("Texto");

		// Nivel de importancia
		ImportanceLevel importanceLevel = new ImportanceLevel();
		importanceLevel.setLevelCode("4");
		request.setImportanceLevel(importanceLevel);

		request.setFentry(new GregorianCalendar());

		Calendar fExpiration = new GregorianCalendar();
		fExpiration.set(Calendar.DAY_OF_YEAR,
				fExpiration.get(Calendar.DAY_OF_YEAR) + 5);
		request.setFexpiration(fExpiration);

		// Tipo de firma
		request.setSignType(SignType.fromValue("CASCADA"));

		/*DataSource dss = new FileDataSource("c:/atxt.txt");
		DataHandler dhh = new DataHandler(dss);
		InputStream dataHandlers = dhh.getInputStream();
		byte[] arrayBytes = IOUtils.toByteArray(dataHandlers);*/
		
		// Documento a firmar
		byte[] documentBytes = "123456789".getBytes();
		_0.v2.type.pfirma.cice.juntadeandalucia.Document doc = new _0.v2.type.pfirma.cice.juntadeandalucia.Document();
		_0.v2.type.pfirma.cice.juntadeandalucia.DocumentList docList = new _0.v2.type.pfirma.cice.juntadeandalucia.DocumentList();
		docList.setDocument(new _0.v2.type.pfirma.cice.juntadeandalucia.Document[]{doc});
		doc.setSign(true);
		DocumentType docType = new DocumentType();
		docType.setIdentifier("GENERICO");
		doc.setDocumentType(docType);
		doc.setMime("text/plain");

		doc.setName("atxt.txt");
		doc.setContent(documentBytes);
		doc.setType("txt");
		request.setDocumentList(docList);

		// Creacion de la peticion
		String requestHash = portafirmasWsClient.createRequest(request);
		StringHolder holder = new StringHolder(requestHash);

		// Envio de la peticion
		portafirmasWsClient.sendRequest(holder);
		request = portafirmasWsClient.getRequest(requestHash);
		String docHash = request.getDocumentList().getDocument()[0].getIdentifier();
		System.out.println(requestHash);
		System.out.println(docHash);
	}

	public static void testReadDocumentSigns(String requestHash, String docHash) throws Exception {

		Authentication authentication = new Authentication();
		authentication.setUserName(USERNAME);
		authentication.setPassword(PASSWORD);
		
		PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(QUERY_URL,
				MODIFY_URL, authentication);
		
		byte[] bytes = portafirmasWsClient.getDocument(docHash);
		System.out.println("bytes.length: " + bytes.length);

		Signature signature = portafirmasWsClient.getSigns(docHash);
		System.out.println("signature.length: " + signature.getContent().length);

		Request request = portafirmasWsClient.getRequest(requestHash);

		List<SignDetailEntry> signDetailEntries = new ArrayList<SignDetailEntry>();
		SignLineList signLineList = request.getSignLineList();
		SignDetailEntry entry = null;
		UserJob userJob = null;
		User user = null;
		Job job = null;
		SignerList signerList = null;
		_0.v2.type.pfirma.cice.juntadeandalucia.Signer signer = null;
		for (SignLine signLine : signLineList.getSignLine()) {
			signerList = signLine.getSignerList();

			if ((signerList != null) && (signerList.getSigner() != null)
					&& (signerList.getSigner().length > 0)) {
				signer = signerList.getSigner(0);
				entry = new SignDetailEntry();
				userJob = signer.getUserJob();
				if (userJob instanceof User) {
					user = (User) userJob;
					entry.setAuthor(user.getName() 
									+ (user.getSurname1() != null ? " " + user.getSurname1(): "") 
									+ (user.getSurname2() != null ? " " + user.getSurname2() : ""));
				} else if (userJob instanceof Job) {
					job = (Job) userJob;
					entry.setAuthor(job.getDescription());
				}

				if ("FIRMADO".equals(signer.getState().getIdentifier())) {
					entry.setFirmado(true);
				} else {
					entry.setFirmado(false);
				}

				if (signer.getFstate() != null) {
					entry.setSignDate(DATE_FORMATTER.format(signer.getFstate()
							.getTime()));
				}

				entry.setIntegrity("No aplica");
				signDetailEntries.add(entry);
			}
		}

		System.out.println("signDetailEntries: " + signDetailEntries.size());
	}
	
	public static void main(String [] args){
		try {
			//testCreateRequest();
			String requestHash = "VO3LkKyykK";
			String docHash = "8LB5ax1NNP";
			testReadDocumentSigns(requestHash, docHash);
		} catch (Exception e) {
			// TODO Bloque catch auto-generado
			e.printStackTrace();
		}
	}
}
