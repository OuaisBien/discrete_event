package cs2030.simulator;

public class Leave extends Event {
    public Leave(Customer c, double time) {
        super(c, time);
    }

    @Override
    public Boolean isServe() {
        return false;
    }

    @Override
    public Boolean isDone() {
        return false;
    }

    @Override
    public Boolean isServerResting() {
        return false;
    }

    @Override
    public void updateStatistics(Statistics st) {
        st.oneMoreLeave();
    }

    @Override
    public Event goToNextEvent() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves", this.getEventStartTime(), 
        this.getCustomer().toString());
    }
}
