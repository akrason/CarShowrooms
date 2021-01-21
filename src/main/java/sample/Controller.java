package sample;

import BD.GetData;
import BD.InitData;
import Source.CarShowroom;
import Source.CarShowroomContainer;
import Source.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

import static com.sun.javafx.application.PlatformImpl.exit;
import static javafx.collections.FXCollections.observableArrayList;

public class Controller implements Initializable {
    //InitData i=new InitData();
    GetData data=new GetData();
    CarShowroomContainer c = data.getData();//new CarShowroomContainer();//
    String salon1="Default";


    @FXML
    public Button srtByName;
    @FXML
    public ComboBox<String> combobox;
    @FXML
    public TextField search;
    @FXML
    public Label nazwal;
    @FXML
    public TableView CarsList=new TableView();
    @FXML
    private final TableColumn<Map,Vehicle>vehicle=new TableColumn<>("Pojazd");
    @FXML
    private final TableColumn<Map,String>nazwa=new TableColumn<>("Nazwa");
    @FXML
    private final TableColumn<Map,Double>cena=new TableColumn<>("Cena");
    @FXML
    private final TableColumn<Map,String>salon=new TableColumn<>("Salon");
    @FXML
    private final TableColumn<Map,String>rok=new TableColumn<>("Rok produckcji");
    @FXML
    private final TableColumn<Map,String>rezerwacja=new TableColumn<>("Stan rezerwacji");

    ObservableList<Map<String, Object>> items =
            FXCollections.observableArrayList();

    ObservableList<String> list;
    ArrayList<Vehicle>reserved=new ArrayList<>();

    public Controller() throws ParseException {
    }

    public void getData(){
        for(CarShowroom cs:c.getSalony()){
            for(Vehicle v:cs.getVehicles()){
                if(v.isReserved()){
                    reserved.add(v);
                }
            }
        }
        list= observableArrayList(c.getNazwy());
        list.add("Default");
        vehicle.setCellValueFactory(new MapValueFactory<>("Pojazd"));
        nazwa.setCellValueFactory(new MapValueFactory<>("Nazwa"));
        cena.setCellValueFactory(new MapValueFactory<>("Cena"));
        salon.setCellValueFactory(new MapValueFactory<>("Salon"));
        rok.setCellValueFactory(new MapValueFactory<>("Rok produkcji"));
        rezerwacja.setCellValueFactory(new MapValueFactory<>("Stan rezerwacji"));
        items=setDefault();

        CarsList.getColumns().add(vehicle);
        CarsList.getColumns().add(nazwa);
        CarsList.getColumns().add(cena);
        CarsList.getColumns().add(salon);
        CarsList.getColumns().add(rok);
        CarsList.getColumns().add(rezerwacja);
        vehicle.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getData();
        combobox.setItems(list);
        CarsList.setItems(items);
        CarsList.setRowFactory(tv -> new TableRow<Map>() {
            private final Tooltip tooltip = new Tooltip();
            @Override
            public void updateItem(Map sth, boolean empty) {
                super.updateItem(sth, empty);
                if (sth == null) {
                    setTooltip(null);
                } else {
                    Vehicle v= (Vehicle) sth.get("Pojazd");
                    tooltip.setText(c.getSalon((String)(sth.get("Salon"))).search(v.getId()).getInfo2());
                    setTooltip(tooltip);
                }
            }
        });

    }

    public void comboChanged(){

        String s ="Wybrano: "+combobox.getValue();
        nazwal.setText(s);
        if(combobox.getValue().equals("Default")){
            items=setDefault();
        }else{
            items=returnNewList(c.getSalon(combobox.getValue()));
        }
        CarsList.setItems(items);
        salon1=combobox.getValue();
    }

    public void textBoxChanged(){
        ObservableList<Map<String, Object>> temp =
                observableArrayList();
        ArrayList<Vehicle> wyszukane;
        String s=search.getText();
        if(s.isEmpty()){
            if(salon1.equals("Default")) {
                items = setDefault();
            }else{
                items=returnNewList(c.getSalon(salon1));
            }
        }else {
            if(salon1.equals("Default")) {
                for (CarShowroom salon : c.getSalony()) {
                    wyszukane = (salon.searchPartial(s));
                    for (Vehicle v : wyszukane) {
                        Map<String, Object> item1 = new HashMap<>();
                        item1.put("Pojazd",v);
                        item1.put("Nazwa",v.getFullName());
                        item1.put("Cena", v.getCena());
                        item1.put("Salon", salon.getNazwa());
                        item1.put("Rok produkcji", v.getRok_produkcji());
                        item1.put("Stan rezerwacji", v.isReserved());

                        temp.add(item1);
                    }
                }
            }else{
                wyszukane = (c.getSalon(salon1).searchPartial(s));
                for (Vehicle v : wyszukane) {
                    Map<String, Object> item1 = new HashMap<>();
                    item1.put("Pojazd",v);
                    item1.put("Nazwa",v.getFullName());
                    item1.put("Cena", v.getCena());
                    item1.put("Salon", c.getSalon(salon1).getNazwa());
                    item1.put("Rok produkcji", v.getRok_produkcji());
                    item1.put("Stan rezerwacji", v.isReserved());

                    temp.add(item1);
                }
            }
            items = temp;
            }

        CarsList.setItems(items);
    }

