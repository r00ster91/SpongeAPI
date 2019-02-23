package org.spongepowered.api.item.inventory.type;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.ContainerType;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.function.Consumer;

// A Menu based on an Inventory
public interface InventoryMenu {

    static InventoryMenu of(ViewableInventory inventory) {
        return inventory.asMenu();
    }

    // Current Inventory
    ViewableInventory getCurrentInventory();
    // Type of the current inventory
    ContainerType getType();

    // If ContainerType matches - this can change the inventory without reopening
    // Different ContainerType clears existing callbacks - closes and reopens inventory for viewing players
    void setCurrentInventory(ViewableInventory inventory);

    // Reopens the Container with the new Title
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
