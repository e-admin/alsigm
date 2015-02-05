package es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.decorator.TableDecorator;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;

/**
 * Decorador de displayTag para completar información de los elementos
 * de la bandeja de salida de Intercambio Registral.
 * Con este decorador conseguimos que cierta información sólo se recupere
 * cuando se va a pintar
 */
public class BandejaSalidaTableDecorator extends TableDecorator {


	public String getNumeroRegistro()
	{
		BandejaSalidaItemVO bandejaSalida = (BandejaSalidaItemVO)getCurrentRowObject();
		completarBandejaSalidaItem(bandejaSalida);
		return bandejaSalida.getNumeroRegistro();
	}

	public String getEstado()
	{
		BandejaSalidaItemVO bandejaSalida = (BandejaSalidaItemVO)getCurrentRowObject();
		return StringEscapeUtils.escapeHtml(bandejaSalida.getEstado().getName());
	}

	public String getEstadoRegistro()
	{
		BandejaSalidaItemVO bandejaSalida = (BandejaSalidaItemVO)getCurrentRowObject();
		return StringEscapeUtils.escapeHtml(bandejaSalida.getEstadoRegistro().getName());
	}

	private void completarBandejaSalidaItem(BandejaSalidaItemVO bandejaSalida) {

		bandejaSalida.setNameEntity(StringEscapeUtils.escapeHtml(bandejaSalida.getNameEntity()));
		((List<BandejaSalidaItemVO>)getDecoratedObject()).set(getViewIndex(), bandejaSalida);


	}

	public String addRowId() {
		BandejaSalidaItemVO bandejaSalida = (BandejaSalidaItemVO)getCurrentRowObject();
		return Long.toString(bandejaSalida.getIdRegistro());
	}
}
