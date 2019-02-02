/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.command;

import com.google.common.collect.Lists;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.managed.ChildExceptionBehavior;
import org.spongepowered.api.command.managed.ChildExceptionBehaviors;
import org.spongepowered.api.command.managed.CommandExecutor;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.command.parameter.flag.Flags;
import org.spongepowered.api.command.parameter.token.InputTokenizer;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ResettableBuilder;
import org.spongepowered.api.world.Location;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import javax.annotation.Nullable;

/**
 * The Command interface is the low-level interface that all commands in the
 * Sponge ecosystem inherit.
 *
 * <p><strong>Most</strong> plugins are highly recommended (but not obligated)
 * to use {@link Command#builder()} to create commands. The
 * {@link Command.Builder} allows plugins to take advantage of a higher level
 * of abstraction, such as argument parsers and simple child command handling,
 * removing the need for boilerplate code.</p>
 *
 * <p>Plugins are free to implement this interface should they prefer to do so.
 * Custom implementations of this class are not required to implement a sane
 * {@link Object#equals(Object)}, but are highly encouraged to do so.</p>
 *
 * <p>Commands in Sponge are provided with a {@link Cause}, as well as an
 * associated {@link CommandSource}. Note that the {@link CommandSource} is
 * not necessarily the object that actually invoked the command, however, it
 * is the source that is <strong>responsible</strong> for the command. Therefore,
 * in general, command eligibility checks should be performed against the
 * provided {@link CommandSource}. However, users and implementors may act upon
 * the {@link Cause} alone as they see fit, should it be preferred.</p>
 */
public interface Command {

    /**
     * Gets a builder for building a {@link Command}.
     *
     * @return The {@link Builder}
     */
    static Builder builder() {
        return Sponge.getRegistry().createBuilder(Builder.class);
    }

    /**
     * Execute the command based on input arguments.
     *
     * <p>The implementing class must perform the necessary permission
     * checks.</p>
     *
     * @param cause The {@link Cause} of the command
     * @param source The {@link CommandSource} responsible for the invocation of
     *               the command
     * @param arguments The raw arguments for this command
     * @return The result of a command being processed
     * @throws CommandException Thrown on a command error
     */
    CommandResult process(Cause cause, CommandSource source, String arguments) throws CommandException;

    /**
     * Gets a list of suggestions based on input.
     *
     * <p>If a suggestion is chosen by the user, it will replace the last
     * word.</p>
     *
     * @param cause The {@link Cause} of the command
     * @param source The {@link CommandSource} responsible for the invocation of
     *               the command
     * @param arguments The arguments entered up to this point
     * @param targetPosition The position the source is looking at when
     *     performing tab completion
     * @return A list of suggestions
     * @throws CommandException Thrown if there was a parsing error
     */
    List<String> getSuggestions(Cause cause, CommandSource source, String arguments, @Nullable Location targetPosition) throws CommandException;

    /**
     * Test whether this command can probably be executed given this
     * {@link Cause} and {@link CommandSource}.
     *
     * <p>If implementations are unsure if the command can be executed by
     * the source, {@code true} should be returned. Return values of this method
     * may be used to determine whether this command is listed in command
     * listings.</p>
     *
     * @param cause The {@link Cause} to check
     * @return Whether permission is (probably) granted
     */
    boolean canExecute(Cause cause, CommandSource source);

    /**
     * Gets a short one-line description of this command.
     *
     * <p>The help system may display the description in the command list.</p>
     *
     * @param cause The {@link Cause} of the help request
     * @param source The {@link CommandSource} responsible for the request
     * @return A description
     */
    Optional<Text> getShortDescription(Cause cause, CommandSource source);

    /**
     * Gets a longer formatted help message about this command.
     *
     * <p>It is recommended to use the default text color and style. Sections
     * with text actions (e.g. hyperlinks) should be underlined.</p>
     *
     * <p>Multi-line messages can be created by separating the lines with
     * {@code \n}.</p>
     *
     * <p>The help system may display this message when a source requests
     * detailed information about a command.</p>
     *
     * @param cause The {@link Cause} of the help request
     * @param source The {@link CommandSource} responsible for the request
     * @return A help text
     */
    Optional<Text> getHelp(Cause cause, CommandSource source);

