const PROXY_CONFIG = [
    {
        context: [
            "/category",
            "/bid",
            "/product",
            "/users"
        ],
        target:"https://atlantbh-auction-api.herokuapp.com",
        secure: false,
        "changeOrigin": true
    }
]

module.exports = PROXY_CONFIG;

//"http://localhost:8080"