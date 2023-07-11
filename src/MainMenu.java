import api.HotelResource;
import model.reservation.Reservation;
import model.room.IRoom;
import utils.InputUtils;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
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
                    case OPTION_EXIT -> exitApplication();
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
        System.out.println("========== See My Reservations ==========");
        String customerEmail = InputUtils.getEmailInput("Enter your email:");

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
        System.out.println("========== Create an Account ==========");
        String email = InputUtils.getEmailInput("Enter your email:");
        String firstName = InputUtils.getInput("Enter your first name: ", String.class);
        String lastName = InputUtils.getInput("Enter your last name: ", String.class);

        // Call the hotel resource's method to create a customer account
        HotelResource.createACustomer(firstName, lastName, email);

        System.out.println("Account created successfully.");
        System.out.println();
    }

    private static void findAndReserveRoom() {
        System.out.println("========== Find and Reserve a Room ==========");
        Date checkInDate = InputUtils.getDateInput("Enter check-in date (MM/dd/yyyy): ", "MM/dd/yyyy");
        Date checkOutDate = InputUtils.getDateInput("Enter check-out date (MM/dd/yyyy): ", "MM/dd/yyyy");

        // Call the reservation service's method to find available rooms
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms for the specified date range.");
        } else {
            System.out.println("Available rooms:");

            for (IRoom room : availableRooms) {
                System.out.println(room);
            }

            String roomNumber = InputUtils.getInput("Enter the room number to reserve: ", String.class);

            IRoom selectedRoom = HotelResource.getRoom(roomNumber);
            if (selectedRoom == null) {
                System.out.println("Invalid room number.");
            } else {
                String customerEmail = InputUtils.getEmailInput("Enter your email:");

                checkInDate = InputUtils.getDateInput("Enter check-in date (MM/dd/yyyy): ", "MM/dd/yyyy");
                checkOutDate = InputUtils.getDateInput("Enter check-out date (MM/dd/yyyy): ", "MM/dd/yyyy");

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

    private static void exitApplication() {
        System.out.println("Exiting the application...");
        System.exit(0);
    }
}
