package transferencias;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import transferencias.electronicas.common.SistemaTramitador;
import transferencias.electronicas.documentos.Almacenamiento;
import transferencias.electronicas.documentos.Binario;
import transferencias.electronicas.documentos.Repositorio;
import transferencias.electronicas.documentos.Ubicacion;
import transferencias.electronicas.ficha.CampoDescriptor;
import transferencias.electronicas.ficha.CampoDocumentoElectronico;
import transferencias.electronicas.ficha.CampoFecha;
import transferencias.electronicas.ficha.CampoFilaTabla;
import transferencias.electronicas.ficha.CampoNumerico;
import transferencias.electronicas.ficha.CampoSistemaExterno;
import transferencias.electronicas.ficha.CampoTabla;
import transferencias.electronicas.ficha.CampoTexto;
import transferencias.electronicas.ficha.CamposFicha;
import transferencias.electronicas.ficha.ICampoFicha;
import transferencias.electronicas.serie.InformacionSerie;
import transferencias.electronicas.serie.Procedimiento;
import transferencias.electronicas.serie.Tramite;
import transferencias.electronicas.udoc.ContenidoUDocXML;
import transferencias.electronicas.udoc.IdentificacionUnidadDocumental;
import transferencias.electronicas.udoc.InformacionUnidadDocumentalElectronica;
import transferencias.vos.TransferenciaElectronicaInfo;

import common.manager.ArchidocBaseTest;

public class TransferenciaElectronicaUtilsTest extends ArchidocBaseTest {

	private static final String FICHERO_XML_TRANSFERENCIA = "/xml/TransferenciaElectronica.xml";

	private static final String FICHERO_XML_TRANSFERENCIA_ELECTRONICA_SIMPLE = "/xml/TransferenciaElectronicaSimple.xml";

	private static final String FICHERO_XSD_TRANSFERENCIA = "/xsd/TransferenciaElectronica.xsd";


	public void validarXml() throws Exception{
		//Validar el XML
		InputStream xsd = getInputStream(getUrlFromClassPath(FICHERO_XSD_TRANSFERENCIA).toString());

		String xml = getContenidoFichero(getUrlFromClassPath(FICHERO_XML_TRANSFERENCIA));

		Assert.assertTrue(TransferenciaElectronicaUtils.validarXmlTransferencia(xml, xsd));
	}


	public void getTransferenciaElectronicaInfo() throws Exception{

		TransferenciaElectronicaInfo info = TransferenciaElectronicaUtils.getTransferenciaElectronicaInfo(null, getMockTransferenciaElectronicaInfo(getFicheroTransferencia()));
		Assert.assertNotNull(info);

		validarContenido(info.getContenidoUDocXML(), true);
	}

	@Test
	public void getTransferenciaElectronicaInfoSimple() throws Exception{

		TransferenciaElectronicaInfo info = TransferenciaElectronicaUtils.getTransferenciaElectronicaInfo(null, getMockTransferenciaElectronicaInfo(getFicheroTransferenciaSimple()));
		Assert.assertNotNull(info);

		validarContenido(info.getContenidoUDocXML(), false);

	}


