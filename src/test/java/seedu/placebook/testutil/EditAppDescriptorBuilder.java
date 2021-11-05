package seedu.placebook.testutil;

import seedu.placebook.logic.commands.EditAppCommand;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.schedule.Appointment;

import java.time.LocalDateTime;

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
        descriptor.setClients(appointment.getClients());
        descriptor.setLocation(appointment.getLocation());
        descriptor.setStart(appointment.getStart());
        descriptor.setEnd(appointment.getEnd());
        descriptor.setDescription(appointment.getDescription());
    }

    /**
     * Sets the {@code UniquePersonList} of the {@code EditAppDescriptor} that we are building.
     */
    public EditAppDescriptorBuilder withClients(UniquePersonList clients) {
        descriptor.setClients(clients);
        return this;
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
    public EditAppDescriptorBuilder withStart(LocalDateTime start) {
        descriptor.setStart(start);
        return this;
    }

    /**
     * Sets the {@code end} of the {@code EditAppDescriptor} that we are building.
     */
    public EditAppDescriptorBuilder withEnd(LocalDateTime end) {
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
