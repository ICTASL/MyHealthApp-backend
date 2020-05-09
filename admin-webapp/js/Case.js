import Vue from 'vue'
import flatPickr from 'vue-flatpickr-component';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import Vuelidate from 'vuelidate'
import vSelect from 'vue-select'

Vue.use(Vuelidate);

Vue.use(VueSweetalert2);

Vue.component('v-select', vSelect,{
    props:['inputId']
})

const axios = require('axios').default;

import {required,maxLength} from 'vuelidate/lib/validators';
import cities from'./post_codes'

var app = new Vue({

    el: '#app',
    components:{
        flatPickr,
    },
    data: {
        submitStatus: false,
        config: {
            noCalendar: true,
            enableTime: true,
            time_24hr: true,
            dateFormat: "H:i"
        },
        configDate:{
            enableTime: false,
        },


        cases:{
            'caseNumber':'',
            'isLocal':'',
            'detectedFrom':'',
            'message_en':'',
            'message_si':'',
            'message_ta':'',

        },

        sl_postal_code: cities,

        locations:[
            {
                'date':'',
                'area':'',
                'longitude':'',
                'latitude':'',
                'locationA':'',



            }
        ]
    },

    validations:{
        cases:{
            caseNumber:{
                required,
                maxLength: maxLength(100)
            },


            isLocal:{
                required,

            },

            detectedFrom:{
                required,

            },


            message_en:{
                required,
                maxLength: maxLength(1500)
            },

            message_si:{
                maxLength: maxLength(1500)
            },

            message_ta:{
                maxLength: maxLength(1500)
            },

        },

        locations:{
            $each:{
                date:{
                    required
                },

                locationA:{
                    required
                },
                area:{
                    required
                },
                longitude:{
                    required
                },
                latitude:{
                    required
                }



            }
        }



    },


    methods:{

        addLocation(){
            this.locations.push({
                'date':'',
                'area':'',
                'longitude':'',
                'latitude':'',
                'locationA':'',


            })
        },

        deleteLocation(index) {
            this.locations.splice(index, 1)

        },
        setSelected(inputId,value){


            let location = this.locations[inputId];
            location.area = value.name;
            location.longitude = value.lon;
            location.latitude = value.lat;
            this.locations[inputId] = location;
        },

        saveCases(){
            let url= "/notification/case/add";

            this.$v.$touch();
            if (this.$v.$invalid){
                return
            }
            else {
                this.submitStatus =true;
                axios.post(url,{
                        "caseNumber" : this.cases.caseNumber,
                        "isLocal" : this.cases.isLocal,
                        "detectedFrom" : this.cases.detectedFrom,
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
                        this.cases.detectedFrom='';
                        this.cases.isLocal=''


                        this.locations = [];
                        this.locations.push({
                            'date':'',
                            'area':'',
                            'longitude':'',
                            'latitude':'',
                            'locationA':'',

                        });
                        this.submitStatus =false;
                        this.$v.$reset()

                    }else if(response.status == 500){
                        Vue.swal({
                            title: 'Something Went Wrong!',
                            icon: 'error'
                        });

                    }
                }).catch(e=>{
                    console.log(e)
                })
            }


        }


    },
})