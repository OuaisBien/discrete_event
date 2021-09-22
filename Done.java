package cs2030.simulator;

public class Done extends Event {
    private final Server server;
    
    public Done(Customer c, Server s, double time) {
        super(c, time);
        this.server = s;
        s.updateCurrentEvent(this);
    }

    @Override
    public Boolean isDone() {
        return true;
    }

    public Server getServer() {
        return this.server;
    }

    @Override
    public void updateStatistics(Statistics st) {
    }

    @Override
    public Event goToNextEvent() {
        Server s = this.getServer();
        if (ServerResting.peekRestLengthList() == 0) {
            s.autoUpdateCurrentEvent();
            return null;
        } else {
            ServerResting sr;
            if (s.getCurrEvent().isServe()) {
                sr = new ServerResting(s, s.getCurrEvent().getEventStartTime());
            } else {
                sr = new ServerResting(s, s.getServiceFinishTime());
            }
            s.updateCurrentEvent(sr);
            return sr;
        }
    }

    @Override
    public String toString() {
        return String.format("%.3f %s done serving by server %d", this.getEventStartTime(), 
            this.getCustomer().toString(), this.getServer().getServerNumber(),
            this.getServer().getServiceFinishTime());
    }
}