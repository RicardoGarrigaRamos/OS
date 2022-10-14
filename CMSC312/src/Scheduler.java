import java.util.PriorityQueue;

// manages CPU usage at the command of the OS
public class Scheduler {
    CPU cpu;
    PriorityQueue<Program> programs = new PriorityQueue<Program>();
    public Scheduler(CPU cpu) {
        setCpu(cpu);
    }


    /**
     * CALCULATE  –  When  this  line  is  read  in,  the  simulator  will  run  the  process  in
     * the run state for the number of cycles specified as a parameter (i.e., occupy CPU
     * for a given number of cycles, simulating the usage of CPU resource).
     **/
    public static void calculate() {

    }

    /**
     * I/O – This will put the process in the waiting state for a specified number of cycles.
     **/
    public static void io()  {

    }

    /**
     * FORK  –  this  will  create  a  child  process  according  to  a  selected  parent-child
     * management scheme.
     **/
    public static void fork() {

    }



    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }
    public CPU getCpu() {
        return cpu;
    }
}
