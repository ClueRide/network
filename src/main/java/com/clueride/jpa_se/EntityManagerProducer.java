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
package com.clueride.jpa_se;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * From the Factory, instantiate the EntityManager.
 */
public class EntityManagerProducer {
    @Inject
    transient EntityManagerFactory emf;

    @Produces
//    @RequestScoped
    public EntityManager create() {
        return emf.createEntityManager();
    }

    public void destroy(@Disposes EntityManager em) {
        em.close();
    }

}
