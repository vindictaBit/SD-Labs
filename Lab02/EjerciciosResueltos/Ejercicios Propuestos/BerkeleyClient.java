public class BerkeleyClient extends Thread
{
    private int clientID;
    private long clientTime;
    private BerkeleySimulatorMonitor sm;
    private final boolean addDelay = true;
    public BerkeleyClient(int clientID,BerkeleySimulatorMonitor sm){
        this.sm         = sm;
        this.clientID   = clientID;
        this.clientTime = System.nanoTime();
    }
    
    public void run(){
        while(true){
                this.clientTime += (this.addDelay) ? (this.clientID+1)*2 : 0;
                System.out.println("Temporización (cliente " + clientID + ") : " + this.clientTime);
                this.sm.setDiffTimes(this.clientTime,this.clientID);
                this.clientTime += this.sm.getSettingTime(this.clientID);
                System.out.println("Temporización acordada (cliente " + clientID + ") : " + this.clientTime);
        }
    }
                 
}