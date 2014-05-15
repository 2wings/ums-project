package samples.ums.ejb.controller;

import javax.ejb.EJB;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import samples.ums.ejb.dao.UserDaoBean;
import samples.ums.ejb.domain.Role;
import samples.ums.ejb.domain.User;
import samples.ums.ejb.dto.UserListDto;

@Controller
@RequestMapping("/users")
public class UserController {

    @EJB(mappedName = "java:app/ejb-ums-webui/UserDaoBean")
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
        return userDaoBean.create(newUser);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    User update(@RequestParam
    String id, @RequestParam
    String firstName, @RequestParam
    String lastName, @RequestParam
    Integer role) {
        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setFirstName(firstName);
        existingUser.setLastName(lastName);
        existingUser.setRole(new Role(role));

        return userDaoBean.update(existingUser);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Boolean delete(@RequestParam
    String id) {
        User existingUser = new User();
        existingUser.setId(id);
        return userDaoBean.delete(existingUser);
    }
}
