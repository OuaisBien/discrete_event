package cs2030.simulator;

public class Wait extends Event {
    private final Server server;

    public Wait(Customer c, Server s, double time) {
        super(c, time);
        this.server = s;
        s.addWaitingEvent(this);
    }

    public Server getServer() {
        return this.server;
    }

    @Override
    public Boolean isWait() {
        return true;
    }

    @Override
    public void updateStatistics(Statistics st) {
    }

    @Override
    public Event goToNextEvent() {
        double time = this.getServer().getServiceFinishTime();
        Serve s = new Serve(this.getCustomer(), this.getServer(), time);
        this.getServer().updateWaitingEvent(s);
        return s;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at server %d", this.getEventStartTime(), 
        this.getCustomer().toString(), this.getServer().getServerNumber());
    }
}
