// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.controller;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import ums.plus.context.TestContext;
import ums.plus.domain.User;
import ums.plus.dto.SearchDTO;
import ums.plus.service.impl.UserService;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class UserControllerTest {

    private static final String FIELD_NAME_FIRST_NAME = "firstName";
    private static final String FIELD_NAME_LAST_NAME = "lastName";

    private static final int PAGE_INDEX = 1;
    private static final long PERSON_COUNT = 4;
    private static final Long PERSON_ID = Long.valueOf(5);

    private static final String FIRST_NAME = "Foo";
    private static final String FIRST_NAME_UPDATED = "FooUpdated";
    private static final String LAST_NAME = "Bar";
    private static final String LAST_NAME_UPDATED = "BarUpdated";

    private static final String SEARCH_TERM = "liusy";

    protected static final String ERROR_MESSAGE = "errorMessage";
    protected static final String FEEDBACK_MESSAGE = "feedbackMessage";

    private static final String FLASH_ERROR_MESSAGE = "errorMessage";
    private static final String FLASH_FEEDBACK_MESSAGE = "feedbackMessage";

    private static final String VIEW_REDIRECT_PREFIX = "redirect:";

    private UserController controller;

    @Mock
    private MessageSource messageSourceMock;
    @Mock
    private UserService userServiceMock;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);

        Injector injector = Guice.createInjector(new UserContorllerModule());
        controller = injector.getInstance(UserController.class);
    }

    public class UserContorllerModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(UserService.class).toInstance(userServiceMock);
            bind(MessageSource.class).toInstance(getMessageSourceMock());
        }
    }

    @Test
    public void count() {
        SearchDTO searchCriteria = createSearchDTO();
        when(userServiceMock.count(searchCriteria.getSearchTerm())).thenReturn(PERSON_COUNT);

        long personCount = controller.count(searchCriteria);

        verify(userServiceMock, times(1)).count(searchCriteria.getSearchTerm());
        verifyNoMoreInteractions(userServiceMock);

        assertEquals(PERSON_COUNT, personCount);
    }

    @Test
    public void delete() {
        User deleted = User.getBuilder(FIELD_NAME_FIRST_NAME, FIELD_NAME_LAST_NAME).build();
        deleted.setId(PERSON_ID);

        when(userServiceMock.delete(PERSON_ID)).thenReturn(deleted);
        initMessageSourceForFeedbackMessage(UserController.FEEDBACK_MESSAGE_KEY_PERSON_DELETED);
        
        RedirectAttributes attributes = new RedirectAttributesModelMap();
        String view = controller.delete(PERSON_ID, attributes);

        verify(userServiceMock, times(1)).delete(PERSON_ID);
        verifyNoMoreInteractions(userServiceMock);
        assertFeedbackMessage(attributes, UserController.FEEDBACK_MESSAGE_KEY_PERSON_DELETED);

        String expectedView = createExpectedRedirectViewPath(UserController.REQUEST_MAPPING_LIST);
        assertEquals(expectedView, view);
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

    private SearchDTO createSearchDTO() {
        SearchDTO dto = new SearchDTO();
        dto.setPageIndex(PAGE_INDEX);
        dto.setSearchTerm(SEARCH_TERM);
        return dto;
    }

}
