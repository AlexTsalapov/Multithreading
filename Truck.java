//import java.time.Instant;
//import java.time.LocalTime;
import java.util.Scanner;

public class Truck  implements Runnable {
    volatile private double allTonn = 0;

    volatile private Proxy1 proxy1;

    private int timeToTonna = 3 * 60;
    private int tempTime = 0;
    private int counter = 0;
    private double tempTonn = 0;
    private double tonnazh = 0;
    private int timeToWay = 0;
    volatile private int time = 0;


    public int getCounter() {
        return counter;
    }

    public int getTimeToWay() {
        return (int) (timeToWay + tonnazh * timeToWay);
    }


    public double getTonnazh() {
        return tonnazh;
    }

    public int getTimeToTonna() {
        return timeToTonna;
    }

    public int getTempTime() {
        return tempTime;
    }

    public synchronized void addTempTonn(double tonn) {
        tempTonn += tonn;
    }

    public synchronized double getTempTonn() {
        return tempTonn;
    }

    public Truck(double allTonn, Proxy1 p) {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите тоннаж");
        this.tonnazh = console.nextDouble();
        System.out.println("Введите время в пути");
        this.timeToWay = console.nextInt() * 60;
        this.allTonn = allTonn;
        this.proxy1 = p;

    }

    public Truck(double tonnazh, int timeToWay, double allTonn, Proxy1 p) {
        this.allTonn = allTonn;
        this.tonnazh = tonnazh;
        this.timeToWay = timeToWay * 60;

        this.proxy1 = p;
    }


    public synchronized void inPogruzka() throws InterruptedException {

            if (cheek()) {
                this.addTime(proxy1.time);
                proxy1.time = (int) (tonnazh * timeToTonna);

                if (allTonn - proxy1.getTempTonn() < tonnazh) {
                    double temp = allTonn - proxy1.getTempTonn();
                    proxy1.addTempTonn(temp);
                    counter++;
                    this.addTime((int) (timeToTonna * temp + timeToWay));
                } else {
                    proxy1.addTempTonn(tonnazh);
                    counter++;
                    this.addTime((int) (timeToTonna * tonnazh + timeToWay));
                }

                Thread.sleep((long) (tonnazh * timeToTonna));

            }


    }

    public boolean cheek() {

        if (allTonn > proxy1.getTempTonn()) {
            return true;
        } else {
            return false;
        }

    }

    public void addTime(int time) {
        tempTime += time;
    }

    public void inWay() {
        try {
            Thread.sleep((long) (timeToWay));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        while (cheek()) {

            try {
                inPogruzka();
                System.out.println("Грузовик c тоннажом " + this.tonnazh + " отправился");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            inWay();
            System.out.println("Грузовик c тоннажом " + this.tonnazh + " прибыл");
        }

    }
}

