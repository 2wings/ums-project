package ums.reactor.handler;

import javax.ejb.EJB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.ReplyTo;
import reactor.spring.annotation.Selector;
import ums.reactor.domain.User;
import ums.reactor.dto.UserListDto;
import ums.reactor.ejb.UserDaoBean;
import ums.reactor.internal.UserEvent;

@Component
public class UserHandler {

    @EJB(mappedName = "java:app/reactor-ums-webui/UserDaoBean")
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
}
