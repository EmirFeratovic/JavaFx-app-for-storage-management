package sample;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.time.LocalDate;
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
    public ChoiceBox<Warehouse> locations_cb;
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
    public ListView<Item> item_list;
    public TextField fld_id;
    public TextField fld_name;
    public TextField price_fld;
    public TextField weight_fld;
    public TextArea desc_textarea;
    public Button item_addbtn;
    public Button item_editbtn;
    public Button item_deletebtn;
    private StorageDAOdb dao;
    private Warehouse warehouseAll = new Warehouse(-1, "All", "All");
    private ObservableList<Warehouse> warehouses;
    private ObservableList<StorageItem> storageItems;
    private ObservableList<Item> items;
    private ObservableList<Warehouse> filtered;
    private ObservableList<StorageItem> storageItems2;

    public Controller() {
        this.dao = StorageDAOdb.getInstance();
    }

    @FXML
    public void initialize() {
        warehouses =  FXCollections.observableArrayList(dao.getWarehouses());
        filtered = FXCollections.observableArrayList(warehouses);

        warehouse_tbl.setItems(warehouses);
        warehouse_id_col.setCellValueFactory(new PropertyValueFactory("id"));
        warehouse_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        warehouse_loc_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        storageItems =  FXCollections.observableArrayList(dao.getStorages());
        storage_tbl.setItems(storageItems);
        storage_id_col.setCellValueFactory(new PropertyValueFactory("id"));
        storage_item_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItem().getName()));
        storage_loc_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWarehouse().getName()));
        storage_ppi_col.setCellValueFactory(new PropertyValueFactory("pricePerItem"));
        storage_qyt_col.setCellValueFactory((new PropertyValueFactory("quantity")));
        storage_totprice_col.setCellValueFactory((new PropertyValueFactory("totalPrice")));

        items = FXCollections.observableArrayList(dao.getItems());
        item_list.setItems(items);

        item_list.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (oldValue != null) {
                fld_name.textProperty().unbindBidirectional(oldValue.nameProperty());
                price_fld.textProperty().unbindBidirectional(oldValue.priceProperty());
                desc_textarea.textProperty().unbindBidirectional(oldValue.descriptionProperty());
                weight_fld.textProperty().unbindBidirectional(oldValue.weightProperty());
            }
            if (newValue == null) {
                fld_name.setText("");
                desc_textarea.setText("");
                price_fld.setText("0");
                weight_fld.setText("0");
            } else {
                fld_name.textProperty().bindBidirectional(newValue.nameProperty());
                price_fld.textProperty().bindBidirectional(newValue.priceProperty(), new NumberStringConverter());
                desc_textarea.textProperty().bindBidirectional(newValue.descriptionProperty());
                weight_fld.textProperty().bindBidirectional(newValue.weightProperty(), new NumberStringConverter());
            }
            item_list.refresh();
            storage_tbl.refresh();

        });
            search_fld.textProperty().addListener((observableValue1, s, t1) -> {
                ArrayList<StorageItem> filter = new ArrayList<>();
                for (StorageItem stIt: storageItems) {
                    if(stIt.getItem().getName().toLowerCase().contains(t1.toLowerCase())&& (stIt.getWarehouse().getId() == locations_cb.getSelectionModel().getSelectedItem().getId() || locations_cb.getSelectionModel().getSelectedItem().getId() == -1)){
                        filter.add(stIt);
                    }
                }
                ObservableList<StorageItem> filtered = FXCollections.observableArrayList(filter);
                storage_tbl.setItems(filtered);
                storage_tbl.refresh();
            });


        filtered.add(warehouseAll);

        locations_cb.setItems(filtered);
        locations_cb.getSelectionModel().select(warehouseAll);

        locations_cb.getSelectionModel().selectedItemProperty().addListener((observableValue, warehouse1, t1) -> {
            ArrayList<StorageItem> filter = new ArrayList<>();
            for (StorageItem stIt: storageItems) {
                if(locations_cb.getSelectionModel().getSelectedItem().getId() == -1 || stIt.getWarehouse().getId()==locations_cb.getSelectionModel().getSelectedItem().getId()) {
                    filter.add(stIt);
                }
            }
            storageItems2 = FXCollections.observableArrayList(filter);

            storage_tbl.setItems(storageItems2);
            storage_tbl.refresh();
    });
        fld_name.textProperty().addListener((observableValue, s, t1) -> {
            item_list.getSelectionModel().getSelectedItem().setName(t1);
            dao.editItem(item_list.getSelectionModel().getSelectedItem());
            item_list.refresh();
            storage_tbl.refresh();
        });

        price_fld.textProperty().addListener((observableValue, s, t1) -> {if(items.isEmpty()) return; dao.editItem(item_list.getSelectionModel().getSelectedItem());});
        weight_fld.textProperty().addListener((observableValue, s, t1) -> {if(items.isEmpty()) return; dao.editItem(item_list.getSelectionModel().getSelectedItem());});
        desc_textarea.textProperty().addListener((observableValue, s, t1) -> {if(items.isEmpty()) return; dao.editItem(item_list.getSelectionModel().getSelectedItem());});

    }

    public void warehoseadd_action(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditWarehouse.fxml"));
            addeditControllerWarehouse warehouseController = new addeditControllerWarehouse(null);
            loader.setController(warehouseController);
            root = loader.load();
            stage.setTitle("Warehouse");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> {
                Warehouse warehouse = warehouseController.getWarehouse();
                if (warehouse != null) {
                    dao.addWarehouse(warehouse);
                    warehouses.add(warehouse);
                    filtered.add(warehouse);
                    warehouse_tbl.refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warehouseedit_action(ActionEvent actionEvent) {
        if(warehouse_tbl.getSelectionModel().getSelectedItem() == null) return;
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditWarehouse.fxml"));
            addeditControllerWarehouse warehouseController = new addeditControllerWarehouse(warehouse_tbl.getSelectionModel().getSelectedItem());
            loader.setController(warehouseController);
            root = loader.load();
            stage.setTitle("Warehouse");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> {
                Warehouse warehouse = warehouseController.getWarehouse();
                if (warehouse != null) {
                    dao.editWarehouse(warehouse);
                    filtered.setAll(warehouses);
                    filtered.add(warehouseAll);
                    storage_tbl.refresh();
                    warehouse_tbl.refresh();
                }
            });

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
            dao.deleteStorageByWarehouse(warehouse);
            dao.deleteWarehouse(warehouse);
            filtered.remove(warehouse);
            warehouses.remove(warehouse_tbl.getSelectionModel().getSelectedItem());
            storageItems = FXCollections.observableArrayList(dao.getStorages());
            storage_tbl.setItems(storageItems);
            storage_tbl.refresh();
            warehouse_tbl.refresh();
        }
    }



    public void itemadd_action(ActionEvent actionEvent) {
        items.add(new Item());
        item_list.refresh();
        item_list.getSelectionModel().selectLast();
        dao.addItem(item_list.getSelectionModel().getSelectedItem());
    }

    public void itemdelete_action(ActionEvent actionEvent) {
        if (item_list.getSelectionModel().isEmpty()) return;
        Item item = item_list.getSelectionModel().getSelectedItem();
        if (item == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Deleting item " + item.getName());
        alert.setContentText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteStorageByItem(item);
            dao.deleteItem(item);
            items.removeAll(item);
            storageItems = FXCollections.observableArrayList(dao.getStorages());
            storage_tbl.setItems(storageItems);
            storage_tbl.refresh();
            item_list.refresh();
        }
    }

    public void storageadd_action() {
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditStorage.fxml"));
            addeditControllerStorage storageController = new addeditControllerStorage(null, dao.getItems(), dao.getWarehouses());
            loader.setController(storageController);
            root = loader.load();
            stage.setTitle("Storage");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> {
                StorageItem storageItem = storageController.getStorageItem();
                if (storageItem != null) {
                    dao.addStorage(storageItem);
                    storageItems.add(storageItem);
                    locations_cb.getSelectionModel().selectFirst();
                    locations_cb.getSelectionModel().selectLast();
                    locations_cb.getSelectionModel().select(warehouseAll);
                    storage_tbl.refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void storageedit_action(ActionEvent actionEvent) {
        if (storage_tbl.getSelectionModel().getSelectedItem() == null) return;
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addeditStorage.fxml"));
            addeditControllerStorage storageController = new addeditControllerStorage(storage_tbl.getSelectionModel().getSelectedItem(), dao.getItems(),dao.getWarehouses());
            loader.setController(storageController);
            root = loader.load();
            stage.setTitle("Storage");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> {
                StorageItem storageItem = storageController.getStorageItem();
                if (storageItem != null) {
                    dao.editStorage(storageItem);
                    storage_tbl.refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storagedelete_action(ActionEvent actionEvent) {
        if (storage_tbl.getSelectionModel().isEmpty()) return;
        StorageItem storage = storage_tbl.getSelectionModel().getSelectedItem();
        if (storage == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletiong");
        alert.setHeaderText("Deleting item " + storage.getItem().getName() + " from warehouse " + storage.getWarehouse().getName());
        alert.setContentText("Are you sure you want to delete this item from "+storage.getWarehouse().getName()+" warehouse?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteStorage(storage);
            storageItems.removeAll(storage);
            locations_cb.getSelectionModel().selectFirst();
            locations_cb.getSelectionModel().selectLast();
            locations_cb.getSelectionModel().select(warehouseAll);
            storage_tbl.refresh();
        }
    }

}
