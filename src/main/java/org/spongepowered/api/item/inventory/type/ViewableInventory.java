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
import org.spongepowered.api.event.item.inventory.container.ClickContainerEvent;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.ContainerType;
import org.spongepowered.api.item.inventory.property.ContainerTypes;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

        interface StepSource extends Builder {
            StepSlot slot();
            StepSlot slots(int amount);
            StepGrid grid(int sizeX, int sizeY);
        }

        interface StepCallback<T> extends StepSource {
            T click(BiConsumer<Container, Slot> handler);
            T change(BiConsumer<Container, Slot> handler);
        }

        interface StepSlot extends StepCallback<StepSlot> {
            StepSlot from(int index);
            StepSlot at(int index);
        }

        interface StepGrid extends StepCallback<StepSlot> {
            StepGrid from(int x, int y);
            StepGrid at(int x, int y);
        }

        interface StepDummy extends StepCallback<StepDummy> {
            StepDummy from(int index);
            StepDummy at(int index);
        }

        // throws exception when inventory is incomplete
        ViewableInventory build();
    }

    // ---------------------------------------------------------------------------------------------------------------------------
    // EXAMPLE USAGE:

    static void foobar()
    {
        Builder builder = null;
        Object plugin = null;
        Inventory inv1 = Inventory.builder().of(InventoryArchetypes.DISPENSER).build(plugin);
        Inventory inv2 = Inventory.builder().of(InventoryArchetypes.DISPENSER).build(plugin);
        Inventory inv3 = Inventory.builder().of(InventoryArchetypes.CHEST).build(plugin);
        ViewableInventory inv = builder
                .type(ContainerTypes.CHEST)
                .source(inv1).grid(3, 3)
                .source(inv2).grid(3, 3).at(3, 1)
                .source(inv3).grid(3, 3).from(3, 0).at(6, 3)
                .dummy().at(3).click(ViewableInventory::onClickMySlot).change(ViewableInventory::onChangeMySlot)
                .fillDummy()
                .build();


    }

    static void onClickMySlot(Container container, Slot slot) {  }
    static void onChangeMySlot(Container container, Slot slot) {  }

    // ---------------------------------------------------------------------------------------------------------------------------


}
