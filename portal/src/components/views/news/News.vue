<template>
<!--Main Wrapper-->

<div id="app" class="flex flex-col container max-w-6xl  mx-auto bg-gray-50 py-12 px-3 sm:px-6 lg:px-8">
    <div class="w-full flex flex-col mt-10">
        <div>
            <h1 class="text-4xl font-bold">Alerts Portal</h1>
        </div>
        <hr class="mt-5">
    </div>
    <form @submit.prevent="saveAlerts" method="post">
        <div class="w-full mt-5">
            <div class="border box-border rounded shadow-md pt-2">
                <h2 class= "bg-white mt-2 px-8 text-left text-2xl leading-9 font-extrabold text-gray-900">
                    Alert Information
                </h2>
                <div class="flex mt-2  py-4 flex-col w-full bg-gray-200 px-4">
                    <!--Source Input-->
                    <div class="mt-2">
                        <label class="px-4 text-xl text-gray-800 w-full " for="source">Source<span class="text-red-400">*</span></label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                   type="text" placeholder="Source" id="source"   name="source"   v-model="$v.source.$model" :class="{'border-red-500':$v.source.$error}">
                            <div v-if="$v.source.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.source.required">Source is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.source.maxLength">Source must have at most 45 characters</p>

                            </div>
                        </div>
                    </div>
                    <!-- English -->
                    <div class="mt-8">
                        <label class="px-4 text-xl text-gray-800 w-full " for="title">English Title<span class="text-red-400">*</span></label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                   type="text" placeholder="Title" name="title" id="title" v-model="$v.title.english.$model" :class="{'border-red-500':$v.title.english.$error}">
                            <div v-if="$v.title.english.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.title.english.required">Title  is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.title.english.maxLength">Title must have at most 100 characters</p>

                            </div>
                        </div>
                    </div>

                    <div class="mt-4">
                        <label class="px-4 text-xl text-gray-800 w-full" for="english_description">English Alert<span class="text-red-400">*</span></label>
                        <div class="px-4">
                            <news-editor ref="englishEditor" 
                                isEnglish
                                requiredErrorMessage="'English Alert is required'"
                                maxLengthErrorMessage="'Alert message must be less than 2500 characters'" >
                            </news-editor>
                        </div>
                    </div>

                    <hr class="my-8">
                    <!-- Sinhala -->

                    <div class="">
                        <label class="px-4 text-xl text-gray-800 w-full " for="Sinhalatitle">Sinhala Title</label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                   type="text" placeholder="Title" name="title" id="Sinhalatitle" v-model="$v.title.sinhala.$model" :class="{'border-red-500':$v.title.sinhala.$error}">
                            <div v-if="$v.title.sinhala.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.title.sinhala.maxLength">Title must have at most 100 characters</p>

                            </div>

                        </div>
                    </div>
                    <div class="mt-4">
                        <label class="px-4 text-xl text-gray-800 w-full" for="sinhala_description">Sinhala Alert</label>
                        <div class="px-4">
                            <news-editor ref="sinhalaEditor" 
                                maxLengthErrorMessage="'Alert message must be less than 2500 characters'" >
                            </news-editor>
                        </div>
                    </div>

                    <hr class="my-8">
                    <!-- Tamil -->

                    <div class="">
                        <label class="px-4 text-xl text-gray-800 w-full " for="TamilTitle">Tamil Title</label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                   type="text" placeholder="Title" name="title" id="Sinhalatitle" v-model="$v.title.tamil.$model" :class="{'border-red-500':$v.title.tamil.$error}">
                            <div v-if="$v.title.tamil.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.title.tamil.maxLength">Title must have at most 100 characters</p>

                            </div>
                        </div>
                    </div>
                    <div class="mt-4">
                        <label class="px-4 text-xl text-gray-800 w-full" for="tamil_description">Tamil Alert</label>
                        <div class="px-4">
                            <news-editor ref="tamilEditor"  
                                maxLengthErrorMessage="'Alert message must be less than 2500 characters'" >
                            </news-editor>            
                        </div>
                    </div>

                    <!-- The Submit Button -->

                    <div class="mt-8 flex justify-end px-4">
                        <button type="submit" :disabled="submitBtnDisable" :class="{'opacity-50':submitBtnDisable}" class="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow">
                            Add New Alerts
                        </button>
                    </div>
                </div>
            </div>

        </div>
    </form>
</div>

<!--Main Wrapper Ends Here-->
</template>
<script src="./news.js"></script>
