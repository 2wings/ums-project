// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.controller;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.Map;

import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ums.plus.dto.SearchDTO;

/**
 * DOC crazyLau  class global comment. Detailled comment
 * @author liushuangyi@126.com
 */
public class AbstractTestUserController {

    protected static final String ERROR_MESSAGE = "errorMessage";
    protected static final String FEEDBACK_MESSAGE = "feedbackMessage";
    private static final String FLASH_ERROR_MESSAGE = "errorMessage";
    static final String FLASH_FEEDBACK_MESSAGE = "feedbackMessage";
    static final String VIEW_REDIRECT_PREFIX = "redirect:";
    static final int PAGE_INDEX = 1;
    protected static final long PERSON_COUNT = 4;
    protected static final Long PERSON_ID = Long.valueOf(5);
    static final String SEARCH_TERM = "liusy";
    @Mock
    MessageSource messageSourceMock;

    /**
     * DOC crazyLau AbstractTestUserController constructor comment.
     */
    public AbstractTestUserController() {
        super();
    }

    /**
     * DOC crazyLau Comment method "createExpectedRedirectViewPath".
     * 
     * @param requestMappingList
     * @return
     */
    protected String createExpectedRedirectViewPath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(VIEW_REDIRECT_PREFIX);
        builder.append(path);
        return builder.toString();
    }

    /**
     * DOC crazyLau Comment method "assertFeedbackMessage".
     * 
     * @param attributes
     * @param feedbackMessageKeyPersonDeleted
     */
    protected void assertFeedbackMessage(RedirectAttributes model, String messageCode) {
        assertFlashMessages(model, messageCode, FLASH_FEEDBACK_MESSAGE);
    }

    private void assertFlashMessages(RedirectAttributes model, String messageCode, String flashMessageParameterName) {
        Map<String, ?> flashMessages = model.getFlashAttributes();
        Object message = flashMessages.get(flashMessageParameterName);
        assertNotNull(message);
        flashMessages.remove(message);
        assertTrue(flashMessages.isEmpty());
    
        verify(messageSourceMock, times(1)).getMessage(eq(messageCode), any(Object[].class), any(Locale.class));
        verifyNoMoreInteractions(messageSourceMock);
    }

    protected void initMessageSourceForFeedbackMessage(String feedbackMessageCode) {
        when(messageSourceMock.getMessage(eq(feedbackMessageCode), any(Object[].class), any(Locale.class))).thenReturn(
                FEEDBACK_MESSAGE);
    }

    protected MessageSource getMessageSourceMock() {
        return this.messageSourceMock;
    }

    protected SearchDTO createSearchDTO() {
        SearchDTO dto = new SearchDTO();
        dto.setPageIndex(PAGE_INDEX);
        dto.setSearchTerm(SEARCH_TERM);
        return dto;
    }

}
