module.exports = {
    devServer: {
        proxy: {
            '^/api': { 
                target: 'http://localhost:8000', 
                changeOrigin: true, 
                secure:false, 
                pathRewrite: {'^/api': '/api'}, 
                logLevel: 'debug' 
            }
        }
    }
}
  