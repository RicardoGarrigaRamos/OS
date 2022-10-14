/**
 * the virtual CPU
 * capable of running all processes at the command of the scheduler
 */
public class CPU {
    int maxCores;
    int availableCores;

    public CPU() {
        setMaxCores(1);
        setAvailableCores(1);
    }
    public CPU (int cores) {
        setMaxCores(cores);
        setAvailableCores(cores);
    }

    public void setAvailableCores(int currentCores) {
        this.availableCores = currentCores;
    }
    public void setMaxCores(int maxCores) {
        this.maxCores = maxCores;
    }

    public int getAvailableCores() {
        return availableCores;
    }
    public int getMaxCores() {
        return maxCores;
    }
}
