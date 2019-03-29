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
package com.clueride.imp.gpx;

import java.io.File;

import com.clueride.network.edge.EdgeBuilder;

/**
 * Defines how we turn a GPX-formatted File into EdgeBuilder instance.
 */
public interface GpxToEdge {
    /**
     * Accepts a File representing a single GPX track, and turns it into an instance of EdgeBuilder.
     *
     * @param gpxFile readable GPX file.
     * @return EdgeBuilder with LineString, name, and referenceTrack populated from the file.
     */
    EdgeBuilder edgeFromGpxFile(File gpxFile);

}
