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
                    <!--Title Input-->
                    <div class="mt-2">
                        <label class="px-4 text-xl text-gray-800 w-full " for="title">Title<span class="text-red-400">*</span></label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                   type="text" placeholder="Title" name="title" id="title" v-model="$v.alert.title.$model" :class="{'border-red-500':$v.alert.title.$error}">
                            <div v-if="$v.alert.title.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.title.required">Title  is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.title.maxLength">Title must have at most 100 characters</p>

                            </div>

                        </div>
                    </div>

                    <!--SubTitle Input-->
                    <div class="mt-2">
                        <label class="px-4 text-xl text-gray-800 w-full " for="sub_title">Subtitle</label>
                        <div class="px-4">
                            <input
                                    class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                    type="text" placeholder="Subtitle"  name="subtitle" id="sub_title" v-model="$v.alert.subtitle.$model">

                            <div v-if="$v.alert.title.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.subtitle.maxLength">Sub Title must have at most 100 characters</p>
                            </div>
                        </div>
                    </div>

                    <!--Source Input-->
                    <div class="mt-2">
                        <label class="px-4 text-xl text-gray-800 w-full " for="source">Source<span class="text-red-400">*</span></label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                   type="text" placeholder="Source" id="source"   name="source"   v-model="$v.alert.source.$model" :class="{'border-red-500':$v.alert.source.$error}">
                            <div v-if="$v.alert.source.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.source.required">Source is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.source.maxLength">Source must have at most 45 characters</p>

                            </div>
                        </div>
                    </div>
                    <div class="mt-8">
                        <label class="px-4 text-xl text-gray-800 w-full" for="english_description">English Alert<span class="text-red-400">*</span></label>
                        <div class="px-4">
                                    <textarea rows="3" class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                              type="text" placeholder="English Alert" id="english_description" name="messageEn"   v-model="$v.alert.messageEn.$model" :class="{'border-red-500':$v.alert.messageEn.$error}"></textarea>

                            <div v-if="$v.alert.messageEn.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.messageEn.required">English Alerts is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.messageEn.maxLength">English Alerts must have at most 1500 characters</p>
                            </div>
                        </div>

                    </div>

                    <div class="mt-8">
                        <label class="px-4 text-xl text-gray-800 w-full" for="sinhala_description">Sinhala Alert</label>
                        <div class="px-4">
                                    <textarea rows="3" class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                              type="text" placeholder="Sinhala Alert" id="sinhala_description"  name="messageSi"   v-model="$v.alert.messageSi.$model" ></textarea>
                            <div v-if="$v.alert.messageSi.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.messageSi.maxLength">Sinhala Alerts must have at most 1500 characters</p>
                            </div>
                        </div>

                    </div>

                    <div class="mt-8">
                        <label class="px-4 text-xl text-gray-800 w-full" for="tamil_description">Tamil Alert</label>
                        <div class="px-4">
                                    <textarea rows="3" class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                              type="text" placeholder="description" id="Tamil Alert" v-model="$v.alert.messageTa.$model"  name="messageTa" ></textarea>
                            <div v-if="$v.alert.messageSi.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.alert.messageTa.maxLength">Tamil Alerts must have at most 1500 characters</p>
                            </div>
                        </div>
                    </div>

                    <div class="mt-8 flex justify-end px-4">

                        <button type="submit" :disabled="submitStatus" :class="{'opacity-50':submitStatus}" class="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow">
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
