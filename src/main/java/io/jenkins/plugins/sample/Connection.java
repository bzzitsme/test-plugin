package io.jenkins.plugins.sample;

import hudson.tasks.Builder;
import hudson.util.Secret;
import org.kohsuke.stapler.DataBoundSetter;

public class Connection extends Builder {
    
    private String url;
    private String username;
    private Secret password;
    
    @DataBoundSetter
    public void setUrl(String url) {
        this.url = url;
    }

    @DataBoundSetter
    public void setUsername(String username) {
        this.username = username;
    }

    @DataBoundSetter
    public void setPassword(Secret password) {
        this.password = password;
    }
}
