/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.app.library.support;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.Service;

import java.util.Map;

/**
 */
public class AppServiceSummaryDTO {
    private final String id;
    private final String name;
    private final String namespace;
    private final String portalIP;
    private final Integer port;
    private final Map<String, String> labels;

    public AppServiceSummaryDTO(Service service) {
        this.id = KubernetesHelper.getId(service);
        this.name = KubernetesHelper.getName(service);
        this.namespace = service.getNamespace();
        this.portalIP = KubernetesHelper.getPortalIP(service);
        this.port = KubernetesHelper.getPort(service);
        this.labels = service.getLabels();
    }

    @Override
    public String toString() {
        return "AppServiceSummaryDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public Integer getPort() {
        return port;
    }

    public String getPortalIP() {
        return portalIP;
    }

    public Map<String, String> getLabels() {
        return labels;
    }
}