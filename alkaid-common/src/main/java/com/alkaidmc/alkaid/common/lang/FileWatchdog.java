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

package com.alkaidmc.alkaid.common.lang;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class FileWatchdog {
    Path path;

    Consumer<Path> create;
    Consumer<Path> modify;
    Consumer<Path> delete;

    int delay = 1000;
    Consumer<Exception> exception = Throwable::printStackTrace;

    public void watch() {
        try {
            if (path == null) {
                exception.accept(new NullPointerException("paths is null"));
            }

            WatchService watcher = FileSystems.getDefault().newWatchService();

            Optional.ofNullable(create).ifPresent(c -> {
                try {
                    path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
                } catch (IOException e) {
                    exception.accept(e);
                }
            });

            Optional.ofNullable(modify).ifPresent(m -> {
                try {
                    path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
                } catch (IOException e) {
                    exception.accept(e);
                }
            });

            Optional.ofNullable(delete).ifPresent(d -> {
                try {
                    path.register(watcher, StandardWatchEventKinds.ENTRY_DELETE);
                } catch (IOException e) {
                    exception.accept(e);
                }
            });

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            WatchKey key = watcher.take();
                            key.pollEvents().forEach(event -> {
                                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE && create != null) {
                                    create.accept(path);
                                }
                                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY && modify != null) {
                                    modify.accept(path);
                                }
                                if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE && delete != null) {
                                    delete.accept(path);
                                }
                            });

                            if (!key.reset()) {
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        exception.accept(e);
                    }
                }
            }, delay);

        } catch (IOException e) {
            exception.accept(e);
        }
    }
}
