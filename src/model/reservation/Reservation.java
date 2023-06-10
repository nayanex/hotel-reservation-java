package model.reservation;

import model.customer.Customer;
import model.room.IRoom;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Reservation other = (Reservation) obj;
        return Objects.equals(customer, other.customer)
                && Objects.equals(room, other.room)
                && Objects.equals(checkInDate, other.checkInDate)
                && Objects.equals(checkOutDate, other.checkOutDate);
    }

    @Override
    public String toString() {
        return "Reservation details:\n"
                + "Customer: " + customer.getFirstName() + " " + customer.getLastName() + "\n"
                + "Room: " + room.toString() + "\n"
                + "Check-in date: " + checkInDate.toString() + "\n"
                + "Check-out date: " + checkOutDate.toString();
    }
}