<template>

<!--Main Wrapper-->
<div class="flex flex-col container max-w-6xl  mx-auto bg-gray-50 py-12 px-3 sm:px-6 lg:px-8">
    <div class="w-full flex flex-col mt-10">
        <div class="flex flex-row justify-between items-center">
            <h1 class="text-4xl font-bold">Dashboard</h1>
            <h3><span class="font-medium">Last Updated:</span>{{last_update_time}}</h3>
        </div>
    </div>

    <div class="grid grid-cols-4 gap-4">
        <div class="w-full mt-5">
            <div class="border box-border bg-red-700 rounded shadow-md pt-2  px-4">
                <div class="flex flex-row justify-between pt-4">
                    <h3 class="text-lg font-medium text-white uppercase">Confirmed</h3>
                    <button @click="CCshow" type="button">
                        <svg class="h-5 fill-current text-white" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><path d="M82.6 2c-4 0-8 1.6-10.8 4.4L56.4 22H14C7.4 22 2 27.4 2 34v52c0 6.6 5.4 12 12 12h52c6.6 0 12-5.4 12-12V43.6L93.6 28c6-6 6-15.6 0-21.6-3-2.8-6.8-4.4-11-4.4zM70 86c0 2.2-1.8 4-4 4H14c-2.2 0-4-1.8-4-4V34c0-2.2 1.8-4 4-4h34.4L34.6 43.8c-8 8-12.6 18.8-12.4 30.2 0 2.2 1.8 4 4 4 11.4 0 22.2-4.4 30.2-12.4l13.8-13.8V86H70zm17.8-63.6L50.4 59.8c-5.4 5.4-12.6 9-20.2 9.8 1-7.6 4.4-14.8 9.8-20.2L77.4 12c3-2.8 7.6-2.4 10.4.4 2.6 3 2.6 7.2 0 10z"/>
                        </svg>
                    </button>
                </div>
                <div class="flex flex-row flex-no-wrap items-center justify-between pb-5 pt-3">
                    <svg class="h-20 fill-current text-white" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100">
                        <path d="M32 66c-4.4 0-8 3.6-8 8s3.6 8 8 8 8-3.6 8-8-3.6-8-8-8zm0 12c-2.2 0-4-1.8-4-4s1.8-4 4-4 4 1.8 4 4-1.8 4-4 4zm58-16.8V51c0-.8-.5-1.5-1.2-1.8l-5.7-2.5-3.2-9.5c-.8-2.7-3.7-4.2-5.9-4.2h-1.1l-.9-5.3c-.2-1-1-1.7-2-1.7h-6c-1 0-1.8.7-2 1.7l-.9 5.3H57v-4c0-2.6-2.4-5-5-5H16c-2.6 0-5 2.4-5 5v30.2l-.4.4c-.4.4-.6.9-.6 1.4v6c0 2.6 2.4 5 5 5h4.2c-.1.7-.2 1.3-.1 2 0 1 .1 1.5.3 2.2l.1.3c.2.9 1 1.6 1.9 1.6.1 0 .3 0 .4-.1 1.1-.2 1.8-1.3 1.5-2.4l-.1-.3c-.1-.6-.2-.8-.2-1.4 0-2.4.9-4.7 2.5-6.3 1.7-1.7 4-2.6 6.5-2.6 3.9 0 7.1 2.2 8 5.5.2.9 1 1.5 1.9 1.5h18.4c1.5 0 1.9-.4 2.2-1.3 1.3-3.5 4.6-5.7 8.4-5.7 3.9 0 7.1 2.2 8 5.5v.1c.1.2.1.4.3.5.1.2.2.3.4.4l.1.1c.1.1.3.2.5.3h.1c.3.1.5.1.7.1h5c2.6 0 5-2.4 5-5v-4c0-.5-.2-1-.6-1.4l-.4-.4zM76 38.4l3.3 9.8-.7 1.8h-6.4L69 47.1V37h5c.7 0 1.8.5 2 1.4zM65.7 30h2.6l.5 3h-3.6l.5-3zM87 67c0 .4-.6 1-1 1h-3.6c-1.8-4.2-6.2-7-11.4-7-5 0-9.4 2.7-11.6 7h-16c-1.8-4.2-6.2-7-11.4-7-3.6 0-6.9 1.4-9.3 3.8-.9.9-1.7 2-2.3 3.2H15c-.4 0-1-.6-1-1v-5.2l.4-.4c.1-.1.2-.3.3-.4H21c1.1 0 2-.9 2-2s-.9-2-2-2h-6V29c0-.4.6-1 1-1h36c.4 0 1 .6 1 1v28H43c-1.1 0-2 .9-2 2s.9 2 2 2h10v3c0 1.1.9 2 2 2s2-.9 2-2V37h8v11c0 .6.2 1.1.7 1.5l4.5 4c.4.3.8.5 1.3.5H80c.8 0 1.6-.5 1.9-1.3l.7-1.9 3.4 1.5V60h-2c-1.1 0-2 .9-2 2s.9 2 2 2h3v3zM46 45v-4c0-2.2-1.8-4-4-4h-2v-2c0-2.2-1.8-4-4-4h-4c-2.2 0-4 1.8-4 4v2h-2c-2.2 0-4 1.8-4 4v4c0 2.2 1.8 4 4 4h2v2c0 2.2 1.8 4 4 4h4c2.2 0 4-1.8 4-4v-2h2c2.2 0 4-1.8 4-4zm-10 0v6h-4v-6h-6v-4h6v-6h4v6h6v4h-6zm29-27v-3c0-1.1.9-2 2-2s2 .9 2 2v3c0 1.1-.9 2-2 2s-2-.9-2-2zm14.2 8.1l2.9-.7c1.1-.3 2.2.4 2.4 1.5.3 1.1-.4 2.2-1.5 2.4l-2.9.7h-.5c-.9 0-1.7-.6-1.9-1.5-.3-1.1.4-2.2 1.5-2.4zm-5.8-6.3l1.8-2.4c.7-.9 1.9-1 2.8-.3.9.7 1 1.9.3 2.8l-1.8 2.4c-.4.5-1 .8-1.6.8-.4 0-.9-.1-1.2-.4-.8-.8-1-2.1-.3-2.9zM71 66c-4.4 0-8 3.6-8 8s3.6 8 8 8 8-3.6 8-8-3.6-8-8-8zm0 12c-2.2 0-4-1.8-4-4s1.8-4 4-4 4 1.8 4 4-1.8 4-4 4z"/>
                    </svg>
                    <h3 class="font-bold text-white text-3xl">{{this.lk_total_case}}</h3>
                </div>
            </div>

        </div>
        <div class="w-full mt-5">
            <div class="border box-border bg-orange-500 rounded shadow-md pt-2  px-4">
                <div class="flex flex-row justify-between pt-4">
                    <h3 class="text-lg font-medium text-white uppercase">Suspected</h3>
                    <button type="button" @click="SSshow">
                        <svg class="h-5 fill-current text-white" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><path d="M82.6 2c-4 0-8 1.6-10.8 4.4L56.4 22H14C7.4 22 2 27.4 2 34v52c0 6.6 5.4 12 12 12h52c6.6 0 12-5.4 12-12V43.6L93.6 28c6-6 6-15.6 0-21.6-3-2.8-6.8-4.4-11-4.4zM70 86c0 2.2-1.8 4-4 4H14c-2.2 0-4-1.8-4-4V34c0-2.2 1.8-4 4-4h34.4L34.6 43.8c-8 8-12.6 18.8-12.4 30.2 0 2.2 1.8 4 4 4 11.4 0 22.2-4.4 30.2-12.4l13.8-13.8V86H70zm17.8-63.6L50.4 59.8c-5.4 5.4-12.6 9-20.2 9.8 1-7.6 4.4-14.8 9.8-20.2L77.4 12c3-2.8 7.6-2.4 10.4.4 2.6 3 2.6 7.2 0 10z"/>

                        </svg>
                    </button>
                </div>
                <div class="flex flex-row flex-no-wrap items-center justify-between pb-5 pt-3">
                    <svg class="h-20 fill-current text-white" viewBox="0 0 512.004 512.004" xmlns="http://www.w3.org/2000/svg"><path d="M511.815 489.154c-1.056-9.652-7.294-57.512-30.592-97.696-9.814-16.781-22.916-31.723-38.94-44.41-37.442-30.644-85.685-31.976-90.678-32.029-5.781-.364-12.131-.54-19.956-.552l-2.287-.002v-39.9c12.482-12.221 21.792-27.426 26.942-44.043h4.772c10.469 0 20.302-4.075 27.674-11.462 7.401-7.386 11.477-17.219 11.477-27.688l-.002-.169.002-.171a39.047 39.047 0 00-3.428-16.052c-.521-88.168-29.744-111.798-41.233-117.679-7.219-11.153-35.001-46.754-95.465-46.754-1.171 0-7.134 0-8.241.01-36.182.15-69.572 14.538-94.021 40.511-23.994 25.491-37.208 59.691-37.208 96.3v19.254c-5.533 6.898-8.545 15.414-8.545 24.41v.34c0 10.483 4.078 20.319 11.463 27.675 7.386 7.4 17.219 11.476 27.688 11.476h4.444c5.159 16.628 14.474 31.836 26.96 44.057v39.801c-4.799-.004-9.598.257-14.391.499-3.684.026-27.926.53-54.421 8.987-5.261 1.68-8.165 7.306-6.485 12.567 1.679 5.261 7.308 8.169 12.567 6.485 24.283-7.751 46.208-8.034 48.63-8.041 4.924 0 9.845-.502 14.774-.498 3.742 27.332 23.314 50.749 49.384 59.583A72.853 72.853 0 00256 397.788c27.589 0 53.988-16.336 65.941-41.303 3.33-6.956 5.667-14.369 6.726-22.019l2.958.002c7.52.011 13.552.18 18.982.529.202.013.423.02.626.021.45.002 45.389.465 78.437 27.554.046.037.091.074.137.109 14.094 11.148 25.584 24.229 34.132 38.843 16.691 28.79 23.897 63.039 26.621 79.935h-45.002v-20.11c0-5.522-4.478-10-10-10s-10 4.478-10 10v20.11H92.895v-20.11c0-5.522-4.478-10-10-10s-10 4.478-10 10v20.11H21.442c2.723-16.895 9.926-51.138 26.604-79.904a135.289 135.289 0 017.309-11.188c3.243-4.471 2.25-10.724-2.22-13.967-4.47-3.244-10.723-2.251-13.967 2.219a156.117 156.117 0 00-8.407 12.875C7.482 431.642 1.244 479.502.188 489.154c-.132.938-.229 1.819-.171 2.829.295 5.262 4.671 9.474 10.004 9.474h491.98c5.344 0 9.708-4.229 9.986-9.506.056-.996-.042-1.869-.172-2.797zm-131.59-297.54a18.999 18.999 0 01-5.615 13.303c-3.608 3.614-8.414 5.604-13.533 5.604h-.707c.375-3.717 1.368-38.625.994-38.631a18.995 18.995 0 0113.26 5.609c3.819 3.814 5.41 8.753 5.601 14.115zm-4.122-36.766a39.098 39.098 0 00-14.831-2.972c.093-10.463-6.522-19.883-16.896-22.377-3.535-.85-7.261-.66-10.857-1.087-5.735-.682-11.582-2.06-16.603-5.015a24.11 24.11 0 01-3.542-2.547c12.442-5.034 30.106-16.06 40.362-39.676 7.93 8.864 19.422 29.176 22.367 73.674zm-235.471-7.481c0-31.493 11.283-60.825 31.771-82.592 20.651-21.938 48.899-34.092 79.585-34.219 1.128-.01 6.965-.005 8.113-.01 48.538 0 71.227 26.733 77.711 36.147-10.159 31.666-36.626 37.559-40.697 38.282-18.039.097-35.905 3.251-53.122 9.395-17.243 6.168-33.321 15.147-47.778 26.681-.564.449-1.14.884-1.726 1.305-8.801 6.319-20.126 9.52-33.737 9.52-6.737 0-13.557-.391-20.12 1.445zm-2.948 57.529c-3.609-3.596-5.598-8.398-5.598-13.525v-.34c0-5.126 1.988-9.929 5.617-13.545 3.452-3.452 8.164-5.46 13.04-5.585.02 12.851-.422 25.818.913 38.62h-.419c-5.119 0-9.925-1.99-13.553-5.625zm35.432 13.543a90.247 90.247 0 01-.656-3.417c-.789-4.739-1.141-9.624-1.486-14.797-.407-9.588-.233-19.239-.229-28.835 13.705-1.378 27.09-6.066 37.934-14.695 12.74-10.164 26.889-18.067 42.045-23.489 13.235-4.723 26.901-7.435 40.716-8.085 6.401 11.839 18.557 19.573 35.312 22.501 2.065.385 4.169.632 6.258.833 1.683.162 1.35.191 8.189.73.038.003.02 29.804-.024 38.78-.165 10.189-.005 20.383-2.285 30.384-3.893 17.195-13.234 32.917-26.46 44.685-47.286 42.048-125.285 17.236-139.314-44.595zm29.525 106.367V289.939c17.481 10.321 37.801 15.353 58.076 14.436a104.836 104.836 0 0048.645-14.449v34.82c-.176 29.247-24.113 53.041-53.36 53.041s-53.185-23.794-53.361-52.981z"/><path d="M233.458 194.145c5.522 0 10-4.478 10-10 0-3.997 1.61-7.701 4.535-10.431 2.921-2.728 6.741-4.086 10.762-3.798 7.037.485 12.71 6.158 13.195 13.195.44 6.383-3.438 12.334-9.432 14.474-8.851 3.158-14.797 11.61-14.797 21.033v6.322c0 5.522 4.478 10 10 10s10-4.478 10-10v-6.322c0-.99.61-1.873 1.52-2.197 14.396-5.138 23.716-19.401 22.662-34.686-1.169-16.944-14.828-30.604-31.772-31.772-9.618-.671-18.769 2.579-25.785 9.13a34.39 34.39 0 00-10.888 25.052c0 5.523 4.477 10 10 10zM266.971 252.731c-1.546-3.707-5.258-6.164-9.269-6.166-4.442-.002-8.406 3.065-9.597 7.329-2.441 8.739 7.645 15.978 15.176 10.978 3.935-2.612 5.476-7.783 3.69-12.141zM67.521 358.132c1.668 4.001 5.876 6.574 10.209 6.129 4.33-.445 7.966-3.727 8.821-7.999 1.742-8.704-8.328-15.172-15.552-10.129-3.804 2.655-5.227 7.728-3.478 11.999z"/></svg>
                    <h3 class="font-bold text-white text-3xl">{{this.lk_total_suspect}}</h3>
                </div>
            </div>

        </div>
        <div class="w-full mt-5">
            <div class="border box-border bg-green-700 rounded shadow-md pt-2  px-4">
                <div class="flex flex-row justify-between pt-4">
                    <h3 class="text-lg font-medium text-white uppercase">Recovered</h3>
                    <button type="button" @click="RRshow">
                        <svg class="h-5 fill-current text-white" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><path d="M82.6 2c-4 0-8 1.6-10.8 4.4L56.4 22H14C7.4 22 2 27.4 2 34v52c0 6.6 5.4 12 12 12h52c6.6 0 12-5.4 12-12V43.6L93.6 28c6-6 6-15.6 0-21.6-3-2.8-6.8-4.4-11-4.4zM70 86c0 2.2-1.8 4-4 4H14c-2.2 0-4-1.8-4-4V34c0-2.2 1.8-4 4-4h34.4L34.6 43.8c-8 8-12.6 18.8-12.4 30.2 0 2.2 1.8 4 4 4 11.4 0 22.2-4.4 30.2-12.4l13.8-13.8V86H70zm17.8-63.6L50.4 59.8c-5.4 5.4-12.6 9-20.2 9.8 1-7.6 4.4-14.8 9.8-20.2L77.4 12c3-2.8 7.6-2.4 10.4.4 2.6 3 2.6 7.2 0 10z"/>

                        </svg>
                    </button>
                </div>
                <div class="flex flex-row flex-no-wrap items-center justify-between pb-5 pt-3">

                    <svg class="h-20 fill-current text-white" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 382 382">
                        <path d="M54.592 58.904a7 7 0 015.176-8.44c1.736-.416 3.439-.84 5.108-1.272a7.001 7.001 0 113.492 13.56c-1.744.448-3.523.89-5.336 1.324a6.988 6.988 0 01-8.44-5.172zm239.12.732C173.352 45.772 156.748 5.548 156.64 5.276a7.033 7.033 0 00-13.232-.132c-.076.192-7.784 19.34-51.692 35.724a7 7 0 104.892 13.116c30.716-11.46 46-24.496 53.42-33.484a87.32 87.32 0 0019.54 16.86c26.04 17.08 65.16 28.992 116.344 35.436v127.12c.088 61.15-33.423 117.406-87.236 146.448A275.256 275.256 0 01150 367.188a275.256 275.256 0 01-48.676-20.824C47.511 317.322 14 261.066 14.088 199.916V72.792c7.492-.944 14.764-2 21.7-3.2a7 7 0 00-2.34-13.804c-8.588 1.456-17.724 2.748-27.16 3.836a6.997 6.997 0 00-6.2 6.952v133.34c-.089 66.295 36.243 127.282 94.584 158.768A286.756 286.756 0 00148 381.212a6.988 6.988 0 004.024 0 286.756 286.756 0 0053.316-22.528c58.341-31.486 94.673-92.473 94.584-158.768V66.6a6.996 6.996 0 00-6.212-6.964zM212.26 303.4c-1.067.96-2.152 1.903-3.256 2.828A7 7 0 10218 316.952c1.21-1.019 2.41-2.056 3.6-3.112a7.004 7.004 0 00-9.34-10.44zm17.64-7.728a7.004 7.004 0 009.832-1.2 153.716 153.716 0 0032.668-94.556V90.404a7.003 7.003 0 00-5.82-6.9C238.12 78.64 185.696 67 154.168 43.6a6.984 6.984 0 00-8.336 0C114.304 67 61.88 78.64 33.436 83.504a7.004 7.004 0 00-5.836 6.9v109.512c-.08 56.18 30.707 107.864 80.148 134.544a269.652 269.652 0 0039.972 17.74 7.028 7.028 0 004.56 0 269.468 269.468 0 0039.972-17.752 7 7 0 00-6.652-12.32 258.252 258.252 0 01-35.6 16.036 258.252 258.252 0 01-35.6-16.036c-44.908-24.236-72.873-71.182-72.8-122.212V96.276C75.652 90.064 119.572 78.544 150 57.8c30.428 20.736 74.348 32.256 108.4 38.468v103.64a139.636 139.636 0 01-29.66 85.936 7 7 0 001.16 9.84v-.012zm1.1-82.832v-43.692a7 7 0 00-7-7h-45.148V117a7 7 0 00-7-7h-43.704a7 7 0 00-7 7v45.148H76a7 7 0 00-7 7v43.704a7 7 0 007 7h45.148V265a7 7 0 007 7h43.704a7 7 0 007-7v-27.332a7 7 0 00-14 0V258h-29.704v-45.148a7.004 7.004 0 00-7-7H83v-29.704h45.148a7.004 7.004 0 007-7V124h29.704v45.148a7.004 7.004 0 007 7H217v29.704h-45.148a7 7 0 000 14H224a7 7 0 007-7v-.012z" />
                    </svg>
                    <h3 class="font-bold text-white text-3xl">{{this.lk_recovered_case}}</h3>

                </div>
            </div>

        </div>
        <div class="w-full mt-5">
            <div class="border box-border bg-blue-900 rounded shadow-md pt-2  px-4">
                <div class="flex flex-row justify-between pt-4">
                    <h3 class="text-lg font-medium text-white uppercase">Deaths</h3>
                    <button type="button" @click="DDshow">
                        <svg class="h-5 fill-current text-white" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><path d="M82.6 2c-4 0-8 1.6-10.8 4.4L56.4 22H14C7.4 22 2 27.4 2 34v52c0 6.6 5.4 12 12 12h52c6.6 0 12-5.4 12-12V43.6L93.6 28c6-6 6-15.6 0-21.6-3-2.8-6.8-4.4-11-4.4zM70 86c0 2.2-1.8 4-4 4H14c-2.2 0-4-1.8-4-4V34c0-2.2 1.8-4 4-4h34.4L34.6 43.8c-8 8-12.6 18.8-12.4 30.2 0 2.2 1.8 4 4 4 11.4 0 22.2-4.4 30.2-12.4l13.8-13.8V86H70zm17.8-63.6L50.4 59.8c-5.4 5.4-12.6 9-20.2 9.8 1-7.6 4.4-14.8 9.8-20.2L77.4 12c3-2.8 7.6-2.4 10.4.4 2.6 3 2.6 7.2 0 10z"/>
                        </svg>
                    </button>
                </div>
                <div class="flex flex-row flex-no-wrap items-center justify-between pb-5 pt-3">
                    <svg class="h-20 fill-current text-white" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 500 500" >
                        <path d="M489.5 467h-69.203a110.543 110.543 0 008.343-5.427c23.128-16.565 38.695-41.105 43.836-69.099.014-.076.026-.152.038-.229l20.041-131.954a7.5 7.5 0 00-6.289-8.541c-4.096-.626-7.919 2.193-8.541 6.289l-20.023 131.836c-4.455 24.106-17.875 45.233-37.796 59.502-19.643 14.071-43.939 20.242-68.579 16.602a94.144 94.144 0 01-57.672-31.811c-14.674-17.097-22.755-38.878-22.755-61.329 0-2.839.131-5.739.396-8.691l8.677-108.255.103.001h52.07c15.131 0 27.44-12.314 27.44-27.45v-104.4c0-29.548-23.925-53.48-53.48-53.48-2.883 0-5.713.229-8.474.671l5.406-36.657c1.822-12.312 13.185-21.01 25.328-19.384 8.331 1.113 13.741 5.239 16.538 12.613 2.217 5.846 2.017 11.741 1.956 12.844l-4.43 30.009c-1.454 9.856 13.377 12.09 14.84 2.19l4.459-30.21c.015-.089.027-.177.039-.266 4.316-23.535 28.524-14.918 26.24.504l-1.483 10.084-.018.112-5.128 34.735c-1.455 9.865 13.378 12.078 14.84 2.19l4.967-33.646c2.527-4.925 7.933-7.758 13.464-6.944 7.104 1.051 12.023 7.724 10.969 14.873l-3.35 22.65a7.116 7.116 0 00-.032.25l-3.882 26.293c-1.455 9.865 13.378 12.078 14.84 2.19l3.727-25.243A12.953 12.953 0 01450 84.168c7.113 1.051 12.034 7.697 10.968 14.876l-4.31 29.2a9.197 9.197 0 00-.03.232l-3.467 23.485c-1.455 9.865 13.378 12.078 14.84 2.19l3.391-22.973c2.219-5.597 8.002-8.976 13.993-8.089 7.103 1.046 12.025 7.711 10.973 14.855l-11.458 77.62a222.207 222.207 0 01-1.828 10.526 7.5 7.5 0 007.364 8.966 7.502 7.502 0 007.348-6.038 236.223 236.223 0 001.955-11.254l11.46-77.632c2.258-15.329-8.341-29.631-23.621-31.882a27.858 27.858 0 00-12.975 1.145l1.204-8.155c2.281-15.373-8.291-29.644-23.617-31.911a27.985 27.985 0 00-12.29.918l.559-3.773c2.262-15.331-8.33-29.643-23.618-31.905a27.888 27.888 0 00-14.087 1.563c-1.957-11.359-10.997-21.109-23.491-22.961-8.104-1.215-15.638.718-21.646 6.248-3.898-8.13-11.694-17.014-27.265-19.095-20.229-2.701-39.137 11.677-42.153 32.06l-6.501 44.08c-17.249 8.885-29.073 26.877-29.073 47.578v104.4c0 9.689 5.046 18.221 12.648 23.108-9.841 122.879-9.135 113.399-9.272 116.601-.13-3.019 1.184 13.979-19.099-239.141a229.933 229.933 0 00-2.21-22.811L223.796 32.38C220.788 12.003 201.874-2.37 181.643.33c-14.558 1.945-22.832 10.029-27.223 19.101-5.073-4.693-12.141-7.669-21.665-6.26-12.245 1.809-21.479 11.356-23.5 22.966a27.876 27.876 0 00-14.098-1.563C79.873 36.832 69.278 51.14 71.541 66.471l.558 3.779a28.011 28.011 0 00-12.296-.915c-15.285 2.259-25.879 16.568-23.617 31.897l1.204 8.16a27.892 27.892 0 00-12.967-1.144C9.138 110.507-1.457 124.812.805 140.137l11.458 77.629a230.607 230.607 0 003.565 18.725l23.654 155.755c.012.077.024.153.039.229 2.921 15.909 9.134 30.607 18.465 43.687a7.5 7.5 0 0012.211-8.711c-8.022-11.247-13.373-23.888-15.903-37.575l-23.68-155.932a7.336 7.336 0 00-.114-.591 215.224 215.224 0 01-3.397-17.777l-11.458-77.629c-1.054-7.144 3.867-13.81 10.971-14.86 5.996-.887 11.788 2.503 13.994 8.109l3.388 22.953c1.474 9.993 16.277 7.549 14.839-2.19l-3.5-23.716-.006-.039-4.305-29.163c-1.055-7.148 3.866-13.817 10.97-14.867 5.264-.779 10.394 1.711 13.082 6.248l3.726 25.245c1.474 9.993 16.277 7.549 14.839-2.19l-3.917-26.538L86.38 64.28c-1.055-7.148 3.866-13.818 10.97-14.867 5.52-.813 10.932 2.015 13.463 6.941L115.779 90c1.474 9.993 16.277 7.549 14.839-2.19l-5.126-34.729-.001-.006-1.506-10.199c-1.055-7.148 3.863-13.817 10.962-14.866 5.277-.78 8.937.929 11.86 5.543 2.578 4.07 3.398 8.707 3.458 9.06l4.464 30.238c1.474 9.993 16.277 7.549 14.839-2.19l-4.427-29.993c-.218-3.07-.857-22.883 18.489-25.468 12.147-1.626 23.511 7.066 25.328 19.372l10.896 73.821a215.079 215.079 0 012.071 21.44c.004.085.009.169.016.254l18.769 234.146c4.508 49.756-30.648 94.448-80.038 101.745-24.1 3.561-48.988-2.118-69.699-17.415a7.501 7.501 0 00-8.912 12.066 111.399 111.399 0 009.635 6.373H22.5C10.093 467 0 477.093 0 489.5 0 501.907 10.093 512 22.5 512h467c12.406 0 22.5-10.093 22.5-22.5S501.906 467 489.5 467zM344.587 198.167h-34.88c-5.055 0-9.167-4.112-9.167-9.167 0-5.054 4.112-9.167 9.167-9.167h34.88v18.334zm-76.961-74.122c0-21.218 17.263-38.48 38.48-38.48 10.275 0 19.938 4.003 27.206 11.273 7.271 7.27 11.274 16.932 11.274 27.207v40.788h-34.88c-13.326 0-24.167 10.841-24.167 24.167 0 13.326 10.841 24.167 24.167 24.167h34.88v15.278c0 6.865-5.581 12.45-12.44 12.45h-52.07c-6.878 0-12.45-5.6-12.45-12.45v-104.4zm-11.623 253.519c1.061 24.35 10.294 47.76 26.269 66.375a109.087 109.087 0 0027.61 23.062h-107.75c31.409-18.452 52.261-51.952 53.871-89.437zM489.5 497h-467c-4.136 0-7.5-3.364-7.5-7.5 0-4.135 3.364-7.5 7.5-7.5h467c4.136 0 7.5 3.364 7.5 7.5s-3.364 7.5-7.5 7.5z"/><path d="M305.246 151.635c14.888 0 27-12.112 27-27s-12.112-27-27-27-27 12.112-27 27 12.112 27 27 27zm0-39c6.617 0 12 5.383 12 12s-5.383 12-12 12-12-5.383-12-12 5.383-12 12-12z"/></svg>

                    <h3 class="font-bold text-white text-3xl">{{this.lk_total_deaths}}</h3>
                </div>
            </div>
        </div>
    </div>

