package seedu.placebook.model.historystates;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.schedule.Schedule;

public class State {
    private Contacts contacts;
    private Schedule schedule;

    /**
     * A constructor to initialize the {@Code State} with the given {@Code contacts} and {@Code Schedule}.
     * The given {@Code contacts} and {@Code Schedule} will be copied and store in this {@Code State}.
     * @param contacts The given {@Code contacts}.
     * @param schedule The given {@Code Schedule}.
     */
    public State(Contacts contacts, Schedule schedule) {
        this.contacts = new Contacts(contacts);
        this.schedule = new Schedule(schedule);
    }

    public Contacts getContacts() {
        return new Contacts(this.contacts);
    }

    public Schedule getSchedule() {
        return new Schedule(this.schedule);
    }
}
