package by.vchaikovski.coffeeshop.controller;

public class Router {
    public enum RouterType {
        FORWARD, REDIRECT
    }

    private RouterType routerType;
    private String pagePath;

    public Router(String pagePath) {
        routerType = RouterType.FORWARD;
        this.pagePath =pagePath;
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