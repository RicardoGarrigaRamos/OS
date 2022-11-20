import java.io.FileNotFoundException;
import java.util.Random;

// the potential States for a Program or Process
enum State {
    NEW, READY, RUNNING, WAITING, TERMINATE;
}
enum Location {
    MAIN, VIRTUAL, HDD;
}

// defined as an array of Processes at a given State in its lifecycle
public class Process implements Comparable<Process>{
    int pressesID = 0;
    int pointer = 0;
    State state;
    Operation[] operations;
    int memory;
    Location location;


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
        int size = random.nextInt(5,8);
        operations = new Operation[size];
        for (int i = 0; i< operations.length; i++) {
            operations[i] = new Operation(template.chooseOperation(), template.chooseLength());
        }
        operations[random.nextInt(operations.length)].operation = OP.CRITICAL;

        memory = template.chooseMemory();
    }
    public Process (State state, Operation[] operations, int pressesID, int memory) {
        this.state = state;
        this.operations = operations;
        this.pressesID = pressesID;
        this.memory = memory;
    }


    public String toString() {
        String table = "";
        for (int i = 0; i< operations.length; i++) {
            table += operations[i].toString()+"\n";
        }
        return table;
    }

    @Override
    public int compareTo(Process o) {
        if (sumOfOpLength() < o.sumOfOpLength()) return -1;
        else if (sumOfOpLength() > o.sumOfOpLength()) return 1;
        return 0;
    }
    public int sumOfOpLength () {
        int sum = 0;
        for(int i = 0; i< operations.length; i++) {
            sum += operations[i].length;
        }
        return sum;
    }
}
