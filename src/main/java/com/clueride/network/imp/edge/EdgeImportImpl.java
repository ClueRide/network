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
package com.clueride.network.imp.edge;

import java.util.List;

import javax.inject.Inject;

import com.vividsolutions.jts.io.WKTWriter;
import org.geolatte.geom.LineString;
import org.geolatte.geom.Position;
import org.slf4j.Logger;

import com.clueride.network.edge.EdgeBuilder;
import com.clueride.network.edge.EdgeStore;
import static org.geolatte.geom.codec.Wkt.fromWkt;

/**
 * Performs the work of importing Edges from GeoJSON files into a spatially-enabled JPA database.
 */
public class EdgeImportImpl implements EdgeImport {
    @Inject
    private Logger LOGGER;

    @Inject
    private EdgeStore edgeStore;

    @Override
    public int run() {
        WKTWriter wktWriter = new WKTWriter(3);
        EdgeStoreJson edgeStore = new EdgeStoreJson();

        List<JsonBasedEdge> jsonBasedEdges = edgeStore.getEdges();
        for (JsonBasedEdge jsonBasedEdge : jsonBasedEdges) {
            /* Turn the Geometry LineString into a WKT (Line)String */
            String wkt = wktWriter.writeFormatted(jsonBasedEdge.getLineString());
            EdgeBuilder edgeBuilder = EdgeBuilder.builder()
                    .withOriginalEdgeId(jsonBasedEdge.getEdgeId())
                    .withName(jsonBasedEdge.getName())
                    .withTrackReference(jsonBasedEdge.getReferenceString())
                    .withPoints((LineString<Position>)fromWkt(wkt));
            LOGGER.debug(edgeBuilder.toString());

            jpaPersist(edgeBuilder);
        }
        return 0;
    }

    private void jpaPersist(EdgeBuilder edgeBuilder) {
        edgeStore.persist(edgeBuilder);
    }

}
