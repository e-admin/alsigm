package descripcion.model.xml.card.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;
import common.util.CustomDate;
import common.util.DateQualifierHelper;
import common.util.NumberUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import descripcion.exceptions.DescriptorRightsException;
import descripcion.model.EstadoDescriptor;
import descripcion.model.TipoNivelAcceso;
import descripcion.model.xml.card.ContenedorElementos;
import descripcion.model.xml.card.EdicionDato;
import descripcion.model.xml.card.EdicionDatoNumerico;
import descripcion.model.xml.card.EdicionDatoReferencia;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.ElementoTabla;
import descripcion.model.xml.card.Valor;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ListaDescrVO;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Utilidad para gestionar los valores del formulario.
 */
public class ValueManager {

	/** Logger de la clase. */
	protected Logger logger = Logger.getLogger(ValueManager.class);

	/** Información de edición del elemento. */
	private EdicionDato edicion = null;

	/** Título del elemento. */
	private String titulo = null;

	/** Lista de errores de validación. */
	private ActionErrors errors = null;

	/** Interfaz de acceso a la lógica de descripcion. */
	GestionDescripcionBI descripcionBI = null;

	/** Interfaz de acceso a la lógica de elementos del cuadro. */
	GestionCuadroClasificacionBI cuadroBI = null;

	/**
	 * Constructor.
    *
	 * @param titulo
	 *            Título del elemento.
	 * @param edicion
	 *            Información de edición del elemento.
	 * @param errors
	 *            Lista de errores en la validación.
	 * @param serviceSession
	 *            Sesión del servicio.
	 */
	public ValueManager(String titulo, EdicionDato edicion,
			ActionErrors errors, ServiceSession serviceSession) {
		// Obtención del repositorio de servicios
		ServiceRepository services = ServiceRepository
				.getInstance(serviceSession);

		this.titulo = titulo;
		this.edicion = edicion;
		this.errors = errors;
		this.descripcionBI = services.lookupGestionDescripcionBI();
		this.cuadroBI = services.lookupGestionCuadroClasificacionBI();
	}

	/**
	 * Obtiene la lista de valores ordenados del formulario para un elemento de
	 * tipo etiqueta-dato concreto.
    *
	 * @param parameters
	 *            Parámetros del formulario.
	 * @return Lista ordenada de valores del formulario.
	 */
	public Valor[] getValoresFormulario(Map parameters,
			ContenedorElementos contenedor, ElementoEtiquetaDato elemento) {
		List valores = new LinkedList();

		switch (edicion.getTipo()) {
		case DefTipos.TIPO_DATO_TEXTO_CORTO:
		case DefTipos.TIPO_DATO_TEXTO_LARGO: {
			String[] valoresFormulario = (String[]) parameters.get("campo_"
					+ edicion.getId());

			if (valoresFormulario != null) {
				for (int i = 0; i < valoresFormulario.length; i++)
					valores.add(new Valor(0, valoresFormulario[i]));
			}

			break;
		}

		case DefTipos.TIPO_DATO_NUMERICO: {
			String[] numeros = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_numero");
			String[] tiposMedida = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_tipoMedida");
			String[] unidadesMedida = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_unidadMedida");

			if (numeros != null) {
				// Recoger los valores del formulario
				for (int i = 0; i < numeros.length; i++) {
					// Validar el valor
					validateNumber(numeros[i]);

					// Añadir el valor
					Valor v = new Valor(0, numeros[i]);
					if ((tiposMedida != null) && (tiposMedida.length > i))
						v.setTipoMedida(TypeConverter.toInt(tiposMedida[i], 0));
					if ((unidadesMedida != null) && (unidadesMedida.length > i))
						v.setUnidadMedida(unidadesMedida[i]);

					valores.add(v);
				}
			}

			break;
		}

		case DefTipos.TIPO_DATO_FECHA: {
			String[] campo_formato = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_formato");
			String[] campo_fechaD = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_fechaD");
			String[] campo_fechaM = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_fechaM");
			String[] campo_fechaA = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_fechaA");

			String[] campo_fechaHH = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_fechaHH");
			String[] campo_fechaMM = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_fechaMM");
			String[] campo_fechaSS = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_fechaSS");


			String[] campo_fechaS = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_fechaS");
			String[] campo_calificador = (String[]) parameters.get("campo_"
					+ edicion.getId() + "_calificador");

			if (campo_formato != null) {
				CustomDate customDate;

				for (int i = 0; i < campo_formato.length; i++) {
					try {

						if (DateQualifierHelper.CALIFICADOR_SIN_FECHA
								.equals(campo_calificador[i])) {
							customDate = new CustomDate(null, null, null, null,
									null, null, null,
									null, null, campo_calificador[i]);
						} else {
							customDate = new CustomDate(campo_formato[i],
									campo_fechaA[i], campo_fechaM[i],campo_fechaD[i],
									campo_fechaHH[i], campo_fechaMM[i],campo_fechaSS[i],
									campo_fechaS[i], null,
									campo_calificador[i]);
						}

						// Validar la fecha
						if (!customDate.validate())
							errors.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(Constants.ERROR_DATE,
											titulo));

						// Añadir la fecha
						valores.add(new Valor(0, customDate));
					} catch (Exception e) {
						errors.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(e.getMessage()));
					}
				}
			}

			break;
		}

