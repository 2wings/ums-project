// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.inject.Inject;

import ums.plus.domain.User;
import ums.plus.dto.SearchDTO;
import ums.plus.service.impl.UserService;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Controller
@RequestMapping("users")
public class UserController extends AbstractController {

    protected static final String FEEDBACK_MESSAGE_KEY_PERSON_CREATED = "feedback.message.user.created";
    protected static final String FEEDBACK_MESSAGE_KEY_PERSON_DELETED = "feedback.message.user.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_PERSON_EDITED = "feedback.message.user.edited";

    public static final String PAGE_NUM = "page";
    public static final String ROWS_NUM = "rows";

    protected static final String REQUEST_MAPPING_LIST = "/";

    @Autowired
    private UserService userService;

    @Inject
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String showMainView() {
        return "index";
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

}
