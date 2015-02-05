package ieci.tecdoc.sgm.pe.struts.receipt;


public class ReceiptSignerAL3001 extends ReceiptSigner {

	public byte[] signReceipt(byte[] data) throws Exception {
		return ITextSignUtils.signPDF(data);
	}
}
