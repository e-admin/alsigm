package es.ieci.plusvalias.util;

import ieci.tecdoc.sgm.pe.ws.client.Liquidacion;
import ieci.tecdoc.sgm.pe.ws.client.Tasa;
import ieci.tecdoc.sgm.rde.database.ContenedorDocumentoDatos;
import ieci.tecdoc.sgm.registro.util.RegistroDocumento;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentos;
import ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType.PARAMETROSPAGOType.DATOSBANCARIOSType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType.REPRESENTANTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.SujetoType;

import es.ieci.plusvalias.api.DatosBancarios;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Sujeto;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.sigem.RegistroTelematico;

public class PagoPlusvaliaHelper{
	public static Logger logger = Logger.getLogger(PagoPlusvaliaHelper.class);
	
	public Sujeto getSujeto(SujetoType sujetoRequest) throws Exception
	{
		Sujeto sujeto = new Sujeto();

		sujeto.setNif(sujetoRequest.getDATOSPERSONALES().getNUMERODOCUMENTO());
		sujeto.setNombre(sujetoRequest.getDATOSPERSONALES().getNOMBRE());
		sujeto.setApellido1(sujetoRequest.getDATOSPERSONALES().getAPELLIDO1RAZONSOCIAL());
		sujeto.setApellido2(sujetoRequest.getDATOSPERSONALES().getAPELLIDO2());
		sujeto.setNombreCompleto(sujeto.getApellido1() + " " + sujeto.getApellido2() + ", " + sujeto.getNombre());
		sujeto.setCp(sujetoRequest.getDOMICILIO().getCODIGOPOSTAL());

		String calleTransmitiente = sujetoRequest.getDOMICILIO().getTIPOVIA() + " " + sujetoRequest.getDOMICILIO().getVIA() + " " + sujetoRequest.getDOMICILIO().getNUMEROVIA()
				+ " " + sujetoRequest.getDOMICILIO().getBLOQUE() + " " + sujetoRequest.getDOMICILIO().getPLANTA() + " " + sujetoRequest.getDOMICILIO().getPUERTA() + " "
				+ sujetoRequest.getDOMICILIO().getRESTO();

		sujeto.setCalle(calleTransmitiente.replaceAll("null", "").trim());

		return sujeto;
	}
	
	public Sujeto[] getSujetos(REPRESENTANTESType representantes) throws Exception{
		ArrayList<Sujeto> sujetos = new ArrayList<Sujeto>();

		if (representantes != null)	{
			List sujetosRequest = representantes.getREPRESENTANTE();
			Iterator iterator = sujetosRequest.iterator();

			while (iterator.hasNext())
			{
				sujetos.add(getSujeto((SujetoType) iterator.next()));
			}
		}

		return sujetos.toArray(new Sujeto[sujetos.size()]);
	}
	
	public DatosBancarios getDatosBancarios(DATOSBANCARIOSType tipoDatosBancarios){
		DatosBancarios datosBancarios=new DatosBancarios();
		datosBancarios.setDc(tipoDatosBancarios.getDC());
		datosBancarios.setEntidad(tipoDatosBancarios.getENTIDAD());
		datosBancarios.setNifTitular(tipoDatosBancarios.getNIFTITULAR());
		datosBancarios.setNombreTitular(tipoDatosBancarios.getNOMBRETITULAR());
		datosBancarios.setNumCuenta(tipoDatosBancarios.getCUENTA());
		datosBancarios.setOficina(tipoDatosBancarios.getOFICINA());
		return datosBancarios;
	}
}