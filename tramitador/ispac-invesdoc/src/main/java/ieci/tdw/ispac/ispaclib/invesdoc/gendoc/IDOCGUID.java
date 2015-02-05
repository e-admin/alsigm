package ieci.tdw.ispac.ispaclib.invesdoc.gendoc;

import org.w3c.dom.Document;

import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.api.errors.ISPACException;
/**
 * Esta clase obtiene los valores de carpeta, documento
 * y página que describen el identificador del documento.
 * El identificador viene dado como un string con el 
 * siguiente formato:
 * 
 * <guid><folder/><document/></guid>

 */
class IDOCGUID
{
	protected int mArchive = 0;
	protected int mFolder = 0;
	protected int mDocument = 0;
	
	public IDOCGUID(String sUID) 
	throws ISPACException
	{
		Document document = XMLDocUtil.newDocument( sUID);
		mArchive = Integer.parseInt(XPathUtil.get( document,"/guid/archive/text()"));
		mFolder = Integer.parseInt(XPathUtil.get( document,"/guid/folder/text()"));
		mDocument = Integer.parseInt(XPathUtil.get( document,"/guid/document/text()"));
	}

	public int getArchive()
	{
		return mArchive;
	}

	public int getFolder()
	{
		return mFolder;
	}
	
	public int getDocument()
	{
		return mDocument;
	}
}