import java.util.LinkedList;

public class PageManager {
    LinkedList<Integer>[] pageTable = new LinkedList[16];
    int lastIn = 0;
    int maxPageSize = 64;
    Scheduler scheduler;
    PCB pcb;

    public PageManager(PCB pcb) {
        this.pcb = pcb;
    }

    public void createPages() {
        Process process = new Process();
        pcb.NEW.add(process);
        pcb.numPrograms++;

        checkNewForReady();
    }

    public void victimSelection(Process process) {
        if(pageTableFull()) {

            lastInFirstOut(process);

        }else{
            for(int i = 0; i< pageTable.length; i++) {
                if(pageTable[i] == null || pageTable[i].isEmpty()){
                    //virtual to main memory
                    if(i>8){
                        LinkedList<Integer> temp = pageTable[i-8];
                        pageTable[i-8] = toLL(process);
                        process.head = i-8;
                        lastIn = i-8;

                        pageTable[i] = temp;
                    }else {
                        pageTable[i] = toLL(process);
                        process.head = i;
                        lastIn = i;
                    }

                }
            }
        }
    }

    public void terminate(int i) {
        if(scheduler.RUNNING[i].head != -1) {
            pageTable[scheduler.RUNNING[i].head].clear();
        }
        pcb.TERMINATE.add(scheduler.RUNNING[i]);
        scheduler.RUNNING[i].state = State.TERMINATE;

        checkNewForReady();

        scheduler.RUNNING[i] = null;
    }

    public void checkNewForReady() {
        while (!pcb.NEW.isEmpty() && !pageTableFull()) {
            Process process = pcb.NEW.peek();

            for (int i = 0; i < pageTable.length; i++) {
                if (pageTable[i]==null ||pageTable[i].isEmpty()) {
                    pageTable[i] = toLL(process);
                    scheduler.READY.add(pcb.NEW.poll());
                    process.head = i;
                    lastIn = i;
                    return;
                }
            }
        }
    }

    public boolean pageTableFull() {
        for (int i = 0; i < pageTable.length; i++) {
            if (pageTable[i] == null || pageTable[i].isEmpty()) return false;
        }
        return true;
    }
    public LinkedList<Integer> toLL(Process process){
        LinkedList<Integer> toll= new LinkedList<>();
        for (int j = 0; j < process.operations.length; j++) {
            if(process.operations[j].memory<=maxPageSize) toll.add(process.operations[j].memory);
            else toll.add(maxPageSize);
        }
        return toll;
    }

    public void lastInFirstOut(Process process) {
        if(!scheduler.READY.isEmpty()){
            for(int i = 0; i< scheduler.READY.size(); i++) {
                if(scheduler.READY.peek().head == lastIn) {
                    scheduler.READY.peek().head = -1;
                    i = scheduler.READY.size();
                }else scheduler.READY.add(scheduler.READY.poll());

            }
        }
        else if(!scheduler.WAITING.isEmpty()) {
            for(int i = 0; i< scheduler.WAITING.size(); i++) {
                if(scheduler.WAITING.peek().head == lastIn) {
                    scheduler.WAITING.peek().head = -1;
                    i = scheduler.WAITING.size();
                } else scheduler.WAITING.add(scheduler.WAITING.poll());
            }
        }
        process.head = lastIn;
        pageTable[lastIn] = toLL(process);
    }
}
