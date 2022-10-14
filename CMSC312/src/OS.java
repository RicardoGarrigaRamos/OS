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

        // Begin interaction with the client
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter the number of programs");
        System.out.print(": ");
        int programs = scanner.nextInt();
        System.out.println("Enter 1 for Round Robin");
        System.out.println("Enter 2 for Priority Queue");
        System.out.print(": ");
        int schedulerType = scanner.nextInt();
        while (true) {
            //scheduler makes number of programs
        }
    }

    public static int diagnostic (CPU cpu) {
        return cpu.getAvailableCores();
    }
}
