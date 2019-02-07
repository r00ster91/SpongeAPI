package org.spongepowered.api.command;

/**
 * Common {@link CommandResult}s.
 */
public final class CommandResults {

    /**
     * Indicates that the command executed successfully.
     */
    public final static CommandResult SUCCESS = CommandResult.builder().setResult(1).build();

    /**
     * Indicates that the command executed but was unable to carry out its task.
     */
    public final static CommandResult EMPTY = CommandResult.builder().build();

    private CommandResults() {
        throw new AssertionError("You shouldn't be trying to instantiate this");
    }

}
