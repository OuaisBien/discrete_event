package project.simulator;

import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.Map;
import java.util.HashMap;

public class Customer {
    private final double arrivalTime;
    protected final Map<String, Double> serviceLengthMap = new HashMap<>();
    private final DoubleStream serviceLengthStream;
    private final int customerNumber;
    private final Boolean predeterminServiceLength;
    protected static final ArrayList<Customer> customers = new ArrayList<Customer>();
    
    public Customer(double time, double sl, int num) {
        this.arrivalTime = time;
        this.serviceLengthMap.put("serviceLength", sl);
        this.serviceLengthStream = null;
        this.customerNumber = num;
        this.predeterminServiceLength = true;
        customers.add(this);
    }

    public Customer(double time, DoubleStream ds, int num) {
        this.arrivalTime = time;
        this.serviceLengthMap.put("serviceLength", -1.0);
        this.serviceLengthStream = ds;
        this.customerNumber = num;
        this.predeterminServiceLength = false;
        customers.add(this);
    }

    public Boolean serviceLengthPredetermined() {
        return this.predeterminServiceLength;
    }

    public Boolean isGreedyCustomer() {
        return false;
    }

    public double getCustomerArrivalTime() {
        return this.arrivalTime;
    }

    public double getServiceLength() {
        if (this.serviceLengthMap.get("serviceLength") < 0) {
            this.serviceLengthMap.put("serviceLength", this.serviceLengthStream.sum());
        }
        return this.serviceLengthMap.get("serviceLength");
    }
    
    public int getCustomerNumber() {
        return this.customerNumber;
    }

    public static int getNumberOfCustomers() {
        return customers.size();
    }

    @Override
    public String toString() {
        return String.format("%d", this.getCustomerNumber());
    }
}
