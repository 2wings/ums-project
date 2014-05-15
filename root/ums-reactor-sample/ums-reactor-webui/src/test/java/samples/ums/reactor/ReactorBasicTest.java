package samples.ums.reactor;

import static reactor.event.selector.Selectors.*;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.event.Event;
import reactor.function.Consumer;
import reactor.function.Function;

public class ReactorBasicTest {

    public static void main(String... args) {
        Environment env = new Environment();
        Reactor r = Reactors.reactor().env(env).dispatcher("ringBuffer").get();

        // Subscribe to topic "test"
        r.<Event<String>> on($("test"), new Consumer<Event<String>>() {

            @Override
            public void accept(Event<String> ev) {
                System.out.println("hi " + ev.getData());

            }
        });

        Consumer c = new Consumer<Event<String>>() {

            @Override
            public void accept(Event<String> ev) {
                System.out.println("hi " + ev.getData());

            }
        };
        
        r.consumer();

        // Notify topic "test"
        r.notify("test", Event.wrap("Jon"));

        // Subscribe to topic "test2" and reply with value
        r.receive($("test2"), new Function<Event<?>, Object>() {

            @Override
            public Object apply(Event<?> event) {
                return "Jon";
            }
        });

        // Notify topic "test2" and reply to topic "test"
        r.send("test2", Event.wrap("test2").setReplyTo("test"));

        env.shutdown();
    }
}
