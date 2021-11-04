package seedu.placebook.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.Email;
import seedu.placebook.model.person.Name;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.Phone;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.model.schedule.TimePeriod;
import seedu.placebook.model.tag.Tag;

/**
 * Contains utility methods for populating {@code contacts} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    private static UniquePersonList getFromSamplePersons(int... index) {
        UniquePersonList personList = new UniquePersonList();
        for (int i : index) {
            personList.add(getSamplePersons()[i]);
        }
        return personList;
    }

    /**
     * Returns the list of sample appointments
     * This depends on the above getSamplePersons() for the app the work correctly
     *
     * @return list of appointment
     */
    private static Appointment[] getSampleAppointment() {
        LocalDateTime now = LocalDateTime.now();
        TimePeriod urgent =
                new TimePeriod(now.plusHours(2), now.plusHours(4));
        TimePeriod medium1 =
                new TimePeriod(now.plusDays(3), now.plusDays(3).plusHours(2));
        TimePeriod medium2 =
                new TimePeriod(now.plusDays(3).plusHours(3), now.plusDays(3).plusHours(4));
        TimePeriod low1 =
                new TimePeriod(now.plusDays(9), now.plusDays(9).plusHours(2));
        TimePeriod low2 =
                new TimePeriod(now.plusDays(9).plusHours(3), now.plusDays(9).plusHours(4));

        return new Appointment[] {
            new Appointment(getFromSamplePersons(0, 1, 2),
                    new Address("Vivo City"), urgent, "Team meeting"),
            new Appointment(getFromSamplePersons(5),
                    new Address("ABC Office"), medium1, "Developer team meeting"),
            new Appointment(getFromSamplePersons(4),
                    new Address("Zoom"), medium2, "Sales Update"),
            new Appointment(getFromSamplePersons(3, 4),
                    new Address("XYZ Company"), low1, "Project meeting"),
            new Appointment(getFromSamplePersons(2, 4, 5),
                    new Address("XYZ School"), low2, "Talk"),
        };
    }

    public static ReadOnlyContacts getSampleContacts() {
        Contacts sampleAb = new Contacts();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlySchedule getSampleSchedule() {
        Schedule sampleSch = new Schedule();
        for (Appointment sampleAppointment : getSampleAppointment()) {
            sampleSch.addAppointment(sampleAppointment);
        }
        return sampleSch;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
