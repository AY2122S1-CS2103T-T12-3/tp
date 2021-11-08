package seedu.placebook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.placebook.commons.core.Config;
import seedu.placebook.commons.core.LogsCenter;
import seedu.placebook.commons.core.Version;
import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.commons.util.ConfigUtil;
import seedu.placebook.commons.util.StringUtil;
import seedu.placebook.logic.Logic;
import seedu.placebook.logic.LogicManager;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.ReadOnlyUserPrefs;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.model.util.SampleDataUtil;
import seedu.placebook.storage.ContactsStorage;
import seedu.placebook.storage.JsonContactsStorage;
import seedu.placebook.storage.JsonScheduleStorage;
import seedu.placebook.storage.JsonUserPrefsStorage;
import seedu.placebook.storage.ScheduleStorage;
import seedu.placebook.storage.Storage;
import seedu.placebook.storage.StorageManager;
import seedu.placebook.storage.UserPrefsStorage;
import seedu.placebook.ui.Ui;
import seedu.placebook.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing PLaceBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ContactsStorage contactsStorage = new JsonContactsStorage(userPrefs.getContactsFilePath());
        ScheduleStorage scheduleStorage = new JsonScheduleStorage(userPrefs.getScheduleFilePath());
        storage = new StorageManager(contactsStorage, userPrefsStorage, scheduleStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);

        // this is to provide logic the power of window creation
        // while maintaining testability for confirmation windows.
        logic.setUi(ui);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s Placebook and {@code userPrefs}. <br>
     * The data from the sample Placebook will be used instead if {@code storage}'s Placebook is not found,
     * or an empty Placebook will be used instead if errors occur when reading {@code storage}'s Placebook.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyContacts> contactsOptional;
        Optional<ReadOnlySchedule> scheduleOptional;
        ReadOnlyContacts initialContacts;
        ReadOnlySchedule initialSchedule;
        boolean usingSampleSchedule = false;

        try {
            scheduleOptional = storage.readSchedule();
            if (!scheduleOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Schedule");
                usingSampleSchedule = true;
            }
            initialSchedule = scheduleOptional.orElseGet(SampleDataUtil::getSampleSchedule);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Schedule");
            initialSchedule = new Schedule();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Schedule");
            initialSchedule = new Schedule();
        }

        try {
            contactsOptional = storage.readContacts();
            if (!contactsOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Contacts");
                initialSchedule = SampleDataUtil.getSampleSchedule();
            } else if (usingSampleSchedule) {
                // Sample Schedule data would most likely not match non-sample Schedule
                // In this case, we will wipe schedule data
                logger.info("Contacts data found, wiping sample Schedule");
                initialSchedule = new Schedule();
            }
            initialContacts = contactsOptional.orElseGet(SampleDataUtil::getSampleContacts);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Contacts");
            initialContacts = new Contacts();
            initialSchedule = new Schedule();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Contacts");
            initialContacts = new Contacts();
            initialSchedule = new Schedule();
        }

        return new ModelManager(initialContacts, userPrefs, initialSchedule);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Placebook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting PlaceBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping PlaceBook ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
