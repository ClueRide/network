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
package com.clueride.network.path;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Holds the raw fields out of the JSON files for Paths.
 */
@Entity(name = "path")
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "path_pk_sequence")
    @SequenceGenerator(name = "path_pk_sequence", sequenceName = "path_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "original_id")
    private int originalId;

    private String name;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "path"
    )
    private List<PathToEdgeId> edgeIds = new ArrayList<>();

    @Column(name = "start_node_id")
    private int startNodeId;
    @Column(name = "end_node_id")
    private int endNodeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPathToEdgeId(PathToEdgeId pathToEdgeId) {
        edgeIds.add(pathToEdgeId);
    }

    public List<PathToEdgeId> getEdgeIds() {
        return edgeIds;
    }

    public void setEdgeIds(List<PathToEdgeId> edgeIds) {
        this.edgeIds = edgeIds;
    }

    public int getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(int startNodeId) {
        this.startNodeId = startNodeId;
    }

    public int getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(int endNodeId) {
        this.endNodeId = endNodeId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
