package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.namespace.QName;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XPathReaderUtil {

	private InputStream xmlInputStream;
	private Document xmlDocument;
	private XPath xPath;

	public XPathReaderUtil(InputStream xmlInputStream) {
		this.xmlInputStream = xmlInputStream;
		initObjects();
	}

	private void initObjects() {
		try {
			xmlDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(xmlInputStream);
			xPath = XPathFactory.newInstance().newXPath();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}

	public Object read(String expression, QName returnType) {
		try {
			XPathExpression xPathExpression = xPath.compile(expression);
			return xPathExpression.evaluate(xmlDocument, returnType);
		} catch (XPathExpressionException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
