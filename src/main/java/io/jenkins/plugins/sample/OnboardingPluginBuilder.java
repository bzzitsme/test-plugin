package io.jenkins.plugins.sample;

import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.ListBoxModel;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.util.*;
import java.util.Queue;
import java.util.stream.Collectors;

public class OnboardingPluginBuilder extends Builder implements SimpleBuildStep {

    private Category category;

    @DataBoundConstructor
    public OnboardingPluginBuilder(String categoryId) {
        List<Category> categories = SampleConfiguration.get().getCategories();
        category = categories.stream().filter(c -> c.getUuid()
                        .equals(UUID.fromString(categoryId)))
                .collect(Collectors.toList()).stream()
                .findFirst().orElseGet(null);
    }

    @Override
    public void perform(@NonNull Run<?, ?> run, @NonNull FilePath workspace, @NonNull EnvVars env, @NonNull Launcher launcher, @NonNull TaskListener listener) throws InterruptedException, IOException {
        System.out.println(category.getName());
        ((DescriptorImpl)getDescriptor()).addBuild(run.getExternalizableId(), category);
    }
    
    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        private Queue<BuildWithCategory> builds = new LinkedList<>();
        
        public void addBuild(String buildId, Category category) {
            if (Objects.nonNull(builds) && builds.size() >= 5) {
                builds.remove();
            }
            builds.add(new BuildWithCategory(buildId, category));
            save();
        }

        public DescriptorImpl() {
            load();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return FreeStyleProject.class.isAssignableFrom(jobType);
        }

        @Override
        public String getDisplayName() {
            return "Onboarding plugin";
        }

        public ListBoxModel doFillCategoryIdItems(
                @AncestorInPath Item item,
                @QueryParameter String categoryId
        ) {
            StandardListBoxModel result = new StandardListBoxModel();
            List<Category> categories = SampleConfiguration.get().getCategories();
            categories.forEach(category -> result.add(category.getName(), category.getUuid().toString()));
            return result;
        }

        public Queue<BuildWithCategory> getBuilds() {
            return builds;
        }

    }
    
    public static class BuildWithCategory {
        String buildId;
        Category category;
        
        public BuildWithCategory(String buildId, Category category) {
            this.buildId = buildId;
            this.category = category;
        }

        public String getBuildId() {
            return buildId;
        }

        public void setBuildId(String buildId) {
            this.buildId = buildId;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }
    }
}
