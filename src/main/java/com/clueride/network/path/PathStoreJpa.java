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

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Persistence layer for Paths.
 */
public class PathStoreJpa {

    @Inject
    private EntityManager entityManager;

    /**
     * Saves a new instance of Path to the database.
     *
     * The passed instance contains a one-to-many relationship with
     * Edges. These Edges instances should be assembled prior to persisting
     * this instance.
     *
     * @param path instance to persist.
     */
    public void persist(Path path) {
        entityManager.getTransaction().begin();
        entityManager.persist(path);
        entityManager.getTransaction().commit();
    }
}
