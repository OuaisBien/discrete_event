package project.simulator;

import java.util.PriorityQueue;
import java.util.stream.DoubleStream;
import java.util.function.Supplier;

public class RandomEventProcessor {
    private final Statistics meter = new Statistics();
    protected final PriorityQueue<Event> queue = new PriorityQueue<Event>();
    private final RandomGenerator generator;

    public RandomEventProcessor(int numOfServers, int lengthOfQueue, int numOfCustomers, int seed, double arrivalRate,
            double serviceRate, double restingRate, double restingLikelihood, double greedyCustomerLikelihood) {
        this.generator = new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);
        DoubleStream.generate(() -> generator.genRandomRest()).limit(numOfCustomers)
                .map(x -> (x < restingLikelihood) ? 1 : 0).forEach(x -> {
                    if (x == 0) {
                        ServerResting.setRestLength(0);
                    } else {
                        ServerResting.setRestLength(generator.genRestPeriod());
                    }
                });
        for (int i = 0; i < numOfServers; i++) {
            new Server(i + 1, lengthOfQueue);
        }
        double[] isGreedy = DoubleStream.generate(() -> generator.genCustomerType()).limit(numOfCustomers).toArray();

        double[] arrivalInterval = DoubleStream.generate(() -> generator.genInterArrivalTime()).limit(numOfCustomers)
                .toArray();

        double time = 0;

        for (int i = 0; i < numOfCustomers; i++) {
            if (isGreedy[i] < greedyCustomerLikelihood) {
                new GreedyCustomer(time, DoubleStream.generate(() -> generator.genServiceTime()).limit(1), i + 1);
            } else {
                new Customer(time, DoubleStream.generate(() -> generator.genServiceTime()).limit(1), i + 1);
            }
            time += arrivalInterval[i];
        }
        for (Customer c : Customer.customers) {
            Arrive a = new Arrive(c, c.getCustomerArrivalTime());
            queue.add(a);
        }
    }

    public void startEventProcessing() {
        while (queue.size() > 0) {
            Event currEvent = queue.poll();
            if (currEvent.isServe() && (((Serve) currEvent).getServer().getCurrEvent().isDone()
                    || ((Serve) currEvent).getServer().getCurrEvent().isServerDoneResting()
                    || ((Serve) currEvent).getServer().getCurrEvent().isServerResting())) {
                ((Serve) currEvent).undoStatistics(meter);
                Event nextEvent = new Serve(currEvent.getCustomer(), ((Serve) currEvent).getServer(),
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
