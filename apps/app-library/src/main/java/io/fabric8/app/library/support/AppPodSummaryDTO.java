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
import io.fabric8.kubernetes.api.PodStatus;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerManifest;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodState;
import io.fabric8.kubernetes.api.model.Port;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
public class AppPodSummaryDTO {
    private final String id;
    private final String namespace;
    private final PodStatus status;
    private final Map<String, String> labels;
    private final Set<Integer> containerPorts = new HashSet<>();
    private final String creationTimestamp;
    private String podIP;
    private String host;

    public AppPodSummaryDTO(Pod pod) {
        this.id = KubernetesHelper.getId(pod);
        this.namespace = pod.getNamespace();
        this.status = KubernetesHelper.getPodStatus(pod);
        this.labels = pod.getLabels();
        this.creationTimestamp = pod.getCreationTimestamp();

        PodState currentState = pod.getCurrentState();
        PodState desiredState = pod.getDesiredState();
        if (currentState != null) {
            this.podIP = currentState.getPodIP();
            this.host = currentState.getHost();
        }
        if (desiredState != null) {
            ContainerManifest manifest = desiredState.getManifest();
            if (manifest != null) {
                List<Container> containers = manifest.getContainers();
                if (containers != null) {
                    for (Container container : containers) {
                        List<Port> ports = container.getPorts();
                        if (ports != null) {
                            for (Port port : ports) {
                                Integer containerPort = port.getContainerPort();
                                if (containerPort != null) {
                                    containerPorts.add(containerPort);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "AppPodSummaryDTO{" +
                "id='" + id + '\'' +
                ", namespace='" + namespace + '\'' +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public PodStatus getStatus() {
        return status;
    }

    public String getNamespace() {
        return namespace;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public Set<Integer> getContainerPorts() {
        return containerPorts;
    }

    public String getPodIP() {
        return podIP;
    }

    public String getHost() {
        return host;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }
}
