package ums.reactor.handler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.EJB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import reactor.core.Reactor;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.event.Event;
import reactor.function.Function;
import reactor.spring.annotation.Consumer;
import reactor.spring.annotation.ReplyTo;
import reactor.spring.annotation.Selector;
import ums.reactor.domain.User;
import ums.reactor.domain.UserListDto;
import ums.reactor.ejb.UserDaoBean;
import ums.reactor.event.UserEvent;

@Component
public class UserHandler {

    @EJB(mappedName = "java:app/reactor-ums/UserDaoBean")
    private UserDaoBean userDaoBean;

    @Autowired
    @Qualifier("rootReactor")
    private Reactor reactor;

    @Selector(value = UserEvent.USER_CREATE, reactor = "@rootReactor")
    public void handleUserCreate(Event<User> evt) throws Exception {
        userDaoBean.create(evt.getData());
    }

    @Selector(value = UserEvent.USER_UPDATE, reactor = "@rootReactor")
    public void handleUserUpdate(Event<User> evt) throws Exception {
        userDaoBean.update(evt.getData());
    }

    @Selector(value = UserEvent.USER_DELETE, reactor = "@rootReactor")
    public void handleUserDelete(Event<User> evt) throws Exception {
        userDaoBean.delete(evt.getData());
    }

    @Selector(value = UserEvent.USER_GET_ALL, reactor = "@rootReactor")
    @ReplyTo(UserEvent.USER_QUERY_REPLY)
    public List<User> handleQueryAll(Event evt) {
        return userDaoBean.readAll();
    }

    @Selector(value = UserEvent.USER_LOGIN, reactor = "@rootReactor")
    @ReplyTo(UserEvent.USER_LOGIN_REPLY)
    public boolean login(Event evt) {
        User user = (User) evt.getData();
        User existedUser = userDaoBean.find(user.getUserName());
        if (existedUser != null && user.getPassword().equals(existedUser.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

}
