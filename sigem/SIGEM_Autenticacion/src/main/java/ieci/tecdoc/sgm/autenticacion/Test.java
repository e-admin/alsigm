package ieci.tecdoc.sgm.autenticacion;

import java.util.Date;

import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfoImpl;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl;

import org.apache.log4j.BasicConfigurator;

public class Test {

  public static void main(String[] args){
    try{
      /*BasicConfigurator.configure();
      SesionInfo sessionInfo = new SesionInfoImpl();
      Solicitante sender = new Solicitante();
      String sessionId = Goodies.getUniqueId();
      sessionInfo.setSessionId(sessionId);
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String fecha = sdf.format(new Date());
      sessionInfo.setLoginDate(fecha);
      sessionInfo.setHookId("HOOK_3");
      sender.setCIF("53492097P");
      sender.setName("José Antonio");
      sender.setSurName("Nogales Rincón");
      sender.setEmail("joseantonio.nogales@gmail.com");
      sender.setId("IDUSUARIO_1");
      sender.setCertificateIssuer("");
      sender.setCertificateSN("");
      sender.setInQuality("");
      sender.setSocialName("José Antonio Nogales Rincón");
      sessionInfo.setSender(sender);
      SesionManager.add(sessionInfo);
      
      SesionInfoImpl sessionRecovered = (SesionInfoImpl)SesionManager.get(sessionId);
      
      ConectorImpl hook = (ConectorImpl)SesionManager.getHook(sessionId);
      
      String hookId = SesionManager.getHookId(sessionId);
      
      Solicitante senderRecovered = SesionManager.getSender(sessionId);
      
      SesionManager.clean();
      
      //SessionManager.delete(sessionId);*/
    	
    	SesionManager.clean("000001");
    }catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
  
}