<!--    //Confirmed Cases-->
    <modal name="confirmed-cases" height="auto">
        <div class="p-4">
            <div>
                <h2 class="text-lg font-normal">Update the "Confirm" Records</h2>
            </div>
            <form @submit.prevent="CCsubmit" method="post">
                <div class="flex justify-between items-center mt-3">
                    <div class="w-1/2">
                        Confirmed Cases:
                    </div>
                    <div class="w-1/2">
                        <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                               type="number" placeholder="lk_total_case" value="lk_total_case" name="title" id="title" v-model="lk_total_case" v-on:keypress="isNumber()"  required >
                    </div>
                </div>
                <hr class="mt-4">
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse mt-3">
                  <span class="flex w-full rounded-md shadow-sm sm:ml-3 sm:w-auto">
                        <button   type="submit" class="inline-flex justify-center w-full rounded-md border border-transparent px-4 py-2 bg-blue-600 text-base leading-6 font-medium text-white shadow-sm hover:bg-red-500 focus:outline-none focus:border-red-700 focus:shadow-outline-red transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Update
                        </button>
                  </span>
                    <span class="mt-3 flex w-full rounded-md shadow-sm sm:mt-0 sm:w-auto">
                        <button  @click="CChide" type="button" class="inline-flex justify-center w-full rounded-md border border-gray-300 px-4 py-2 bg-white text-base leading-6 font-medium text-gray-700 shadow-sm hover:text-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Cancel
                        </button>
                  </span>
                </div>
            </form>
        </div>

    </modal>

