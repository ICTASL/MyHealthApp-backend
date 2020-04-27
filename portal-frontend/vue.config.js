module.exports = {
    devServer: {
        proxy: {
            '^/api': { 
                target: 'http://localhost:8000', 
                changeOrigin: true, //to avoid cors errors when testing
                secure:false, 
                logLevel: 'debug' 
            }
        }
    }
}
  