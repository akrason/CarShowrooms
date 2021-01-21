package Source;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

public class CarShowroomContainer implements Serializable {

    private int container_id;

    String nazwa;
    HashMap<String, CarShowroom> spis=new HashMap<>();
    ArrayList<String> nazwy=new ArrayList<>();
    ArrayList<CarShowroom> salony=new ArrayList<>();

    public void addCenter(String s,double poj){
        CarShowroom c= new CarShowroom(s,poj);
        if(!spis.containsKey(s)) {
            spis.put(s,c);
            nazwy.add(s);
            salony.add(c);
        }else{
            System.out.println("Taki klucz już istnieje!");
        }
    }

    public void addCenter2(String s, CarShowroom c){
        if(!spis.containsKey(s)) {
            spis.put(s,c);
            nazwy.add(s);
            salony.add(c);
        }else{
            System.out.println("Taki klucz już istnieje!");
        }
    }

    public void removeCenter(String s){
        if(spis.containsKey(s)){
            salony.remove(spis.get(s));
            spis.remove(s);
            nazwy.remove(s);
        }else{
            System.out.println("Nie ma takiego salonu!");
        }
    }

    public ArrayList<CarShowroom> findEmpty(){
        ArrayList<CarShowroom> empty = new ArrayList<CarShowroom>();
        for(CarShowroom c: spis.values()){
            if(c.pojazdy.size()==0){
                empty.add(c);
            }
        }
        return empty;
    }

    public void summary(){
        for(CarShowroom c:spis.values()){
            System.out.println("Nazwa:"+c.nazwa+"\nPojemność:"+c.max);
        }
    }
    public void sortByCurrentLoad(){
        Collections.sort(salony,comparator);
        ArrayList<String>temp = new ArrayList<>();
        for(CarShowroom c: salony){
            temp.add(c.getNazwa());
        }
        nazwy=temp;
    }
    public ArrayList<CarShowroom> getSalony(){
        return salony;
    }

    public ArrayList<String> getNazwy(){
        return nazwy;
    }

    public CarShowroom getSalon(String s){
        return spis.get(s);
    }

    public ArrayList<String> getCars(String s){
        ArrayList<String> temp=new ArrayList<>();
        if (spis.containsKey(s)){
            for(Vehicle v:spis.get(s).getVehicles()){
                temp.add(v.getFullName());
            }
        }
    return temp;
    }
    public Vehicle findVehicle(Vehicle v){
        for(CarShowroom cs:salony){
            for(Vehicle v1:cs.getVehicles()){
                if(v1==v){
                    return v1;
                }
            }
        }
        return v;
    }

    transient Comparator<CarShowroom> comparator=new Comparator<CarShowroom>() {
        @Override
        public int compare(CarShowroom o1, CarShowroom o2) {
            return Integer.compare(o1.getVehicles().size(),o2.getVehicles().size());
        }
    };

}
