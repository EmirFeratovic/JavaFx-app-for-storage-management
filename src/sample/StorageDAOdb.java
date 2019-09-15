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


    private StorageDAOdb() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            getItemsStm = connection.prepareStatement("SELECT * FROM items");
            getItemStm = connection.prepareStatement("SELECT  * FROM items WHERE id = ?");
            getWarehouseStm = connection.prepareStatement("SELECT  * FROM warehouses WHERE id = ?");
            getWarehousesStm = connection.prepareStatement("SELECT * FROM warehouses");
            getStoragesStm = connection.prepareStatement("SELECT * FROM storage");
            getStorageStm = connection.prepareStatement("SELECT * FROM storage WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private StorageItem getStorageItemFromResultSet(ResultSet rs, Warehouse warehouse, Item item) throws SQLException {
        return new StorageItem(rs.getInt(1), warehouse, rs.getInt(4), rs.getDouble(5), rs.getDouble(6), item);
    }

    @Override
    public ArrayList<StorageItem> getStorages() {
        ArrayList<StorageItem> result = new ArrayList();
        try {
            ResultSet rs = getStoragesStm.executeQuery();
            while (rs.next()) {
                Warehouse warehouse = getWarehouse(rs.getInt(2));
                Item item = getItem(rs.getInt(3));
                StorageItem storageItem = getStorageItemFromResultSet(rs, warehouse, item);
                result.add(storageItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Warehouse getWarehouseFromResultSet(ResultSet rs) throws SQLException {
        return new Warehouse(rs.getInt(1), rs.getString(2), rs.getString(3));
    }

    @Override
    public Warehouse getWarehouse(int id) {
        try {
            getWarehouseStm.setInt(1, id);
            ResultSet rs = getWarehouseStm.executeQuery();
            if (!rs.next()) return null;
            return getWarehouseFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public StorageItem getStorage(int id) {
        return null;
    }

    private Item getItemFromResultSet(ResultSet rs) throws SQLException {
        return new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5));
    }
    @Override
    public Item getItem(int id) {
        try {
            getItemStm.setInt(1, id);
            ResultSet rs = getItemStm.executeQuery();
            if (!rs.next()) return null;
            return getItemFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Item> getItems() {
        ArrayList<Item> result = new ArrayList();
        try {
            ResultSet rs = getItemsStm.executeQuery();
            while (rs.next()) {
                Item item = getItem(rs.getInt(1));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Warehouse> getWarehouses() {
        ArrayList<Warehouse> result = new ArrayList<>();
        try {
            ResultSet rs = getWarehousesStm.executeQuery();
            while (rs.next()){
                Warehouse warehouse = getWarehouse(rs.getInt(1));
                result.add(warehouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
