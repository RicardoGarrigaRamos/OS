import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


// Used to read and store relevant information from template.txt inorder to create processes at O(1)
public class ProcessTemplates {
    int size = 5;
    OP[] ops = new OP[size];
    int[] min = new int[size];
    int[] max = new int[size];
    Random random = new Random();
    int index = 0;


    ProcessTemplates() throws FileNotFoundException {
        File file = new File
                ("src/Template.txt");
        Scanner scanner = new Scanner(file);


        int i = 0;

        while (scanner.hasNext() || i<size) {
            try {
                String text = scanner.next();
                if (text.equals("CALCULATE")) {
                    ops[i] = OP.CALCULATE;
                    min[i] = Integer.parseInt(scanner.next().replaceAll("[^0-9]", ""));
                    max[i] = Integer.parseInt(scanner.next().replaceAll("[^0-9]", ""));
                    i++;
                } else if (text.equals("I/O")) {
                    ops[i] = OP.IO;
                    min[i] = Integer.parseInt(scanner.next().replaceAll("[^0-9]", ""));
                    max[i] = Integer.parseInt(scanner.next().replaceAll("[^0-9]", ""));

                    i++;
                }
            } catch (NumberFormatException e) {}
        }


    }

    public OP chooseOperation() {
        index = random.nextInt(5);
        return ops[index];
    }
    public int chooseLength () {
        return random.nextInt(min[index],max[index]);
    }
}
