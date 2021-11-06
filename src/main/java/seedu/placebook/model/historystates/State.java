package seedu.placebook.model.historystates;

import java.util.List;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.UniquePersonList;
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

    /**
     * A constructor to initialize the {@Code State} with the given {@Code Contacts} and {@Code Schedule}.
     * The given {@Code Contacts} and {@Code Schedule} will be copied and store in this {@Code State}.
     * @param contacts The given {@Code contacts}.
     * @param schedule The given {@Code Schedule}.
     */
    public State(Contacts contacts, Schedule schedule) {
        this.contacts = new Contacts();
        List<Person> personList = contacts.getPersonList();
        for (Person personToCopy : personList) {
            this.contacts.addPerson(
                    new Person(
                            personToCopy.getName(),
                            personToCopy.getPhone(),
                            personToCopy.getEmail(),
                            personToCopy.getAddress(),
                            personToCopy.getTags()
                    ));
        }

        this.schedule = new Schedule();
        List<Appointment> appointmentList = schedule.getSchedule();
        for (Appointment appointmentToCopy : appointmentList) {
            UniquePersonList clientList = new UniquePersonList();
            for (Person clientToCopy : appointmentToCopy.getClients()) {
                clientList.add(
                        new Person(
                                clientToCopy.getName(),
                                clientToCopy.getPhone(),
                                clientToCopy.getEmail(),
                                clientToCopy.getAddress(),
                                clientToCopy.getTags()
                        ));
            }
            this.schedule.addAppointment(
                    new Appointment(
                            clientList,
                            appointmentToCopy.getLocation(),
                            appointmentToCopy.getTimePeriod(),
                            appointmentToCopy.getDescription()
                    ));
        }
    }

    /**
     * Get the {@Code Contacts} of this state.
     * @return A deep copy of the {@Code Contacts} of this state.
     */
    public Contacts getContacts() {
        Contacts result = new Contacts();
        for (Person personToCopy : this.contacts.getPersonList()) {
            result.addPerson(
                    new Person(
                            personToCopy.getName(),
                            personToCopy.getPhone(),
                            personToCopy.getEmail(),
                            personToCopy.getAddress(),
                            personToCopy.getTags()
                    )
            );
        }
        return result;
    }

    /**
     * Get the {@Code Schedule} of this state.
     * @return A deep copy of the {@Code Schedule} of this state.
     */
    public Schedule getSchedule() {
        Schedule result = new Schedule();
        List<Appointment> appointmentList = schedule.getSchedule();
        for (Appointment appointmentToCopy : appointmentList) {
            UniquePersonList clientList = new UniquePersonList();
            for (Person clientToCopy : appointmentToCopy.getClients()) {
                clientList.add(
                        new Person(
                                clientToCopy.getName(),
                                clientToCopy.getPhone(),
                                clientToCopy.getEmail(),
                                clientToCopy.getAddress(),
                                clientToCopy.getTags()
                        ));
            }
            result.addAppointment(
                    new Appointment(
                            clientList,
                            appointmentToCopy.getLocation(),
                            appointmentToCopy.getTimePeriod(),
                            appointmentToCopy.getDescription()
                    ));
        }
        return result;
    }
}
