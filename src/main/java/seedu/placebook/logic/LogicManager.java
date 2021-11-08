package seedu.placebook.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.placebook.commons.core.GuiSettings;
import seedu.placebook.commons.core.LogsCenter;
import seedu.placebook.logic.commands.Command;
import seedu.placebook.logic.commands.CommandResult;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.logic.parser.PlaceBookParser;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.Model;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.storage.Storage;
import seedu.placebook.ui.Ui;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private Ui ui;
    private final PlaceBookParser placeBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        placeBookParser = new PlaceBookParser();
    }

    /**
     * Sets the ui for logic to create new windows.
     */
    @Override
    public void setUi(Ui ui) {
        this.ui = ui;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        if (ui == null) {
            logger.info("Ui is not given to logic for window creation!");
            throw new CommandException("Fatal Error: Ui not given. Please restart PlaceBook again!");
        }

        CommandResult commandResult;
        Command command = placeBookParser.parseCommand(commandText);
        commandResult = command.execute(model, ui);

        try {
            storage.saveContacts(model.getContacts());
            storage.saveSchedule(model.getSchedule());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyContacts getContacts() {
        return model.getContacts();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    @Override
    public Path getContactsFilePath() {
        return model.getContactsFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
