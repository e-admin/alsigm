package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author RAULHC
 * 
 */
public class Sello {
    
    private int mWidth = 0;
    private int mHeight = 0;
    private int mFontStyle = Font.PLAIN;
    private int mFontSize = 12;
    private String mFontName = "Arial";
    private Color mBackgroundColor = Color.white;
    private Color mColor = Color.blue;

    private Graphics2D mG2D;
    private Font mFont;
    private BufferedImage mBufferedImage;
    private FontRenderContext mFrc;
    
    public Sello() throws ISPACException {
        this(230, 100);
    }
    
    public Sello(int width, int height) throws ISPACException {
        mWidth = width;
        mHeight = height;
        mBufferedImage = new BufferedImage(mWidth,mHeight,BufferedImage.TYPE_INT_RGB);
        mG2D = mBufferedImage.createGraphics();
        mFont = new Font(mFontName, mFontStyle, mFontSize);
        mG2D.setFont(mFont);
        mG2D.setBackground(mBackgroundColor);
        mG2D.setColor(mColor);
        mG2D.clearRect(0, 0, mWidth, mHeight);
        mFrc = mG2D.getFontRenderContext();
    }
    
    private void drawString(String str, int position) {
        Rectangle2D bounds = mFont.getStringBounds(str, mFrc);
        float width = (float) bounds.getWidth();
        int offset = (int) ((mHeight / 4) * position);
        float y0 = (float) (offset);// - (lineHeight / 2));
        float x0 = (float) ((mWidth - width) / 2);
        mG2D.drawString(str, x0, y0);
    }
    
    
    public void create(String titulo, String numRegistro, String fechaRegistro) {
        int order = 0;
        drawString(titulo, ++order);
        drawString(numRegistro, ++order);
        drawString(fechaRegistro, ++order);
    }
    
    public String save(File file) throws ISPACException {
        String absolutePath = "";
        try {
            FileOutputStream out = new FileOutputStream(file);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(mBufferedImage);
            param.setQuality(1.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(mBufferedImage);
            out.close();
        }
        catch (IOException ioException) {
            throw new ISPACException("Error al guardar fichero de imagen.", ioException);
        }
        absolutePath = file.getAbsolutePath();
        file = null;
        return absolutePath;
        
    }
    
    public String save() throws ISPACException {
//        File file;
//        try {
//	        File directory = new File(mConfig.get(InveSicresConfiguration.SELLO_IMG_PATH));
//	        file = File.createTempFile("sello",".jpg",directory);
//        }
//        catch (IOException ioException) {
//            throw new ISPACException("Error al guardar fichero de imagen.", ioException);
//        }
//        return save(file);
        return save(FileTemporaryManager.getInstance().newFile());
    }
     
    public final Color getBackgroundColor() {
        return mBackgroundColor;
    }

    public final void setBackgroundColor(Color backgroundColor) {
        mBackgroundColor = backgroundColor;
        mG2D.setBackground(backgroundColor);
        mG2D.clearRect(0, 0, mWidth, mHeight);
    }

    public final Color getColor() {
        return mColor;
    }

    public final void setColor(Color color) {
        mColor = color;
        mG2D.setColor(color);
    }

    public final String getFontName() {
        return mFontName;
    }

    public final void setFontName(String fontName) {
        mFontName = fontName;
        mFont = null;
        mFont = new Font(mFontName, mFontStyle, mFontSize);
        mG2D.setFont(mFont);
    }

    public final int getFontSize() {
        return mFontSize;
    }

    public final void setFontSize(int fontSize) {
        mFontSize = fontSize;
        mFont = null;
        mFont = new Font(mFontName, mFontStyle, mFontSize);
        mG2D.setFont(mFont);        
    }

    public final int getFontStyle() {
        return mFontStyle;
    }

    public final void setFontStyle(int fontStyle) {
        mFontStyle = fontStyle;
        mFont = null;
        mFont = new Font(mFontName, mFontStyle, mFontSize);
        mG2D.setFont(mFont);
    }
}

