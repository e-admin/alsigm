/**
 *
 */
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.List;

import com.ieci.tecdoc.common.invesicres.ScrDistreg;

/**
 * @author 66575267
 *
 */
public class ScrDistRegResults implements Serializable {

	private static final long serialVersionUID = 1L;

	//Información de la distribución
	private ScrDistreg scrDistReg;
	//Información del registro
	private AxSf axsf;
	//Información del archivador
	private Object idocarch;

	//Literal del origen de la distribucion
	private String sourceDescription;
	//Literal del destino de la distribucion
	private String targetDescription;
	//Tipo de distribucion
	private Integer distType;

	//Literal con los destinos actuales de la distribucion
	private String targetActualDescription;

	public ScrDistRegResults(ScrDistreg scrDistReg, AxSf axsf, Object idocarch,
			String sourceDescription, String targetDescription,
			Integer distType, String targetActualDescription) {
		super();
		this.scrDistReg = scrDistReg;
		this.axsf = axsf;
		this.idocarch = idocarch;
		this.sourceDescription = sourceDescription;
		this.targetDescription = targetDescription;
		this.distType = distType;
		this.targetActualDescription = targetActualDescription;
	}

	public ScrDistRegResults(ScrDistreg scrDistReg, AxSf axsf,
			Object idocarch, String sourceDescription,
			String targetDescription, Integer distType) {
		super();
		this.scrDistReg = scrDistReg;
		this.axsf = axsf;
		this.idocarch = idocarch;
		this.sourceDescription = sourceDescription;
		this.targetDescription = targetDescription;
		this.distType = distType;
	}

	public ScrDistreg getScrDistReg() {
		return scrDistReg;
	}

	public void setScrDistReg(ScrDistreg scrDistReg) {
		this.scrDistReg = scrDistReg;
	}

	public AxSf getAxsf() {
		return axsf;
	}

	public void setAxsf(AxSf axsf) {
		this.axsf = axsf;
	}

	public Object getIdocarch() {
		return idocarch;
	}

	public void setIdocarch(Object idocarch) {
		this.idocarch = idocarch;
	}

	public String getSourceDescription() {
		return sourceDescription;
	}

	public void setSourceDescription(String sourceDescription) {
		this.sourceDescription = sourceDescription;
	}

	public String getTargetDescription() {
		return targetDescription;
	}

	public void setTargetDescription(String targetDescription) {
		this.targetDescription = targetDescription;
	}

	public Integer getDistType() {
		return distType;
	}

	public void setDistType(Integer distType) {
		this.distType = distType;
	}

	public String getTargetActualDescription() {
		return targetActualDescription;
	}

	public void setTargetActualDescription(String targetActualDescription) {
		this.targetActualDescription = targetActualDescription;
	}



}
