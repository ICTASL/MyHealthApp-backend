import Vue from 'vue'
import NewsEditor from './NewsEditor'
import { required, maxLength } from 'vuelidate/lib/validators';

import api from '../../../api'

export default {
    name: 'News',
    components: {
        NewsEditor
    },
    data() {
        return {
            submitBtnDisable: false,
            source: "",
            title: {
               english: "",
               sinhala: "",
               tamil: "",
            },
        }
    },
    validations: {
        source: {
            required,
            maxLength: maxLength(45)
        },
        title:{
            english:{
              required,
              maxLength: maxLength(100)
            },
            sinhala:{
              maxLength: maxLength(100)
            },
            tamil:{
                maxLength: maxLength(100)
            },
        },
    },

    methods:{
        saveAlerts(){
            this.$v.$touch();
            if (this.$v.$invalid 
                    || this.$refs.englishEditor.isInvalid()
                    || this.$refs.sinhalaEditor.isInvalid()
                    || this.$refs.tamilEditor.isInvalid()) {
                        if(this.$refs.sinhalaEditor.isInvalid()) console.log("sinhala");
                return
            } else {
                this.submitBtnDisable = true;

                let sinhalaMessageLength = this.$refs.sinhalaEditor.message.length;
                let sinhalaMessage = sinhalaMessageLength>7? this.$refs.sinhalaEditor.message : ""; //avoid empty paragraph
                let tamilMessageLength = this.$refs.tamilEditor.message.length;
                let tamilMessage = tamilMessageLength>7? this.$refs.tamilEditor.message : ""; //avoid empty paragraph
                
                api.postJsonWithToken('/notification/alert/add',{
                        source: this.source,
                        title: {
                            english: this.title.english,
                            sinhala: this.title.sinhala,
                            tamil: this.title.tamil,
                        },
                        message:{
                            english: this.$refs.englishEditor.message,
                            sinhala: sinhalaMessage,
                            tamil: tamilMessage,
                        }
                    }
                ).then(response=>{
                    if(response.status == 202){
                        Vue.swal({
                            title: 'New Alert Was Created',
                            icon: 'success'
                        });
                        this.source ='';
                        this.title.english='';
                        this.title.sinhala='';
                        this.title.tamil='';
                        this.$v.$reset();
                        this.$refs.englishEditor.clearContent();
                        this.$refs.sinhalaEditor.clearContent();
                        this.$refs.tamilEditor.clearContent();
                    }
                    this.submitBtnDisable = false;
                }).catch(error =>{
                    Vue.swal({
                        title: 'Something Went Wrong!',
                        icon: 'error'
                    });
                    if (error.response) {
                        console.log(error.response.status);
                    }
                    this.submitBtnDisable =false;
                });
            }
        },
    }
}
