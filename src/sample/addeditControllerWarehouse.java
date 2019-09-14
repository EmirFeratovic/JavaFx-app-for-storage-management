package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;

public class addeditControllerWarehouse {
    public TextField warehsnameAddEdit_fld;
    public TextField warehslocAddEdit_fld;
    public Button warehouseok_btn;
    public Button warehoucecancel_btn;
    private Warehouse warehouse;
    private ObservableList<Warehouse> warehouses;

    @FXML
    public void initialize() {
        warehsnameAddEdit_fld.setText(warehouse.getName());
        warehslocAddEdit_fld.setText(warehouse.getLocation());
    }

    public addeditControllerWarehouse (Warehouse warehouse) {
        this.warehouse=warehouse;
    }

    public void warehouseCancel_action(ActionEvent actionEvent) {

        Stage stage = (Stage) warehoucecancel_btn.getScene().getWindow();
        stage.close();
    }
    public void warehouseOk_action(ActionEvent actionEvent) {
        if (warehouse==null) {
            warehouse = new Warehouse();
        }
        warehouse.setName(warehsnameAddEdit_fld.getText());
        warehouse.setLocation(warehslocAddEdit_fld.getText());
        Stage stage = (Stage) warehslocAddEdit_fld.getScene().getWindow();
        stage.close();
    }

    
}
