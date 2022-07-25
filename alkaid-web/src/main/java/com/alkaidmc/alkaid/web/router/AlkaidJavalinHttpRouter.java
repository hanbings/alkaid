package com.alkaidmc.alkaid.web.router;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused SpellCheckingInspection")
public class AlkaidJavalinHttpRouter {
    final Javalin app;
    final HandlerType method;
    final String path;
    final Consumer<Context> handler;
    Consumer<Context> none = ctx -> ctx.result("404");

    List<Predicate<Context>> filters = new ArrayList<>();

    public AlkaidJavalinHttpRouter(Javalin app, HandlerType method, String path, Consumer<Context> handler) {
        this.app = app;
        this.method = method;
        this.path = path;
        this.handler = handler;

        app.addHandler(method, path, ctx -> {
            if (filters.stream().allMatch(filter -> filter.test(ctx))) {
                handler.accept(ctx);
                return;
            }
            none.accept(ctx);
        });
    }

    public AlkaidJavalinHttpRouter filter(Predicate<Context> filter) {
        filters.add(filter);

        return this;
    }
}