    /**
     * Gets the usage string of this command.
     *
     * <p>A usage string may look like
     * {@code [-w &lt;world&gt;] &lt;var1&gt; &lt;var2&gt;}.</p>
     *
     * <p>The string must not contain the command alias.</p>
     *
     * @param cause The {@link Cause} of the help request
     * @param source The {@link CommandSource} responsible for the request
     * @return A usage string
     */
    Text getUsage(Cause cause, CommandSource source);

    /**
     * A high level {@link Builder} for creating a {@link Command}.
     *
     * <p>When creating a command, ensure that a {@link CommandExecutor}
     * <strong>and/or</strong> a child command is specified.</p>
     */
    interface Builder extends ResettableBuilder<Command, Builder> {

        /**
         * Adds a {@link Command} as a child to this command, under the
         * supplied keys. The keys are case insensitive.
         *
         * @param child The {@link Command} that is a child.
         * @param keys The keys to register as a sub command.
         * @return This builder, for chaining
         * @throws IllegalArgumentException thrown if a child key is already
         *                                  in the builder
         */
        default Builder child(Command child, String... keys) {
            return child(child, Arrays.asList(keys));
        }

        /**
         * Adds a {@link Command} as a child to this command, under the
         * supplied keys. The keys are case insensitive.
         *
         * @param child The {@link Command} that is a child.
         * @param keys The keys to register as a sub command.
         * @return This builder, for chaining
         * @throws IllegalArgumentException thrown if a child key is already
         *                                  in the builder
         */
        Builder child(Command child, Iterable<String> keys);

        /**
         * Adds multiple {@link Command} as children to this command,
         * under the supplied keys. The keys are case insensitive.
         *
         * @param children The {@link Map} that contains a mapping of keys to
         *                 their respective {@link Command} children.
         * @return This builder, for chaining
         * @throws IllegalArgumentException thrown if a child key is already
         *                                  in the builder
         */
        default Builder children(Map<? extends Iterable<String>, ? extends Command> children) {
            for (Map.Entry<? extends Iterable<String>, ? extends Command> child : children.entrySet()) {
                child(child.getValue(), child.getKey());
            }

            return this;
        }

        /**
         * Adds a parameter for use when parsing arguments. When executing a
         * command, they will be executed in the order they are added to this
         * builder.
         *
         * @param parameter The parameter to add to the parameter list
         * @return This builder, for chaining
         */
        Builder parameter(Parameter parameter);

        /**
         * Adds parameters to use when parsing arguments. Parameters will be
         * used in the order provided here.
         *
         * @param parameters The {@link Parameter}s to use
         * @return This builder, for chaining
         */
        default Builder parameters(Parameter... parameters) {
            return parameters(Arrays.asList(parameters));
        }

        /**
         * Adds parameters to use when parsing arguments. Parameters will be
         * used in the order provided here.
         *
         * @param parameters The {@link Parameter}s to use
         * @return This builder, for chaining
         */
        default Builder parameters(Iterable<Parameter> parameters) {
            for (Parameter parameter : parameters) {
                parameter(parameter);
            }

            return this;
        }

        /**
         * Determines what to do if a child command throws an exception.
         *
         * <p>Defaults to {@link ChildExceptionBehaviors#SUPPRESS}, which means
         * that if a child command fails to execute, the error will be ignored
         * and this base command will attempt to execute.</p>
         *
         * <p>See {@link ChildExceptionBehaviors} for other possible behaviors.
         * </p>
         *
         * @param exceptionBehavior The {@link ChildExceptionBehavior} to adhere
         *                          to.
         * @return This builder, for chaining
         */
        Builder setChildExceptionBehavior(ChildExceptionBehavior exceptionBehavior);

        /**
         * Provides the logic of the command.
         *
         * <p>This is only optional if child commands are specified. This will
         * replace any previous executor that has been set.</p>
         *
         * @param executor The {@link CommandExecutor} that will run the command
         * @return This builder, for chaining
         */
        Builder setExecutor(CommandExecutor<CommandSource> executor);

