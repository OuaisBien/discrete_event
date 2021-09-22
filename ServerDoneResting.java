package cs2030.simulator;

public class ServerDoneResting extends Event {
    private final Server server;

    public ServerDoneResting(Server s, double time) {
        super(new Customer(0, 0, 0), time);
        this.server = s;
        s.updateCurrentEvent(this);
    }

    public Server getServer() {
        return this.server;
    }

    @Override
    public Boolean isServerDoneResting() {
        return true;
    }

    @Override
    public void updateStatistics(Statistics st) {
    }

    @Override
    public Event goToNextEvent() {
        this.getServer().autoUpdateCurrentEvent();
        return null;
    }
}

