package seedu.placebook.model.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.placebook.model.schedule.exceptions.EndTimeBeforeStartTimeException;

public class TimePeriod implements Comparable<TimePeriod> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

    /** The start date and time of this {@code TimePeriod}. */
    private LocalDateTime startDateTime;

    /** The end date and time of this {@code TimePeriod}. */
    private LocalDateTime endDateTime;

    /**
     * A public constructor to initialize the {@code TimePeriod} with startDateTime and endDateTime.
     * @param startDateTime The given startDateTime.
     * @param endDateTime The given endDateTIme.
     * @throws EndTimeBeforeStartTimeException
     */
    public TimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) throws EndTimeBeforeStartTimeException {
        if (endDateTime.isAfter(startDateTime)) {
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
        } else {
            this.startDateTime = startDateTime;
            this.endDateTime = startDateTime;
            throw new EndTimeBeforeStartTimeException("EndDateTime has been set to startDateTime by default.");
        }
    }

    /**
     * A boolean method to check whether a moment is included in this {@code TimePeriod}.
     * @param moment A LocalDateTime, the given moment.
     * @return A boolean value indicating whether the moment is included.
     */
    public boolean containsMoment(LocalDateTime moment) {
        return (this.startDateTime.isBefore(moment) && this.endDateTime.isAfter(moment));
    }

    /**
     * A boolean method to check whether this {@code TimePeriod} has conflict with another {@code TimePeriod}.
     * @param tp Another {@code TimePeriod}.
     * @return A boolean value indicating whether the two {@TimePeriod} has conflicts.
     */
    public boolean hasConflictWith(TimePeriod tp) {
        return this.containsMoment(tp.startDateTime) || this.containsMoment(tp.endDateTime)
                || tp.containsMoment(this.startDateTime) || tp.containsMoment(this.endDateTime)
                || (this.startDateTime.equals(tp.startDateTime) && this.endDateTime.equals(tp.endDateTime));
    }

    /**
     * Set the {@Code TimePeriod}'s start date and time to the given date and time,
     * exception will be thrown if the given start date and time is after the end date
     * and time of the {@Code TimePeriod}.
     * @param newStartDateTime The given start date and time.
     * @throws EndTimeBeforeStartTimeException Exception will be thrown
     * if the given start date and time is after the end date and time of the {@Code TimePeriod}.
     */
    public void setStartDateTime(LocalDateTime newStartDateTime) throws EndTimeBeforeStartTimeException {
        if (this.endDateTime.isAfter(newStartDateTime)) {
            this.startDateTime = newStartDateTime;
        } else {
            throw new EndTimeBeforeStartTimeException("The operation to set startDateTime was not executed.");
        }
    }

    /**
     * Set the {@Code TimePeriod}'s end date and time to the given date and time,
     * exception will be thrown if the given end date and time is before the start date
     * and time of the {@Code TimePeriod}.
     * @param newEndDateTime The given end date and time.
     * @throws EndTimeBeforeStartTimeException Exception will be thrown
     * if the given end date and time is before the start date and time of the {@Code TimePeriod}.
     */
    public void setEndDateTime(LocalDateTime newEndDateTime) throws EndTimeBeforeStartTimeException {
        if (this.startDateTime.isBefore(newEndDateTime)) {
            this.endDateTime = newEndDateTime;
        } else {
            throw new EndTimeBeforeStartTimeException("The operation to set endDateTime was not executed.");
        }
    }

    /**
     * Get the startDateTime of this {@Code TimePeriod}.
     * @return A LocalDateTime, the startDateTime of this {@Code TimePeriod}.
     */
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(this.startDateTime.toLocalDate(), this.startDateTime.toLocalTime());
    }

    /**
     * Get the endDateTime of this {@Code TimePeriod}.
     * @return Get the endDateTime of this {@Code TimePeriod}.
     */
    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(this.endDateTime.toLocalDate(), this.endDateTime.toLocalTime());
    }

    /**
     * Creates a string representation of the startDateTime using the formatter.
     */
    public String getStartDateTimeAsString() {
        return this.startDateTime.format(formatter);
    }

    /**
     * Creates a string representation of the endDateTime using the formatter.
     */
    public String getEndDateTimeAsString() {
        return this.endDateTime.format(formatter);
    }

    @Override
    public int compareTo(TimePeriod o) {
        if (this.startDateTime.compareTo(o.startDateTime) == 0) {
            return this.endDateTime.compareTo(o.endDateTime);
        } else {
            return this.startDateTime.compareTo(o.startDateTime);
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Start Time: ")
               .append(getStartDateTime().format(formatter))
               .append(" End Time: ")
               .append(getEndDateTime().format(formatter));

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimePeriod)) {
            return false;
        }

        TimePeriod otherTimePeriod = (TimePeriod) other;
        return otherTimePeriod.startDateTime.equals(startDateTime)
                && otherTimePeriod.endDateTime.equals(endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.startDateTime, this.endDateTime);
    }
}
