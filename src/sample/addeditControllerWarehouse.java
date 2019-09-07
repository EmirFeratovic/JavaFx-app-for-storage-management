package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addeditControllerWarehouse {
    public TextField warehsnameAddEdit_fld;
    public TextField warehslocAddEdit_fld;
    public Button warehouseok_btn;
    public Button warehoucecancel_btn;

    public void warehouseOk_action(ActionEvent actionEvent) {

    }

    public void warehouseCancel_action(ActionEvent actionEvent) {

        Stage stage = (Stage) warehoucecancel_btn.getScene().getWindow();
        stage.close();
    }
}
