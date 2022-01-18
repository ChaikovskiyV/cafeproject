package by.vchaikovski.coffeeshop.util;

import jakarta.servlet.http.HttpServletRequest;

public class PageExtractor {
    private static final String PAGE_START = "/controller?";
    private static PageExtractor instance;

    private PageExtractor() {
    }

    public static PageExtractor getInstance() {
        if(instance == null) {
            instance = new PageExtractor();
        }
        return instance;
    }

    public String extractCurrentPage(HttpServletRequest request) {
        String command = request.getQueryString();

        return PAGE_START.concat(command);
    }
}