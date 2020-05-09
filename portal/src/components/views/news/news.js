import Vue from 'vue'
import { required, maxLength } from 'vuelidate/lib/validators';

import api from '../../../api'

export default {
    name: 'News',
    data(){
        return{
            submitStatus: false,
            alert:{
                "title" : "",
                "subtitle":"",
                "source":"",
                "messageEn":"",
                "messageSi":"",
                "messageTa":""
            }
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
                api.postJsonWithToken('/notification/alert/add',{
                        "title" : this.alert.title,
                        "subtitle":this.alert.subtitle,
                        "source":this.alert.source,
                        "messageEn":this.alert.messageEn,
                        "messageSi":this.alert.messageSi,
                        "messageTa":this.alert.messageTa,
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
                }).catch(error =>{
                    Vue.swal({
                        title: 'Something Went Wrong!',
                        icon: 'error'
                    });
                    if (error.response) {
                        console.log(error.response.status);
                    }
                    this.submitStatus =false;
                })
            }
        }
    }
}
