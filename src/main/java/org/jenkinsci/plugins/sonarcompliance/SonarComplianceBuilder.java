package org.jenkinsci.plugins.sonarcompliance;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.ParameterValue;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.StringParameterValue;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.jenkinsci.plugins.sonarcompliance.common.ComplianceConfigurator;
import org.jenkinsci.plugins.sonarcompliance.executors.ComplianceExecutorFactory;
import org.jenkinsci.plugins.sonarcompliance.interfaces.Executor;
import org.jenkinsci.plugins.sonarcompliance.model.ComplianceRule;
import org.jenkinsci.plugins.sonarcompliance.model.Result;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 *
 * <p>
 * Sonar Compliance Builder 
 *
 * <p>
 *
 * @author Ramesh Dara
 */
public class SonarComplianceBuilder extends Builder {

    private final String name;
    private final String primarySonarURL;
    private final String primaryProjectKey;
    private final String secondarySonarURL;
    private final String secondaryProjectKey;
    private final String complianceRules;
    private final Boolean sonarComparison;
    private final Boolean additionalSonar;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public SonarComplianceBuilder(String name, String complianceRules, String primarySonarURL,
            String primaryProjectKey, boolean sonarComparison, String secondarySonarURL, String secondaryProjectKey,
            boolean additionalSonar) {
        this.name = name;
        this.primarySonarURL = primarySonarURL;
        this.primaryProjectKey = primaryProjectKey;
        this.secondarySonarURL = secondarySonarURL;
        this.secondaryProjectKey = secondaryProjectKey;
        this.complianceRules = complianceRules;
        this.sonarComparison = sonarComparison;
        this.additionalSonar = additionalSonar;
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {

        ComplianceConfigurator complianceConfigurator = new ComplianceConfigurator(this, build, listener);
        complianceConfigurator.setLogger(listener.getLogger());

        Executor executor = ComplianceExecutorFactory.getComplianceExecutor(complianceConfigurator);
        boolean buildResult = executor.execute(complianceConfigurator);

        List<ParameterValue> params = new ArrayList<ParameterValue>();
        for (ComplianceRule rule : complianceConfigurator.getAllComplianceRules().values()) {
            params.add(new StringParameterValue(rule.getKey(), rule.getRule()));
            params.add(new StringParameterValue(rule.getKey() + "_RESULT", rule.getResult().toString()));
            listener.getLogger().println(rule.getKey());
            if (rule.getResult() == Result.EXCEPTION) {
                params.add(new StringParameterValue(rule.getKey() + "_EXCEPTION", rule.getExceptionMessage()));
                rule.getException().printStackTrace(listener.getLogger());
            }
        }
        for (ParameterValue param : params) {
            param.createVariableResolver(build);
        }
        build.addAction(new SonarComplainceParametersAction("SC_" + getName(), params));

        return buildResult;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    /**
     * Descriptor for {@link SonarComplianceBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See <tt>src/main/resources/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension(optional = true)
    // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        /**
         * To persist global configuration information,
         * simply store it in a field and call save().
         *
         * <p>
         * If you don't want fields to be persisted, use <tt>transient</tt>.
         */

        /**
         * In order to load the persisted global configuration, you have to 
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'name'.
         *
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         *      <p>
         *      Note that returning {@link FormValidation#error(String)} does not
         *      prevent the form from being saved. It just means that a message
         *      will be displayed to the user. 
         */
        public FormValidation doCheckPrimarySonarURL(@QueryParameter String value) throws IOException, ServletException {
            try {
                new URL(value).openStream().close();
            } catch (Exception e) {
                return FormValidation.error("Can't connect to " + value + ". " + e.getMessage());
            }
            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Sonar Compliance";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            //useFrench = formData.getBoolean("useFrench");
            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            req.bindJSON(this, formData);
            save();
            return super.configure(req, formData);
        }

        /**
         * This method returns true if the global configuration says we should speak French.
         *
         * The method name is bit awkward because global.jelly calls this method to determine
         * the initial state of the checkbox by the naming convention.
        public boolean getUseFrench() {
            return useFrench;
        }
        */
    }

    public String getPrimarySonarURL() {
        return primarySonarURL;
    }

    public String getPrimaryProjectKey() {
        return primaryProjectKey;
    }

    public String getSecondaryProjectKey() {
        return secondaryProjectKey;
    }

    public String getComplianceRules() {
        return complianceRules;
    }

    public Boolean getSonarComparison() {
        return sonarComparison;
    }

    public String getSecondarySonarURL() {
        return secondarySonarURL;
    }

    public Boolean getAdditionalSonar() {
        return additionalSonar;
    }

    public String getName() {
        return name;
    }
}
