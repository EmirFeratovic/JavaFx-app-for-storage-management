package rs_projekat;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class AllTests {
    Stage mainStage;
    Controller controller;
    StorageDAOdb dao = StorageDAOdb.getInstance();

    @Start
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        stage.setTitle("Storage manager");
        stage.setScene(new Scene(root, 800, 600));
        stage.setResizable(false);
        stage.show();
        mainStage = stage;
    }

    @Test
    public void testAddWarehouse(FxRobot robot) {
        int numOfWarehouses = dao.getWarehouses().size();
        robot.clickOn("#warehouseAddBtn");
        robot.clickOn("#warehouseNameFld");
        robot.write("Test");
        robot.clickOn("#warehouseLocationFld");
        robot.write("LocationTest");
        robot.clickOn("#warehouseOkBtn");

        ArrayList<Warehouse> warehouses = dao.getWarehouses();
        assertEquals(numOfWarehouses+1, warehouses.size());
    }

    @Test
    public void testDeleteWarehouse (FxRobot robot) {
        int numOfWarehouses = dao.getWarehouses().size();
        robot.clickOn("#warehouseTbl");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#warehouseDeleteBtn");
        robot.clickOn("OK");
        ArrayList<Warehouse> warehouses = dao.getWarehouses();
        assertEquals(numOfWarehouses-1,warehouses.size());
    }

    @Test
    public void testEditWarehouse (FxRobot robot) {
        robot.clickOn("#warehouseTbl");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#warehouseEditBtn");
        robot.doubleClickOn("#warehouseNameFld");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("WarehouseNameTest");
        robot.doubleClickOn("#warehouseLocationFld");
        robot.clickOn("#warehouseLocationFld");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("WarehouseLocationTest");
        robot.clickOn("#warehouseOkBtn");
        Warehouse warehouse = dao.getWarehouses().get(0);
        assertEquals("WarehouseNameTest",warehouse.getName());

    }

    @Test
    public void testAddStorage(FxRobot robot) {
        robot.clickOn("#storageTab");
        robot.clickOn("#storageAddBtn");
        robot.clickOn("#warehouseChoiceBox");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#itemChoiceBox");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#quantityFld");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("9");
        robot.clickOn("#storageOkBtn");
        ArrayList<StorageItem> storages = dao.getStorages();
        StorageItem storageItem = storages.get(storages.size()-1);
        assertEquals(9, storageItem.getQuantity());
    }

    @Test
    public void testDeleteStorage (FxRobot robot) {
        int numOfStorages = dao.getStorages().size();
        robot.clickOn("#storageTab");
        robot.clickOn("#storageTbl");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#storageDeleteBtn");
        robot.clickOn("OK");
        ArrayList<StorageItem> storages = dao.getStorages();
        assertEquals(numOfStorages-1,storages.size());
    }

    @Test
    public void  testEditStorage (FxRobot robot) {
        robot.clickOn("#storageTab");
        robot.clickOn("#storageTbl");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#storageEditBtn");
        robot.clickOn("#warehouseChoiceBox");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#itemChoiceBox");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.doubleClickOn("#quantityFld");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("12121");
        robot.clickOn("#storageOkBtn");
        StorageItem storage = dao.getStorages().get(0);
        assertEquals("12121",String.valueOf(storage.getQuantity()));
    }

    @Test
    public void testAddItem(FxRobot robot) {
        int numOfItems = dao.getItems().size();
        robot.clickOn("#itemTab");
        robot.clickOn("#itemAddBtn");
        robot.clickOn("#itemNameFld");
        robot.write("ItemNameTest");
        robot.doubleClickOn("#itemWeightFld");
        robot.write("0.5");
        robot.doubleClickOn("#itemPriceFld");
        robot.write("155");
        robot.clickOn("#itemDescTextarea");
        robot.write("This item is for testing purposes");
        ArrayList<Item> items = dao.getItems();
        assertEquals(numOfItems+1,items.size());
    }

    @Test
    public void testDeleteItem (FxRobot robot) {
        int numOfItems = dao.getItems().size();
        robot.clickOn("#itemTab");
        robot.clickOn("#itemList");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#itemDeleteBtn");
        robot.clickOn("OK");
        assertEquals(numOfItems-1,dao.getItems().size());
    }

    @Test
    public void testEditItem (FxRobot robot) {
        robot.clickOn("#itemTab");
        robot.clickOn("#itemList");
        robot.type(KeyCode.ENTER);
        robot.doubleClickOn("#itemNameFld");
        robot.clickOn("#itemNameFld");
        robot.write("EditItemNameTest");
        robot.doubleClickOn("#itemWeightFld");
        robot.write("1");
        robot.doubleClickOn("#itemPriceFld");
        robot.write("100");
        robot.doubleClickOn("#itemDescTextarea");
        robot.clickOn("#itemDescTextarea");
        robot.write("This item is for testing purposes");
        Item item = dao.getItems().get(0);
        assertEquals("EditItemNameTest",item.getName());
    }

    @Test
    public void testAddWarehouseBase(){
        int numOfWarehouses = dao.getWarehouses().size();
        Warehouse warehouse= new Warehouse();
        warehouse.setId(dao.nextWarehouseId());
        warehouse.setName("WarehouseTest");
        warehouse.setLocation("WarehouseTest");
        dao.addWarehouse(warehouse);
        assertEquals(dao.getWarehouses().get(numOfWarehouses).getId(),warehouse.getId());
    }

    @Test
    public void testDeleteWarehouseBase(){
        int numOfWarehouses = dao.getWarehouses().size();
        Warehouse warehouse= new Warehouse();
        warehouse.setId(dao.nextWarehouseId());
        warehouse.setName("WarehouseTest");
        warehouse.setLocation("WarehouseTest");
        dao.addWarehouse(warehouse);
        dao.deleteWarehouse(warehouse);
        assertEquals(numOfWarehouses,dao.getWarehouses().size());
    }

    @Test
    public void testEditWarehouseBase() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("WarehouseTest");
        warehouse.setLocation("WarehouseTest");
        dao.editWarehouse(warehouse);
        assertEquals("WarehouseTest", dao.getWarehouse(1).getName());
    }

    @Test
    public void testAddStorageBase() {
        int numOfStorageItem = dao.getStorages().size();
        StorageItem storageItem = new StorageItem();
        storageItem.setItem(dao.getItems().get(0));
        storageItem.setWarehouse(dao.getWarehouses().get(0));
        storageItem.setPricePerItem(100);
        storageItem.setQuantity(50);
        storageItem.setTotalPrice(5000);
        dao.addStorage(storageItem);
        assertEquals(dao.getItems().get(0).getId(), storageItem.getItem().getId());

        //todo delete warehouse,storage,item
        //todo edit warehouse,storage,item
        //todo add item
        //todo delete,edit,add from base warehouse,item,storage
    }

    @Test
    public void testDeleteStorageBase(){

    }



}


