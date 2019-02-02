package org.spongepowered.api.command.parameter.managed.standard;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.parameter.ArgumentParseException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.command.parameter.token.CommandArgs;
import org.spongepowered.api.util.annotation.CatalogedBy;

/**
 * Determines what happens when an element was not parsed and an error was thrown.
 */
@CatalogedBy(OptionalParameterFlags.class)
public interface OptionalParameterFlag extends CatalogType {

    /**
     * Determines whether to continue parsing based on the current context.
     *
     * @param parameter The {@link Parameter.Value} that has just been parsed
     * @param args The {@link CommandArgs} containing the raw arguments
     * @param context The {@link CommandContext} with parsed elements
     */
    void continueParsing(Parameter.Value<?> parameter, CommandArgs args, CommandContext context) throws ArgumentParseException;

}
