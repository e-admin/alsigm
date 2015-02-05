package fondos.forms;

import java.util.ArrayList;

import common.pagination.PageInfo;

public interface IBusquedaForm {

	public String getPostBack();

	public void setPostBack(String postBack);

	public String getSignatura_like();

	public void setSignatura_like(String signatura_like);

	public String getNumeroExpediente_like();

	public void setNumeroExpediente_like(String numeroExpediente_like);

	public String getSignatura();

	public void setSignatura(String signatura);

	public String getCodigo();

	public void setCodigo(String codigo);

	public String getCodigoRelacion();

	public void setCodigoRelacion(String codigoRelacion);

	public String[] getEstados();

	public void setEstados(String[] estados);

	public String[] getNiveles();

	public void setNiveles(String[] niveles);

	public PageInfo getPageInfo();

	public void setPageInfo(PageInfo pageInfo);

	public String getTitulo();

	public void setTitulo(String titulo);

	public String[] getNombreObjetoAmbito();

	public void setNombreObjetoAmbito(String[] nombreObjetoAmbito);

	public String getIdFicha();

	public void setIdFicha(String idFicha);

	public String getTipoBusqueda();

	public void setTipoBusqueda(String tipoBusqueda);

	public String[] getIdObjetoAmbito();

	public void setIdObjetoAmbito(String[] idObjetoAmbito);

	public String[] getTipoObjetoAmbito();

	public void setTipoObjetoAmbito(String[] tipoObjetoAmbito);

	public String[] getAbrirpar();

	public void setAbrirpar(String[] abrirpar);

	public String[] getBooleano();

	public void setBooleano(String[] booleano);

	public String[] getCampo();

	public void setCampo(String[] campo);

	public String[] getCerrarpar();

	public void setCerrarpar(String[] cerrarpar);

	public String getTipoElemento();

	public void setTipoElemento(String tipoElemento);

	public String[] getOperador();

	public void setOperador(String[] operador);

	public Integer[] getTipoCampo();

	public void setTipoCampo(Integer[] tipoCampo);

	public String[] getValor1();

	public void setValor1(String[] valor1);

	public String[] getValor1A();

	public void setValor1A(String[] valor1A);

	public String[] getValor1D();

	public void setValor1D(String[] valor1D);

	public String[] getValor1M();

	public void setValor1M(String[] valor1M);

	public String[] getValor2();

	public void setValor2(String[] valor2);

	public String[] getValor2A();

	public void setValor2A(String[] valor2A);

	public String[] getValor2D();

	public void setValor2D(String[] valor2D);

	public String[] getValor2M();

	public void setValor2M(String[] valor2M);

	public String getCalificador();

	public void setCalificador(String calificador);

	public String getCodigoReferencia();

	public void setCodigoReferencia(String codigoReferencia);

	public String getDescriptores();

	public void setDescriptores(String descriptores);

	public String getFechaAFin();

	public void setFechaAFin(String fechaAFin);

	public String getFechaAIni();

	public void setFechaAIni(String fechaAIni);

	public String getFechaCompFin();

	public void setFechaCompFin(String fechaCompFin);

	public String getFechaCompIni();

	public void setFechaCompIni(String fechaCompIni);

	public String getFechaDFin();

	public void setFechaDFin(String fechaDFin);

	public String getFechaDIni();

	public void setFechaDIni(String fechaDIni);

	public String getFechaFinAFin();

	public void setFechaFinAFin(String fechaFinAFin);

	public String getFechaFinAIni();

	public void setFechaFinAIni(String fechaFinAIni);

	public String getFechaFinDFin();

	public void setFechaFinDFin(String fechaFinDFin);

	public String getFechaFinDIni();

	public void setFechaFinDIni(String fechaFinDIni);

	public String getFechaFinFormatoFin();

	public void setFechaFinFormatoFin(String fechaFinFormatoFin);

	public String getFechaFinFormatoIni();

	public void setFechaFinFormatoIni(String fechaFinFormatoIni);

	public String getFechaFinMFin();

	public void setFechaFinMFin(String fechaFinMFin);

	public String getFechaFinMIni();

	public void setFechaFinMIni(String fechaFinMIni);

	public String getFechaFinSFin();

	public void setFechaFinSFin(String fechaFinSFin);

	public String getFechaFinSIni();

	public void setFechaFinSIni(String fechaFinSIni);

	public String getFechaFormatoFin();

	public void setFechaFormatoFin(String fechaFormatoFin);

	public String getFechaFormatoIni();

	public void setFechaFormatoIni(String fechaFormatoIni);

