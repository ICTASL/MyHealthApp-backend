import Vue from 'vue'
import NewsEditor from './NewsEditor'
import { required, maxLength } from 'vuelidate/lib/validators';
import { Editor, EditorContent, EditorMenuBar } from 'tiptap';
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
    Image,
} from 'tiptap-extensions'

import api from '../../../api'

export default {
    name: 'News',
    components: {
        EditorContent,
        EditorMenuBar,
        NewsEditor
    },
    data() {
        return {
            submitBtnDisable: false,
            imageBtnDisable: false,
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
                    new Image(),
                ],
                onUpdate: ({getHTML}) => {
                    this.message.english =  getHTML();
                    this.englishChar();
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
                    new Image(),
                ],
                onUpdate: ({getHTML}) => {
                    this.message.sinhala = getHTML();
                    this.sinhalaChar();
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
                    new Image(),
                ],
                onUpdate: ({getHTML}) => {
                    this.message.tamil = getHTML();
                    this.tamilChar();
                },
            }),

            buttons:{
                "bold":"Bold",
                "italic":"Italic",
                "strike":"Strike",
                "underline":"Underline",
                "paragraph":"Paragraph",
                "H1":"H1",
                "H2":"H2",
                "H3":"H3",
                "order_list":"Order List",
                "bullet_list":"Bullet List",
                "redo":"Redo",
                "undo":"UnDo",
                "image":"Image",
            },
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
            },
            charcount:{
                "englishChar":0,
                "sinhalaChar":0,
                "tamilChar":0,
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
        message:{
            // english:{
            //     required,
            //     maxLength: maxLength(2500)
            // },
            sinhala:{
                maxLength: maxLength(2500)
            },
            tamil:{
                maxLength: maxLength(2500)
            },
        },
    },

    methods:{
        saveAlerts(){
            this.$v.$touch();
            if (this.$v.$invalid || this.$refs.englishEditor.invalid()){
                return
            }else{
                this.submitBtnDisable = true;
                api.postJsonWithToken('/notification/alert/add',{
                        "source":this.source,
                        title:{
                            "english":this.title.english,
                            "sinhala":this.title.sinhala,
                            "tamil":this.title.tamil,
                        },
                        message:{
                            "english":this.$refs.englishEditor.message,
                            "sinhala":this.message.sinhala,
                            "tamil":this.message.tamil,
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
                            this.charcount.sinhalaChar =0;
                            this.charcount.englishChar =0;
                            this.charcount.tamilChar =0;
                            this.$v.$reset();
                            this.$refs.englishEditor.clearContent();
                            this.sinhala.destroy();
                            this.tamil.destroy();
                            this.sinhala =new Editor({
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
                                    new Image(),
                                ],
                                onUpdate: ({getHTML}) => {
                                    this.message.english =  getHTML();
                                    this.englishChar();
                                },
                            });
                            this.tamil =new Editor({
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
                                    new Image(),
                                ],
                                onUpdate: ({getHTML}) => {
                                    this.message.english =  getHTML();
                                    this.englishChar();
                                },
                            });

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
        sinhalaChar()
        {
            this.charcount.sinhalaChar = (this.message.sinhala.length)-7;
        },
        englishChar()
        {
            this.charcount.englishChar = (this.message.english.length)-7;
        },
        tamilChar()
        {
            this.charcount.tamilChar = (this.message.tamil.length)-7;
        },
        showImagePrompt(commands) {  
          this.imageBtnDisable = true; 
          let inputElement = document.createElement("input");
          inputElement.setAttribute("type", "file");
          inputElement.addEventListener('change', (e) => {
            if (e.target.files && e.target.files[0]) {
              let anImage = e.target.files[0];  // file from input
              
              let formData = new FormData();
              formData.append("image", anImage, anImage.name);                                
              api.postMultipartFDWithToken(
                '/images', 
                formData
              ).then(response=>{
                if(response.status == 202 && response.data.url.length>0){                              
                  const src = response.data.url;
                  commands.image({ src });
                } 
                this.imageBtnDisable = false;
              }).catch(()=>{
                Vue.swal.mixin({
                  toast: true,
                  position: 'top-end',
                  showConfirmButton: false,
                  timer: 2000
                }).fire({
                    title: 'Error uploading image',
                    icon: 'error'
                });
                this.submitBtnDisable = false;
              });       
            }
          })
          inputElement.click();        
        },
    }
}
