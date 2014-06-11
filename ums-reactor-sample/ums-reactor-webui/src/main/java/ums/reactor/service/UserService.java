package ums.reactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.function.Consumer;
import ums.reactor.domain.User;
import ums.reactor.dto.UserDTO;

@Service
public class UserService {

    @Autowired
    @Qualifier("rootReactor")
    private Reactor reactor;

    /**
     * 
     * DOC crazyLau Comment method "fireEvent".
     * 
     * @param topic
     * @param user
     */
    public void fireEvent(String topic, UserDTO user) {
        
        
        reactor.notify(topic, Event.wrap(user));
    }

    public void fireEvent(String to, String reply, Object data) {
        reactor.send(to, Event.wrap(data).setReplyTo(reply));
    }
}
