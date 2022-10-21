import java.util.Scanner;

/**
 * Acts as main operating System for this virtual machine
 * Initiates hardware through bootstrapping
 * and
 * Manages software in regard to user input
 */
public class OS {
    static Scheduler scheduler;
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        int running = 1;
        while (running == 1) {
            System.out.print("Enter the number of Cores in CPU\n: ");
            PCB pcb  = new PCB();
            pcb.cpu = scanner.nextInt();
            scheduler = new Scheduler(pcb);
            System.out.print("Enter the number of programs\n: ");
            int programs = scanner.nextInt();
            System.out.print("Enter 1 for Round Robin\nEnter 2 for Shortest Job First\n: ");
            int schedulerType = scanner.nextInt();

            //runs scheduler unless there are no programs remaining
            while (schedulerType == 1 && (scheduler.isActive()||programs>0)) {
                if(programs>0) {
                    scheduler.create();
                    programs--;
                }

                scheduler.roundRobin();

            }
            while ((schedulerType == 2 && (scheduler.isActive()||programs>0))) {

                if(programs>0) {
                    scheduler.create();
                    programs--;
                }

                scheduler.shortestJobFirst();
            }


            /**
            while (!pcb.NEW.isEmpty() || !pcb.TERMINATE.isEmpty()) {
                System.out.println(pcb.NEW.pop().toString());
            }
            while (!pcb.TERMINATE.isEmpty()) {
                System.out.println(pcb.TERMINATE.poll().toString());
            }
             **/

            //Condition for the end of a Simulated run
            if (programs == 0) {
                System.out.printf("Complete.\nAverage CPU uptime was %.1f%%\n", pcb.averageCoreUtil()*100);
                System.out.println("\nTo exit type        0\nTo go again type    1");
                running = scanner.nextInt();
            }
        }
    }
}
