public class BerkeleySimulation {
    public static void main(String args[]) {
        BerkeleySimulatorMonitor sm = new BerkeleySimulatorMonitor();
        BerkeleyServer srv = new BerkeleyServer(sm);
        srv.start();
        BerkeleyClient clv[] = new BerkeleyClient[3];
        for (int i = 0; i < 3; i++) {
            clv[i] = new BerkeleyClient(i, sm);
            clv[i].start();
        }

    }
}