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
package org.spongepowered.api.command.parameter.managed.standard;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.parameter.managed.ValueParameterModifier;
import org.spongepowered.api.command.source.RemoteSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.network.RemoteConnection;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

import java.net.InetAddress;
import java.time.LocalDateTime;

/**
 * Class containing cataloged {@link ValueParameterModifier}s.
 */
public final class CatalogedValueParameterModifiers {

    private CatalogedValueParameterModifiers() {}

    // SORTFIELDS:ON

    /**
     * Indicates that if the parameter cannot be parsed, return a
     * {@link LocalDateTime} representing the current time.
     */
    public static final CatalogedValueParameterModifier<LocalDateTime> OR_CURRENT_DATE_TIME =
            DummyObjectProvider.createExtendedFor(CatalogedValueParameterModifier.class, "OR_CURRENT_DATE_TIME");

    /**
     * Indicates that if the parameter cannot be parsed and an {@link Entity}
     * is the command source, return the player instead.
     */
    public static final CatalogedValueParameterModifier<Entity> OR_ENTITY_SOURCE =
            DummyObjectProvider.createExtendedFor(CatalogedValueParameterModifier.class, "OR_ENTITY_SOURCE");

    /**
     * Indicates that if the parameter cannot be parsed and an {@link Entity}
     * is the command source, return the entity that they they are
     * looking at instead, if any.
     */
    public static final CatalogedValueParameterModifier<Entity> OR_ENTITY_TARGET =
            DummyObjectProvider.createExtendedFor(CatalogedValueParameterModifier.class, "OR_ENTITY_TARGET");

    /**
     * Indicates that if the parameter cannot be parsed, return the executing
     * {@link CommandSource} instead.
     */
    public static final CatalogedValueParameterModifier<CommandSource> OR_SOURCE =
            DummyObjectProvider.createExtendedFor(CatalogedValueParameterModifier.class, "OR_SOURCE");

    /**
     * Indicates that if the parameter cannot be parsed, return the executing
     * {@link CommandSource}'s IP address, if it is a {@link RemoteSource},
     * instead.
     */
    public static final CatalogedValueParameterModifier<InetAddress> OR_SOURCE_IP =
            DummyObjectProvider.createExtendedFor(CatalogedValueParameterModifier.class, "OR_SOURCE_IP");

    /**
     * Indicates that if the parameter cannot be parsed and a {@link Player}
     * is the command source, return the player instead.
     */
    public static final CatalogedValueParameterModifier<Player> OR_PLAYER_SOURCE =
            DummyObjectProvider.createExtendedFor(CatalogedValueParameterModifier.class, "OR_PLAYER_SOURCE");

    /**
     * Indicates that if the parameter cannot be parsed and an {@link Entity}
     * is the command source, return the {@link Player} that they they are
     * looking at instead, if any.
     */
    public static final CatalogedValueParameterModifier<Player> OR_PLAYER_TARGET =
            DummyObjectProvider.createExtendedFor(CatalogedValueParameterModifier.class, "OR_PLAYER_TARGET");

    // SORTFIELDS:OFF

}
