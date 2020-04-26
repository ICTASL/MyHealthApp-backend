<template>
  <div id="notFound">
    <div>
      <h4>Page not found</h4>
      <router-link to="/dashboard" class="ml-4 px-3 py-2 rounded-md text-sm font-medium leading-5 text-blue-500 hover:text-white hover:bg-gray-700 focus:outline-none focus:text-white focus:bg-gray-700 transition duration-150 ease-in-out">Go to dashboard</router-link>
    </div>
  </div>
</template>
<script>
export default {
  name: 'NotFound'
}
</script>