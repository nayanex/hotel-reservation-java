import java.util.*;

import model.customer.Customer;
import model.room.IRoom;
import api.AdminResource;
import model.room.Room;
import model.room.enums.RoomType;
import utils.InputUtils;

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
            try {
                System.out.println("\nAdmin Menu");
                System.out.println("1. See all Customers");
                System.out.println("2. See all Rooms");
                System.out.println("3. See all Reservations");
                System.out.println("4. Add a Room");
                System.out.println("5. Back to Main Menu");
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
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice (1-5).");
                scanner.nextLine(); // Consume the invalid input
                choice = 0; // Set choice to an invalid value to continue the loop
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

        int roomCount = InputUtils.getInput("Enter the number of rooms to add:", Integer.class);
        System.out.println("Number of rooms: " + roomCount);


        for (int i = 0; i < roomCount; i++) {
            String roomNumber = InputUtils.getInput("Enter room number:", String.class);
            double price = InputUtils.getInput("Enter room price:", Double.class);
            RoomType roomType = InputUtils.getInput("Enter the room type (SINGLE or DOUBLE):", RoomType.class);

            IRoom room = new Room(roomNumber, price, roomType);
            rooms.add(room);
        }

        AdminResource.addRoom(rooms);

        System.out.println("Rooms added successfully.");
    }
}
