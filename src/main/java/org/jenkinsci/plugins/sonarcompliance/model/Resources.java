package org.jenkinsci.plugins.sonarcompliance.model;

import java.util.List;

/**
 * @author Ramesh Dara
 *
 */

public class Resources {
    List<Resource> resource;

    public List<Resource> getResource() {
        return resource;
    }

    public void setResource(List<Resource> resource) {
        this.resource = resource;
    }

}
