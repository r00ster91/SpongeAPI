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

import com.flowpowered.math.vector.Vector2i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.property.Property;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryProperties;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.property.ContainerType;
import org.spongepowered.api.util.ResettableBuilder;

import java.util.Set;
import java.util.UUID;

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

    /**
     * Returns the {@link ContainerType} of this viewable inventory.
     *
     * @return the ContainerType of this viewable inventory.
     */
    ContainerType getContainerType();

    // creates a InventoryMenu which allows adding callbacks for inventory clicks and changes - more lightweight than inventory events
    // to receive callbacks the inventory MUST be opened from InventoryMenu#open

    /**
     * Create a new {@link InventoryMenu} based on this ViewableInventory which allows for lightweight callbacks on inventory clicks and changes.
     * To receive callbacks the inventory must be opened from {@link InventoryMenu#open(Player)}
     *
     * @return The new InventoryMenu
     */
    InventoryMenu asMenu();

    /**
     * Creates a new {@link Builder} to build an {@link ViewableInventory}.
     *
     * @return The builder
     */
    static ViewableInventory.Builder builder() {
        return Sponge.getRegistry().createBuilder(ViewableInventory.Builder.class);
    }

    /**
     * A builder for inventories conforming to a ContainerType
     */
    interface Builder extends ResettableBuilder<Inventory, ViewableInventory.Builder> {

        /**
         * Specifies the type of inventory you want to build.
         * <p>You must define all slots of the given type.</p>
         *
         * @param type The ContainerType
         * @return The building step.
         */
        BuildingStep type(ContainerType type);

        /**
         * Builds a wrapper around an existing ViewableInventory.
         *
         * @param inventory the existing ViewableInventory.
         * @return The end step.
         */
        EndStep ofViewable(ViewableInventory inventory);

        /**
         * The building step. Define all slots needed for the chosen {@link ContainerType}.
         * <p>When done use {@link #completeStructure()} to finalize the inventory.</p>
         */
        interface BuildingStep {

            /**
             * Sets the source inventory for the following calls.
             * @param inventory the next source inventory
             *
             * @return the source step
             */
            SourceStep source(Inventory inventory);

            /**
             * Sets the source inventory to a dummy source.
             * <p>Sponge will generate single slot inventories for every slot.</p>
             *
             * @return the dummy source step
             */
            SourceStep dummySource();

            /**
             * Sets the source inventory to a dummy source.
             * <p>Sponge will generate single slot inventories filled with given item for every slot.</p>
             *
             * @return the dummy source step
             */
            SourceStep dummySource(ItemStackSnapshot item);

            /**
             * Adds all undefined slots as dummy slots.
             *
             * @return the building step.
             */
            BuildingStep fillDummy();

            /**
             * Completes the inventory structure.
             * <p>If no slots are defined this will create the structure mirroring the vanilla type.</p>
             * <p>If some but not all slots are defined undefined slots will be defined using {@link #fillDummy()}</p>
             *
             * @return the end step
             */
            EndStep completeStructure();
        }


        /**
         * The source step.
         * <p>The source Inventory has been set. Methods on this interface reference the last source set.</p>
         * <p>If a dummy source was set slots will be generated with the defined {@link ItemStackSnapshot}</p>
         */
        interface SourceStep extends BuildingStep {

            /**
             * Adds a single slot.
             *
             * @return the single slot step
             */
            SingleStep slot();

            /**
             * Adds multiple slots.
             *
             * @param amount the amount of slots to add
             *
             * @return the multiple slots step
             */
            MultipleStep slots(int amount);

            // TODO do we need smth. like this? You could do this kind of simple loop yourself very easy
            default SourceStep slots(int[] indizesFrom, int[] indizesAt) {
                for (int i = 0; i < indizesFrom.length; i++) {
                    int fromIndex = indizesFrom[i];
                    int atIndex = indizesAt[i];
                    slot().from(fromIndex).at(atIndex);
                }
                return this;
            }
            SourceStep slots(Vector2i[] positionsFrom, Vector2i[] positionsAt);

            SourceStep slots(int[] indizesFrom, int startingAt);
            SourceStep slots(Vector2i[] positionsFrom, int startingAt);

            /**
             * Adds a grid of slots.
             *
             * @param sizeX the horizontal size
             * @param sizeY the vertical size
             *
             * @return the grid slots step
             */
            GridStep grid(int sizeX, int sizeY);
        }

        /**
         * The single slot step. A single slot can be positioned at an index {@link #at(int)} or x/y position {@link #at(int, int)}.
         */
        interface SingleStep extends MultipleStep, GridStep {

            /**
             * Sets the slot index in the source inventory.
             * <p>This does nothing for the dummy source</p>
             *
             * @param index the slot index
             *
             * @return this step
             */
            @Override
            SingleStep from(int index);

            /**
             * Sets the slot position in the source inventory.
             * <p>This does nothing for the dummy source</p>
             *
             * @param x the slot x position
             * @param y the slot y position
             *
             * @return this step
             */
            @Override
            SingleStep from(int x, int y);

            /**
             * Sets the slot index in the new inventory.
             *
             * @param index the slot index
             *
             * @return the source step
             */
            @Override
            SourceStep at(int index);

            /**
             * Sets the slot position in the new inventory
             *
             * @param x the slot x position
             * @param y the slot y position
             *
             * @return the source step
             */
            @Override
            SourceStep at(int x, int y);
        }

        /**
         * The multiple slots step. Multiple slots can be positioned at a starting index.
         */
        interface MultipleStep extends SourceStep {
            /**
             * Sets the starting index in the source inventory.
             * <p>This does nothing for the dummy source</p>
             *
             * @param index the starting index
             *
             * @return this step
             */
            MultipleStep from(int index);

            /**
             * Sets the starting index in the new inventory.
             *
             * @param index the starting index
             *
             * @return the source step
             */
            SourceStep at(int index);
        }

        /**
         * The grid slots step. A grid of slots can be positioned at a starting x/y position.
         */
        interface GridStep extends SourceStep {

            /**
             * Sets the starting position in the source inventory.
             * <p>This does nothing for the dummy source</p>
             *
             * @param x the starting x position
             * @param y the starting y position
             *
             * @return this step
             */
            GridStep from(int x, int y);

            /**
             * Sets the starting position in the new inventory
             *
             * @param x the starting x position
             * @param y the starting y position
             *
             * @return the source step
             */
            SourceStep at(int x, int y);
        }

        /**
         * The end Step. You can set an identifier and/or carrier for the inventory before building it.
         */
        interface EndStep {

            /**
             * Sets a unique identifier. Can be retrieved later using. {@link Inventory#getProperty(Property)} with {@link InventoryProperties#UNIQUE_ID}
             *
             * @param uuid the UUID.
             *
             * @return this step
             */
            EndStep identity(UUID uuid);

            /**
             * Sets a carrier.
             *
             * @param carrier the carrier.
             *
             * @return this step
             */
            EndStep carrier(Carrier carrier);

            /**
             * Builds the inventory.
             *
             * @return the new inventory.
             */
            ViewableInventory build();
        }

    }

}
