public class Operation {
    OperationType op;
    int cycles;

    public Operation(OperationType op, int cycles){
        setOp(op);
        setCycles(cycles);
    }

    public void setOp(OperationType op) {
        this.op = op;
    }
    public void setCycles(int cycles) {
        this.cycles = cycles;
    }
    public OperationType getOp() {
        return op;
    }
    public int getCycles() {
        return cycles;
    }
    public void printInfo() {
        System.out.println(getOp().toString() + " " +getCycles() + " Cycles");
    }
}
