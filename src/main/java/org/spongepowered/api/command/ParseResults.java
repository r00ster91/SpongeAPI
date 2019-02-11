package org.spongepowered.api.command;

import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.ArgumentReader;

public interface ParseResults {

    ArgumentReader getArgumentReader();

    CommandContext getContext();


}
