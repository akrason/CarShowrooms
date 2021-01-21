package BD;

import Source.CarShowroom;
import Source.CarShowroomContainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class GetData {
    public CarShowroomContainer getData(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<CarShowroom> typedQuery = entityManager.createQuery("select c from CarShowroom c where c.nazwa !=null",CarShowroom.class);
        List<CarShowroom> carShowroomList=typedQuery.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        CarShowroomContainer data=new CarShowroomContainer();
        for(CarShowroom c:carShowroomList){
            data.addCenter2(c.getNazwa(),c);
        }
        return data;
    }

}
