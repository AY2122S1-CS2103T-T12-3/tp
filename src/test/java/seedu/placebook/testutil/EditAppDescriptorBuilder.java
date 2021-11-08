package seedu.placebook.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.placebook.logic.commands.EditAppCommand;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.schedule.Appointment;

public class EditAppDescriptorBuilder {

    private EditAppCommand.EditAppDescriptor descriptor;

    public EditAppDescriptorBuilder() {
        descriptor = new EditAppCommand.EditAppDescriptor();
    }

    public EditAppDescriptorBuilder(EditAppCommand.EditAppDescriptor descriptor) {
        this.descriptor = new EditAppCommand.EditAppDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAppDescriptor} with fields containing {@code person}'s details
     */
    public EditAppDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppCommand.EditAppDescriptor();
        descriptor.setLocation(appointment.getLocation());
        descriptor.setStart(appointment.getStart());
        descriptor.setEnd(appointment.getEnd());
        descriptor.setDescription(appointment.getDescription());
    }


    /**
     * Sets the {@code Location} of the {@code EditAppDescriptor} that we are building.
     */
    public EditAppDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Address(location));
        return this;
    }

    /**
     * Sets the {@code start} of the {@code EditAppDescriptor} that we are building.
     */
    public EditAppDescriptorBuilder withStart(String startString) {
        LocalDateTime start = LocalDateTime
                .parse(startString, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        descriptor.setStart(start);
        return this;
    }

    /**
     * Sets the {@code end} of the {@code EditAppDescriptor} that we are building.
     */
    public EditAppDescriptorBuilder withEnd(String endString) {
        LocalDateTime end = LocalDateTime
                .parse(endString, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        descriptor.setEnd(end);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditAppDescriptor} that we are building.
     */
    public EditAppDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }


    public EditAppCommand.EditAppDescriptor build() {
        return descriptor;
    }
}