		case DefTipos.TIPO_DATO_REFERENCIA: {
			String[] valoresFormulario = (String[]) parameters.get("campo_"
					+ edicion.getId());
			// String [] tiposRef = (String []) parameters.get("campo_" +
			// edicion.getId() + "_tiporef");
			// String [] idsRef = (String []) parameters.get("campo_" +
			// edicion.getId() + "_idref");

			if (valoresFormulario != null) {
				short tipoRef = ((EdicionDatoReferencia) edicion)
						.getTipoReferencia();

				for (int i = 0; i < valoresFormulario.length; i++) {
					String idRef = null;
					if (StringUtils.isNotBlank(valoresFormulario[i])) {
						switch (tipoRef) {
						case DefTipos.TIPO_REFERENCIA_DESCRIPTOR:
						case DefTipos.TIPO_REFERENCIA_ENTIDAD_PRODUCTORA: {

							if (contenedor instanceof ElementoTabla
									&& !StringUtils
											.isEmpty(((ElementoTabla) contenedor)
													.getSistemaExterno())
									&& edicion instanceof EdicionDatoReferencia) {

								EdicionDatoReferencia edicionDatoReferencia = (EdicionDatoReferencia) edicion;
								ListaDescrVO listaDescrVO = descripcionBI
										.getListaDescriptora(edicionDatoReferencia
												.getIdentificador(0));
								if (listaDescrVO != null) {

									DescriptorVO descriptorVO = descripcionBI
											.getDescriptorByNombreYIdLista(
													valoresFormulario[i],
													listaDescrVO.getId());

									if (descriptorVO == null) {
										descriptorVO = new DescriptorVO();
										descriptorVO
												.setNombre(valoresFormulario[i]);
										descriptorVO.setIdLista(listaDescrVO
												.getId());
										descriptorVO
												.setNivelAcceso(TipoNivelAcceso.PUBLICO_VALUE);
										descriptorVO
												.setEditClfDocs(Constants.FALSE_STRING);
										descriptorVO.setTipo(listaDescrVO
												.getTipoDescriptor());
										descriptorVO
												.setEstado(EstadoDescriptor.VALIDADO);
										descriptorVO.setIdLista(listaDescrVO
												.getId());
										try {
											descriptorVO = descripcionBI
													.insertDescriptor(descriptorVO);
										} catch (DescriptorRightsException e) {
											errors.add(
													ActionErrors.GLOBAL_MESSAGE,
													new ActionError(
															Constants.ERROR_FICHA_PERMISOS_INSERCION_DESCRIPTOR,
															descriptorVO
																	.getNombre()));
										} catch (Exception e) {
											errors.add(
													ActionErrors.GLOBAL_MESSAGE,
													new ActionError(
															Constants.ERROR_GENERAL_MESSAGE,
															e.getMessage()));
										}
									}
									idRef = descriptorVO.getId();
								}
							} else {
								List descriptores = descripcionBI
										.getDescriptoresByNombre(valoresFormulario[i]);
								if (descriptores.size() > 0)
									idRef = ((DescriptorVO) descriptores.get(0))
											.getId();
								else
									errors.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionError(
													Constants.ERROR_REFERENCE,
													titulo
															+ " ("
															+ valoresFormulario[i]
															+ ")"));
							}
							break;
						}

						case DefTipos.TIPO_REFERENCIA_ELEMENTO_CUADRO_CLASIFICACION: {
							ElementoCuadroClasificacionVO elementoCFVO = cuadroBI
									.getElementoCuadroClasificacionXCodReferencia(valoresFormulario[i]);
							if (elementoCFVO != null)
								idRef = elementoCFVO.getId();
							else
								errors.add(ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_REFERENCE,
												titulo + " ("
														+ valoresFormulario[i]
														+ ")"));
							break;
						}

						}
					}

					// Añadir la referencia
					valores.add(new Valor(0, valoresFormulario[i], idRef,
							tipoRef));
				}
			}

			break;
		}
		}

		return (Valor[]) valores.toArray(new Valor[valores.size()]);
	}

	/**
	 * Valida el campo numérico.
    *
	 * @param number
	 *            Cadena numérica.
	 */
	private void validateNumber(String number) {
		if (StringUtils.isNotBlank(number)) {
			double dbl;

			if (((EdicionDatoNumerico) edicion).getTipoNumerico() == 1) {
				try {
					// Validar que sea un entero
					dbl = TypeConverter.toInt(number);
				} catch (NumberFormatException e) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_INT, titulo + " (" + number + ")"));
					return;
				}
			} else {
				try {
					// Validar que sea un double
					dbl = TypeConverter.toDouble(number);
				} catch (NumberFormatException e) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_DOUBLE, titulo + " (" + number
									+ ")"));
					return;
				}
			}

			// Rango máximo
			if ((((EdicionDatoNumerico) edicion).getRangoMaximo() != null)
					&& (dbl > ((EdicionDatoNumerico) edicion).getRangoMaximo()
							.doubleValue())) {

				String rangoMaximo = NumberUtils.formatea(
						((EdicionDatoNumerico) edicion).getRangoMaximo()
								.doubleValue(),
						NumberUtils.FORMATO_CUATRO_DECIMALES);
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_MAX_RANGE, new String[] {
								titulo + " (" + number + ")", rangoMaximo }));
			}
			// Rango mínimo

			if ((((EdicionDatoNumerico) edicion).getRangoMinimo() != null)
					&& (dbl < ((EdicionDatoNumerico) edicion).getRangoMinimo()
							.doubleValue())) {
				String rangoMinimo = NumberUtils.formatea(
						((EdicionDatoNumerico) edicion).getRangoMinimo()
								.doubleValue(),
						NumberUtils.FORMATO_CUATRO_DECIMALES);
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_MIN_RANGE, new String[] {
								titulo + " (" + number + ")", rangoMinimo }));
			}
		}
	}
}
