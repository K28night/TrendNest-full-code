<script setup>
import { ref } from 'vue'
import Prodects from '../components/Prodects.vue'
import ItemDetails from '../components/ItemDetails.vue'

// reactive state holding the selected item
const selectedItem = ref(null)

// method to set the selected item
function selectItem(item) {
  selectedItem.value = item
}
</script>

<template>
  <div>
   
    <Prodects v-if="!selectedItem" @select="selectItem" />
    <ItemDetails
      v-else
      :item="selectedItem"
      @back="selectedItem = null"
    />
  </div>
</template>
