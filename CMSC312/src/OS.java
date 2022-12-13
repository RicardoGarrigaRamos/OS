import java.util.Scanner;

/**
 * Acts as main operating System for this virtual machine
 * Initiates hardware through bootstrapping
 * and
 * Manages software in regard to user input
 */
public class OS {
    static CPU scheduler;
    static PageManager pageManager;
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        int running = 1;
        while (running == 1) {
            PCB pcb  = new PCB();
            scheduler = new CPU(pcb);
            pageManager = new PageManager(pcb);
            scheduler.pageManager = pageManager;
            pageManager.scheduler = scheduler;


            System.out.print("Enter the number of CPUs\n: ");
            pcb.cpu = scanner.nextInt();
            System.out.print("Enter the number of Cores in CPU\n: ");
            pcb.cpuCores = scanner.nextInt();
            System.out.print("Enter the number of programs\n: ");
            int programs = scanner.nextInt();
            pcb.initialProcesses =programs;

            System.out.print("Enter 1 for Round Robin\nEnter 2 for Shortest Job First\n: ");
            int schedulerType = scanner.nextInt();
            pcb.schedulerType = schedulerType;

            System.out.print("Enter 1 for Continuous Memory\nEnter 2 for Noncontagious Memory\n: ");
            int memoryType = scanner.nextInt();
            pcb.memoryType = memoryType;

            CPU[] CPUs = new CPU[pcb.cpu];

            CPUs[0] = scheduler;
            for(int i = 1; i< pcb.cpu; i++){
                CPUs[i] = new CPU(pcb);
                CPUs[i].pageManager = new PageManager(pcb);
                CPUs[i].pageManager.scheduler = CPUs[i];
            }
            for(int i = 0; i< pcb.cpu; i++){
                CPUs[i].start();
            }


            /**
            while (!pcb.NEW.isEmpty() || !pcb.TERMINATE.isEmpty()) {
                System.out.println(pcb.NEW.pop().toString());
            }
            while (!pcb.TERMINATE.isEmpty()) {
                System.out.println(pcb.TERMINATE.poll().toString());
            }
             **/







            boolean end = true;
            //Condition for the end of a Simulated run
            while(end) {
                if (programs == 0 && !isActive(CPUs)) {
                    System.out.printf("Complete.\nAverage CPU uptime was %.1f%%\n", pcb.averageCoreUtil() * 100);
                    System.out.println("\nTo exit type        0\nTo go again type    1");
                    running = scanner.nextInt();
                    end = false;
                }
            }
        }
    }

    public static boolean isActive(CPU[] CPUs) {
        for(int i = 0; i< CPUs.length; i++) {
            if(!CPUs[i].complete) return true;
        }
        return false;
    }
}
