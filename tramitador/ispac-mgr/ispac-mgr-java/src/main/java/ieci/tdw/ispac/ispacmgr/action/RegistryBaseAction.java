package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public abstract class RegistryBaseAction extends BaseDispatchAction {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = Logger.getLogger(RegistryBaseAction.class);
	
	
	protected void viewRegister(HttpServletRequest request, Register register)
			throws ISPACException {

		try {
			// PROCEDENCIA
			if (register.getRegisterOrigin() != null) {

				request.setAttribute("FECHA_REGISTRO", (register
						.getRegisterOrigin().getRegisterDate() == null) ? ""
						: DateUtil.formatCalendar(register.getRegisterOrigin()
								.getRegisterDate()));
				request.setAttribute("FECHA_CREACION", (register
						.getRegisterOrigin().getCreationDate() == null) ? ""
						: DateUtil.formatDate(register.getRegisterOrigin()
								.getCreationDate().getTime()));
				request.setAttribute("USUARIO", (register.getRegisterOrigin()
						.getUser() == null) ? "" : register.getRegisterOrigin()
						.getUser());

				if (register.getRegisterOrigin().getRegisterOffice() != null) {

					request.setAttribute("OFICINA_REGISTRO",
							(register.getRegisterOrigin().getRegisterOffice()
									.getName() == null) ? "" : register
									.getRegisterOrigin().getRegisterOffice()
									.getName());
				}
			}

			// ORGANISMOS ORIGEN Y DESTINO
			if (register.getOriginOrganization() != null) {

				request.setAttribute("ORGANISMO_ORIGEN", (register
						.getOriginOrganization().getName() == null) ? ""
						: register.getOriginOrganization().getName());
			}
			if (register.getDestinationOrganization() != null) {

				request.setAttribute("ORGANISMO_DESTINO", (register
						.getDestinationOrganization().getName() == null) ? ""
						: register.getDestinationOrganization().getName());
			}

			// CONTENIDO / REMITENTES/DESTINATARIOS
			if (register.getRegisterData() != null) {

				if ((register.getRegisterData().getParticipants() != null)
						&& (register.getRegisterData().getParticipants().length > 0)) {

					ThirdPerson thirdPerson = register.getRegisterData()
							.getParticipants()[0];
					String participantes = thirdPerson.getName();
					for (int i = 1; i < register.getRegisterData()
							.getParticipants().length; i++) {
						thirdPerson = register.getRegisterData()
								.getParticipants()[i];
						participantes += ", " + thirdPerson.getName();
					}
					
					if (RegisterType.ENTRADA.equals(register.getRegisterOrigin().getRegisterType())) {
						request.setAttribute("REMITENTES",
								(participantes == null) ? "" : participantes);
					} else {
						request.setAttribute("DESTINATARIOS",
								(participantes == null) ? "" : participantes);
					}

				} else {
					request.setAttribute("REMITENTES", "");
					request.setAttribute("DESTINATARIOS", "");
				}
				
				if ((register.getRegisterData().getInfoDocuments() != null)
						&& (register.getRegisterData().getInfoDocuments().length > 0)) {
					request.setAttribute("DOCUMENTOS", register.getRegisterData().getInfoDocuments().length);
				}
				
				request.setAttribute("RESUMEN", (register.getRegisterData()
						.getSummary() == null) ? "" : register
						.getRegisterData().getSummary());

				if (register.getRegisterData().getSubject() != null) {

					request.setAttribute("ASUNTO", (register.getRegisterData()
							.getSubject().getName() == null) ? "" : register
							.getRegisterData().getSubject().getName());
				}
			}

			// TRANSPORTE
			if (register.getTransport() != null) {

				request.setAttribute("TIPO_TRANSPORTE", (register
						.getTransport().getType() == null) ? "" : register
						.getTransport().getType());
				request.setAttribute("NUMERO_TRANSPORTE", (register
						.getTransport().getNumber() == null) ? "" : register
						.getTransport().getNumber());
			}
		} catch (Exception e) {
			logger.error("Error al mostrar el registro", e);
			throw new ISPACException(e);
		}
	}
    
}
