package org.jenkinsci.plugins.sonarcompliance.interfaces;

/**
 * @author Ramesh Dara
 *
 */

public interface Executor {
    public boolean execute();

    public boolean execute(Configurator conf);
}
