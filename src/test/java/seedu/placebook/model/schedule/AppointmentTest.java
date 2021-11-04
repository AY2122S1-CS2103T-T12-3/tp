package seedu.placebook.model.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.placebook.testutil.TypicalAppointment.ALICE_APPOINTMENT;
import static seedu.placebook.testutil.TypicalPersons.ALICE;
import static seedu.placebook.testutil.TypicalPersons.BENSON;
import static seedu.placebook.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.person.exceptions.DuplicatePersonException;
import seedu.placebook.model.person.exceptions.PersonNotFoundException;
import seedu.placebook.testutil.PersonBuilder;

public class AppointmentTest {
    private static final LocalDateTime TEST_MOMENT1 =
            LocalDateTime.of(2021, 01, 01, 00, 00);
    private static final LocalDateTime TEST_MOMENT2 =
            LocalDateTime.of(2021, 01, 01, 23, 59);
    private static final LocalDateTime TEST_MOMENT3 =
            LocalDateTime.of(2021, 01, 02, 00, 00);
    private static final LocalDateTime TEST_MOMENT4 =
            LocalDateTime.of(2021, 01, 02, 23, 59);

    private static UniquePersonList testClientList1;
    private static UniquePersonList testClientList2;

    private static final Address TEST_ADDRESS1 = new Address("vivocity");
    private static final Address TEST_ADDRESS2 = new Address("utown");

