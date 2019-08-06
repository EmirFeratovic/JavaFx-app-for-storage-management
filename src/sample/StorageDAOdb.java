package sample;

import java.util.ArrayList;
import java.sql.*;

public class StorageDAOdb implements StorageDAO {

    private static StorageDAOdb instance=null;
    private Connection connection;
    private PreparedStatement getItemStm;
    private PreparedStatement getItemsStm;
    private PreparedStatement getWarehouseStm;
    private PreparedStatement getWarehousesStm;
    private PreparedStatement getStorageStm;
    private PreparedStatement getStoragesStm;
    private PreparedStatement editItemStm;
    private PreparedStatement editWarehouseStm;
    private PreparedStatement editStorageStm;
    private PreparedStatement addItemStm;
    private PreparedStatement addWarehouseStm;
    private PreparedStatement addStorageStm;
    private PreparedStatement deleteItemStm;
    private PreparedStatement deleteWarehouseStm;
    private PreparedStatement deleteStorageStm;




    @Override
    public Item getItem(int id) {
        return null;
    }

    @Override
    public Warehouse getWarehouse(int id) {
        return null;
    }

    @Override
    public StorageItem getStorage(int id) {
        return null;
    }

    @Override
    public ArrayList<Item> getItems() {
        return null;
    }

    @Override
    public ArrayList<Warehouse> getWarehouses() {
        return null;
    }

    @Override
    public ArrayList<StorageItem> getStorages() {
        return null;
    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void addWarehouse(Warehouse warehouse) {

    }

    @Override
    public void addStorage(StorageItem storage) {

    }

    @Override
    public void editItem(Item item) {

    }

    @Override
    public void editWarehouse(Warehouse warehouse) {

    }

    @Override
    public void editStorage(StorageItem storage) {

    }

    @Override
    public void deleteItem(Item item) {

    }

    @Override
    public void deleteWarehouse(Warehouse warehouse) {

    }

    @Override
    public void deleteStorage(StorageItem storage) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static StorageDAOdb getInstance() {
        if(instance==null) instance = new StorageDAOdb();
        return instance;
    }

    public static void removeInstance () {
        if (instance==null) return;
        instance.close();
        instance=null;
    }

}
