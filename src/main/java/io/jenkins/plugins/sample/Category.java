package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("name='").append(name).append('\'');
        sb.append(", UUID='").append(uuid).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }

        Category category = (Category) o;

        if (!Objects.equals(name, category.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Category> {

        private static final Logger LOGGER = Logger.getLogger(DescriptorImpl.class.getName());

        @Override
        public String getDisplayName() {
            return "";
        }
    }
}
