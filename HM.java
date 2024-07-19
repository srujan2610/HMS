import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Room {
    private String type;
    private double price;
    private boolean isBooked;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
        this.isBooked = false;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        isBooked = true;
    }

    public void releaseRoom() {
        isBooked = false;
    }
}

class Customer {
    private String name;
    private String address;
    private String phone;
    private Room room;
    private ArrayList<String> services;


    public Customer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.room = null;
        this.services = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }

    public void addService(String service) {
        this.services.add(service);
    }

    public String toString() {
        String roomDetails = (room == null) ? "No room booked" : "Room: " + room.getType() + " - $" + room.getPrice() + " per night";
        String serviceDetails = (services.isEmpty()) ? "No services added" : "Services: " + String.join(", ", services);

        return "Name: " + name + "\nAddress: " + address + "\nPhone: " + phone + "\n" + roomDetails + "\n" + serviceDetails;
    }

}

public class HM {
    private static final int MAX_ROOMS = 10;

    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();

        while (true) {
            int choice = displayMenu();

            switch (choice) {
                case 1:
                    bookRoom();
                    break;
                case 2:
                    releaseRoom();
                    break;
                case 3:
                    addService();
                    break;
                case 4:
                    displayCustomers();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room("Single", 50));
        rooms.add(new Room("Double", 75));
        rooms.add(new Room("Suite", 100));
        rooms.add(new Room("Deluxe", 125));
        rooms.add(new Room("Family", 150));
    }

    private static int displayMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Book a room");
        System.out.println("2. Release a room");
        System.out.println("3. Add a service");
        System.out.println("4. Display customer details");
        System.out.println("5. Exit");

        int choice;

        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            choice = 0;
        }

        return choice;
    }

    private static void bookRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer name:");
        String name = scanner.nextLine();

        System.out.println("Enter customer address:");
        String address = scanner.nextLine();

        System.out.println("Enter customer phone number:");
        String phone = scanner.nextLine();

        Customer customer = new Customer(name, address, phone);
        customers.add(customer);

        int roomChoice = displayRoomMenu();

        if (roomChoice == 0) {
            System.out.println("Invalid choice!");
            return;
        }

        Room room = rooms.get(roomChoice - 1);

        if (room.isBooked()) {
            System.out.println("Sorry, the room is already booked.");
        } else {
            room.bookRoom();
            customer.setRoom(room);
            System.out.println("Room booked successfully!");
        }

}

    private static int displayRoomMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Rooms available:");

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            System.out.println((i + 1) + ". " + room.getType() + " - $" + room.getPrice() + " per night");
        }

        int choice;

        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            choice = 0;
        }

        return choice;
    }

    private static void releaseRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the room number to release:");
        int roomNumber = scanner.nextInt();

        Room room = rooms.get(roomNumber - 1);

        if (!room.isBooked()) {
            System.out.println("The room is not currently booked.");
        } else {
            room.releaseRoom();
            System.out.println("Room released successfully!");
        }
    }

    private static void addService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();

        Customer customer = findCustomer(name);

        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Select a service:");
        System.out.println("1. Laundry");
        System.out.println("2. Gym");
        System.out.println("3. Buffet");

        int choice;

        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            choice = 0;
        }

        switch (choice) {
            case 1:
                customer.addService("Laundry");
                System.out.println("Laundry added to the bill.");
                break;
            case 2:
                customer.addService("Gym");
                System.out.println("Gym added to the bill.");
                break;
            case 3:
                customer.addService("Buffet");
                System.out.println("Buffet added to the bill.");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static Customer findCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }

        return null;
    }

    private static void displayCustomers() {
        for (Customer customer : customers) {
            System.out.println(customer);
            System.out.println("----------------------------");
        }
    }
}