import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// manages CPU usage at the command of the OS
public class Scheduler {
    PCB pcb;
    Queue<Process> READY = new LinkedList<>();
    PriorityQueue<Process> WAITING = new PriorityQueue<>();
    Process[] RUNNING;

    public Scheduler(PCB pcb) {
        this.pcb = pcb;
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
            pcb.time++;
            for(int j = 0; j<RUNNING.length; j++) {
                if((RUNNING[j] != null) && (RUNNING[j].operations[RUNNING[j].pointer].length > 0)) {
                    if((RUNNING[j].operations[RUNNING[j].pointer].length <= 0)) i=cycles;
                    RUNNING[j].operations[RUNNING[j].pointer].length--;
                    pcb.processes++;
                }
            }
        }
    }


    /**
     * I/O – This will put the process in the waiting state for a specified number of cycles.
     **/
    public void io(int cycles)  {
        for(int i = 0; i<cycles; i++) {
            WAITING.add(READY.poll());
        }
    }

    /**
     * FORK  –  this  will  create  a  child  process  according  to  a  selected  parent-child
     * management scheme.
     **/
    public void fork() {
        int i = 0;
        while (!runningAtMax() && runningDividable() && isActive()) {
            if (i < RUNNING.length-1) {
                // final element in operations queue
                while (RUNNING[i] != null && RUNNING[i].pointer < RUNNING[i].operations.length-1) i++;

                //in case of critical section
                if (RUNNING[i] != null && RUNNING[i].operations[RUNNING[i].pointer + 1] != null && RUNNING[i].operations[RUNNING[i].pointer + 1].operation.equals(OP.CRITICAL))
                    return;

                //Creating parent and child
                if (RUNNING[i] != null) {
                    Operation[] childOPs = new Operation[1];
                    Operation[] parentOPs = new Operation[RUNNING[i].operations.length - 1];
                    childOPs[0] = RUNNING[i].operations[RUNNING[i].pointer + 1];
                    parentOPs[0] = RUNNING[i].operations[RUNNING[i].pointer];
                    for (int j = 1; j < parentOPs.length; j++) {
                        if (RUNNING[i].operations[j + 1] != null) {
                            parentOPs[j] = RUNNING[i].operations[j + 1];
                        }
                    }
                    Process parent = new Process(State.RUNNING, parentOPs, 1);
                    Process child = new Process(State.RUNNING, childOPs, 2);
                    pcb.NEW.add(child);
                    RUNNING[i] = parent;
                    for (int j = 0; j < RUNNING.length; j++) {
                        if (RUNNING[j] == null) RUNNING[j] = child;
                    }
                }


                if(i<RUNNING.length)i++;
            } i = 0;
        }
    }


    public void roundRobin() {
        int i = 0;
        //filling cpu with programs
        while(!READY.isEmpty() && i< RUNNING.length) {
            // handling critical sections
            if(READY.peek().operations.length>READY.peek().pointer
                    && READY.peek().operations[READY.peek().pointer] != null
                    && READY.peek().operations[READY.peek().pointer].operation.equals(OP.CRITICAL))
            {
                executeCritical();
                i=0;
            }
            if(!READY.isEmpty())
            {
                READY.peek().state = State.RUNNING;
                RUNNING[i] = READY.poll();
                i++;
            }
        }
        // filling cpu for optimal multithreading
        if(!runningAtMax() && runningDividable()) {
            fork();
        }

        //letting the cpu run
        if(RUNNING[0] != null) calculate(5);

        //documentation
        double cores = 0;
        for(i=0; i< RUNNING.length; i++) {
            if(RUNNING[i] != null)
            {
                System.out.println(RUNNING[i].toString());
                cores++;
            }
        }
        System.out.printf("CPU Utilized %.1f%% of cores this time slice\n\n", cores/RUNNING.length*100);

        //adjusts metadata of possess
        progress();

        //refactoring the ready queue
        clearRunning();
    }

    public void shortestJobFirst() {
        while(!READY.isEmpty()) {
            io(READY.size());
        }

        int i = 0;
        //filling cpu with programs
        while(!WAITING.isEmpty() && i< RUNNING.length) {
            // handling critical sections
            if(WAITING.peek().operations[WAITING.peek().pointer].operation.equals(OP.CRITICAL))
            {
                READY.add(WAITING.poll());
                executeCritical();
                i=0;
            }


            while (!READY.isEmpty()) {
                WAITING.add(READY.poll());
            }
            if(!WAITING.isEmpty())
            {
                WAITING.peek().state = State.RUNNING;
                RUNNING[i] = WAITING.poll();
                i++;
            }
        }
        // filling cpu for optimal multithreading
        if(!runningAtMax() && runningDividable()) {
            fork();
        }

        //letting the cpu run
        if(RUNNING[0] != null) calculate();


        //Documentation
        double cores = 0;
        for(i=0; i< RUNNING.length; i++) {
            if(RUNNING[i] != null)
            {
                System.out.println(RUNNING[i].toString());
                cores++;
            }
        }
        System.out.printf("CPU Utilized %.1f%% of cores for %.0f cycle\n\n", cores/RUNNING.length*100, 1.0);

        //adjusts metadata of possess
        progress();

        // switching
        clearRunning();
    }






    public boolean isActive() {
        boolean runningIsEmpty = true;
        for(int i = 0; i< RUNNING.length; i++) {
            if(RUNNING[i] != null) runningIsEmpty = false;
        }

        if(READY.isEmpty() && WAITING.isEmpty() && runningIsEmpty) return false;
        else return true;
    }
    public void create(){
        pcb.NEW.push(new Process());
        READY.add(pcb.NEW.peek());
    }
    public void calculate() {
        calculate(1);
    }
    public void io() {
        io(1);
    }

    public void clearRunning() {
        for(int i=0; i< RUNNING.length; i++) {
            if(RUNNING[i] != null) {
                RUNNING[i].state = State.READY;
                READY.add(RUNNING[i]);
                RUNNING[i] = null;
            }
        }
    }

    public void executeCritical() {
        clearRunning();
        READY.peek().state = State.RUNNING;
        RUNNING[0] = READY.poll();
        int cycles = RUNNING[0].operations[RUNNING[0].pointer].length;
        calculate(cycles);

        //documentation
        System.out.println(RUNNING[0].toString());
        System.out.printf("CPU Utilized %.1f%% of cores for " + cycles + " Cycles\n\n", ((1)/(double)RUNNING.length)*100 );

        progress();
        if(RUNNING[0] != null)
        {
            RUNNING[0].state = State.READY;
            READY.add(RUNNING[0]);
        }
    }
    public void progress() {
        for (int i = 0; i<RUNNING.length; i++) {
            if(RUNNING[i] != null && RUNNING[i].operations.length > RUNNING[i].pointer) {
                if (RUNNING[i].operations[RUNNING[i].pointer].length == 0) {
                    RUNNING[i].pointer++;
                    if (RUNNING[i].pointer == RUNNING[i].operations.length) {
                        pcb.TERMINATE.add(RUNNING[i]);
                        RUNNING[i] = null;
                        System.out.println("There are "+pcb.processRemaining()+ " processes remaining");
                    }
                }
            }else if (RUNNING[i] != null && RUNNING[i].pointer == RUNNING[i].operations.length) {
                pcb.TERMINATE.add(RUNNING[i]);
                RUNNING[i] = null;
                System.out.println("There are "+pcb.processRemaining()+ " processes remaining");
            }
        }
    }

    public boolean runningAtMax() {
        for (int i = 0; i< RUNNING.length; i++) {
            if(RUNNING[i] == null) return false;
        }
        return true;
    }

    public boolean runningDividable() {
        for (int i = 0; i< RUNNING.length; i++){
            if(RUNNING[i] != null
                    && RUNNING[i].operations.length > RUNNING[i].pointer) return false;
        }
        return true;
    }
}
