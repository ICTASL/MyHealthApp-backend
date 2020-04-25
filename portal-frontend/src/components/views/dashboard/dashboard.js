import Vue from 'vue';
import VModal from 'vue-js-modal'
import VueSweetalert2 from 'vue-sweetalert2';


const axios = require('axios').default;
Vue.use(VModal);
Vue.use(VueSweetalert2);

export default {
    name: 'Dashboard',
    data(){
        return{
            'submitStatus':false,
            'lk_total_case':'',
            'lk_recovered_case':'',
            'lk_total_deaths':'',
            'lk_total_suspect':'',
            'last_update_time':'',
        }
    },

   mounted(){
        this.fetchData()
   },

   methods:{

       fetchData(){
           axios.get('/application/dashboard/status')
               .then(response => (
                 this.lk_total_case = response.data.lk_total_case,
                 this.lk_recovered_case = response.data.lk_recovered_case,
                 this.lk_total_deaths = response.data.lk_total_deaths,
                 this.lk_total_suspect = response.data.lk_total_suspect,
                 this.last_update_time = response.data.last_update_time

               ))
       },

       currentDate()
       {
           const today = new Date();
           const date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
           const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
           const dateTime = date +' '+ time;
           this.last_update_time = dateTime;
       },


       CCshow () {
           this.fetchData();
           this.$modal.show('confirmed-cases');
       },
       CChide () {
           this.$modal.hide('confirmed-cases');
           this.fetchData()
       },

       CCsubmit(){
           this.Update();
       },

       SSshow () {
           this.fetchData();
           this.$modal.show('suspected-cases');
       },
       SShide () {
           this.$modal.hide('suspected-cases');
           this.fetchData()
       },

       SSsubmit(){
           this.Update();
       },


       RRshow () {
           this.fetchData();
           this.$modal.show('recover-cases');
       },
       RRhide () {
           this.$modal.hide('recover-cases');
           this.fetchData()
       },

       RRsubmit(){
           this.Update();
       },


       DDshow () {
           this.fetchData();
           this.$modal.show('deaths-cases');
       },
       DDhide () {
           this.$modal.hide('deaths-cases');
           this.fetchData()
       },

       DDsubmit(){
           this.Update();
       },


       Update(){
           let url= "/application/dashboard/status";

           axios.put(url,{
                   "lk_total_case" : this.lk_total_case,
                   "lk_recovered_case":this.lk_recovered_case,
                   "lk_total_deaths":this.lk_total_deaths,
                   "lk_total_suspect":this.lk_total_suspect,
                   "last_update_time": this.last_update_time
               },{
                   headers:
                       {
                           'content-type': 'application/json'
                       }
               }
           ).then(response=>{
               if(response.status == 202){
                   this.CChide();
                   this.SShide();
                   this.RRhide();
                   this.DDhide();

                   Vue.swal({
                       title: 'Records Updated',
                       icon: 'success'
                   });

                   this.fetchData();
               }
           }).catch(error => {
               Vue.swal({
                   title: 'Something Went Wrong!',
                   icon: 'error'
               });
               if (error.response) {
                    console.log(error.response.status);
               }
           })
       },

       isNumber: function(evt) {
           evt = (evt) ? evt : window.event;
           var charCode = (evt.which) ? evt.which : evt.keyCode;
           if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
               evt.preventDefault();
           } else {
               return true;
           }
       }
   }
}