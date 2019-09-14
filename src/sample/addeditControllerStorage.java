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
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.ArrayList;

public class addeditControllerStorage {
    public Button storagecancel_btn;
    public TextField storagewh_fld;
    public ChoiceBox<Item> item_cb;
    public TextField storageqty_fld;
    public TextField storagepricepi_fld;
    public TextField storagetotprice_fld;
    public Button storageok_btn;
    private StorageItem storageItem;

    public addeditControllerStorage(StorageItem storageItem) {
        this.storageItem= storageItem;
    }

    @FXML
    public void initialize() {
        storagewh_fld.setText(storageItem.getWarehouse().toString());
        storageqty_fld.setText(storageItem.getQuantity()+"");
        storagepricepi_fld.setText(storageItem.getPricePerItem()+"");
        storagetotprice_fld.setText(storageItem.getQuantity()*storageItem.getPricePerItem()+"");

    }

    public void storagecancle_action () {
        Stage stage = (Stage) storagecancel_btn.getScene().getWindow();
        stage.close();
    }


    public void storageok_action() {
        if (storageItem==null){
            storageItem=new StorageItem();
        }
      //  storageItem.setWarehouse(storagewh_fld.);
    }
}
