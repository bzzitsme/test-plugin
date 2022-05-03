package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.ExtensionList;
import hudson.util.FormValidation;
import hudson.util.Secret;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.verb.POST;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
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
    private Connection connection;

    public SampleConfiguration() {
        // When Jenkins is restarted, load any saved configuration from disk.
        load();
    }

    public String getName() {
        return name;
    }

    @DataBoundSetter
    public void setName(String name) {
        if (!Util.validateAlphaSpace(name)) {
            throw new RuntimeException("Name should contain only letters (non-case sensitive) and spaces");
        }
        this.name = name;
        save();
    }

    public String getDescription() {
        return description;
    }

    @DataBoundSetter
    public void setDescription(String description) {
        if (!Util.validateAlphaSpace(description)) {
            return;
        }
        this.description = description;
        save();
    }

    public Connection getConnection() {
        return connection;
    }

    @DataBoundSetter
    public void setConnection(Connection connection) {
        this.connection = connection;
        save();
    }

    public synchronized List<Category> getCategories() {
        return categories == null ? Collections.emptyList() : Collections.unmodifiableList(categories);
    }

    public synchronized void setCategories(List<Category> categories) {
        categories = new ArrayList<>(categories == null ? Collections.emptyList() : categories);
        this.categories = categories;
        save();
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        // validation which allows removing whole category list
        if (!json.containsKey("categories")) {
            json.put("categories", Collections.emptyList());
        }
        return super.configure(req, json);
    }

    public FormValidation doCheckName(@QueryParameter String name) {
        if (!Util.validateAlphaSpace(name)) {
            return FormValidation.warning("Name should contain only lowercase and uppercase letters and space");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckUsername(@QueryParameter String username) {
        if (!Util.validateAlphabet(username)) {
            return FormValidation.warning("Username should contain only letters");
        }
        return FormValidation.ok();
    }

    @POST
    public FormValidation doTestConnection(@QueryParameter("url") String url,
                                           @QueryParameter("username") String username,
                                           @QueryParameter("password") Secret password) throws URISyntaxException, IOException {
        URL uri = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty(HttpHeaders.AUTHORIZATION, Util.toBasicAuth(username, password));
        int responseCode = httpURLConnection.getResponseCode();
        if (HttpStatus.OK.value() == responseCode) {
            return FormValidation.ok("Success");
        } else {
            return FormValidation.warning("Connection refused (Http Status: " + responseCode + "), please check your credentials and url");
        }
    }
}
