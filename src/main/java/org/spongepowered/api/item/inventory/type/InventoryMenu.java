package org.spongepowered.api.item.inventory.type;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.ContainerType;
import org.spongepowered.api.item.inventory.slot.SlotIndex;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.function.Consumer;

// A Menu based on an Inventory

/**
 * Helper for Menus based on Inventories.
 * <p>This helper provides simple callbacks that can be used instead of listening to inventory events.</p>
 */
public interface InventoryMenu {

    /**
     * Creates a new InventoryMenu based on given inventory.
     *
     * @param inventory the inventory
     *
     * @return the new menu.
     */
    static InventoryMenu of(ViewableInventory inventory) {
        return inventory.asMenu();
    }

    /**
     * Returns the current inventory used in this menu.
     *
     * @return the current inventory
     */
    ViewableInventory getCurrentInventory();

    /**
     * Returns the container type of the current inventory.
     *
     * @return current container type.
     */
    ContainerType getType();

    /**
     * Sets a new inventory. If the ContainerType does not change the inventory will be swapped out silently.
     * <p>If the ContainerType is different all existing callbacks are cleared and open menus are closed and reopened with the new inventory.</p>
     *
     * @param inventory
     */
    void setCurrentInventory(ViewableInventory inventory);

    /**
     * Sets the title of this menu.
     * <p>Any open menus are closed and reopened with the new title.</p>
     *
     * @param title the new title.
     */
    void setTitle(Text title);

    // Register callback for clicked slot ; no param=all
    MenuCallback registerClick(SlotClickHandler callback, SlotIndex... slotIndices);
    // Register callback for changed slot ; no param=all
    MenuCallback registerChange(SlotChangeHandler callback, SlotIndex... slotIndices);

    MenuCallback registerClose(Consumer<Container> callback);
    // TODO shift move handler?

    // Callback that automatically cancels
    default MenuCallback registerReadOnly(SlotIndex... slotIndices) {
        return registerChange((container, slot, slotIndex) -> false, slotIndices);
    }

    // readonly for the entire inventory default true

    /**
     * Sets the readonly mode for this menu.
     * <p>By default this is true and cancels any change in menu.</p>
     *
     * @param readOnly whether to make the menu readonly or not.
     *
     * @return this menu
     */
    InventoryMenu setReadOnly(boolean readOnly);

    // unregister all at given SlotIndex
    void unregisterAt(SlotIndex... slotIndices);
    // unregister a previously registered callback
    boolean unregister(MenuCallback callback);
    // unregister all
    void clearCallbacks();

    // opens this menu and makes callbacks in the opened container work
    Optional<Container> open(Player player);

    // Can be used to unregister single callbacks
    interface MenuCallback { }

    interface SlotClickHandler {
        // return false to cancel changes
        // TODO click types - mixin into ClickType?
        enum ClickType {
            PICKUP,
            QUICK_MOVE,
            SWAP,
            CLONE,
            THROW,
            QUICK_CRAFT,
            PICKUP_ALL;
        }
        // Mixin at begin of Container#slotClick and do nothing. (maybe need to send rollback packets to client)
        boolean handle(Container container, Slot slot, SlotIndex slotIndex, ClickType clickType);
    }

    // TODO do we need smth. like this? maybe autocancel is enough
    // impl: maybe in detectAndSendChanges?
    interface SlotChangeHandler {
        // return false to cancel changes
        void handle(Container container, Slot slot, SlotIndex slotIndex);
    }


}
