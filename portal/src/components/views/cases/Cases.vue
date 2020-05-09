<template>
<div class="bg-gray-50">

<!-- ===============================================-->
<!--    Main Wrapper-->
<!-- ===============================================-->

<div class="flex flex-col container max-w-6xl  mx-auto bg-gray-50 py-12 px-3 sm:px-6 lg:px-8">
    <!-- ===============================================-->
    <!--    Header-->
    <!-- ===============================================-->

    <div class="w-full flex flex-col mt-10">
        <div>
            <h1 class="text-4xl font-bold">Case Report Portal </h1>
        </div>
        <hr class="mt-5">
    </div>

    <!-- ===============================================-->
    <!--   Form Wrapper-->
    <!-- ===============================================-->
    <form action="#" @submit.prevent="saveCases" method="post">
        <div class="w-full mt-5">

            <!-- ===============================================-->
            <!--   Form-Part-1 General-->
            <!-- ===============================================-->

            <div class="border box-border rounded shadow-md pt-2">
                <h2 class= "bg-white mt-2 px-8 text-left text-2xl leading-9 font-semibold text-gray-900">
                    Case General Details
                </h2>
                <div class="flex mt-2  py-4 flex-col w-full bg-gray-200 px-4">
                    <!--Title Input-->
                    <div class="mt-2">
                        <label class="px-4 text-base text-gray-700 w-full " for="caseNumber">Case Number(DHIS2)<span class="text-red-400">*</span></label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                   v-model="$v.cases.caseNumber.$model" type="text" placeholder="Case Number" name="caseNumber" id="caseNumber" :class="{'border-red-500':$v.cases.caseNumber.$error}">
                            <div v-if="$v.cases.caseNumber.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.cases.caseNumber.required">Case Number is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.cases.caseNumber.maxLength">Case Number must have at most 100 characters</p>
                            </div>
                        </div>
                    </div>

                    <div class="mt-8 flex flex-row">
                        <!--From Input-->
                        <div class="w-1/2">
                            <label class="px-4 text-base text-gray-700 w-full" for="from">Origin<span class="text-red-400">*</span></label>
                            <div class="px-4">
                                <select v-model="$v.cases.isLocal.$model" class="form-select block w-full mt-1">
                                    <option value="true" selected>Local </option>
                                    <option value="false">Foreigner</option>
                                </select>
                                <div v-if="$v.cases.isLocal.$dirty">
                                    <p class="text-red-500 text-xs italic" v-if="!$v.cases.isLocal.required">Origin is required </p>
                                </div>
                            </div>
                        </div>
                        <!--To Input-->
                        <div class="w-1/2">
                            <label class="px-4 text-base text-gray-700 w-full" for="to">Detected From<span class="text-red-400">*</span></label>
                            <div class="px-4">
                                <select v-model="$v.cases.detectedFrom.$model" class="form-select block w-full mt-1">
                                    <option value="quarantine facility" selected>Quarantine Facility</option>
                                    <option value="home">Home</option>
                                </select>
                            </div>
                        </div>
                    </div>



                    <!-- English Input-->
                    <div class="mt-8">
                        <label class="px-4 text-base text-gray-700 w-full" for="message_en">English Message<span class="text-red-400">*</span></label>
                        <div class="px-4">
                                    <textarea rows="2" class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                              v-model="$v.cases.message_en.$model" type="text" placeholder="Message in English" id="message_en"  :class="{'border-red-500':$v.cases.message_en.$error}"></textarea>
                            <div v-if="$v.cases.message_en.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.cases.message_en.required">English Message is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.cases.message_en.maxLength">English Message must have at most 1500 characters</p>

                            </div>
                        </div>
                    </div>

                    <div class="mt-8">
                        <label class="px-4 text-base text-gray-700 w-full" for="message_si">Sinhala Message</label>
                        <div class="px-4">
                                    <textarea rows="2" class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                              v-model="$v.cases.message_si.$model"   type="text" name="message_si" placeholder="Message in English" id="message_si" :class="{'border-red-500':$v.cases.message_si.$error}" ></textarea>

                            <div v-if="$v.cases.message_si.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.cases.message_si.maxLength">Sinhala Message must have at most 1500 characters</p>

                            </div>
                        </div>
                    </div>

                    <div class="mt-8">
                        <label class="px-4 text-base text-gray-700 w-full" for="message_ta">Tamil Message</label>
                        <div class="px-4">


                        <textarea rows="2" class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                  v-model="$v.cases.message_ta.$model" type="text" name="message_ta" placeholder="Message in Tamil" id="message_ta" :class="{'border-red-500':$v.cases.message_ta.$error}"  ></textarea>

                            <div v-if="$v.cases.message_ta.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.cases.message_ta.maxLength">Tamil Message must have at most 1500 characters</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <!-- ===============================================-->
            <!--   Form-Part-2 Locations-->
            <!-- ===============================================-->
            <div class="border box-border rounded shadow-md pt-2 mt-8">
                <h2 class= "bg-white mt-2 px-8 text-left text-2xl leading-9 font-semibold text-gray-900">
                    Add Locations
                </h2>
                <div class="flex mt-2  py-4 flex-col w-full bg-gray-200 px-4">
                    <div class="bg-white mt-4 border pb-5 px-4 rounded" v-for="(v , index) in $v.locations.$each.$iter" :key="index">
                        <div class="flex flex-row item-center justify-between">
                            <div class="-mb-10 flex inline-flex justify-end relative w-full" v-if="index > 0">
                                <svg   @click="deleteLocation(index)" class="fill-current mt-4 px-4 text-red-700 w-16 hover:text-red-500 cursor-pointer" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 125"><path d="M50 87c20.4 0 37-16.6 37-37S70.4 13 50 13 13 29.6 13 50s16.6 37 37 37zM36.7 41a3.036 3.036 0 010-4.3 3.036 3.036 0 014.3 0l9 9 9-9a3.036 3.036 0 014.3 0 3.036 3.036 0 010 4.3l-9 9 9 9a3.036 3.036 0 010 4.3c-.59.59-1.37.89-2.15.89s-1.56-.3-2.15-.89l-9-9-9 9c-.59.59-1.37.89-2.15.89s-1.56-.3-2.15-.89a3.036 3.036 0 010-4.3l9-9-9-9z"/>
                                </svg>
                            </div>

                        </div>

                        <!--Date Input-->
                        <div class="mt-8">
                            <label class="px-4 text-base text-gray-700 w-full" for="date">Date<span class="text-red-400">*</span></label>
                            <div class="px-4">
                                <flat-pickr class="bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                            v-model="v.date.$model" :config="configDate" placeholder="Select a date" :name="`locations[${index}][date]`"></flat-pickr>

                                <div v-if="v.date.$dirty">
                                    <p class="text-red-500 text-xs italic" v-if="!v.date.required">Date  is required</p>
                                </div>
                            </div>
                        </div>

                        <!--Address Input-->
                        <div class="mt-8 mb-8">
                            <label class="px-4 text-base text-gray-700 w-full" for="address">Location(Postal Area)<span class="text-red-400">*</span></label>
                            <div class="px-4">
                                <v-select id="index"
                                          class="bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                                          label="name" :options="sl_postal_code"
                                          :input-id="`${index}`"
                                          v-model="v.locationA.$model"
                                          @input="(value) => setSelected(index, value)"
                                >

                                </v-select>

                                <div v-if="v.locationA.$dirty">
                                    <p class="text-red-500 text-xs italic" v-if="!v.locationA.required">Location is required</p>
                                </div>

                            </div>
                        </div>
                    </div>


                </div>

                <div class="bg-gray-200 pb-6 px-4">
                    <button @click="addLocation" type="button" class="bg-white shadow hover:bg-white text-gray-600 font-bold py-2 px-4 rounded inline-flex items-center hover:text-gray-800 hover:shadow-md">
                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 60 75"><path d="M33 27h25a2 2 0 012 2v2a2 2 0 01-2 2H33v25a2 2 0 01-2 2h-2a2 2 0 01-2-2V33H2a2 2 0 01-2-2v-2a2 2 0 012-2h25V2a2 2 0 012-2h2a2 2 0 012 2v25z" />

                        </svg>
                        <span>Add Location</span>
                    </button>
                </div>
            </div>

            <!-- ===============================================-->
            <!--  SaveButton-->
            <!-- ===============================================-->
            <div class="bg-white mt-8 flex flex-row border shadow-md py-4 px-6 justify-between items-center">
                <div class="text-lg text-black font-bold ">
                    Nice Job! You're almost done
                </div>
                <div>
                    <button type="submit" :disabled="submitStatus" :class="{'opacity-50':submitStatus}" class="bg-white hover:bg-gray-100 text-blue-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow">
                        Add Case Report
                    </button>
                </div>
            </div>

        </div>
    </form>

    <!-- ===============================================-->
    <!--   Ends-Form Wrapper-->
    <!-- ===============================================-->
</div>

<!-- ===============================================-->
<!--    End Main Wrapper-->
<!-- ===============================================-->
</div>
</template>

<script src="./cases.js"></script>