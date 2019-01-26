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
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;

/**
 * A list of enchantments to apply to an {@link ItemStack} when enchanting.
 */
public interface EnchantmentList extends DataSerializable {

    /**
     * The list of enchantments
     *
     * @return the list of enchantments.
     */
    List<Enchantment> enchantments();

    /**
     * Create a new enchantment list with given enchantments.
     *
     * @param enchantments the enchantments
     *
     * @return the new enchantment list
     */
    EnchantmentList withEnchantments(Enchantment... enchantments);
    /**
     * Create a new enchantment list with given enchantments.
     *
     * @param enchantments the enchantments
     *
     * @return the new enchantment list
     */
    EnchantmentList withEnchantments(List<Enchantment> enchantments);

    // Utility functions to generate enchantment lists

    /**
     * Creates a new random vanilla enchantment list with different input values.
     *
     * @param seed the random seed
     * @param option the option
     * @param level the level requirement
     * @param treasure whether to include treasure enchantments
     *
     * @return the new enchantment list
     */
    EnchantmentList with(int seed, int option, int level, boolean treasure);

    /**
     * Creates a new random enchantment list based on a fixed enchantment pool.
     *
     * @param seed the random seed
     * @param option the option
     * @param level the level requirement
     * @param list the enchantment pool
     *
     * @return the new enchantment list
     */
    EnchantmentList with(int seed, int option, int level, List<Enchantment> list);

}
