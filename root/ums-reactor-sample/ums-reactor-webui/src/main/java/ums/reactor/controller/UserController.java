package ums.reactor.controller;

import static reactor.event.selector.Selectors.$;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.event.Event;
import reactor.function.Consumer;
import reactor.function.Function;
import reactor.tuple.Tuple2;
import ums.reactor.domain.Role;
import ums.reactor.domain.User;
import ums.reactor.domain.UserListDto;
import ums.reactor.ejb.UserDaoBean;
import ums.reactor.event.UserEvent;
import ums.reactor.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    @Qualifier("rootReactor")
    private Reactor reactor;

    @Autowired
    private UserService userService;

    @RequestMapping
    public String getUsersPage() {
        return "users";
    }

   

    @RequestMapping(value = "/records")
    public @ResponseBody
    UserListDto getUsers() {
        final CountDownLatch latch = new CountDownLatch(1);
        final UserListDto userListDto = new UserListDto();
        reactor.on($(UserEvent.USER_QUERY_REPLY), new Consumer<Event<List<User>>>() {

            @Override
            public void accept(Event<List<User>> evt) {
                userListDto.setUsers(evt.getData());
                latch.countDown();
            }
        });
        userService.fireEvent(UserEvent.USER_GET_ALL, null);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userListDto;
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
