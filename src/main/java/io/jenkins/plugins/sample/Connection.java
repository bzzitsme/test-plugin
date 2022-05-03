package io.jenkins.plugins.sample;

import hudson.tasks.Builder;
import hudson.util.Secret;
import org.kohsuke.stapler.DataBoundConstructor;

public class Connection extends Builder {

    private String url;
    private String username;
    private Secret password;

    @DataBoundConstructor
    public Connection(String url, String username, Secret password) {
        if (!Util.validateAlphabet(username)) {
            throw new RuntimeException("Username should contain only letters");
        }
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public Secret getPassword() {
        return password;
    }
}
