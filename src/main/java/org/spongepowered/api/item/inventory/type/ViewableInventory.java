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
package org.spongepowered.api.item.inventory.type;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.ContainerType;

import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Interface for inventories which may be interacted with by Players.
 * <p>e.g. the inventory of a Chest</p>
 */
public interface ViewableInventory extends Inventory {

    /**
     * Gets the current viewers looking at this Inventory.
     *
     * @return The current viewers of this inventory
     */
    Set<Player> getViewers();

    /**
     * Checks for whether this Inventory currently has viewers.
     *
     * @return True if viewers are currently looking at this inventory
     */
    boolean hasViewers();

    /**
     * Gets whether the specified player can interact with this object.
     * 
     * @param player the Player wishing to interact with this Inventory
     * @return true if the Entity is able to interact with this Inventory
     */
    boolean canInteractWith(Player player);


    interface Builder {
        Builder type(ContainerType type);

        StepSource source(Inventory inventory);

        StepDummy dummy();
        StepDummy dummy(ItemStackSnapshot item);
        StepDummy dummy(int amount);
        StepDummy dummy(int amount, ItemStackSnapshot item);

        StepDummy fillDummy(ItemStackSnapshot item);
        StepDummy fillDummy();

        // ? Builder skip(int amount);

        interface StepSource extends Builder {
            StepSlot slot();
            StepSlot slots(int amount);
            StepGrid grid(int sizeX, int sizeY);

            // ? StepSource skip(int amount);
            // ? StepSource skipSource(int amount);
        }

        interface StepCallback<T> extends StepSource {
            T click(BiConsumer<Container, Slot> handler);
            T change(BiConsumer<Container, Slot> handler);
            // autocancel changes?
            T autoCancel();
            // shift-click behaviour?
            T denyShiftMove();
            T allowShiftMove();
        }

        interface StepSlotLike<T> extends StepCallback<T> {
            T from(int index);
            T at(int index);
        }

        interface StepSlot extends StepSlotLike<StepSlot> {
        }

        interface StepDummy extends StepSlotLike<StepDummy> {
        }

        interface StepGrid extends StepCallback<StepSlot> {
            StepGrid from(int x, int y);
            StepGrid at(int x, int y);
        }

        // throws exception when inventory is incomplete
        ViewableInventory build();
    }



}
