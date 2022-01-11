package by.vchaikovski.coffeeshop.controller;

import by.vchaikovski.coffeeshop.controller.command.PagePath;

public class Router {
    public enum RouterType {
        FORWARD, REDIRECT
    }

    private RouterType routerType;
    private String pagePath;

    public Router() {
        routerType = RouterType.FORWARD;
        pagePath = PagePath.INDEX_PAGE;
    }

    public RouterType getRouterType() {
        return routerType;
    }

    public void setRouterType(RouterType routerType) {
        this.routerType = routerType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}