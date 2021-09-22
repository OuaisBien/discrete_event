package cs2030.simulator;

import java.util.PriorityQueue;

public class EventProcessor {
    private final Statistics meter = new Statistics();
    protected final PriorityQueue<Event> queue = new PriorityQueue<Event>();

    public EventProcessor(int numOfcustomers) {
        for (Customer c : Customer.customers) {
            Arrive a = new Arrive(c, c.getCustomerArrivalTime());
            queue.add(a);
        }
    }

    public void startEventProcessing() {
        while (queue.size() > 0) {
            Event currEvent = queue.poll();
            if (currEvent.isServe() &&
                (((Serve) currEvent).getServer().getCurrEvent().isDone() ||
                ((Serve) currEvent).getServer().getCurrEvent().isServerDoneResting() ||
                ((Serve) currEvent).getServer().getCurrEvent().isServerResting())) {
                ((Serve) currEvent).undoStatistics(meter);
                Event nextEvent = new Serve(currEvent.getCustomer(),
                    ((Serve) currEvent).getServer(),
                    ((Serve) currEvent).getServer().getServiceFinishTime());
                queue.add(nextEvent);
                nextEvent.updateStatistics(meter);
                continue;
            }
            if (!(currEvent.isServerResting() || currEvent.isServerDoneResting())) {
                System.out.println(currEvent);
            }
            Event nextEvent = currEvent.goToNextEvent();
            if (nextEvent != null) {
                nextEvent.updateStatistics(meter);
                queue.add(nextEvent);
            }
        }
        System.out.println(meter);
    }
}