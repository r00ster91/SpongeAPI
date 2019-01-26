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
package org.spongepowered.api.event.item.inventory.enchantment;

import org.spongepowered.api.data.DataSerializable;

/**
 * The level requirement when enchanting an item on an enchanting table.
 */
public interface LevelRequirement extends DataSerializable {

    /**
     * Returns the level requirement.
     * <p>In vanilla the maximum value is 30.</p>
     *
     * @return the level requirement
     */
    int levelRequirement();

    /**
     * Creates a modified level requirement.
     * <p>Returning a much higher level than 30 may result in no enchantments
     * because the existing enchantments are too weak.</p>
     *
     * @param lvl the level requirement.
     *
     * @return the new level requirement.
     */
    LevelRequirement withLevelRequirement(int lvl);
}
