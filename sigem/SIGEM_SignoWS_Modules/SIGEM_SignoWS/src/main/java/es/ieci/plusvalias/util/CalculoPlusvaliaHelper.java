package es.ieci.plusvalias.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.AdquirenteType;
import org.notariado.ancert.xml.plusvalias.liquidacion.NotarioType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType.BONIFICACIONESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType.TRANSMISIONESANTERIORESType.TRANSMISIONType;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.Transmitente;

public class CalculoPlusvaliaHelper
{
	public static Logger logger = Logger.getLogger(CalculoPlusvaliaHelper.class);

//	public boolean eliminarExistente(String refCatastral, Transmitente[] transmitentes, Adquiriente[] adquirientes, String tipoAncert,
//			int claseDerecho, MessageSource messageSource)
//	{
//		boolean ok = true;
//
//		for (int i = 0; i < transmitentes.length; i++)
//		{
//			for (int j = 0; j < adquirientes.length; j++)
//			{
//				if (eliminarExistente(refCatastral, transmitentes[i].getNif(), adquirientes[j].getNif(), tipoAncert, claseDerecho, messageSource)
//						== false)
//				{
//					return false;
//				}
//			}
//		}
//
//		return ok;
//	}

	/**
	 * Elimina la plusvalia registrada, anula el registro, archiva el expediente y elimina el pago telemático
	 *
	 * @param refCatastral
	 * @param nifTrans
	 * @param nifAdq
	 * @param tipoAncert
	 * @return
	 */
//	public boolean eliminarExistente(String refCatastral, String nifTrans, String nifAdq, String tipoAncert, int claseDerecho,
//			MessageSource messageSource)
//	{
//		boolean ok = false;
//
//		try
//		{
//			PlusvaliaTempDTO plusvaliaDTO = getPlusvalia(refCatastral, nifTrans, nifAdq, tipoAncert, claseDerecho);
//
//			if (plusvaliaDTO != null)
//			{
//				PlusvaliaTempDTO plusvaliaTempDto = plusvaliatempdao.find(plusvaliaDTO.getRefcatastral(), plusvaliaDTO.getNifTrans(),
//						plusvaliaDTO.getNifAdq(), plusvaliaDTO.getClaseDerecho());
//
//				if (plusvaliaTempDto != null)
//				{
//					String idEntidad = messageSource.getMessage("sigem.entidad", null, null);
//					String pagoEndpoint = messageSource.getMessage("sigem.pago.endpoint", null, null);
//					String tramitacionEndpoint = messageSource.getMessage("sigem.tramitacion.endpoint", null, null);
//					String idFaseArchivo = messageSource.getMessage("sigem.tramitacion.idFaseArchivo", null, null);
//
//					PagoTelematico pt = new PagoTelematico(pagoEndpoint);
//
//					Liquidacion liquidacion = pt.buscarLiquidacion(idEntidad, plusvaliaDTO.getReferenciaPago());
//
//					if (liquidacion != null)
//					{
//						if (liquidacion.getEstado().equals(PagoTelematico.ESTADO_PAGADO))
//						{
//							throw new PlusvaliaException(ErrorCode.PLUSVALIA_PAID);
//						}
//						else
//						{
//							pt.bajaLiquidacion(idEntidad, plusvaliaDTO.getReferenciaPago());
//						}
//					}
//
//					Tramitacion t = new Tramitacion(tramitacionEndpoint);
//					t.moverExpedienteAFase(idEntidad, plusvaliaDTO.getNumExpediente(), idFaseArchivo);
//
//					RegistroTelematico rt = new RegistroTelematico(idEntidad);
//					rt.eliminaRegistro(plusvaliaDTO.getFolderIdRegistro());
//
//					// Eliminamos la plusvalia
//					plusvaliatempdao.delete(plusvaliaTempDto);
//				}
//
//				ok = true;
//			}
//
//
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//
//			ok = false;
//		}
//
//		return ok;
//	}

