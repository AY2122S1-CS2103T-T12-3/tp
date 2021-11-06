package seedu.placebook.model.historystates;

import java.util.function.Predicate;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.Schedule;

/**
 * A State describes what the PlaceBook is like after executing a certain command.
 */
public class State {
    /** The {@Code Contacts} of the PlaceBook. */
    private final Contacts contacts;

    /** The {@Code Schedule} of the PlaceBook. */
    private final Schedule schedule;

    private final Predicate<? super Person> personListPredicate;

    private final Predicate<? super Appointment> appointmentListPredicate;

    private final String commandName;

    /**
     * A constructor to initialize the {@Code State} with the given {@Code Contacts} and {@Code Schedule}.
     * The given {@Code Contacts} and {@Code Schedule} will be copied and store in this {@Code State}.
     * @param contacts
     * @param schedule
     * @param personListPredicate
     * @param appointmentListPredicate
     */
    public State(Contacts contacts, Schedule schedule,
                 Predicate<? super Person> personListPredicate,
                 Predicate<? super Appointment> appointmentListPredicate,
                 String commandName) {
        this.contacts = Contacts.deepCopy(contacts);
        this.schedule = Schedule.deepCopy(schedule);
        this.personListPredicate = personListPredicate;
        this.appointmentListPredicate = appointmentListPredicate;
        this.commandName = commandName;
    }

    /**
     * Get the {@Code Contacts} of this state.
     * @return A deep copy of the {@Code Contacts} of this state.
     */
    public Contacts getContacts() {
        return Contacts.deepCopy(this.contacts);
    }

    /**
     * Get the {@Code Schedule} of this state.
     * @return A deep copy of the {@Code Schedule} of this state.
     */
    public Schedule getSchedule() {
        return Schedule.deepCopy(this.schedule);
    }

    public Predicate<? super Person> getPersonListPredicate() {
        return this.personListPredicate;
    }

    public Predicate<? super Appointment> getAppointmentListPredicate() {
        return this.appointmentListPredicate;
    }

    public String getCommandName() {
        return this.commandName;
    }
}
