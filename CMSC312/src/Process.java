// types of operations given to a certain Process
enum Operation {
    CALCULATE, IO, CRITICAL;
}

// defined as a type of Operation and a decrementing number of cycles
public class Process {
    Operation op;
    int cycles;

    public Process (Operation op, int cycles) {
        setOp(op);
        setCycles(cycles);
    }

    private void setCycles(int cycles) {
        this.cycles = cycles;
    }
    private void setOp(Operation op) {
        this.op = op;
    }

    public void cycle() {
        cycles--;
    }

    public int getCycles() {
        return cycles;
    }
    public Operation getOp() {
        return op;
    }

    public String toString() {
        if (op.equals(Operation.IO)) {
            return op.toString() + "\t\t\t\t" + cycles;
        }else {
            return op.toString() + "\t\t" + cycles;
        }
    }
}
