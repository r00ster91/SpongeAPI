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
package org.spongepowered.api.event.item.inventory;

import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.item.inventory.container.InteractContainerEvent;
import org.spongepowered.api.event.item.inventory.enchantment.EnchantmentList;
import org.spongepowered.api.event.item.inventory.enchantment.LevelRequirement;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;

/**
 * This event is triggered when an item is enchanted at an enchanting table.
 */
public interface EnchantItemEvent extends InteractContainerEvent {

    /**
     * Returns the slot of the enchanting item.
     *
     * @return the slot of the enchanting item.
     */
    Slot getEnchantingSlot();

    /**
     * The seed for pseudo random enchantment generation.
     *
     * @return The seed
     */
    int getSeed();

    /**
     * The enchantment option. 1, 2 or 3
     *
     * @return The enchantment option
     */
    int getOption();

    /**
     * The itemstack to enchant.
     *
     * @return the itemstack to enchant.
     */
    ItemStackSnapshot getItem();

    /**
     * Triggers when the enchantment level requirement for an item is calculated.
     */
    interface CalculateLevelRequirement extends EnchantItemEvent {

        /**
         * The enchantment power based on bookshelves around the enchantment table.
         * <p>In vanilla the maximum value is 15.</p>

         * @return the enchantment power
         */
        int getPower();

        /**
         * The calculated level requirement for the option.
         * <p>In vanilla the maximum value is 30.</p>
         * <p>Returning a much higher level may result in no enchantments
         * because the existing enchantments are too weak.</p>
         *
         * @return the level requirement.
         */
        Transaction<LevelRequirement> getLevelRequirement();

    }

    /**
     * Triggers when the enchantments for an item are calculated.
     * This happens after a new item to enchant is put in the table
     * and again when the item is enchanted.
     * <p>Note that when modifying the enchantment list you should
     * return the same enchantments for the same seed and option.</p>
     */
    interface CalculateEnchantment extends EnchantItemEvent {

        /**
         * The final level requirement from {@link CalculateLevelRequirement}.
         * <p>In vanilla the maximum value is 30.</p>
         *
         * @return the level requirement for the option
         */
        int getLevelRequirement();

        /**
         * The list of enchantments to apply to the item.
         * <p>The first item in the list is used for display when previewing the enchantments.</p>
         *
         * @return the list of enchantments
         */
        Transaction<EnchantmentList> getEnchantments();

    }

    /**
     * Triggers when the item was enchanted.
     */
    interface Post extends EnchantItemEvent, ChangeInventoryEvent {

    }

}
