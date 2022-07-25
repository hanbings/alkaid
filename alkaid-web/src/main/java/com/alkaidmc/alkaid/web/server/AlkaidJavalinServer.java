package com.alkaidmc.alkaid.web.server;

import com.alkaidmc.alkaid.web.router.AlkaidJavalinHttpRouter;
import com.alkaidmc.alkaid.web.router.AlkaidJavalinWebsocketRouter;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.util.function.Consumer;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
@SuppressWarnings("unused SpellCheckingInspection")
public class AlkaidJavalinServer {
    String host = "127.0.0.1";
    int port = 8080;
    boolean ssl = false;
    ClassLoader loader = this.getClass().getClassLoader();
    String cert;
    String password;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Javalin app;

    public AlkaidJavalinServer server() {
        // 切换 loader / switch loader
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(loader);

        // 初始化服务 / init server
        app = Javalin.create(c ->
                        c.server(() -> {
                            Server server = new Server();
                            // 选择是否使用 https
                            @SuppressWarnings("all")
                            ServerConnector connector
                                    = ssl()
                                    ? new ServerConnector(server,
                                    new SslContextFactory.Server() {{
                                        setKeyStorePath(cert());
                                        setKeyStorePassword(password());
                                    }})
                                    : new ServerConnector(server);
                            connector.setPort(port());
                            server.setConnectors(new Connector[]{connector});
                            return server;
                        }))
                .start(port());

        // 还原 loader / restore loader
        Thread.currentThread().setContextClassLoader(classloader);

        return this;
    }

    public AlkaidJavalinServer application(Consumer<Javalin> consumer) {
        consumer.accept(app);
        return this;
    }

    public AlkaidJavalinHttpRouter get(String path, Consumer<Context> handler) {
        return new AlkaidJavalinHttpRouter(app, HandlerType.GET, path, handler);
    }

    public AlkaidJavalinHttpRouter post(String path, Consumer<Context> handler) {
        return new AlkaidJavalinHttpRouter(app, HandlerType.POST, path, handler);
    }

    public AlkaidJavalinHttpRouter put(String path, Consumer<Context> handler) {
        return new AlkaidJavalinHttpRouter(app, HandlerType.PUT, path, handler);
    }

    public AlkaidJavalinHttpRouter delete(String path, Consumer<Context> handler) {
        return new AlkaidJavalinHttpRouter(app, HandlerType.DELETE, path, handler);
    }

    public void webscoket(String path, Consumer<AlkaidJavalinWebsocketRouter> websocket) {
        websocket.accept(new AlkaidJavalinWebsocketRouter(app, path));
    }
}