        /**
         * Provides the logic of the command, filtering commands based on the
         * type of {@link CommandSource} executing the .
         *
         * <p>Unlike the setExecutor methods, this will chain multiple executors.
         * Note the following:</p>
         *
         * <ul>
         *     <li>The first call to this will remove any set executors</li>
         *     <li>Subsequent calls will add executors</li>
         *     <li>When a command is called, executors will be checked in the
         *     order they are provided and the first to match will run</li>
         *     <li>Any call to {@link #setExecutor(CommandExecutor)} will
         *     remove these executors, and the next call to this will be
         *     treated as the first call.</li>
         * </ul>
         *
         * @param executor The {@link CommandExecutor} that will run the command
         * @return This builder, for chaining
         */
        <T extends CommandSource> Builder targetedExecutor(CommandExecutor<T> executor, Class<T> sourceType);

        /**
         * If using {@link #targetedExecutor(CommandExecutor, Class)} to
         * build up a set of executors, this provides the message to return if no
         * {@link CommandExecutor}s match when the command is run.
         *
         * @param targetedExecutorError The error {@link Text}
         * @return This builder, for chaining
         */
        Builder setTargetedExecutorErrorMessage(Text targetedExecutorError);

        /**
         * Provides the description for this command, which is dependent on the
         * {@link Cause} that requests it.
         *
         * <p>A one line summary should be provided to
         * {@link #setShortDescription(Text)}</p>
         *
         * @param extendedDescriptionFunction A function that provides a
         *      relevant description based on the supplied {@link Cause}
         * @return This builder, for chaining
         */
        Builder setExtendedDescription(BiFunction<Cause, CommandSource, Optional<Text>> extendedDescriptionFunction);

        /**
         * Provides the description for this command.
         *
         * <p>A one line summary should be provided to
         * {@link #setShortDescription(Text)}</p>
         *
         * @param extendedDescription The description to use, or {@code null}
         *                            for no description.
         * @return This builder, for chaining
         */
        default Builder setExtendedDescription(@Nullable Text extendedDescription) {
            // Done outside the lambda so that we don't have to recreate the object each time.
            Optional<Text> text = Optional.ofNullable(extendedDescription);
            return setExtendedDescription((cause, commandSource) -> text);
        }

        /**
         * The flags that this command should accept. See {@link Flags}.
         *
         * @param flags The {@link Flags} to accept
         * @return This builder, for chaining
         */
        Builder setFlags(Flags flags);

        /**
         * Determines how an argument string should be split.
         *
         * <p>Defaults to splitting on spaces, ignoring spaces in quoted
         * regions.</p>
         *
         * @param tokenizer The {@link InputTokenizer} to use
         * @return This builder, for chaining
         */
        Builder setInputTokenizer(InputTokenizer tokenizer);

        /**
         * Provides a simple description for this command, typically no more
         * than one line, which is dependent on the {@link Cause} and the
         * responsible {@link CommandSource} that requests it.
         *
         * <p>Fuller descriptions should be provided through
         * {@link #setExtendedDescription(BiFunction)}</p>
         *
         * @param descriptionFunction A function that provides a relevant
         *      description based on the supplied {@link Cause} and
         *      {@link CommandSource}
         * @return This builder, for chaining
         */
        Builder setShortDescription(BiFunction<Cause, CommandSource, Optional<Text>> descriptionFunction);

        /**
         * Provides a simple description for this command, typically no more
         * than one line.
         *
         * <p>Fuller descriptions should be provided through
         * {@link #setExtendedDescription(Text)}</p>
         *
         * @param description The description to use, or {@code null} for no
         *                    description
         * @return This builder, for chaining
         */
        default Builder setShortDescription(@Nullable Text description) {
            // Done outside the lambda so that we don't have to recreate the object each time.
            Optional<Text> text = Optional.ofNullable(description);
            return setShortDescription((cause, commandSource) -> text);
        }

        /**
         * The permission that a {@link CommandSource} requires to run this
         * command, or {@code null} if no permission is required.
         *
         * <p>For more control over whether a command can be executed, use
         * {@link #setExecutionRequirements(BiPredicate)}.</p>
         *
         * <p><strong>Note:</strong> this will overwrite any requirements set
         * using {@link #setExecutionRequirements(BiPredicate)}.</p>
         *
         * @param permission The permission that is required, or {@code null}
         *                   for no permission
         * @return This builder, for chaining
         */
        default Builder setPermission(@Nullable String permission) {
            if (permission == null) {
                return setExecutionRequirements(null);
            }
            return setExecutionRequirements((cause, commandSource) -> commandSource.hasPermission(permission));
        }

