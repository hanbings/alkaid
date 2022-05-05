/*
 * Copyright 2022 Alkaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidParseCommandCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidParseTabCallback;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ParseCommandParser {
    final CommandSender sender;
    final String[] args;
    final String[] tokens;
    final int deep;

    public ParseCommandParser parse(String command) {
        if (tokens.length <= 0) {
            return this;
        }

        if (tokens[0].equalsIgnoreCase(command)) {
            return new ParseCommandParser(
                    this.sender,
                    this.args,
                    // token 减少一层 / token delete one level
                    Arrays.copyOfRange(this.tokens, 1, this.tokens.length),
                    // deep 增加一层 / deep add one level
                    this.deep + 1
            );
        }

        return this;
    }

    public void execute(AlkaidParseCommandCallback callback) {
        callback.execute(sender, args, tokens, deep);
    }

    public void tab(AlkaidParseTabCallback callback) {
        callback.tab(sender, args, tokens, deep);
    }
}
