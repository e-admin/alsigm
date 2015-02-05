package ieci.tdw.ispac.ispaclib.producers;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.producers.IProducersConnector;
import ieci.tdw.ispac.ispaclib.producers.vo.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProducersConnectorImpl implements IProducersConnector {

	/**
	 * Contexto de cliente.
	 */
	private IClientContext ctx = null;
	
	
	/**
	 * Constructor
	 * @param ctx Contexto de cliente.
	 */
	public ProducersConnectorImpl(IClientContext ctx) {
		super();
		this.ctx = ctx;
	}
	
	/**
	 * Obtiene el productor principal.
	 * @return Información del productor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Producer getRootProducer() throws ISPACException {
		
		Producer producer = null;
		
		if (ctx != null) {
			IResponsible resp = ctx.getAPI().getRespManagerAPI().getRootResp();
			producer = getProducer(resp);
		}
		
		return producer;
	}

	/**
	 * Obtiene la información de un productor.
	 * @param id Identificador del productor
	 * @return Información del productor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Producer getProducer(String id) throws ISPACException {
		
		Producer producer = null;
		
		if (ctx != null) {
			IResponsible resp = ctx.getAPI().getRespManagerAPI().getResp(id);
			producer = getProducer(resp);
		}
		
		return producer;
	}

	/**
	 * Obtiene la lista de hijos del productor.
	 * @param id Identificador del productor
	 * @return Lista de productores ({@see Producer}.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getProducerChildren(String id) throws ISPACException {

		List producers = new ArrayList();
		
		if (ctx != null) {
			IResponsible resp = ctx.getAPI().getRespManagerAPI().getResp(id);
			if (resp != null) {
				IItemCollection children = resp.getRespOrgUnits();
				producers.addAll(getProducerList(children));	
			}
		}	
		
		Collections.sort(producers, new Comparator() {

			public int compare(Object o1, Object o2) {
				
				String name1 = "";
				String name2 = "";
				
				if ((o1 != null) && (o1 instanceof Producer)) {
					name2 = ((Producer) o1).getName();
				}

				if ((o2 != null) && (o2 instanceof Producer)) {
					name2 = ((Producer) o2).getName();
				}

				return name1.compareToIgnoreCase(name2);
			}
			
		});

		return producers;
	}
	
	/**
	 * Obtiene la lista de antecesores del productor.
	 * @param id Identificador del productor
	 * @return Lista de productores ({@see Producer}.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getProducerAncestors(String id) throws ISPACException {
		
		List producers = new ArrayList();
		
		if (ctx != null) {
			IItemCollection ancestors = ctx.getAPI().getRespManagerAPI().getAncestors(id);
			producers.addAll(getProducerList(ancestors));
		}		
		
		return producers;
	}

	protected List getProducerList(IItemCollection respCollection) throws ISPACException {
		List producers = new ArrayList();
		
		while (respCollection.next()) {
			IResponsible resp = (IResponsible) respCollection.value();
			if (resp != null) {
				producers.add(getProducer(resp));
			}
 		}
		
		return producers;
	}
	
	protected Producer getProducer(IResponsible resp) {

		Producer producer = null;
		
		if (resp != null) {
			producer = new Producer();
			producer.setId(resp.getUID());
			producer.setName(resp.getRespName());
		}
		
		return producer;
	}
}
