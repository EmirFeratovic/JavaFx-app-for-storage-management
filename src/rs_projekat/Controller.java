package rs_projekat;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;


public class Controller {

    public TableView<Warehouse> warehouseTbl;
    public TableColumn warehouseIdCol;
    public TableColumn<Warehouse, String> warehouseLocCol;
    public TableColumn<Warehouse, String> warehouseNameCol;
    public Tab warehouseTab;
    public Button warehouseAddBtn;
    public Button warehouseEditBtn;
    public Button warehouseDeleteBtn;
    public Tab storageTab;
    public TextField searchFld;
    public ChoiceBox<Warehouse> locationsChoiceBox;
    public Button storageAddBtn;
    public Button storageEditBtn;
    public Button storageDeleteBtn;
    public TableView <StorageItem> storageTbl;
    public TableColumn <StorageItem , String> storageIdCol;
    public TableColumn <StorageItem , String> storageLocCol;
    public TableColumn <StorageItem , String> storageItemCol;
    public TableColumn <StorageItem , String> storageQytCol;
    public TableColumn <StorageItem , String> storagePpiCol;
    public TableColumn <StorageItem , String> storageTotalCol;
    public Tab itemTab;
    public ListView<Item> itemList;
    public TextField itemIdFld;
    public TextField itemNameFld;
    public TextField itemPriceFld;
    public TextField itemWeightFld;
    public TextArea itemDescTextarea;
    public Button itemAddBtn;
    public Button itemDeleteBtn;
    public Button reportGenerateBtn;
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
        locationsChoiceBox.setTooltip(new Tooltip("Filter by warehouse"));
        itemAddBtn.setTooltip(new Tooltip("Add new item"));
        itemDeleteBtn.setTooltip(new Tooltip("Delete selected item"));
        storageAddBtn.setTooltip(new Tooltip("Add new record"));
        storageDeleteBtn.setTooltip(new Tooltip("Delete selected record"));
        storageEditBtn.setTooltip(new Tooltip("Edit selected record"));
        warehouseAddBtn.setTooltip(new Tooltip("Add new warehouse"));
        warehouseDeleteBtn.setTooltip(new Tooltip("Delete selected warehouse"));
        warehouseEditBtn.setTooltip(new Tooltip("Edit selected warehouse"));
        reportGenerateBtn.setTooltip(new Tooltip("Generate storage report"));
        warehouses =  FXCollections.observableArrayList(dao.getWarehouses());
        filtered = FXCollections.observableArrayList(warehouses);

