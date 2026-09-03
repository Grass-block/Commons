import me.gb2022.commons.event.CancellableEvent;
import me.gb2022.commons.event.EventHandler;
import me.gb2022.commons.event.SimpleEventBus;
import me.gb2022.commons.event.SubscribedEvent;

public class Test {
    public static void main(String[] args) {
        var eb=new SimpleEventBus();

        eb.registerEventListener(new Test());

        System.out.println("--no--");
        eb.callEvent(new TestEvent());

        System.out.println("--s1--");
        eb.callEvent(new TestEvent(),"s1");

        System.out.println("--s2--");
        eb.callEvent(new TestEvent(),"s2");
    }

    @EventHandler(priority = 114)
    public void m1(TestEvent e){
        e.cancel();
        System.out.println("m1");
    }

    @EventHandler
    public void m2(TestEvent e){
        System.out.println("m2");
    }

    @EventHandler
    @SubscribedEvent("s1")
    public void m1s1(TestEvent e){
        System.out.println("m1s1");
    }

    @EventHandler
    @SubscribedEvent("s1")
    public void m2s1(TestEvent e){
        System.out.println("m2s1");
    }

    @EventHandler
    @SubscribedEvent("s2")
    public void m1s2(TestEvent e){
        System.out.println("m1s2");
    }

    @EventHandler
    @SubscribedEvent("s2")
    public void m2s2(TestEvent e){
        System.out.println("m2s2");
    }


    public static class TestEvent extends CancellableEvent {

    }
}
