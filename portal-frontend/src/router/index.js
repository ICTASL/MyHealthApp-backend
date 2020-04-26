import Vue from 'vue'
import VueRouter from 'vue-router';

import Login from '@/components/views/Login'
import Base from '@/components/views/Base'
import NotFound from '@/components/views/NotFound'
import Dashboard from '@/components/views/dashboard/Dashboard'
import News from '@/components/views/news/News'
import Cases from '@/components/views/cases/Cases'

Vue.use(VueRouter);

export default new VueRouter({
  mode: 'history',
  routes: [
    {
      path: "/login", name: "login", component: Login
    },
    {
      path: "/", 
      component: Base,
      meta: {
        requiresAuth: true
      },
      children: [
        { 
          path: "dashboard", 
          name: "dashboard", 
          component: Dashboard 
        },
        { 
          path: "news", 
          name: "news", 
          component: News 
        },
        { 
          path: "cases", 
          name: "cases", 
          component: Cases 
        },
      ]
    },
    {
      path: '*',
      component: NotFound
    }
  ]
});
