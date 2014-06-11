// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ums.plus.domain.User;
import ums.plus.dto.SearchDTO;
import ums.plus.dto.UserDTO;
import ums.plus.service.UserService;

import com.google.inject.Inject;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Controller
public class UserController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    
    protected static final String FEEDBACK_MESSAGE_KEY_PERSON_CREATED = "feedback.message.user.created";
    protected static final String FEEDBACK_MESSAGE_KEY_PERSON_DELETED = "feedback.message.user.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_PERSON_EDITED = "feedback.message.user.edited";

    public static final String PAGE_NUM = "page";
    public static final String ROWS_NUM = "rows";

    protected static final String MODEL_ATTIRUTE_USER = "user";
    protected static final String MODEL_ATTRIBUTE_USERS = "users";

    protected static final String REQUEST_MAPPING_LIST = "/";
    protected static final String USER_LOGIN_FORM_VIEW = "user/login";
    protected static final String USER_ADD_FORM_VIEW = "user/create";
    protected static final String USER_LIST_VIEW = "user/list";
    
    @Autowired
    private UserService userService;

    public UserController() {

    }

    @Inject
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
    public String showList(Model model) {
        LOGGER.debug("Rendering person list page");

        List<User> persons = userService.findAll();
        model.addAttribute(MODEL_ATTRIBUTE_USERS, persons);

        return USER_LIST_VIEW;
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public String showCreatePersonForm(Model model) {
        model.addAttribute(MODEL_ATTIRUTE_USER, new UserDTO());
        return USER_ADD_FORM_VIEW;
    }

    @RequestMapping(value = "/person/create", method = RequestMethod.POST)
    public String submitCreatePersonForm(@Valid @ModelAttribute(MODEL_ATTIRUTE_USER) UserDTO created, BindingResult bindingResult, RedirectAttributes attributes) {
        LOGGER.debug("Create person form was submitted with information: " + created);

        if (bindingResult.hasErrors()) {
            return USER_ADD_FORM_VIEW;
        }
        User user = userService.createUser(created);

        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_PERSON_CREATED, user.getUsername());

        return createRedirectViewPath(REQUEST_MAPPING_LIST);
    }
    
    @RequestMapping(value = "list")
    public @ResponseBody
    List<User> pagingUsers(@RequestParam
    int page, @RequestParam
    int rows) {
        return userService.getPageList(page, rows);
    }

    /**
     * DOC crazyLau Comment method "count".
     * 
     * @param searchCriteria
     * @return
     */
    public long count(SearchDTO searchCriteria) {
        return userService.count(searchCriteria.getSearchTerm());

    }

    /**
     * DOC crazyLau Comment method "setUserService".
     * 
     * @param userServiceMock
     */
    public void setUserService(UserService userServiceMock) {
        this.userService = userServiceMock;
    }

    /**
     * DOC crazyLau Comment method "delete".
     * 
     * @param personId
     * @param attributes
     * @return
     */
    @RequestMapping(value = "user/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id")
    Long userId, RedirectAttributes attributes) {
        User deleted = userService.delete(userId);
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_PERSON_DELETED, deleted.getUsername());
        return createRedirectViewPath(REQUEST_MAPPING_LIST);
    }

    /**
     * DOC crazyLau Comment method "showList".
     */
    @RequestMapping(value = "users/page", method = RequestMethod.POST)
    @ResponseBody
    public List<User> showList(@RequestBody
    SearchDTO searchDto) {

        return null;
    }

}
