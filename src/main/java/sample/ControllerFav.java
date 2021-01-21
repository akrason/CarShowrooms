package sample;

import Source.CarShowroomContainer;
import Source.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;

public class ControllerFav implements Initializable {
    ObservableList<String> res = FXCollections.observableArrayList();
    CarShowroomContainer c;
    ArrayList<Vehicle>reserved=new ArrayList<>();

    @FXML
    public ListView<String> CarsList;


    public void getData(ArrayList<Vehicle> re,CarShowroomContainer csc){
        reserved = re;
        c=csc;

        List<String> temp = new ArrayList<>();
        for(Vehicle v:reserved){
            temp.add(v.getInfo2());
        }
        temp=FXCollections.observableList(temp);
        res= (ObservableList<String>) temp;
        CarsList.setItems(res);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData(reserved,c);
    }

    public void removeRes(){
        int info = CarsList.getSelectionModel().getSelectedIndex();
        Vehicle v=c.findVehicle(reserved.get(info));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zapisać zmiany?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK) {
            v.isReserved = false;
            reserved.remove(v);
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mojaBaza");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(v);
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }
        System.out.println("Reserved"+ Arrays.toString(new ArrayList[]{reserved}));
        getData(reserved,c);
        CarsList.setItems(res);
    }
/*
    public void serializeReservedList() throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("reserved.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
        outputStream.writeObject(reserved);
        fileOutput.close();
        outputStream.close();
        //System.out.println("Serializacja r2");
    }
    public void serializeCarShowroom() throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("cars.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
        outputStream.writeObject(c);
        fileOutput.close();
        outputStream.close();
        //System.out.println("Serializacja c1");
    }*/

    public void close(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz wyjść?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK){
            //serializeReservedList();
            //serializeCarShowroom();
            stage.close();
        }
        //System.out.println("Reserved"+Arrays.toString(new ArrayList[]{reserved}));
    }
}
