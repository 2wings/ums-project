// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.reactor.controller;

import static reactor.event.selector.Selectors.$;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.function.Consumer;
import ums.reactor.domain.User;
import ums.reactor.event.UserEvent;
import ums.reactor.service.UserService;


/**
 * DOC crazyLau  class global comment. Detailled comment
 * @author liushuangyi@126.com
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    
    @Autowired
    @Qualifier("rootReactor")
    private Reactor reactor;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping( method = RequestMethod.GET)
    public String showForm(Map model) {
        User user = new User();
        model.put("user", user);
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public 
    String login(@RequestParam
    String userName, @RequestParam
    String password,
    Map model) {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean loginSuc = new AtomicBoolean();
        reactor.on($(UserEvent.USER_LOGIN_REPLY), new Consumer<Event<Boolean>>() {

            @Override
            public void accept(Event<Boolean> evt) {
                loginSuc.set(evt.getData());
                latch.countDown();
            }
        });

        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setPassword(password);
        userService.fireEvent(UserEvent.USER_LOGIN, newUser);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (loginSuc.get()) {
            model.put("username", newUser.getUserName());
            return "loginSuccess";
        } else {
            return "login";
        }
    }
}
