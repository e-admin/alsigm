package es.ieci.tecdoc.fwktd.csv.web.delegate.impl;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.csv.web.delegate.CaptchaDelegate;

/**
 * Implementación del delegate de gestión de captchas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CaptchaDelegateImpl implements CaptchaDelegate {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CaptchaDelegateImpl.class);

    private static int DEFAULT_WITH = 200;
    private static int DEFAULT_HEIGHT = 50;

    private static final List<Color> COLORS = new ArrayList<Color>(2);
    private static final List<Font> FONTS = new ArrayList<Font>(3);

    static {
        COLORS.add(Color.BLACK);
        COLORS.add(Color.BLUE);

        FONTS.add(new Font("Geneva", Font.ITALIC, 48));
        FONTS.add(new Font("Courier", Font.BOLD, 48));
        FONTS.add(new Font("Arial", Font.BOLD, 48));
    }

    private static WordRenderer DEFAULT_WORD_RENDERER = new ColoredEdgesWordRenderer(COLORS, FONTS);

    /**
     * Anchura de la imagen del captcha.
     */
    private int with = DEFAULT_WITH;

    /**
     * Altura de la imagen del captcha.
     */
    private int height = DEFAULT_HEIGHT;

    /**
     * Generador de palabras.
     */
    private WordRenderer wordRenderer = DEFAULT_WORD_RENDERER;

	/**
	 * Constructor.
	 */
	public CaptchaDelegateImpl() {
		super();
	}

	public int getWith() {
		return with;
	}

	public void setWith(int with) {
		this.with = with;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public WordRenderer getWordRenderer() {
		return wordRenderer;
	}

	public void setWordRenderer(WordRenderer wordRenderer) {
		this.wordRenderer = wordRenderer;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.web.delegate.CaptchaDelegate#renderCaptcha(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void renderCaptcha(HttpServletRequest request, HttpServletResponse response) {

		logger.info("Entrada en renderCaptcha");

        ColoredEdgesWordRenderer wordRenderer = new ColoredEdgesWordRenderer(COLORS, FONTS);
        Captcha captcha = new Captcha.Builder(getWith(), getHeight())
        		.addText(wordRenderer)
                .gimp()
                .addNoise()
                .addBackground(new GradiatedBackgroundProducer(Color.LIGHT_GRAY, Color.WHITE))
                .build();

        CaptchaServletUtil.writeImage(response, captcha.getImage());
        request.getSession().setAttribute(Captcha.NAME, captcha);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.web.delegate.CaptchaDelegate#validarCaptcha(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	public boolean validarCaptcha(HttpServletRequest request, String answer) {

		logger.info("Entrada en validarCaptcha: answer=[{}]", answer);

		boolean valid = false;

		if (StringUtils.isNotBlank(answer)) {
			Captcha captcha = (Captcha) request.getSession().getAttribute(Captcha.NAME);
			if (captcha != null) {
				valid = captcha.isCorrect(answer);
			}
		}

		return valid;
	}
}
