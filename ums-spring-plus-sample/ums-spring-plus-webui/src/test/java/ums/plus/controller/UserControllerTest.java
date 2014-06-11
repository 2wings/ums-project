// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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

import ums.plus.context.TestContext;
import ums.plus.domain.User;
import ums.plus.dto.SearchDTO;
import ums.plus.dto.UserDTO;
import ums.plus.service.UserService;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class UserControllerTest extends AbstractTestUserController {

    private static final String FIELD_NAME_FIRST_NAME = "firstName";
    private static final String FIELD_NAME_LAST_NAME = "lastName";

    private static final String FIRST_NAME = "Foo";
    private static final String FIRST_NAME_UPDATED = "FooUpdated";
    private static final String LAST_NAME = "Bar";
    private static final String LAST_NAME_UPDATED = "BarUpdated";

    private UserController controller;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);

        Injector injector = Guice.createInjector(new UserContorllerModule());
        controller = injector.getInstance(UserController.class);
    }

    class UserContorllerModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(UserService.class).toInstance(userServiceMock);
            bind(MessageSource.class).toInstance(getMessageSourceMock());
        }
    }

    @Test
    public void createUser() {
        UserDTO user = new UserDTO();
        user.setFirstName("liu");
        user.setLastName("sy");
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

    @Test
    public void showList() {
        // controller.showList();
    }

}
