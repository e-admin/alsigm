/*
 * Created on 14-mar-2005
 *
 */
package ieci.tdw.ispac.ispacweb.manager;

import ieci.tdw.ispac.ispacweb.util.PathUtils;
import ieci.tdw.ispac.ispacweb.util.ResourceUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;


/**
 * @author juanin
 *
 */
public class ISPACRewrite
{
    String ispacbase;
    String skin;
    String sep;
    ServletContext mservletctx;

    public ISPACRewrite(ServletContext servletctx)
    {
        mservletctx=servletctx;
        ispacbase =(String)servletctx.getAttribute("ispacbase");
        skin =(String)servletctx.getAttribute("skin");
        //sep=System.getProperty("file.separator");
        sep="/";
    }

    public String rewrite(HttpServletRequest request,String url) throws JspException
    {
        String baseurl = request.getContextPath();
        return rewriteAction(baseurl,ispacbase,url);
    }
    public String rewriteSkin(HttpServletRequest request,String url) throws JspException
    {
        String baseurl = request.getContextPath();
        return rewriteHref(baseurl,ispacbase,skin,url);
    }

    public String rewriteRealPath(String url) throws JspException
    {
        return ResourceUtil.getFileRealPath(mservletctx, rewriteAction("",ispacbase,url));
    }

    public String rewriteRelativePath(String url) throws JspException
    {
        return rewriteAction("",ispacbase,url);
    }
    public String rewriteSkinRealPath(String url) throws JspException
    {
        return ResourceUtil.getFileRealPath(mservletctx, rewriteHref("",ispacbase,skin,url));
    }

    private String rewriteHref(String baseurl,String ispacbase,String skin,String url)
    {
        if (ispacbase==null || ispacbase.equalsIgnoreCase(""))
            return baseurl+=sep+skin+sep+url;

        return baseurl+=sep+ispacbase+sep+skin+sep+url;
    }

    private String rewriteAction(String baseurl,String ispacbase,String url)
    {
        if (ispacbase==null || ispacbase.equalsIgnoreCase(""))
            return baseurl+=sep+url;

        return baseurl+=sep+ispacbase+sep+url;
    }

    public String getBasePath()
    {
        String path = PathUtils.getRealPath(mservletctx, "/");
        return path+ispacbase;
    }

    public String rewritePath(String relativepath)
    {
        if (relativepath.startsWith(sep))
            return getBasePath()+relativepath;

        return getBasePath()+sep+relativepath;
    }
}
