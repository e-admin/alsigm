package ieci.tdw.ispac.api.expedients;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestExpedients {
	
	/*
	public static void initExpedient(ClientContext context) throws Exception {
		
        Expedients expedientsAPI = new Expedients(context);
    */
        
	public static void initExpedient(int idPcd) throws Exception {
    		
        Expedients expedientsAPI = new Expedients();
        
        // Lista de interesados del expediente
        List interested = new ArrayList();
        
        InterestedPerson interestedPerson1 = new InterestedPerson(null,"N", "06565893A", "José López López", "jll@hotmail.com");
        InterestedPerson interestedPerson2 = new InterestedPerson(null,"N", "09461393B", "Manuel Álvarez López", "Cervantes 5 - 2ºA", "33005", "Oviedo", "Asturias / España");
        InterestedPerson interestedPerson3 = new InterestedPerson(null,"N", "03242544C", "Luis Álvarez Gómez", "Asturias 23 - 5ºE", "33012", "Gijón", "Asturias / España", "lag@hotmail.com", InterestedPerson.IND_TELEMATIC, "999999999", "666666666");
        InterestedPerson interestedPerson4 = new InterestedPerson(null,"S", "12345678A", "Pablo Alonso García", "paglo_g_a@gmail.com");
        interested.add(interestedPerson1);
        interested.add(interestedPerson2);
        interested.add(interestedPerson3);
        interested.add(interestedPerson4);
        
        CommonData commonData = null;
        String especificDataXML = null;
        
        //Procedimiento de Quejas, Reclamaciones y Sugerencias
        //==================================================
        // Datos comunes del expediente
        if (idPcd == 3){
	        commonData = new CommonData("1", "PCD-3", "200700000001", new Date(), interested);
	        // Datos específicos del procedimiento
	        especificDataXML = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><datos_especificos><asunto_queja>Ruidos</asunto_queja><cod_organo>0001</cod_organo><descr_organo>Servicio de Atención al Ciudadano</descr_organo></datos_especificos>";
        } else 
        //Procedimiento de Subvenciones
        //==================================================
       	if (idPcd == 4){
	        commonData = new CommonData("1", "PCD-4", "200700000001", new Date(), interested);
	        // Datos específicos del procedimiento
	        especificDataXML = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><datos_especificos><tipo_subvencion>001</tipo_subvencion><resumen_proyecto>Resumen</resumen_proyecto></datos_especificos>";
        	
        }
        //Procedimiento de Obras
        //==================================================
       	else {
	        commonData = new CommonData("1", "PCD-5", "200700000001", new Date(), interested);
	        // Datos específicos del procedimiento
	        especificDataXML = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><datos_especificos><ubicacion_inmueble>Uria, 21</ubicacion_inmueble><descripcion_obras>Reforma Integral</descripcion_obras></datos_especificos>";
        }
        
        // Lista de documentos del expediente
        List documents = new ArrayList();
        
        /*
        File f1 = new File("C:\\temp\\prueba.doc");
        InputStream in1 = new FileInputStream(f1);
        Document document1 = new Document("Solicitud Registro", "Solicitud", in1, "doc", (int) f1.length());
        File f2 = new File("C:\\temp\\prueba.xls");
        InputStream in2 = new FileInputStream(f2);
        Document document2 = new Document("Justificante", "Informe", in2, "xls", (int) f2.length());
        */
        
        /* Tipo mime no permitido
        File f3 = new File("C:\\temp\\prueba.java");
        InputStream in3 = new FileInputStream(f3);
        Document document3 = new Document("Anexo a Solicitud", "Informe de Valoración", in3, "java", (int) f3.length());
        */
        
        //documents.add(document1);
        //documents.add(document2);
        
        // Iniciar el expediente
        expedientsAPI.initExpedient(commonData, especificDataXML, documents);
	}

	public static void addDocuments(String numExp) throws Exception {
		
        List documents = new ArrayList();
        
        File f1 = new File("C:\\temp\\prueba.doc");
        InputStream in1 = new FileInputStream(f1);
        Document document1 = new Document("Solicitud Registro", "Solicitud", in1, "doc", (int) f1.length());

        documents.add(document1);
        File f2 = new File("C:\\temp\\prueba.xls");
        InputStream in2 = new FileInputStream(f2);
        Document document2 = new Document("Justificante", "Informe", in2, "xls", (int) f2.length());
        documents.add(document2);

		
		Expedients expedientsAPI = new Expedients();
        boolean resultado = expedientsAPI.addDocuments(numExp, null, null, documents);
	}
	
	public static void changeAdmState(String numExp, String admState) throws Exception {
		
		Expedients expedientsAPI = new Expedients();
        boolean resultado = expedientsAPI.changeAdmState(numExp, admState);		
	}
	
	public static void moveExpedientToStage(String numExp, String stageCtId) throws Exception {
		Expedients expedientsAPI = new Expedients();
        boolean resultado = expedientsAPI.moveExpedientToStage(numExp, stageCtId);
	}
		
	
	
	
}