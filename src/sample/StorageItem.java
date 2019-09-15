package sample;

public class StorageItem {
    private StorageDAOdb dao = StorageDAOdb.getInstance();
    private int id,quantity;
    private Warehouse warehouse;
    private double pricePerItem,totalPrice;
    private Item item;

    public StorageItem() {
        this.id = dao.nextStorageId();
    }

    public StorageItem(int id, Warehouse warehouse, int quantity, double pricePerItem, double totalPrice, Item item) {
        this.id = id;
        this.warehouse = warehouse;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalPrice = totalPrice;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
