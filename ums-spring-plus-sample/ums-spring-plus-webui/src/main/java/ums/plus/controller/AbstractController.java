// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public abstract class AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    private static final String FLASH_ERROR_MESSAGE = "errorMessage";
    private static final String FLASH_FEEDBACK_MESSAGE = "feedbackMessage";

    private static final String VIEW_REDIRECT_PREFIX = "redirect:";

    @Resource
    protected MessageSource messageSource;

    protected void addFeedbackMessage(RedirectAttributes model, String code, Object... params) {
        LOGGER.debug("Adding feedback message with code: " + code + " and params: " + params);
        Locale current = LocaleContextHolder.getLocale();
        LOGGER.debug("Current locale is " + current);
        String localizedFeedbackMessage = messageSource.getMessage(code, params, current);
        LOGGER.debug("Localized message is: " + localizedFeedbackMessage);
        model.addFlashAttribute(FLASH_FEEDBACK_MESSAGE, localizedFeedbackMessage);
    }

    protected String createRedirectViewPath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(VIEW_REDIRECT_PREFIX);
        builder.append(path);
        return builder.toString();
    }

    /**
     * DOC crazyLau Comment method "setMessageSource".
     * 
     * @param messageSourceMock
     */
    public void setMessageSource(MessageSource messageSourceMock) {
        this.messageSource = messageSourceMock;

    }
}
