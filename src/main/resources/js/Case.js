import Vue from 'vue'
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import VueGoogleAutocomplete from './GoogleAutoComplete'
import Vuelidate from 'vuelidate'

Vue.use(Vuelidate);

Vue.use(VueSweetalert2);

const axios = require('axios').default;

import {required} from 'vuelidate/lib/validators';

var app = new Vue({

    el: '#app',
    components:{
        flatPickr,
        VueGoogleAutocomplete
    },
    data: {
        submitStatus: null,
        config: {
            noCalendar: true,
            enableTime: true,
            time_24hr: true,
            dateFormat: "H:i"
        },
        address: {
            'address':''
        },
        configDate:{
            enableTime: false,
        },
        cases:{
            'caseNumber':'',
            'message_en':'',
            'message_si':'',
            'message_ta':''
        },
        locations:[
            {
                'date':'',
                'from':'',
                'to':'',
                'address':'',
                'longitude':'',
                'latitude':'',
                'from_date':'',
                'to_date':'',


            }
        ]
    },

    validations:{
        cases:{
            caseNumber:{
                required
            },

            message_en:{
                required
            },

            message_si:{
                required
            },

            message_ta:{
                required
            }
        },



    },


    methods:{
        addLocation(){
            this.locations.push({
                'date':'',
                'from':'',
                'to':'',
                'address':'',
                'longitude':'0',
                'latitude':'0'
            })
        },

        getAddressData: function (addressData, placeResultData, id ,index) {

            let location = this.locations[id];
            location.address = placeResultData.formatted_address;
            location.longitude = addressData.longitude;
            location.latitude = addressData.latitude;
            this.locations[id] = location;
        },

        saveCases(){
            let url= "/notification/case/add";

            this.$v.$touch();
            if (this.$v.$invalid){
                this.saveCasesStatus = 'ERROR'
            }
            else {
                this.submitStatus = 'PENDING'
                axios.post(url,{
                        "caseNumber" : this.cases.caseNumber,
                        "message_en":this.cases.message_en,
                        "message_si":this.cases.message_si,
                        "message_ta":this.cases.message_ta,
                        "locations": this.locations
                    },{
                        headers:
                            {
                                'content-type': 'application/json'
                            }
                    }
                ).then(response=>{
                    if(response.status == 202){

                        Vue.swal({
                            title: 'New Case Report Was Submitted',
                            icon: 'success'
                        });

                        this.submitStatus = 'OK'
                        this.cases.caseNumber= '';
                        this.cases.message_en= '';
                        this.cases.message_si= '';
                        this.cases.message_ta= '';

                        this.locations = [];
                        this.locations.push({
                            'date':'',
                            'from':'',
                            'to':'',
                            'address':'',
                            'longitude':'0',
                            'latitude':'0'
                        });
                        this.$refs.makeAddress.clear();
                    }else if(response.status == 500){
                        Vue.swal({
                            title: 'Something Went Wrong!',
                            icon: 'error'
                        });

                    }
                }).catch(e=>{
                    console.log(e);
                })
            }


        }


    },
})