<!--    //Suspected Cases-->
    <modal name="suspected-cases" height="auto">
        <div class="p-4">
            <div>
                <h2 class="text-lg font-normal">Update the "Suspected" Records</h2>
            </div>
            <form @submit.prevent="SSsubmit" method="post">
                <div class="flex justify-between items-center mt-3">
                    <div class="w-1/2">
                        Suspected Cases:
                    </div>
                    <div class="w-1/2">
                        <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                               type="number" placeholder="lk_total_suspect" value="lk_total_suspect" name="title" id="ss" v-model="lk_total_suspect" v-on:keypress="isNumber()"  required >
                    </div>
                </div>
                <hr class="mt-4">
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse mt-3">
                  <span class="flex w-full rounded-md shadow-sm sm:ml-3 sm:w-auto">
                        <button   type="submit" class="inline-flex justify-center w-full rounded-md border border-transparent px-4 py-2 bg-blue-600 text-base leading-6 font-medium text-white shadow-sm hover:bg-red-500 focus:outline-none focus:border-red-700 focus:shadow-outline-red transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Update
                        </button>
                  </span>
                    <span class="mt-3 flex w-full rounded-md shadow-sm sm:mt-0 sm:w-auto">
                        <button  @click="SShide" type="button" class="inline-flex justify-center w-full rounded-md border border-gray-300 px-4 py-2 bg-white text-base leading-6 font-medium text-gray-700 shadow-sm hover:text-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Cancel
                        </button>
                  </span>
                </div>
            </form>
        </div>

    </modal>

    <!--    //recover Cases-->
    <modal name="recover-cases" height="auto">
        <div class="p-4">
            <div>
                <h2 class="text-lg font-normal">Update the "Recovered%" Records</h2>
            </div>
            <form @submit.prevent="RRsubmit" method="post">
                <div class="flex justify-between items-center mt-3">
                    <div class="w-1/2">
                        Recover:
                    </div>
                    <div class="w-1/2">
                        <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                               type="number" placeholder="lk_total_suspect" value="lk_recovered_case" name="title" id="rr" v-on:keypress="isNumber()"  v-model="lk_recovered_case" required >
                    </div>
                </div>
                <hr class="mt-4">
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse mt-3">
                  <span class="flex w-full rounded-md shadow-sm sm:ml-3 sm:w-auto">
                        <button   type="submit" class="inline-flex justify-center w-full rounded-md border border-transparent px-4 py-2 bg-blue-600 text-base leading-6 font-medium text-white shadow-sm hover:bg-red-500 focus:outline-none focus:border-red-700 focus:shadow-outline-red transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Update
                        </button>
                  </span>
                    <span class="mt-3 flex w-full rounded-md shadow-sm sm:mt-0 sm:w-auto">
                        <button  @click="RRhide" type="button" class="inline-flex justify-center w-full rounded-md border border-gray-300 px-4 py-2 bg-white text-base leading-6 font-medium text-gray-700 shadow-sm hover:text-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Cancel
                        </button>
                  </span>
                </div>
            </form>
        </div>

    </modal>

    <!--    //recover Cases-->
    <modal name="deaths-cases" height="auto">
        <div class="p-4">
            <div class="flex flex-row justify-between items-center">
                <h2 class="text-lg font-normal">Update the "Deaths" Records</h2>

            </div>
            <form @submit.prevent="DDsubmit" method="post">
                <div class="flex justify-between items-center mt-3">
                    <div class="w-1/2">
                        Deaths:
                    </div>
                    <div class="w-1/2">
                        <input class=" bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 mt-2 px-4 text-gray-700 leading-tight focus:outline-none focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5"
                               type="number" placeholder="lk_total_suspect" value="lk_recovered_case" name="title" id="rr" v-model="lk_total_deaths" v-on:keypress="isNumber()" required >
                    </div>
                </div>
                <hr class="mt-4">
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse mt-3">
                  <span class="flex w-full rounded-md shadow-sm sm:ml-3 sm:w-auto">
                        <button   type="submit" class="inline-flex justify-center w-full rounded-md border border-transparent px-4 py-2 bg-blue-600 text-base leading-6 font-medium text-white shadow-sm hover:bg-red-500 focus:outline-none focus:border-red-700 focus:shadow-outline-red transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Update
                        </button>
                  </span>
                    <span class="mt-3 flex w-full rounded-md shadow-sm sm:mt-0 sm:w-auto">
                        <button  @click="DDhide" type="button" class="inline-flex justify-center w-full rounded-md border border-gray-300 px-4 py-2 bg-white text-base leading-6 font-medium text-gray-700 shadow-sm hover:text-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline transition ease-in-out duration-150 sm:text-sm sm:leading-5">
                          Cancel
                        </button>
                  </span>
                </div>
            </form>
        </div>
    </modal>
</div>

<!--Main Wrapper Ends Here-->
</template>

<script src="./dashboard.js"></script>