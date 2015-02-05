package es.ieci.plusvalias.sigem.receipt;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;

public interface ReceiptFiller {

	public void fillReceipt(AcroFields poFormFlds, XmlDocument poDoc) throws IOException, DocumentException;
}
