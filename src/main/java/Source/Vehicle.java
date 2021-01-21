package Source;



import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

@Entity
public class Vehicle implements Serializable,Comparable<Vehicle>  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vehicle_id",nullable = false)
    private int vehicle_id;
    String marka;
    String model;
    double cena;
    int rok_produkcji;
    double przebieg;
    double poj_silnika;
    public boolean isReserved;




    public Vehicle(){
        super();
    }

    public Vehicle(String ma,String mo,double c,int r, double pr, double poj,CarShowroom cs){
        marka = ma;
        model=mo;
        cena=c;
        rok_produkcji=r;
        przebieg=pr;
        poj_silnika=poj;
        isReserved=false;
    }

    public void print(){
        System.out.println("\nMarka: "+marka);
        System.out.println("Model: "+model);
        System.out.println("Cena: "+cena);
        System.out.println("Rok produkcji: "+rok_produkcji);
        System.out.println("Przebieg: "+przebieg);
        System.out.println("Pojemność silnika: "+poj_silnika);

    }

    public String getFullName(){
        return marka + " " + model;
    }

    public LinkedList<String> getInfo(){
        LinkedList<String>temp=new LinkedList<>();
        temp.add("Marka: "+marka+"\n");
        temp.add("Model: "+model+"\n");
        temp.add("Cena: "+cena+"zł\n");
        temp.add("Rok produkcji: "+rok_produkcji+"\n");
        temp.add("Przebieg: "+przebieg+"km\n");
        temp.add("Pojemność silnika: "+poj_silnika+"dm3");
        return temp;
    }

    public String getInfo2(){
        return ("Marka: "+marka+"\n"+"Model: "+model+"\n"+
                "Cena: "+cena+"zł\n"+"Rok produkcji: "+rok_produkcji+"\n"+"Przebieg: "+przebieg+"km\n"+
                "Pojemność silnika: "+poj_silnika+"dm3");
    }

    @Override
    public int compareTo(Vehicle v) {
        if(this==v){
                return 1;
        }
        else{
            return 0;
        }
    }

    public String getMarka() { return marka; }

    public String getModel() { return model; }

    public double getPoj_silnika() {
        return poj_silnika;
    }

    public double getPrzebieg() {
        return przebieg;
    }

    public int getId() {
        return vehicle_id;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setId(int id) {
        this.vehicle_id = id;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPoj_silnika(double poj_silnika) {
        this.poj_silnika = poj_silnika;
    }

    public void setPrzebieg(double przebieg) {
        this.przebieg = przebieg;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public void setRok_produkcji(int rok_produkcji) {
        this.rok_produkcji = rok_produkcji;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicle_id=" + vehicle_id +
                ", marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", cena=" + cena +
                ", rok_produkcji=" + rok_produkcji +
                ", przebieg=" + przebieg +
                ", poj_silnika=" + poj_silnika +
                ", isReserved=" + isReserved +
                '}';
    }

    public int getRok_produkcji() { return rok_produkcji; }

    public double getCena() { return cena; }

    public boolean isReserved() { return isReserved; }

}
