package rs_projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddEditStorageController {
    public ChoiceBox<Warehouse> warehouseChoiceBox;
    public ChoiceBox<Item> itemChoiceBox;
    public TextField quantityFld;
    public TextField pricePerItemFld;
    public TextField totalPriceFld;
    private StorageItem storageItem;
    private ObservableList<Item> items;
    private ObservableList<Warehouse> warehouses;

    public AddEditStorageController(StorageItem storageItem, ArrayList<Item> items, ArrayList<Warehouse> warehouses) {
        this.storageItem= storageItem;
        this.items = FXCollections.observableArrayList(items);
        this.warehouses = FXCollections.observableArrayList(warehouses);
    }

    @FXML
    public void initialize() {
        itemChoiceBox.setItems(items);
        warehouseChoiceBox.setItems(warehouses);
        pricePerItemFld.setText("1");
        quantityFld.setText("1");
        totalPriceFld.setText("1");
        totalPriceFld.setEditable(false);
            if(storageItem != null) {
                warehouseChoiceBox.getSelectionModel().select(getWarehouseID());
                itemChoiceBox.getSelectionModel().select(getItemID());
                quantityFld.setText(String.valueOf(storageItem.getQuantity()));
                pricePerItemFld.setText(String.valueOf(storageItem.getPricePerItem()));
                totalPriceFld.setText(String.valueOf(storageItem.getQuantity()*storageItem.getPricePerItem()));
            }

            pricePerItemFld.setDisable(true);
            pricePerItemFld.setEditable(false);
            totalPriceFld.setDisable(true);

        pricePerItemFld.textProperty().addListener((observableValue, s, t1) -> {
                totalPriceFld.setText(String.valueOf(Integer.parseInt(quantityFld.getText())*Double.parseDouble(t1)));
        });

        quantityFld.textProperty().addListener((observableValue, s, t1) -> {
            fieldValidate();
            if(t1.isEmpty()) {
                t1 = "0";
            }
            totalPriceFld.setText(String.valueOf(Integer.parseInt(t1)*Double.parseDouble(pricePerItemFld.getText())));
        });
    }

    public void fieldValidate () {
        if (quantityFld.getText().trim().isEmpty()) {
            quantityFld.getStyleClass().add("notvalid");
        }
        else quantityFld.getStyleClass().removeAll("notvalid");

        if (pricePerItemFld.getText().trim().isEmpty()) {
            pricePerItemFld.getStyleClass().add("notvalid");
        }
        else pricePerItemFld.getStyleClass().removeAll("notvalid");

        if (totalPriceFld.getText().trim().isEmpty()){
            totalPriceFld.getStyleClass().add("notvalid");
        }
        else totalPriceFld.getStyleClass().removeAll("notvalid");
    }

    private int getItemID() {
        int id = 0;
        for (Warehouse warehouse : warehouses)
            if (warehouse.getId() == storageItem.getWarehouse().getId()) id = warehouses.indexOf(warehouse);
        return id;
    }

    private int getWarehouseID() {
        int id = 0;
        for (Warehouse warehouse : warehouses)
            if (warehouse.getId() == storageItem.getWarehouse().getId()) id = warehouses.indexOf(warehouse);
        return id;
    }

    public void cancelAction () {
        Stage stage = (Stage) quantityFld.getScene().getWindow();
        stage.close();
    }


    public void okAction() {
        if (storageItem==null){
            storageItem=new StorageItem();
        }
        storageItem.setItem(itemChoiceBox.getValue());
        storageItem.setWarehouse(warehouseChoiceBox.getValue());
        storageItem.setPricePerItem(Double.parseDouble(pricePerItemFld.getText()));
        storageItem.setQuantity(Integer.parseInt(quantityFld.getText()));
        storageItem.setTotalPrice(Double.parseDouble(totalPriceFld.getText()));
        Stage stage = (Stage) quantityFld.getScene().getWindow();
        stage.close();
    }

    public StorageItem getStorageItem() {
        return storageItem;
    }
}
