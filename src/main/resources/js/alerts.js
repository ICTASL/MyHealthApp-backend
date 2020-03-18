import Vue from 'vue'
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
const axios = require('axios').default;
Vue.use(VueSweetalert2);
var app = new Vue({

    el: '#app',
    data: {

        alert:{
            "title" : "",
            "subtitle":"",
            "source":"",
            "messageEn":"",
            "messageSi":"",
            "messageTa":""
        }

    },

    methods:{
        saveAlerts(){
            axios.post('/notification/alert/add',{
                "title" : this.alert.title,
                "subtitle":this.alert.subtitle,
                "source":this.alert.source,
                "messageEn":this.alert.messageEn,
                "messageSi":this.alert.messageSi,
                "messageTa":this.alert.messageTa,
            },{
                headers:
                    {
                        'content-type': 'application/json'
                    }
                }
            ).then(response=>{
                if(response.status == 202){
                    Vue.swal({
                       title: 'New Alert Was Created',
                        icon: 'success'
                    });

                   this.alert.title ='',
                   this.alert.subtitle='',
                   this.alert.source='',
                   this.alert.messageEn='',
                   this.alert.messageSi='',
                   this.alert.messageTa=''
                }
            }).catch(e=>{
                console.log(e);
            })
        }
    }


})
