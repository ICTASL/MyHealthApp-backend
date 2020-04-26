
import Vue from 'vue'
import Vuelidate from 'vuelidate';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

import App from './App.vue'
import router from './router'
import store from './store'
import '@/assets/css/app.sass'

Vue.config.productionTip = false;
Vue.use(Vuelidate);
Vue.use(VueSweetalert2);

router.beforeEach((to, from, next) => {
    if(to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.state.user.token || store.state.user.token === 'null') {
            next({
                path: '/login',
                params: { nextUrl: to.fullPath }
            })
        } else {
            next()
        }
    } else {
        next()
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');