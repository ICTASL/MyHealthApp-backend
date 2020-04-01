import Vue from 'vue'
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
const axios = require('axios').default;
import Vuelidate from 'vuelidate'

Vue.use(Vuelidate);
Vue.use(VueSweetalert2);
import {required,maxLength} from 'vuelidate/lib/validators';

var app = new Vue({

    el: '#app',
    data: {
        submitStatus: false,

        alert:{
            "title" : "",
            "subtitle":"",
            "source":"",
            "messageEn":"",
            "messageSi":"",
            "messageTa":""
        }

    },

    validations: {
        alert: {
            title: {
                required,
                maxLength: maxLength(100)
            },
            subtitle: {
                maxLength: maxLength(100)
            },

            source: {
                required,
                maxLength: maxLength(45)
            },

            messageEn: {
                required,
                maxLength: maxLength(1500)
            },

            messageSi: {
                maxLength: maxLength(1500)
            },

            messageTa: {
                maxLength: maxLength(1500)
            },


        }
    },


    methods:{

        saveAlerts(){
            this.$v.$touch();
            if (this.$v.$invalid){
                return
            }else{
                this.submitStatus = true;
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
                        this.submitStatus = false;
                        this.$v.$reset()
                    }
                }).catch(e=>{
                    Vue.swal({
                        title: 'Something Went Wrong!',
                        icon: 'error'
                    });
                })
            }

        }
    }



})
