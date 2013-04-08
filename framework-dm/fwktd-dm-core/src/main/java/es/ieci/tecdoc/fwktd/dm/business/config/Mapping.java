package es.ieci.tecdoc.fwktd.dm.business.config;

public class Mapping {

	private Source source = null;
	private Destination destination = null;

	public Mapping() {
		super();
	}

	public Mapping(Source source, Destination destination) {
		this();
		setSource(source);
		setDestination(destination);
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
}
