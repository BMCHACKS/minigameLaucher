package com.minigamelauncher.flappybird;

public class ResourceLoadException extends RuntimeException {

    private final String resourcePath;

    public ResourceLoadException(String resourcePath, Throwable cause) {
        super("Failed to load resource: " + resourcePath, cause);
        this.resourcePath = resourcePath;
    }

    public ResourceLoadException(String resourcePath) {
        super("Resource not found: " + resourcePath);
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
