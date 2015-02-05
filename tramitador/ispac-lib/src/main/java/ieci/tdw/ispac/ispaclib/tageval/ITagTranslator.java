/*
 * Created on 07-sep-2004
 *
 */
package ieci.tdw.ispac.ispaclib.tageval;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.List;

public interface ITagTranslator
{
	public List translateTags(List tags) throws ISPACException;
	public String translateStringTag(String tag) throws ISPACException;
	public boolean translateBooleanTag(String tag) throws ISPACException;
}