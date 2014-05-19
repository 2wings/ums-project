package ums.reactor;

import static reactor.event.selector.Selectors.$;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.event.Event;
import reactor.function.Consumer;
import reactor.function.Function;

public class ReactorBasicTest {

    private String result;

    @Test
    public void asyncTest1() {
        Environment env = new Environment();
        Reactor r = Reactors.reactor().env(env).dispatcher("ringBuffer").get();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<String> ref = new AtomicReference<String>();

        r.<Event<String>> on($("test"), new Consumer<Event<String>>() {

            @Override
            public void accept(Event<String> ev) {
                ref.set(ev.getData());
                latch.countDown();
            }
        });

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    latch.await(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = ref.get();
                System.out.println("get Result :" + result);
            }

        });
        t.start();
        r.notify("test", Event.wrap("Jon"));
    }

    @Test
    public void asyncTest2() {
        Environment env = new Environment();
        Reactor r = Reactors.reactor().env(env).dispatcher("ringBuffer").get();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<String> ref = new AtomicReference<String>();

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    latch.await(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = ref.get();
                System.out.println("get Result :" + result);
            }

        });
        t.start();
        r.notify("test", Event.wrap("Jon"), new Consumer<Event<String>>() {

            @Override
            public void accept(Event<String> ev) {
                ref.set(ev.getData());
                latch.countDown();
            }
        });
    }

    @Test
    public void asyncTest3() {
        Environment env = new Environment();
        final Reactor r = Reactors.reactor().env(env).dispatcher("ringBuffer").get();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<String> ref = new AtomicReference<String>();

        final Consumer c = new Consumer<Event<String>>() {

            @Override
            public void accept(Event<String> ev) {
                System.out.println("hi " + ev.getData());

            }
        };
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r.notify("test", Event.wrap("Jon"), c);
            }

        });
        t.start();
    }

    @Test
    public void basicTest() {
        Environment env = new Environment();
        final Reactor r = Reactors.reactor().env(env).dispatcher("ringBuffer").get();

        // Subscribe to topic "test"
        r.<Event<String>> on($("test"), new Consumer<Event<String>>() {

            @Override
            public void accept(Event<String> ev) {
                System.out.println("test hi " + ev.getData());

            }
        });

        // Notify topic "test"
        r.notify("test", Event.wrap("Jon"));

        // Subscribe to topic "test2" and reply with value

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                r.receive($("test2"), new Function<Event<?>, Object>() {

                    @Override
                    public Object apply(Event<?> event) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "Still Jon";
                    }
                });
            }

        });

        t.start();

        // Notify topic "test2" and reply to topic "test"
        r.send("test2", Event.wrap("test2").setReplyTo("test"));

        env.shutdown();
    }
}
