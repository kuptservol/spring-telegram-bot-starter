package ru.skuptsov.telegram.bot.platform.config;

import com.ning.http.client.ProxyServer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Sergey Kuptsov
 * @since 29/01/2017
 */
@Configuration
@ConfigurationProperties(prefix = "telegram.client.proxy")
public class ClientProxyConfiguration {

    private ProxyServer.Protocol protocol = ProxyServer.Protocol.HTTP;
    private String host;
    private Integer port;
    private String principal;
    private String password;

    private ProxyServer proxyServer;

    public void setProtocol(ProxyServer.Protocol protocol) {
        this.protocol = protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    @PostConstruct
    public void init() {
        if (host != null && port != null) {
            proxyServer = new ProxyServer(protocol, host, port, principal, password);
        }
    }
}
