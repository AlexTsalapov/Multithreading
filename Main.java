import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.RandomAccessFile;
import java.time.Instant;
import java.util.*;

public class Main extends Thread {
    public int time = 0;
    private double totalTonn = 86;


    private int tempTime = 0;
    private double tonna = 0;

    public double getTotalTonn() {
        return totalTonn;
    }


    public double getTonna() {
        return tonna;
    }

    public int getTempTime() {
        return tempTime;
    }

    public void setTempTime(int tempTime) {
        this.tempTime = tempTime;
    }

    public void setTonna(double tonna) {
        this.tonna = tonna;
    }

    public void addTonna(double tonna) {
        this.tonna += tonna;
    }

    public void setTotalTonn(double totalTonn) {
        this.totalTonn = totalTonn;
    }



    public void show(int time,ArrayList<Truck> list) {

        System.out.println((time / 3600) + " Часов");
        System.out.println(time % 3600 / 60 + " Минут");
        for (int i = 0; i < 3; i++) {
            System.out.println(i + 1 + "c грузоподъемностью " + list.get(i).getTonnazh() + " грузовик совершил - " + list.get(i).getCounter() + " поездок");
        }
    }

    public void plusTonn(double tonna) {
        setTonna((getTonna() + tonna));

    }


    public static void main(String[] args) throws InterruptedException {
        Main t = new Main();
        Scanner console = new Scanner(System.in);
        int count = 0;
        System.out.println("Введите массу груза в тоннах");
        t.setTotalTonn(console.nextDouble());
        Proxy1 proxy1 = new Proxy1();
         Truck truck1 = new Truck(12, 10, t.totalTonn, proxy1);
        Truck truck2 = new Truck(13, 5, t.totalTonn, proxy1);
        Truck truck3 = new Truck(24, 15, t.totalTonn, proxy1);
        /*Truck truck1 = new Truck(t.totalTonn,proxy1);
        Truck truck2 = new Truck(t.totalTonn,proxy1);
        Truck truck3 = new Truck(t.totalTonn, proxy1);*/
        ArrayList<Truck> list = new ArrayList<>();

        list.add(truck1);
        list.add(truck2);
        list.add(truck3);
       /* for (int i = 0; i < 2; i++) {//можно через компаратор
            for (int j = i + 1; j < 3; j++) {
                if (list.get(i).getTimeToWay()+list.get(i).getTonnazh()*list.get(i).getTimeToTonna() > list.get(j).getTimeToWay()+list.get(j).getTonnazh()*list.get(j).getTimeToTonna()) {
                    Truck temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }*/
        Collections.sort(list, new Comparator<Truck>() {
            @Override
            public int compare(Truck o1, Truck o2) {
                return (int) (o1.getTimeToWay() + o1.getTonnazh() * o1.getTimeToTonna() - o2.getTimeToWay() + o2.getTonnazh() * o2.getTimeToTonna());
            }
        });

        Thread thread1 = new Thread(list.get(0));
        Thread thread2 = new Thread(list.get(1));
        Thread thread3 = new Thread(list.get(2));
        ArrayList<Thread> listStream = new ArrayList<>();
        listStream.add(thread1);
        listStream.add(thread2);
        listStream.add(thread3);

        listStream.get(0).start();
        listStream.get(1).start();
        listStream.get(2).start();

        listStream.get(0).join();
        listStream.get(1).join();
        listStream.get(2).join();


     /*   for (int i = 0; i < 2; i++) {
            for (int j = i + 1; j < 3; j++) {
                if (list.get(i).getTempTime() < list.get(j).getTempTime()&&list.get(j).getTempTime()>0) {
                    Truck temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }*/

        Collections.sort(list, new Comparator<Truck>() {
            @Override
            public int compare(Truck o1, Truck o2) {
                return o2.getTempTime() - o1.getTempTime();
            }
        });
        t.show(list.get(0).getTempTime(),list);



    }
}
