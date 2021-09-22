package project.simulator;

import java.util.stream.DoubleStream;

public class GreedyCustomer extends Customer {
    public GreedyCustomer(double time, double sl, int num) {
        super(time, sl, num);
    }

    public GreedyCustomer(double time, DoubleStream ds, int num) {
        super(time, ds, num);
    }
   
    @Override
    public Boolean isGreedyCustomer() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "(greedy)";
    }
}
