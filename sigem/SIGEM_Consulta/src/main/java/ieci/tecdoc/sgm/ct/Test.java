package ieci.tecdoc.sgm.ct;

import java.sql.Timestamp;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.ct.database.datatypes.ExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Expedientes;
import ieci.tecdoc.sgm.ct.database.datatypes.FicheroHitoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito;
import ieci.tecdoc.sgm.ct.database.datatypes.HitoExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.InteresadoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.NotificacionImpl;

public class Test {
	/**
	 * Programa de prueba de todas las funciones contenidas en GestorConsulta
	 * @param args Ninguno
	 */
	public static void main (String[] args){
		//GestorConsulta manager = new GestorConsulta();

		try{
			NotificacionImpl not = new NotificacionImpl();
			not.setDescripcion("Desc");
			not.setDEU("DEU_121212");
			not.setExpediente("EXP/247");
			not.setFechaNotificacion(new Timestamp(2007,9,3,12,12,12,0));
			not.setHitoId("ND7055600FA511DC8000DEEDF807A148");
			not.setNotificacionId("100");
			not.setServicioNotificacionesId("__");
			
			GestorConsulta.altaNotificacion(not, "");
			
			/*FicherosHito ficheros = new FicherosHito();
			FicheroHitoImpl f1 = new FicheroHitoImpl();
			FicheroHitoImpl f2 = new FicheroHitoImpl();
			
			f1.setGuid("N20407400F5111DCADD1E0D89B5C6AE9");
			f1.setGuidHito("N3680A50009811DC8000875A3A9D437F");
			f1.setTitulo("Documento Anexado PDF");
			
			f2.setGuid("NDF8C6300F5311DC8000EE882BD85355");
			f2.setGuidHito("N3680A50009811DC8000875A3A9D437F");
			f2.setTitulo("Documento Anexado XML");
			
			ficheros.add(f1);
			ficheros.add(f2);
			
			GestorConsulta.anexarFicherosHitoActual(ficheros);*/
			
	    	/*String numero = "" + (int)(Math.random() * 10000);*/
	    	
/*	    	ExpedienteImpl expediente = new ExpedienteImpl();
	    	expediente.setNumero(numero);
	    	expediente.setProcedimiento("handemor");
	    	expediente.setFechaInicio("2007-02-02");
	    	expediente.setAportacion("S");
	    	expediente.setCodigoPresentacion("S");
	    	expediente.setEstado(ExpedienteImpl.COD_ESTADO_EXPEDIENTE_FINALIZADO);
	    	
	    	InteresadoImpl interesado = new InteresadoImpl();
	    	interesado.setNIF("123A");
	    	interesado.setNumeroExpediente(expediente.getNumero());
	    	interesado.setPrincipal("S");
	    	interesado.setNombre("Manfredo Perugote");
	    	interesado.setInformacionAuxiliar("informacionAuxiliar");
	    	
	    	GestorConsulta.nuevoExpediente(expediente, interesado);

	    	GestorConsulta.actualizarEstado(numero, "4");*/
	    	
	    	/*Expedientes exp = GestorConsulta.busquedaExpedientes("", "123A", "01-02-2007", "03-02-2007", "entre", "");
	    	for(int i=0; i<exp.size(); i++)
	    		System.out.println(((ExpedienteImpl)exp.get(i)).toString());*/
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		/***************** OBTENER DETALLE DE UN EXPEDIENTE ***************/
		String numeroExpediente = "1"; 
		/*try{
    	GestorConsulta.obtenerDetalle("---------", numeroExpediente);

    }catch (IncorrectGuidException e){
      e.printStackTrace();
    }catch (DocumentsRepositoryException e){
      e.printStackTrace();
    }*/
		
		/*************** FIN CONSULTAR LOS EXPEDIENTES DE UN INTERESADO *************/
		/***************** OBTENER HITOS DEL HISTORICO DE UN EXPEDIENTE ***************/
		/*try{
			GestorConsulta.obtenerHistoricoExpediente("---------", numeroExpediente); 
		}catch (Exception e){
			e.printStackTrace();
		}*/
		
		/*************** FIN OBTENER HITOS DEL HISTORICO DE UN EXPEDIENTE *************/
		
		
		/*************** GENERAR UN NUEVO EXPEDIENTE *************/
		/*try{
    	
	    	ExpedienteImpl expediente = new ExpedienteImpl();
	    	expediente.setNumero("" + (int)(Math.random() * 10000));
	    	//expediente.setNumero("9665");
	    	expediente.setProcedimiento("handemor");
	    	expediente.setFechaInicio("2007-02-02");
	    	expediente.setAportacion("S");
	    	expediente.setCodigoPresentacion("S");
	    	InteresadoImpl interesado = new InteresadoImpl();
	    	//interesado.setNIF("" + (int)(Math.random() * 10000) + "A");
	    	interesado.setNIF("123A");
	    	interesado.setNumeroExpediente(expediente.getNumero());
	    	interesado.setPrincipal("S");
	    	interesado.setNombre("Manfredo Perugote");
	    	interesado.setInformacionAuxiliar("informacionAuxiliar");
	    	GestorConsulta.nuevoExpediente(expediente, interesado);

		}catch (Exception e){
			e.printStackTrace();
		}*/
		/*************** FIN GENERAR UN NUEVO EXPEDIENTE *************/

		/***************** ESTABLECER HITO ACTUAL ***************/
		
		/*try{
    	
    	HitoExpedienteImpl hitoExpediente = new HitoExpedienteImpl();
    	hitoExpediente.setNumeroExpediente("2");
    	hitoExpediente.setGuid(new Guid().toString());
    	hitoExpediente.setFecha("2007-02-02");
    	hitoExpediente.setDescripcion("este es hito de nueva carga");
    	FicherosHito ficheros = new FicherosHito(); 
    	for(int a = 0; a < 3; a++) {
	    	FicheroHitoImpl fichero = new FicheroHitoImpl();
	    	fichero.setGuid(new Guid().toString());
	    	fichero.setTitulo("peazo documentooo");
	    	fichero.setGuidHito(hitoExpediente.getGuid());
	    	ficheros.add(fichero);
    	}
    	GestorConsulta.establecerHitoActual(hitoExpediente,ficheros,true);

		} catch (Exception e){
			e.printStackTrace();
		}*/
		/*try {
			ExpedienteImpl expediente = new ExpedienteImpl();
	    	expediente.setNumero("22");
			GestorConsulta.eliminarExpediente(expediente);
		} catch (Exception e){
			e.printStackTrace();
		}*/
		
		
	}
}