        /**
         * Sets a function that determines what is required of the provided
         * {@link Cause} and {@link CommandSource} before this command executes.
         *
         * <p><strong>Note:</strong> this will overwrite any requirements set
         * using {@link #setPermission(String)}.</p>
         *
         * @param executionRequirements A function that sets the
         * @return This builder, for chaining
         */
        Builder setExecutionRequirements(@Nullable BiPredicate<Cause, CommandSource> executionRequirements);

        /**
         * If this is set to true, then if the parent command (this) has
         * execution requirements (or a permission) set using
         * {@link #setPermission(String)} or
         * {@link #setExecutionRequirements(BiPredicate)}, then these
         * requirements are required for all children too. If this is set to
         * false, then child commands <em>do not</em> require the set
         * requirements.
         *
         * <p>This defaults to {@code true}.</p>
         *
         * @param required Whether this command's permission is required for
         *                 child commands.
         * @return This builder, for chaining
         */
        Builder setCheckRequirementForChildren(boolean required);

        /**
         * Builds this command, creating a {@link Command} object
         *
         * <p>To build the command, <strong>one</strong> of the following is
         * required:</p>
         *
         * <ul>
         *     <li>A {@link CommandExecutor} is provided using
         *     {@link #setExecutor(CommandExecutor)}</li>
         *     <li>At least one {@link Command} is set to be a child command
         *     using {@link #child(Command, Iterable)} or {@link #children(Map)}
         *     </li>
         * </ul>
         *
         * <p>If these conditions are not fulfilled, an
         * {@link IllegalStateException} will be thrown.</p>
         *
         * @return The command, ready for registration
         * @throws IllegalStateException if the builder is not complete
         */
        Command build();

        /**
         * Builds this command, creating a {@link Command} object, and registers
         * it with Sponge's {@link CommandManager}.
         *
         * <p>To build the command, <strong>one</strong> of the following is
         * required:</p>
         *
         * <ul>
         *     <li>A {@link CommandExecutor} is provided using
         *     {@link #setExecutor(CommandExecutor)}</li>
         *     <li>At least one {@link Command} is set to be a child command
         *     using {@link #child(Command, Iterable)} or {@link #children(Map)}
         *     </li>
         * </ul>
         *
         * <p>If these conditions are not fulfilled, an
         * {@link IllegalStateException} will be thrown.</p>
         *
         * @param pluginContainer The {@link PluginContainer} to register the
         *      command under
         * @param aliases The aliases to register for the command
         * @return The {@link CommandMapping} with the registration, or an
         *      empty optional if no valid aliases were registered
         * @throws IllegalStateException if the builder is not complete
         * @throws IllegalArgumentException if an invalid
         *      {@link PluginContainer} is supplied
         */
        default Optional<CommandMapping> buildAndRegister(PluginContainer pluginContainer, String... aliases) {
            return buildAndRegister(pluginContainer, Lists.newArrayList(aliases));
        }

        /**
         * Builds this command, creating a {@link Command} object, and registers
         * it with Sponge's {@link CommandManager}.
         *
         * <p>To build the command, <strong>one</strong> of the following is
         * required:</p>
         *
         * <ul>
         *     <li>A {@link CommandExecutor} is provided using
         *     {@link #setExecutor(CommandExecutor)}</li>
         *     <li>At least one {@link Command} is set to be a child command
         *     using {@link #child(Command, Iterable)} or {@link #children(Map)}
         *     </li>
         * </ul>
         *
         * <p>If these conditions are not fulfilled, an
         * {@link IllegalStateException} will be thrown.</p>
         *
         * @param pluginContainer The {@link PluginContainer} to register the
         *      command under
         * @param aliases The aliases to register for the command
         * @return The {@link CommandMapping} with the registration, or an
         *      empty optional if no valid aliases were registered
         * @throws IllegalStateException if the builder is not complete
         * @throws IllegalArgumentException if an invalid
         *      {@link PluginContainer} is supplied
         */
        Optional<CommandMapping> buildAndRegister(PluginContainer pluginContainer, List<String> aliases);
    }

}
