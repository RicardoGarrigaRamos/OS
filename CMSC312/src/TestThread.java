public class TestThread extends Thread{

    int num;
    public TestThread(int i) {
        this.num = i;
    }

    @Override
    public void run() {
        method();
    }

    public void method() {
        System.out.println("Ran processes on thread " + num);

        /**try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }**/
    }

}
