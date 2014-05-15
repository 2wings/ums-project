package ums.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ums.spring.domain.Role;
import ums.spring.domain.UserEntry;
import ums.spring.dto.UserListDto;
import ums.spring.service.UserService;
import ums.spring.utils.UserFactory;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping
    public String getUsersPage() {
        return "users";
    }

    @RequestMapping(value = "/records")
    public @ResponseBody
    UserListDto getUsers() {
        UserListDto userListDto = new UserListDto();
        userListDto.setUsers(service.readAll());
        return userListDto;
    }

    @RequestMapping(value = "/get")
    public @ResponseBody
    UserEntry get(@RequestBody
    UserEntry user) {
        return service.read(user);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    UserEntry create(@RequestParam
    String userName, @RequestParam
    String password, @RequestParam
    String firstName, @RequestParam
    String lastName, @RequestParam
    Integer role) {
        UserEntry newUser = (UserEntry) UserFactory.create(firstName, lastName, userName, password, role);
        return service.create(newUser);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    UserEntry update(@RequestParam
    String id, @RequestParam
    String firstName, @RequestParam
    String lastName, @RequestParam
    Integer role) {

        UserEntry existingUser = new UserEntry();
        existingUser.setId(id);
        existingUser.setFirstName(firstName);
        existingUser.setLastName(lastName);
        existingUser.setRole(new Role(role));

        return service.update(existingUser);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Boolean delete(@RequestParam
    String id) {
        UserEntry existingUser = new UserEntry();
        existingUser.setUserName(id);
        return service.delete(existingUser);
    }
}
