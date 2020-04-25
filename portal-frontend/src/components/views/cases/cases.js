import Vue from 'vue'
import vSelect from 'vue-select'
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';
import { required, maxLength } from 'vuelidate/lib/validators';

import cities from'./post_codes'
const axios = require('axios').default;

Vue.component('v-select', vSelect)

export default {
    name: 'Cases',
    components:{
        flatPickr,
    },
    data(){
        return{
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
        }
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
                // area:{
                //     required
                // },
                // longitude:{
                //     required
                // },
                // latitude:{
                //     required
                // }
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
            this.$v.$touch()
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
                        this.$v.$reset();
                    }
                }).catch(error=>{
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
    },
}