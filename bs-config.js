module.exports = {
    proxy: "localhost:8080",
    files: [
        "src/main/resources/templates/**/*.html",
        "src/main/resources/static/**/*.css",
        "src/main/resources/static/**/*.js",
        "src/main/java/**/*.java"
    ],
    open: true,
    notify: true,
    reloadDelay: 0,
    reloadDebounce: 0,
    reloadThrottle: 0,
    watchEvents: ["change", "add", "unlink", "addDir", "unlinkDir"],
    watch: true,
    watchOptions: {
        ignoreInitial: true,
        ignored: ["node_modules", "target"]
    }
}; 