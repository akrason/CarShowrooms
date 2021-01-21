package sample;

import Source.CarShowroom;
import Source.CarShowroomContainer;
import Source.Review;
import Source.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;

import javax.persistence.*;
import java.net.URL;
import java.sql.Date;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerReview implements Initializable {
    CarShowroomContainer c=new CarShowroomContainer();
    String salon="Default";
    @FXML
    private final TableColumn<Map, CarShowroom> showroom=new TableColumn<>("Id salonu");
    @FXML
    private final TableColumn<Map,String>nazwa=new TableColumn<>("Nazwa salonu");
    @FXML
    private final TableColumn<Map,String>com=new TableColumn<>("Komentarz");
    @FXML
    private final TableColumn<Map,Integer>ocena=new TableColumn<>("Ocena");
    @FXML
    private final TableColumn<Map, Date>rok=new TableColumn<>("Data dodania");

    @FXML
    public TableView reviews = new TableView();
    @FXML
    public TextArea comment;
    @FXML
    public TextField rate;
    @FXML
    public Label l_ocen;
    @FXML
    public Label srednia_ocen;
    @FXML
    public ComboBox<String> combobox;
    ObservableList<String> list;
    ObservableList<Map<String, Object>> items= FXCollections.observableArrayList();

    public void getData(CarShowroomContainer container){
        c=container;
        showroom.setCellValueFactory(new MapValueFactory<>("Id salonu"));
        nazwa.setCellValueFactory(new MapValueFactory<>("Nazwa salonu"));
        com.setCellValueFactory(new MapValueFactory<>("Komentarz"));
        ocena.setCellValueFactory(new MapValueFactory<>("Ocena"));
        rok.setCellValueFactory(new MapValueFactory<>("Data dodania"));
        list= observableArrayList(c.getNazwy());
        list.add("Default");
        combobox.setItems(list);
        reviews.getColumns().add(showroom);
        reviews.getColumns().add(nazwa);
        reviews.getColumns().add(com);
        reviews.getColumns().add(ocena);
        reviews.getColumns().add(rok);
        showroom.setVisible(false);
        items=setDefault();
        reviews.setItems(items);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void comboChanged(){
        salon=combobox.getValue();
        if(combobox.getValue().equals("Default")){
            items=setDefault();
            l_ocen.setText("Liczba ocen: Wybierz salon");
            srednia_ocen.setText("Średnia ocen:???");
        }else{
            CarShowroom cs=c.getSalon(salon);
            items=returnNewList(cs);
            avgChanged(cs);
        }
        reviews.setItems(items);


    }
    public ObservableList<Map<String, Object>> returnNewList(CarShowroom cs){
        ObservableList<Map<String, Object>> temp =
                observableArrayList();
        List<Review> rev = new ArrayList<>(cs.getOceny());
        for(Review r:rev){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("Id salonu",cs);
            item1.put("Nazwa salonu",cs.getNazwa());
            item1.put("Komentarz",r.getComment());
            item1.put("Ocena",r.getRate());
            item1.put("Data dodania",r.getData());

            temp.add(item1);
        }

        return temp;
    }

    public ObservableList<Map<String, Object>> setDefault(){
        ObservableList<Map<String, Object>> temp =
                observableArrayList();
        for(CarShowroom salon:c.getSalony()){
            temp.addAll(returnNewList(salon));
        }
        return temp;
    }

    public void addReview(){
        if(salon=="Default"){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wybierz salon!!");
            alert.showAndWait();
        }else{
            boolean isGood=true;
            CarShowroom  cs=c.getSalon(salon);
            Calendar calendar=Calendar.getInstance();
            java.util.Date currentDate=calendar.getTime();
            Date date= new java.sql.Date(currentDate.getTime());
            int o = 0;
            String s = new String();
            if(rate.getText()!=null){
                o= Integer.parseInt(rate.getText());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie wpisano komentarza!!");
                alert.showAndWait();
                isGood=false;
            }
            if(comment.getText()!=null) {
                s = comment.getText();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie wystawiono oceny!!");
                alert.showAndWait();
                isGood=false;
            }

            if(isGood){
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                Review r=new Review(s,date,o);
                cs.addReview(r);
                entityManager.getTransaction().begin();
                entityManager.persist(r);
                entityManager.merge(cs);
                entityManager.getTransaction().commit();
                entityManager.close();
                entityManagerFactory.close();
                comment.setText("");
                rate.setText("");
                reviews.setItems(returnNewList(cs));
                avgChanged(cs);
            }
        }
    }

    public void avgChanged(CarShowroom carShowroom){
        l_ocen.setText("Liczba ocen: "+carShowroom.getOceny().size());
        double suma=0.0;
        for (Review r:carShowroom.getOceny()){
            suma+=r.getRate();
        }
        double srednia=suma/carShowroom.getOceny().size();
        srednia_ocen.setText("Średnia ocen: "+(srednia));
    }
}
