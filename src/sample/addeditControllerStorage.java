package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class addeditControllerStorage {
    public Button storagecancel_btn;
    public TextField storageloc_fld;
    public TextField storageitem_fld;
    public TextField storageqty_fld;
    public TextField storagepricepi_fld;
    public TextField storagetotprice_fld;
    public Button storageok_btn;
    private StorageItem storageItem;


    public void storagecancle_action () {
        Stage stage = (Stage) storagecancel_btn.getScene().getWindow();
        stage.close();
    }


    public void storageok_action() {

    }
}
