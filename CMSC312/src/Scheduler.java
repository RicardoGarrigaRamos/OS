import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// manages CPU usage at the command of the OS
public class Scheduler {
    CPU cpu;
    Queue<Program> programs = new LinkedList<>();
    Queue<Program> waiting = new LinkedList<>();

    public Scheduler(CPU cpu) {
        setCpu(cpu);
    }


    /**
     * CALCULATE  –  When  this  line  is  read  in,  the  simulator  will  run  the  process  in
     * the run state for the number of cycles specified as a parameter (i.e., occupy CPU
     * for a given number of cycles, simulating the usage of CPU resource).
     **/
    public void calculate(int cycles) {
        programs.peek().setState(State.RUN);
        //checks if current program has calculations remaining and has time remaining from the scheduler
        int i = 0;
        while ((programs.peek().getProcesses()[programs.peek().currentProcess].getCycles() > 0) && (i<cycles)) {
            programs.peek().getProcesses()[programs.peek().currentProcess].cycle();
            i++;
        }
        //to see if scheduler is working as intended
        //System.out.println(programs.peek().toString());
    }

    /**
     * I/O – This will put the process in the waiting state for a specified number of cycles.
     **/
    public void io(int cycles)  {
        programs.peek().setState(State.WAIT);
        waiting.add(programs.poll());
        for(int i = 0; i< cycles; i++) {
            //wait?
        }
        programs.add(waiting.poll());
    }

    /**
     * FORK  –  this  will  create  a  child  process  according  to  a  selected  parent-child
     * management scheme.
     **/
    public void fork() {

    }

    public void exit() {
        programs.peek().setState(State.EXIT);
        //Create program from OS to be scheduled that removes the given program from memory
        programs.poll();
    }


    public boolean isActive() {
        if(programs.isEmpty()) return false;
        else return true;
    }

    public void create (){
        programs.add(new Program());
        //to check if programs are generated correctly
        //System.out.println(programs.peek().toString());
    }

    public void roundRobin() {
        if (programs.peek().getProcesses()[programs.peek().currentProcess].getCycles() == 0) {
            programs.peek().currentProcess++;
        }
        if (programs.peek().currentProcess == programs.peek().processes.length) {
            exit();
            return;
        }
        calculate(5);
        programs.add(programs.poll());
    }

    /**
     * work in progress
     **/
    public void priorityQueue() {
        //for another day...
        calculate(programs.peek().getProcesses()[programs.peek().currentProcess].getCycles());
        //programs.
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }
    public CPU getCpu() {
        return cpu;
    }
}