	public void validarContenido(ContenidoUDocXML contenidoUDocXML, boolean conFichas) throws Exception {

		Assert.assertNotNull(contenidoUDocXML);
		Assert.assertEquals("5.0", contenidoUDocXML.getVersion());

		InformacionSerie informacionSerie = contenidoUDocXML
				.getInformacionSerie();
		Assert.assertNotNull(informacionSerie);

		Assert.assertEquals("1",
				informacionSerie.getIdFichaUdocs());

		Assert.assertEquals("cDF10000000000000000000000100802",
				informacionSerie.getIdPadre());


		//DATOS DEL PROCEDIMIENTO
		Procedimiento procedimiento = informacionSerie.getProcedimiento();

		Assert.assertNotNull(procedimiento);

		Assert.assertEquals("ID", procedimiento.getId());

		Assert.assertEquals("PCD1", procedimiento.getCodigo());

		Assert.assertEquals("NOMBRE", procedimiento.getNombre());

		Assert.assertEquals("TITULO CORTO", procedimiento.getTituloCorto());

		Assert.assertEquals("NORMATIVA", procedimiento.getNormativa());

		Assert.assertEquals("OBJETO", procedimiento.getObjeto());

		Assert.assertEquals("DOCUMENTOS", procedimiento.getDocumentos());

		SistemaTramitador sistemaTramitador = procedimiento.getSistemaTramitador();

		Assert.assertNotNull(sistemaTramitador);

		Assert.assertEquals("TE", sistemaTramitador.getId());
		Assert.assertEquals("NOMBRE TRAMITADOR", sistemaTramitador.getNombre());

		List<Tramite> tramites = procedimiento.getListaTramites();

		Assert.assertNotNull(tramites);

		Assert.assertEquals(2, tramites.size());

		Assert.assertEquals("TRAMITE1", tramites.get(0).toString());
		Assert.assertEquals("TRAMITE2", tramites.get(1).toString());

		//DATOS DE LA FICHA DE SERIE

		if(conFichas){
			CamposFicha fichaSerie = informacionSerie.getCamposFicha();

			validarFicha(fichaSerie, false);
		}

		//UNIDAD DOCUMENTAL
		InformacionUnidadDocumentalElectronica infoUdoc = contenidoUDocXML.getInfoUnidadDocumentalElectronica();

		Assert.assertNotNull(infoUdoc);


		IdentificacionUnidadDocumental identificacion = infoUdoc.getIdentificacionUnidadDocumental();
		Assert.assertNotNull(identificacion);

		Assert.assertNotNull(identificacion.getId());
		Assert.assertEquals("Id", identificacion.getId());

		Assert.assertNotNull(identificacion.getTitulo());
		Assert.assertEquals("Título del expediente", identificacion.getTitulo());

		Assert.assertNotNull(identificacion.getCodigoProcedimiento());
		Assert.assertEquals("PCD1", identificacion.getCodigoProcedimiento());

		Assert.assertNotNull(identificacion.getFechaInicio());
		Assert.assertEquals("01/01/2012", identificacion.getFechaInicio().getValor());
		Assert.assertEquals("31/12/2012", identificacion.getFechaFin().getValor());

		Assert.assertNotNull(identificacion.getProductor());

		Assert.assertEquals("ID", identificacion.getProductor().getTipoAtributo());
		Assert.assertEquals("ORG1", identificacion.getProductor().getValor());


		//FICHA DE UNIDAD DOCUMENTAL
		if(conFichas){
			CamposFicha camposFichaUdoc = infoUdoc.getCamposFicha();

			Assert.assertNotNull(camposFichaUdoc);

			validarFicha(camposFichaUdoc, true);
		}

	}