	public String getFechaIniAFin();

	public void setFechaIniAFin(String fechaIniAFin);

	public String getFechaIniAIni();

	public void setFechaIniAIni(String fechaIniAIni);

	public String getFechaIniDFin();

	public void setFechaIniDFin(String fechaIniDFin);

	public String getFechaIniDIni();

	public void setFechaIniDIni(String fechaIniDIni);

	public String getFechaIniFormatoFin();

	public void setFechaIniFormatoFin(String fechaIniFormatoFin);

	public String getFechaIniFormatoIni();

	public void setFechaIniFormatoIni(String fechaIniFormatoIni);

	public String getFechaIniMFin();

	public void setFechaIniMFin(String fechaIniMFin);

	public String getFechaIniMIni();

	public void setFechaIniMIni(String fechaIniMIni);

	public String getFechaIniSFin();

	public void setFechaIniSFin(String fechaIniSFin);

	public String getFechaIniSIni();

	public void setFechaIniSIni(String fechaIniSIni);

	public String getFechaMFin();

	public void setFechaMFin(String fechaMFin);

	public String getFechaMIni();

	public void setFechaMIni(String fechaMIni);

	public String getFechaSFin();

	public void setFechaSFin(String fechaSFin);

	public String getFechaSIni();

	public void setFechaSIni(String fechaSIni);

	public String getFondo();

	public void setFondo(String fondo);

	public String getNumero();

	public void setNumero(String numero);

	public String getNumeroComp();

	public void setNumeroComp(String numeroComp);

	public String getTexto();

	public void setTexto(String texto);

	public String getTipoMedida();

	public void setTipoMedida(String tipoMedida);

	public String getUnidadMedida();

	public void setUnidadMedida(String unidadMedida);

	public String getFechaA();

	public void setFechaA(String fechaA);

	public String getFechaComp();

	public void setFechaComp(String fechaComp);

	public String getFechaD();

	public void setFechaD(String fechaD);

	public String getFechaFinA();

	public void setFechaFinA(String fechaFinA);

	public String getFechaFinD();

	public void setFechaFinD(String fechaFinD);

	public String getFechaFinFormato();

	public void setFechaFinFormato(String fechaFinFormato);

	public String getFechaFinM();

	public void setFechaFinM(String fechaFinM);

	public String getFechaFinS();

	public void setFechaFinS(String fechaFinS);

	public String getFechaFormato();

	public void setFechaFormato(String fechaFormato);

	public String getFechaIniA();

	public void setFechaIniA(String fechaIniA);

	public String getFechaIniD();

	public void setFechaIniD(String fechaIniD);

	public String getFechaIniFormato();

	public void setFechaIniFormato(String fechaIniFormato);

	public String getFechaIniM();

	public void setFechaIniM(String fechaIniM);

	public String getFechaIniS();

	public void setFechaIniS(String fechaIniS);

	public String getFechaM();

	public void setFechaM(String fechaM);

	public String getFechaS();

	public void setFechaS(String fechaS);

	public String getNumeroExpediente();

	public void setNumeroExpediente(String numeroExpediente);

	public String getProductores();

	public void setProductores(String productores);

	public String getCalificadorFinal();

	public void setCalificadorFinal(String calificadorFinal);

	public String getCalificadorInicial();

	public void setCalificadorInicial(String calificadorInicial);

	public String getProcedimiento();

	public void setProcedimiento(String procedimiento);

	public String getRango();

	public void setRango(String rango);

	public String[] getFormatoFecha1();

	public void setFormatoFecha1(String[] formatoFecha1);

	public String[] getFormatoFecha2();

	public void setFormatoFecha2(String[] formatoFecha2);

	public String[] getValor1S();

	public void setValor1S(String[] valor1S);

	public String[] getValor2S();

	public void setValor2S(String[] valor2S);

	public String getContenidoFichero();

	public void setContenidoFichero(String contenidoFichero);

	public String[] getGenericoFechaComp();

	public void setGenericoFechaComp(String[] genericoFechaComp);

	public String[] getGenericoFechaFormato();

	public void setGenericoFechaFormato(String[] genericoFechaFormato);

	public String[] getGenericoFechaA();

	public void setGenericoFechaA(String[] genericoFechaA);

	public String[] getGenericoFechaM();

	public void setGenericoFechaM(String[] genericoFechaM);

	public String[] getGenericoFechaD();

	public void setGenericoFechaD(String[] genericoFechaD);

	public String[] getGenericoFechaS();

	public void setGenericoFechaS(String[] genericoFechaS);

