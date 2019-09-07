package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

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
    public TableView storage_tbl;
    public TableColumn storage_id_col;
    public TableColumn storage_loc_col;
    public TableColumn storage_item_col;
    public TableColumn storage_qyt_col;
    public TableColumn storage_ppi_col;
    public TableColumn storage_totprice_col;
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


    @FXML
    public void initialize() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setLocation("Sarajevo");
        warehouse.setName("SaTrade");
        ArrayList<Warehouse> arrayList = new ArrayList<>();
        arrayList.add(warehouse);
        ObservableList<Warehouse> warehouses =  FXCollections.observableArrayList(arrayList);

        warehouse_tbl.setItems(warehouses);
        warehouse_id_col.setCellValueFactory(new PropertyValueFactory("id"));
      //  warehouse_loc_col.setCellValueFactory(new PropertyValueFactory("warehouseLocation"));
       // warehouse_name_col.setCellValueFactory(new PropertyValueFactory("warehouseName"));
        warehouse_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        warehouse_loc_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));
    }



}