        warehouseTbl.setItems(warehouses);
        warehouseIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        warehouseNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        warehouseLocCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        storageItems =  FXCollections.observableArrayList(dao.getStorages());
        storageTbl.setItems(storageItems);
        storageIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        storageItemCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItem().getName()));
        storageLocCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWarehouse().getName()));
        storagePpiCol.setCellValueFactory(new PropertyValueFactory("pricePerItem"));
        storageQytCol.setCellValueFactory((new PropertyValueFactory("quantity")));
        storageTotalCol.setCellValueFactory((new PropertyValueFactory("totalPrice")));

        items = FXCollections.observableArrayList(dao.getItems());
        itemList.setItems(items);

        itemList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (oldValue != null) {
                itemNameFld.textProperty().unbindBidirectional(oldValue.nameProperty());
                itemPriceFld.textProperty().unbindBidirectional(oldValue.priceProperty());
                itemDescTextarea.textProperty().unbindBidirectional(oldValue.descriptionProperty());
                itemWeightFld.textProperty().unbindBidirectional(oldValue.weightProperty());
            }
            if (newValue == null) {
                itemNameFld.setText("");
                itemDescTextarea.setText("");
                itemPriceFld.setText("0");
                itemWeightFld.setText("0");
            } else {
                itemNameFld.textProperty().bindBidirectional(newValue.nameProperty());
                itemPriceFld.textProperty().bindBidirectional(newValue.priceProperty(), new NumberStringConverter());
                itemDescTextarea.textProperty().bindBidirectional(newValue.descriptionProperty());
                itemWeightFld.textProperty().bindBidirectional(newValue.weightProperty(), new NumberStringConverter());
            }
            itemList.refresh();
            storageTbl.refresh();

        });
            searchFld.textProperty().addListener((observableValue1, s, t1) -> {
                ArrayList<StorageItem> filter = new ArrayList<>();
                for (StorageItem stIt: storageItems) {
                    if(stIt.getItem().getName().toLowerCase().contains(t1.toLowerCase())&& (stIt.getWarehouse().getId() == locationsChoiceBox.getSelectionModel().getSelectedItem().getId() || locationsChoiceBox.getSelectionModel().getSelectedItem().getId() == -1)){
                        filter.add(stIt);
                    }
                }
                ObservableList<StorageItem> filtered = FXCollections.observableArrayList(filter);
                storageTbl.setItems(filtered);
                storageTbl.refresh();
            });


        filtered.add(warehouseAll);

        locationsChoiceBox.setItems(filtered);
        locationsChoiceBox.getSelectionModel().select(warehouseAll);

        locationsChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, warehouse1, t1) -> {
            ArrayList<StorageItem> filter = new ArrayList<>();
            for (StorageItem stIt: storageItems) {
                if(locationsChoiceBox.getSelectionModel().getSelectedItem().getId() == -1 || stIt.getWarehouse().getId()== locationsChoiceBox.getSelectionModel().getSelectedItem().getId()) {
                    filter.add(stIt);
                }
            }
            storageItems2 = FXCollections.observableArrayList(filter);

            storageTbl.setItems(storageItems2);
            storageTbl.refresh();
    });
        itemNameFld.textProperty().addListener((observableValue, s, t1) -> {
            fieldValidate();
            itemList.getSelectionModel().getSelectedItem().setName(t1);
            for (StorageItem stIt: storageItems) {
                if(stIt.getItem().getId() == itemList.getSelectionModel().getSelectedItem().getId()){
                    stIt.getItem().setName(t1);
                }
            }
            storageTbl.refresh();
            locationsChoiceBox.getSelectionModel().selectFirst();
            locationsChoiceBox.getSelectionModel().select(warehouseAll);
            dao.editItem(itemList.getSelectionModel().getSelectedItem());
            itemList.refresh();
            storageTbl.refresh();
        });

        itemPriceFld.textProperty().addListener((observableValue, s, t1) -> { fieldValidate(); if(items.isEmpty()) return; itemList.getSelectionModel().getSelectedItem().setPrice(Double.parseDouble(t1));dao.editItem(itemList.getSelectionModel().getSelectedItem()); storageTbl.setItems(FXCollections.observableArrayList(dao.getStorages()));
            storageTbl.refresh();});
        itemWeightFld.textProperty().addListener((observableValue, s, t1) -> {fieldValidate(); if(items.isEmpty()) return; dao.editItem(itemList.getSelectionModel().getSelectedItem());});
        itemDescTextarea.textProperty().addListener((observableValue, s, t1) -> {fieldValidate(); if(items.isEmpty()) return; dao.editItem(itemList.getSelectionModel().getSelectedItem());});

    }

    public void warehoseadd_action(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEditWarehouse.fxml"));
            AddEditWarehouseController warehouseController = new AddEditWarehouseController(null);
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
                    warehouseTbl.refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fieldValidate () {
        if (itemNameFld.getText().trim().isEmpty()) {
            itemNameFld.getStyleClass().add("notvalid");
        }
        else itemNameFld.getStyleClass().removeAll("notvalid");

        if (itemPriceFld.getText().trim().isEmpty()){
            itemPriceFld.getStyleClass().add("notvalid");
        }
        else itemPriceFld.getStyleClass().removeAll("notvalid");

        if (itemWeightFld.getText().trim().isEmpty()){
            itemWeightFld.getStyleClass().add("notvalid");
        }
        else itemWeightFld.getStyleClass().removeAll("notvalid");

        if (itemDescTextarea.getText().trim().isEmpty()){
            itemDescTextarea.getStyleClass().add("notvalid");
        }
        else itemDescTextarea.getStyleClass().removeAll("notvalid");

    }


    public void warehouseedit_action(ActionEvent actionEvent) {
        if(warehouseTbl.getSelectionModel().getSelectedItem() == null) return;
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEditWarehouse.fxml"));
            AddEditWarehouseController warehouseController = new AddEditWarehouseController(warehouseTbl.getSelectionModel().getSelectedItem());
            loader.setController(warehouseController);
            root = loader.load();
            stage.setTitle("Warehouse");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> {
                Warehouse warehouse = warehouseController.getWarehouse();
                if (warehouse != null) {
                    for (StorageItem stIt: storageItems ){
                        if(stIt.getWarehouse().getId() == warehouse.getId()) stIt.getWarehouse().setName(warehouse.getName());
                    }
                    dao.editWarehouse(warehouse);
                    filtered.setAll(warehouses);
                    filtered.add(warehouseAll);
                    storageTbl.refresh();
                    warehouseTbl.refresh();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warehousedelete_action(ActionEvent actionEvent) {
        if (warehouseTbl.getSelectionModel().isEmpty()) return;
        Warehouse warehouse = warehouseTbl.getSelectionModel().getSelectedItem();
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
            warehouses.remove(warehouseTbl.getSelectionModel().getSelectedItem());
            storageItems = FXCollections.observableArrayList(dao.getStorages());
            storageTbl.setItems(storageItems);
            storageTbl.refresh();
            warehouseTbl.refresh();
        }
    }


    public void itemadd_action(ActionEvent actionEvent) {
        items.add(new Item());
        itemList.refresh();
        itemList.getSelectionModel().selectLast();
        dao.addItem(itemList.getSelectionModel().getSelectedItem());
    }

    public void itemdelete_action(ActionEvent actionEvent) {
        if (itemList.getSelectionModel().isEmpty()) return;
        Item item = itemList.getSelectionModel().getSelectedItem();
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
            storageTbl.setItems(storageItems);
            storageTbl.refresh();
            itemList.refresh();
        }
    }

    public void storageadd_action() {
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEditStorage.fxml"));
            AddEditStorageController storageController = new AddEditStorageController(null, dao.getItems(), dao.getWarehouses());
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
                    locationsChoiceBox.getSelectionModel().selectFirst();
                    locationsChoiceBox.getSelectionModel().selectLast();
                    locationsChoiceBox.getSelectionModel().select(warehouseAll);
                    storageTbl.refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void storageedit_action(ActionEvent actionEvent) {
        if (storageTbl.getSelectionModel().getSelectedItem() == null) return;
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEditStorage.fxml"));
            AddEditStorageController storageController = new AddEditStorageController(storageTbl.getSelectionModel().getSelectedItem(), dao.getItems(),dao.getWarehouses());
            loader.setController(storageController);
            root = loader.load();
            stage.setTitle("Storage");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> {
                StorageItem storageItem = storageController.getStorageItem();
                if (storageItem != null) {
                    dao.editStorage(storageItem);
                    storageTbl.refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storagedelete_action(ActionEvent actionEvent) {
        if (storageTbl.getSelectionModel().isEmpty()) return;
        StorageItem storage = storageTbl.getSelectionModel().getSelectedItem();
        if (storage == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletiong");
        alert.setHeaderText("Deleting item " + storage.getItem().getName() + " from warehouse " + storage.getWarehouse().getName());
        alert.setContentText("Are you sure you want to delete this item from "+storage.getWarehouse().getName()+" warehouse?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteStorage(storage);
            storageItems.removeAll(storage);
            locationsChoiceBox.getSelectionModel().selectFirst();
            locationsChoiceBox.getSelectionModel().selectLast();
            locationsChoiceBox.getSelectionModel().select(warehouseAll);
            storageTbl.refresh();
        }
    }


    public void generateReportAction() {
        if(dao.getStorages().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Report not generated");
            alert.setHeaderText("No records in table");
            alert.showAndWait();
            return;
        }
        PrintWriter output = null;
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Report");
        File defaultDirectory = new File(System.getProperty("user.home"));
        chooser.setInitialDirectory(defaultDirectory);
        File file = chooser.showSaveDialog(new Stage());
        try {
            FileWriter writer = new FileWriter(file, false);
            output = new PrintWriter(writer);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Report not generated");
            alert.setHeaderText("Report could not be generated at " + file.getAbsolutePath());
            alert.showAndWait();
            e.printStackTrace();
        }

        if(output != null) {
            output.println(String.format("%3s", "#") + " " + String.format("%-25s" , "Warehouse") + " " +
                    String.format("%-35s", "Item") + " " + String.format("%-8s", "Quantity") + " " + String.format("%-14s", "Price per item")
            + " " + String.format("%-15s", "Total price"));
            for (StorageItem storageItem : dao.getStorages()) {
                output.println(String.format("%3d", storageItem.getId()) + " " + String.format("%-25s" , storageItem.getWarehouse().toString()) + " " +
                        String.format("%-35s", storageItem.getItem().getName()) + " " + String.format("%-8d", storageItem.getQuantity()) + " " + String.format("%-14.2f", storageItem.getPricePerItem())
                        + " " + String.format("%-15.2f", storageItem.getTotalPrice()));
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report generated");
            alert.setHeaderText("Report generated at " + file.getAbsolutePath());
            alert.showAndWait();
            output.close();
        }
    }
}
