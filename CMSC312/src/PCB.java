import java.util.LinkedList;
import java.util.Queue;

/**
 * Programs Control Block
 * Stores information about Programs
 */

public class PCB {
    int cpu;
    int cpuCores;
    int mainMemory = 0;
    int virtualMemory = 0;
    int totalMM = 512;
    int totalVM = 512;

    int numPinMM = 0;
    int numPinVM = 0;
    int numPinHDD = 0;
    int numPrograms = 0;
    Queue<Process> NEW = new LinkedList<>();
    Queue<Process> TERMINATE = new LinkedList<>();
    double processes = 0;
    int time = 0;
    int initialProcesses;
    int schedulerType;


    public int processRemaining() {
        return numPrograms-TERMINATE.size();
    }
    public double averageCoreUtil(){
        double maximumOutput = time*cpuCores;
        return processes/maximumOutput;
    }

    public boolean hasMM(Process process) {
        if(mainMemory+process.memory <= totalMM) return true;
        return false;
    }

    public boolean hasVM(Process process) {
        if(virtualMemory+process.memory <= totalVM) return true;
        return false;
    }
}


