<template>
<!--Navigation Wrapper-->
<nav x-data="{ open: false }" class="bg-gray-800">
    <div class="max-w-6xl mx-auto px-2 sm:px-6 lg:px-8">
        <div class="relative flex items-center justify-between h-16">
            <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
                <button x-on:click="open = true" class="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:bg-gray-700 focus:text-white transition duration-150 ease-in-out">
                    <svg class="h-6 w-6" stroke="currentColor" fill="none" viewBox="0 0 24 24">
                        <path class="inline-flex" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
                        <path class="hidden" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                    </svg>
                </button>
            </div>
            <!--Desktop Wrapper-->
            <div class="flex-1 flex items-center justify-center sm:items-stretch sm:justify-start">
                <h4 class="text-lg font-bold text-white">MyHealth Sri Lanka Management Portal</h4>
                <div class="flex-shrink-0">
<!--                    <img class="block lg:hidden h-8 w-auto" th:src="@{/Covid-19.png}" alt="Covid-19"  />-->
<!--                    <img class="hidden lg:block h-8 w-auto" th:src="@{/Covid-19.png}"alt="Covid-19" />-->
                </div>
                <div class="hidden sm:block sm:ml-6">
                    <div class="flex">
                        <router-link to="/dashboard" class="ml-4 px-3 py-2 rounded-md text-sm font-medium bg-gray-900 leading-5 text-gray-300 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Dashboard</router-link>
                        <router-link to="/news" class="ml-4 px-3 py-2 rounded-md text-sm font-medium leading-5 text-gray-300 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Send Alerts</router-link>
                        <router-link to="/cases" class="ml-4 px-3 py-2 rounded-md text-sm font-medium leading-5 text-gray-300 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Send Case</router-link>
                    </div>
                </div>
            </div>
            <!--Mobile Wrapper-->
          <div>
              <router-link to="/portal/logout" class="ml-4 px-3 py-2 rounded-md text-sm font-medium leading-5 text-gray-300 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Sign out</router-link>

          </div>
        </div>
    </div>
    <div x-show="open" x-on:click.away="open = false">
        <div class="px-2 pt-2 pb-3">
            <router-link to="/dashboard" class="mt-1 block px-3 py-2 rounded-md text-base font-medium text-gray-300 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Dashboard</router-link>
            <router-link to="/news" class="mt-1 block px-3 py-2 rounded-md text-base font-medium text-gray-300 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Send News</router-link>
            <router-link to="/cases" class="mt-1 block px-3 py-2 rounded-md text-base font-medium text-gray-300 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Send Case</router-link>
        </div>
    </div>
</nav>
</template>

<script>
export default {
  name: 'BaseHeader'
}
</script>