package es.ieci.tecdoc.fwktd.sir.api.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws.WSSIRHelper;
import es.ieci.tecdoc.fwktd.sir.api.schema.Fichero_Intercambio_SICRES_3;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.TrazabilidadWS;
import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.AuditoriaFicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;

@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
        "/beans/fwktd-sir-test-beans-custom.xml" })
public class TestUtils extends AbstractJUnit4SpringContextTests {

    @Test
    public void test() {
    }

    public static CriteriosVO createCriteriosVO(PageInfo pageInfo) {
        return new CriteriosVO()
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_ESTADO,
                    OperadorCriterioEnum.IN,
                    new EstadoAsientoRegistralEnum[] {
                            EstadoAsientoRegistralEnum.PENDIENTE_ENVIO,
                            EstadoAsientoRegistralEnum.ENVIADO,
                            EstadoAsientoRegistralEnum.ENVIADO_Y_ACK,
                            EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR,
                            EstadoAsientoRegistralEnum.RECHAZADO,
                            EstadoAsientoRegistralEnum.RECHAZADO_Y_ACK,
                            EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR,
                            EstadoAsientoRegistralEnum.REENVIADO,
                            EstadoAsientoRegistralEnum.REENVIADO_Y_ACK,
                            EstadoAsientoRegistralEnum.REENVIADO_Y_ERROR,
                            EstadoAsientoRegistralEnum.ANULADO,
                            EstadoAsientoRegistralEnum.VALIDADO,
                            EstadoAsientoRegistralEnum.DEVUELTO,
                            EstadoAsientoRegistralEnum.RECIBIDO }))
            .addOrderBy(CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO)
            .setPageInfo(pageInfo);
    }

    public static CriteriosVO createCriteriosVO_Asientos_Interesados() {
        return new CriteriosVO()
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO,
                    OperadorCriterioEnum.EQUAL,
                    "ER0000000000000000001_10_10000001"))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_FECHA_REGISTRO,
                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
                    new Date()))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_ESTADO,
                    OperadorCriterioEnum.IN,
                    new EstadoAsientoRegistralEnum[] {
                            EstadoAsientoRegistralEnum.PENDIENTE_ENVIO,
                            EstadoAsientoRegistralEnum.ENVIADO,
                            EstadoAsientoRegistralEnum.RECHAZADO,
                            EstadoAsientoRegistralEnum.DEVUELTO }))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_CODIGO_ASUNTO,
                    OperadorCriterioEnum.LIKE,
                    "ASUNTO"))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.INTERESADO_RAZON_SOCIAL_INTERESADO,
                    OperadorCriterioEnum.LIKE,
                    "Corte"))
            .addOrderBy(CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO)
            .addOrderBy(CriterioEnum.INTERESADO_RAZON_SOCIAL_INTERESADO);
    }

    public static CriteriosVO createCriteriosVO_Asientos_Anexos() {
        return new CriteriosVO()
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO,
                    OperadorCriterioEnum.EQUAL,
                    "ER0000000000000000001_10_10000001"))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_FECHA_REGISTRO,
                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
                    new Date()))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_ESTADO,
                    OperadorCriterioEnum.IN,
                    new EstadoAsientoRegistralEnum[] {
                            EstadoAsientoRegistralEnum.PENDIENTE_ENVIO,
                            EstadoAsientoRegistralEnum.ENVIADO,
                            EstadoAsientoRegistralEnum.ENVIADO_Y_ACK,
                            EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR,
                            EstadoAsientoRegistralEnum.RECHAZADO,
                            EstadoAsientoRegistralEnum.RECHAZADO_Y_ACK,
                            EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR,
                            EstadoAsientoRegistralEnum.DEVUELTO }))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_CODIGO_ASUNTO,
                    OperadorCriterioEnum.LIKE,
                    "ASUNTO"))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ANEXO_NOMBRE_FICHERO,
                    "documento.gif"))
            .addOrderBy(CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO)
            .addOrderBy(CriterioEnum.ANEXO_NOMBRE_FICHERO);
    }

    public static CriteriosVO createCriteriosVO_Asientos_Interesados_Anexos() {
        return new CriteriosVO()
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO,
                    OperadorCriterioEnum.EQUAL,
                    "ER0000000000000000001_10_10000001"))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_FECHA_REGISTRO,
                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
                    new Date()))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_ESTADO,
                    OperadorCriterioEnum.IN,
                    new EstadoAsientoRegistralEnum[] {
                            EstadoAsientoRegistralEnum.PENDIENTE_ENVIO,
                            EstadoAsientoRegistralEnum.ENVIADO,
                            EstadoAsientoRegistralEnum.ENVIADO_Y_ACK,
                            EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR,
                            EstadoAsientoRegistralEnum.RECHAZADO,
                            EstadoAsientoRegistralEnum.RECHAZADO_Y_ACK,
                            EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR,
                            EstadoAsientoRegistralEnum.DEVUELTO }))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ASIENTO_CODIGO_ASUNTO,
                    OperadorCriterioEnum.LIKE,
                    "ASUNTO"))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.INTERESADO_RAZON_SOCIAL_INTERESADO,
                    OperadorCriterioEnum.LIKE,
                    "Corte"))
            .addCriterioVO(new CriterioVO(
                    CriterioEnum.ANEXO_NOMBRE_FICHERO,
                    "documento.gif"))
            .addOrderBy(CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO)
            .addOrderBy(CriterioEnum.INTERESADO_RAZON_SOCIAL_INTERESADO)
            .addOrderBy(CriterioEnum.ANEXO_NOMBRE_FICHERO);
    }

    public static AsientoRegistralFormVO createDefaultAsientoRegistralFormVO() {

        AsientoRegistralFormVO asiento = new AsientoRegistralFormVO();

        asiento.setCodigoEntidadRegistral("ER0000000000000000001");
        asiento.setCodigoEntidadRegistralOrigen("ER0000000000000000001");
        asiento.setDescripcionEntidadRegistralOrigen("Entidad Registral ER0000000000000000001");
        asiento.setCodigoUnidadTramitacionOrigen("UT0000000000000000001");
        asiento.setDescripcionUnidadTramitacionOrigen("Unidad de Tramitación UT0000000000000000001");
        asiento.setCodigoEntidadRegistralDestino("ER0000000000000000002");
        asiento.setDescripcionEntidadRegistralDestino("Entidad Registral ER0000000000000000002");
        asiento.setCodigoUnidadTramitacionDestino("UT0000000000000000002");
        asiento.setDescripcionUnidadTramitacionDestino("Unidad de Tramitación UT0000000000000000002");
        asiento.setCodigoEntidadRegistralInicio(asiento.getCodigoEntidadRegistralOrigen());
        asiento.setDescripcionEntidadRegistralInicio(asiento.getDescripcionEntidadRegistralOrigen());
        asiento.setNumeroRegistro("201000100000001");
        asiento.setFechaRegistro(new Date());
        asiento.setTimestampRegistro("***timestamp***".getBytes());
        asiento.setResumen("Resumen");
        asiento.setCodigoAsunto("ASUNTO0000000001");
        asiento.setReferenciaExterna("REF0000000000001");
        asiento.setNumeroExpediente("EXP2010/00001");
        asiento.setTipoTransporte(TipoTransporteEnum.FAX);
        asiento.setNumeroTransporte("99999");
        asiento.setNombreUsuario("usuario");
        asiento.setContactoUsuario("usuario@contacto.es");
        asiento.setTipoRegistro(TipoRegistroEnum.ENTRADA);
        asiento.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
        asiento.setObservacionesApunte("observaciones");
        asiento.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
        asiento.setExpone("expone");
        asiento.setSolicita("solicita");


        InteresadoFormVO interesado = new InteresadoFormVO();

        interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF);
        interesado.setDocumentoIdentificacionInteresado("A28855260");
        interesado.setRazonSocialInteresado("razonSocialInteresado");
        interesado.setNombreInteresado("");
        interesado.setPrimerApellidoInteresado("");
        interesado.setSegundoApellidoInteresado("");
        interesado.setCodigoPaisInteresado("0001");
        interesado.setCodigoProvinciaInteresado("05");
        interesado.setCodigoMunicipioInteresado("01544");
        interesado.setDireccionInteresado("direccionInteresado");
        interesado.setCodigoPostalInteresado("33004");
        interesado.setCorreoElectronicoInteresado("correoElectronico@interesado.es");
        interesado.setTelefonoInteresado("999999999");
        interesado.setDireccionElectronicaHabilitadaInteresado("deu");
        interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
        interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
        interesado.setDocumentoIdentificacionRepresentante("00000000T");
        interesado.setRazonSocialRepresentante("");
        interesado.setNombreRepresentante("nombreRepresentante");
        interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante");
        interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante");
        interesado.setCodigoPaisRepresentante("0001");
        interesado.setCodigoProvinciaRepresentante("05");
        interesado.setCodigoMunicipioRepresentante("01544");
        interesado.setDireccionRepresentante("direccionRepresentante");
        interesado.setCodigoPostalRepresentante("33004");
        interesado.setCorreoElectronicoRepresentante("correoElectronico@representante.es");
        interesado.setTelefonoRepresentante("666666666");
        interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
        interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
        interesado.setObservaciones("observaciones");

        asiento.getInteresados().add(interesado);


        AnexoFormVO anexo = new AnexoFormVO();
        anexo.setNombreFichero("fichero1.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_COMPULSADA);
        anexo.setTipoDocumento(TipoDocumentoEnum.FICHERO_TECNICO_INTERNO);
        anexo.setCertificado(Base64.decodeBase64("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlGMnpDQ0JNT2dBd0lCQWdJRVJKbmxrekFOQmdrcWhraUc5dzBCQVFVRkFEQmNNUXN3Q1FZRFZRUUdFd0pGDQpVekVvTUNZR0ExVUVDZ3dmUkVsU1JVTkRTVTlPSUVkRlRrVlNRVXdnUkVVZ1RFRWdVRTlNU1VOSlFURU5NQXNHDQpBMVVFQ3d3RVJFNUpSVEVVTUJJR0ExVUVBd3dMUVVNZ1JFNUpSU0F3TURJd0hoY05NRGt3T1RJNU1Ea3lNakEzDQpXaGNOTVRJd016STVNRGsxTWpBMVdqQjVNUXN3Q1FZRFZRUUdFd0pGVXpFU01CQUdBMVVFQlJNSk1EazBNamN4DQpPVE5GTVJJd0VBWURWUVFFREFsR1JWSk9RVTVFUlZveERqQU1CZ05WQkNvTUJVUkJWa2xFTVRJd01BWURWUVFEDQpEQ2xHUlZKT1FVNUVSVm9nUVV4V1FWSkZXaXdnUkVGV1NVUWdLRUZWVkVWT1ZFbERRVU5KdzVOT0tUQ0NBU0l3DQpEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDV6Z2J6ZlRDWmh1V3dia3RobFJsSlg3eFlNDQpvanBLNFJFVkhOMHpuV01QQTdxNzhqaGoyYlRsU1NaQXdGSFJqc0RPUUdDTlJmQzc4WWRhQStNTm92Rk1TK1crDQpKRW41dGxjRUZ0OTdUb0RZenhQbThTVG81dEFJODNndTZuNXRUY2pWbyt1Q2dTRFFBcnUxanVpRFpVMGNRdm9ZDQpUMmF6WDlIbXVPMkcwUHcvNmVDMEdkSDFHZEtSZFJoelpJVDFCaEFMNytQU2swN1IvWGhXb0FDUFBYMGYzbHcxDQpHVmNCZTZOOGlVWUU1S3NKOHdUZUpxZjBHNEFndTVGSW8zV1RKZjFhWE1YZzJTRC8zTjhlZ2JnWXB6a1RVZU4wDQoxRmdmMjRVblo3M1hVL1BPRHZseExNQWlnT29ha2gyN0QxUkY3ZFkxanZSZ1A3STNsQkxMa3k3WUxFVUNBd0VBDQpBYU9DQW9Zd2dnS0NNQTRHQTFVZER3RUIvd1FFQXdJSGdEQW9CZ05WSFFrRUlUQWZNQjBHQ0NzR0FRVUZCd2tCDQpNUkVZRHpFNU56Y3dNVEl6TVRJd01EQXdXakJDQmdoZ2hWUUJBZ0lFQVFRMk1EUXdNZ0lCQWpBTEJnbGdoa2dCDQpaUU1FQWdFRUlDSUI1cm85UjdWSm1XeXF5S1Q5bFFlMmlNMDA3VmVVYzFaWE1GaE1LZWswTUlId0JnZ3JCZ0VGDQpCUWNCQWdTQjR6Q0I0REF5QWdFQk1Bc0dDV0NHU0FGbEF3UUNBUVFnR0RrNHkzQTY2U1oxelJpbi90NjNzMjVnDQowZkVVOWI4UE9hT2dYdFRPRE5Fd01nSUJBREFMQmdsZ2hrZ0JaUU1FQWdFRUlJbDJqSVY4UnFRYmdjS0JaNW54DQpPSzhETVJqNWN4d0NEOGo1dk9DZnR5aUZNRG9HQ1dDRlZBRUNBZ1FDQVRBTEJnbGdoa2dCWlFNRUFnRUVJRUVyDQpVazFJcFBTeDM3RE5KRUNSRFRuQmw4VnFKUGJKS0liNjFUZVVjUXU3TURvR0NXQ0ZWQUVDQWdRQ0JqQUxCZ2xnDQpoa2dCWlFNRUFnRUVJRHVSbXc4RkVOdDNpNTNPb2tDdmcxSVJKaWI1VS9tY3JNVHFmL2VvZXcvNE1Bd0dBMVVkDQpFd0VCL3dRQ01BQXdJZ1lJS3dZQkJRVUhBUU1FRmpBVU1BZ0dCZ1FBamtZQkFUQUlCZ1lFQUk1R0FRUXdZQVlJDQpLd1lCQlFVSEFRRUVWREJTTUI4R0NDc0dBUVVGQnpBQmhoTm9kSFJ3T2k4dmIyTnpjQzVrYm1sbExtVnpNQzhHDQpDQ3NHQVFVRkJ6QUNoaU5vZEhSd09pOHZkM2QzTG1SdWFXVXVaWE12WTJWeWRITXZRVU5TWVdsNkxtTnlkREE3DQpCZ05WSFNBRU5EQXlNREFHQ0dDRlZBRUNBZ0lFTUNRd0lnWUlLd1lCQlFVSEFnRVdGbWgwZEhBNkx5OTNkM2N1DQpaRzVwWlM1bGN5OWtjR013SHdZRFZSMGpCQmd3Rm9BVU9xYUo3QlhvSkdSeDRDVit5YkZpTVFmcEJxSXdIUVlEDQpWUjBPQkJZRUZDQll5cnN5S2hIbzN5dzBYazJVUHJicWtDdEJNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUJBUUJ6DQpBaXYrSS9IVnFzVk00TXpyRlJtTHBQMU9JLzVYMDB1YWhjNUFPbGUwaVkwWm5PcjV6TnpNbExBRFVUcGJSQ0VvDQpnZWV1c2ZPR1dkTHQvdjY1N0pucHBNb083cEs2OVo2c2hVT2R1Q0MvaEdKc0tHL1JBUXoyakNOV05IamRCYUNEDQp2TlFNcGFKSkc3MW8wSWRQY0hPYUlEbmNEQUMxQXQzNHRGZTdRMFlqU2JNTkpCTkFRRzY2eC9nWW1iRVhncWlqDQp2eDVUZktmQ05UbG9ZclBnSk5PSUNjV1pmbmRlemRZblIvMTc2NTB1SE02UndJeld2TjdMU1UzSWVkWFVQUGo1DQpaZEdmZjdWT294cW5MSklQcFNUOFBoOCtNUjExYi9MTTJXU3JseXUybUNhMTFlSUdranl1ZXJoNTRzWWxPK3VJDQpuaHdzRkJxKzBxZk9zRkg2T1JnMA0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K"));
        //anexo.setCertificado(Base64.decodeToBytes("MIIEEDCCAvigAwIBAgIKXz4SMAAAAAAAFzANBgkqhkiG9w0BAQUFADAUMRIwEAYDVQQDEwlDRVggVEQtV0YwHhcNMTExMDAzMTEzMDIxWhcNMTIxMDAzMTE0MDIxWjBUMTAwLgYDVQQDEydOT01CUkUgSlVBTiBHT01FWiBMT1BFWiAtIE5JRiAwNTI2MTA0MkUxIDAeBgkqhkiG9w0BCQEWEWp1YW5nb21lekBtYWlsLmVzMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDESbpCclY2QjG/E8ic2lHz6P4QXrwTWir4psFvG6fnsAezPAhfAXc4pTgvA+hjHziyTG5LwgPYhCAOGxG2etuJBj2lLAUl5JnT1cCunu5OZY6y6gHbxu28Rg7BhbG7xv2rzRHmTp04hdum5ow+pGmRmftIlZdEUczZlvoFPBS3+QIDAQABo4IBpjCCAaIwDgYDVR0PAQH/BAQDAgTwMEQGCSqGSIb3DQEJDwQ3MDUwDgYIKoZIhvcNAwICAgCAMA4GCCqGSIb3DQMEAgIAgDAHBgUrDgMCBzAKBggqhkiG9w0DBzAdBgNVHQ4EFgQUJ6Xfug0ZGRW684OTiH1BQbH/FhEwEwYDVR0lBAwwCgYIKwYBBQUHAwIwHwYDVR0jBBgwFoAUZ3m9+NCNmU6vXDr0oWgt/P/NI3owZQYDVR0fBF4wXDBaoFigVoYpaHR0cDovL3Rkb2NkYzEvQ2VydEVucm9sbC9DRVglMjBURC1XRi5jcmyGKWZpbGU6Ly9cXHRkb2NkYzFcQ2VydEVucm9sbFxDRVggVEQtV0YuY3JsMIGNBggrBgEFBQcBAQSBgDB+MD0GCCsGAQUFBzAChjFodHRwOi8vdGRvY2RjMS9DZXJ0RW5yb2xsL3Rkb2NkYzFfQ0VYJTIwVEQtV0YuY3J0MD0GCCsGAQUFBzAChjFmaWxlOi8vXFx0ZG9jZGMxXENlcnRFbnJvbGxcdGRvY2RjMV9DRVggVEQtV0YuY3J0MA0GCSqGSIb3DQEBBQUAA4IBAQAfUkcLDtqW8nKDXpi1orYBesEwRIiE3QniXwiVlynt544aNSTstmvn/IPafbsHTNGvfQ2uGTsoce2GMrUJaWosUQOS4U1gB/6IyPWEV+NQ93yHZst7A8iNjeWPja4qk5pbDBRnkCHnfyCY5HHWgEJcRaLNHtkOP5kqd8xWrShSBYM7zx4Wv/3IvwwIDX70vDvtUjzEFz6J2+r1R894ApypBf8oEiNB73wIoNlfM7MQDtn+M+sI8iKgusDJbysacgRO5/EfesI+/FucJWnDwAShfeGXSNCfaL52ZAo04UP0IJGixOtCSHr/LSsJy8usMvr8MaCH9//B3oxnUHyVCu9g"));
        anexo.setFirma(Base64.decodeBase64("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"));
        //anexo.setFirma(Base64.decodeBase64("MIIFlwYJKoZIhvcNAQcCoIIFiDCCBYQCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCBBQwggQQMIIC+KADAgECAgpfPhIwAAAAAAAXMA0GCSqGSIb3DQEBBQUAMBQxEjAQBgNVBAMTCUNFWCBURC1XRjAeFw0xMTEwMDMxMTMwMjFaFw0xMjEwMDMxMTQwMjFaMFQxMDAuBgNVBAMTJ05PTUJSRSBKVUFOIEdPTUVaIExPUEVaIC0gTklGIDA1MjYxMDQyRTEgMB4GCSqGSIb3DQEJARYRanVhbmdvbWV6QG1haWwuZXMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMRJukJyVjZCMb8TyJzaUfPo/hBevBNaKvimwW8bp+ewB7M8CF8BdzilOC8D6GMfOLJMbkvCA9iEIA4bEbZ624kGPaUsBSXkmdPVwK6e7k5ljrLqAdvG7bxGDsGFsbvG/avNEeZOnTiF26bmjD6kaZGZ+0iVl0RRzNmW+gU8FLf5AgMBAAGjggGmMIIBojAOBgNVHQ8BAf8EBAMCBPAwRAYJKoZIhvcNAQkPBDcwNTAOBggqhkiG9w0DAgICAIAwDgYIKoZIhvcNAwQCAgCAMAcGBSsOAwIHMAoGCCqGSIb3DQMHMB0GA1UdDgQWBBQnpd+6DRkZFbrzg5OIfUFBsf8WETATBgNVHSUEDDAKBggrBgEFBQcDAjAfBgNVHSMEGDAWgBRneb340I2ZTq9cOvShaC38/80jejBlBgNVHR8EXjBcMFqgWKBWhilodHRwOi8vdGRvY2RjMS9DZXJ0RW5yb2xsL0NFWCUyMFRELVdGLmNybIYpZmlsZTovL1xcdGRvY2RjMVxDZXJ0RW5yb2xsXENFWCBURC1XRi5jcmwwgY0GCCsGAQUFBwEBBIGAMH4wPQYIKwYBBQUHMAKGMWh0dHA6Ly90ZG9jZGMxL0NlcnRFbnJvbGwvdGRvY2RjMV9DRVglMjBURC1XRi5jcnQwPQYIKwYBBQUHMAKGMWZpbGU6Ly9cXHRkb2NkYzFcQ2VydEVucm9sbFx0ZG9jZGMxX0NFWCBURC1XRi5jcnQwDQYJKoZIhvcNAQEFBQADggEBAB9SRwsO2pbycoNemLWitgF6wTBEiITdCeJfCJWXKe3njho1JOy2a+f8g9p9uwdM0a99Da4ZOyhx7YYytQlpaixRA5LhTWAH/ojI9YRX41D3fIdmy3sDyI2N5Y+NriqTmlsMFGeQIed/IJjkcdaAQlxFos0e2Q4/mSp3zFatKFIFgzvPHha//ci/DAgNfvS8O+1SPMQXPonb6vVHz3gCnKkF/ygSI0HvfAig2V8zsxAO2f4z6wjyIqC6wMlvKxpyBE7n8R96wj78W5wlacPABKF94ZdI0J9ovnZkCjThQ/QgkaLE60JIev8tKwnLy6wy+vwxoIf3/8HejGdQfJUK72AxggFLMIIBRwIBATAiMBQxEjAQBgNVBAMTCUNFWCBURC1XRgIKXz4SMAAAAAAAFzAJBgUrDgMCGgUAoIGAMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkFMQ8XDTEyMDIyNzEyMjAyM1owIQYDVQQFMRoTGDQ0OTc2OTgyNDU1MjY3Nzg1MjMxNTY3MTAjBgkqhkiG9w0BCQQxFgQU2+jnBPTa+Tjrlh1JY9dqnSI4OtkwDQYJKoZIhvcNAQEBBQAEgYC63dNbdwFyRkJmEq1soJ7qUqpkqtojO9wwBH4RYwVlW72aZZ2QGaOXY02AIgvQ5OhcW68ZH574DWfJmWJDzqIEudkoAryvoRxtbxFsrAwW3cwEd13lh+nxlN4apem8kCZ734K1lXVmttcCBgHNb/j5IoV3M8aFVp6MSbqEjigBeA=="));
        anexo.setTimestamp("***timestmap***".getBytes());
        anexo.setValidacionOCSPCertificado(Base64.decodeBase64("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"));
        anexo.setHash("hash".getBytes());
        anexo.setTipoMIME("text/plain");
        anexo.setObservaciones("Observaciones al fichero 1");
        anexo.setContenido("Contenido del anexo".getBytes());
        anexo.setCodigoFichero("COD_001");

        asiento.getAnexos().add(anexo);

        anexo = new AnexoFormVO();
        anexo.setNombreFichero("fichero2.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_COMPULSADA);
        anexo.setTipoDocumento(TipoDocumentoEnum.FICHERO_TECNICO_INTERNO);
        anexo.setCertificado(Base64.decodeBase64("MIID0zCCArugAwIBAgIKVd9kNAAAAAAADjANBgkqhkiG9w0BAQUFADAUMRIwEAYDVQQDEwlDRVggVEQtV0YwHhcNMTEwMzIyMTQyMjI4WhcNMTIwMzIyMTQzMjI4WjAXMRUwEwYDVQQDDAxSYcO6bCBOdcOxZXowgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMB+T29tyeYf0kKMNH6SUKjfdeCfpHuBmhZt43nxTKJiYR5Qt/tYAsvOwGQc8hgcASel+tbyk3/4TpmjGJVZKnqcYFFCkchbBDIJ2J4lFaNLU+Xnpjj5Yp7YwcXAVFXNBqumlV8U8VI8vKu0frEILM/Et7TzXaTPUHMU3I8Fo0GHAgMBAAGjggGmMIIBojAOBgNVHQ8BAf8EBAMCBPAwRAYJKoZIhvcNAQkPBDcwNTAOBggqhkiG9w0DAgICAIAwDgYIKoZIhvcNAwQCAgCAMAcGBSsOAwIHMAoGCCqGSIb3DQMHMB0GA1UdDgQWBBRomqmxGm2Va3jf7RHNdp2ahHOUbTATBgNVHSUEDDAKBggrBgEFBQcDAzAfBgNVHSMEGDAWgBRneb340I2ZTq9cOvShaC38/80jejBlBgNVHR8EXjBcMFqgWKBWhilodHRwOi8vdGRvY2RjMS9DZXJ0RW5yb2xsL0NFWCUyMFRELVdGLmNybIYpZmlsZTovL1xcdGRvY2RjMVxDZXJ0RW5yb2xsXENFWCBURC1XRi5jcmwwgY0GCCsGAQUFBwEBBIGAMH4wPQYIKwYBBQUHMAKGMWh0dHA6Ly90ZG9jZGMxL0NlcnRFbnJvbGwvdGRvY2RjMV9DRVglMjBURC1XRi5jcnQwPQYIKwYBBQUHMAKGMWZpbGU6Ly9cXHRkb2NkYzFcQ2VydEVucm9sbFx0ZG9jZGMxX0NFWCBURC1XRi5jcnQwDQYJKoZIhvcNAQEFBQADggEBAHqVinteAuIsN4pSkLqyYmvWO18wp8/zCTBuqhjeCPHk7+e+FutE55quu0ffoPKRpx48ixFDDmM9e985LeYbkr9f28UmrEIWb4tPdo69xGD9lBu3yesATC0vxWnOsx2o2DYXAliF0s+/OPsIllQFImNm0+GxNui5KfN3s5UfOldqiCIdNNUbL9S1FRzOr317Ny4R2xv+G+eFTUtwiGnoHh5QVzUIeGltu3RLc8L3rXVUCYFq+7Bsxpz0lOjwGNoPEbi3nDmnPKvYpuw2kWjDq47OP21spXKYz3nXexFpSEIxJIuwfjufCS+EH5IizVX3fkXvO6lDuozhYhjNI/BEpGI="));
        anexo.setFirma(null);
        anexo.setTimestamp("***timestmap***".getBytes());
        anexo.setValidacionOCSPCertificado(null);
        anexo.setTipoMIME("text/plain");
        anexo.setObservaciones("Observaciones al fichero 1");
        anexo.setContenido("Contenido del anexo".getBytes());
        anexo.setCodigoFichero("COD_002");
        anexo.setCodigoFicheroFirmado("COD_002"); // Firma embebida

        asiento.getAnexos().add(anexo);

        anexo = new AnexoFormVO();
        anexo.setNombreFichero("fichero3.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_ORIGINAL);
        anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
        anexo.setCertificado(Base64.decodeBase64("MIID0zCCArugAwIBAgIKVd9kNAAAAAAADjANBgkqhkiG9w0BAQUFADAUMRIwEAYDVQQDEwlDRVggVEQtV0YwHhcNMTEwMzIyMTQyMjI4WhcNMTIwMzIyMTQzMjI4WjAXMRUwEwYDVQQDDAxSYcO6bCBOdcOxZXowgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMB+T29tyeYf0kKMNH6SUKjfdeCfpHuBmhZt43nxTKJiYR5Qt/tYAsvOwGQc8hgcASel+tbyk3/4TpmjGJVZKnqcYFFCkchbBDIJ2J4lFaNLU+Xnpjj5Yp7YwcXAVFXNBqumlV8U8VI8vKu0frEILM/Et7TzXaTPUHMU3I8Fo0GHAgMBAAGjggGmMIIBojAOBgNVHQ8BAf8EBAMCBPAwRAYJKoZIhvcNAQkPBDcwNTAOBggqhkiG9w0DAgICAIAwDgYIKoZIhvcNAwQCAgCAMAcGBSsOAwIHMAoGCCqGSIb3DQMHMB0GA1UdDgQWBBRomqmxGm2Va3jf7RHNdp2ahHOUbTATBgNVHSUEDDAKBggrBgEFBQcDAzAfBgNVHSMEGDAWgBRneb340I2ZTq9cOvShaC38/80jejBlBgNVHR8EXjBcMFqgWKBWhilodHRwOi8vdGRvY2RjMS9DZXJ0RW5yb2xsL0NFWCUyMFRELVdGLmNybIYpZmlsZTovL1xcdGRvY2RjMVxDZXJ0RW5yb2xsXENFWCBURC1XRi5jcmwwgY0GCCsGAQUFBwEBBIGAMH4wPQYIKwYBBQUHMAKGMWh0dHA6Ly90ZG9jZGMxL0NlcnRFbnJvbGwvdGRvY2RjMV9DRVglMjBURC1XRi5jcnQwPQYIKwYBBQUHMAKGMWZpbGU6Ly9cXHRkb2NkYzFcQ2VydEVucm9sbFx0ZG9jZGMxX0NFWCBURC1XRi5jcnQwDQYJKoZIhvcNAQEFBQADggEBAHqVinteAuIsN4pSkLqyYmvWO18wp8/zCTBuqhjeCPHk7+e+FutE55quu0ffoPKRpx48ixFDDmM9e985LeYbkr9f28UmrEIWb4tPdo69xGD9lBu3yesATC0vxWnOsx2o2DYXAliF0s+/OPsIllQFImNm0+GxNui5KfN3s5UfOldqiCIdNNUbL9S1FRzOr317Ny4R2xv+G+eFTUtwiGnoHh5QVzUIeGltu3RLc8L3rXVUCYFq+7Bsxpz0lOjwGNoPEbi3nDmnPKvYpuw2kWjDq47OP21spXKYz3nXexFpSEIxJIuwfjufCS+EH5IizVX3fkXvO6lDuozhYhjNI/BEpGI="));
        anexo.setFirma(null);
        anexo.setTimestamp("***timestmap***".getBytes());
        anexo.setValidacionOCSPCertificado(null);
        anexo.setTipoMIME("text/plain");
        anexo.setObservaciones("Observaciones al fichero 3");
        anexo.setContenido("Contenido del anexo".getBytes());
        anexo.setCodigoFichero("COD_003");

        asiento.getAnexos().add(anexo);

        anexo = new AnexoFormVO();
        anexo.setNombreFichero("fichero3_firma.p7s");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_ORIGINAL);
        anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
        anexo.setCertificado(null);
        anexo.setFirma(null);
        anexo.setTimestamp(null);
        anexo.setValidacionOCSPCertificado(null);
        anexo.setTipoMIME("application/pkcs7-signature");
        anexo.setCodigoFicheroFirmado("COD_003");
        anexo.setObservaciones("Firma del fichero 3");
        anexo.setContenido(Base64.decodeBase64("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"));

        asiento.getAnexos().add(anexo);

        return asiento;
    }

    public static AsientoRegistralVO createDefaultAsientoRegistralVO() {
        return createDefaultAsientoRegistralVO(null);
    }

    public static AsientoRegistralVO createDefaultAsientoRegistralVO(String id) {

        AsientoRegistralVO asiento = new AsientoRegistralVO();

        asiento.setId(id);
        asiento.setCodigoEntidadRegistral("ER0000000000000000001");
        asiento.setCodigoEntidadRegistralOrigen("ER0000000000000000001");
        asiento.setDescripcionEntidadRegistralOrigen("Entidad Registral ER0000000000000000001");
        asiento.setCodigoUnidadTramitacionOrigen("UT0000000000000000001");
        asiento.setDescripcionUnidadTramitacionOrigen("Unidad de Tramitación UT0000000000000000001");
        asiento.setCodigoEntidadRegistralDestino("ER0000000000000000002");
        asiento.setDescripcionEntidadRegistralDestino("Entidad Registral ER0000000000000000002");
        asiento.setCodigoUnidadTramitacionDestino("UT0000000000000000002");
        asiento.setDescripcionUnidadTramitacionDestino("Unidad de Tramitación UT0000000000000000002");
        asiento.setNumeroRegistro("201000100000001");
        asiento.setFechaRegistro(new Date());
        asiento.setTimestampRegistro("***timestamp***".getBytes());
        asiento.setNumeroRegistroInicial("201000100000001");
        asiento.setFechaRegistroInicial(new Date());
        asiento.setTimestampRegistroInicial("***timestamp***".getBytes());
        asiento.setResumen("Resumen");
        asiento.setCodigoAsunto("ASUNTO0000000001");
        asiento.setReferenciaExterna("REF0000000000001");
        asiento.setNumeroExpediente("EXP2010/00001");
        asiento.setTipoTransporte(TipoTransporteEnum.FAX);
        asiento.setNumeroTransporte("99999");
        asiento.setNombreUsuario("usuario");
        asiento.setContactoUsuario("usuario@contacto.es");
        asiento.setIdentificadorIntercambio("ER0000000000000000001_10_99999999");
        asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
        asiento.setFechaEstado(new Date());
        asiento.setAplicacion("app");
        asiento.setTipoRegistro(TipoRegistroEnum.ENTRADA);
        asiento.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
        asiento.setObservacionesApunte("observaciones");
        asiento.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
        asiento.setCodigoEntidadRegistralInicio("ER0000000000000000001");
        asiento.setDescripcionEntidadRegistralInicio("Entidad Registral ER0000000000000000001");
        asiento.setExpone("expone");
        asiento.setSolicita("solicita");

        // Interesados
        List<InteresadoVO> interesados = new ArrayList<InteresadoVO>();
        interesados.add(createDefaultInteresadoVO());
        asiento.setInteresados(interesados);

        // Anexos
        List<AnexoVO> anexos = new ArrayList<AnexoVO>();
        anexos.add(createDefaultAnexoVO());
        asiento.setAnexos(anexos);

        return asiento;
    }

    public static InteresadoFormVO createDefaultInteresadoFormVO() {

        InteresadoFormVO interesado = new InteresadoFormVO();

        interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF);
        interesado.setDocumentoIdentificacionInteresado("A28855260");
        interesado.setRazonSocialInteresado("razonSocialInteresado");
        interesado.setNombreInteresado("");
        interesado.setPrimerApellidoInteresado("");
        interesado.setSegundoApellidoInteresado("");
        interesado.setCodigoPaisInteresado("0001");
        interesado.setCodigoProvinciaInteresado("05");
        interesado.setCodigoMunicipioInteresado("01544");
        interesado.setDireccionInteresado("direccionInteresado");
        interesado.setCodigoPostalInteresado("33004");
        interesado.setCorreoElectronicoInteresado("correoElectronico@interesado.es");
        interesado.setTelefonoInteresado("999999999");
        interesado.setDireccionElectronicaHabilitadaInteresado("deu");
        interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
        interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
        interesado.setDocumentoIdentificacionRepresentante("00000000T");
        interesado.setRazonSocialRepresentante("");
        interesado.setNombreRepresentante("nombreRepresentante");
        interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante");
        interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante");
        interesado.setCodigoPaisRepresentante("0001");
        interesado.setCodigoProvinciaRepresentante("05");
        interesado.setCodigoMunicipioRepresentante("01544");
        interesado.setDireccionRepresentante("direccionRepresentante");
        interesado.setCodigoPostalRepresentante("33004");
        interesado.setCorreoElectronicoRepresentante("correoElectronico@representante.es");
        interesado.setTelefonoRepresentante("666666666");
        interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
        interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
        interesado.setObservaciones("observaciones");

        return interesado;
    }

    public static InteresadoVO createDefaultInteresadoVO() {

        InteresadoVO interesado = new InteresadoVO();

        //interesado.setId(anId);
        //interesado.setIdAsientoRegistral(idAsientoRegistral);
        interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF);
        interesado.setDocumentoIdentificacionInteresado("A28855260");
        interesado.setRazonSocialInteresado("razonSocialInteresado");
        interesado.setNombreInteresado("");
        interesado.setPrimerApellidoInteresado("");
        interesado.setSegundoApellidoInteresado("");
        interesado.setCodigoPaisInteresado("0001");
        interesado.setCodigoProvinciaInteresado("05");
        interesado.setCodigoMunicipioInteresado("01544");
        interesado.setDireccionInteresado("direccionInteresado");
        interesado.setCodigoPostalInteresado("33004");
        interesado.setCorreoElectronicoInteresado("correoElectronico@interesado.es");
        interesado.setTelefonoInteresado("999999999");
        interesado.setDireccionElectronicaHabilitadaInteresado("deu");
        interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
        interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
        interesado.setDocumentoIdentificacionRepresentante("00000000T");
        interesado.setRazonSocialRepresentante("");
        interesado.setNombreRepresentante("nombreRepresentante");
        interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante");
        interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante");
        interesado.setCodigoPaisRepresentante("0001");
        interesado.setCodigoProvinciaRepresentante("05");
        interesado.setCodigoMunicipioRepresentante("01544");
        interesado.setDireccionRepresentante("direccionRepresentante");
        interesado.setCodigoPostalRepresentante("33004");
        interesado.setCorreoElectronicoRepresentante("correoElectronico@representante.es");
        interesado.setTelefonoRepresentante("666666666");
        interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
        interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
        interesado.setObservaciones("observaciones");

        return interesado;
    }

    public static AnexoFormVO createDefaultAnexoFormVO() {

        AnexoFormVO anexo = new AnexoFormVO();

        anexo.setNombreFichero("texto.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_COMPULSADA);
        anexo.setTipoDocumento(TipoDocumentoEnum.FICHERO_TECNICO_INTERNO);
        anexo.setCertificado("***certificado***".getBytes());
        anexo.setFirma("***firma***".getBytes());
        anexo.setTimestamp("***timestmap***".getBytes());
        anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
        anexo.setHash(Base64.encodeBase64("hash".getBytes()));
        anexo.setTipoMIME("text/plain");
        anexo.setCodigoFichero("COD_FICHERO");
        anexo.setCodigoFicheroFirmado(anexo.getCodigoFichero());
        anexo.setObservaciones("Observaciones");
        anexo.setContenido("contenido".getBytes());

        return anexo;
    }

    public static AnexoVO createDefaultAnexoVO() {

        AnexoVO anexo = new AnexoVO();

        //anexo.setId(id);
        //anexo.setIdAsientoRegistral(idAsientoRegistral);
        anexo.setNombreFichero("texto.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_COMPULSADA);
        anexo.setTipoDocumento(TipoDocumentoEnum.FICHERO_TECNICO_INTERNO);
        anexo.setCertificado("***certficado***".getBytes());
        anexo.setFirma("***firma***".getBytes());
        anexo.setTimestamp("***timestamp***".getBytes());
        anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
        anexo.setTipoMIME("text/plain");
        anexo.setIdentificadorFicheroFirmado(null);
        anexo.setObservaciones("Observaciones");
        anexo.setHash(Base64.decodeBase64("KA+fgWIUmvhDdsq6Qx3odcFImHQ=")); //"contenido"

        return anexo;
    }

    public static void assertEquals(AsientoRegistralFormVO asiento, AsientoRegistralVO asientoCreado) {

        Assert.assertNotNull("Asiento registral", asiento);
        Assert.assertNotNull("Asiento registral creado", asientoCreado);

        Assert.assertNotNull(asientoCreado.getId());
        Assert.assertEquals(asiento.getCodigoEntidadRegistral(), asientoCreado.getCodigoEntidadRegistral());
        Assert.assertEquals(asiento.getCodigoEntidadRegistralOrigen(), asientoCreado.getCodigoEntidadRegistralOrigen());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralOrigen(), asientoCreado.getDescripcionEntidadRegistralOrigen());
        Assert.assertEquals(asiento.getNumeroRegistro(), asientoCreado.getNumeroRegistro());
        Assert.assertEquals(asiento.getFechaRegistro(), asientoCreado.getFechaRegistro());
        Assert.assertEquals(Base64.encodeBase64String(asiento.getTimestampRegistro()), Base64.encodeBase64String(asientoCreado.getTimestampRegistro()));
        Assert.assertNotNull(asientoCreado.getNumeroRegistroInicial());
        Assert.assertNotNull(asientoCreado.getFechaRegistroInicial());
        Assert.assertNotNull(asientoCreado.getTimestampRegistroInicial());
        Assert.assertEquals(asiento.getCodigoUnidadTramitacionOrigen(), asientoCreado.getCodigoUnidadTramitacionOrigen());
        Assert.assertEquals(asiento.getDescripcionUnidadTramitacionOrigen(), asientoCreado.getDescripcionUnidadTramitacionOrigen());
        Assert.assertEquals(asiento.getCodigoEntidadRegistralDestino(), asientoCreado.getCodigoEntidadRegistralDestino());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralDestino(), asientoCreado.getDescripcionEntidadRegistralDestino());
        Assert.assertEquals(asiento.getCodigoUnidadTramitacionDestino(), asientoCreado.getCodigoUnidadTramitacionDestino());
        Assert.assertEquals(asiento.getDescripcionUnidadTramitacionDestino(), asientoCreado.getDescripcionUnidadTramitacionDestino());
        Assert.assertEquals(asiento.getResumen(), asientoCreado.getResumen());
        Assert.assertEquals(asiento.getCodigoAsunto(), asientoCreado.getCodigoAsunto());
        Assert.assertEquals(asiento.getReferenciaExterna(), asientoCreado.getReferenciaExterna());
        Assert.assertEquals(asiento.getNumeroExpediente(), asientoCreado.getNumeroExpediente());
        Assert.assertEquals(asiento.getTipoTransporte(), asientoCreado.getTipoTransporte());
        Assert.assertEquals(asiento.getNumeroTransporte(), asientoCreado.getNumeroTransporte());
        Assert.assertEquals(asiento.getNombreUsuario(), asientoCreado.getNombreUsuario());
        Assert.assertEquals(asiento.getContactoUsuario(), asientoCreado.getContactoUsuario());
        //Assert.assertNotNull(asientoCreado.getIdentificadorIntercambio());
        Assert.assertNotNull(asientoCreado.getEstado());
        //Assert.assertNotNull(asientoCreado.getAplicacion());
        Assert.assertEquals(asiento.getTipoRegistro(), asientoCreado.getTipoRegistro());
        Assert.assertEquals(asiento.getDocumentacionFisica(), asientoCreado.getDocumentacionFisica());
        Assert.assertEquals(asiento.getObservacionesApunte(), asientoCreado.getObservacionesApunte());
        Assert.assertEquals(asiento.getIndicadorPrueba(), asientoCreado.getIndicadorPrueba());
        Assert.assertNotNull(asientoCreado.getCodigoEntidadRegistralInicio());
        Assert.assertNotNull(asientoCreado.getDescripcionEntidadRegistralInicio());
        Assert.assertEquals(asiento.getExpone(), asientoCreado.getExpone());
        Assert.assertEquals(asiento.getSolicita(), asientoCreado.getSolicita());
    }

    public static void assertEquals(AsientoRegistralVO asiento, AsientoRegistralVO asientoCreado) {

        assertEqualsSimple(asiento, asientoCreado);

        if (CollectionUtils.isNotEmpty(asiento.getInteresados())) {

            Assert.assertNotNull(asientoCreado.getInteresados());
            Assert.assertEquals(asiento.getInteresados().size(), asientoCreado.getInteresados().size());

            for (int i = 0; i < asiento.getInteresados().size(); i++) {

                InteresadoVO interesado = asiento.getInteresados().get(i);
                Assert.assertNotNull(interesado);

                InteresadoVO interesadoCreado = asientoCreado.getInteresados().get(i);
                assertEquals(interesado, interesadoCreado);
            }
        }

        if (CollectionUtils.isNotEmpty(asiento.getAnexos())) {

            Assert.assertNotNull(asientoCreado.getAnexos());
            Assert.assertEquals(asiento.getAnexos().size(), asientoCreado.getAnexos().size());

            for (int i = 0; i < asiento.getAnexos().size(); i++) {

                AnexoVO anexo = asiento.getAnexos().get(i);
                Assert.assertNotNull(anexo);

                AnexoVO anexoCreado = asientoCreado.getAnexos().get(i);
                assertEquals(anexo, anexoCreado);
            }
        }

    }

    public static void assertEqualsSimple(AsientoRegistralVO asiento, AsientoRegistralVO asientoCreado) {

        Assert.assertNotNull("Asiento registral", asiento);
        Assert.assertNotNull("Asiento registral creado", asientoCreado);

        Assert.assertNotNull(asientoCreado.getId());
        Assert.assertEquals(asiento.getCodigoEntidadRegistral(), asientoCreado.getCodigoEntidadRegistral());
        Assert.assertEquals(asiento.getCodigoEntidadRegistralOrigen(), asientoCreado.getCodigoEntidadRegistralOrigen());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralOrigen(), asientoCreado.getDescripcionEntidadRegistralOrigen());
        Assert.assertEquals(asiento.getNumeroRegistro(), asientoCreado.getNumeroRegistro());
        Assert.assertEquals(asiento.getFechaRegistro().toString(), asientoCreado.getFechaRegistro().toString());
        Assert.assertEquals(Base64.encodeBase64String(asiento.getTimestampRegistro()), Base64.encodeBase64String(asientoCreado.getTimestampRegistro()));
        Assert.assertEquals(asiento.getNumeroRegistroInicial(), asientoCreado.getNumeroRegistroInicial());
        Assert.assertEquals(asiento.getFechaRegistroInicial().toString(), asientoCreado.getFechaRegistroInicial().toString());
        Assert.assertEquals(Base64.encodeBase64String(asiento.getTimestampRegistroInicial()), Base64.encodeBase64String(asientoCreado.getTimestampRegistroInicial()));
        Assert.assertEquals(asiento.getCodigoUnidadTramitacionOrigen(), asientoCreado.getCodigoUnidadTramitacionOrigen());
        Assert.assertEquals(asiento.getDescripcionUnidadTramitacionOrigen(), asientoCreado.getDescripcionUnidadTramitacionOrigen());
        Assert.assertEquals(asiento.getCodigoEntidadRegistralDestino(), asientoCreado.getCodigoEntidadRegistralDestino());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralDestino(), asientoCreado.getDescripcionEntidadRegistralDestino());
        Assert.assertEquals(asiento.getCodigoUnidadTramitacionDestino(), asientoCreado.getCodigoUnidadTramitacionDestino());
        Assert.assertEquals(asiento.getDescripcionUnidadTramitacionDestino(), asientoCreado.getDescripcionUnidadTramitacionDestino());
        Assert.assertEquals(asiento.getResumen(), asientoCreado.getResumen());
        Assert.assertEquals(asiento.getCodigoAsunto(), asientoCreado.getCodigoAsunto());
        Assert.assertEquals(asiento.getReferenciaExterna(), asientoCreado.getReferenciaExterna());
        Assert.assertEquals(asiento.getNumeroExpediente(), asientoCreado.getNumeroExpediente());
        Assert.assertEquals(asiento.getTipoTransporte(), asientoCreado.getTipoTransporte());
        Assert.assertEquals(asiento.getNumeroTransporte(), asientoCreado.getNumeroTransporte());
        Assert.assertEquals(asiento.getNombreUsuario(), asientoCreado.getNombreUsuario());
        Assert.assertEquals(asiento.getContactoUsuario(), asientoCreado.getContactoUsuario());
        Assert.assertEquals(asiento.getIdentificadorIntercambio(), asientoCreado.getIdentificadorIntercambio());
        //Assert.assertEquals(asiento.getEstado(), asientoCreado.getEstado());
        Assert.assertEquals(asiento.getAplicacion(), asientoCreado.getAplicacion());
        Assert.assertEquals(asiento.getTipoAnotacion(), asientoCreado.getTipoAnotacion());
        Assert.assertEquals(asiento.getDescripcionTipoAnotacion(), asientoCreado.getDescripcionTipoAnotacion());
        Assert.assertEquals(asiento.getTipoRegistro(), asientoCreado.getTipoRegistro());
        Assert.assertEquals(asiento.getDocumentacionFisica(), asientoCreado.getDocumentacionFisica());
        Assert.assertEquals(asiento.getObservacionesApunte(), asientoCreado.getObservacionesApunte());
        Assert.assertEquals(asiento.getIndicadorPrueba(), asientoCreado.getIndicadorPrueba());
        Assert.assertEquals(asiento.getCodigoEntidadRegistralInicio(), asientoCreado.getCodigoEntidadRegistralInicio());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralInicio(), asientoCreado.getDescripcionEntidadRegistralInicio());
        Assert.assertEquals(asiento.getExpone(), asientoCreado.getExpone());
        Assert.assertEquals(asiento.getSolicita(), asientoCreado.getSolicita());
    }

    public static void assertEquals(AnexoFormVO anexoForm, AnexoVO anexo) {

        Assert.assertNotNull("AnexoFormVO", anexoForm);
        Assert.assertNotNull("AnexoVO", anexo);

        Assert.assertNotNull(anexo);
        Assert.assertNotNull(anexo.getId());

        Assert.assertEquals(anexoForm.getNombreFichero(), anexo.getNombreFichero());
        Assert.assertEquals(anexoForm.getValidezDocumento(), anexo.getValidezDocumento());
        Assert.assertEquals(anexoForm.getTipoDocumento(), anexo.getTipoDocumento());
        Assert.assertEquals(Base64.encodeBase64String(anexoForm.getCertificado()), Base64.encodeBase64String(anexo.getCertificado()));
        Assert.assertEquals(Base64.encodeBase64String(anexoForm.getFirma()), Base64.encodeBase64String(anexo.getFirma()));
        Assert.assertEquals(Base64.encodeBase64String(anexoForm.getTimestamp()), Base64.encodeBase64String(anexo.getTimestamp()));
        Assert.assertEquals(Base64.encodeBase64String(anexoForm.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexo.getValidacionOCSPCertificado()));
        Assert.assertEquals(anexoForm.getTipoMIME(), anexo.getTipoMIME());
        Assert.assertEquals(anexoForm.getObservaciones(), anexo.getObservaciones());
    }

    public static void assertEquals(AnexoVO anexo, AnexoVO anexo2) {

        Assert.assertNotNull("Anexo", anexo);
        Assert.assertNotNull("Anexo secundario", anexo2);

        Assert.assertNotNull(anexo2);
        Assert.assertNotNull(anexo2.getId());

        //Assert.assertEquals(anexo.getIdAsientoRegistral(), anexo2.getIdAsientoRegistral());
        Assert.assertEquals(anexo.getNombreFichero(), anexo2.getNombreFichero());
        Assert.assertEquals(anexo.getValidezDocumento(), anexo2.getValidezDocumento());
        Assert.assertEquals(anexo.getTipoDocumento(), anexo2.getTipoDocumento());
        Assert.assertEquals(Base64.encodeBase64String(anexo.getCertificado()), Base64.encodeBase64String(anexo2.getCertificado()));
        Assert.assertEquals(Base64.encodeBase64String(anexo.getFirma()), Base64.encodeBase64String(anexo2.getFirma()));
        Assert.assertEquals(Base64.encodeBase64String(anexo.getTimestamp()), Base64.encodeBase64String(anexo2.getTimestamp()));
        Assert.assertEquals(Base64.encodeBase64String(anexo.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexo2.getValidacionOCSPCertificado()));
        Assert.assertEquals(Base64.encodeBase64String(anexo.getHash()), Base64.encodeBase64String(anexo2.getHash()));
        Assert.assertEquals(anexo.getTipoMIME(), anexo2.getTipoMIME());
        Assert.assertEquals(anexo.getIdentificadorFicheroFirmado(), anexo2.getIdentificadorFicheroFirmado());
        Assert.assertEquals(anexo.getObservaciones(), anexo2.getObservaciones());
    }

    public static void assertEquals(InteresadoFormVO interesadoForm, InteresadoVO interesado) {

        Assert.assertNotNull("InteresadoFormVO", interesadoForm);
        Assert.assertNotNull("InteresadoVO", interesado);

        Assert.assertNotNull(interesado);
        Assert.assertNotNull(interesado.getId());

        //Assert.assertEquals(interesado.getIdAsientoRegistral(), interesado2.getIdAsientoRegistral());
        Assert.assertEquals(interesadoForm.getTipoDocumentoIdentificacionInteresado(), interesado.getTipoDocumentoIdentificacionInteresado());
        Assert.assertEquals(interesadoForm.getDocumentoIdentificacionInteresado(), interesado.getDocumentoIdentificacionInteresado());
        Assert.assertEquals(interesadoForm.getRazonSocialInteresado(), interesado.getRazonSocialInteresado());
        Assert.assertEquals(interesadoForm.getNombreInteresado(), interesado.getNombreInteresado());
        Assert.assertEquals(interesadoForm.getPrimerApellidoInteresado(), interesado.getPrimerApellidoInteresado());
        Assert.assertEquals(interesadoForm.getSegundoApellidoInteresado(), interesado.getSegundoApellidoInteresado());
        Assert.assertEquals(interesadoForm.getCodigoPaisInteresado(), interesado.getCodigoPaisInteresado());
        Assert.assertEquals(interesadoForm.getCodigoProvinciaInteresado(), interesado.getCodigoProvinciaInteresado());
        Assert.assertEquals(interesadoForm.getCodigoMunicipioInteresado(), interesado.getCodigoMunicipioInteresado());
        Assert.assertEquals(interesadoForm.getDireccionInteresado(), interesado.getDireccionInteresado());
        Assert.assertEquals(interesadoForm.getCodigoPostalInteresado(), interesado.getCodigoPostalInteresado());
        Assert.assertEquals(interesadoForm.getCorreoElectronicoInteresado(), interesado.getCorreoElectronicoInteresado());
        Assert.assertEquals(interesadoForm.getTelefonoInteresado(), interesado.getTelefonoInteresado());
        Assert.assertEquals(interesadoForm.getDireccionElectronicaHabilitadaInteresado(), interesado.getDireccionElectronicaHabilitadaInteresado());
        Assert.assertEquals(interesadoForm.getCanalPreferenteComunicacionInteresado(), interesado.getCanalPreferenteComunicacionInteresado());
        Assert.assertEquals(interesadoForm.getTipoDocumentoIdentificacionRepresentante(), interesado.getTipoDocumentoIdentificacionRepresentante());
        Assert.assertEquals(interesadoForm.getDocumentoIdentificacionRepresentante(), interesado.getDocumentoIdentificacionRepresentante());
        Assert.assertEquals(interesadoForm.getRazonSocialRepresentante(), interesado.getRazonSocialRepresentante());
        Assert.assertEquals(interesadoForm.getNombreRepresentante(), interesado.getNombreRepresentante());
        Assert.assertEquals(interesadoForm.getPrimerApellidoRepresentante(), interesado.getPrimerApellidoRepresentante());
        Assert.assertEquals(interesadoForm.getSegundoApellidoRepresentante(), interesado.getSegundoApellidoRepresentante());
        Assert.assertEquals(interesadoForm.getCodigoPaisRepresentante(), interesado.getCodigoPaisRepresentante());
        Assert.assertEquals(interesadoForm.getCodigoProvinciaRepresentante(), interesado.getCodigoProvinciaRepresentante());
        Assert.assertEquals(interesadoForm.getCodigoMunicipioRepresentante(), interesado.getCodigoMunicipioRepresentante());
        Assert.assertEquals(interesadoForm.getDireccionRepresentante(), interesado.getDireccionRepresentante());
        Assert.assertEquals(interesadoForm.getCodigoPostalRepresentante(), interesado.getCodigoPostalRepresentante());
        Assert.assertEquals(interesadoForm.getCorreoElectronicoRepresentante(), interesado.getCorreoElectronicoRepresentante());
        Assert.assertEquals(interesadoForm.getTelefonoRepresentante(), interesado.getTelefonoRepresentante());
        Assert.assertEquals(interesadoForm.getDireccionElectronicaHabilitadaRepresentante(), interesado.getDireccionElectronicaHabilitadaRepresentante());
        Assert.assertEquals(interesadoForm.getCanalPreferenteComunicacionRepresentante(), interesado.getCanalPreferenteComunicacionRepresentante());
        Assert.assertEquals(interesadoForm.getObservaciones(), interesado.getObservaciones());
    }

    public static void assertEquals(InteresadoVO interesado, InteresadoVO interesado2) {

        Assert.assertNotNull("Interesado", interesado);
        Assert.assertNotNull("Interesado secundario", interesado2);

        Assert.assertNotNull(interesado2);
        Assert.assertNotNull(interesado2.getId());

        //Assert.assertEquals(interesado.getIdAsientoRegistral(), interesado2.getIdAsientoRegistral());
        Assert.assertEquals(interesado.getTipoDocumentoIdentificacionInteresado(), interesado2.getTipoDocumentoIdentificacionInteresado());
        Assert.assertEquals(interesado.getDocumentoIdentificacionInteresado(), interesado2.getDocumentoIdentificacionInteresado());
        Assert.assertEquals(interesado.getRazonSocialInteresado(), interesado2.getRazonSocialInteresado());
        Assert.assertEquals(interesado.getNombreInteresado(), interesado2.getNombreInteresado());
        Assert.assertEquals(interesado.getPrimerApellidoInteresado(), interesado2.getPrimerApellidoInteresado());
        Assert.assertEquals(interesado.getSegundoApellidoInteresado(), interesado2.getSegundoApellidoInteresado());
        Assert.assertEquals(interesado.getCodigoPaisInteresado(), interesado2.getCodigoPaisInteresado());
        Assert.assertEquals(interesado.getCodigoProvinciaInteresado(), interesado2.getCodigoProvinciaInteresado());
        Assert.assertEquals(interesado.getCodigoMunicipioInteresado(), interesado2.getCodigoMunicipioInteresado());
        Assert.assertEquals(interesado.getDireccionInteresado(), interesado2.getDireccionInteresado());
        Assert.assertEquals(interesado.getCodigoPostalInteresado(), interesado2.getCodigoPostalInteresado());
        Assert.assertEquals(interesado.getCorreoElectronicoInteresado(), interesado2.getCorreoElectronicoInteresado());
        Assert.assertEquals(interesado.getTelefonoInteresado(), interesado2.getTelefonoInteresado());
        Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaInteresado(), interesado2.getDireccionElectronicaHabilitadaInteresado());
        Assert.assertEquals(interesado.getCanalPreferenteComunicacionInteresado(), interesado2.getCanalPreferenteComunicacionInteresado());
        Assert.assertEquals(interesado.getTipoDocumentoIdentificacionRepresentante(), interesado2.getTipoDocumentoIdentificacionRepresentante());
        Assert.assertEquals(interesado.getDocumentoIdentificacionRepresentante(), interesado2.getDocumentoIdentificacionRepresentante());
        Assert.assertEquals(interesado.getRazonSocialRepresentante(), interesado2.getRazonSocialRepresentante());
        Assert.assertEquals(interesado.getNombreRepresentante(), interesado2.getNombreRepresentante());
        Assert.assertEquals(interesado.getPrimerApellidoRepresentante(), interesado2.getPrimerApellidoRepresentante());
        Assert.assertEquals(interesado.getSegundoApellidoRepresentante(), interesado2.getSegundoApellidoRepresentante());
        Assert.assertEquals(interesado.getCodigoPaisRepresentante(), interesado2.getCodigoPaisRepresentante());
        Assert.assertEquals(interesado.getCodigoProvinciaRepresentante(), interesado2.getCodigoProvinciaRepresentante());
        Assert.assertEquals(interesado.getCodigoMunicipioRepresentante(), interesado2.getCodigoMunicipioRepresentante());
        Assert.assertEquals(interesado.getDireccionRepresentante(), interesado2.getDireccionRepresentante());
        Assert.assertEquals(interesado.getCodigoPostalRepresentante(), interesado2.getCodigoPostalRepresentante());
        Assert.assertEquals(interesado.getCorreoElectronicoRepresentante(), interesado2.getCorreoElectronicoRepresentante());
        Assert.assertEquals(interesado.getTelefonoRepresentante(), interesado2.getTelefonoRepresentante());
        Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaRepresentante(), interesado2.getDireccionElectronicaHabilitadaRepresentante());
        Assert.assertEquals(interesado.getCanalPreferenteComunicacionRepresentante(), interesado2.getCanalPreferenteComunicacionRepresentante());
        Assert.assertEquals(interesado.getObservaciones(), interesado2.getObservaciones());
    }


    public static MensajeVO createDefaultMensajeVO() {

        MensajeVO mensaje = new MensajeVO();

        mensaje.setCodigoEntidadRegistralOrigen("ER0000000000000000002");
        mensaje.setCodigoEntidadRegistralDestino("ER0000000000000000001");
        mensaje.setIdentificadorIntercambio("ER0000000000000000001_10_10000001");
        mensaje.setTipoMensaje(TipoMensajeEnum.ACK);
        mensaje.setDescripcionMensaje(mensaje.getTipoMensaje().getName());
        mensaje.setNumeroRegistroEntradaDestino("201000100000001");
        mensaje.setFechaEntradaDestino(new Date());
        mensaje.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
        mensaje.setIdentificadoresFicheros(Arrays.asList(new String[] { "ER0000000000000000001_10_10000001" }));
        mensaje.setCodigoError("0000");

        return mensaje;
    }

    public static void assertEquals(AsientoRegistralVO asiento, AuditoriaFicheroIntercambioVO auditoriaFicheroIntercambio) {

        Assert.assertNotNull("No se ha encontrado la información del asiento", asiento);
        Assert.assertNotNull("No se ha encontrado la información de la auditoría del fichero de intercambio", auditoriaFicheroIntercambio);

        Assert.assertEquals(BandejaEnum.RECIBIDOS, auditoriaFicheroIntercambio.getBandeja());
        Assert.assertNotNull(auditoriaFicheroIntercambio.getFechaCreacion());
        Assert.assertNotNull(auditoriaFicheroIntercambio.getXml());

        Assert.assertEquals(asiento.getCodigoEntidadRegistralOrigen(), auditoriaFicheroIntercambio.getCodigoEntidadRegistralOrigen());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralOrigen(), auditoriaFicheroIntercambio.getDescripcionEntidadRegistralOrigen());
        Assert.assertEquals(asiento.getCodigoUnidadTramitacionOrigen(), auditoriaFicheroIntercambio.getCodigoUnidadTramitacionOrigen());
        Assert.assertEquals(asiento.getDescripcionUnidadTramitacionOrigen(), auditoriaFicheroIntercambio.getDescripcionUnidadTramitacionOrigen());
        Assert.assertEquals(asiento.getCodigoEntidadRegistralDestino(), auditoriaFicheroIntercambio.getCodigoEntidadRegistralDestino());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralDestino(), auditoriaFicheroIntercambio.getDescripcionEntidadRegistralDestino());
        Assert.assertEquals(asiento.getCodigoUnidadTramitacionDestino(), auditoriaFicheroIntercambio.getCodigoUnidadTramitacionDestino());
        Assert.assertEquals(asiento.getDescripcionUnidadTramitacionDestino(), auditoriaFicheroIntercambio.getDescripcionUnidadTramitacionDestino());
        Assert.assertEquals(asiento.getNumeroRegistro(), auditoriaFicheroIntercambio.getNumeroRegistro());
        Assert.assertEquals(asiento.getFechaRegistro(), auditoriaFicheroIntercambio.getFechaRegistro());
        Assert.assertEquals(asiento.getTimestampRegistro(), auditoriaFicheroIntercambio.getTimestampRegistro());
        Assert.assertEquals(asiento.getResumen(), auditoriaFicheroIntercambio.getResumen());
        Assert.assertEquals(asiento.getCodigoAsunto(), auditoriaFicheroIntercambio.getCodigoAsunto());
        Assert.assertEquals(asiento.getReferenciaExterna(), auditoriaFicheroIntercambio.getReferenciaExterna());
        Assert.assertEquals(asiento.getNumeroExpediente(), auditoriaFicheroIntercambio.getNumeroExpediente());
        Assert.assertEquals(asiento.getTipoTransporte(), auditoriaFicheroIntercambio.getTipoTransporte());
        Assert.assertEquals(asiento.getNumeroTransporte(), auditoriaFicheroIntercambio.getNumeroTransporte());
        Assert.assertEquals(asiento.getNombreUsuario(), auditoriaFicheroIntercambio.getNombreUsuario());
        Assert.assertEquals(asiento.getContactoUsuario(), auditoriaFicheroIntercambio.getContactoUsuario());
        Assert.assertEquals(asiento.getIdentificadorIntercambio(), auditoriaFicheroIntercambio.getIdentificadorIntercambio());
        Assert.assertEquals(asiento.getAplicacion(), auditoriaFicheroIntercambio.getAplicacion());
        Assert.assertEquals(asiento.getTipoAnotacion(), auditoriaFicheroIntercambio.getTipoAnotacion());
        Assert.assertEquals(asiento.getDescripcionTipoAnotacion(), auditoriaFicheroIntercambio.getDescripcionTipoAnotacion());
        Assert.assertEquals(asiento.getTipoRegistro(), auditoriaFicheroIntercambio.getTipoRegistro());
        Assert.assertEquals(asiento.getDocumentacionFisica(), auditoriaFicheroIntercambio.getDocumentacionFisica());
        Assert.assertEquals(asiento.getObservacionesApunte(), auditoriaFicheroIntercambio.getObservacionesApunte());
        Assert.assertEquals(asiento.getIndicadorPrueba(), auditoriaFicheroIntercambio.getIndicadorPrueba());
        Assert.assertEquals(asiento.getCodigoEntidadRegistralInicio(), auditoriaFicheroIntercambio.getCodigoEntidadRegistralInicio());
        Assert.assertEquals(asiento.getDescripcionEntidadRegistralInicio(), auditoriaFicheroIntercambio.getDescripcionEntidadRegistralInicio());
        Assert.assertEquals(asiento.getExpone(), auditoriaFicheroIntercambio.getExpone());
        Assert.assertEquals(asiento.getSolicita(), auditoriaFicheroIntercambio.getSolicita());
    }

    public static FicheroIntercambioVO createDefaultFicheroIntercambioVO() throws MarshalException, ValidationException {

    	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Fichero_Intercambio_SICRES_3><De_Origen_o_Remitente><Codigo_Entidad_Registral_Origen>ER0000000000000000001</Codigo_Entidad_Registral_Origen><Decodificacion_Entidad_Registral_Origen><![CDATA[Descripcin de ER0000000000000000001]]></Decodificacion_Entidad_Registral_Origen><Numero_Registro_Entrada>201000100000001</Numero_Registro_Entrada><Fecha_Hora_Entrada>20110107121346</Fecha_Hora_Entrada><Codigo_Unidad_Tramitacion_Origen>UT0000000000000000001</Codigo_Unidad_Tramitacion_Origen><Decodificacion_Unidad_Tramitacion_Origen><![CDATA[Descripcin de UT0000000000000000001]]></Decodificacion_Unidad_Tramitacion_Origen></De_Origen_o_Remitente><De_Destino><Codigo_Entidad_Registral_Destino>ER0000000000000000002</Codigo_Entidad_Registral_Destino><Decodificacion_Entidad_Registral_Destino>Descripcin de ER0000000000000000002</Decodificacion_Entidad_Registral_Destino><Codigo_Unidad_Tramitacion_Destino>UT0000000000000000002</Codigo_Unidad_Tramitacion_Destino><Decodificacion_Unidad_Tramitacion_Destino>Descripcin de UT0000000000000000002</Decodificacion_Unidad_Tramitacion_Destino></De_Destino><De_Interesado><Tipo_Documento_Identificacion_Interesado>C</Tipo_Documento_Identificacion_Interesado><Documento_Identificacion_Interesado>A28855260</Documento_Identificacion_Interesado><Razon_Social_Interesado>razonSocialInteresado</Razon_Social_Interesado><Tipo_Documento_Identificacion_Representante>N</Tipo_Documento_Identificacion_Representante><Documento_Identificacion_Representante>00000000T</Documento_Identificacion_Representante><Nombre_Representante>nombreRepresentante</Nombre_Representante><Primer_Apellido_Representante>primerApellidoRepresentante</Primer_Apellido_Representante><Segundo_Apellido_Representante>segundoApellidoRepresentante</Segundo_Apellido_Representante><Pais_Interesado>0001</Pais_Interesado><Provincia_Interesado>05</Provincia_Interesado><Municipio_Interesado>01544</Municipio_Interesado><Direccion_Interesado>direccionInteresado</Direccion_Interesado><Codigo_Postal_Interesado>33004</Codigo_Postal_Interesado><Correo_Electronico_Interesado>correoElectronico@interesado.es</Correo_Electronico_Interesado><Telefono_Contacto_Interesado>999999999</Telefono_Contacto_Interesado><Direccion_Electronica_Habilitada_Interesado>deu</Direccion_Electronica_Habilitada_Interesado><Canal_Preferente_Comunicacion_Interesado>01</Canal_Preferente_Comunicacion_Interesado><Pais_Representante>0001</Pais_Representante><Provincia_Representante>05</Provincia_Representante><Municipio_Representante>01544</Municipio_Representante><Direccion_Representante>direccionRepresentante</Direccion_Representante><Codigo_Postal_Representante>33004</Codigo_Postal_Representante><Correo_Electronico_Representante>correoElectronico@representante.es</Correo_Electronico_Representante><Telefono_Contacto_Representante>666666666</Telefono_Contacto_Representante><Direccion_Electronica_Habilitada_Representante>deu_repr</Direccion_Electronica_Habilitada_Representante><Canal_Preferente_Comunicacion_Representante>02</Canal_Preferente_Comunicacion_Representante><Observaciones><![CDATA[observaciones]]></Observaciones></De_Interesado><De_Asunto><Resumen><![CDATA[Resumen]]></Resumen><Codigo_Asunto_Segun_Destino>ASUNTO0000000001</Codigo_Asunto_Segun_Destino><Referencia_Externa><![CDATA[REF0000000000001]]></Referencia_Externa><Numero_Expediente><![CDATA[EXP2010/000001]]></Numero_Expediente></De_Asunto><De_Anexo><Nombre_Fichero_Anexado>nuevo.txt</Nombre_Fichero_Anexado><Identificador_Fichero>ER0000000000000000001_10_10000001_01_0002.txt</Identificador_Fichero><Validez_Documento>02</Validez_Documento><Tipo_Documento>03</Tipo_Documento><Certificado>KioqY2VydGlmaWNhZG8qKio=</Certificado><Firma_Documento>KioqZmlybWEqKio=</Firma_Documento><TimeStamp>KioqdGltZXN0YW1wKioq</TimeStamp><Validacion_OCSP_Certificado>KioqdmFsaWRhY2lvbk9DU1BDZXJ0aWZpY2FkbyoqKg==</Validacion_OCSP_Certificado><Hash>AF4818A0071A25A5544E7B25271ED159CD18D58B</Hash><Tipo_MIME>text/plain</Tipo_MIME><Anexo>KioqYW5leG8qKio=</Anexo><Identificador_Documento_Firmado>ER0000000000000000001_10_10000001_01_0002.txt</Identificador_Documento_Firmado><Observaciones><![CDATA[Observaciones]]></Observaciones></De_Anexo><De_Internos_Control><Tipo_Transporte_Entrada>03</Tipo_Transporte_Entrada><Numero_Transporte_Entrada>00000000000000000001</Numero_Transporte_Entrada><Nombre_Usuario>usuario</Nombre_Usuario><Contacto_Usuario><![CDATA[contactousuario]]></Contacto_Usuario><Identificador_Intercambio>ER0000000000000000001_10_10000001</Identificador_Intercambio><Aplicacion_Version_Emisora>ieci</Aplicacion_Version_Emisora><Tipo_Anotacion>02</Tipo_Anotacion><Descripcion_Tipo_Anotacion><![CDATA[Envo]]></Descripcion_Tipo_Anotacion><Tipo_Registro>0</Tipo_Registro><Documentacion_Fisica>3</Documentacion_Fisica><Observaciones_Apunte><![CDATA[observacionesApunte]]></Observaciones_Apunte><Indicador_Prueba>1</Indicador_Prueba><Codigo_Entidad_Registral_Inicio>ER0000000000000000001</Codigo_Entidad_Registral_Inicio><Decodificacion_Entidad_Registral_Inicio><![CDATA[Descripcin de ER0000000000000000001]]></Decodificacion_Entidad_Registral_Inicio></De_Internos_Control><De_Formulario_Generico><Expone><![CDATA[expone]]></Expone><Solicita><![CDATA[solicita]]></Solicita></De_Formulario_Generico></Fichero_Intercambio_SICRES_3>";

    	FicheroIntercambioVO ficheroIntercambio = new FicheroIntercambioVO();
    	ficheroIntercambio.setFicheroIntercambio(Fichero_Intercambio_SICRES_3.unmarshal(new StringReader(xml)));

        return ficheroIntercambio;
    }

    public static void assertEquals(FicheroIntercambioVO ficheroIntercambio, AuditoriaFicheroIntercambioVO auditoriaFicheroIntercambio) {

        Assert.assertNotNull("No se ha encontrado la información del fichero de intercambio", ficheroIntercambio);
        Assert.assertNotNull("No se ha encontrado la información de la auditoría del fichero de intercambio", auditoriaFicheroIntercambio);

        Assert.assertEquals(BandejaEnum.RECIBIDOS, auditoriaFicheroIntercambio.getBandeja());
        Assert.assertNotNull(auditoriaFicheroIntercambio.getFechaCreacion());
        Assert.assertNotNull(auditoriaFicheroIntercambio.getXml());

        Assert.assertEquals(ficheroIntercambio.getCodigoEntidadRegistralOrigen(), auditoriaFicheroIntercambio.getCodigoEntidadRegistralOrigen());
        Assert.assertEquals(ficheroIntercambio.getDescripcionEntidadRegistralOrigen(), auditoriaFicheroIntercambio.getDescripcionEntidadRegistralOrigen());
        Assert.assertEquals(ficheroIntercambio.getCodigoUnidadTramitacionOrigen(), auditoriaFicheroIntercambio.getCodigoUnidadTramitacionOrigen());
        Assert.assertEquals(ficheroIntercambio.getDescripcionUnidadTramitacionOrigen(), auditoriaFicheroIntercambio.getDescripcionUnidadTramitacionOrigen());
        Assert.assertEquals(ficheroIntercambio.getCodigoEntidadRegistralDestino(), auditoriaFicheroIntercambio.getCodigoEntidadRegistralDestino());
        Assert.assertEquals(ficheroIntercambio.getDescripcionEntidadRegistralDestino(), auditoriaFicheroIntercambio.getDescripcionEntidadRegistralDestino());
        Assert.assertEquals(ficheroIntercambio.getCodigoUnidadTramitacionDestino(), auditoriaFicheroIntercambio.getCodigoUnidadTramitacionDestino());
        Assert.assertEquals(ficheroIntercambio.getDescripcionUnidadTramitacionDestino(), auditoriaFicheroIntercambio.getDescripcionUnidadTramitacionDestino());
        Assert.assertEquals(ficheroIntercambio.getNumeroRegistro(), auditoriaFicheroIntercambio.getNumeroRegistro());
        Assert.assertEquals(ficheroIntercambio.getFechaRegistro(), auditoriaFicheroIntercambio.getFechaRegistro());
        Assert.assertEquals(ficheroIntercambio.getTimestampRegistro(), auditoriaFicheroIntercambio.getTimestampRegistro());
        Assert.assertEquals(ficheroIntercambio.getResumen(), auditoriaFicheroIntercambio.getResumen());
        Assert.assertEquals(ficheroIntercambio.getCodigoAsunto(), auditoriaFicheroIntercambio.getCodigoAsunto());
        Assert.assertEquals(ficheroIntercambio.getReferenciaExterna(), auditoriaFicheroIntercambio.getReferenciaExterna());
        Assert.assertEquals(ficheroIntercambio.getNumeroExpediente(), auditoriaFicheroIntercambio.getNumeroExpediente());
        Assert.assertEquals(ficheroIntercambio.getTipoTransporte(), auditoriaFicheroIntercambio.getTipoTransporte());
        Assert.assertEquals(ficheroIntercambio.getNumeroTransporte(), auditoriaFicheroIntercambio.getNumeroTransporte());
        Assert.assertEquals(ficheroIntercambio.getNombreUsuario(), auditoriaFicheroIntercambio.getNombreUsuario());
        Assert.assertEquals(ficheroIntercambio.getContactoUsuario(), auditoriaFicheroIntercambio.getContactoUsuario());
        Assert.assertEquals(ficheroIntercambio.getIdentificadorIntercambio(), auditoriaFicheroIntercambio.getIdentificadorIntercambio());
        Assert.assertEquals(ficheroIntercambio.getAplicacionEmisora(), auditoriaFicheroIntercambio.getAplicacion());
        Assert.assertEquals(ficheroIntercambio.getTipoAnotacion(), auditoriaFicheroIntercambio.getTipoAnotacion());
        Assert.assertEquals(ficheroIntercambio.getTipoAnotacion().getName(), auditoriaFicheroIntercambio.getDescripcionTipoAnotacion());
        Assert.assertEquals(ficheroIntercambio.getTipoRegistro(), auditoriaFicheroIntercambio.getTipoRegistro());
        Assert.assertEquals(ficheroIntercambio.getDocumentacionFisica(), auditoriaFicheroIntercambio.getDocumentacionFisica());
        Assert.assertEquals(ficheroIntercambio.getObservacionesApunte(), auditoriaFicheroIntercambio.getObservacionesApunte());
        Assert.assertEquals(ficheroIntercambio.getIndicadorPrueba(), auditoriaFicheroIntercambio.getIndicadorPrueba());
        Assert.assertEquals(ficheroIntercambio.getCodigoEntidadRegistralInicio(), auditoriaFicheroIntercambio.getCodigoEntidadRegistralInicio());
        Assert.assertEquals(ficheroIntercambio.getDescripcionEntidadRegistralInicio(), auditoriaFicheroIntercambio.getDescripcionEntidadRegistralInicio());
        Assert.assertEquals(ficheroIntercambio.getExpone(), auditoriaFicheroIntercambio.getExpone());
        Assert.assertEquals(ficheroIntercambio.getSolicita(), auditoriaFicheroIntercambio.getSolicita());
    }

    public static String createXMLFicheroIntercambio(String identificadorIntercambio, TipoAnotacionEnum tipoAnotacion) {
    	return new StringBuffer()
    		.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    		.append("<Fichero_Intercambio_SICRES_3>")
    		.append("<De_Origen_o_Remitente>")
		        .append("<Codigo_Entidad_Registral_Origen>ER0000000000000000001</Codigo_Entidad_Registral_Origen>")
		        .append("<Decodificacion_Entidad_Registral_Origen><![CDATA[Entidad Registral ER0000000000000000001]]></Decodificacion_Entidad_Registral_Origen>")
		        .append("<Numero_Registro_Entrada>201000100000001</Numero_Registro_Entrada>")
		        .append("<Fecha_Hora_Entrada>20110308131106</Fecha_Hora_Entrada>")
		        .append("<Timestamp_Entrada>").append(Base64.encodeBase64String("timestamp".getBytes())).append("</Timestamp_Entrada>")
		        .append("<Codigo_Unidad_Tramitacion_Origen>UT0000000000000000001</Codigo_Unidad_Tramitacion_Origen>")
		        .append("<Decodificacion_Unidad_Tramitacion_Origen><![CDATA[Unidad de Tramitación UT0000000000000000001]]></Decodificacion_Unidad_Tramitacion_Origen>")
	        .append("</De_Origen_o_Remitente>")
	        .append("<De_Destino>")
		        .append("<Codigo_Entidad_Registral_Destino>ER0000000000000000002</Codigo_Entidad_Registral_Destino>")
		        .append("<Decodificacion_Entidad_Registral_Destino>Entidad Registral ER0000000000000000002</Decodificacion_Entidad_Registral_Destino>")
		        .append("<Codigo_Unidad_Tramitacion_Destino>UT0000000000000000002</Codigo_Unidad_Tramitacion_Destino>")
		        .append("<Decodificacion_Unidad_Tramitacion_Destino>Unidad de Tramitación UT0000000000000000002</Decodificacion_Unidad_Tramitacion_Destino>")
	        .append("</De_Destino>")
	        .append("<De_Interesado>")
		        .append("<Tipo_Documento_Identificacion_Interesado>C</Tipo_Documento_Identificacion_Interesado>")
		        .append("<Documento_Identificacion_Interesado>A28855260</Documento_Identificacion_Interesado>")
		        .append("<Razon_Social_Interesado>razonSocialInteresado</Razon_Social_Interesado>")
		        .append("<Tipo_Documento_Identificacion_Representante>N</Tipo_Documento_Identificacion_Representante>")
		        .append("<Documento_Identificacion_Representante>00000000T</Documento_Identificacion_Representante>")
		        .append("<Nombre_Representante>nombreRepresentante</Nombre_Representante>")
		        .append("<Primer_Apellido_Representante>primerApellidoRepresentante</Primer_Apellido_Representante>")
		        .append("<Segundo_Apellido_Representante>segundoApellidoRepresentante</Segundo_Apellido_Representante>")
		        .append("<Pais_Interesado>0001</Pais_Interesado>")
		        .append("<Provincia_Interesado>05</Provincia_Interesado>")
		        .append("<Municipio_Interesado>01544</Municipio_Interesado>")
		        .append("<Direccion_Interesado>direccionInteresado</Direccion_Interesado>")
		        .append("<Codigo_Postal_Interesado>33004</Codigo_Postal_Interesado>")
		        .append("<Correo_Electronico_Interesado>correoElectronico@interesado.es</Correo_Electronico_Interesado>")
		        .append("<Telefono_Contacto_Interesado>999999999</Telefono_Contacto_Interesado>")
		        .append("<Direccion_Electronica_Habilitada_Interesado>deu</Direccion_Electronica_Habilitada_Interesado>")
		        .append("<Canal_Preferente_Comunicacion_Interesado>01</Canal_Preferente_Comunicacion_Interesado>")
		        .append("<Pais_Representante>0001</Pais_Representante>")
		        .append("<Provincia_Representante>05</Provincia_Representante>")
		        .append("<Municipio_Representante>01544</Municipio_Representante>")
		        .append("<Direccion_Representante>direccionRepresentante</Direccion_Representante>")
		        .append("<Codigo_Postal_Representante>33004</Codigo_Postal_Representante>")
		        .append("<Correo_Electronico_Representante>correoElectronico@representante.es</Correo_Electronico_Representante>")
		        .append("<Telefono_Contacto_Representante>666666666</Telefono_Contacto_Representante>")
		        .append("<Direccion_Electronica_Habilitada_Representante>deu_repr</Direccion_Electronica_Habilitada_Representante>")
		        .append("<Canal_Preferente_Comunicacion_Representante>02</Canal_Preferente_Comunicacion_Representante>")
		        .append("<Observaciones><![CDATA[observaciones]]>")
		        .append("</Observaciones>")
	        .append("</De_Interesado>")
	        .append("<De_Asunto>")
		        .append("<Resumen><![CDATA[Resumen]]></Resumen>")
		        .append("<Codigo_Asunto_Segun_Destino>ASUNTO0000000001</Codigo_Asunto_Segun_Destino>")
		        .append("<Referencia_Externa><![CDATA[REF0000000000001]]></Referencia_Externa>")
		        .append("<Numero_Expediente><![CDATA[EXP2010/00001]]></Numero_Expediente>")
	        .append("</De_Asunto>")
	        .append("<De_Anexo>")
		        .append("<Nombre_Fichero_Anexado>fichero1.txt</Nombre_Fichero_Anexado>")
		        .append("<Identificador_Fichero>").append(identificadorIntercambio).append("_01_0001.txt</Identificador_Fichero>")
		        .append("<Validez_Documento>02</Validez_Documento>")
		        .append("<Tipo_Documento>03</Tipo_Documento>")
		        .append("<Certificado>KioqY2VydGlmaWNhZG8qKio=</Certificado>")
		        .append("<Firma_Documento>KioqZmlybWEqKio=</Firma_Documento>")
		        .append("<TimeStamp>KioqdGltZXN0bWFwKioq</TimeStamp>")
		        .append("<Validacion_OCSP_Certificado>Kioqb2NzcCoqKg==</Validacion_OCSP_Certificado>")
		        .append("<Tipo_MIME>text/plain</Tipo_MIME>")
		        .append("<Anexo>Y29udGVuaWRvIGZpY2hlcm8gMQ==</Anexo>")
		        .append("<Hash>oLO7sg9G8wj9vvD3adxdLnPXrcY=</Hash>")
		        .append("<Identificador_Documento_Firmado>").append(identificadorIntercambio).append("_01_0001</Identificador_Documento_Firmado>")
		        .append("<Observaciones><![CDATA[Observaciones al fichero 1]]>")
		        .append("</Observaciones>")
	        .append("</De_Anexo>")
	        .append("<De_Anexo>")
		        .append("<Nombre_Fichero_Anexado>fichero2.txt</Nombre_Fichero_Anexado>")
		        .append("<Identificador_Fichero>").append(identificadorIntercambio).append("_01_0002.txt</Identificador_Fichero>")
		        .append("<Validez_Documento>03</Validez_Documento>")
		        .append("<Tipo_Documento>02</Tipo_Documento>")
		        .append("<Certificado>KioqY2VydGlmaWNhZG8qKio=</Certificado>")
		        .append("<TimeStamp>KioqdGltZXN0bWFwKioq</TimeStamp>")
		        .append("<Tipo_MIME>text/plain</Tipo_MIME>")
		        .append("<Anexo>Y29udGVuaWRvIGZpY2hlcm8gMQ==</Anexo>")
		        .append("<Hash>oLO7sg9G8wj9vvD3adxdLnPXrcY=</Hash>")
		        .append("<Observaciones><![CDATA[Observaciones al fichero 2]]>")
		        .append("</Observaciones>")
	        .append("</De_Anexo>")
	        .append("<De_Anexo>")
		        .append("<Nombre_Fichero_Anexado>fichero2_firma.txt</Nombre_Fichero_Anexado>")
		        .append("<Identificador_Fichero>").append(identificadorIntercambio).append("_01_0003.txt</Identificador_Fichero>")
		        .append("<Validez_Documento>03</Validez_Documento>")
		        .append("<Tipo_Documento>02</Tipo_Documento>")
		        .append("<Certificado>KioqY2VydGlmaWNhZG8qKio=</Certificado>")
		        .append("<TimeStamp>KioqdGltZXN0bWFwKioq</TimeStamp>")
		        .append("<Validacion_OCSP_Certificado>Kioqb2NzcCoqKg==</Validacion_OCSP_Certificado>")
		        .append("<Tipo_MIME>text/plain</Tipo_MIME>")
		        .append("<Anexo>Y29udGVuaWRvIGZpY2hlcm8gMQ==</Anexo>")
		        .append("<Hash>oLO7sg9G8wj9vvD3adxdLnPXrcY=</Hash>")
		        .append("<Identificador_Documento_Firmado>").append(identificadorIntercambio).append("_01_0001</Identificador_Documento_Firmado>")
		        .append("<Observaciones><![CDATA[Firma del fichero 2]]>")
		        .append("</Observaciones>")
	        .append("</De_Anexo>")
	        .append("<De_Internos_Control>")
		        .append("<Tipo_Transporte_Entrada>06</Tipo_Transporte_Entrada>")
		        .append("<Numero_Transporte_Entrada>99999</Numero_Transporte_Entrada>")
		        .append("<Nombre_Usuario>usuario</Nombre_Usuario>")
		        .append("<Contacto_Usuario><![CDATA[usuario@contacto.es]]></Contacto_Usuario>")
		        .append("<Identificador_Intercambio>").append(identificadorIntercambio).append("</Identificador_Intercambio>")
		        .append("<Tipo_Anotacion>").append(tipoAnotacion.getValue()).append("</Tipo_Anotacion>")
		        .append("<Descripcion_Tipo_Anotacion><![CDATA[").append(tipoAnotacion.getName()).append("]]></Descripcion_Tipo_Anotacion>")
		        .append("<Tipo_Registro>0</Tipo_Registro>")
		        .append("<Documentacion_Fisica>2</Documentacion_Fisica>")
		        .append("<Observaciones_Apunte><![CDATA[observaciones]]></Observaciones_Apunte>")
		        .append("<Indicador_Prueba>1</Indicador_Prueba>")
		        .append("<Codigo_Entidad_Registral_Inicio>ER0000000000000000001</Codigo_Entidad_Registral_Inicio>")
		        .append("<Decodificacion_Entidad_Registral_Inicio><![CDATA[Entidad Registral ER0000000000000000001]]></Decodificacion_Entidad_Registral_Inicio>")
	        .append("</De_Internos_Control>")
	        .append("<De_Formulario_Generico>")
		        .append("<Expone><![CDATA[expone]]></Expone>")
		        .append("<Solicita><![CDATA[solicita]]></Solicita>")
	        .append("</De_Formulario_Generico>")
	        .append("</Fichero_Intercambio_SICRES_3>")
    		.toString();
    }

	public static final TrazabilidadWS[] createTrazabilidadWSArray() {
		return new TrazabilidadWS[] {
			TestUtils.createTrazabilidadWS("1"),
			TestUtils.createTrazabilidadWS("2"),
			TestUtils.createTrazabilidadWS("3") };
	}

	public static TrazabilidadWS createTrazabilidadWS(String id) {

		TrazabilidadWS traza = new TrazabilidadWS();

	    traza.setCodigo("CODIGO" + id);
	    traza.setDescripcion("DESCRIPCION" + id);
	    traza.setCdError("CODIGO_ERROR" + id);
	    traza.setCdErrorServicio("CODIGO_ERROR_SERVICIO" + id);
	    traza.setCdEstado("CODIGO_ESTADO" + id);
	    traza.setCdIntercambio("CODIGO_INTERCAMBIO" + id);
	    traza.setCdNodoError("CODIGO_NODO" + id);
	    traza.setCdUgDestino("CODIGO_OFICINA_REGISTRO_DESTINO" + id);
	    traza.setCdUgOrigen("CODIGO_OFICINA_REGISTRO_ORIGEN" + id);
	    traza.setCdOrDestino("CODIGO_ORGANISMO_DESTINO" + id);
	    traza.setCdOrOrigen("CODIGO_ORGANISMO_ORIGEN" + id);
	    traza.setDsErrorAlternativa("DESCRIPCION_ERROR_ALTERNATIVA" + id);
	    traza.setDsErrorServicio("DESCRIPCION_ERROR_SERVICIO" + id);
	    traza.setDsUgDestino("DESCRIPCION_OFICINA_REGISTRO_DESTINO" + id);
	    traza.setDsUgOrigen("DESCRIPCION_OFICINA_REGISTRO_ORIGEN" + id);
	    traza.setDsOrDestino("DESCRIPCION_ORGANISMO_DESTINO" + id);
	    traza.setDsOrOrigen("DESCRIPCION_ORGANISMO_ORIGEN" + id);
	    traza.setFechaAlta(WSSIRHelper.formatDateTrazabilidad(new Date()));
	    traza.setFechaMod(WSSIRHelper.formatDateTrazabilidad(new Date()));
	    traza.setDsMotivoRechazo("MOTIVO_RECHAZO_ACEPTACION" + id);
	    traza.setDsNombreFichero("NOMBRE_FICHERO_INTERCAMBIO" + id);
	    traza.setRegistro("S");
	    traza.setTamanyoDocs(1024L);

	    return traza;
	}

	public static TrazabilidadVO createTrazabilidadVO(String id) {

		TrazabilidadVO traza = new TrazabilidadVO();

	    traza.setCodigo("CODIGO" + id);
	    traza.setDescripcion("DESCRIPCION" + id);
	    traza.setCodigoError("CODIGO_ERROR" + id);
	    traza.setCodigoErrorServicio("CODIGO_ERROR_SERVICIO" + id);
	    traza.setCodigoEstado("CODIGO_ESTADO" + id);
	    traza.setCodigoIntercambio("CODIGO_INTERCAMBIO" + id);
	    traza.setCodigoNodo("CODIGO_NODO" + id);
	    traza.setCodigoEntidadRegistralDestino("CODIGO_OFICINA_REGISTRO_DESTINO" + id);
	    traza.setCodigoEntidadRegistralOrigen("CODIGO_OFICINA_REGISTRO_ORIGEN" + id);
	    traza.setCodigoUnidadTramitacionDestino("CODIGO_ORGANISMO_DESTINO" + id);
	    traza.setCodigoUnidadTramitacionOrigen("CODIGO_ORGANISMO_ORIGEN" + id);
	    traza.setDescripcionErrorAlternativa("DESCRIPCION_ERROR_ALTERNATIVA" + id);
	    traza.setDescripcionErrorServicio("DESCRIPCION_ERROR_SERVICIO" + id);
	    traza.setDescripcionEntidadRegistralDestino("DESCRIPCION_OFICINA_REGISTRO_DESTINO" + id);
	    traza.setDescripcionEntidadRegistralOrigen("DESCRIPCION_OFICINA_REGISTRO_ORIGEN" + id);
	    traza.setDescripcionUnidadTramitacionDestino("DESCRIPCION_ORGANISMO_DESTINO" + id);
	    traza.setDescripcionUnidadTramitacionOrigen("DESCRIPCION_ORGANISMO_ORIGEN" + id);
	    traza.setFechaAlta(new Date());
	    traza.setFechaModificacion(new Date());
	    traza.setMotivoRechazo("MOTIVO_RECHAZO_ACEPTACION" + id);
	    traza.setNombreFicheroIntercambio("NOMBRE_FICHERO_INTERCAMBIO" + id);
	    traza.setRegistro(true);
	    traza.setTamanyoDocs(1024L);

	    return traza;
	}

	public static void assertEqualsTrazas(TrazabilidadWS[] trazasWS, List<TrazabilidadVO> trazasVO) {

		if (trazasWS == null) {
			Assert.assertNull("trazasWS es nulo y trazasVO no", trazasVO);
		} else {
			Assert.assertEquals(trazasWS.length, trazasVO.size());
			for (int i = 0; i < trazasWS.length; i++) {
				assertEquals(trazasWS[i], trazasVO.get(i));
			}
		}
	}

	public static void assertEquals(TrazabilidadWS trazaWS, TrazabilidadVO trazaVO) {

		if (trazaWS == null) {
			Assert.assertNull("trazaWS es nulo y trazaVO no", trazaVO);
		} else {
		    Assert.assertEquals(trazaWS.getCodigo(), trazaVO.getCodigo());
		    Assert.assertEquals(trazaWS.getDescripcion(), trazaVO.getDescripcion());
		    Assert.assertEquals(trazaWS.getCdError(), trazaVO.getCodigoError());
		    Assert.assertEquals(trazaWS.getCdEstado(), trazaVO.getCodigoEstado());
		    Assert.assertEquals(trazaWS.getCdErrorServicio(), trazaVO.getCodigoErrorServicio());
		    Assert.assertEquals(trazaWS.getDsErrorServicio(), trazaVO.getDescripcionErrorServicio());
		    Assert.assertEquals(trazaWS.getCdIntercambio(), trazaVO.getCodigoIntercambio());
		    Assert.assertEquals(trazaWS.getCdNodoError(), trazaVO.getCodigoNodo());
		    Assert.assertEquals(trazaWS.getCdOrDestino(), trazaVO.getCodigoEntidadRegistralDestino());
		    Assert.assertEquals(trazaWS.getCdOrOrigen(), trazaVO.getCodigoEntidadRegistralOrigen());
		    Assert.assertEquals(trazaWS.getCdUgDestino(), trazaVO.getCodigoUnidadTramitacionDestino());
		    Assert.assertEquals(trazaWS.getCdUgOrigen(), trazaVO.getCodigoUnidadTramitacionOrigen());
		    Assert.assertEquals(trazaWS.getDsErrorAlternativa(), trazaVO.getDescripcionErrorAlternativa());
		    Assert.assertEquals(trazaWS.getDsOrDestino(), trazaVO.getDescripcionEntidadRegistralDestino());
		    Assert.assertEquals(trazaWS.getDsOrOrigen(), trazaVO.getDescripcionEntidadRegistralOrigen());
		    Assert.assertEquals(trazaWS.getDsUgDestino(), trazaVO.getDescripcionUnidadTramitacionDestino());
		    Assert.assertEquals(trazaWS.getDsUgOrigen(), trazaVO.getDescripcionUnidadTramitacionOrigen());
		    Assert.assertEquals(trazaWS.getFechaAlta(), WSSIRHelper.formatDateTrazabilidad(trazaVO.getFechaAlta()));
		    Assert.assertEquals(trazaWS.getFechaMod(), WSSIRHelper.formatDateTrazabilidad(trazaVO.getFechaModificacion()));
		    Assert.assertEquals(trazaWS.getDsMotivoRechazo(), trazaVO.getMotivoRechazo());
		    Assert.assertEquals(trazaWS.getDsNombreFichero(), trazaVO.getNombreFicheroIntercambio());
		    Assert.assertEquals(trazaWS.getRegistro(), trazaVO.isRegistro() ? "S" : "N");
		    Assert.assertEquals(trazaWS.getTamanyoDocs().longValue(), trazaVO.getTamanyoDocs());
		}
	}
	
	public static InfoRechazoVO createInfoRechazoVO(TipoRechazoEnum tipoRechazo) {
		
		InfoRechazoVO infoRechazo = new InfoRechazoVO();
		
		infoRechazo.setTipoRechazo(tipoRechazo);
		infoRechazo.setDescripcion("Motivos del rechazo");
		infoRechazo.setUsuario("usuario");
		infoRechazo.setContacto("contacto");
		infoRechazo.setAplicacion("app1");
		
		return infoRechazo;
	}
}
