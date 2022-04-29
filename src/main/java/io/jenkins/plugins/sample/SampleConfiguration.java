package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.ExtensionList;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Example of Jenkins global configuration.
 */
@Extension
public class SampleConfiguration extends GlobalConfiguration {

    /**
     * @return the singleton instance
     */
    public static SampleConfiguration get() {
        return ExtensionList.lookupSingleton(SampleConfiguration.class);
    }

    private String name;
    private String description;
    private List<Category> categories;

    public SampleConfiguration() {
        // When Jenkins is restarted, load any saved configuration from disk.
        load();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @DataBoundSetter
    public void setName(String name) {
        if (!Util.validateWithRegex(name)) {
            return;
        }
        this.name = name;
        save();
    }

    @DataBoundSetter
    public void setDescription(String description) {
        if (!Util.validateWithRegex(description)) {
            return;
        }
        this.description = description;
        save();
    }

    public FormValidation doCheckName(@QueryParameter String name) {
        if (!Util.validateWithRegex(name)) {
            return FormValidation.warning("Name should contain only lowercase and uppercase letters and space");
        }
        return FormValidation.ok();
    }

    public synchronized List<Category> getCategories() {
        return categories == null ? Collections.emptyList() : Collections.unmodifiableList(categories);
    }

    public synchronized void removeCategories(List<Category> categories) {
        List<Category> categories_ = new ArrayList<>(getCategories());
        categories.removeAll(categories);
        setCategories(categories_);
    }

    public synchronized void setCategories(List<Category> categories) {
        categories = new ArrayList<>(categories == null ? Collections.emptyList() : categories);
        this.categories = categories;
        save();
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        // validation which allows to remove whole category list
        if (!json.containsKey("categories")) {
            json.put("categories", Collections.emptyList());
        }
        return super.configure(req, json);
    }
}
