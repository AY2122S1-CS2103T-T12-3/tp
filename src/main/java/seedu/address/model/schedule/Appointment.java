package seedu.address.model.schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

public class Appointment {

    private final UniquePersonList clients;
    private final Address location;
    private final TimePeriod timePeriod;
    private String description;


    /**
     * Creates an Appointment class with a specified time.
     */
    public Appointment(UniquePersonList clients, Address location, TimePeriod timePeriod, String description) {
        this.clients = clients;
        this.location = location;
        this.timePeriod = timePeriod;
        this.description = description;
    }

    public UniquePersonList getClients() {
        return clients;
    }

    public Address getLocation() {
        return location;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public String getDescription() {
        return description;
    }

    public ObservableList<Person> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    /**
     * Creates a string representation of the start timing.
     */
    public String getStartDateTimeString() {
        return this.timePeriod.getStartDateTimeString();
    }

    /**
     * Creates a string representation of the end timing.
     */
    public String getEndDateTimeString() {
        return this.timePeriod.getEndDateTimeString();
    }

    /**
     * Returns the urgency of this appointment.
     * Returns HIGH if appointment is in 2 days, MEDIUM if it is in a week and LOW otherwise
     * @return the urgency of this appointment.
     */
    public Urgency getUrgency() {
        if (LocalDateTime.now().plusDays(2).isAfter(timePeriod.getStartDateTime())) {
            return Urgency.HIGH;
        } else if (LocalDateTime.now().plusDays(8).isAfter(timePeriod.getStartDateTime())) {
            return Urgency.MEDIUM;
        } else {
            return Urgency.LOW;
        }
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
     * Checks whether this {@Code Appointment}'s client list is empty.
     * @return A boolean value indicating whether the client list is empty.
     */
    public boolean isClientListEmpty() {
        return this.clients.isEmpty();
    }

    /**
     * Remove a given person from the client list of this {@code Appointment}.
     * @param personToRemove the given person to be removed.
     */
    public void removeClient(Person personToRemove) {
        this.clients.remove(personToRemove);
    }

    /**
     * Add a given person to the client list of this {@Code Appointment}.
     * @param personToAdd the given person to be added.
     */
    public void addClient(Person personToAdd) {
        this.clients.add(personToAdd);
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
                && otherApp.getTimePeriod().equals(getTimePeriod())
                && otherApp.getDescription().equals(getDescription());
    }

    /**
     * Checks whether the given client is the only client in the client list of this {@Code Appointment}.
     * @param client the given client.
     * @return A boolean value indicating whether the given client is the only client.
     */
    public boolean isTheOnlyClient(Person client) {
        return this.hasClient(client) && this.getClientList().size() == 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clients, location, timePeriod, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append("; Clients: ")
                .append(getClients())
                .append("; Location: ")
                .append(getLocation())
                .append("; Time Period: ")
                .append(getTimePeriod());

        return builder.toString();
    }

    public boolean isSameAppointment(Appointment editedAppointment) {
        return this.equals(editedAppointment);
    }

}
