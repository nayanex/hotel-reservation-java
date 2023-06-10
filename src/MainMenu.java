import api.HotelResource;
import model.reservation.Reservation;
import model.room.IRoom;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu {
    private static final int OPTION_FIND_AND_RESERVE = 1;
    private static final int OPTION_SEE_MY_RESERVATIONS = 2;
    private static final int OPTION_CREATE_ACCOUNT = 3;
    private static final int OPTION_ADMIN = 4;
    private static final int OPTION_EXIT = 5;

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            try {
                System.out.println("========== Main Menu ==========");
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                System.out.println("===============================");

                System.out.print("Enter your choice (1-5): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case OPTION_FIND_AND_RESERVE ->
                        // Handle the "Find and reserve a room" option
                            findAndReserveRoom();
                    case OPTION_SEE_MY_RESERVATIONS ->
                        // Handle the "See my reservations" option
                            seeMyReservations();
                    case OPTION_CREATE_ACCOUNT ->
                        // Handle the "Create an account" option
                            createAccount();
                    case OPTION_ADMIN ->
                        // Handle the "Admin" option
                            AdminMenu.displayMenu();
                    case OPTION_EXIT -> System.out.println("Exiting the application...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice (1-5).");
                scanner.nextLine(); // Consume the invalid input
                choice = 0; // Set choice to an invalid value to continue the loop
            }
        } while (choice != OPTION_EXIT);
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== See My Reservations ==========");
        System.out.print("Enter your email: ");
        String customerEmail = scanner.nextLine();

        // Call the reservation service's method to get the customer's reservations
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(customerEmail);

        if (reservations.isEmpty()) {
            System.out.println("You have no reservations.");
        } else {
            System.out.println("Your reservations:");

            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }

        System.out.println();
    }

    private static void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== Create an Account ==========");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Call the hotel resource's method to create a customer account
        HotelResource.createACustomer(firstName, lastName, email);

        System.out.println("Account created successfully.");
        System.out.println();
    }

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== Find and Reserve a Room ==========");
        System.out.print("Enter check-in date (MM/dd/yyyy): ");
        Date checkInDate = parseDate(scanner.nextLine());

        System.out.print("Enter check-out date (MM/dd/yyyy): ");
        Date checkOutDate = parseDate(scanner.nextLine());

        // Call the reservation service's method to find available rooms
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms for the specified date range.");
        } else {
            System.out.println("Available rooms:");

            for (IRoom room : availableRooms) {
                System.out.println(room);
            }

            System.out.print("Enter the room number to reserve: ");
            String roomNumber = scanner.nextLine();

            IRoom selectedRoom = HotelResource.getRoom(roomNumber);
            if (selectedRoom == null) {
                System.out.println("Invalid room number.");
            } else {
                System.out.print("Enter your email: ");
                String customerEmail = scanner.nextLine();
                System.out.print("Enter check-in date (MM/dd/yyyy): ");
                checkInDate = parseDate(scanner.nextLine());

                System.out.print("Enter check-out date (MM/dd/yyyy): ");
                checkOutDate = parseDate(scanner.nextLine());

                // Call the reservation service's method to reserve the room
                Reservation reservation = HotelResource.bookARoom(customerEmail, selectedRoom, checkInDate, checkOutDate);

                if (reservation != null) {
                    System.out.println("Room reserved successfully. Reservation details:");
                    System.out.println(reservation);
                }
            }
        }

        System.out.println();
    }

    private static Date parseDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            return null;
        }
    }

}
