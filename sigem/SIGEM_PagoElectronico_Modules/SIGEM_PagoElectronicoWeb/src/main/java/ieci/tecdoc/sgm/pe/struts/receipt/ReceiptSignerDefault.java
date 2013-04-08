package ieci.tecdoc.sgm.pe.struts.receipt;


public class ReceiptSignerDefault extends ReceiptSigner {

	public byte[] signReceipt(byte[] data) throws Exception {
		return ITextSignUtils.signPDF(data);
	}
}
