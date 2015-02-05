package ieci.tdw.ispac.ispacmgr.action.form;

import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;

/**
 * @author RAULHC
 *
 */
public class CustomBatchForm extends BatchForm {
    private String xml;

    /**
     * @return Retorna xml.
     */
    public String getXml() {
        return xml;
    }
    /**
     * @param xml Establece el valor de xml
     */
    public void setXml(String xml) {
        this.xml = xml;
    }
}
