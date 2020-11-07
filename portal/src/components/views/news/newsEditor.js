import Vue from 'vue';
import { maxLength } from 'vuelidate/lib/validators';
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
import api from '../../../api';

export default {
    name: 'NewsEditor',
    components: {
        EditorContent,
        EditorMenuBar,
    },
    props: ['isEnglish','requiredErrorMessage', 'maxLengthErrorMessage'],
    computed: {
        isRequired: function () {
          return this.isEnglish=="";
        }
    },
    data() {
        return {
            imageBtnDisable: false,
            message: "",
            newsEditor: new Editor({
                extensions: [
                    new Blockquote(),
                    new BulletList(),
                    new HardBreak(),
                    new Heading({ levels: [1, 2, 3] }),
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
                onUpdate: ({ getHTML }) => {
                    this.message = getHTML();
                    this.$v.message.$touch();
                },
            }),
            buttons: {
                bold: "Bold",
                italic: "Italic",
                strike: "Strike",
                underline: "Underline",
                paragraph: "Paragraph",
                H1: "H1",
                H2: "H2",
                H3: "H3",
                order_list: "Numbered List",
                bullet_list: "Bullet List",
                redo: "Redo",
                undo: "UnDo",
                image: "Image",
            },
        }
    },
    validations: {
        message: {
            required: (function () {
                if(this.isRequired) {
                    return this.message.length > 7; // atleast one letter -->  <p>a</p>
                } else {
                    return true; // no validations here
                } 
            }),
            maxLength: maxLength(2500)
        },
    },
    methods: {
        clearContent() {
            this.newsEditor.clearContent(true);
            this.$v.$reset();
        },
        isInvalid() {
            if (this.isRequired) {
                this.$v.$touch(); //sets $dirty in vualidate even if the field is not touched by the user
            }
            return this.$v.$invalid;
        },
        showImagePrompt(commands) {
            this.imageBtnDisable = true;
            let inputElement = document.createElement("input");
            inputElement.setAttribute("type", "file");
            inputElement.addEventListener("change", (e) => {
                if (e.target.files && e.target.files[0]) {
                    let anImage = e.target.files[0]; // file from input

                    let formData = new FormData();
                    formData.append("image", anImage, anImage.name);
                    api.postMultipartFDWithToken(
                        '/images',
                        formData
                    ).then(response => {
                        if (response.status == 202 && response.data.url.length > 0) {
                            const src = response.data.url;
                            commands.image({ src });
                            this.imageBtnDisable = false;
                        }
                    }).catch(error => {
                        if (error.response.status == 400) {
                            Vue.swal.mixin({
                                position: 'top-end',
                                showConfirmButton: false,
                                showCancelButton:true,
                            }).fire({
                                title: "Invalid image type, size or name",
                                text: "Image must be of type jpg. Image size must be smaller than 200KB. Image name can only contain letters, numbers, dashes, underscores and spaces",
                                icon: "error",
                            });
                        } else {
                            Vue.swal.mixin({
                                toast: true,
                                position: 'top-end',
                                showConfirmButton: false,
                                timer: 2000,
                            }).fire({
                                    title: "Error uploading image",
                                    icon: "error",
                            });
                        }
                        this.submitBtnDisable = false;
                    });
                }
            })
            inputElement.click();
        },
        beforeDestroy() {
            this.editor.destroy();
        },
    }
};