	private void validarFicha(CamposFicha camposficha, boolean isUnidadDocumental){

		Assert.assertNotNull(camposficha);


		List<ICampoFicha> listaCampos = camposficha.getCamposFicha();

		Assert.assertNotNull(listaCampos);


		CampoTexto identificacion = (CampoTexto) camposficha.getCampoListaByEtiqueta("Identificacion");

		Assert.assertNotNull(identificacion);

		Assert.assertEquals("Identificación", identificacion.getValor());

		CampoTabla emplazamientos = (CampoTabla) camposficha.getCampoListaByEtiqueta("Emplazamientos");
		Assert.assertNotNull(emplazamientos);


		CampoFilaTabla emplazamiento1 = (CampoFilaTabla) emplazamientos.getFila(1);
		Assert.assertNotNull(emplazamiento1);

		CampoDescriptor emplazamiento1Pais = (CampoDescriptor) emplazamiento1.getCampoListaByEtiqueta("Pais");
		Assert.assertNotNull(emplazamiento1Pais);

		Assert.assertEquals(emplazamiento1Pais.getEtiqueta(), "Pais");
		Assert.assertEquals(emplazamiento1Pais.getIdLista(), "ID_LIST_PAIS");
		Assert.assertEquals(emplazamiento1Pais.getValor(), "España");


		CampoFilaTabla emplazamiento2 = (CampoFilaTabla) emplazamientos.getFila(2);
		Assert.assertNotNull(emplazamiento2);

		CampoTexto emplazamiento2Localizacion = (CampoTexto) emplazamiento2.getCampoListaByEtiqueta("Localizacion");
		Assert.assertNotNull(emplazamiento2Localizacion);

		Assert.assertEquals(emplazamiento2Localizacion.getEtiqueta(), "Localizacion");
		Assert.assertEquals(emplazamiento2Localizacion.getValor(), "Oficina de Turismo");


		//Documentos electrónicos
		CampoTabla documentosElectronicos = (CampoTabla) camposficha.getCampoListaByEtiqueta("Documentos_Electronicos");
		Assert.assertNotNull(documentosElectronicos);

		CampoFilaTabla documentosElectronicosFila1 = documentosElectronicos.getFila(1);
		Assert.assertNotNull(documentosElectronicosFila1);



//
//
//		CampoTexto textoLargo1 = (CampoTexto)camposFicha.get("campoTextoLargo1");
//
//		Assert.assertNotNull(textoLargo1);
//
//		Assert.assertEquals("Texto Largo1", textoLargo1.getValor());
//
//
//		CampoTexto textoLargo2 = (CampoTexto)camposFicha.get("campoTextoLargo2");
//
//		Assert.assertNotNull(textoLargo2);
//
//		Assert.assertEquals("Texto Largo2", textoLargo2.getValor());
//
//
//		CampoFecha campoFecha1 = (CampoFecha)camposFicha.get("campoFecha1");
//
//		Assert.assertNotNull(campoFecha1);
//
//		Assert.assertEquals("01/01/2000", campoFecha1.getValor());
//		Assert.assertEquals("/", campoFecha1.getSeparador());
//		Assert.assertEquals("DDMMAAAA", campoFecha1.getFormato());
//		Assert.assertEquals("con", campoFecha1.getCalificador());
//
//
//		CampoFecha campoFecha2 = (CampoFecha)camposFicha.get("campoFecha2");
//
//		Assert.assertNotNull(campoFecha2);
//
//		Assert.assertEquals("01-2001", campoFecha2.getValor());
//		Assert.assertEquals("-", campoFecha2.getSeparador());
//		Assert.assertEquals("MMAAAA", campoFecha2.getFormato());
//		Assert.assertEquals("sup", campoFecha2.getCalificador());
//
//
//		CampoNumerico campoNumerico1 = (CampoNumerico)camposFicha.get("campoNumerico1");
//
//		Assert.assertNotNull(campoNumerico1);
//
//		Assert.assertEquals("10", campoNumerico1.getValor());
//		Assert.assertEquals("1", campoNumerico1.getTipoMedida());
//		Assert.assertEquals("cm.", campoNumerico1.getUnidadMedida());
//
//		CampoNumerico campoNumerico2 = (CampoNumerico)camposFicha.get("campoNumerico2");
//
//		Assert.assertNotNull(campoNumerico2);
//
//		Assert.assertEquals("20", campoNumerico2.getValor());
//		Assert.assertEquals("2", campoNumerico2.getTipoMedida());
//		Assert.assertEquals("kg.", campoNumerico2.getUnidadMedida());
//
//		CampoDescriptor campoDescriptor1 = (CampoDescriptor)camposFicha.get("campoDescriptor1");
//
//		Assert.assertNotNull(campoDescriptor1);
//
//		Assert.assertEquals("IDLISTA1", campoDescriptor1.getIdLista());
//		Assert.assertEquals("IDDESCRIPTOR1", campoDescriptor1.getValor());
//
//
//		CampoDescriptor campoDescriptor2 = (CampoDescriptor)camposFicha.get("campoDescriptor2");
//
//		Assert.assertNotNull(campoDescriptor2);
//
//		Assert.assertEquals("IDLISTA2", campoDescriptor2.getIdLista());
//		Assert.assertEquals("IDDESCRIPTOR2", campoDescriptor2.getValor());

//		if(isUnidadDocumental){

//			//Validar campo Sistema Externo
//			CampoSistemaExterno campoSistemaExterno = (CampoSistemaExterno) camposFicha.get("campoSistemaExterno1");
//
//			Assert.assertNotNull(campoSistemaExterno);
//			Assert.assertTrue(campoSistemaExterno.isAtributoId());
//
//
//			//Assert.assertEquals(expected, actual)
//
//
//			//Validar los campos Tabla
//			CampoTabla campoIE = (CampoTabla) camposFicha.get("Indice_electronico");
//			Assert.assertNotNull(campoIE);
//
//			CampoFilaTabla  fila1 = campoIE.getFila(0);
//
//
//			CampoTexto textoCorto = (CampoTexto) fila1.getCampo("campoTextoCorto1");
//			Assert.assertNotNull(textoCorto);
//			Assert.assertEquals("Texto Corto1", textoCorto.getValor());
//
//			CampoTexto textoLargo = (CampoTexto) fila1.getCampo("campoTextoLargo1");
//			Assert.assertNotNull(textoLargo);
//			Assert.assertEquals("Texto Largo1", textoLargo.getValor());
//
//			CampoFecha campoFecha = (CampoFecha) fila1.getCampo("campoFecha1");
//
//			Assert.assertNotNull(campoFecha);
//
//			Assert.assertEquals("01/01/2000", campoFecha.getValor());
//			Assert.assertEquals("/", campoFecha.getSeparador());
//			Assert.assertEquals("DDMMAAAA", campoFecha.getFormato());
//			Assert.assertEquals("con", campoFecha.getCalificador());
//
//
//			CampoNumerico campoNumerico = (CampoNumerico) fila1.getCampo("campoNumerico1");
//			Assert.assertNotNull(campoNumerico);
//
//			Assert.assertEquals("10", campoNumerico.getValor());
//			Assert.assertEquals("1", campoNumerico.getTipoMedida());
//			Assert.assertEquals("cm.", campoNumerico.getUnidadMedida());
//
//			CampoDescriptor campoDescriptor = (CampoDescriptor) fila1.getCampo("campoDescriptor1");
//
//			Assert.assertNotNull(campoDescriptor);
//
//			Assert.assertEquals("IDLISTA1", campoDescriptor.getIdLista());
//			Assert.assertEquals("IDDESCRIPTOR1", campoDescriptor.getValor());
//
//
//			CampoDocumentoElectronico indiceElectronico = (CampoDocumentoElectronico) fila1.getCampo("IE_Id_Interno");
//			Assert.assertNotNull(indiceElectronico);
//
//			Ubicacion ubicacion = indiceElectronico.getUbicacion();
//
//			Assert.assertNotNull(ubicacion);
//
//			Assert.assertTrue(ubicacion.isBinario());
//
//			Binario binario = ubicacion.getBinario();
//
//			Assert.assertNotNull(binario);
//
//			Assert.assertEquals("JVBERi0xLjQKJcfsj6IKNSAwIG9iago8PC9MZW5ndGggNiAwIFIvRmlsdGVyIC9GbGF0ZURlY29kZT4+CnN0cmVhbQp4nK1UTW8TMRC9+1fM0StR46+xPRz5lHoLBHGgHEoIlJJQmrRQ/j2zWe94F4JUJBKt8jzPM7HnvdlrsMZ5sP13BKutcvBJOYeQSvAmZYjZF0gEu7X6qCyT16qY0H8OGVO82sLjpXr4MkM2jhLnLTnFEFEsww4HLpoIxTuTYLlVb/WTjrHPST/qXDLWYtZnZ51zhhHq1yO777w3fCy9HsGuQ2asj42TxKBTjaEgbUeQRxAGEFGjoHgkNtYtRT8dDoRxPAjTe0FfBN3Iba46n41NAfU3QXLQMtnYCt79vYxH7eRqZqiSplXuhL3pWJd3y1P1bKkWrFogJFaVqFd1q2KJQZabcTlovhk3j8sL9Qa+HqwxI0pJwD//yRoxo4kzc1xK32/l+rVN3MXvgmqs+YTRgyE3Zg2CgrBOUGM//P4fM3bbKx/5Pvpc2J+Cmjz3reclZo+cyh/JIInVWWH7tH3UIfbBJvmCBfvBzyk/l6zPizrXDmcOGJYb9aqOPkuaSuxjZFkWFwd9nZ0wOCNSI0LOqVH380QBMpTEEIFNVd8VwZCHYtHkaojn3Yk3uVinPwtaHVAmcvqiwXWDuwp5aK9aFGqBhCzUsTSo0BWe3D+KMbw9mva+wfMG912yVpTpG+1jIO5UNtw6KA4y/kvPiKMWcdK26Ki2zRMPZwHWlbz1Q+NO+G3JCmJhj0WDIVDg2PQF0XvDs9X641RvjMveGwv1C8m2QA1lbmRzdHJlYW0KZW5kb2JqCjYgMCBvYmoKNTQ5CmVuZG9iago0IDAgb2JqCjw8L1R5cGUvUGFnZS9NZWRpYUJveCBbMCAwIDU5NSA4NDJdCi9Sb3RhdGUgMC9QYXJlbnQgMyAwIFIKL1Jlc291cmNlczw8L1Byb2NTZXRbL1BERiAvVGV4dF0KL0ZvbnQgMTAgMCBSCj4+Ci9Db250ZW50cyA1IDAgUgo+PgplbmRvYmoKMyAwIG9iago8PCAvVHlwZSAvUGFnZXMgL0tpZHMgWwo0IDAgUgpdIC9Db3VudCAxCj4+CmVuZG9iagoxIDAgb2JqCjw8L1R5cGUgL0NhdGFsb2cgL1BhZ2VzIDMgMCBSCi9NZXRhZGF0YSAxMSAwIFIKPj4KZW5kb2JqCjEwIDAgb2JqCjw8L1I3CjcgMCBSL1I5CjkgMCBSL1I4CjggMCBSPj4KZW5kb2JqCjcgMCBvYmoKPDwvQmFzZUZvbnQvSGVsdmV0aWNhLUJvbGQvVHlwZS9Gb250Ci9TdWJ0eXBlL1R5cGUxPj4KZW5kb2JqCjkgMCBvYmoKPDwvQmFzZUZvbnQvSGVsdmV0aWNhL1R5cGUvRm9udAovU3VidHlwZS9UeXBlMT4+CmVuZG9iago4IDAgb2JqCjw8L0Jhc2VGb250L0NvdXJpZXIvVHlwZS9Gb250Ci9TdWJ0eXBlL1R5cGUxPj4KZW5kb2JqCjExIDAgb2JqCjw8L1R5cGUvTWV0YWRhdGEKL1N1YnR5cGUvWE1ML0xlbmd0aCAxNTgwPj5zdHJlYW0KPD94cGFja2V0IGJlZ2luPSfvu78nIGlkPSdXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQnPz4KPD9hZG9iZS14YXAtZmlsdGVycyBlc2M9IkNSTEYiPz4KPHg6eG1wbWV0YSB4bWxuczp4PSdhZG9iZTpuczptZXRhLycgeDp4bXB0az0nWE1QIHRvb2xraXQgMi45LjEtMTMsIGZyYW1ld29yayAxLjYnPgo8cmRmOlJERiB4bWxuczpyZGY9J2h0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMnIHhtbG5zOmlYPSdodHRwOi8vbnMuYWRvYmUuY29tL2lYLzEuMC8nPgo8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0ndXVpZDpmYzA2ZmRhZS1hZDRkLTExZTEtMDAwMC04ODdmMzg3Y2I4MmUnIHhtbG5zOnBkZj0naHR0cDovL25zLmFkb2JlLmNvbS9wZGYvMS4zLyc+PHBkZjpQcm9kdWNlcj5HUEwgR2hvc3RzY3JpcHQgOS4wNTwvcGRmOlByb2R1Y2VyPgo8cGRmOktleXdvcmRzPigpPC9wZGY6S2V5d29yZHM+CjwvcmRmOkRlc2NyaXB0aW9uPgo8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0ndXVpZDpmYzA2ZmRhZS1hZDRkLTExZTEtMDAwMC04ODdmMzg3Y2I4MmUnIHhtbG5zOnhtcD0naHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyc+PHhtcDpNb2RpZnlEYXRlPjIwMTItMDUtMzFUMDk6Mjk6NDgrMDI6MDA8L3htcDpNb2RpZnlEYXRlPgo8eG1wOkNyZWF0ZURhdGU+MjAxMi0wNS0zMVQwOToyOTo0OCswMjowMDwveG1wOkNyZWF0ZURhdGU+Cjx4bXA6Q3JlYXRvclRvb2w+UERGQ3JlYXRvciBWZXJzaW9uIDEuMy4yPC94bXA6Q3JlYXRvclRvb2w+PC9yZGY6RGVzY3JpcHRpb24+CjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSd1dWlkOmZjMDZmZGFlLWFkNGQtMTFlMS0wMDAwLTg4N2YzODdjYjgyZScgeG1sbnM6eGFwTU09J2h0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8nIHhhcE1NOkRvY3VtZW50SUQ9J3V1aWQ6ZmMwNmZkYWUtYWQ0ZC0xMWUxLTAwMDAtODg3ZjM4N2NiODJlJy8+CjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSd1dWlkOmZjMDZmZGFlLWFkNGQtMTFlMS0wMDAwLTg4N2YzODdjYjgyZScgeG1sbnM6ZGM9J2h0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvJyBkYzpmb3JtYXQ9J2FwcGxpY2F0aW9uL3BkZic+PGRjOnRpdGxlPjxyZGY6QWx0PjxyZGY6bGkgeG1sOmxhbmc9J3gtZGVmYXVsdCc+UGRmIGRlIHBydWViYXM8L3JkZjpsaT48L3JkZjpBbHQ+PC9kYzp0aXRsZT48ZGM6Y3JlYXRvcj48cmRmOlNlcT48cmRmOmxpPkluZm9ybcOhdGljYSBFbCBDb3J0ZSBJbmdsw6lzPC9yZGY6bGk+PC9yZGY6U2VxPjwvZGM6Y3JlYXRvcj48ZGM6ZGVzY3JpcHRpb24+PHJkZjpTZXE+PHJkZjpsaT4oKTwvcmRmOmxpPjwvcmRmOlNlcT48L2RjOmRlc2NyaXB0aW9uPjwvcmRmOkRlc2NyaXB0aW9uPgo8L3JkZjpSREY+CjwveDp4bXBtZXRhPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCjw/eHBhY2tldCBlbmQ9J3cnPz4KZW5kc3RyZWFtCmVuZG9iagoyIDAgb2JqCjw8L1Byb2R1Y2VyKEdQTCBHaG9zdHNjcmlwdCA5LjA1KQovQ3JlYXRpb25EYXRlKEQ6MjAxMjA1MzEwOTI5NDgrMDInMDAnKQovTW9kRGF0ZShEOjIwMTIwNTMxMDkyOTQ4KzAyJzAwJykKL1RpdGxlKFwzNzZcMzc3XDAwMFBcMDAwZFwwMDBmXDAwMCBcMDAwZFwwMDBlXDAwMCBcMDAwcFwwMDByXDAwMHVcMDAwZVwwMDBiXDAwMGFcMDAwcykKL0NyZWF0b3IoXDM3NlwzNzdcMDAwUFwwMDBEXDAwMEZcMDAwQ1wwMDByXDAwMGVcMDAwYVwwMDB0XDAwMG9cMDAwclwwMDAgXDAwMFZcMDAwZVwwMDByXDAwMHNcMDAwaVwwMDBvXDAwMG5cMDAwIFwwMDAxXDAwMC5cMDAwM1wwMDAuXDAwMDIpCi9BdXRob3IoXDM3NlwzNzdcMDAwSVwwMDBuXDAwMGZcMDAwb1wwMDByXDAwMG1cMDAwXDM0MVwwMDB0XDAwMGlcMDAwY1wwMDBhXDAwMCBcMDAwRVwwMDBsXDAwMCBcMDAwQ1wwMDBvXDAwMHJcMDAwdFwwMDBlXDAwMCBcMDAwSVwwMDBuXDAwMGdcMDAwbFwwMDBcMzUxXDAwMHMpCi9LZXl3b3JkcygpCi9TdWJqZWN0KCk+PmVuZG9iagp4cmVmCjAgMTIKMDAwMDAwMDAwMCA2NTUzNSBmIAowMDAwMDAwODU0IDAwMDAwIG4gCjAwMDAwMDI4MTkgMDAwMDAgbiAKMDAwMDAwMDc5NSAwMDAwMCBuIAowMDAwMDAwNjUzIDAwMDAwIG4gCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMDYzNCAwMDAwMCBuIAowMDAwMDAwOTY3IDAwMDAwIG4gCjAwMDAwMDExMDAgMDAwMDAgbiAKMDAwMDAwMTAzNiAwMDAwMCBuIAowMDAwMDAwOTE5IDAwMDAwIG4gCjAwMDAwMDExNjIgMDAwMDAgbiAKdHJhaWxlcgo8PCAvU2l6ZSAxMiAvUm9vdCAxIDAgUiAvSW5mbyAyIDAgUgovSUQgWzwyRjhDOUUzOENDREY1REZDODAyRDY4M0U1OEQ3Qjc5Nz48MkY4QzlFMzhDQ0RGNURGQzgwMkQ2ODNFNThEN0I3OTc+XQo+PgpzdGFydHhyZWYKMzM1MAolJUVPRgo=", binario.getContenido());
//
//			Repositorio repositorio = ubicacion.getRepositorio();
//
//			Assert.assertNotNull(repositorio);
//
//			Assert.assertTrue(repositorio.isAlfresco());
//
//			Assert.assertEquals("idRepAlfresco", repositorio.getIdRepositorio());
//			Assert.assertEquals("localizador" ,repositorio.getLocalizador());
//
//			Almacenamiento almacenamiento = indiceElectronico.getAlmacenamiento();
//
//			Assert.assertNotNull(almacenamiento);
//
//			Assert.assertTrue(almacenamiento.isRepositorio());
//
//			Assert.assertEquals("prueba1", almacenamiento.getNombre());
//			Assert.assertEquals("pdf", almacenamiento.getExtension());
//			Assert.assertEquals("Fichero de pruebas", almacenamiento.getDescripcion());
//			Assert.assertEquals("pruebas", almacenamiento.getClasificador());
//
//			Repositorio repositorioAlmacenamiento = almacenamiento.getRepositorio();
//
//			Assert.assertNotNull(repositorioAlmacenamiento);
//
//			Assert.assertTrue(repositorioAlmacenamiento.isInvesdoc());
//
//			Assert.assertEquals("IdRepositorioInvesdoc", repositorioAlmacenamiento.getIdRepositorio());
//			Assert.assertEquals("LocalizadorInvesdoc" ,repositorioAlmacenamiento.getLocalizador());
//		}

	}


	private byte[] getFicheroTransferencia() throws Exception {
		return getFicheroAsBase64(getUrlFromClassPath(FICHERO_XML_TRANSFERENCIA));
	}

	private byte[] getFicheroTransferenciaSimple() throws Exception {
		return getFicheroAsBase64(getUrlFromClassPath(FICHERO_XML_TRANSFERENCIA_ELECTRONICA_SIMPLE));
	}


}
