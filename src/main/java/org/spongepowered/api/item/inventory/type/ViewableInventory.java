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
import org.spongepowered.api.event.item.inventory.container.InteractContainerEvent;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.ContainerType;
import org.spongepowered.api.text.Text;

import java.util.Set;
import java.util.UUID;
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

    ContainerType getContainerType();

    // creates a InventoryMenu which allows adding callbacks for inventory clicks and changes - more lightweight than inventory events
    // to receive callbacks the inventory MUST be opened from InventoryMenu#open
    InventoryMenu asMenu();

    static Builder builder() {
        return new SpongeViewableInventoryBuilder();
    }

    interface Builder {
        StepBuilding type(ContainerType type);
        // throws exception when inventory cannot be viewed already
        StepEnd ofViewable(Inventory inventory);

        interface StepBuilding {
            StepSource source(Inventory inventory);

            StepDummy dummy();
            StepDummy dummy(ItemStackSnapshot item);
            StepDummy dummy(int amount);
            StepDummy dummy(int amount, ItemStackSnapshot item);

            StepDummy fillDummy(ItemStackSnapshot item);
            StepDummy fillDummy();

            // complete inventory structure into lens
            // if no slots and source added assume vanilla structure
            // if incomplete call fillDummy()
            StepEnd completeStructure();
        }

        interface StepSource extends StepBuilding {
            StepSlot slot();
            StepSlot slots(int amount);
            StepGrid grid(int sizeX, int sizeY);
        }

        interface StepSlotLike<T> extends StepBuilding {
            T from(int index);
            T at(int index);
        }

        interface StepSlot extends StepSlotLike<StepSlot>, StepSource {
        }

        interface StepDummy extends StepSlotLike<StepDummy>, StepBuilding {
        }

        interface StepGrid extends StepBuilding, StepSource {
            StepGrid from(int x, int y);
            StepGrid at(int x, int y);
        }

        interface StepEnd {
            StepEnd title(Text title); // TODO maybe instead only provide when opening/on InventoryMenu
            StepEnd identity(UUID uuid);
            StepEnd carrier(Carrier carrier);
            <E extends InteractContainerEvent> StepEnd listener(Class<E> type, Consumer<E> listener);

            ViewableInventory build();
        }

    }

}
