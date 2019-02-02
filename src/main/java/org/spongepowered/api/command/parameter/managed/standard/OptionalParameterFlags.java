package org.spongepowered.api.command.parameter.managed.standard;

import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

/**
 * Determines what happens if a parameter cannot parse an element.
 */
public class OptionalParameterFlags {

    // SORTFIELDS: ON

    /**
     * Indicates that this element is required, and any failure to parse
     * will halt command execution.
     */
    public static final OptionalParameterFlag NOT_OPTIONAL =
            DummyObjectProvider.createFor(OptionalParameterFlag.class, "NOT_OPTIONAL");

    /**
     * Indicates that this element is optional if, and only if, there are no
     * more arguments to parse. Any other errors in parsing will cause a halt to
     * command execution.
      */
    public static final OptionalParameterFlag OPTIONAL_ON_END =
            DummyObjectProvider.createFor(OptionalParameterFlag.class, "OPTIONAL_ON_END");

    /**
     * Indicates that this element is optional, such that any error will be
     * suppressed and the {@link Parameter} will be skipped.
     */
    public static final OptionalParameterFlag OPTIONAL =
            DummyObjectProvider.createFor(OptionalParameterFlag.class, "OPTIONAL");

    // SORTFIELDS: OFF
}
