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
package org.spongepowered.api.item.inventory.custom;

import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

/**
 * An enumeration of all possible {@link ContainerType}s in vanilla minecraft.
 */
public final class ContainerTypes {

    // SORTFIELDS:ON

    /**
     * Size only multiple of 9 up to 6x9.
     */
    public static final ContainerType CHEST = DummyObjectProvider.createFor(ContainerType.class, "chest"); // "minecraft:chest"
    /**
     * Size only 3.
     */
    public static final ContainerType FURNACE = DummyObjectProvider.createFor(ContainerType.class, "furnace"); // "minecraft:furnace"
    /**
     * Size only 9.
     */
    public static final ContainerType DISPENSER = DummyObjectProvider.createFor(ContainerType.class, "dispenser"); // "minecraft:dispenser"
    /**
     * Size only 10.
     */
    public static final ContainerType CRAFTING_TABLE = DummyObjectProvider.createFor(ContainerType.class, "crafting_table"); // "minecraft:crafting_table"
    /**
     * Size only 5.
     */
    public static final ContainerType BREWING_STAND = DummyObjectProvider.createFor(ContainerType.class, "brewing_stand"); // "minecraft:brewing_stand"
    /**
     * Size only 5.
     */
    public static final ContainerType HOPPER = DummyObjectProvider.createFor(ContainerType.class, "hopper"); // "minecraft:hopper"
    /**
     * Size only 1.
     */
    public static final ContainerType BEACON = DummyObjectProvider.createFor(ContainerType.class, "beacon"); // "minecraft:beacon"
    /**
     * Size only 2.
     */
    public static final ContainerType ENCHANTING_TABLE = DummyObjectProvider.createFor(ContainerType.class, "enchanting_table"); // "minecraft:enchanting_table"
    /**
     * Size only 3.
     */
    public static final ContainerType ANVIL = DummyObjectProvider.createFor(ContainerType.class, "anvil"); // "minecraft:anvil"
    /**
     * Size only 3.
     */
    public static final ContainerType VILLAGER = DummyObjectProvider.createFor(ContainerType.class, "villager"); // "minecraft:villager"
    /**
     * Sizes 2 and more depending on the Horse Carrier.
     */
    public static final ContainerType HORSE = DummyObjectProvider.createFor(ContainerType.class, "horse"); // "minecraft:horse" internally "EntityHorse"
    /**
     * Size only 27 (3x9).
     */
    public static final ContainerType SHULKER_BOX = DummyObjectProvider.createFor(ContainerType.class, "shulker_box"); // "minecraft:shulker_box"

    // SORTFIELDS:OFF
}


