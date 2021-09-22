package project.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class Server {
    private final int serverNumber;
    private final double lengthOfQueue;
    protected final Map<String, Event> currEventMap = new HashMap<>() {{
            put("currEvent", null); 
        }
    };
    private final ArrayList<Event> pendingEvents = new ArrayList<Event>();
    private final Map<String, Double> time = new HashMap<>() {{ 
            put("serviceFinishTime", 0.0);
        }
    };
    protected static final ArrayList<Server> servers = new ArrayList<Server>();

    public Server(int num, int len) {
        this.serverNumber = num;
        this.lengthOfQueue = len;
        servers.add(this);
    }

    public Event getCurrEvent() {
        return this.currEventMap.get("currEvent");
    }

    public ArrayList<Event> getWaitingEvent() {
        return this.pendingEvents;
    }

    public Event getOneEventFromPendingQueue() {
        if (pendingEvents.size() > 0) {
            return pendingEvents.get(0);
        }
        return null;
    }

    public Boolean partiallyAvailable() {
        return (this.getCurrEvent() != null) && (this.pendingEvents.size() < this.lengthOfQueue);
    }

    public Boolean serverIsIdle() {
        return (this.getOneEventFromPendingQueue() == null) && (this.getCurrEvent() == null);
    }

    public void updateServiceFinishTime(double time) {
        this.time.put("serviceFinishTime", time);
    }

    public void updateCurrentEvent(Event e) {
        this.currEventMap.put("currEvent", e);
        if (!e.isServe()) {
            this.updateServiceFinishTime(e.getEventFinishTime());
        }
    }

    public void autoUpdateCurrentEvent() {
        if (this.getCurrEvent() != null) {
            this.currEventMap.put("currEvent", null);
        }
        if (this.getOneEventFromPendingQueue() != null) {
            this.updateCurrentEvent(this.getOneEventFromPendingQueue());
            this.pendingEvents.remove(0);
            if (this.getCurrEvent().getCustomer().serviceLengthPredetermined()) {
                this.updateServiceFinishTime(this.getCurrEvent().getEventFinishTime());
            }
        }
        

    }

    public void addWaitingEvent(Event e) {
        this.pendingEvents.add(e);
    }

    public void updateWaitingEvent(Event e) {
        if (this.pendingEvents.size() == 0) {
            this.pendingEvents.add(e);
        } else {
            for (int i = 0; i < this.pendingEvents.size(); i++) {
                if (this.pendingEvents.get(i).getCustomer().getCustomerNumber() ==
                    e.getCustomer().getCustomerNumber()) {
                    this.pendingEvents.set(i, e);
                    break;
                }
            }
        }
    }

    public double getServiceFinishTime() {
        return this.time.get("serviceFinishTime");
    }

    public int getServerNumber() {
        return this.serverNumber;
    }

    public static Server getServerSlot() {
        for (Server s : servers) {
            if (s.serverIsIdle()) {
                return s;
            }
        }
        for (Server s : servers) {
            if (s.partiallyAvailable()) {
                return s;
            }
        }
        return null;
    }

    public static Server getServerSlotGreedily() {
        for (Server s : servers) {
            if (s.serverIsIdle()) {
                return s;
            }
        }
        ArrayList<Integer> map = new ArrayList<Integer>();
        servers.forEach(x -> map.add(x.pendingEvents.size()));
        int minimumWaiting = Collections.min(map);
        int theServer = map.indexOf(minimumWaiting);
        if (minimumWaiting == servers.get(theServer).lengthOfQueue) {
            return null;
        } else {
            return servers.get(theServer);
        }
    }
}
