import java.io.FileNotFoundException;

// the potential States for a Program or Process
enum State {
    NEW, READY, RUN, WAIT, EXIT;
}

// defined as an array of Processes at a given State in its lifecycle
public class Program {
    State state;
    Process[] processes;
    int currentProcesses = 0;
    ProcessTemplates template;
    {
        try {
            template = new ProcessTemplates();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Program (int numProcesses) {
        //Scheduler will manage the rate at which the processes are created given a number of programs
        setState(State.NEW);

        processes = new Process[numProcesses];
        for (int i = 0; i< processes.length; i++) {
            processes[i] = new Process(template.makeOp(), template.makeCycles());
            currentProcesses++;
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
    public int getCurrentProcesses() {
        return currentProcesses;
    }

    public String toString() {
        String table = "";
        for (int i = 0; i< processes.length; i++) {
            table.concat(processes[i].toString()+"\n");
        }
        return table;
    }
}
