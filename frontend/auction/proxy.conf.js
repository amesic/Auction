const PROXY_CONFIG = [
    {
        context: [
            "/category",
            "/bid",
            "/product",
            "/users"
        ],
        target:"http://localhost:8080",
        secure: false,
        "changeOrigin": true
    }
]

module.exports = PROXY_CONFIG;

//"https://atlantbh-auction-api.herokuapp.com"