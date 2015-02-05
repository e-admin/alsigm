package es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.decorator.TableDecorator;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoRegistroEnumVO;

/**
 * Decorador de displayTag para completar información de los elementos
 * de la bandeja de entrada de Intercambio Registral.
 * Con este decorador conseguimos que cierta información sólo se recupere
 * cuando se va a pintar
 */
public class BandejaEntradaTableDecorator extends TableDecorator {


	public String getNumeroRegistro()
	{
		BandejaEntradaItemVO bandejaEntrada = (BandejaEntradaItemVO)getCurrentRowObject();
		completarBandejaEntradaItem(bandejaEntrada);
		return bandejaEntrada.getNumeroRegistro();
	}

	public String getEstado()
	{
		BandejaEntradaItemVO bandejaEntrada = (BandejaEntradaItemVO)getCurrentRowObject();
		return bandejaEntrada.getEstado().getName();
	}

	public String getDocumentacionFisica()
	{
		BandejaEntradaItemVO bandejaEntrada = (BandejaEntradaItemVO)getCurrentRowObject();
		return bandejaEntrada.getDocumentacionFisicaIntercambioRegistral().getName();
	}

	public String getDocumentacionFisicaId()
	{
		BandejaEntradaItemVO bandejaEntrada = (BandejaEntradaItemVO)getCurrentRowObject();
		return bandejaEntrada.getDocumentacionFisicaIntercambioRegistral().getValue();
	}

	private void completarBandejaEntradaItem(BandejaEntradaItemVO bandejaEntrada) {

		IntercambioRegistralManager intercambioManager = IsicresManagerProvider.getInstance().getIntercambioRegistralManager();
		
		// solo en caso de lso aceptados que generarn registro, los rechazados no
		if(bandejaEntrada.getEstado()== EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO){ 
			bandejaEntrada = intercambioManager.completarBandejaEntradaItem(bandejaEntrada);
			bandejaEntrada.setOrigenName(StringEscapeUtils.escapeHtml(bandejaEntrada.getOrigenName()));
			((List<BandejaEntradaItemVO>)getDecoratedObject()).set(getViewIndex(), bandejaEntrada);
		}


	}

	public String getTipoLibro() {
		String result = "";
		BandejaEntradaItemVO bandejaEntrada = (BandejaEntradaItemVO) getCurrentRowObject();
		if (bandejaEntrada.getTipoLibro() != null) {
			if (bandejaEntrada.getTipoLibro().intValue() == TipoRegistroEnumVO.ENTRADA_VALUE) {
				result = TipoRegistroEnumVO.ENTRADA.getName();
			} else {
				result = TipoRegistroEnumVO.SALIDA.getName();
			}
		}

		return result;
	}

	@Override
	public String addRowId() {
		BandejaEntradaItemVO bandejaEntrada = (BandejaEntradaItemVO)getCurrentRowObject();
		if(bandejaEntrada.getIdRegistro()!=null)
		{
			return Long.toString(bandejaEntrada.getIdRegistro());
		}
		else
		{
			return Long.toString(bandejaEntrada.getIdIntercambioInterno());
		}
	}
}
