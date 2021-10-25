package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

public class Appointment {

    private final UniquePersonList clients;
    private final Address location;
    private LocalDate date;
    private String description;
    private LocalTime time;

    /**
     * Creates an Appointment class with a specified time.
     */
    public Appointment(UniquePersonList clients, Address location, LocalDate date, LocalTime time, String description) {
        this.clients = clients;
        this.location = location;
        this.date = date;
        this.description = description;
        this.time = time;
    }

    public UniquePersonList getClients() {
        return clients;
    }

    public Address getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getTime() {
        return time;
    }

    public ObservableList<Person> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    /**
     * Checks if this appointment is related to the client.
     * @param person the client to check with.
     * @return true if the client is related and false otherwise.
     */
    public boolean hasClient(Person person) {
        return this.clients.contains(person);
    }

    /**
     * Remove a given person from the client list of this {@code Appointment}.
     * @param personToRemove the given person to be removed
     */
    public void removeClient(Person personToRemove) {
        this.clients.remove(personToRemove);
    }

    /**
     * Returns true if both Appointments have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherApp = (Appointment) other;
        return otherApp.getClients().equals(getClients())
                && otherApp.getLocation().equals(getLocation())
                && otherApp.getDate().equals(getDate())
                && otherApp.getDescription().equals(getDescription())
                && otherApp.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clients, location, date, time, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append("; Clients: ")
                .append(getClients())
                .append("; Location: ")
                .append(getLocation())
                .append("; Date: ")
                .append(getDate());

        if (!(getTime() == null)) {
            builder.append("; Time: ")
                    .append(getTime());
        }

        return builder.toString();
    }
}
