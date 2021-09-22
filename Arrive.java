package cs2030.simulator;

public class Arrive extends Event {
    public Arrive(Customer c, double time) {
        super(c, time);
    }

    @Override
    public void updateStatistics(Statistics st) {
    }

    @Override
    public Event goToNextEvent() {
        Server s;
        if (this.getCustomer().isGreedyCustomer()) {
            s = Server.getServerSlotGreedily();
        } else {
            s = Server.getServerSlot();
        }
        if (s == null) {
            return new Leave(this.getCustomer(), this.getEventStartTime());
        } else if (s.serverIsIdle()) {
            Serve serve = new Serve(this.getCustomer(), s, this.getEventStartTime());
            s.updateCurrentEvent(serve);
            return serve;
        } else if (s.partiallyAvailable()) {
            return new Wait(this.getCustomer(), s, this.getEventStartTime());
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives", this.getEventStartTime(), this.getCustomer().toString());
    }
}
