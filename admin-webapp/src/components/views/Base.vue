<template>
<div>
    <base-header></base-header>
    <router-view></router-view>
</div>
</template>

<script>
import BaseHeader from '@/components/layout/BaseHeader'

export default {
  name: 'Base',
  components: {
    BaseHeader
  }
}
</script>