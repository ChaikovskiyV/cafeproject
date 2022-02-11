package by.vchaikovski.coffeehouse.controller;

import by.vchaikovski.coffeehouse.controller.command.PagePath;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Router.
 */

public class Router {
    /**
     * The enum Router type.
     */
    public enum RouterType {
        /**
         * Forward router type.
         */
        FORWARD,
        /**
         * Redirect router type.
         */
        REDIRECT
    }

    private RouterType routerType;
    private String pagePath;

    /**
     * Instantiates a new Router.
     *
     * @param pagePath the page path
     */
    public Router(String pagePath) {
        routerType = RouterType.FORWARD;
        this.pagePath = pagePath != null ? pagePath : PagePath.MAIN_PAGE;
    }

    /**
     * Gets router type.
     *
     * @return the router type
     */
    public RouterType getRouterType() {
        return routerType;
    }

    /**
     * Sets router type.
     *
     * @param routerType the router type
     */
    public void setRouterType(RouterType routerType) {
        this.routerType = routerType;
    }

    /**
     * Gets page path.
     *
     * @return the page path
     */
    public String getPagePath() {
        return pagePath;
    }

    /**
     * Sets page path.
     *
     * @param pagePath the page path
     */
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}