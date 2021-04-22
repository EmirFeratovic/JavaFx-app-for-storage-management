package icr.etf.unsa.ba;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditWarehouseController{
    public TextField warehouseNameFld;
    public TextField warehouseLocationFld;
    private Warehouse warehouse;
    public Button warehouseOkBtn;


    public AddEditWarehouseController(Warehouse warehouse) {
        this.warehouse=warehouse;
    }

    public void fieldValidate () {
        if (warehouseLocationFld.getText().trim().isEmpty()) {
            warehouseLocationFld.getStyleClass().add("notvalid");
        }
        else warehouseLocationFld.getStyleClass().removeAll("notvalid");

        if (warehouseNameFld.getText().trim().isEmpty()){
            warehouseNameFld.getStyleClass().add("notvalid");
        }
        else warehouseNameFld.getStyleClass().removeAll("notvalid");
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) warehouseNameFld.getScene().getWindow();
        stage.close();
    }
    public void okAction(ActionEvent actionEvent) {
        if (warehouse==null) {
            warehouse = new Warehouse();
        }
        warehouse.setName(warehouseNameFld.getText());
        warehouse.setLocation(warehouseLocationFld.getText());
        Stage stage = (Stage) warehouseLocationFld.getScene().getWindow();
        stage.close();
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    @FXML
    public void initialize() {
        if(warehouse != null) {
            warehouseNameFld.setText(warehouse.getName());
            warehouseLocationFld.setText(warehouse.getLocation());
        }
        else fieldValidate();
        warehouseNameFld.textProperty().addListener((observableValue, s, t1) -> {
            fieldValidate();
        });
        warehouseLocationFld.textProperty().addListener((observableValue, s, t1) -> {
            fieldValidate();
        });
    }
}
