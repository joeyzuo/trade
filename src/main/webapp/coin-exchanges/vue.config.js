module.exports = {
    devServer: {
        proxy: {
            '/': {
                target: "http://localhost:8080/",
                // target: "http://10.26.6.130:8103",
                changeOrigin: true,
                pathRewrite: {
                    '^/': 'trade' // 重写路径
                }
            }
        },
        host:"127.0.0.1",
        port:"8800"

    }
};
