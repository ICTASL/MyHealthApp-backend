const state = () => ({
    token: null
})

const actions = {}

const mutations = {
    saveToken: (state, token) => {
        state.token = token
    },
    deleteToken: (state) => {
        state.token = null
    },
}
  
const getters = {
    getToken: (state) => {
        return state.token
    }   
}

export default {
    namespaced: true,
    state,
    actions,
    mutations,
    getters
}
  