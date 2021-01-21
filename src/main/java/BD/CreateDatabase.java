package BD;

import Source.CarShowroom;
import Source.Review;
import Source.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateDatabase {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    public void create(){
        entityManagerFactory= Persistence.createEntityManagerFactory("mojaBaza");
        entityManager=entityManagerFactory.createEntityManager();
        CarShowroom cs = new CarShowroom();
        Vehicle v=new Vehicle();
        Review r=new Review();
        entityManager.close();
        entityManagerFactory.close();
    }
}
