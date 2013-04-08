package ieci.tecdoc.sgm.tram.secretaria.sign;


import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.sign.Sigm30SignConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;

/**
 */
public class SigmDecretoSignConnector extends Sigm30SignConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SigmDecretoSignConnector.class);

	/**
	 * Particularización del formato de la fecha de la firma.
	 */
	protected static SimpleDateFormat DECRETOS_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	/**
	 * Constructor.
	 *
	 */
	public SigmDecretoSignConnector() {
		super();
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.tram.sign.Sigem23SignConnector#generateGrayBandImagen(java.lang.String, com.lowagie.text.pdf.PdfContentByte, float, boolean, float, int, int, java.lang.String, java.util.List)
	 */
	public void generateGrayBandImagen(String dateFirma,
			PdfContentByte pdfContentByte, float margen, boolean vh, float x,
			int numberOfPages, int pageActual, String codCotejo, List signerList)
			throws ISPACException {

		try {

			String font = ISPACConfiguration.getInstance().getProperty(FONT_BAND);
			String encoding = ISPACConfiguration.getInstance().getProperty(ENCODING_BAND);
			float fontSize = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(FONTSIZE_BAND));
			//float positionY = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(MARGIN_BAND));

			String num_decreto="";
			//En el caso de que estemos firmando el modelo de decreto y existan numDecreto se debe incluir en la banda lateral
			String id_tpdoc_decreto=clientContext.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_MODELO_DECRETO);

			if ((signDocument != null) &&
				(StringUtils.equalsIgnoreCase(signDocument.getDocumentType(), id_tpdoc_decreto))) {

				String numexp = signDocument.getNumExp();
				IInvesflowAPI invesflowAPI = clientContext.getAPI();
				IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
				IItemCollection itemcol=entitiesAPI.getEntities(SecretariaConstants.ENTITY_DECRETO, numexp);
				if(itemcol.next())  {
					IItem decreto = itemcol.value();
					if(StringUtils.isNotBlank(decreto.getString(SecretariaConstants.FIELD_DECRETO_NUM_DECRETO))){
					 num_decreto=decreto.getString(SecretariaConstants.FIELD_DECRETO_NUM_DECRETO);
					 num_decreto += " " + getSignDateFormatter(DECRETOS_DATE_FORMATTER).format(decreto.getDate(SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA));
					}
				}
				else{
					logger.warn("No hay decreto en el expediente"+numexp );
				}
			}
			BaseFont bf = BaseFont.createFont(font, encoding, false);
			pdfContentByte.beginText();
			pdfContentByte.setFontAndSize(bf, fontSize);

			BufferedReader br = new BufferedReader(new FileReader(getDataFile()));
			String sCadena = null;
			int i = 0;
			while ((sCadena = br.readLine()) != null) {
				if (vh) {

					pdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, x, margen);

					if (i == 0 ) {
						if(StringUtils.isNotBlank(num_decreto)){

							pdfContentByte.showText(sCadena + num_decreto);
							x += fontSize;
						}
					} else if (i == 1) {

						pdfContentByte.showText(sCadena + codCotejo);

						if ((signerList != null) && (!signerList.isEmpty())) {
							for (int signerCont = 0; signerCont < signerList.size(); signerCont++) {
								x += fontSize;
								pdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, x, margen);
								pdfContentByte.showText((String) signerList.get(signerCont));
							}
						}
						x += fontSize;
					} else if (i == 2) {

						pdfContentByte.showText(sCadena
								+ MessagesFormatter.format(getString(clientContext.getLocale(), "pdf.band.pageInfo"), new String[] {
									String.valueOf(numberOfPages),
									String.valueOf(pageActual),
									String.valueOf(numberOfPages) }));
						x += fontSize;
					} else {
						pdfContentByte.showText(sCadena);
						x += fontSize;
					}
					//i++;
					//x += fontSize;
				} else {

	                pdfContentByte.setTextMatrix(margen, x);

	                if (i == 0 ) {
						if(StringUtils.isNotBlank(num_decreto)){
							pdfContentByte.showText(sCadena + num_decreto);
							x -= fontSize;
						}
					} else  if(i == 1) {

	                    pdfContentByte.showText(sCadena+ codCotejo);

	                    if ((signerList != null) && (!signerList.isEmpty())) {
							for (int signerCont = 0; signerCont < signerList.size(); signerCont++) {
								x -= fontSize;
								pdfContentByte.setTextMatrix(margen, x);
								pdfContentByte.showText((String) signerList.get(signerCont));
							}
	                    }
	                    x -= fontSize;
	                } else if (i == 2) {
	                    //pdfContentByte.showText(sCadena + numberOfPages + " folios. Folio " + pageActual + " de " + numberOfPages + ".");
						pdfContentByte.showText(sCadena
								+ MessagesFormatter.format(getString(clientContext.getLocale(), "pdf.band.pageInfo"), new String[] {
									String.valueOf(numberOfPages),
									String.valueOf(pageActual),
									String.valueOf(numberOfPages) }));
						x -= fontSize;
	                } else {
	                    pdfContentByte.showText(sCadena);
	                    x -= fontSize;
	                }
	                //i++;
	                //x -= fontSize;
	            }

				 i++;
			}

			pdfContentByte.endText();

		} catch (Exception e) {
			logger.error("Error al componer la imagen de la banda lateral", e);
			throw new ISPACException(e);
		}
	}

}