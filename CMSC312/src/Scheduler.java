import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// manages CPU usage at the command of the OS
public class Scheduler {
    PCB pcb = new PCB();
    Queue<Process> READY = new LinkedList<>();
    PriorityQueue<Process> WAITING = new PriorityQueue<>();
    Process[] RUNNING;

    public Scheduler(PCB pcb) {
        RUNNING = new Process[pcb.cpu];
        for(int i = 0; i<RUNNING.length; i++) {
            RUNNING[i] = null;
        }
    }


    /**
     * CALCULATE  –  When  this  line  is  read  in,  the  simulator  will  run  the  process  in
     * the run state for the number of cycles specified as a parameter (i.e., occupy CPU
     * for a given number of cycles, simulating the usage of CPU resource).
     **/
    public void calculate(int cycles) {
        for(int i = 0; i< cycles; i++) {
            for(int j = 0; j<RUNNING.length; j++) {
                if(RUNNING[j] != null)
                    RUNNING[j].operations[RUNNING[j].pointer].length--;
            }
        }
    }


    /**
     * I/O – This will put the process in the waiting state for a specified number of cycles.
     **/
    public void io(int cycles)  {

    }

    /**
     * FORK  –  this  will  create  a  child  process  according  to  a  selected  parent-child
     * management scheme.
     **/
    public void fork() {

    }


    public void roundRobin() {
        if(READY.peek().operations[READY.peek().pointer].length == 0){
            READY.peek().pointer++;
        }
        if(READY.peek().pointer == READY.peek().operations.length) {
            pcb.TERMINATE.add(READY.poll());
        }

        int i = 0;
        while(!READY.isEmpty() && i< RUNNING.length) {
            READY.peek().state = State.RUNING;
            RUNNING[i] = READY.poll();
            i++;
        }
        calculate(5);
        for(i=0; i< RUNNING.length; i++) {
            RUNNING[i].state = State.READY;
            READY.add(RUNNING[i]);
            RUNNING[i] = null;
        }


    }

    public void shortestJobFirst() {

        calculate();
    }






    public boolean isActive() {
        if(READY.isEmpty() || WAITING.isEmpty()) return false;
        else return true;
    }
    public void create (){
        pcb.NEW.push(new Process());
        READY.add(pcb.NEW.peek());
    }
    public void calculate() {
        calculate(1);
    }
    public void io() {
        io(1);
    }
}
