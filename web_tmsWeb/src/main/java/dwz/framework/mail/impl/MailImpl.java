package dwz.framework.mail.impl;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import dwz.framework.config.AppConfiguration;
import dwz.framework.mail.Mail;
import dwz.framework.sys.business.AbstractBusinessObject;

public class MailImpl extends AbstractBusinessObject implements Mail {

    /**
     * 
     */
    private static final long      serialVersionUID    = -6724779372853699727L;

    private final Map<String, Object>  content             = new HashMap<>();

    private static final String    CHARSET             = "UTF-8";
    private final boolean          isHtmlBody;
    private final HtmlEmail        mail;

    protected MailImpl(boolean isHtmlBody) {
        AppConfiguration appconfig = getAppConfig();
        String host = appconfig.getString("app.mail.server.host");
        // String protocol = appconfig.getString("app.mail.server.protocol");
        String username = appconfig.getString("app.default.username");
        String password = appconfig.getString("app.default.password");
        int port = appconfig.getInt("app.mail.server.port");
        boolean isSecurity = appconfig.getBoolean("app.mail.server.isSecurity");
        boolean isDebug = appconfig.getBoolean("app.mail.server.isDebug");

        this.isHtmlBody = isHtmlBody;

        mail = new HtmlEmail();
        mail.setCharset(CHARSET);
        mail.addHeader("X-Mailer", "WEB MAILER 1.0.1");
        mail.setDebug(isDebug);

        mail.setHostName(host);
        mail.setAuthentication(username, password);
        mail.setSmtpPort(port);

        if (isSecurity)
            mail.setSSL(true);
    }

    HtmlEmail getMail() {
        return mail;
    }

    @Override
    public void addAttache(URL url, String name, String desc)
            throws EmailException {
        mail.attach(url, name, desc);
    }

    @Override
    public void addAttache(String path, String name, String desc)
            throws EmailException {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(path);
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setName(name);
        attachment.setDescription(desc);

        mail.attach(attachment);
    }

    @Override
    public void addBcc(String email) throws EmailException {
        mail.addBcc(email);

    }

    @Override
    public void addBcc(String email, String name) throws EmailException {
        mail.addBcc(email, name);
    }

    @Override
    public void addCc(String email) throws EmailException {
        mail.addCc(email);
    }

    @Override
    public void addCc(String email, String name) throws EmailException {
        mail.addBcc(email, name);
    }

    /**
     *
     * @param key
     * @param o
     */
    @Override
    public void addContent(String key, Object o) {
        content.put(key, o);
    }

    public Map<String, Object> getContentMap() {
        return content;
    }

    @Override
    public void addReplyTo(String email) throws EmailException {
        mail.addReplyTo(email);
    }

    @Override
    public void addReplyTo(String email, String name) throws EmailException {
        mail.addReplyTo(email, name);
    }

    @Override
    public void addTo(String email) throws EmailException {
        mail.addTo(email);
    }

    @Override
    public void addTo(String email, String name) throws EmailException {

        mail.addBcc(email, name);
    }

    public void clear() {
        content.clear();
    }

    public String getSubject() {
        return mail.getSubject();
    }

    @Override
    public void setFrom(String email) throws EmailException {
        mail.setFrom(email);
    }

    /**
     *
     * @param msg
     * @throws EmailException
     */
    @Override
    public void setMsg(String msg) throws EmailException {
        if (isHtmlBody)
            mail.setHtmlMsg(msg);
        else
            mail.setTextMsg(msg);
    }

    /**
     *
     * @param subject
     */
    @Override
    public void setSubject(String subject) {
        mail.setSubject(subject);
    }

    /**
     *
     * @return
     */
    @Override
    public Serializable getId() {
        return null;
    }

}
