package icr.etf.unsa.ba;

import java.util.ArrayList;

public interface StorageDAO {

    Item getItem (int id);
    Warehouse getWarehouse(int id);
    StorageItem getStorage(int id);

    ArrayList<Item> getItems ();
    ArrayList<Warehouse> getWarehouses ();
    ArrayList<StorageItem> getStorages ();

    void addItem (Item item);
    void addWarehouse(Warehouse warehouse);
    void addStorage(StorageItem storage);

    void editItem (Item item);
    void editWarehouse(Warehouse warehouse);
    void editStorage(StorageItem storage);

    void deleteItem (Item item);
    void deleteWarehouse(Warehouse warehouse);
    void deleteStorage(StorageItem storage);

    void close ();



}
