import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import model.customer.Customer;
import model.room.IRoom;
import api.AdminResource;
import model.room.Room;
import model.room.enums.RoomType;

public class AdminMenu {
    private static final int OPTION_SEE_ALL_CUSTOMERS = 1;
    private static final int OPTION_SEE_ALL_ROOMS = 2;
    private static final int OPTION_SEE_ALL_RESERVATIONS = 3;
    private static final int OPTION_ADD_ROOM = 4;
    private static final int OPTION_BACK_TO_MAIN_MENU = 5;

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nAdmin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            System.out.print("Enter your choice (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case OPTION_SEE_ALL_CUSTOMERS -> seeAllCustomers();
                case OPTION_SEE_ALL_ROOMS -> seeAllRooms();
                case OPTION_SEE_ALL_RESERVATIONS -> seeAllReservations();
                case OPTION_ADD_ROOM -> addRoom();
                case OPTION_BACK_TO_MAIN_MENU -> MainMenu.displayMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != OPTION_BACK_TO_MAIN_MENU);
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = AdminResource.getAllCustomers();
        System.out.println("\nAll Customers:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        System.out.println("\nAll Rooms:");
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    private static void seeAllReservations() {
        AdminResource.displayAllReservations();
    }

    private static void addRoom() {
        Scanner scanner = new Scanner(System.in);
        List<IRoom> rooms = new ArrayList<>();

        System.out.println("Enter the number of rooms to add:");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.println("Enter room number:");
            String roomNumber = scanner.nextLine();

            System.out.println("Enter room price:");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter room type (SINGLE/DOUBLE):");
            String roomTypeString = scanner.nextLine();
            RoomType roomType = RoomType.valueOf(roomTypeString);

            IRoom room = new Room(roomNumber, price, roomType);
            rooms.add(room);
        }

        AdminResource.addRoom(rooms);

        System.out.println("Rooms added successfully.");
    }
}
