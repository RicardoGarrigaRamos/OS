import java.util.LinkedList;
import java.util.Queue;

public class LifeCycle {


    public static void main(String[] args) {
        // try priority queue
        Queue<Process> memory = new LinkedList<Process>();

        //NEW  –  The  program  or  process  is  being  created  or  loaded  (but  not  yet  in memory)
        memory.add(new Process());
        //memory.peek().printInfo();

        //READY  –  The  program  is  loaded  into  memory  and  is  waiting  to  run  on  the CPU.
        ((Process)memory.toArray()[memory.size()-1]).setState(State.READY);
        //memory.peek().printInfo();

        //RUN – Instructions are being executed (or simulated).
        memory.peek().setState(State.RUN);
        if (true) memory.peek().setState(State.READY);
        else memory.peek().setState(State.WAIT);
        //memory.peek().printInfo();

        //WAIT  –  The  program  is  waiting  for  some  event  to  occur  (such  as  an  I/O completion).
        memory.peek().setState(State.WAIT);
        memory.add(memory.poll());
        //memory.peek().printInfo();

        //EXIT – The program has finished execution on the CPU (all instructions and I/O complete), releases resources and leaves memory.
        memory.peek().setState(State.EXIT);

        //memory.peek().printInfo();

        memory.poll();


        /**
         * times to run scheduler
         * running to waiting
         * running to ready
         * waiting to ready
         * exits
         *
         *
         * Round robbin
         *
         * scheduler and dispatcher
         *
         * Shortest job first relaxed version
         *
         * priority scheduling
         * */
    }






}
