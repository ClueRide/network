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
 * Created by jett on 1/13/19.
 */
package com.clueride.network.edge;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.clueride.RecordNotFoundException;

/**
 * JPA-based implementation of {@link EdgeStore}.
 */
public class EdgeStoreJpa implements EdgeStore {

    @Inject
    private EntityManager entityManager;

    @Override
    public EdgeBuilder getEdgeById(Integer id) {
        EdgeBuilder builder = entityManager.find(EdgeBuilder.class, id);
        if (builder == null) {
            throw new RecordNotFoundException("Edge with ID " + id + " not found");
        }
        return builder;
    }

    @Override
    public EdgeBuilder getEdgeByOriginalId(Integer originalId) {
        TypedQuery<EdgeBuilder> tq = entityManager.createQuery("from edge WHERE originalEdgeId=:originalId", EdgeBuilder.class);
        EdgeBuilder builder = tq.setParameter("originalId", originalId).getSingleResult();
        if (builder == null) {
            throw new RecordNotFoundException("Edge with Original ID " + originalId + " not found");
        }
        return builder;
    }

    @Override
    public EdgeBuilder persist(EdgeBuilder edgeBuilder) {
        entityManager.getTransaction().begin();
        entityManager.persist(edgeBuilder);
        entityManager.getTransaction().commit();
        return edgeBuilder;
    }

}