	/**
	 * Devuelve la lista de adquirientes
	 *
	 * @param adquirientesRequest
	 * @return
	 * @throws Exception
	 */
	public Adquiriente[] getAdquirientes(List adquirientesRequest) throws Exception
	{
		ArrayList<Adquiriente> adquirientes = new ArrayList<Adquiriente>();

		for (Iterator iterator = adquirientesRequest.iterator(); iterator.hasNext();)
		{
			AdquirenteType adquirienteRequest = (AdquirenteType)iterator.next();

			Adquiriente adquiriente = new Adquiriente();
			adquiriente.setIdentificador("" + adquirienteRequest.getDATOSBASICOS().getIDENTIFICADOR());
			adquiriente.setCuotaParticipacion(NumberConversionUtil.parseNumber(adquirienteRequest.getPORCENTAJEADQUIRIDO()));
			adquiriente.setEdad(Integer.parseInt(new Long(adquirienteRequest.getEDADUSUFRUCTUARIO()).toString()));
			adquiriente.setNumDerecho(Integer.parseInt(adquirienteRequest.getCLASEDERECHO()));
			adquiriente.setNif(adquirienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getNUMERODOCUMENTO());
			adquiriente.setNombre(adquirienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getNOMBRE());
			adquiriente.setApellido1(adquirienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getAPELLIDO1RAZONSOCIAL());
			adquiriente.setApellido2(adquirienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getAPELLIDO2());
			adquiriente.setCp(adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getCODIGOPOSTAL());
			String calleAdquiriente = adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getTIPOVIA() + " " + adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getVIA() + " "
					+ adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getNUMEROVIA() + " " + adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getBLOQUE() + " "
					+ adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getPLANTA() + " " + adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getPUERTA() + " "
					+ adquirienteRequest.getDATOSBASICOS().getDOMICILIO().getRESTO();
			adquiriente.setCalle(calleAdquiriente.replaceAll("null", "").trim());

			adquirientes.add(adquiriente);
		}

		return adquirientes.toArray(new Adquiriente[adquirientes.size()]);
	}

	public Transmitente[] getTransmitentes(List transmitentesRequest) throws Exception
	{
		ArrayList<Transmitente> transmitentes = new ArrayList<Transmitente>();

		for (Iterator iterator = transmitentesRequest.iterator(); iterator.hasNext();)
		{
			TransmitenteType transmitienteRequest = (TransmitenteType) iterator.next();

			List listaTransmisionesAnteriores = transmitienteRequest.getTRANSMISIONESANTERIORES().getTRANSMISION();
			TRANSMISIONType transmisionAnteriorRequest = (TRANSMISIONType) listaTransmisionesAnteriores.get(listaTransmisionesAnteriores.size() - 1);

			Transmitente transmitente = new Transmitente();
			transmitente.setIdentificador("" + transmitienteRequest.getDATOSBASICOS().getIDENTIFICADOR());
			transmitente.setNif(transmitienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getNUMERODOCUMENTO());

			BONIFICACIONESType bonificaciones = transmitienteRequest.getBONIFICACIONES();

			if (bonificaciones != null)
			{
				transmitente.setBonificacion(NumberConversionUtil.parseNumber(transmitienteRequest.getBONIFICACIONES().getPORCENTAJE()));
			}

			if (transmitienteRequest.getFECHADEFUNCION() != null)
			{
				transmitente.setFechaDefuncion(NumberConversionUtil.parseDate(transmitienteRequest.getFECHADEFUNCION()));
			}

			transmitente.setFechaTransAnterior(NumberConversionUtil.parseDate(transmisionAnteriorRequest.getFECHA()));
			transmitente.setPorcentajeTransmitido(NumberConversionUtil.parseNumber(transmitienteRequest.getPORCENTAJETRANSMITIDO()));
			transmitente.setNumDerecho(Integer.parseInt(transmitienteRequest.getCLASEDERECHO()));
			transmitente.setNombre(transmitienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getNOMBRE());
			transmitente.setApellido1(transmitienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getAPELLIDO1RAZONSOCIAL());
			transmitente.setApellido2(transmitienteRequest.getDATOSBASICOS().getDATOSPERSONALES().getAPELLIDO2());
			transmitente.setEdad((int) transmitienteRequest.getEDADUSUFRUCTUARIO());
			transmitente.setCp(transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getCODIGOPOSTAL());
			transmitente.setAnyosUsufruto(transmitienteRequest.getANYOSUSUFRUCTO());
			String calleTransmitiente = transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getTIPOVIA() + " " + transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getVIA()
					+ " " + transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getNUMEROVIA() + " " + transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getBLOQUE() + " "
					+ transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getPLANTA() + " " + transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getPUERTA() + " "
					+ transmitienteRequest.getDATOSBASICOS().getDOMICILIO().getRESTO();
			transmitente.setCalle(calleTransmitiente.replaceAll("null", "").trim());

			transmitentes.add(transmitente);
		}

		return transmitentes.toArray(new Transmitente[transmitentes.size()]);
	}

	public String getNotarioNombreCompleto(NotarioType notario) {

		StringBuffer nombreCompleto = new StringBuffer();

		if (notario != null) {
			append(nombreCompleto, notario.getAPELLIDO1(), " ");
			append(nombreCompleto, notario.getAPELLIDO2(), " ");
			append(nombreCompleto, notario.getNOMBRE(), " ");
		}

		return nombreCompleto.toString();
	}

	protected void append(StringBuffer sbuffer, String cadena, String separador) {

		if (StringUtils.isNotBlank(cadena)) {
			if (sbuffer.length() > 0) {
				sbuffer.append(separador);
			}
			sbuffer.append(cadena);
		}
	}
}