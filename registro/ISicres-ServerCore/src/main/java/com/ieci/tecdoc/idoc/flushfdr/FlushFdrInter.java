package com.ieci.tecdoc.idoc.flushfdr;

import java.io.Serializable;

/*
 * @author JCEBRIEN
 * @creationDate 09-sep-2004 13:00:15
 * @version
 * @since
 */
public class FlushFdrInter implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4964582962866166182L;
	private int interId = 0;
	private String interName = null;
	private int domId = 0;
	private String Direction = null;

	protected FlushFdrInter representante;

	/**
	 * @return Returns the direction.
	 */
	public String getDirection() {
		return Direction;
	}

	/**
	 * @param direction
	 *            The direction to set.
	 */
	public void setDirection(String direction) {
		Direction = direction;
	}

	/**
	 * @return Returns the InterName.
	 */
	public String getInterName() {
		return interName;
	}

	/**
	 * @param documentName
	 *            The InterName to set.
	 */
	public void setInterName(String interName) {
		this.interName = interName;
	}

	/**
	 * @return Returns the domId.
	 */
	public int getDomId() {
		return domId;
	}

	/**
	 * @param domId
	 *            The domId to set.
	 */
	public void setDomId(int domId) {
		this.domId = domId;
	}

	/**
	 * @return Returns the interId.
	 */
	public int getInterId() {
		return interId;
	}

	/**
	 * @param interId
	 *            The interId to set.
	 */
	public void setInterId(int interId) {
		this.interId = interId;
	}

	/**
	 * Devuelve los datos del representante del interesado.
	 *
	 * @return
	 */
	public FlushFdrInter getRepresentante() {
		return representante;
	}

	/**
	 *
	 * @param representante
	 */
	public void setRepresentante(FlushFdrInter representante) {
		this.representante = representante;
	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 * @author info.vancauwenberge.tostring plugin
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("FlushFdrInter[");
		buffer.append("interId = ").append(interId);
		buffer.append(", interName = ").append(interName);
		buffer.append(", domId = ").append(domId);
		buffer.append(", Direction = ").append(Direction);
		if (null != getRepresentante()) {
			buffer.append(", repreId = ").append(
					getRepresentante().getInterId());
			buffer.append(", repreName = ").append(
					getRepresentante().getInterName());
			buffer.append(", domRepreId = ").append(
					getRepresentante().getDomId());
			buffer.append(", repreDirection = ").append(
					getRepresentante().getDirection());
		}
		buffer.append("]");
		return buffer.toString();
	}

}