    public ObservableList<Map<String, Object>> returnNewList(CarShowroom cs){
        ObservableList<Map<String, Object>> temp =
                observableArrayList();
        for(Vehicle v:cs.getVehicles()){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("Pojazd",v);
            item1.put("Nazwa",v.getFullName());
            item1.put("Cena",v.getCena());
            item1.put("Salon",cs.getNazwa());
            item1.put("Rok produkcji",v.getRok_produkcji());
            item1.put("Stan rezerwacji",v.isReserved());

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

    public void sortByName(){
        CarsList.refresh();
        nazwa.setComparator(nazwa.getComparator().reversed());
        CarsList.getSortOrder().add(nazwa);
        CarsList.sort();
    }
    public void sortByPrice(){
        cena.setComparator(cena.getComparator().reversed());
        CarsList.getSortOrder().add(cena);
        CarsList.sort();
        }
    public void sortByYear(){
        rok.setComparator(rok.getComparator().reversed());
        CarsList.getSortOrder().add(rok);
        CarsList.sort();
    }

    public void reserveCar() throws IOException {
        Map<String,Object> map = (Map<String, Object>) CarsList.getSelectionModel().getSelectedItem();
        CarShowroom cs = c.getSalon((String) map.get("Salon"));
        Vehicle v= (Vehicle) map.get("Pojazd");
        if(v.isReserved) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ten samochód jest już na liście ulubionych!");
            alert.showAndWait();
        }else{
            v.isReserved=true;
            reserved.add(v);
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(v);
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }
        if(salon1.equals("Default")) {
            items=setDefault();}
        else{
            items=returnNewList(cs);
        }
        CarsList.setItems(items);

    }

    public void unreserveCar() throws IOException {
        Map<String,Object> map = (Map<String, Object>) CarsList.getSelectionModel().getSelectedItem();
        CarShowroom cs = c.getSalon((String) map.get("Salon"));
        Vehicle v= (Vehicle) map.get("Pojazd");
        if(!v.isReserved) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ten samochód nie jest na liście!");
            alert.showAndWait();
        }else{
            v.isReserved=false;
            reserved.remove(v);
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(v);
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }
        if(salon1.equals("Default")) {
            items=setDefault();}
        else{
            items=returnNewList(cs);
        }
        CarsList.setItems(items);
    }


    public void buyCar() {
        Map<String, Object> map = (Map<String, Object>) CarsList.getSelectionModel().getSelectedItem();
        CarShowroom cs = c.getSalon((String) map.get("Salon"));
        Vehicle v = (Vehicle) map.get("Pojazd");

        if (v.isReserved) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Samochód zarezerwowany - nie można dokonać zakupu!!!");
            alert.showAndWait();
        } else {
            cs.getProduct(v);
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(cs);
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            Vehicle v1=entityManager.find(Vehicle.class,v.getId());
            entityManager.remove(v1);
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
            if (salon1.equals("Default")) {
                items = setDefault();
            } else {
                items = returnNewList(cs);
            }
            CarsList.setItems(items);
        }
    }

    public void favourite() throws IOException {
        Stage favourites = new Stage();
        FXMLLoader root = new FXMLLoader();
        URL url=new File("src/main/resources/fav.fxml").toURI().toURL();
        root.setLocation(url);
        Parent root1 = root.load();
        favourites.setTitle("Ulubione");
        favourites.setScene(new Scene(root1, 600, 400));
        favourites.show();
        ControllerFav cntr = root.getController();
        cntr.getData(reserved,c);
        favourites.setOnCloseRequest(eu->{
            eu.consume();

            try {
                cntr.close(favourites);
                cntr.getData(reserved,c);
            } catch (IOException  e) {
                e.printStackTrace();
            }
            CarShowroom cs = c.getSalon(salon1);
            if(salon1.equals("Default")) {
                items=setDefault();}
            else{
                items=returnNewList(cs);
            }
            CarsList.setItems(items);
            System.out.println("Reserved"+Arrays.toString(new ArrayList[]{reserved}));

        });
    }
    public void reviews() throws IOException {
        Stage reviews = new Stage();
        FXMLLoader root = new FXMLLoader();
        URL url=new File("src/main/resources/rev.fxml").toURI().toURL();
        root.setLocation(url);
        Parent root1=root.load();
        reviews.setTitle("Oceny");
        reviews.setScene(new Scene(root1, 680, 480));
        reviews.show();
        ControllerReview cntr = root.getController();
        cntr.getData(c);

    }

    public void close(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zamknąć program?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK){

            stage.close();
        }
    }

}
