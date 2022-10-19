public class Proxy1 {
    private double tempTonn = 0;
    public int time = 0;

    public  void addTempTonn(double tonn) {
        tempTonn += tonn;
    }

    public synchronized double getTempTonn() {
        return tempTonn;
    }

    public synchronized double getTime() {
        return time;
    }

    public  void setTime(int time)
    {
        this.time=time;
    }


}
