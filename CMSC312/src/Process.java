import java.io.FileNotFoundException;
import java.util.Random;

// the potential States for a Program or Process
enum State {
    NEW, READY, RUNING, WAITING, TERMINATE;
}

// defined as an array of Processes at a given State in its lifecycle
public class Process {
    int pressesID;
    int pointer = 0;
    int priority = 1;
    State state;
    Operation[] operations;

    ProcessTemplates template;
    {
        try {
            template = new ProcessTemplates();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //creates a program with a random number of random process
    public Process () {
        this.state = State.NEW;

        Random random = new Random();
        operations = new Operation[random.nextInt(5,8)];
        for (int i = 0; i< operations.length; i++) {
            operations[i] = new Operation(template.chooseOperation(), template.chooseLength());
        }
        operations[random.nextInt(operations.length)].operation = OP.CRITICAL;
    }
    public Process (State state, Operation[] operations) {
        this.state = state;
        this.operations = operations;
    }


    public String toString() {
        String table = "";
        for (int i = 0; i< operations.length; i++) {
            table += operations[i].toString()+"\n";
        }
        return table;
    }
}
