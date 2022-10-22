import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Programs Control Block
 * Stores information about Programs
 */

public class PCB {
    int cpu;
    Stack<Process> NEW = new Stack<>();
    Queue<Process> TERMINATE = new LinkedList<>();
    double processes = 0;
    int time = 0;

    public int processRemaining() {
        return NEW.size()-TERMINATE.size();
    }
    public double averageCoreUtil(){
        double maximumOutput = time*cpu;
        return processes/maximumOutput;
    }
}


