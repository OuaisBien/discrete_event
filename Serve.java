package project.simulator;

public class Serve extends Event {
    private final Server server;
    
    public Serve(Customer c, Server s, double time) {
        super(c,time);
        this.server = s;
    }

    @Override
    public Boolean isServe() {
        return true;
    }

    @Override
    public double getEventFinishTime() {
        return super.getEventFinishTime() + this.getCustomer().getServiceLength();
    }

    public Server getServer() {
        return this.server;
    }

    @Override
    public Done goToNextEvent() {
        this.server.updateServiceFinishTime(this.getEventFinishTime());
        Done d = new Done(this.getCustomer(), this.getServer(), this.getEventFinishTime());
        return d;
    }
    
    @Override
    public void updateStatistics(Statistics st) {
        st.oneMoreServe();
        st.addWaitingTime(this.getEventStartTime() - this.getCustomer().getCustomerArrivalTime());
    }

    public void undoStatistics(Statistics st) {
        st.addWaitingTime(this.getCustomer().getCustomerArrivalTime() - this.getEventStartTime());
        st.oneLessServe();
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by server %d", this.getEventStartTime(), 
        this.getCustomer().toString(), this.getServer().getServerNumber(), this.getCustomer()
        .getServiceLength());
    }
}
