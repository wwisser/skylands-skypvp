package me.skylands.skypvp.container;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ContainerManager {

    private Set<Container> containers = new HashSet<>();

    public void destroyContainer(final Container container) {
        this.containers.remove(container);
    }

    public void registerContainer(final Container container) {
        this.containers.add(container);
    }

    public Set<Container> getContainers() {
        return Collections.unmodifiableSet(this.containers);
    }

}
