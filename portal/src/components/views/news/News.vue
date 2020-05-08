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

                    <div class="mt-8">
                        <label class="px-4 text-xl text-gray-800 w-full " for="title">English Title<span class="text-red-400">*</span></label>
                        <div class="px-4">
                            <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
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

                            <div class="editor">
                                <editor-menu-bar class="mt-4 mb-4" :editor="english" v-slot="{ commands, isActive }">
                                    <div class="menubar">

                                        <button v-tooltip.top-center="{
                                            content:buttons.bold,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                :class="{ 'is-active': isActive.bold() }"
                                                @click="commands.bold"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 281.332 281.332"><path d="M198.102 134.449c15.233-11.431 28.497-29.829 28.497-59.239v-.753c0-18.694-6.274-34.93-18.649-48.258a8.073 8.073 0 00-.181-.189C191.021 8.994 165.96 0 135.294 0H49.656a9 9 0 00-9 9v263.332a9 9 0 009 9h90.331c29.385 0 54.297-7.214 72.043-20.863 18.741-14.414 28.647-35.157 28.647-59.988v-.753c0-29.502-14.634-51.788-42.575-65.279zm-57.393 102.175H86.813v-74.919h48.842c19.735 0 35.34 3.551 45.129 10.27 8.757 6.011 13.015 14.474 13.015 25.872v.752c0 34.32-37.128 38.025-53.09 38.025zM130.58 117.372H86.813V44.709h45.955c29.839 0 46.952 12.351 46.952 33.886v.752c-.001 33.085-30.788 38.025-49.14 38.025z"/></svg>
                                        </button>

                                        <button  v-tooltip.top-center="{
                                            content:buttons.italic,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                :class="{ 'is-active': isActive.italic() }"
                                                @click="commands.italic"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 306.001 306.001"><path d="M249.142 0H120.713a7.5 7.5 0 000 15h54.958l-58.527 276H56.859a7.5 7.5 0 000 15h66.339l.028.001.017-.001h62.044a7.5 7.5 0 000-15h-52.811l58.527-276h58.138a7.5 7.5 0 00.001-15z"/></svg>

                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.strike,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                :class="{ 'is-active': isActive.strike() }"
                                                @click="commands.strike"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 289 289"><path d="M281.5 137h-35.52V25.039a7.5 7.5 0 00-7.5-7.5 7.5 7.5 0 00-7.5 7.5V137H58.02V25.039a7.5 7.5 0 00-7.5-7.5 7.5 7.5 0 00-7.5 7.5V137H7.5c-4.143 0-7.5 3.358-7.5 7.5s3.357 7.5 7.5 7.5h35.52v17.98c0 55.956 45.524 101.481 101.48 101.481s101.48-45.524 101.48-101.481V152h35.52c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5zm-50.52 32.98c0 47.686-38.795 86.481-86.48 86.481s-86.48-38.795-86.48-86.481V152h172.96v17.98z"/></svg>

                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.underline,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                :class="{ 'is-active': isActive.underline() }"
                                                @click="commands.underline"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 333.725 333.725"><path d="M68.773 47.696a7.5 7.5 0 007.5-7.5V15h83.089v255.342H142.14a7.5 7.5 0 000 15h49.443c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5h-17.222V15h83.089v25.196a7.5 7.5 0 0015 0V7.5a7.5 7.5 0 00-7.5-7.5H68.773a7.5 7.5 0 00-7.5 7.5v32.696a7.5 7.5 0 007.5 7.5zM292.862 318.725h-252a7.5 7.5 0 000 15h252a7.5 7.5 0 007.5-7.5c0-4.143-3.357-7.5-7.5-7.5z"/></svg>
                                        </button>


                                        <button v-tooltip.top-center="{
                                            content:buttons.paragraph,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                :class="{ 'is-active': isActive.paragraph() }"
                                                @click="commands.paragraph"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 333 333"><path d="M294.769 0H127.752c-.457 0-.902.047-1.336.126A7.443 7.443 0 00125.08 0H95.685C59.869 0 30.731 29.139 30.731 64.954s29.138 64.954 64.954 64.954h24.567V325.5a7.5 7.5 0 007.5 7.5 7.5 7.5 0 007.5-7.5V15h10.386v310.5a7.5 7.5 0 007.5 7.5 7.5 7.5 0 007.5-7.5V15h94.564v310.5a7.5 7.5 0 007.5 7.5c4.143 0 7.5-3.357 7.5-7.5V15h24.566c4.142 0 7.5-3.357 7.5-7.5S298.911 0 294.769 0zM95.685 114.908c-27.545 0-49.954-22.409-49.954-49.954S68.14 15 95.685 15h24.567v99.908H95.685z"/></svg>
                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.H1,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button pr-2"
                                                :class="{ 'is-active': isActive.heading({ level: 1 }) }"
                                                @click="commands.heading({ level: 1 })"
                                        >
                                            H1
                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.H2,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button pr-2"
                                                :class="{ 'is-active': isActive.heading({ level: 2 }) }"
                                                @click="commands.heading({ level: 2 })"
                                        >
                                            H2
                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.H3,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button pr-2"
                                                :class="{ 'is-active': isActive.heading({ level: 3 }) }"
                                                @click="commands.heading({ level: 3 })"
                                        >
                                            H3
                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.bullet_list,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                :class="{ 'is-active': isActive.bullet_list() }"
                                                @click="commands.bullet_list"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 290.5 290.5"><path d="M74 66.001h209c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5H74a7.5 7.5 0 000 15zM283 138H74a7.5 7.5 0 000 15h209a7.5 7.5 0 007.5-7.5 7.5 7.5 0 00-7.5-7.5zM283 224.999H74a7.5 7.5 0 000 15h209c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5zM26 32.501c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11zM26 119.5c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11zM26 205.999c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11z"/></svg>
                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.order_list,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                :class="{ 'is-active': isActive.ordered_list() }"
                                                @click="commands.ordered_list"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 366.176 366.176"><path d="M149.676 122.528h209a7.5 7.5 0 000-15h-209a7.5 7.5 0 000 15zM358.676 194.528h-209a7.5 7.5 0 000 15h209a7.5 7.5 0 000-15zM358.676 281.527h-209a7.5 7.5 0 000 15h209a7.5 7.5 0 000-15zM108.813 84.516a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657V84.516h2.657z"/><circle cx="129.176" cy="114.778" r="5.375"/><path d="M108.813 172.016a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM68.313 172.016a7.5 7.5 0 000-15H48a7.5 7.5 0 000 15h2.656v22.762H48a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657z"/><circle cx="129.176" cy="202.215" r="5.375"/><path d="M108.813 258.898a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM68.313 258.898a7.5 7.5 0 000-15H48a7.5 7.5 0 000 15h2.656v22.762H48a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM27.813 258.898a7.5 7.5 0 000-15H7.5a7.5 7.5 0 000 15h2.656v22.762H7.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657z"/><circle cx="129.176" cy="289.097" r="5.375"/></svg>
                                        </button>



                                        <button v-tooltip.top-center="{
                                            content:buttons.undo,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                @click="commands.undo"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2"  viewBox="0 0 332.114 332.114"><path d="M324.614 176.5h-26.506c-.272-73.383-60.049-133-133.494-133-73.612 0-133.5 59.888-133.5 133.5v86.007l-18.31-18.31a7.502 7.502 0 00-10.608 0 7.5 7.5 0 000 10.606l31.113 31.113.03.027c.166.165.339.321.52.47.084.069.173.129.26.194.109.082.217.167.331.243.103.069.208.128.313.19.106.064.208.129.316.188.107.057.218.105.328.158.111.053.222.109.337.156.107.044.217.081.326.12.121.044.241.09.366.128.109.033.221.058.332.085.126.032.251.067.379.092.129.026.26.041.389.06.112.016.223.038.336.049a7.623 7.623 0 001.48 0c.113-.011.224-.033.336-.049.129-.019.26-.034.389-.06.128-.025.253-.061.379-.092.111-.028.223-.052.332-.085.125-.038.245-.084.367-.128.108-.039.218-.075.325-.119.115-.048.226-.104.338-.157.109-.052.22-.1.327-.157.108-.058.211-.124.317-.188.105-.063.211-.122.313-.19.114-.076.222-.161.331-.243.087-.065.176-.125.26-.194.181-.148.354-.305.52-.47l.03-.027 31.113-31.113a7.5 7.5 0 00-10.607-10.606l-18.31 18.31V177c0-65.341 53.159-118.5 118.5-118.5 65.174 0 118.223 52.888 118.494 118h-26.494a7.5 7.5 0 00-7.5 7.5 7.5 7.5 0 007.5 7.5h68a7.5 7.5 0 007.5-7.5 7.497 7.497 0 00-7.498-7.5z"/></svg>
                                        </button>

                                        <button v-tooltip.top-center="{
                                            content:buttons.redo,
                                             placement: 'top-center',
                                        }" type="button"
                                                class="menubar__button"
                                                @click="commands.redo"
                                        >
                                            <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 332.113 332.113"><path d="M329.917 244.697a7.5 7.5 0 00-10.607 0L301 263.007V177c0-73.612-59.888-133.5-133.5-133.5-73.445 0-133.223 59.617-133.494 133H7.5a7.5 7.5 0 000 15h68a7.5 7.5 0 000-15H49.006c.271-65.112 53.319-118 118.494-118C232.841 58.5 286 111.659 286 177v86.007l-18.31-18.31a7.5 7.5 0 00-10.606 10.607l31.112 31.112c.175.176.36.342.552.499.083.068.171.127.256.191.11.083.219.168.334.245.103.069.211.129.317.193.104.062.205.128.311.184.11.059.223.109.335.162.11.052.217.106.33.153.11.046.223.083.335.123.119.043.236.088.357.125.112.034.227.059.34.087.124.031.246.066.372.091.131.026.263.042.394.061.111.016.219.037.331.048a7.623 7.623 0 001.48 0c.112-.011.221-.032.331-.048.132-.019.264-.035.395-.061.126-.025.248-.06.371-.09.114-.029.228-.054.341-.088.122-.037.239-.082.357-.125.112-.04.225-.077.335-.123.112-.046.22-.101.33-.153.112-.053.225-.103.335-.162.106-.057.207-.121.31-.183.107-.064.215-.125.319-.194.114-.077.222-.162.332-.244.085-.064.174-.124.257-.192a7.44 7.44 0 00.552-.499l31.112-31.112a7.5 7.5 0 000-10.607z"/></svg>
                                        </button>

                                    </div>
                                </editor-menu-bar>

                                <editor-content class="editor__content" :class="{'border-red-500':$v.message.english.$error}" :editor="english"></editor-content>
                            </div>
                            <div class="my-3">
                                <p class="text-base text-blue-400 text-right">{{ charcount.englishChar }} characters</p>
                            </div>
                            <div v-if="$v.message.english.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.message.english.required">English Alerts is required</p>
                                <p class="text-red-500 text-xs italic" v-if="!$v.message.english.maxLength">English Alerts must have at most 2500 characters</p>
                            </div>
                        </div>

                    </div>

                    <hr class="my-8">

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

                            <editor-menu-bar :editor="sinhala" v-slot="{ commands, isActive }">
                                <div class="menubar">

                                    <button v-tooltip.top-center="{
                                            content:buttons.bold,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.bold() }"
                                            @click="commands.bold"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 281.332 281.332"><path d="M198.102 134.449c15.233-11.431 28.497-29.829 28.497-59.239v-.753c0-18.694-6.274-34.93-18.649-48.258a8.073 8.073 0 00-.181-.189C191.021 8.994 165.96 0 135.294 0H49.656a9 9 0 00-9 9v263.332a9 9 0 009 9h90.331c29.385 0 54.297-7.214 72.043-20.863 18.741-14.414 28.647-35.157 28.647-59.988v-.753c0-29.502-14.634-51.788-42.575-65.279zm-57.393 102.175H86.813v-74.919h48.842c19.735 0 35.34 3.551 45.129 10.27 8.757 6.011 13.015 14.474 13.015 25.872v.752c0 34.32-37.128 38.025-53.09 38.025zM130.58 117.372H86.813V44.709h45.955c29.839 0 46.952 12.351 46.952 33.886v.752c-.001 33.085-30.788 38.025-49.14 38.025z"/></svg>
                                    </button>

                                    <button  v-tooltip.top-center="{
                                            content:buttons.italic,
                                             placement: 'top-center',
                                        }" type="button"
                                             class="menubar__button"
                                             :class="{ 'is-active': isActive.italic() }"
                                             @click="commands.italic"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 306.001 306.001"><path d="M249.142 0H120.713a7.5 7.5 0 000 15h54.958l-58.527 276H56.859a7.5 7.5 0 000 15h66.339l.028.001.017-.001h62.044a7.5 7.5 0 000-15h-52.811l58.527-276h58.138a7.5 7.5 0 00.001-15z"/></svg>

                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.strike,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.strike() }"
                                            @click="commands.strike"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 289 289"><path d="M281.5 137h-35.52V25.039a7.5 7.5 0 00-7.5-7.5 7.5 7.5 0 00-7.5 7.5V137H58.02V25.039a7.5 7.5 0 00-7.5-7.5 7.5 7.5 0 00-7.5 7.5V137H7.5c-4.143 0-7.5 3.358-7.5 7.5s3.357 7.5 7.5 7.5h35.52v17.98c0 55.956 45.524 101.481 101.48 101.481s101.48-45.524 101.48-101.481V152h35.52c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5zm-50.52 32.98c0 47.686-38.795 86.481-86.48 86.481s-86.48-38.795-86.48-86.481V152h172.96v17.98z"/></svg>

                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.underline,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.underline() }"
                                            @click="commands.underline"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 333.725 333.725"><path d="M68.773 47.696a7.5 7.5 0 007.5-7.5V15h83.089v255.342H142.14a7.5 7.5 0 000 15h49.443c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5h-17.222V15h83.089v25.196a7.5 7.5 0 0015 0V7.5a7.5 7.5 0 00-7.5-7.5H68.773a7.5 7.5 0 00-7.5 7.5v32.696a7.5 7.5 0 007.5 7.5zM292.862 318.725h-252a7.5 7.5 0 000 15h252a7.5 7.5 0 007.5-7.5c0-4.143-3.357-7.5-7.5-7.5z"/></svg>
                                    </button>


                                    <button v-tooltip.top-center="{
                                            content:buttons.paragraph,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.paragraph() }"
                                            @click="commands.paragraph"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 333 333"><path d="M294.769 0H127.752c-.457 0-.902.047-1.336.126A7.443 7.443 0 00125.08 0H95.685C59.869 0 30.731 29.139 30.731 64.954s29.138 64.954 64.954 64.954h24.567V325.5a7.5 7.5 0 007.5 7.5 7.5 7.5 0 007.5-7.5V15h10.386v310.5a7.5 7.5 0 007.5 7.5 7.5 7.5 0 007.5-7.5V15h94.564v310.5a7.5 7.5 0 007.5 7.5c4.143 0 7.5-3.357 7.5-7.5V15h24.566c4.142 0 7.5-3.357 7.5-7.5S298.911 0 294.769 0zM95.685 114.908c-27.545 0-49.954-22.409-49.954-49.954S68.14 15 95.685 15h24.567v99.908H95.685z"/></svg>
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.H1,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button pr-2"
                                            :class="{ 'is-active': isActive.heading({ level: 1 }) }"
                                            @click="commands.heading({ level: 1 })"
                                    >
                                        H1
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.H2,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button pr-2"
                                            :class="{ 'is-active': isActive.heading({ level: 2 }) }"
                                            @click="commands.heading({ level: 2 })"
                                    >
                                        H2
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.H3,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button pr-2"
                                            :class="{ 'is-active': isActive.heading({ level: 3 }) }"
                                            @click="commands.heading({ level: 3 })"
                                    >
                                        H3
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.bullet_list,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.bullet_list() }"
                                            @click="commands.bullet_list"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 290.5 290.5"><path d="M74 66.001h209c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5H74a7.5 7.5 0 000 15zM283 138H74a7.5 7.5 0 000 15h209a7.5 7.5 0 007.5-7.5 7.5 7.5 0 00-7.5-7.5zM283 224.999H74a7.5 7.5 0 000 15h209c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5zM26 32.501c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11zM26 119.5c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11zM26 205.999c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11z"/></svg>
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.order_list,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.ordered_list() }"
                                            @click="commands.ordered_list"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 366.176 366.176"><path d="M149.676 122.528h209a7.5 7.5 0 000-15h-209a7.5 7.5 0 000 15zM358.676 194.528h-209a7.5 7.5 0 000 15h209a7.5 7.5 0 000-15zM358.676 281.527h-209a7.5 7.5 0 000 15h209a7.5 7.5 0 000-15zM108.813 84.516a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657V84.516h2.657z"/><circle cx="129.176" cy="114.778" r="5.375"/><path d="M108.813 172.016a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM68.313 172.016a7.5 7.5 0 000-15H48a7.5 7.5 0 000 15h2.656v22.762H48a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657z"/><circle cx="129.176" cy="202.215" r="5.375"/><path d="M108.813 258.898a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM68.313 258.898a7.5 7.5 0 000-15H48a7.5 7.5 0 000 15h2.656v22.762H48a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM27.813 258.898a7.5 7.5 0 000-15H7.5a7.5 7.5 0 000 15h2.656v22.762H7.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657z"/><circle cx="129.176" cy="289.097" r="5.375"/></svg>
                                    </button>



                                    <button v-tooltip.top-center="{
                                            content:buttons.undo,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            @click="commands.undo"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2"  viewBox="0 0 332.114 332.114"><path d="M324.614 176.5h-26.506c-.272-73.383-60.049-133-133.494-133-73.612 0-133.5 59.888-133.5 133.5v86.007l-18.31-18.31a7.502 7.502 0 00-10.608 0 7.5 7.5 0 000 10.606l31.113 31.113.03.027c.166.165.339.321.52.47.084.069.173.129.26.194.109.082.217.167.331.243.103.069.208.128.313.19.106.064.208.129.316.188.107.057.218.105.328.158.111.053.222.109.337.156.107.044.217.081.326.12.121.044.241.09.366.128.109.033.221.058.332.085.126.032.251.067.379.092.129.026.26.041.389.06.112.016.223.038.336.049a7.623 7.623 0 001.48 0c.113-.011.224-.033.336-.049.129-.019.26-.034.389-.06.128-.025.253-.061.379-.092.111-.028.223-.052.332-.085.125-.038.245-.084.367-.128.108-.039.218-.075.325-.119.115-.048.226-.104.338-.157.109-.052.22-.1.327-.157.108-.058.211-.124.317-.188.105-.063.211-.122.313-.19.114-.076.222-.161.331-.243.087-.065.176-.125.26-.194.181-.148.354-.305.52-.47l.03-.027 31.113-31.113a7.5 7.5 0 00-10.607-10.606l-18.31 18.31V177c0-65.341 53.159-118.5 118.5-118.5 65.174 0 118.223 52.888 118.494 118h-26.494a7.5 7.5 0 00-7.5 7.5 7.5 7.5 0 007.5 7.5h68a7.5 7.5 0 007.5-7.5 7.497 7.497 0 00-7.498-7.5z"/></svg>
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.redo,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            @click="commands.redo"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 332.113 332.113"><path d="M329.917 244.697a7.5 7.5 0 00-10.607 0L301 263.007V177c0-73.612-59.888-133.5-133.5-133.5-73.445 0-133.223 59.617-133.494 133H7.5a7.5 7.5 0 000 15h68a7.5 7.5 0 000-15H49.006c.271-65.112 53.319-118 118.494-118C232.841 58.5 286 111.659 286 177v86.007l-18.31-18.31a7.5 7.5 0 00-10.606 10.607l31.112 31.112c.175.176.36.342.552.499.083.068.171.127.256.191.11.083.219.168.334.245.103.069.211.129.317.193.104.062.205.128.311.184.11.059.223.109.335.162.11.052.217.106.33.153.11.046.223.083.335.123.119.043.236.088.357.125.112.034.227.059.34.087.124.031.246.066.372.091.131.026.263.042.394.061.111.016.219.037.331.048a7.623 7.623 0 001.48 0c.112-.011.221-.032.331-.048.132-.019.264-.035.395-.061.126-.025.248-.06.371-.09.114-.029.228-.054.341-.088.122-.037.239-.082.357-.125.112-.04.225-.077.335-.123.112-.046.22-.101.33-.153.112-.053.225-.103.335-.162.106-.057.207-.121.31-.183.107-.064.215-.125.319-.194.114-.077.222-.162.332-.244.085-.064.174-.124.257-.192a7.44 7.44 0 00.552-.499l31.112-31.112a7.5 7.5 0 000-10.607z"/></svg>
                                    </button>

                                </div>
                            </editor-menu-bar>

                            <editor-content class="editor__content" :class="{'border-red-500':$v.message.sinhala.$error}" :editor="sinhala"></editor-content>
                            <div class="my-3">
                                <p class="text-base text-blue-400 text-right">{{ charcount.sinhalaChar }} characters</p>
                            </div>
                        </div>

                    </div>

                    <hr class="my-8">

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
                            <editor-menu-bar :editor="tamil" v-slot="{ commands, isActive }">
                                <div class="menubar">

                                    <button v-tooltip.top-center="{
                                            content:buttons.bold,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.bold() }"
                                            @click="commands.bold"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 281.332 281.332"><path d="M198.102 134.449c15.233-11.431 28.497-29.829 28.497-59.239v-.753c0-18.694-6.274-34.93-18.649-48.258a8.073 8.073 0 00-.181-.189C191.021 8.994 165.96 0 135.294 0H49.656a9 9 0 00-9 9v263.332a9 9 0 009 9h90.331c29.385 0 54.297-7.214 72.043-20.863 18.741-14.414 28.647-35.157 28.647-59.988v-.753c0-29.502-14.634-51.788-42.575-65.279zm-57.393 102.175H86.813v-74.919h48.842c19.735 0 35.34 3.551 45.129 10.27 8.757 6.011 13.015 14.474 13.015 25.872v.752c0 34.32-37.128 38.025-53.09 38.025zM130.58 117.372H86.813V44.709h45.955c29.839 0 46.952 12.351 46.952 33.886v.752c-.001 33.085-30.788 38.025-49.14 38.025z"/></svg>
                                    </button>

                                    <button  v-tooltip.top-center="{
                                            content:buttons.italic,
                                             placement: 'top-center',
                                        }" type="button"
                                             class="menubar__button"
                                             :class="{ 'is-active': isActive.italic() }"
                                             @click="commands.italic"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 306.001 306.001"><path d="M249.142 0H120.713a7.5 7.5 0 000 15h54.958l-58.527 276H56.859a7.5 7.5 0 000 15h66.339l.028.001.017-.001h62.044a7.5 7.5 0 000-15h-52.811l58.527-276h58.138a7.5 7.5 0 00.001-15z"/></svg>

                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.strike,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.strike() }"
                                            @click="commands.strike"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 289 289"><path d="M281.5 137h-35.52V25.039a7.5 7.5 0 00-7.5-7.5 7.5 7.5 0 00-7.5 7.5V137H58.02V25.039a7.5 7.5 0 00-7.5-7.5 7.5 7.5 0 00-7.5 7.5V137H7.5c-4.143 0-7.5 3.358-7.5 7.5s3.357 7.5 7.5 7.5h35.52v17.98c0 55.956 45.524 101.481 101.48 101.481s101.48-45.524 101.48-101.481V152h35.52c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5zm-50.52 32.98c0 47.686-38.795 86.481-86.48 86.481s-86.48-38.795-86.48-86.481V152h172.96v17.98z"/></svg>

                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.underline,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.underline() }"
                                            @click="commands.underline"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 333.725 333.725"><path d="M68.773 47.696a7.5 7.5 0 007.5-7.5V15h83.089v255.342H142.14a7.5 7.5 0 000 15h49.443c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5h-17.222V15h83.089v25.196a7.5 7.5 0 0015 0V7.5a7.5 7.5 0 00-7.5-7.5H68.773a7.5 7.5 0 00-7.5 7.5v32.696a7.5 7.5 0 007.5 7.5zM292.862 318.725h-252a7.5 7.5 0 000 15h252a7.5 7.5 0 007.5-7.5c0-4.143-3.357-7.5-7.5-7.5z"/></svg>
                                    </button>


                                    <button v-tooltip.top-center="{
                                            content:buttons.paragraph,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.paragraph() }"
                                            @click="commands.paragraph"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 333 333"><path d="M294.769 0H127.752c-.457 0-.902.047-1.336.126A7.443 7.443 0 00125.08 0H95.685C59.869 0 30.731 29.139 30.731 64.954s29.138 64.954 64.954 64.954h24.567V325.5a7.5 7.5 0 007.5 7.5 7.5 7.5 0 007.5-7.5V15h10.386v310.5a7.5 7.5 0 007.5 7.5 7.5 7.5 0 007.5-7.5V15h94.564v310.5a7.5 7.5 0 007.5 7.5c4.143 0 7.5-3.357 7.5-7.5V15h24.566c4.142 0 7.5-3.357 7.5-7.5S298.911 0 294.769 0zM95.685 114.908c-27.545 0-49.954-22.409-49.954-49.954S68.14 15 95.685 15h24.567v99.908H95.685z"/></svg>
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.H1,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button pr-2"
                                            :class="{ 'is-active': isActive.heading({ level: 1 }) }"
                                            @click="commands.heading({ level: 1 })"
                                    >
                                        H1
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.H2,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button pr-2"
                                            :class="{ 'is-active': isActive.heading({ level: 2 }) }"
                                            @click="commands.heading({ level: 2 })"
                                    >
                                        H2
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.H3,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button pr-2"
                                            :class="{ 'is-active': isActive.heading({ level: 3 }) }"
                                            @click="commands.heading({ level: 3 })"
                                    >
                                        H3
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.bullet_list,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.bullet_list() }"
                                            @click="commands.bullet_list"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 290.5 290.5"><path d="M74 66.001h209c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5H74a7.5 7.5 0 000 15zM283 138H74a7.5 7.5 0 000 15h209a7.5 7.5 0 007.5-7.5 7.5 7.5 0 00-7.5-7.5zM283 224.999H74a7.5 7.5 0 000 15h209c4.143 0 7.5-3.358 7.5-7.5s-3.357-7.5-7.5-7.5zM26 32.501c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11zM26 119.5c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11zM26 205.999c-14.336 0-26 11.664-26 26s11.664 26 26 26 26-11.664 26-26-11.664-26-26-26zm0 37c-6.075 0-11-4.925-11-11s4.925-11 11-11 11 4.925 11 11-4.925 11-11 11z"/></svg>
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.order_list,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            :class="{ 'is-active': isActive.ordered_list() }"
                                            @click="commands.ordered_list"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 366.176 366.176"><path d="M149.676 122.528h209a7.5 7.5 0 000-15h-209a7.5 7.5 0 000 15zM358.676 194.528h-209a7.5 7.5 0 000 15h209a7.5 7.5 0 000-15zM358.676 281.527h-209a7.5 7.5 0 000 15h209a7.5 7.5 0 000-15zM108.813 84.516a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657V84.516h2.657z"/><circle cx="129.176" cy="114.778" r="5.375"/><path d="M108.813 172.016a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM68.313 172.016a7.5 7.5 0 000-15H48a7.5 7.5 0 000 15h2.656v22.762H48a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657z"/><circle cx="129.176" cy="202.215" r="5.375"/><path d="M108.813 258.898a7.5 7.5 0 000-15H88.5a7.5 7.5 0 000 15h2.656v22.762H88.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM68.313 258.898a7.5 7.5 0 000-15H48a7.5 7.5 0 000 15h2.656v22.762H48a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657zM27.813 258.898a7.5 7.5 0 000-15H7.5a7.5 7.5 0 000 15h2.656v22.762H7.5a7.5 7.5 0 000 15h20.313a7.5 7.5 0 000-15h-2.657v-22.762h2.657z"/><circle cx="129.176" cy="289.097" r="5.375"/></svg>
                                    </button>



                                    <button v-tooltip.top-center="{
                                            content:buttons.undo,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            @click="commands.undo"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2"  viewBox="0 0 332.114 332.114"><path d="M324.614 176.5h-26.506c-.272-73.383-60.049-133-133.494-133-73.612 0-133.5 59.888-133.5 133.5v86.007l-18.31-18.31a7.502 7.502 0 00-10.608 0 7.5 7.5 0 000 10.606l31.113 31.113.03.027c.166.165.339.321.52.47.084.069.173.129.26.194.109.082.217.167.331.243.103.069.208.128.313.19.106.064.208.129.316.188.107.057.218.105.328.158.111.053.222.109.337.156.107.044.217.081.326.12.121.044.241.09.366.128.109.033.221.058.332.085.126.032.251.067.379.092.129.026.26.041.389.06.112.016.223.038.336.049a7.623 7.623 0 001.48 0c.113-.011.224-.033.336-.049.129-.019.26-.034.389-.06.128-.025.253-.061.379-.092.111-.028.223-.052.332-.085.125-.038.245-.084.367-.128.108-.039.218-.075.325-.119.115-.048.226-.104.338-.157.109-.052.22-.1.327-.157.108-.058.211-.124.317-.188.105-.063.211-.122.313-.19.114-.076.222-.161.331-.243.087-.065.176-.125.26-.194.181-.148.354-.305.52-.47l.03-.027 31.113-31.113a7.5 7.5 0 00-10.607-10.606l-18.31 18.31V177c0-65.341 53.159-118.5 118.5-118.5 65.174 0 118.223 52.888 118.494 118h-26.494a7.5 7.5 0 00-7.5 7.5 7.5 7.5 0 007.5 7.5h68a7.5 7.5 0 007.5-7.5 7.497 7.497 0 00-7.498-7.5z"/></svg>
                                    </button>

                                    <button v-tooltip.top-center="{
                                            content:buttons.redo,
                                             placement: 'top-center',
                                        }" type="button"
                                            class="menubar__button"
                                            @click="commands.redo"
                                    >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="fill-current w-4 h-4 mr-2" viewBox="0 0 332.113 332.113"><path d="M329.917 244.697a7.5 7.5 0 00-10.607 0L301 263.007V177c0-73.612-59.888-133.5-133.5-133.5-73.445 0-133.223 59.617-133.494 133H7.5a7.5 7.5 0 000 15h68a7.5 7.5 0 000-15H49.006c.271-65.112 53.319-118 118.494-118C232.841 58.5 286 111.659 286 177v86.007l-18.31-18.31a7.5 7.5 0 00-10.606 10.607l31.112 31.112c.175.176.36.342.552.499.083.068.171.127.256.191.11.083.219.168.334.245.103.069.211.129.317.193.104.062.205.128.311.184.11.059.223.109.335.162.11.052.217.106.33.153.11.046.223.083.335.123.119.043.236.088.357.125.112.034.227.059.34.087.124.031.246.066.372.091.131.026.263.042.394.061.111.016.219.037.331.048a7.623 7.623 0 001.48 0c.112-.011.221-.032.331-.048.132-.019.264-.035.395-.061.126-.025.248-.06.371-.09.114-.029.228-.054.341-.088.122-.037.239-.082.357-.125.112-.04.225-.077.335-.123.112-.046.22-.101.33-.153.112-.053.225-.103.335-.162.106-.057.207-.121.31-.183.107-.064.215-.125.319-.194.114-.077.222-.162.332-.244.085-.064.174-.124.257-.192a7.44 7.44 0 00.552-.499l31.112-31.112a7.5 7.5 0 000-10.607z"/></svg>
                                    </button>

                                </div>
                            </editor-menu-bar>

                            <editor-content class="editor__content" :class="{'border-red-500':$v.message.tamil.$error}" :editor="tamil"></editor-content>
                            <div class="my-3">
                                <p class="text-base text-blue-400 text-right">{{ charcount.tamilChar }} characters</p>
                            </div>
                            <div v-if="$v.message.tamil.$dirty">
                                <p class="text-red-500 text-xs italic" v-if="!$v.message.tamil.maxLength">Tamil Alerts must have at most 2500 characters</p>
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
