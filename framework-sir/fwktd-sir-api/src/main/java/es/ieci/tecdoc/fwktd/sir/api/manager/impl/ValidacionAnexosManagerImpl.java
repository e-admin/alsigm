package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import es.ieci.tecdoc.fwktd.sir.api.manager.ValidacionAnexosManager;

/*
 *
 * No validar los anexos. 
 * - No se indica el algoritmo con el que se ha generado el código hash.
 * - No se indica el formato de firma.
 */
public class ValidacionAnexosManagerImpl implements ValidacionAnexosManager {

//	/**
//	 * Logger de la clase.
//	 */
//	private static final Logger logger = LoggerFactory
//			.getLogger(ValidacionAnexosManagerImpl.class);
//
//    /**
//     * Gestor de asientos registrales.
//     */
//    private AsientoRegistralManager asientoRegistralManager = null;
//
//    /**
//     * Gestor de anexos de asientos registrales.
//     */
//    private AnexoManager anexoManager = null;
//
//	/**
//	 * Gestor de códigos hash.
//	 */
//	private HashManager hashManager = null;
//
//	/**
//	 * Servicio de firma digital.
//	 */
//	private FirmaDigitalService firmaDigitalService = null;

	/**
	 * Constructor.
	 */
	public ValidacionAnexosManagerImpl() {
		super();
	}

//	public AsientoRegistralManager getAsientoRegistralManager() {
//		return asientoRegistralManager;
//	}
//
//	public void setAsientoRegistralManager(
//			AsientoRegistralManager asientoRegistralManager) {
//		this.asientoRegistralManager = asientoRegistralManager;
//	}
//
//	public AnexoManager getAnexoManager() {
//		return anexoManager;
//	}
//
//	public void setAnexoManager(AnexoManager anexoManager) {
//		this.anexoManager = anexoManager;
//	}
//
//	public HashManager getHashManager() {
//		return hashManager;
//	}
//
//	public void setHashManager(HashManager hashManager) {
//		this.hashManager = hashManager;
//	}
//
//	public FirmaDigitalService getFirmaDigitalService() {
//		return firmaDigitalService;
//	}
//
//	public void setFirmaDigitalService(FirmaDigitalService firmaDigitalService) {
//		this.firmaDigitalService = firmaDigitalService;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.ValidacionAnexosManager#validarAnexos(java.lang.String)
//	 */
//	public List<ValidacionAnexoVO> validarAnexos(String id) {
//
//        logger.info("Validando los documentos del asiento registral con identificador [{}]", id);
//
//        Assert.hasText(id, "'id' must not be empty");
//        Assert.notNull(getAsientoRegistralManager(), "AsientoRegistralManager must not be null");
//
//        // Obtener la información del asiento registral
//        AsientoRegistralVO asiento = getAsientoRegistralManager().get(id);
//        if (asiento == null) {
//            logger.error("No se ha encontrado el asiento registral con identificador [{}] para validar sus anexos", id);
//            throw new SIRException("error.sir.validarAnexos.asientoNoEncontrado", null,
//                    "No se ha encontrado el asiento registral para validar sus anexos");
//        }
//
//        return validarAnexos(asiento);
//	}
//
//	protected List<ValidacionAnexoVO> validarAnexos(AsientoRegistralVO asiento) {
//
//		logger.info("Validando los documentos del asiento registral: {}", asiento);
//
//        Assert.notNull(getFirmaDigitalService(), "FirmaDigitalService must not be null");
//
//    	List<ValidacionAnexoVO> validacionAnexos = new ArrayList<ValidacionAnexoVO>();
//
//    	// Obtener los anexos del asiento registral
//    	List<AnexoVO> anexos = asiento.getAnexos();
//    	if (CollectionUtils.isNotEmpty(anexos)) {
//
//    		logger.info("El asiento registral [{}] tiene {} anexo/s", asiento.getId(), anexos.size());
//
//    		// Lista de anexos que son firmas de otros anexos
//    		List<AnexoVO> firmasExternas = new ArrayList<AnexoVO>();
//
//    		for (AnexoVO anexo : anexos) {
//
//    			// Validar el anexo
//    			ValidacionAnexoVO validacionAnexo = validarAnexo(anexo);
//    			validacionAnexos.add(validacionAnexo);
//
//    			// Comprobar si la firma está embebida o se trata de la firma de otro anexo
//    			if (StringUtils.equals(anexo.getId(), anexo.getIdentificadorFicheroFirmado())) {
//
//    				// El anexo tiene la firma embebida
//    				validacionAnexo.setValidacionFirma(ValidacionFirmaEnum.FIRMA_EMBEBIDA);
//
//    			} else if (StringUtils.isNotBlank(anexo.getIdentificadorFicheroFirmado())) {
//
//    				// El contenido del anexo es la firma de otro anexo
//    				firmasExternas.add(anexo);
//    			}
//    		}
//
//    		// Comprobar las firmas externas de anexos
//			for (AnexoVO firmaExterna : firmasExternas) {
//
//				// Obtener la información de validación del anexo firmado
//				ValidacionAnexoVO validacionAnexo = getValidacionAnexo(validacionAnexos, firmaExterna.getIdentificadorFicheroFirmado());
//				if (validacionAnexo != null) {
//
//					// Validar la firma
//					ValidacionFirmaVO validacionFirma = validarFirma(validacionAnexo.getAnexo(), firmaExterna);
//					if (validacionFirma != null) {
//						if (validacionFirma.isValida()) {
//							validacionAnexo.setValidacionFirma(ValidacionFirmaEnum.FIRMA_VALIDA);
//						} else {
//							validacionAnexo.setValidacionFirma(ValidacionFirmaEnum.FIRMA_INVALIDA);
//						}
//					}
//				}
//			}
//
//    	} else {
//    		logger.info("El asiento registral [{}] no tiene anexos", asiento.getId());
//    	}
//
//    	return validacionAnexos;
//	}
//
//	protected ValidacionAnexoVO getValidacionAnexo(List<ValidacionAnexoVO> validacionAnexos, String idAnexo) {
//
//		for (ValidacionAnexoVO validacionAnexo : validacionAnexos) {
//
//			AnexoVO anexo = validacionAnexo.getAnexo();
//			if ((anexo != null) && StringUtils.equals(idAnexo, anexo.getId())) {
//				return validacionAnexo;
//			}
//		}
//
//		return null;
//	}
//
//	protected ValidacionAnexoVO validarAnexo(AnexoVO anexo) {
//
//		logger.info("Comprobando la validez del anexo: {}", anexo);
//
//		ValidacionAnexoVO validacionAnexo = new ValidacionAnexoVO();
//		validacionAnexo.setAnexo(anexo);
//
//		// Obtener el contenido del anexo
//		byte[] contenido = getAnexoManager().getContenidoAnexo(anexo.getId());
//		if (ArrayUtils.isNotEmpty(contenido)) {
//
//			// Validar el hash
//			boolean hashValidado = validarHash(contenido, anexo.getHash());
//			validacionAnexo.setHashValidado(hashValidado);
//
//			// Cargar el certificado
//			Certificate certificado = getCertificate(anexo.getCertificado());
//			if (certificado != null) {
//
//				// Validar el certificado
//				ValidacionCertificadoVO validacionCertificado = validarCertificado(anexo, certificado);
//				if (validacionCertificado != null) {
//
//					if (validacionCertificado.isValido()) {
//						validacionAnexo.setValidacionCertificado(ValidacionCertificadoEnum.CERTIFICADO_VALIDO);
//					} else if (validacionCertificado.getCodigoError() != null) {
//
//			            if (validacionCertificado.getCodigoError() == CodigosErrorValidacionCertificados.CERTIFICADO_NULO) {
//			            	validacionAnexo.setValidacionCertificado(ValidacionCertificadoEnum.SIN_CERTIFICADO);
//			            } else if (validacionCertificado.getCodigoError() == CodigosErrorValidacionCertificados.CERTIFICADO_NO_X509) {
//			            	validacionAnexo.setValidacionCertificado(ValidacionCertificadoEnum.CERTIFICADO_NO_X509);
//			            } else if (validacionCertificado.getCodigoError() == CodigosErrorValidacionCertificados.CERTIFICADO_EXPIRADO) {
//			            	validacionAnexo.setValidacionCertificado(ValidacionCertificadoEnum.CERTIFICADO_EXPIRADO);
//			            } else if (validacionCertificado.getCodigoError() == CodigosErrorValidacionCertificados.CERTIFICADO_NO_VALIDO_AUN) {
//			            	validacionAnexo.setValidacionCertificado(ValidacionCertificadoEnum.CERTIFICADO_NO_VALIDO_AUN);
//			            } else if (validacionCertificado.getCodigoError() == CodigosErrorValidacionCertificados.CERTIFICADO_REVOCADO) {
//			            	validacionAnexo.setValidacionCertificado(ValidacionCertificadoEnum.CERTIFICADO_REVOCADO);
//			            } else { // CodigosErrorValidacionCertificados.CERTIFICADO_ERRONEO
//			            	validacionAnexo.setValidacionCertificado(ValidacionCertificadoEnum.CERTIFICADO_ERRONEO);
//			            }
//
//			            if (validacionCertificado.getExcepcionError() != null) {
//			            	validacionAnexo.setDescripcionErrorValidacionCertificado(validacionCertificado.getExcepcionError().toString());
//			            }
//					}
//				}
//
//				// Comprobar la validación OCPS del certificado
//				boolean validacionOCSPCertificado = checkValidacionOCSPCertificado(anexo, certificado);
//				validacionAnexo.setValidacionOCSPCertificado(validacionOCSPCertificado);
//
//			} else {
//				logger.info("El anexo [{}] no tiene certificado", anexo.getId());
//			}
//
//			// Validar la firma
//			ValidacionFirmaVO validacionFirma = validarFirma(anexo, contenido);
//			if (validacionFirma != null) {
//				if (validacionFirma.isValida()) {
//					validacionAnexo.setValidacionFirma(ValidacionFirmaEnum.FIRMA_VALIDA);
//				} else {
//					validacionAnexo.setValidacionFirma(ValidacionFirmaEnum.FIRMA_INVALIDA);
//				}
//			}
//
//		} else {
//			logger.info("El anexo [{}] no tiene contenido", anexo.getId());
//		}
//
//		logger.debug("Validación del anexo [{}]: {}", anexo.getId(), validacionAnexo);
//
//		return validacionAnexo;
//	}
//
//	protected boolean validarHash(byte[] contenido, byte[] hash) {
//
//		if (logger.isInfoEnabled()) {
//			logger.info("Validando el hash del anexo: {}", Base64.encodeBase64String(hash));
//		}
//
//		byte[] hashGenerado = getHashManager().generarHash(contenido);
//
//		if (logger.isInfoEnabled()) {
//			logger.info("Hash generado a partir del contenido: {}", Base64.encodeBase64String(hashGenerado));
//		}
//
//		boolean resultado = Arrays.equals(hashGenerado, hash);
//		logger.info("Validación del hash del anexo: {}", resultado);
//
//		return resultado;
//	}
//
//	protected ValidacionCertificadoVO validarCertificado(AnexoVO anexo, Certificate certificado) {
//
//		ValidacionCertificadoVO validacionCertificado = null;
//
//		logger.info("Comprobando la validez del certificado del anexo [{}]", anexo.getId());
//
//		try {
//
//			validacionCertificado = firmaDigitalService.validarCertificado(certificado);
//			logger.info("Certificado del anexo [{}] validado", anexo.getId());
//			logger.debug("Certificado validado: {}", validacionCertificado);
//
//		} catch (FirmaDigitalException e) {
//			logger.error("Error al validar el certificado", e);
//            throw new SIRException("error.sir.validarAnexos.validarCertificado", null, "Error al validar el certificado del anexo");
//		}
//
//		return validacionCertificado;
//	}
//
//	protected boolean checkValidacionOCSPCertificado(AnexoVO anexo, Certificate certificado) {
//
//		logger.info("Comprobando la validez del certificado del anexo [{}]", anexo.getId());
//
//		boolean validado = false;
//
//		if (ArrayUtils.isNotEmpty(anexo.getValidacionOCSPCertificado())) {
//
//			try {
//
//				// Cargar la información de validación OCSP del certificado
//				OCSPResp ocspResp = new OCSPResp(anexo.getValidacionOCSPCertificado());
//
//				/*
//				 * SUCCESSFUL (0) : Response has valid confirmations
//				 * MALFORMED_REQUEST (1) : Illegal confirmation request
//				 * INTERNAL_ERROR (2) : Internal error in issuer tryLater
//				 * TRY_LATER (3) : Try again later
//				 * (4) is not used
//				 * SIG_REQUIRED (5) : Must sign the request unauthorized
//				 * UNAUTHORIZED (6) : Requestunauthorized
//				 */
//				int status = ocspResp.getStatus();
//				logger.info("OCSPResp status: {}", status);
//
//				if (status == OCSPResponseStatus.SUCCESSFUL) {
//
//					BasicOCSPResp basicOCSPResp = (BasicOCSPResp) ocspResp.getResponseObject();
//
//					// Comprobamos que la respuesta OCSP no ha sido manipulada y
//					// ha sido firmada por un certificado de confianza.
//					// Recupero el certificado con el que debe haber sido
//					// firmado el ocsp
//					checkOCSP(basicOCSPResp, certificado);
//
//					SingleResp[] singleResps = basicOCSPResp.getResponses();
//					for (SingleResp singleResp : singleResps) {
//						CertificateID respCertID = singleResp.getCertID();
//						if (respCertID != null) {
//							if (respCertID.getSerialNumber().equals(((X509Certificate)certificado).getSerialNumber())) {
//								Object singleRespStatus = singleResp.getCertStatus();
//								if (singleRespStatus == CertificateStatus.GOOD) {
//									logger.info("OCSP: Status of certificate is: good");
//									validado = true;
//									break;
//								} else if (singleRespStatus instanceof org.bouncycastle.ocsp.RevokedStatus) {
//									logger.info("OCSP: Status of certificate is: revoked");
//									//throw new CertPathValidatorException("Certificate has been revoked");
//								} else if (singleRespStatus instanceof org.bouncycastle.ocsp.UnknownStatus) {
//									logger.info("OCSP: Status of certificate is: unknown");
//									//throw new CertPathValidatorException("Certificate's revocation status is unknown");
//								} else {
//									logger.info("Status of certificate is: not recognized");
//									//throw new CertPathValidatorException("Unknown OCSP response for certificate");
//								}
//							}
//						}
//					}
//
//				} else {
//					logger.info(
//							"La validación OCSP del certificado no es correcta [status={}]",
//							ocspResp.getStatus());
//				}
//
//				logger.info(
//						"Validación OCSP del certificado del anexo [{}] comprobada: {}",
//						anexo.getId(), validado);
//
//			} catch (IOException e) {
//				logger.error("Error al leer la validación OCSP del certificado", e);
//			} catch (OCSPException e) {
//				logger.error("Error al leer la validación OCSP del certificado", e);
//			} catch (CertPathValidatorException e) {
//				logger.error("Error al leer la validación OCSP del certificado", e);
//			} catch (NoSuchProviderException e) {
//				logger.error("Error al leer la validación OCSP del certificado", e);
//			}
//		} else {
//			logger.info("El anexo [{}] no tiene validación OCSP del certificado", anexo.getId());
//		}
//
//		return validado;
//	}
//
//	private void checkOCSP(BasicOCSPResp ocspResponse, Certificate certificado) throws OCSPException,
//			CertPathValidatorException, NoSuchProviderException {
//
//		// Obtener la clave pública de la firma de la respuesta OCSP
//		X509Certificate[] certificatePath = null;
//		try {
//			certificatePath = ocspResponse.getCerts(BouncyCastleProvider.PROVIDER_NAME);
//		} catch (NoSuchProviderException e) {
//			throw new CertPathValidatorException("La respuesta OCSP no puede ser validada.", e);
//		}
//
//		if (ArrayUtils.isEmpty(certificatePath)) {
//
//			// La respuesta OCSP se ha firmado por la propia CA del certificado.
//			boolean verificationResult = ocspResponse.verify(certificado.getPublicKey(), BouncyCastleProvider.PROVIDER_NAME);
//			if (!verificationResult) {
//				logger.info("OCSP response signature invalid");
//				//return false;
//			}
//
//		} else {
//
//			// Obtenemos el certificado
//			X509Certificate ocspResponseCertificate = certificatePath[0];
//
//			if (!ocspResponse.verify(ocspResponseCertificate.getPublicKey(),
//					BouncyCastleProvider.PROVIDER_NAME)) {
//				logger.debug("OCSP Responser response signature invalid");
//				//throw new CertPathValidatorException("La respuesta OCSP no es válida. La firma no corresponde a un certificado de confianza.");
//				//return false;
//			}
//		}
//	}
//
//	protected ValidacionFirmaVO validarFirma(AnexoVO anexo, byte[] contenido) {
//
//		ValidacionFirmaVO validacionFirma = null;
//
//		logger.info("Comprobando la validez de la firma del anexo [{}]", anexo.getId());
//
//		if (ArrayUtils.isNotEmpty(anexo.getFirma())) {
//
//			try {
//
//				// Validar la firma
//				validacionFirma = firmaDigitalService.validarFirma(contenido, anexo.getFirma());
//				logger.info("Firma del anexo [{}] validada", anexo.getId());
//				logger.debug("Firma validada: {}", validacionFirma);
//
//			} catch (FirmaDigitalException e) {
//				logger.error("Error al validar la firma del anexo", e);
//				throw new SIRException("error.sir.validarAnexos.validarFirma",
//						null, "Error al validar la firma del anexo");
//			}
//
//		} else {
//			logger.info("El anexo [{}] no tiene firma", anexo.getId());
//		}
//
//		return validacionFirma;
//	}
//
//	protected ValidacionFirmaVO validarFirma(AnexoVO anexo, AnexoVO firmaExterna) {
//
//		ValidacionFirmaVO validacionFirma = null;
//
//		logger.info("Comprobando la validez de la firma externa del anexo [{}]", anexo.getId());
//
//		// Obtener el contenido del anexo
//		byte[] contenidoFirmado = getAnexoManager().getContenidoAnexo(anexo.getId());
//		if (ArrayUtils.isNotEmpty(contenidoFirmado)) {
//
//			// Obtener el contenido de la firma
//			byte[] firma = getAnexoManager().getContenidoAnexo(firmaExterna.getId());
//			if (ArrayUtils.isNotEmpty(firma)) {
//
//				try {
//
//					// Validar la firma
//					validacionFirma = firmaDigitalService.validarFirma(contenidoFirmado, firma);
//					logger.info("Firma externa del anexo [{}] validada", anexo.getId());
//					logger.debug("Firma externa validada: {}", validacionFirma);
//
//				} catch (FirmaDigitalException e) {
//					logger.error("Error al validar la firma externa del anexo", e);
//					throw new SIRException("error.sir.validarAnexos.validarFirma",
//							null, "Error al validar la firma externa del anexo");
//				}
//			}
//		}
//
//		return validacionFirma;
//	}
//
//	protected Certificate getCertificate(byte[] cert) {
//
//		Certificate certificate = null;
//
//		logger.info("Cargando la información del certificado");
//
//		if (ArrayUtils.isNotEmpty(cert)) {
//			try {
//
//				CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//				//certificate = certFactory.generateCertificate(new ByteArrayInputStream(cert));
//				Collection<? extends Certificate> certificates = certFactory.generateCertificates(new ByteArrayInputStream(cert));
//				if (CollectionUtils.isNotEmpty(certificates)) {
//					certificate = certificates.iterator().next();
//				}
//
//			} catch (CertificateException e) {
//				logger.error("Error al cargar el certificado", e);
//	            throw new SIRException("error.sir.validarAnexos.cargarCertificado", null, "Error al cargar el certificado del anexo");
//			}
//		}
//
//		logger.debug("Certificado cargado: {}", certificate);
//
//    	return certificate;
//	}
}
