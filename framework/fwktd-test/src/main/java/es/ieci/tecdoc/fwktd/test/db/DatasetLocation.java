/**
 * 
 */
package es.ieci.tecdoc.fwktd.test.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

/**
 * Anotatión para describir el fichero de datos y tipo de dataset a utilizar en
 * pruebas unitarias de Daos.
 * 
 * @author IECISA
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface DatasetLocation {

	public String value() default "";

	public Class<? extends AbstractDataSet> type() default XmlDataSet.class;

}
