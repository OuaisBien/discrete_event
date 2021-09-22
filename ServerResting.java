package cs2030.simulator;

import java.util.ArrayList;

public class ServerResting extends Event {
    private final Server server;
    private final double restLength;
    public static final ArrayList<Double> restLengthList = new ArrayList<>();

    public ServerResting(Server s, double time) {
        super(new Customer(0, 0, 0), time);
        this.server = s;
        this.restLength = restLengthList.remove(0);
        s.updateCurrentEvent(this);
    }

    public static void setRestLength(double time) {
        restLengthList.add(time);
    }

    public Server getServer() {
        return this.server;
    }

    public static double peekRestLengthList() {
        if (restLengthList.get(0) == 0) {
            return restLengthList.remove(0);
        } else {
            return restLengthList.get(0);
        }
    }

    @Override
    public double getThisRestingLength() {
        return this.restLength;
    }
    
    @Override
    public Boolean isServe() {
        return false;
    }

    @Override
    public double getEventFinishTime() {
        return this.getEventStartTime() + this.restLength;
    }

    @Override
    public Boolean isDone() {
        return false;
    }

    @Override
    public Boolean isServerResting() {
        return true;
    }

    @Override
    public void updateStatistics(Statistics st) {
    }

    @Override
    public Event goToNextEvent() {
        return new ServerDoneResting(this.getServer(),
            this.getEventStartTime() + this.restLength);
    }
}
