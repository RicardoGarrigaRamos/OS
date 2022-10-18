// types of operations given to a certain Process
enum OP {
    CALCULATE, IO, CRITICAL;
}

// defined as a type of Operation and a decrementing number of cycles
public class Operation {
    OP operation;
    int length;
    int currentOperation = 0;

    public Operation(OP operation, int length) {
        this.operation = operation;
        this.length = length;
    }

    public String toString() {
        if (operation.equals(OP.IO)) {
            return operation.toString() + "\t\t\t\t" + length;
        }else {
            return operation.toString() + "\t\t" + length;
        }
    }
}
