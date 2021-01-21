package Source;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

@Entity
public class CarShowroom implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="showroom_id",nullable = false)
    private int showroom_id;
    @Column(name="name")
    String nazwa;
    @Column(name="max")
    double max;
    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="showroom_id")
    List<Vehicle> pojazdy=new ArrayList<>();
    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="showroom_id")
    Set<Review> oceny =new HashSet<>();

    public CarShowroom(){
        super();
    }
    public CarShowroom(String s,double m){
        nazwa=s;
        max=m;
    }

    public void addProduct(Vehicle v){
        if(pojazdy.size()==max){
            System.err.println("Maksymalna ilosc pojazdow w salonie");
        }else{
            pojazdy.add(v);
        }
    }

    public void addReview(Review r){
        oceny.add(r);
    }

    public void addProducts(Vehicle[] list){
        for(Vehicle v:list){
            addProduct(v);
        }
    }

    public void getProduct(Vehicle v){
        if(pojazdy.contains(v)){
            pojazdy.remove(v);
        }
    }



    public void removeProduct(Vehicle v){
        if(pojazdy.contains(v)){
            List<Vehicle> temp =new ArrayList<>();
            for(Vehicle v1:pojazdy){
                if(v.compareTo(v1)==1){
                    temp.add(v1);
                }
            }
            pojazdy.removeAll(temp);
        }
    }



    public Vehicle search(int id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery query = entityManager.createQuery("select v from Vehicle v where v.vehicle_id="+id, Vehicle.class);
        Vehicle v = (Vehicle) query.getSingleResult();
        entityManager.close();
        entityManagerFactory.close();
        return v;
    }

    public ArrayList<Vehicle> searchPartial(String s){
        ArrayList znalezione=new ArrayList<>();
        if(s.isEmpty()){
            throw new IllegalArgumentException("Nie można szukać pustego ciągu");

        }else{/*
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TypedQuery query = entityManager.createQuery("select v from Vehicle v where v.model LIKE'%"+s+"%'", Vehicle.class);
            znalezione= (ArrayList) query.getResultList();*/
            for(Vehicle v: pojazdy){
                String name = v.getFullName();
                if(name.toLowerCase().contains(s.toLowerCase())) {
                    //System.out.println(v.getFullName());
                    znalezione.add(v);
                }
            }

        }
        return znalezione;
    }

    public void summary(){
        for(int i =0; i<pojazdy.size();i++){
            pojazdy.get(i).print();
        }
    }

    public List sortByName(){
        Collections.sort(pojazdy,comparator);
        return pojazdy;
    }

    public List<Vehicle>getVehicles(){
        return pojazdy;
    }
    @Transactional()
    public Set<Review> getOceny() {
        return oceny;
    }

    public String getNazwa(){
        return nazwa;
    }

    public int getShowroom_id() {
        return showroom_id;
    }

    transient Comparator<Vehicle> comparator=new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.getFullName(),o2.getFullName());
        }
    };
}
