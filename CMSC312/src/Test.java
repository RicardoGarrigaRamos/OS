public class Test {
    public static void main(String[] args) {
        int t = 10;
        TestThread[] threads = new TestThread[2];

        for(int i = 0; i< threads.length; i++) {
            threads[i] = new TestThread(i);
        }

        for(int i = 0; i< threads.length; i++) {
            threads[i].start();
        }

    }
}
