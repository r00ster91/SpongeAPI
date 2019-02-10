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
package org.spongepowered.api.command.parameter;

import org.spongepowered.api.util.ResettableBuilder;

import java.util.List;
import java.util.Set;

/**
 * Builds the completions to send back to the client.
 */
public interface Completions {

    interface Builder extends ResettableBuilder<Completions, Builder> {

        /**
         * Gets the command that is being completed.
         *
         * @return The input string
         */
        String getInput();

        /**
         * Gets the argument(s) that require completion.
         *
         * @return The argument(s)
         */
        String getRemaining();

        /**
         * Add a potential completion to the builder.
         *
         * @param completion The completion.
         */
        Builder addCompletion(String completion);

        /**
         * Adds a collection of potential completions to the builder.
         *
         * @param completions The completions.
         */
        default Builder addCompletions(List<String> completions) {
            completions.forEach(this::addCompletion);
            return this;
        }


    }

}
