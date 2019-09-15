package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public ChoiceBox<Warehouse> warehouse_cb;
    public ChoiceBox<Item> item_cb;
    public TextField storageqty_fld;
    public TextField storagepricepi_fld;
    public TextField storagetotprice_fld;
    public Button storageok_btn;
    private StorageItem storageItem;
    private ObservableList<Item> items;
    private ObservableList<Warehouse> warehouses;

    public addeditControllerStorage(StorageItem storageItem, ArrayList<Item> items, ArrayList<Warehouse> warehouses) {
        this.storageItem= storageItem;
        this.items = FXCollections.observableArrayList(items);
        this.warehouses = FXCollections.observableArrayList(warehouses);
    }

    @FXML
    public void initialize() {
        item_cb.setItems(items);
        warehouse_cb.setItems(warehouses);
        storagepricepi_fld.setText("1");
        storageqty_fld.setText("1");
        storagetotprice_fld.setText("1");
        storagetotprice_fld.setEditable(false);
            if(storageItem != null) {
                warehouse_cb.getSelectionModel().select(storageItem.getWarehouse());
                item_cb.getSelectionModel().select(storageItem.getItem());
                storageqty_fld.setText(String.valueOf(storageItem.getQuantity()));
                storagepricepi_fld.setText(String.valueOf(storageItem.getPricePerItem()));
                storagetotprice_fld.setText(String.valueOf(storageItem.getQuantity()*storageItem.getPricePerItem()));
            }


        storagepricepi_fld.textProperty().addListener((observableValue, s, t1) -> {
                storagetotprice_fld.setText(String.valueOf(Integer.parseInt(storageqty_fld.getText())*Double.parseDouble(t1)));
        });

        storageqty_fld.textProperty().addListener((observableValue, s, t1) -> {
                storagetotprice_fld.setText(String.valueOf(Integer.parseInt(t1)*Double.parseDouble(storagepricepi_fld.getText())));
        });
    }

    public void storagecancle_action () {
        Stage stage = (Stage) storagecancel_btn.getScene().getWindow();
        stage.close();
    }


    public void storageok_action() {
        if (storageItem==null){
            storageItem=new StorageItem();
        }
        storageItem.setItem(item_cb.getValue());
        storageItem.setWarehouse(warehouse_cb.getValue());
        storageItem.setPricePerItem(Double.parseDouble(storagepricepi_fld.getText()));
        storageItem.setQuantity(Integer.parseInt(storageqty_fld.getText()));
        storageItem.setTotalPrice(Double.parseDouble(storagetotprice_fld.getText()));
        Stage stage = (Stage) storageok_btn.getScene().getWindow();
        stage.close();
    }

    public StorageItem getStorageItem() {
        return storageItem;
    }
}
