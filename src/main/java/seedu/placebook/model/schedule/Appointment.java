package seedu.placebook.model.schedule;

import java.time.LocalDateTime;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.UniquePersonList;

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

    /**
     * A factory method to create a deep copy of the given appointment to copy.
     * @param appointmentToCopy The given appointment to copy.
     * @return The deep copy.
     */
    public static Appointment deepCopy(Appointment appointmentToCopy) {
        Appointment result = new Appointment(
                UniquePersonList.deepCopy(appointmentToCopy.getClients()),
                appointmentToCopy.getLocation(),
                appointmentToCopy.getTimePeriod(),
                appointmentToCopy.getDescription()
        );
        return result;
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

    public LocalDateTime getStart() {
        return this.getTimePeriod().getStartDateTime();
    }

    public LocalDateTime getEnd() {
        return this.getTimePeriod().getEndDateTime();
    }

    /**
     * Creates a string representation of the start timing.
     */
    public String getStartDateTimeString() {
        return this.timePeriod.getStartDateTimeAsString();
    }

    /**
     * Creates a string representation of the end timing.
     */
    public String getEndDateTimeString() {
        return this.timePeriod.getEndDateTimeAsString();
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
     * Checks if this Appointment is conflicting with the given Appointment.
     * @param appointment the appointment to check against.
     * @return true if there is clash in time, false otherwise.
     */
    public boolean isConflictingWith(Appointment appointment) {
        return this.timePeriod.hasConflictWith(appointment.getTimePeriod());
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
     * Sets the Client in the client list to the Edited Client.
     *
     * @param personToEdit the client to be edited.
     * @param editedPerson the edited client.
     */
    public void setClient(Person personToEdit, Person editedPerson) {
        clients.setPerson(personToEdit, editedPerson);
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


}
