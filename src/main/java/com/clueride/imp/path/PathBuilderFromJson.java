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

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Builder instance for Paths that come out of the JSON files.
 */
public class PathBuilderFromJson {
    private Integer id;

    //        private List<PathToEdgeId> edges;

    private List<Integer> edgeIds;
    private Integer startNodeId;
    private Integer endNodeId;
    private int segments;

    private int startLocationId;
    private int endLocationId;

//    public Path build() {

//        return new Path(this);
//    }
    public static PathBuilderFromJson builder() {
        return new PathBuilderFromJson();
    }

    public PathBuilderFromJson withStartNodeId(Integer startNodeId) {
        this.startNodeId = startNodeId;
        return this;
    }

    public PathBuilderFromJson withEndNodeId(Integer endNodeId) {
        this.endNodeId = endNodeId;
        return this;
    }

    public PathBuilderFromJson addEdgeId(Integer edgeId) {
        this.edgeIds.add(edgeId);
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStartNodeId() {
        return startNodeId;
    }

    public Integer getEndNodeId() {
        return endNodeId;
    }

    public List<Integer> getEdgeIds() {
        return edgeIds;
    }

    public PathBuilderFromJson setEdgeIds(List<Integer> edgeIds) {
        this.edgeIds = edgeIds;
        return this;
    }


    /* Perhaps empty in all cases. */
    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public int getStartLocationId() {
        return startLocationId;
    }

    public void setStartLocationId(int startLocationId) {
        this.startLocationId = startLocationId;
    }

    public int getEndLocationId() {
        return endLocationId;
    }

    public void setEndLocationId(int endLocationId) {
        this.endLocationId = endLocationId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
