package BD;

import Source.CarShowroom;
import Source.Review;
import Source.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.text.ParseException;


public class InitData {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    public InitData() throws ParseException {

        CarShowroom c1 = new CarShowroom("Krakow",56.0);
        Vehicle v =new Vehicle("Peugeot","307CC",30063.0,2017,20.2,3.2,c1);
        Vehicle v1 =new Vehicle("Ford","Fiesta",56630.0,2020,5.0,2.0,c1);
        Vehicle v2 =new Vehicle("Ford","Mustang",253457.0,2018,53014.0,5.0,c1);
        Vehicle v3 =new Vehicle("Peugeot","508",53457.0,2013,200000.0,5.0,c1);
        Vehicle v4 =new Vehicle("Peugeot","307CC",30063.0,2017,20.2,3.2,c1);
        Vehicle v5 =new Vehicle("Jeep","Grand Cherokee",115075.0,2015,154720.0,5.0,c1);
        Review r1=new Review("It's OK", Date.valueOf("2019-01-02"),4);
        Review r2=new Review("Could be better", Date.valueOf("2020-04-02"),3);
        Review r3=new Review("5/5!!!", Date.valueOf("2015-01-17"),5);

        CarShowroom c2 = new CarShowroom("Sandomierz",24);
        Vehicle v6 =new Vehicle("Jeep","Grand Cherokee",115075.0,2015,154720.0,5.0,c2);
        Vehicle v7 =new Vehicle("BMW","X5",153457.0,2017,121552.0,3.2,c2);
        Vehicle v8 =new Vehicle("BMW","X5",153457.0,2017,121552.0,3.2,c2);
        Vehicle v9 =new Vehicle("Fiat","Panda",12000.0,2009,201245.0,1.6,c2);
        Vehicle v10 =new Vehicle("Audi","Q7",399000.0,2020,20.0,3.0,c2);
        Vehicle v11 =new Vehicle("Ford","Mustang",253457.0,2018,53014.0,5.0,c2);
        Review r4=new Review("Meh", Date.valueOf("2017-06-13"),2);
        Review r5=new Review("I love it!! <3", Date.valueOf("2020-12-02"),5);


        CarShowroom c3 = new CarShowroom("Warszawa",67);
        Vehicle v12 =new Vehicle("Ford","Focus",41000.0,2017,53000.0,1.6,c3);
        Vehicle v13 =new Vehicle("BMW","I8",300000.0,2015,124000.0,2.5,c3);
        Vehicle v14 =new Vehicle("Porsche","718 Cayman",483048.0,2019,9600.0,4.0,c3);
        Vehicle v15 =new Vehicle("Hyundai","I30",34900.0,2013,114284.0,1.4,c3);
        Vehicle v16 =new Vehicle("BMW","X5",153457.0,2017,121552.0,3.2,c3);
        Vehicle v17 =new Vehicle("Ford","Fiesta",56630.0,2020,5.0,2.0,c3);
        Review r6=new Review("OK", Date.valueOf("2018-01-02"),4);
        Review r7=new Review("IDK", Date.valueOf("2020-04-12"),3);
        Review r8=new Review("Perfect!", Date.valueOf("2013-01-17"),5);
        Review r9=new Review("<3<3!!!", Date.valueOf("2017-07-17"),5);

        CarShowroom c4 = new CarShowroom("Wroclaw",80);
        Vehicle v18 =new Vehicle("Porsche","718 Cayman",483048.0,2019,9600.0,4.0,c4);
        Vehicle v19 =new Vehicle("Hyundai","I30",34900.0,2013,114284.0,1.4,c4);
        Vehicle v20 =new Vehicle("Audi","A5",204840.0,2020,1.0,2.0,c4);
        Vehicle v21 =new Vehicle("BMW","X5",153457.0,2017,121552.0,3.2,c4);
        Vehicle v22 =new Vehicle("Mercedez-Benz","GLE 250",139457.0,2019,5800.0,1.4,c4);
        Review r10=new Review("I like cars here a lot", Date.valueOf("2019-01-02"),5);
        Review r11=new Review("Could be better,idk really", Date.valueOf("2010-01-18"),3);
        Review r12=new Review("5/5!!!", Date.valueOf("2015-01-17"),5);

        CarShowroom c5 = new CarShowroom("Rzeszow",45);
        Vehicle v23=new Vehicle("Mercedez-Benz","GLE 250",184457.0,2018,34500.0,2.2,c5);
        Vehicle v24=new Vehicle("Ford","Fiesta",56630.0,2020,5.0,2.0,c5);
        Vehicle v25=new Vehicle("Porsche","718 Cayman",483048.0,2019,9600.0,4.0,c5);
        Vehicle v26=new Vehicle("BMW","X5",153457.0,2017,121552.0,3.2,c5);
        Vehicle v27=new Vehicle("BMW","I8",300000.0,2015,124000.0,2.5,c5);
        Vehicle v28=new Vehicle("Ford","Focus",41000.0,2017,53000.0,1.6,c5);
        Review r13=new Review("You can do better!", Date.valueOf("2020-11-02"),4);
        Review r14=new Review("4/5", Date.valueOf("2020-04-02"),4);


        c1.addProducts(new Vehicle[]{v, v1, v2, v3, v4, v5});
        c2.addProducts(new Vehicle[]{v6, v7, v8, v9, v10, v11});
        c3.addProducts(new Vehicle[]{v12, v13, v14, v15, v16, v17});
        c4.addProducts(new Vehicle[]{v18, v19, v20, v21, v22});
        c5.addProducts(new Vehicle[]{v23, v24, v25, v26, v27, v28});
        c1.addReview(r1);
        c1.addReview(r2);
        c1.addReview(r3);
        c2.addReview(r4);
        c2.addReview(r5);
        c3.addReview(r6);
        c3.addReview(r7);
        c3.addReview(r8);
        c3.addReview(r9);
        c4.addReview(r10);
        c4.addReview(r11);
        c4.addReview(r12);
        c5.addReview(r13);
        c5.addReview(r14);


        entityManager.getTransaction().begin();

        CarShowroom[]c=new CarShowroom[]{c1, c2, c3, c4, c5};
        Vehicle[]vs=new Vehicle[]{v, v1, v2, v3, v4, v5,
                                 v6, v7, v8, v9, v10, v11,
                                 v12, v13, v14, v15, v16, v17,
                                 v18, v19, v20, v21, v22,
                                 v23, v24, v25, v26, v27, v28};
        Review[] rs=new Review[]{r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14};
        persistAll(c);
        persistAll(vs);
        persistAll(rs);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public void persistAll(Object[] list){
        for(Object o:list){
            entityManager.persist(o);
        }
    }

}
