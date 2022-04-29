package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.UUID;

public class Category extends AbstractDescribableImpl<Category> {
    private String name;
    private UUID uuid;

    @DataBoundConstructor
    public Category(String name, String uuid) {
        this.name = name;
        this.uuid = (!StringUtils.isBlank(uuid)) ? UUID.fromString(uuid) : UUID.randomUUID();
    }

    @NonNull
    public UUID getUuid() {
        return uuid;
    }

    @CheckForNull
    public String getName() {
        return name;
    }


    @Extension
    public static class DescriptorImpl extends Descriptor<Category> {

    }
}
