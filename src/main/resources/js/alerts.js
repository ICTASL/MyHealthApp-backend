import Vue from 'vue'
import ElementUI from "element-ui";
import { ElementTiptapPlugin } from "element-tiptap";
import "element-tiptap/lib/index.css";
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
const axios = require('axios').default;
import Vuelidate from 'vuelidate';
Vue.use(Vuelidate);
Vue.use(VueSweetalert2);
import {required,maxLength} from 'vuelidate/lib/validators';

import { Editor, EditorContent ,EditorMenuBar } from 'tiptap';

import {
    Blockquote,
    HardBreak,
    Heading,
    HorizontalRule,
    OrderedList,
    BulletList,
    ListItem,
    Bold,
    Italic,
    Link,
    Strike,
    Underline,
    History,
} from 'element-tiptap'


var app = new Vue({

    el: '#app',
    components: {
        EditorContent,
        EditorMenuBar,
    },
    data() {
        return {
            submitStatus: false,
            english: new Editor({
                extensions: [
                    new Blockquote(),
                    new BulletList(),
                    new HardBreak(),
                    new Heading({levels: [1, 2, 3]}),
                    new HorizontalRule(),
                    new ListItem(),
                    new OrderedList(),
                    new Link(),
                    new Bold(),
                    new Italic(),
                    new Strike(),
                    new Underline(),
                    new History(),
                ],
                onUpdate: ({getJSON, getHTML}) => {
                    this.message.english = getHTML()
                },
            }),

            sinhala: new Editor({
                extensions: [
                    new Blockquote(),
                    new BulletList(),
                    new HardBreak(),
                    new Heading({levels: [1, 2, 3]}),
                    new HorizontalRule(),
                    new ListItem(),
                    new OrderedList(),
                    new Link(),
                    new Bold(),
                    new Italic(),
                    new Strike(),
                    new Underline(),
                    new History(),
                ],
                onUpdate: ({getJSON, getHTML}) => {
                    this.message.sinhala = getHTML()
                },
            }),

            tamil: new Editor({
                extensions: [
                    new Blockquote(),
                    new BulletList(),
                    new HardBreak(),
                    new Heading({levels: [1, 2, 3]}),
                    new HorizontalRule(),
                    new ListItem(),
                    new OrderedList(),
                    new Link(),
                    new Bold(),
                    new Italic(),
                    new Strike(),
                    new Underline(),
                    new History(),
                ],
                onUpdate: ({getJSON, getHTML}) => {
                    this.message.tamil = getHTML()
                },
            }),

            "source":'',

            title:{
               "english":"",
               "sinhala":"",
               "tamil":"",
            },

            message:{
                "english":"",
                "sinhala":"",
                "tamil":"",
            }

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

        message:{

            english:{
                required,
                maxLength: maxLength(1500)
            },

            sinhala:{
                maxLength: maxLength(1500)
            },

            tamil:{
                maxLength: maxLength(1500)
            },
        },
    },


    methods:{

        saveAlerts(){
            this.$v.$touch();
            if (this.$v.$invalid){
                return
            }else{
                this.submitStatus = true;
                axios.post('/notification/alert/add',{


                    "source":this.source,

                    title:{
                        "english":this.title.english,
                        "sinhala":this.title.sinhala,
                        "tamil":this.title.tamil,
                    },

                    message:{
                        "english":this.message.english,
                        "sinhala":this.message.sinhala,
                        "tamil":this.message.tamil,
                    }


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

                           this.source ='';
                            this.title.english='';
                            this.title.sinhala='';
                            this.title.tamil='';
                            this.message.english='';
                            this.message.sinhala='';
                            this.message.tamil='';
                          this.submitStatus = false;
                         this.$v.$reset();
                        this.beforeDestroy;
                    }
                }).catch(e=>{
                    Vue.swal({
                        title: 'Something Went Wrong!',
                        icon: 'error'
                    });
                })
            }

        },

    },


    beforeDestroy() {
        this.sinhala.destroy()
        this.tamil.destroy()
        this.english.destroy()
    },



})
