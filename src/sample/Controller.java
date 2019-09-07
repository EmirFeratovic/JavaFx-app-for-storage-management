package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    public TableView<Warehouse> warehouse_tbl;
    public TableColumn warehouse_id_col;
    public TableColumn<Warehouse, String> warehouse_loc_col;
    public TableColumn<Warehouse, String> warehouse_name_col;
    public Tab warehouse_tab;
    public Button warehouse_addbtn;
    public Button warehouse_editbtn;
    public Button warehouse_deletebtn;
    public Tab storage_tab;
    public TextField search_fld;
    public ChoiceBox locations_cb;
    public Button storage_addbtn;
    public Button storage_editbtn;
    public Button storage_deletebtn;
    public TableView <StorageItem> storage_tbl;
    public TableColumn <StorageItem , String> storage_id_col;
    public TableColumn <StorageItem , String> storage_loc_col;
    public TableColumn <StorageItem , String> storage_item_col;
    public TableColumn <StorageItem , String> storage_qyt_col;
    public TableColumn <StorageItem , String> storage_ppi_col;
    public TableColumn <StorageItem , String> storage_totprice_col;
    public Tab item_tab;
    public ListView item_list;
    public TextField fld_id;
    public TextField fld_name;
    public TextField price_fld;
    public TextField weight_fld;
    public TextArea desc_textarea;
    public Button item_addbtn;
    public Button item_editbtn;
    public Button item_deletebtn;
    ArrayList<Warehouse> arrayList = new ArrayList<>();
    ArrayList<StorageItem> arrayList2 = new ArrayList<>();



    @FXML
    public void initialize() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setLocation("Sarajevo");
        warehouse.setName("SaTrade");
        arrayList.add(warehouse);
        ObservableList<Warehouse> warehouses =  FXCollections.observableArrayList(arrayList);

        warehouse_tbl.setItems(warehouses);
        warehouse_id_col.setCellValueFactory(new PropertyValueFactory("id"));
        warehouse_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        warehouse_loc_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        Item item = new Item();
        ArrayList<StorageItem> arrayList2 = new ArrayList<>();
        item.setId(1);
        item.setDescription("8GB,DVI,VGA");
        item.setName("Nvidia GTX 1050");
        item.setWeight(450);
        item.setPrice(600);
        StorageItem storageItem = new StorageItem();
        storageItem.setId(1);
        storageItem.setItem(item);
        storageItem.setPricePerItem(item.getPrice());
        storageItem.setQuantity(5);
        storageItem.setTotalPrice(storageItem.getQuantity() * storageItem.getPricePerItem());
        storageItem.setWarehouse(warehouse);
        arrayList2.add(storageItem);
        ObservableList<StorageItem> storageItems =  FXCollections.observableArrayList(arrayList2);
        storage_tbl.setItems(storageItems);
        storage_id_col.setCellValueFactory(new PropertyValueFactory("id"));
        storage_item_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItem().getName()));
        storage_loc_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWarehouse().getName()));
        storage_ppi_col.setCellValueFactory(new PropertyValueFactory("pricePerItem"));
        storage_qyt_col.setCellValueFactory((new PropertyValueFactory("quantity")));
        storage_totprice_col.setCellValueFactory((new PropertyValueFactory("totalPrice")));
    }

    public void warehoseadd_action(ActionEvent actionEvent) {
        //if (patientsList.isEmpty()) return;
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditWarehouse.fxml"));
            addeditControllerWarehouse warehouseController = new addeditControllerWarehouse();
            loader.setController(warehouseController);
            root = loader.load();
            stage.setTitle("Warehouse");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warehouseedit_action(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditWarehouse.fxml"));
            addeditControllerWarehouse warehouseController = new addeditControllerWarehouse();
            loader.setController(warehouseController);
            root = loader.load();
            stage.setTitle("Warehouse");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warehousedelete_action(ActionEvent actionEvent) {
        if (warehouse_tbl.getSelectionModel().isEmpty()) return;
        Warehouse warehouse = warehouse_tbl.getSelectionModel().getSelectedItem();
        if (warehouse == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Deleting warehouse " + warehouse.getName());
        alert.setContentText("Are you sure you want to delete this warehouse?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            arrayList.remove(warehouse_tbl.getSelectionModel().getSelectedItem());
           // arrayList.removeAll(warehouse_tbl.getSelectionModel().getSelectedItem());
            ObservableList<Warehouse> warehouses =  FXCollections.observableArrayList(arrayList);
            warehouse_tbl.setItems(warehouses);
            warehouse_tbl.refresh();
        }
    }



    public void itemadd_action(ActionEvent actionEvent) {
    }

    public void itemdelete_action(ActionEvent actionEvent) {

    }
    public void itemedit_action(ActionEvent actionEvent) {

    }

    public void storageadd_action() {
        //if (patientsList.isEmpty()) return;
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditStorage.fxml"));
            addeditControllerStorage storageController = new addeditControllerStorage();
            loader.setController(storageController);
            root = loader.load();
            stage.setTitle("Storage");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void storageedit_action(ActionEvent actionEvent) {
        //if (patientsList.isEmpty()) return;
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditStorage.fxml"));
            addeditControllerStorage storageController = new addeditControllerStorage();
            loader.setController(storageController);
            root = loader.load();
            stage.setTitle("Storage");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storagedelete_action(ActionEvent actionEvent) {
    }

}
