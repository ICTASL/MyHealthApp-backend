import store from '../store'
const axios = require('axios').default;
const qs = require('querystring')

export default {
    get(url) {
        return axios.get('/api' + url)
    },
    getByParam(url, params) {
        return axios({
            method: 'get',
            url: '/api' + url,
            params,
        })
    },
    postUrlEncoded(url, data) {
        return axios({
            method: 'post',
            url: '/api' + url,
            data: qs.stringify(data),
            headers: {
                'content-type': 'application/x-www-form-urlencoded; charset=UTF-8'
            }
        })
    },
    postJsonWithToken(url, data) {
        return axios({
            method: 'post',
            url: '/api' + url,
            data: data,
            headers: {
                'content-type': 'application/json; charset=UTF-8',
                'x-auth-token': store.getters['user/getToken']
            }
        })
    },
    putJsonWithToken(url, data) {
        return axios({
            method: 'put',
            url: '/api' + url,
            data: data,
            headers: {
                'x-auth-token': store.getters['user/getToken']
            }
        })
    },
}