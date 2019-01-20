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
 * Created by jett on 1/19/19.
 */
package com.clueride.imp.path;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;

import com.clueride.network.edge.EdgeBuilder;
import com.clueride.network.edge.EdgeStoreJpa;
import com.clueride.network.path.Path;
import com.clueride.network.path.PathStoreJpa;
import com.clueride.network.path.PathToEdgeId;

/**
 * Performs the work of importing Edges from GeoJSON files into a spatially-enabled JPA database.
 */
public class PathImportImpl implements PathImport {
    @Inject
    private Logger LOGGER;

    @Inject
    private PathStoreJson pathStore;

    @Inject
    private EdgeStoreJpa edgeStore;

    @Inject
    private PathStoreJpa pathStoreJpa;

    @Override
    public int run() {
        Path path;

        List<PathBuilderFromJson> builders = pathStore.loadPaths();
        for (PathBuilderFromJson pathBuilderFromJson : builders) {
            LOGGER.debug(pathBuilderFromJson.toString());

            /* Turn PathBuilder into Sourced Path. */
            Path newPath = new Path();
            newPath.setOriginalId(pathBuilderFromJson.getId());
            newPath.setStartNodeId(pathBuilderFromJson.getStartNodeId());
            newPath.setEndNodeId(pathBuilderFromJson.getEndNodeId());

            int order = 1;
            for (int edgeId : pathBuilderFromJson.getEdgeIds()) {
                EdgeBuilder edgeBuilder = edgeStore.getEdgeByOriginalId(edgeId);

                LOGGER.debug("Path {} has Edge with original ID of {} and new ID of {}",
                        pathBuilderFromJson.getId(),
                        edgeBuilder.getOriginalEdgeId(),
                        edgeBuilder.getId()
                        );

                /* Build Path to Edge record and link to Path. */
                PathToEdgeId pathToEdgeId = new PathToEdgeId();
                pathToEdgeId.setOrder(order++);
                pathToEdgeId.setEdgeId(edgeBuilder.getId());
                pathToEdgeId.setPath(newPath);
                newPath.addPathToEdgeId(pathToEdgeId);
            }

            jpaPersist(newPath);

        }
        return 0;
    }

    private void jpaPersist(Path path) {
        LOGGER.debug(path.toString());
        pathStoreJpa.persist(path);
    }

}
