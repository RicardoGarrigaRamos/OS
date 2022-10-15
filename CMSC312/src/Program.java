import java.io.FileNotFoundException;
import java.util.Random;

// the potential States for a Program or Process
enum State {
    NEW, READY, RUN, WAIT, EXIT;
}

// defined as an array of Processes at a given State in its lifecycle
public class Program {
    State state;
    Process[] processes;
    int currentProcess = 0;
    ProcessTemplates template;
    {
        try {
            template = new ProcessTemplates();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //creates a program with a random number of random process
    public Program () {

        setState(State.NEW);
        Random random = new Random();
        processes = new Process[random.nextInt(3,8)];
        for (int i = 0; i< processes.length; i++) {
            processes[i] = new Process(template.makeOp(), template.makeCycles());
        }

        //Will only begin prepossessing given that all processes are created
        setState(State.READY);
    }

    public void setState(State state) {
        this.state = state;
    }
    public void setProcesses(Process[] processes) {
        this.processes = processes;
    }

    public State getState() {
        return state;
    }
    public Process[] getProcesses() {
        return processes;
    }

    public String toString() {
        String table = "";
        for (int i = 0; i< processes.length; i++) {
            table += processes[i].toString()+"\n";
        }
        return table;
    }
}
