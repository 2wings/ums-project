package ums.reactor.controller;

import static reactor.event.selector.Selectors.$;

import javax.ejb.EJB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.function.Consumer;
import ums.reactor.domain.Role;
import ums.reactor.domain.User;
import ums.reactor.dto.UserListDto;
import ums.reactor.ejb.UserDaoBean;
import ums.reactor.internal.UserEvent;
import ums.reactor.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @EJB(mappedName = "java:app/reactor-ums-webui/UserDaoBean")
    private UserDaoBean userDaoBean;

    @RequestMapping
    public String getUsersPage() {
        return "users";
    }

    @RequestMapping(value = "/records")
    public @ResponseBody
    UserListDto getUsers() {
        UserListDto userListDto = new UserListDto();
        userListDto.setUsers(userDaoBean.readAll());
        return userListDto;
    }

    @RequestMapping(value = "/get")
    public @ResponseBody
    User get(@RequestBody
    User user) {
        return userDaoBean.read(user);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    User create(@RequestParam
    String userName, @RequestParam
    String password, @RequestParam
    String firstName, @RequestParam
    String lastName, @RequestParam
    Integer role) {
        User newUser = new User(firstName, lastName, userName, password, role);
        userService.fireEvent(UserEvent.USER_CREATE, newUser);
        return newUser;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    User update(@RequestParam
    String id, @RequestParam
    String firstName, @RequestParam
    String lastName, @RequestParam
    Integer role) {
        User newUser = new User();
        newUser.setId(id);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setRole(new Role(role));
        userService.fireEvent(UserEvent.USER_UPDATE, newUser);
        return newUser;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Boolean delete(@RequestParam
    String id) {
        User existingUser = new User();
        existingUser.setId(id);
        userService.fireEvent(UserEvent.USER_DELETE, existingUser);
        return true;
    }
}
