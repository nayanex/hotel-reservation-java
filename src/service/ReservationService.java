package service;

import model.customer.Customer;
import model.room.IRoom;
import model.reservation.Reservation;

import java.util.*;
public class ReservationService {
    private static final int CONFLICT_RESERVATION_PLUS_DAYS = 7;
    private static final ReservationService instance = new ReservationService();
    private final List<Reservation> reservations = new ArrayList<>();
    private final Map<String, IRoom> rooms = new HashMap<>();

    private ReservationService() {
        // Private constructor to prevent instantiation outside the class
    }

    public static ReservationService getInstance() {
        return instance;
    }

    public void addRoom(IRoom room) {
        // Logic to add a room to the reservation system
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        // Logic to retrieve a specific room from the reservation system
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }

        return customerReservations;
    }

    public void printAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("All Reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (isRoomReserved(room, checkInDate, checkOutDate)) {
            // Room is already reserved, find recommended rooms on alternative dates
            Collection<IRoom> recommendedRooms = findRecommendedRooms(checkInDate, checkOutDate);

            if (recommendedRooms.isEmpty()) {
                // No recommended rooms available
                System.out.println("No available rooms for the specified date range or recommended dates.");
            } else {
                // Display recommended rooms and dates to the customer
                System.out.println("Recommended rooms for alternative dates:");
                for (IRoom recommendedRoom : recommendedRooms) {
                    System.out.println(recommendedRoom);
                }
            }
            return null;
        } else {
            // Create a new reservation
            Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservations.add(reservation);
            return reservation;
        }
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> availableRooms = findAvailableRooms(checkInDate, checkOutDate);

        // If no available rooms, perform recommended room search
        if (availableRooms.isEmpty()) {
            Date recommendedCheckInDate = addDays(checkInDate, CONFLICT_RESERVATION_PLUS_DAYS);
            Date recommendedCheckOutDate = addDays(checkOutDate, CONFLICT_RESERVATION_PLUS_DAYS);
            availableRooms = findAvailableRooms(recommendedCheckInDate, recommendedCheckOutDate);
        }

        return availableRooms;
    }

    private Set<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> availableRooms = new HashSet<>();

        for (IRoom room : rooms.values()) {
            if (!isRoomReserved(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    private Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate) {
        Date recommendedCheckInDate = addDays(checkInDate, CONFLICT_RESERVATION_PLUS_DAYS);
        Date recommendedCheckOutDate = addDays(checkOutDate, CONFLICT_RESERVATION_PLUS_DAYS);
        return findAvailableRooms(recommendedCheckInDate, recommendedCheckOutDate);
    }

    private boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
        // Check if the room is already reserved for the given date range
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room) &&
                    !checkOutDate.before(reservation.getCheckInDate()) &&
                    !checkInDate.after(reservation.getCheckOutDate())) {
                return true; // Room is already reserved for the specified date range
            }
        }
        return false; // Room is available
    }

    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}
