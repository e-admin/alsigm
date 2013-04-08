/*
 	 RFC 2252                   LADPv3 Attributes               December 1997

   4.3.2. Syntax Object Identifiers

   
   Syntaxes for use with LDAP are named by OBJECT IDENTIFIERs, which are
   dotted-decimal strings.  These are not intended to be displayed to
   users.

   noidlen = numericoid [ "{" len "}" ]

   len     = numericstring

   The following table lists some of the syntaxes that have been defined
   for LDAP thus far.  The H-R column suggests whether a value in that
   syntax would likely be a human readable string.  Clients and servers
   need not implement all the syntaxes listed here, and MAY implement
   other syntaxes.

   Other documents may define additional syntaxes.  However, the
   definition of additional arbitrary syntaxes is strongly deprecated
   since it will hinder interoperability: today's client and server
   implementations generally do not have the ability to dynamically
   recognize new syntaxes.  In most cases attributes will be defined
   with the syntax for directory strings.



   Value being represented        H-R OBJECT IDENTIFIER
   =================================================================   
   ACI Item                        N  1.3.6.1.4.1.1466.115.121.1.1
   Access Point                    Y  1.3.6.1.4.1.1466.115.121.1.2
   Attribute Type Description      Y  1.3.6.1.4.1.1466.115.121.1.3
   Audio                           N  1.3.6.1.4.1.1466.115.121.1.4
   Binary                          N  1.3.6.1.4.1.1466.115.121.1.5
   Bit String                      Y  1.3.6.1.4.1.1466.115.121.1.6
   Boolean                         Y  1.3.6.1.4.1.1466.115.121.1.7
   Certificate                     N  1.3.6.1.4.1.1466.115.121.1.8
   Certificate List                N  1.3.6.1.4.1.1466.115.121.1.9
   Certificate Pair                N  1.3.6.1.4.1.1466.115.121.1.10
   Country String                  Y  1.3.6.1.4.1.1466.115.121.1.11
   DN                              Y  1.3.6.1.4.1.1466.115.121.1.12
   Data Quality Syntax             Y  1.3.6.1.4.1.1466.115.121.1.13
   Delivery Method                 Y  1.3.6.1.4.1.1466.115.121.1.14
   Directory String                Y  1.3.6.1.4.1.1466.115.121.1.15
   DIT Content Rule Description    Y  1.3.6.1.4.1.1466.115.121.1.16
   DIT Structure Rule Description  Y  1.3.6.1.4.1.1466.115.121.1.17
   DL Submit Permission            Y  1.3.6.1.4.1.1466.115.121.1.18
   DSA Quality Syntax              Y  1.3.6.1.4.1.1466.115.121.1.19
   DSE Type                        Y  1.3.6.1.4.1.1466.115.121.1.20
   Enhanced Guide                  Y  1.3.6.1.4.1.1466.115.121.1.21
   Facsimile Telephone Number      Y  1.3.6.1.4.1.1466.115.121.1.22
   Fax                             N  1.3.6.1.4.1.1466.115.121.1.23
   Generalized Time                Y  1.3.6.1.4.1.1466.115.121.1.24
   Guide                           Y  1.3.6.1.4.1.1466.115.121.1.25
   IA5 String                      Y  1.3.6.1.4.1.1466.115.121.1.26
   INTEGER                         Y  1.3.6.1.4.1.1466.115.121.1.27
   JPEG                            N  1.3.6.1.4.1.1466.115.121.1.28
   LDAP Syntax Description         Y  1.3.6.1.4.1.1466.115.121.1.54
   LDAP Schema Definition          Y  1.3.6.1.4.1.1466.115.121.1.56
   LDAP Schema Description         Y  1.3.6.1.4.1.1466.115.121.1.57
   Master And Shadow Access Points Y  1.3.6.1.4.1.1466.115.121.1.29
   Matching Rule Description       Y  1.3.6.1.4.1.1466.115.121.1.30
   Matching Rule Use Description   Y  1.3.6.1.4.1.1466.115.121.1.31
   Mail Preference                 Y  1.3.6.1.4.1.1466.115.121.1.32
   MHS OR Address                  Y  1.3.6.1.4.1.1466.115.121.1.33
   Modify Rights                   Y  1.3.6.1.4.1.1466.115.121.1.55
   Name And Optional UID           Y  1.3.6.1.4.1.1466.115.121.1.34
   Name Form Description           Y  1.3.6.1.4.1.1466.115.121.1.35
   Numeric String                  Y  1.3.6.1.4.1.1466.115.121.1.36
   Object Class Description        Y  1.3.6.1.4.1.1466.115.121.1.37
   Octet String                    Y  1.3.6.1.4.1.1466.115.121.1.40
   OID                             Y  1.3.6.1.4.1.1466.115.121.1.38
   Other Mailbox                   Y  1.3.6.1.4.1.1466.115.121.1.39
   Postal Address                  Y  1.3.6.1.4.1.1466.115.121.1.41
   Protocol Information            Y  1.3.6.1.4.1.1466.115.121.1.42
   Presentation Address            Y  1.3.6.1.4.1.1466.115.121.1.43
   Printable String                Y  1.3.6.1.4.1.1466.115.121.1.44
   Substring Assertion             Y  1.3.6.1.4.1.1466.115.121.1.58
   Subtree Specification           Y  1.3.6.1.4.1.1466.115.121.1.45
   Supplier Information            Y  1.3.6.1.4.1.1466.115.121.1.46
   Supplier Or Consumer            Y  1.3.6.1.4.1.1466.115.121.1.47
   Supplier And Consumer           Y  1.3.6.1.4.1.1466.115.121.1.48
   Supported Algorithm             N  1.3.6.1.4.1.1466.115.121.1.49
   Telephone Number                Y  1.3.6.1.4.1.1466.115.121.1.50
   Teletex Terminal Identifier     Y  1.3.6.1.4.1.1466.115.121.1.51
   Telex Number                    Y  1.3.6.1.4.1.1466.115.121.1.52
   UTC Time                        Y  1.3.6.1.4.1.1466.115.121.1.53
 */

package ieci.tdw.ispac.ispaclib.ldap.common;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.HashSet;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;


public class HumanReadableAttr {
	
	private static HashSet m_attr = new HashSet();

	static {
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.2");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.3");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.6");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.7");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.11");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.12");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.13");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.14");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.15");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.16");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.17");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.18");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.19");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.20");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.21");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.22");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.24");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.25");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.26");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.27");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.54");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.56");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.57");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.29");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.30");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.31");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.32");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.33");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.55");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.34");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.35");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.36");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.37");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.40");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.38");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.39");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.41");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.42");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.43");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.44");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.58");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.45");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.46");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.47");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.48");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.50");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.51");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.52");
		m_attr.add("1.3.6.1.4.1.1466.115.121.1.53");
	}

	public static boolean isReadable(Attribute attr) throws ISPACException {
		boolean isReadable = false;
		try {
			DirContext dc = attr.getAttributeDefinition();
			String[] out = { "syntax" };
			Attributes attrs = dc.getAttributes("", out);
			if (attrs != null) {
				NamingEnumeration neAttrs = attrs.getAll();
				if (neAttrs.hasMore()) {
					BasicAttribute ba = (BasicAttribute) neAttrs.next();
					Object value = ba.get();
					isReadable = m_attr.contains(value);
				}
			}
		} catch (Exception e) {
			throw new ISPACException("Error en HumanReadableAttr::isReadable: "
					+ e);
		}
		return isReadable;
	}
}