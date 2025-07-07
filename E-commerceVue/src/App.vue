<script setup>
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import { RouterView } from 'vue-router'

</script>

<template>
  <Header /> <!-- Header with nav -->
  <main class="main-content">
    <RouterView /> <!-- Dynamically loads current route -->
  </main>
  <Footer />
</template>
