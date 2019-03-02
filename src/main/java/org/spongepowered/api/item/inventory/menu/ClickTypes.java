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

package org.spongepowered.api.item.inventory.menu;

import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

/**
 * An enumeration of the click types in {@link Container}s.
 */
public class ClickTypes {

    // SORTFIELDS:ON

    // TODO impl - PICKUP
    // 0 left 1 right
    /**
     * Left click on a slot.
     */
    public static final ClickType CLICK_LEFT = DummyObjectProvider.createFor(ClickType.class, "click_left");
    /**
     * Right click on a slot.
     */
    public static final ClickType CLICK_RIGHT = DummyObjectProvider.createFor(ClickType.class, "click_right");
    // TODO impl -  QUICK_MOVE 0 left 1 right
    /**
     * Shift-Left click on a slot.
     */
    public static final ClickType SHIFT_CLICK_LEFT = DummyObjectProvider.createFor(ClickType.class, "shift_click_left");
    /**
     * Shift-Right click on a slot.
     */
    public static final ClickType SHIFT_CLICK_RIGHT = DummyObjectProvider.createFor(ClickType.class, "shift_click_right");
    // TODO impl - SWAP 1-9 key
    // TODO needs 2 slots hovered and KEY
    /**
     * This click-type is used when using a number key-press to swap the corresponding hotbar slot with the slot hovered over.
     * <p>The primary slot is the hovered slot.</p>
     * <p>The secondary slot is the hotbar slot.</p>
     */
    public static final ClickType KEY_SWAP = DummyObjectProvider.createFor(ClickType.class, "key_swap");
    // TODO impl - CLONE middle mouse
    /**
     * Used to clone the clicked item onto the cursor in creative.
     */
    public static final ClickType CLICK_MIDDLE = DummyObjectProvider.createFor(ClickType.class, "click_middle");
    // TODO impl - THROW
    // 0 Q      /w slot -999 cursor left
    // 1 ctrl+Q /w slot -999 cursor right
    /**
     * Throwing one item in the hovered slot using the throw item key.
     */
    public static final ClickType KEY_THROW_ONE = DummyObjectProvider.createFor(ClickType.class, "key_throw_one");
    /**
     * Throwing all items in the hovered slot using the throw item key.
     */
    public static final ClickType KEY_THROW_ALL = DummyObjectProvider.createFor(ClickType.class, "key_throw_all");
    /**
     * Throwing one item on the cursor by clicking outside the inventory window.
     */
    public static final ClickType CLICK_THROW_ONE = DummyObjectProvider.createFor(ClickType.class, "click_throw_one");
    /**
     * Throwing all items on the cursor by clicking outside the inventory window.
     */
    public static final ClickType CLICK_THROW_ALL = DummyObjectProvider.createFor(ClickType.class, "click_throw_all");
    // TODO impl - QUICK_CRAFT drag
    // 0 left start 1 left add 2 left end
    // 4 right start 5 right add 6 right end
    // 8 middle start 9 middle add 10 middle end
    /**
     * Starting Left-click drag motion.
     */
    public static final ClickType DRAG_LEFT_START = DummyObjectProvider.createFor(ClickType.class, "drag_left_start");
    /**
     * Left-click drag motion.
     */
    public static final ClickType DRAG_LEFT_ADD = DummyObjectProvider.createFor(ClickType.class, "drag_left_add");
    /**
     * Stopping Left-click drag motion. This evenly distributes items on the cursor in all previously added slots.
     */
    public static final ClickType DRAG_LEFT_END = DummyObjectProvider.createFor(ClickType.class, "drag_left_end");
    /**
     * Starting Right-click drag motion.
     */
    public static final ClickType DRAG_RIGHT_START = DummyObjectProvider.createFor(ClickType.class, "drag_right_start");
    /**
     * Right-click drag motion.
     */
    public static final ClickType DRAG_RIGHT_ADD = DummyObjectProvider.createFor(ClickType.class, "drag_right_add");
    /**
     * Stopping Right-click drag motion. This adds one item from the cursor to all previously added slots.
     */
    public static final ClickType DRAG_RIGHT_END = DummyObjectProvider.createFor(ClickType.class, "drag_right_end");
    /**
     * Starting Middle-click drag motion.
     */
    public static final ClickType DRAG_MIDDLE_START = DummyObjectProvider.createFor(ClickType.class, "drag_middle_start");
    /**
     * Middle-click drag motion.
     */
    public static final ClickType DRAG_MIDDLE_ADD = DummyObjectProvider.createFor(ClickType.class, "drag_middle_add");
    /**
     * Stopping Middle-click drag motion. In creative mode this fills all previously added slots with full sized stacks from the cursor item.
     */
    public static final ClickType DRAG_MIDDLE_END = DummyObjectProvider.createFor(ClickType.class, "drag_middle_end");
    // TODO impl - PICKUP_ALL dbl-click collect to cursor
    /**
     * Collects as much items of the same type as possible to the cursor.
     */
    public static final ClickType PICKUP_ALL = DummyObjectProvider.createFor(ClickType.class, "pickup_all");

    // SORTFIELDS:FF
}

