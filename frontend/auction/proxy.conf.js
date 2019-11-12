const PROXY_CONFIG = [
    {
        context: [
            "/category",
            "/bid",
            "/product",
            "/users"
        ],
        target: "http://localhost:8080",
        secure: false
    }
]

module.exports = PROXY_CONFIG;