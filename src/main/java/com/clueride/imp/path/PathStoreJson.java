/*
 * Copyright 2019 Jett Marks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by jett on 1/20/19.
 */
package com.clueride.imp.path;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clueride.imp.common.JsonStoreLocation;
import com.clueride.imp.common.JsonStoreType;

/**
 * Brings JSON files to populate Path entities.
 */
public class PathStoreJson {
    private static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<PathBuilderFromJson> loadPaths() {
        final JsonStoreType storeType = JsonStoreType.PATH;
        List<PathBuilderFromJson> paths = new ArrayList<>();
        File directory = new File(JsonStoreLocation.toString(storeType));
        if (!directory.canWrite()) {
            if (!directory.mkdir()) {
                throw new RuntimeException("Unable to create directory " + directory.getName());
            }
        } else {
            for (File child : directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
//                    LOGGER.debug("Checking for file at " + s);
                    return s.contains("path");
                }
            })) {
//                LOGGER.debug("Checking for JSON file in the directory " + child.getName());
                for (File file : child.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File file2, String s) {
                        return s.startsWith("path") && s.endsWith(".json");
                    }
                })) {
                    paths.add(loadPath(file));
                }
            }
        }
        return paths;
    }

    private static PathBuilderFromJson loadPath(File file) {
        PathBuilderFromJson builder;
        try {
            synchronized (objectMapper) {
                builder = objectMapper.readValue(file, PathBuilderFromJson.class);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected I/O error", e);
        }
        return builder;
    }

}