	public String[] getGenericoFechaIniFormato();

	public void setGenericoFechaIniFormato(String[] genericoFechaIniFormato);

	public String[] getGenericoFechaIniA();

	public void setGenericoFechaIniA(String[] genericoFechaIniA);

	public String[] getGenericoFechaIniM();

	public void setGenericoFechaIniM(String[] genericoFechaIniM);

	public String[] getGenericoFechaIniD();

	public void setGenericoFechaIniD(String[] genericoFechaIniD);

	public String[] getGenericoFechaIniS();

	public void setGenericoFechaIniS(String[] genericoFechaIniS);

	public String[] getGenericoFechaFinFormato();

	public void setGenericoFechaFinFormato(String[] genericoFechaFinFormato);

	public String[] getGenericoFechaFinA();

	public void setGenericoFechaFinA(String[] genericoFechaFinA);

	public String[] getGenericoFechaFinM();

	public void setGenericoFechaFinM(String[] genericoFechaFinM);

	public String[] getGenericoFechaFinD();

	public void setGenericoFechaFinD(String[] genericoFechaFinD);

	public String[] getGenericoFechaFinS();

	public void setGenericoFechaFinS(String[] genericoFechaFinS);

	public String[] getGenericoFechaCalificador();

	public void setGenericoFechaCalificador(String[] genericoFechaCalificador);

	public String[] getGenericoTextoLargo();

	public void setGenericoTextoLargo(String[] genericoTextoLargo);

	public String[] getGenericoTextoCorto();

	public void setGenericoTextoCorto(String[] genericoTextoCorto);

	public String[] getGenericoIdFecha();

	public void setGenericoIdFecha(String[] genericoIdFecha);

	public String[] getGenericoIdTextoLargo();

	public void setGenericoIdTextoLargo(String[] genericoIdTextoLargo);

	public String[] getGenericoIdTextoCorto();

	public void setGenericoIdTextoCorto(String[] genericoIdTextoCorto);

	public String[] getGenericoCampoNumerico();

	public void setGenericoCampoNumerico(String[] genericoCampoNumerico);

	public String[] getGenericoIdCampoNumerico();

	public void setGenericoIdCampoNumerico(String[] genericoIdCampoNumerico);

	public String[] getGenericoOperadorCampoNumerico();

	public void setGenericoOperadorCampoNumerico(
			String[] genericoOperadorCampoNumerico);

	public String[] getGenericoCampoNumericoFin();

	public void setGenericoCampoNumericoFin(String[] genericoCampoNumericoFin);

	public String[] getGenericoEtiquetaFecha();

	public void setGenericoEtiquetaFecha(String[] genericoEtiquetaFecha);

	public String[] getGenericoEtiquetaTextoLargo();

	public void setGenericoEtiquetaTextoLargo(
			String[] genericoEtiquetaTextoLargo);

	public String[] getGenericoEtiquetaTextoCorto();

	public void setGenericoEtiquetaTextoCorto(
			String[] genericoEtiquetaTextoCorto);

	public String[] getGenericoEtiquetaCampoNumerico();

	public void setGenericoEtiquetaCampoNumerico(
			String[] genericoEtiquetaCampoNumerico);

	public String[] getGenericoOperadorTextoCorto();

	public void setGenericoOperadorTextoCorto(
			String[] genericoOperadorTextoCorto);

	public void setActivarNiveles(boolean activarNiveles);

	public void setActivarEstados(boolean activarEstados);

	public String getTipoReferencia();

	public void setTipoReferencia(String tipoReferencia);

	public String[] getTiposReferencia();

	public void setTiposReferencia(String[] tiposReferencia);

	public ArrayList getCamposRellenos();

	public boolean isReemplazoValoresNulos();

	public String getFormatoFecha4();

	public void setFormatoFecha4(String formatoFecha4);

	public String getValor4();

	public void setValor4(String valor4);

	public String getValor4D();

	public void setValor4D(String valor4D);

	public String getValor4M();

	public void setValor4M(String valor4M);

	public String getValor4A();

	public void setValor4A(String valor4A);

	public String getValor4S();

	public void setValor4S(String valor4S);

	public String[] getBloqueos();

	public void setBloqueos(String[] bloqueos);

	public void setActivarBloqueos(boolean activarBloqueos);

	// REEMPLAZO
	public boolean isReemplazoSimple();

	public String getReemplazoSimple();

	public void setReemplazoSimple(boolean reemplazoSimple);

	public String getConservada();

	public void setConservada(String conservada);

}