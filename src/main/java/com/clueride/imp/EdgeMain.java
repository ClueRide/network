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
 * Created by jett on 1/15/19.
 */
package com.clueride.imp;

import java.lang.invoke.MethodHandles;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clueride.imp.edge.EdgeImport;

/**
 * Utility to read and persist Edges from GeoJSON into the
 * spatially-enabled database.
 */
public class EdgeMain {
    private static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        Weld weld = new Weld();
        /* Takes advantage of auto-closeable to release resources should an exception get thrown. */
        try (WeldContainer container = weld.initialize()) {
            container.select(EdgeImport.class).get().run();
        }
    }

}
