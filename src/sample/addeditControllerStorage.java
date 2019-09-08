package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class addeditControllerStorage {
    public Button storagecancel_btn;
    public TextField storageloc_fld;
    public ChoiceBox<Item> item_cb;
    public TextField storageqty_fld;
    public TextField storagepricepi_fld;
    public TextField storagetotprice_fld;
    public Button storageok_btn;
    private StorageItem storageItem;


    @FXML
    public void initialize() {
        ArrayList<Item> arrayList3 = new ArrayList<>();
        Item item = new Item();
        item.setId(1);
        item.setPrice(450);
        item.setWeight(0.6);
        item.setName("AMD FX8120");
        item.setDescription("8 core, 3.1ghz, 250");
        arrayList3.add(item);
        ObservableList<Item> items = FXCollections.observableArrayList(arrayList3);
        item_cb.setItems(items);
    }

    public void storagecancle_action () {
        Stage stage = (Stage) storagecancel_btn.getScene().getWindow();
        stage.close();
    }


    public void storageok_action() {

    }
}
