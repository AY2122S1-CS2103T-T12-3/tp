package seedu.placebook.model.historystates;

import seedu.placebook.model.Contacts;
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
        this.contacts = new Contacts(contacts);
        this.schedule = new Schedule(schedule);
    }

    /**
     * Get the {@Code Contacts} of this state.
     * @return A copy of the {@Code Contacts} of this state.
     */
    public Contacts getContacts() {
        return new Contacts(this.contacts);
    }

    /**
     * Get the {@Code Schedule} of this state.
     * @return A copy of the {@Code Schedule} of this state.
     */
    public Schedule getSchedule() {
        return new Schedule(this.schedule);
    }
}
