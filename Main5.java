import java.util.Scanner;
import project.simulator.RandomEventProcessor;

class Main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RandomEventProcessor rep = new RandomEventProcessor(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(),
                sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
        sc.close();
        rep.startEventProcessing();
    }
}