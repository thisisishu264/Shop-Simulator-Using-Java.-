import java.util.*;

public class ShopSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        ArrayList<item> shopInventory = new ArrayList<>();
        shopInventory.add(new books("Atomic Habits", 500.00, 2, "James Clear", "Self-help"));
        shopInventory.add(new books("The Subtle Art Of Not Giving F*ck", 400.00, 2, "Mark Manson", "Self-help"));
        shopInventory.add(new VideoGames("Balatro", 500.00, 2, "Indie", "Local Thunk"));
        shopInventory.add(new VideoGames("Elden Ring", 2000.00, 1, "Souls-Borne", "Fromsoftware"));
        shopInventory.add(new Clothes("T-Shirt", 300.00, 20, "L", "Cotton"));
        shopInventory.add(new Clothes("Jeans", 800.00, 10, "32", "Denim"));
        shopInventory.add(new Cosmetics("Lipstick", 250.00, 30, "Red", "Matte"));
        shopInventory.add(new Cosmetics("Perfume", 1200.00, 5, "Rose", "Floral"));


        HashMap<String, ShoppingList> carts = new HashMap<>();

        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("1. View Shop Inventory");
            System.out.println("2. Create New Shopping Cart");
            System.out.println("3. Access Existing Cart");
            System.out.println("4. Delete Cart");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewShop(shopInventory);
                    break;
                case "2":
                    System.out.print("Enter cart name: ");
                    String cartName = sc.nextLine();
                    carts.put(cartName, new ShoppingList());
                    System.out.println("Cart '" + cartName + "' created!");
                    break;
                case "3":
                    System.out.print("Enter cart name: ");
                    String accessName = sc.nextLine();
                    if (carts.containsKey(accessName)) {
                        cartMenu(sc, carts.get(accessName), shopInventory, carts);
                    } else {
                        System.out.println("Cart not found!");
                    }
                    break;
                case "4":
                    System.out.print("Enter cart name to delete: ");
                    String delName = sc.nextLine();
                    carts.remove(delName);
                    System.out.println("Cart deleted (if it existed).");
                    break;
                case "0":
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    public static void viewShop(ArrayList<item> shop) {
        System.out.println("\nShop Inventory");

        System.out.println("\nBooks:");
        for (item i : shop) {
            if (i instanceof books) i.Details();
        }

        System.out.println("\nVideo Games:");
        for (item i : shop) {
            if (i instanceof VideoGames) i.Details();
        }

        System.out.println("\nClothes:");
        for (item i : shop) {
            if (i instanceof Clothes) i.Details();
        }

        System.out.println("\nCosmetics:");
        for (item i : shop) {
            if (i instanceof Cosmetics) i.Details();
        }
    }

    public static void cartMenu(Scanner sc, ShoppingList cart, ArrayList<item> shop, HashMap<String, ShoppingList> carts) {
        while (true) {
            System.out.println("\n--- Cart Menu ---");
            System.out.println("1. Add item from shop");
            System.out.println("2. Remove item");
            System.out.println("3. View item total");
            System.out.println("4. View cart total");
            System.out.println("5. List all items");
            System.out.println("6. Edit item");
            System.out.println("7. Merge with another cart");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewShop(shop);
                    System.out.print("\nEnter item name: ");
                    String name = sc.nextLine();
                    item selected = null;
                    for (item i : shop) {
                        if (i.getName().equalsIgnoreCase(name)) {
                            selected = i;
                            break;
                        }
                    }
                    if (selected != null) {
                        System.out.print("Enter quantity: ");
                        int qty = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter custom price: ");
                        double price = Double.parseDouble(sc.nextLine());
                        cart.addItem(name, qty, price);
                        System.out.println("Item added to cart!");
                    } else {
                        System.out.println("Item not found!");
                    }
                    break;
                case "2":
                    System.out.print("Enter item to remove: ");
                    cart.removeItem(sc.nextLine());
                    break;
                case "3":
                    System.out.print("Enter item to view total: ");
                    String itemName = sc.nextLine();
                    double total = cart.getItemTotal(itemName);
                    if (total != -1) System.out.println("Total for " + itemName + ": " + total);
                    else System.out.println("Item not found!");
                    break;
                case "4":
                    System.out.println("Cart total: " + cart.getTotal());
                    break;
                case "5":
                    cart.listItems();
                    break;
                case "6":
                    System.out.print("Enter item to edit: ");
                    String editName = sc.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newQty = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter new price: ");
                    double newPrice = Double.parseDouble(sc.nextLine());
                    cart.editItem(editName, newQty, newPrice);
                    break;
                case "7":
                    System.out.print("Enter cart name to merge from: ");
                    String fromCart = sc.nextLine();
                    if (carts.containsKey(fromCart)) {
                        cart.mergeList(carts.get(fromCart));
                        System.out.println("Merged successfully!");
                    } else {
                        System.out.println("Cart not found!");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

class item {
    private String name;
    private double price;
    private int amount;

    public item(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getName() { return this.name; }
    public double getPrice() { return this.price; }
    public int getAmount() { return this.amount; }

    public void Details() {
        System.out.printf("Name: %s | Price: %.2f | Amount: %d\n", name, price, amount);
    }
}

class books extends item {
    private String author;
    private String genre;
    public books(String name, double price, int amount, String author, String genre) {
        super(name, price, amount);
        this.author = author;
        this.genre = genre;
    }
    @Override
    public void Details() {
        System.out.printf("Book: %s by %s | Genre: %s | Price: %.2f | Amount: %d\n", getName(), author, genre, getPrice(), getAmount());
    }
}

class VideoGames extends item {
    private String genre;
    private String developer;
    public VideoGames(String name, double price, int amount, String genre, String developer) {
        super(name, price, amount);
        this.genre = genre;
        this.developer = developer;
    }
    @Override
    public void Details() {
        System.out.printf("Game: %s | Genre: %s | Developer: %s | Price: %.2f | Amount: %d\n", getName(), genre, developer, getPrice(), getAmount());
    }
}

class Clothes extends item {
    private String size;
    private String material;
    public Clothes(String name, double price, int amount, String size, String material) {
        super(name, price, amount);
        this.size = size;
        this.material = material;
    }
    @Override
    public void Details() {
        System.out.printf("Clothing: %s | Size: %s | Material: %s | Price: %.2f | Amount: %d\n", getName(), size, material, getPrice(), getAmount());
    }
}

class Cosmetics extends item {
    private String color;
    private String type;
    public Cosmetics(String name, double price, int amount, String color, String type) {
        super(name, price, amount);
        this.color = color;
        this.type = type;
    }
    @Override
    public void Details() {
        System.out.printf("Cosmetic: %s | Color: %s | Type: %s | Price: %.2f | Amount: %d\n", getName(), color, type, getPrice(), getAmount());
    }
}


class ShoppingList {
    private HashMap<String, ItemDetails> items = new HashMap<>();

    public void addItem(String name, int amount, double price) {
        items.put(name, new ItemDetails(amount, price));
    }

    public void removeItem(String name) {
        if (items.remove(name) != null)
            System.out.println("Item removed.");
        else
            System.out.println("Item not found.");
    }

    public double getItemTotal(String name) {
        if (items.containsKey(name))
            return items.get(name).getAmount() * items.get(name).getPrice();
        return -1;
    }

    public double getTotal() {
        double total = 0;
        for (ItemDetails d : items.values())
            total += d.getAmount() * d.getPrice();
        return total;
    }

    public void listItems() {
        if (items.isEmpty()) {
            System.out.println("No items in cart.");
            return;
        }
        for (Map.Entry<String, ItemDetails> e : items.entrySet())
            System.out.printf("%s | Qty: %d | Price: %.2f\n", e.getKey(), e.getValue().getAmount(), e.getValue().getPrice());
    }

    public void editItem(String name, int amount, double price) {
        if (items.containsKey(name)) {
            items.put(name, new ItemDetails(amount, price));
            System.out.println("Item updated.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void mergeList(ShoppingList other) {
        for (Map.Entry<String, ItemDetails> e : other.items.entrySet())
            items.put(e.getKey(), e.getValue());
    }
}


class ItemDetails {
    private int amount;
    private double price;

    public ItemDetails(int amount, double price) {
        this.amount = amount;
        this.price = price;
    }

    public int getAmount() { return amount; }
    public double getPrice() { return price; }
}
