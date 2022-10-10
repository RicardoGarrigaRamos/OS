import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Process {
    State state = null;
    Operation[] operationLst = new Operation[5];
    
    //Constructor based on 5 operation template
    public Process () {
        this.state = State.NEW;
        try {
            this.operationLst = newOpLst();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static Operation[] newOpLst () throws FileNotFoundException {
        File file = new File
                ("C:\\Users\\ricar\\OneDrive\\Desktop\\School\\Fall2022" +
                        "\\Intro to OS\\Programming\\Project 1\\ProcessTemplate.txt");
        Scanner scanner = new Scanner(file);
        Random random = new Random();

        Operation[] process = new Operation[5];
        int i = 0;

        while (scanner.hasNext()) {
            try {
                String text = scanner.next();
                if (text.equals("CALCULATE")) {
                    process[i] = (new Operation(OperationType.CALCULATE,
                            random.nextInt(Integer.parseInt(scanner.next().replaceAll("[^0-9]", "")),
                                    Integer.parseInt(scanner.next().replaceAll("[^0-9]", "")))));
                    i++;
                } else if (text.equals("I/O")) {
                    process[i] = (new Operation(OperationType.IO,
                            random.nextInt(Integer.parseInt(scanner.next().replaceAll("[^0-9]", "")),
                                    Integer.parseInt(scanner.next().replaceAll("[^0-9]", "")))));
                    i++;
                }
            } catch (NumberFormatException e) {}
        }

        return process;
    }

    //utility functions
    public void setState(State state) {
        this.state = state;
    }
    public void setOperationLst(Operation[] operationLst) {
        this.operationLst = operationLst;
    }
    public State getState() {
        return state;
    }
    public Operation[] getOperationLst() {
        return operationLst;
    }
    public void printInfo () {
        System.out.println(state.toString());

        for (int i = 0; i<operationLst.length; i++) {
            operationLst[i].printInfo();
        }
    }
}