    private static final TimePeriod TEST_TIME_PERIOD1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
    private static final TimePeriod TEST_TIME_PERIOD2 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);

    private static final String TEST_DESCRIPTION1 = "Team meeting";
    private static final String TEST_DESCRIPTION2 = "project demo";

    @BeforeEach
     public void setUp() {
        testClientList1 = new UniquePersonList();
        testClientList1.add(ALICE);
        testClientList1.add(BENSON);

        testClientList2 = new UniquePersonList();
        testClientList2.add(ALICE);
        testClientList2.add(BOB);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(ALICE_APPOINTMENT.equals(ALICE_APPOINTMENT));

        // null -> returns false
        assertFalse(ALICE_APPOINTMENT.equals(null));

        UniquePersonList testClients = new UniquePersonList();
        testClients.add(ALICE);

        // same attributes, different object
        Appointment similarAliceAppointment = new Appointment(testClients,
                new Address("369 Tanjong Rhu"),
                new TimePeriod(LocalDateTime.of(2021, 12, 25, 10, 00),
                        LocalDateTime.of(2021, 12, 25, 11, 00)),
                "Talk about sales");
        assertTrue(ALICE_APPOINTMENT.equals(similarAliceAppointment));

        // same client, different attributes
        Appointment editedAliceAppointment = new Appointment(testClients,
                new Address("369 Geylang Street"),
                new TimePeriod(LocalDateTime.of(2021, 12, 25, 21, 30),
                        LocalDateTime.of(2021, 12, 25, 22, 30)),
                "Talk about sales");
        assertFalse(ALICE_APPOINTMENT.equals(editedAliceAppointment));
    }

    @Test
    public void getClients_returnsCorrectClientList() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertEquals(appointment.getClients(), testClientList1);
        assertNotEquals(appointment.getClientList(), testClientList2);
    }

    @Test
    public void getLocation_returnsCorrectLocation() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertEquals(appointment.getLocation(), TEST_ADDRESS1);
        assertNotEquals(appointment.getLocation(), TEST_ADDRESS2);
    }

    @Test
    public void getTimePeriod_returnsCorrectTimePeriod() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertEquals(appointment.getTimePeriod(), TEST_TIME_PERIOD1);
        assertEquals(appointment.getTimePeriod(), new TimePeriod(TEST_MOMENT1, TEST_MOMENT2));
        assertNotEquals(appointment.getTimePeriod(), TEST_TIME_PERIOD2);
    }

    @Test
    public void getDescription_returnsCorrectDescription() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertEquals(appointment.getDescription(), TEST_DESCRIPTION1);
        assertEquals(appointment.getDescription(), "Team meeting");
    }

    @Test
    public void getClientList_returnsCorrectClientList() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertEquals(appointment.getClientList(), testClientList1.asUnmodifiableObservableList());
        assertNotEquals(appointment.getClientList(), testClientList2.asUnmodifiableObservableList());
    }

    @Test
    public void getStartDateTimeString_returnsCorrectStringRepresentation() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertEquals(appointment.getStartDateTimeString(), "01-01-2021 0000");

        Appointment appointment2 = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD2, TEST_DESCRIPTION1);
        assertEquals(appointment2.getStartDateTimeString(), TEST_TIME_PERIOD2.getStartDateTimeAsString());
    }

    @Test
    public void getEndDateTimeString_returnsCorrectStringRepresentation() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertEquals(appointment.getEndDateTimeString(), "01-01-2021 2359");

        Appointment appointment2 = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD2, TEST_DESCRIPTION1);
        assertEquals(appointment2.getEndDateTimeString(), TEST_TIME_PERIOD2.getEndDateTimeAsString());
    }

    @Test
    public void getUrgency_appointmentOccursInTwoDaysCase1_returnsHighUrgency() {
        TimePeriod timePeriod = new TimePeriod(
                LocalDateTime.now().plusDays(2).minusMinutes(1),
                LocalDateTime.now().plusDays(2).plusHours(1));
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS2, timePeriod, TEST_DESCRIPTION1);
        assertEquals(appointment.getUrgency(), Urgency.HIGH);
    }

    @Test
    public void getUrgency_appointmentOccursWithinTwoToEightDaysCase1_returnsMediumUrgency() {
        TimePeriod timePeriod = new TimePeriod(
                LocalDateTime.now().plusDays(2).plusMinutes(1),
                LocalDateTime.now().plusDays(8).plusHours(1));
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod, TEST_DESCRIPTION1);
        assertEquals(appointment.getUrgency(), Urgency.MEDIUM);
    }

    @Test
    public void getUrgency_appointmentOccursWithinTwoToEightDaysCase2_returnsMediumUrgency() {
        TimePeriod timePeriod = new TimePeriod(
                LocalDateTime.now().plusDays(8).minusMinutes(1),
                LocalDateTime.now().plusDays(8).plusHours(1));
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod, TEST_DESCRIPTION1);
        assertEquals(appointment.getUrgency(), Urgency.MEDIUM);
    }

    @Test
    public void getUrgency_appointmentOccursAfterEightDaysCase1_returnsLowUrgency() {
        TimePeriod timePeriod = new TimePeriod(
                LocalDateTime.now().plusDays(8).plusMinutes(1),
                LocalDateTime.now().plusDays(8).plusHours(1));
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod, TEST_DESCRIPTION1);
        assertEquals(appointment.getUrgency(), Urgency.LOW);
    }

    @Test
    public void getUrgency_appointmentOccursAfterEightDaysCase2_returnsLowUrgency() {
        TimePeriod timePeriod = new TimePeriod(
                LocalDateTime.now().plusDays(100),
                LocalDateTime.now().plusDays(100).plusHours(1));
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod, TEST_DESCRIPTION1);
        assertEquals(appointment.getUrgency(), Urgency.LOW);
    }

    @Test
    public void hasConflictWith_twoAppointmentsWithoutConflict_returnsFalse() {
        TimePeriod timePeriod1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        TimePeriod timePeriod2 = new TimePeriod(TEST_MOMENT3, TEST_MOMENT4);
        Appointment appointment1 = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod1, TEST_DESCRIPTION1);
        Appointment appointment2 = new Appointment(testClientList2, TEST_ADDRESS2, timePeriod2, TEST_DESCRIPTION2);
        assertFalse(appointment1.isConflictingWith(appointment2));
        assertFalse(appointment2.isConflictingWith(appointment1));
    }

    @Test
    public void hasConflictWith_twoAppointmentsWithConflictCase1_returnsTrue() {
        TimePeriod timePeriod1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        TimePeriod timePeriod2 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT4);
        Appointment appointment1 = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod1, TEST_DESCRIPTION1);
        Appointment appointment2 = new Appointment(testClientList2, TEST_ADDRESS2, timePeriod2, TEST_DESCRIPTION2);
        assertTrue(appointment1.isConflictingWith(appointment2));
        assertTrue(appointment2.isConflictingWith(appointment1));
    }

    @Test
    public void hasConflictWith_twoAppointmentsWithConflictCase2_returnsTrue() {
        TimePeriod timePeriod1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        TimePeriod timePeriod2 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        Appointment appointment1 = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod1, TEST_DESCRIPTION1);
        Appointment appointment2 = new Appointment(testClientList2, TEST_ADDRESS2, timePeriod2, TEST_DESCRIPTION2);
        assertTrue(appointment1.isConflictingWith(appointment2));
        assertTrue(appointment2.isConflictingWith(appointment1));
    }

    @Test
    public void hasConflictWith_twoAppointmentsWithConflictCase3_returnsTrue() {
        TimePeriod timePeriod1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT4);
        TimePeriod timePeriod2 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        Appointment appointment1 = new Appointment(testClientList1, TEST_ADDRESS1, timePeriod1, TEST_DESCRIPTION1);
        Appointment appointment2 = new Appointment(testClientList2, TEST_ADDRESS2, timePeriod2, TEST_DESCRIPTION2);
        assertTrue(appointment1.isConflictingWith(appointment2));
        assertTrue(appointment2.isConflictingWith(appointment1));
    }

    @Test
    public void hasClient_clientInClientList_returnsTrue() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertTrue(appointment.hasClient(ALICE));
        assertTrue(appointment.hasClient(BENSON));
    }

    @Test
    public void hasClient_clientNotInClientList_returnsFalse() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertFalse(appointment.hasClient(BOB));
    }

    @Test
    public void isClientListEmpty_emptyClientList_returnsTrue() {
        Appointment appointment = new Appointment(new UniquePersonList(), TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertTrue(appointment.isClientListEmpty());
    }

    @Test
    public void isClientListEmpty_clientListNotEmpty_returnsFalse() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertFalse(appointment.isClientListEmpty());
    }

    @Test
    public void removeClient_clientInClientList_clientRemoved() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        appointment.removeClient(ALICE);
        assertFalse(appointment.hasClient(ALICE));
        assertFalse(testClientList1.contains(ALICE));
        assertTrue(testClientList1.contains(BENSON));
    }

    @Test
    public void removeClient_clientNotInClientList_exceptionThrown() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertThrows(PersonNotFoundException.class, () -> appointment.removeClient(BOB));
        assertTrue(appointment.hasClient(ALICE));
        assertTrue(appointment.hasClient(BENSON));
    }

    @Test
    public void addClient_clientNotInClientList_clientAdded() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        appointment.addClient(BOB);
        assertTrue(appointment.hasClient(ALICE));
        assertTrue(appointment.hasClient(BENSON));
        assertTrue(appointment.hasClient(BOB));
        assertTrue(testClientList1.contains(ALICE));
        assertTrue(testClientList1.contains(BENSON));
        assertTrue(testClientList1.contains(BOB));
    }

    @Test
    public void addClient_duplicateClient_exceptionThrown() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1,
                TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertThrows(DuplicatePersonException.class, () -> appointment.addClient(ALICE));
        assertThrows(DuplicatePersonException.class, () -> appointment.addClient(BENSON));
        assertTrue(appointment.hasClient(ALICE));
        assertTrue(appointment.hasClient(BENSON));
        assertFalse(appointment.hasClient(BOB));
    }

    @Test
    public void setClient_validInput_success() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        appointment.setClient(ALICE, BOB);
        UniquePersonList clientList = appointment.getClients();
        assertFalse(clientList.contains(ALICE));
        assertTrue(clientList.contains(BOB));
        assertTrue(clientList.contains(BENSON));
    }

    @Test
    public void setClient_duplicateClientSameReference_exceptionThrown() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertThrows(DuplicatePersonException.class, () -> appointment.setClient(ALICE, BENSON));
    }

    @Test
    public void setClient_duplicateClientSameAttributesDifferentReference_exceptionThrown() {
        Person editedPerson = new PersonBuilder(BENSON).build();
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertThrows(DuplicatePersonException.class, () -> appointment.setClient(ALICE, editedPerson));
    }

    @Test
    public void setClient_targetPersonNotInClientList_exceptionThrown() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertThrows(PersonNotFoundException.class, () -> appointment.setClient(BOB, BOB));
    }

    @Test
    public void isTheOnlyClient_onlyClient_returnsTrue() {
        UniquePersonList clientList = new UniquePersonList();
        clientList.add(ALICE);
        Appointment appointment = new Appointment(clientList, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertTrue(appointment.isTheOnlyClient(ALICE));
    }

    @Test
    public void isTheOnlyClient_clientListContainsTargetAndOtherClients_returnsFalse() {
        Appointment appointment = new Appointment(testClientList1, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertFalse(appointment.isTheOnlyClient(ALICE));
        assertFalse(appointment.isTheOnlyClient(BENSON));
    }

    @Test
    public void isTheOnlyClient_targetNotInClientList_returnsFalse() {
        UniquePersonList clientList = new UniquePersonList();
        clientList.add(ALICE);
        Appointment appointment = new Appointment(clientList, TEST_ADDRESS1, TEST_TIME_PERIOD1, TEST_DESCRIPTION1);
        assertFalse(appointment.isTheOnlyClient(BENSON));
        assertFalse(appointment.isTheOnlyClient(BOB));
    }
}
