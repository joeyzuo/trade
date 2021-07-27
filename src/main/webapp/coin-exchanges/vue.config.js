module.exports = {
    devServer: {
        proxy: {
            '/trade': {
                target: "http://127.0.0.1:8101/",
                //target: "http://10.26.6.130:8103",
                changeOrigin: true,

            }
        },
        host:"127.0.0.1",
        port:"8800"

    }
};
