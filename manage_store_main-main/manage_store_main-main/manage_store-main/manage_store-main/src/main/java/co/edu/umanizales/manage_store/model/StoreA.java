package co.edu.umanizales.manage_store.model;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class StoreA {
    public class Store {
        private int id;
        private String name;
        private List<Item> soldItems;

        public Store(int id, String name) {
            this.id = id;
            this.name = name;
            this.soldItems = new ArrayList<>();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void addItem(Item item) {
            for (Item i : soldItems) {
                if (i.getId() == item.getId()) {
                    i.setQuantity(i.getQuantity() + item.getQuantity());
                    return;
                }
            }
            soldItems.add(item);
        }

        public List<Item> getSoldItems(int quantity) {
            List<Item> items = new ArrayList<>();
            for (Item i : soldItems) {
                if (i.getQuantity() > quantity) {
                    items.add(i);
                }
            }
            return items;
        }

        public class Item {

            private int id;
            private String name;
            private String description;
            private int quantity;


            public Item(int id, String name, String description, int quantity) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.quantity = quantity;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }


            public String getDescription() {
                return description;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public class Main {
                public void main(String[] args) {
                    List<Store> stores = new LinkedList<>();
                    Store store1 = new Store(1, "EBay");
                    stores.add(store1);

                    Item item1 = new Item(2, "Jheferson", "Ingeniero de sistemas", 20);
                    store1.addItem(item1);
                    Item item2 = new Item(2, "Jose", "vendedor de almojabanas", 10);
                    store1.addItem(item2);

                    List<Item> soldItems = store1.getSoldItems(5);
                    System.out.println("Stores with sold items greater than 5:");
                    for (Store store : stores) {
                        List<Item> items = store.getSoldItems(5);
                        if (!items.isEmpty()) {
                            System.out.println("Store ID: " + store.getId() + ", Name: " + store.getName());
                            for (Item item : items) {
                                System.out.println("Item ID: " + item.getId() + ", Name: " + item.getName() +
                                        ", Quantity: " + item.getQuantity());
                            }
                        }
                    }


                }
            }
        }
    }
}



