<template>
<div id="login" class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full">

        <div>
            <h4 class="text-lg text-center font-bold text-gray-900">MyHealth Sri Lanka Management Portal</h4>
            <h2 class="mt-6 text-center text-lg leading-9 font-extrabold text-gray-800">
                Sign in to your account
            </h2>
        </div>

        <form class="mt-8" @submit.prevent="submitCredentials">
            <input type="hidden" name="remember" value="false" />
            <div class="rounded-md shadow-sm">
            <!-- Email Input-->
                <div>
                    <input aria-label="Email address" v-model="username" type="text" required class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5" placeholder="Email address"/>
                </div>

            <!--Password Input-->

                <div class="-mt-px">
                    <input aria-label="Password" v-model="password" type="password" required class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:shadow-outline-blue focus:border-blue-300 focus:z-10 sm:text-sm sm:leading-5" placeholder="Password"/>
                </div>

            </div>

            <div class="mt-6 flex items-center justify-between">
                <div class="flex items-center">
                    <input id="remember_me" type="checkbox" class="form-checkbox h-4 w-4 text-indigo-600 transition duration-150 ease-in-out" />
                    <label for="remember_me" class="ml-2 block text-sm leading-5 text-gray-900">
                        Remember me
                    </label>
                </div>
<!--                Enabled this if you have forgot your password-->

<!--                <div class="text-sm leading-5">-->
<!--                    <a href="#" class="font-medium text-indigo-600 hover:text-indigo-500 focus:outline-none focus:underline transition ease-in-out duration-150">-->
<!--                        Forgot your password?-->
<!--                    </a>-->
<!--                </div>-->
            </div>

            <!--Login Button -->
            <div class="mt-6">
                <button type="submit" class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-500 focus:outline-none focus:border-indigo-700 focus:shadow-outline-indigo active:bg-indigo-700 transition duration-150 ease-in-out">
                      <span class="absolute left-0 inset-y pl-3">
                        <svg class="h-5 w-5 text-indigo-500 group-hover:text-indigo-400 transition ease-in-out duration-150" fill="currentColor" viewBox="0 0 20 20">
                          <path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd" />
                        </svg>
                      </span>
                    Sign in
                </button>
            </div>

        </form>
<!--        form tag ends here-->
        <div v-text="errorMsg" class="text-red-500 text-xs italic"></div>
    </div>
</div>
</template>

<script>
import api from '../../api'

export default {
    name: 'Login',
    data() {
        return {
            submitStatus: false,
            username: '',
            password: '',
            errorMsg: ''
        }
    },
    methods: {
        submitCredentials() {
            this.submitStatus = true;
            const { username, password } = this
            this.resetResponse()
            api.post('/auth',{
                    'username' : username,
                    'password' : password,
                }
            ).then(response => {
                if(response.headers['x-auth-token'] != null){
                    let token = 'x-auth-token ' + response.headers['x-auth-token'];
                    if (localStorage) {
                        localStorage.setItem('token', token);
                    }
                } 
                if (localStorage.getItem('token') != null){
                    if(this.$route.params.nextUrl != null){
                        this.$router.push(this.$route.params.nextUrl);
                    }
                    else {
                        this.$router.push('dashboard');
                    }
                } else {
                    this.errorMsg = 3
                    this.submitStatus = false;
                }
            }).catch(error => {
                let errorStatus = error.response.status;
                console.log(errorStatus);
                if(errorStatus == 403) {
                    this.errorMsg = 'Username/Password incorrect. Please try again.'
                } else if (errorStatus == 404) {
                    this.errorMsg = 'Server appears to be offline'
                }else {
                    this.errorMsg = "Error during login"
                } 
            })
            this.submitStatus = false;
        },
        resetResponse() {
            this.errorMsg = ''
        }
    }
}
</script>