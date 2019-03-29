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
 * Created by jett on 3/17/19.
 */
package com.clueride.imp;

import java.io.File;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.clueride.imp.gpx.GpxToEdge;
import com.clueride.imp.gpx.GpxToEdgeImpl;
import com.clueride.network.edge.EdgeBuilder;
import com.clueride.network.edge.EdgeStore;

/**
 * Main program for importing GPX-formatted files as Edge instances.
 */
public class GpxMain {

    private static EdgeStore edgeStore;

    /**
     * Entry point accepting list of files to be imported.
     *
     * @param args expected to be list of readable file names.
     */
    public static void main(String[] args) {
        Weld weld = new Weld();
        /* Takes advantage of auto-closeable to release resources should an exception get thrown. */
        try (WeldContainer container = weld.initialize()) {
            edgeStore = container.select(EdgeStore.class).get();

            GpxToEdge gpxToEdge = new GpxToEdgeImpl();

            for (String fileName : args) {
                try {
                    File inputFile = validate(fileName);
                    EdgeBuilder edgeBuilder = gpxToEdge.edgeFromGpxFile(inputFile);
                    System.out.println(edgeBuilder);
                    persist(edgeBuilder);
                } catch (RuntimeException rte) {
                    System.err.println("Unable to read " + fileName);
                }

            }
        }

    }

    private static void persist(EdgeBuilder edgeBuilder) {
        EdgeBuilder persistedEdge = edgeStore.persist(edgeBuilder);
        System.out.println(
                String.format(
                        "New Record for %s with ID %d",
                        persistedEdge.getName(),
                        persistedEdge.getId()
                )
        );
    }

    /**
     * Check that the file can be opened for reading.
     *
     * @param fileName String name of the file; full path to the file is expected.
     * @return The opened and readable {@link File}.
     */
    private static File validate(String fileName) {
        File file = new File(fileName);
        if (!file.canRead()) {
            throw new RuntimeException("Unable to read file: " + fileName);
        }
        return file;
    }

}
