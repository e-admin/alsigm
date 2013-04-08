package ieci.tecdoc.sgm.registro;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.PeticionDocumento;
import ieci.tecdoc.sgm.registro.util.PeticionDocumentos;
import ieci.tecdoc.sgm.registro.util.RegistroConsulta;
import ieci.tecdoc.sgm.registro.util.RegistroImpl;
import ieci.tecdoc.sgm.registro.util.RegistroPeticion;
import ieci.tecdoc.sgm.registro.util.Registros;

import org.apache.log4j.BasicConfigurator;

public class Test
{
  public static void main (String[] args){
	  Entidad entidad = new Entidad();
	  entidad.setIdentificador("0001");
    //CREACION DE LA PETICION
    RegistroPeticion requestInfo = new RegistroPeticion();
    requestInfo.setProcedureId("TRAM_3");
    requestInfo.setAddressee("0002");
    requestInfo.setEmail("ja.nogales@planetmedia.es");
    requestInfo.setFolderId("");
    requestInfo.setSpecificData("");
    requestInfo.setSenderIdType("");
    
    PeticionDocumento aux1 = new PeticionDocumento();
    aux1.setCode("CP2");
    aux1.setExtension("doc");
    aux1.setFileName("m");
    aux1.setLocation("d:\\m.doc");
    PeticionDocumento aux2 = new PeticionDocumento();
    aux2.setCode("CP_PDF_2");
    aux2.setExtension("pdf");
    aux2.setFileName("p");
    aux2.setLocation("d:\\p.pdf");
    PeticionDocumento aux3 = new PeticionDocumento();
    aux3.setCode("CP_PDF_2");
    aux3.setExtension("pdf");
    aux3.setFileName("p");
    aux3.setLocation("d:\\p.pdf");
    PeticionDocumentos documents = new PeticionDocumentos();
    documents.add(aux1);
    documents.add(aux2);
    //documents.add(aux3);
    //requestInfo.setDocuments(documents);
    requestInfo.setDocuments(null);
    try{
      BasicConfigurator.configure();
      String sessionId = "660EACB25571635935AF650323A846A0";
      byte[] xml = RegistroManager.createRegistryRequest(sessionId, requestInfo, "es", "Ayuntamiento de Madrid","", entidad.getIdentificador());
      System.out.println("createRegistryRequest: " + new String(xml));
      
      byte[] xml1 = RegistroManager.register(sessionId, xml, "", "ESP","", "", "", entidad.getIdentificador());
      System.out.println("register: " + new String(xml1));
      
      RegistroConsulta query = new RegistroConsulta();
      query.setLastRegistryDate("2007-04-23");
      Registros regs = RegistroManager.query(sessionId, query, entidad.getIdentificador());
      if(regs != null){
        for(int i=0;i<regs.count(); i++){
          System.out.println("Query result #" + (i+1) + ": " + ((RegistroImpl)regs.get(i)).toString());
        }
      }
      
      RegistroImpl reg = (RegistroImpl)RegistroManager.getRegistry(sessionId, "2007000000000200", entidad.getIdentificador());
      
      /*ContenedorDocumentos datadocuments = RegistroManager.getRegistryDocumentsData("2007000000000200");
      if(datadocuments != null){
        for(int i=0;i<datadocuments.count(); i++){
          ContenedorDocumentoDatos dd = (ContenedorDocumentoDatos)datadocuments.get(i);
          System.out.println("getRegistryDocuments #" + (i+1) + ": " + dd.toString());
          ContenedorDocumentosManager dm = new ContenedorDocumentosManager();
          dm.retrieveDocument(sessionId, dd.getGuid(), "c:\\");
        }
      }*/
      
      System.out.println("hasDocuments: " + RegistroManager.hasDocuments("2007000000000200", entidad.getIdentificador()));
      
      System.out.println("getRegistryRequest: " + new String(RegistroManager.getRegistryRequest("2007000000000200", entidad.getIdentificador())));
    }catch(RegistroExcepcion e){
      System.out.println("Se ha producido una exception: " + e.getMessage());
    }catch(Exception e){
      System.out.println("Se ha producido una exception: " + e.getMessage());
    }
  }
}
