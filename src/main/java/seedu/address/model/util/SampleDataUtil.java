package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.schedule.Appointment;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.TimePeriod;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
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
        TimePeriod urgent =
                new TimePeriod(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4));
        TimePeriod medium =
                new TimePeriod(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(2));
        TimePeriod low =
                new TimePeriod(LocalDateTime.now().plusDays(9), LocalDateTime.now().plusDays(9).plusHours(2));

        return new Appointment[] {
            new Appointment(getFromSamplePersons(0, 1, 2),
                    new Address("Vivo City"), urgent, "Team meeting"),
            new Appointment(getFromSamplePersons(5),
                    new Address("ABC Office"), medium, "Team meeting"),
            new Appointment(getFromSamplePersons(4),
                    new Address("Zoom"), medium, "Sales Update"),
            new Appointment(getFromSamplePersons(3, 4),
                    new Address("XYZ Company"), low, "Project meeting"),
            new Appointment(getFromSamplePersons(2, 4, 5),
                    new Address("XYZ School"), low, "Talk"),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
