package cs2030.simulator;

import java.util.Map;
import java.util.HashMap;

public class Statistics {
    private final Map<String, Double> counter = new HashMap<>() {{
            put("totalWaitingTime", 0.0);
            put("sumOfServe", 0.0);
            put("sumOfLeave", 0.0);
        }
    };

    public Statistics() {

    }

    public void addWaitingTime(double time) {
        counter.put("totalWaitingTime", counter.get("totalWaitingTime") + time);
    }

    public void oneLessServe() {
        counter.put("sumOfServe", counter.get("sumOfServe") - 1); 
    }

    public void oneMoreServe() {
        counter.put("sumOfServe", counter.get("sumOfServe") + 1);
    }

    public void oneMoreLeave() {
        counter.put("sumOfLeave", counter.get("sumOfLeave") + 1);
    }

    public double getTotalWaitingTime() {
        return counter.get("totalWaitingTime");
    }

    public int getTotalServe() {
        return (counter.get("sumOfServe")).intValue();
    }

    public int getTotalLeave() {
        return (counter.get("sumOfLeave")).intValue();
    }

    public String toString() {
        return String.format("[%.3f %d %d]", (double) 
            this.getTotalServe() == 0 ? 0 : (this.getTotalWaitingTime() / this.getTotalServe()),
            this.getTotalServe(), this.getTotalLeave());
    }
}
