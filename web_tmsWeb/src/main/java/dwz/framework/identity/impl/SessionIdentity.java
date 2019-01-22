package dwz.framework.identity.impl;

import java.io.Serializable;

import dwz.framework.identity.Identity;

public class SessionIdentity implements Identity {

    private Serializable accessToken = null;

    public SessionIdentity(Serializable accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Serializable getAccessToken() {
        return this.accessToken;
    }

    @Override
    public void setAccessToken(Serializable accessToken) {
        this.accessToken = accessToken;
    }

}
