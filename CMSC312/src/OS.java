import java.util.Scanner;

/**
 * Acts as main operating System for this virtual machine
 * Initiates hardware through bootstrapping
 * and
 * Manages software in regard to user input
 */
public class OS {
    public static void main(String[] args) {
        // initializing hardware and software of the OS
        CPU cpu = new CPU();
        Scheduler scheduler = new Scheduler(cpu);
        boolean interrupt = false;

        // Begin interaction with the client
        Scanner scanner= new Scanner(System.in);
        int running = 1;
        while (running == 1) {
            System.out.print("Enter the number of programs\n: ");
            int programs = scanner.nextInt();
            System.out.print("Enter 1 for Round Robin\nEnter 2 for Priority Queue\n: ");
            int schedulerType = scanner.nextInt();
            System.out.println("To change initial conditions type 0\nSimulating...");


            //runs scheduler unless there are no programs remaining or there is an interrupt
            /**
             * Figure out how to do interrupts without pausing cpu activity
             **/
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

                scheduler.priorityQueue();
            }



            //Condition for the end of a Simulated run
            if (programs == 0) {
                System.out.println("Complete.\n\nTo exit type        0\nTo go again type    1");
                running = scanner.nextInt();
            }
        }
    }




    public static int diagnostic (CPU cpu) {
        return cpu.getAvailableCores();
    }

}
