/*
 * Created on 02-nov-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package deposito.actions;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.FormatoHuecoVO;

public class FormatoHuecoPO extends FormatoHuecoVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FormatoHuecoPO.class);

	GestorEstructuraDepositoBI depositoBI = null;
	Boolean modificable = null;

	public FormatoHuecoPO(GestorEstructuraDepositoBI depositoBI,
			FormatoHuecoVO adaptee) {
		this.depositoBI = depositoBI;
		try {
			BeanUtils.copyProperties(this, adaptee);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		}
	}

	public boolean isModificable() {
		if (modificable == null)
			modificable = new Boolean(
					depositoBI.isFormatoHuecoModificable(getId()));
		return modificable.booleanValue();
	}
}
