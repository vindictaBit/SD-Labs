
public class BerkeleySimulatorMonitor {
    private long serverTime;
    private long[] diffTimes;
    private long sumDiffs;
    private final int numClients = 3;
    private int countClientsOpered;

    public BerkeleySimulatorMonitor() {
        this.serverTime = 0;
        this.countClientsOpered = this.numClients;
        this.diffTimes = new long[this.numClients];
        this.sumDiffs = 0;
    }

    public synchronized void setServerTime(long serverTime) {
        this.serverTime = serverTime;
        try {
            notifyAll();
            wait();
        } catch (InterruptedException e) {}
    }

    public synchronized void setDiffTimes(long time, int n) {
        try {
            if (serverTime == 0)
                wait();
            this.diffTimes[n] = (time - serverTime);
            this.sumDiffs += time;
            countClientsOpered--;
            if (countClientsOpered == 0)
                notify();
            wait();
        } catch (InterruptedException e) {}
    }

    public synchronized void calcAvgAndSet() {
        long avg = (this.sumDiffs / (this.numClients + 1));
        for (int i = 0; i < this.numClients; i++)
            this.diffTimes[i] = ((-this.diffTimes[i]) + avg);
        notifyAll();
    }

    public synchronized long getSettingTime(int n) {
        return this.diffTimes[n];
    }

    public synchronized long getAverage() {
        return this.sumDiffs / (this.numClients + 1);
    }

    public synchronized void restartProcess() {
        this.serverTime = 0;
        this.countClientsOpered = this.numClients;
        this.sumDiffs = 0;
    }

}