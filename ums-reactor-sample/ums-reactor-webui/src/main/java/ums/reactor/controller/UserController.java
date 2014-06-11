package ums.reactor.controller;

import static reactor.event.selector.Selectors.$;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.ejb.EJB;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.Reactor;
import reactor.core.action.WhenAction;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.event.Event;
import reactor.function.Consumer;
import reactor.function.Function;
import reactor.tuple.Tuple2;
import ums.reactor.domain.User;
// import ums.reactor.domain.Role;
// import ums.reactor.domain.User;
import ums.reactor.dto.UserDTO;
import ums.reactor.dto.UserListDTO;
import ums.reactor.ejb.UserDaoBean;
import ums.reactor.event.UserEvent;
import ums.reactor.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    protected static final String MODEL_ATTIRUTE_USER = "user";

    private static final String ADD_USER_VIEW = "users/create";

    private static final String SHOW_USERS_VIEW = "users/list";

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
    UserListDTO getUsers() {
        final CountDownLatch latch = new CountDownLatch(1);
        final UserListDTO userListDto = new UserListDTO();

        reactor.on($(UserEvent.USER_QUERY_REPLY), new WhenAction(null, reactor, userListDto, userListDto) {

        });

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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String initializeForm(Model model) {
        Map<Integer, String> priority = new LinkedHashMap<Integer, String>();
        priority.put(1, "Admin Role");
        priority.put(2, "Regular Role");
        model.addAttribute("roleList", priority);
        return ADD_USER_VIEW;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody
    @Valid
    UserDTO userDTO, BindingResult result) {
        userService.fireEvent(UserEvent.USER_CREATE, userDTO);
        return SHOW_USERS_VIEW;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody
    UserDTO userDTO) {
        userService.fireEvent(UserEvent.USER_UPDATE, userDTO);
        return SHOW_USERS_VIEW;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Boolean delete(@RequestParam
    String id) {
        UserDTO existingUser = new UserDTO();
        existingUser.setId(id);
        userService.fireEvent(UserEvent.USER_DELETE, existingUser);
        return true;
    }
}
