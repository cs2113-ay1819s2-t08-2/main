package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalContactList;
import static seedu.address.testutil.TypicalHabits.getTypicalHabitTrackerList;
import static seedu.address.testutil.TypicalPurchases.getTypicalExpenditureList;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalContactList(), new UserPrefs(),
            getTypicalTaskList(), getTypicalExpenditureList(), getTypicalWorkoutList(), getTypicalHabitTrackerList());
    private final Model expectedModel = new ModelManager(getTypicalContactList(), new UserPrefs(),
            getTypicalTaskList(), getTypicalExpenditureList(), getTypicalWorkoutList(), getTypicalHabitTrackerList());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        model.undoContactList();
        model.undoContactList();

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        expectedModel.undoContactList();
        expectedModel.undoContactList();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoContactList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoContactList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
