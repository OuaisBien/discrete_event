package project.simulator;

public abstract class Event implements Comparable<Event> {
    protected final Customer customer;
    protected final double time;

    public Event(Customer c, double time) {
        this.customer = c;
        this.time = time;
    }

    public int compareTo(Event e) {
        if (this.getEventStartTime() != e.getEventStartTime()) {
            double diff = this.getEventStartTime() - e.getEventStartTime();
            return (int) (diff / Math.abs(diff));
        } else {
            double diff = this.getCustomer().getCustomerNumber() - e.getCustomer().getCustomerNumber();
            return (int) (diff / Math.abs(diff));
        }
    }

    public Boolean isServe() {
        return false;
    }

    public Boolean isDone() {
        return false;
    }

    public Boolean isWait() {
        return false;
    }

    public Boolean isServerResting() {
        return false;
    }

    public Boolean isServerDoneResting() {
        return false;
    }

    public abstract void updateStatistics(Statistics st);

    public abstract Event goToNextEvent();

    public Customer getCustomer() {
        return this.customer;
    }

    public double getEventStartTime() {
        return this.time;
    }

    public double getEventFinishTime() {
        return this.time;
    }

    public double getThisRestingLength() {
        return 0.0;
    }
}
