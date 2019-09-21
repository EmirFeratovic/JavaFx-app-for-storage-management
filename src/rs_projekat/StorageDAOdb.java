package rs_projekat;

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
    private PreparedStatement nextWarehouseIdStm;
    private PreparedStatement nextItemIdStm;
    private PreparedStatement nextStorageIdStm;
    private PreparedStatement deleteStorageByItemStm;
    private PreparedStatement deleteStorageByWarehouseStm;


    private StorageDAOdb() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            getItemsStm = connection.prepareStatement("SELECT * FROM items");
            getItemStm = connection.prepareStatement("SELECT  * FROM items WHERE id = ?");
            getWarehouseStm = connection.prepareStatement("SELECT  * FROM warehouses WHERE id = ?");
            getWarehousesStm = connection.prepareStatement("SELECT * FROM warehouses");
            getStoragesStm = connection.prepareStatement("SELECT * FROM storage");
            getStorageStm = connection.prepareStatement("SELECT * FROM storage WHERE id = ?");
            nextWarehouseIdStm = connection.prepareStatement("SELECT MAX(id) + 1 FROM warehouses");
            addWarehouseStm = connection.prepareStatement("INSERT INTO warehouses VALUES (?,?,?)");
            editWarehouseStm = connection.prepareStatement("UPDATE warehouses SET name = ?, location = ? WHERE  id = ?");
            deleteWarehouseStm = connection.prepareStatement("DELETE FROM warehouses WHERE id = ?");
            nextItemIdStm = connection.prepareStatement("SELECT MAX(id) + 1 FROM items");
            nextStorageIdStm = connection.prepareStatement("SELECT MAX(id) + 1 FROM storage");
            deleteItemStm =connection.prepareStatement("DELETE FROM items WHERE id = ?");
            editItemStm = connection.prepareStatement("UPDATE items SET name = ?, description = ?, price = ?, weight = ? WHERE  id = ?");
            addItemStm = connection.prepareStatement("INSERT INTO items VALUES (?,?,?,?,?)");
            deleteStorageStm = connection.prepareStatement("DELETE FROM storage WHERE id = ?");
            addStorageStm = connection.prepareStatement("INSERT INTO storage VALUES (?,?,?,?)");
            deleteStorageByItemStm = connection.prepareStatement("DELETE FROM storage WHERE item = ?");
            deleteStorageByWarehouseStm = connection.prepareStatement("DELETE FROM storage WHERE warehouse = ?");
            editStorageStm = connection.prepareStatement("UPDATE storage SET warehouse = ?, item = ?, quantity = ? WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private StorageItem getStorageItemFromResultSet(ResultSet rs, Warehouse warehouse, Item item) throws SQLException {
        return new StorageItem(rs.getInt(1), warehouse, rs.getInt(4), item.getPrice(), item.getPrice()*rs.getInt(4), item);
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

    public int nextWarehouseId() {
        ResultSet rs;
        int id = 1;
        try {
            rs = nextWarehouseIdStm.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
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
        try {
            ResultSet rs = nextItemIdStm.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addItemStm.setInt(1, id);
            addItemStm.setString(2, item.getName());
            addItemStm.setString(3, item.getDescription());
            addItemStm.setDouble(4, item.getPrice());
            addItemStm.setDouble(5, item.getWeight());
            addItemStm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addWarehouse(Warehouse warehouse) {
        try {
            ResultSet rs = nextWarehouseIdStm.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addWarehouseStm.setInt(1, id);
            addWarehouseStm.setString(2, warehouse.getLocation());
            addWarehouseStm.setString(3, warehouse.getName());
            addWarehouseStm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStorage(StorageItem storage) {
        try {
            ResultSet rs = nextStorageIdStm.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addStorageStm.setInt(1, id);
            addStorageStm.setInt(2, storage.getWarehouse().getId());
            addStorageStm.setInt(3, storage.getItem().getId());
            addStorageStm.setInt(4, storage.getQuantity());
            addStorageStm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editItem(Item item) {
        try {
            editItemStm.setString(1, item.getName());
            editItemStm.setString(2, item.getDescription());
            editItemStm.setDouble(3, item.getPrice());
            editItemStm.setDouble(4, item.getWeight());
            editItemStm.setInt(5, item.getId());
            editItemStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editWarehouse(Warehouse warehouse) {
        try {
            editWarehouseStm.setString(1, warehouse.getName());
            editWarehouseStm.setString(2, warehouse.getLocation());
            editWarehouseStm.setInt(3, warehouse.getId());
            editWarehouseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editStorage(StorageItem storage) {
        try {
            editStorageStm.setInt(1, storage.getWarehouse().getId());
            editStorageStm.setInt(2, storage.getItem().getId());
            editStorageStm.setInt(3, storage.getQuantity());
            editStorageStm.setInt(4, storage.getId());
            editStorageStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(Item item) {
        try {
            deleteItemStm.setInt(1, item.getId());
            deleteItemStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWarehouse(Warehouse warehouse) {
            try {
                deleteWarehouseStm.setInt(1, warehouse.getId());
                deleteWarehouseStm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void deleteStorage(StorageItem storage) {
        try {
            deleteStorageStm.setInt(1, storage.getId());
            deleteStorageStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public int nextItemId() {
        ResultSet rs;
        int id = 1;
        try {
            rs = nextItemIdStm.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int nextStorageId() {
        ResultSet rs;
        int id = 1;
        try {
            rs = nextStorageIdStm.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void deleteStorageByItem(Item item) {
        try {
            deleteStorageByItemStm.setInt(1, item.getId());
            deleteStorageByItemStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStorageByWarehouse(Warehouse warehouse) {
        try {
            deleteStorageByWarehouseStm.setInt(1, warehouse.getId());
            deleteStorageByWarehouseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
