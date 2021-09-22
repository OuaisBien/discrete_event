import java.util.Scanner;
import project.simulator.Customer;
import project.simulator.GreedyCustomer;
import project.simulator.Server;
import project.simulator.Event;
import project.simulator.Arrive;
import project.simulator.Wait;
import project.simulator.Serve;
import project.simulator.Done;
import project.simulator.ServerResting;
import project.simulator.ServerDoneResting;
import project.simulator.Leave;
import project.simulator.EventProcessor;
import project.simulator.Statistics;

class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int i = 1;

        while (sc.hasNextDouble()) {
            double arrivalTime = sc.nextDouble();
            new Customer(arrivalTime, 1.0, i);
            ServerResting.setRestLength(0);
            i++;
        }
        sc.close();

        for (i = 0; i < numOfServers; i++) {
            new Server(i + 1, 1);
        }

        EventProcessor ep = new EventProcessor(i - 1);
        ep.startEventProcessing();
    }
}