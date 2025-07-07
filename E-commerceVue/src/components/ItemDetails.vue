<template>
  <div class="item-detail">
    <button @click="$emit('back')">← Back</button>
    <div class="product-card">
      <img :src="`http://localhost:9087${item.image}`" :alt="item.image" />
      <div class="info">
        <h1>{{ item.name }}</h1>
        <p class="price">Rs.{{ item.price }}</p>
        <p>{{ item.description || 'Detailed description goes here.' }}</p>
         <p>Seller Name: {{ item.user.fullName || 'Detailed description goes here.' }}</p>
           <button @click="addToWatchlist(item.id)">Add to Watchlist</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useToast } from 'vue-toastification'

const toast = useToast()
// const products = ref([])
const user = JSON.parse(localStorage.getItem('user'))
defineProps(['item'])
defineEmits(['back'])
const addToWatchlist = async (productId) => {
  try {
    await axios.post('http://localhost:9087/Watchlist', null, {
      params: {
        userId: user.id,
        productId: productId
      }
    })
    toast.success('Added to watchlist!')
  } catch (error) {
    toast.error('Failed to add to watchlist')
  }
}
</script>


<style scoped>
.item-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
      padding-top: 93px;
    padding-bottom: 20px;
}

.product-card {
  display: flex;
  max-width: 900px;
  background: var(--bg-light);
  border: 1px solid var(--bg-muted);
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}

.product-card img {
  width: 400px;
  object-fit: cover;
}

.info {
  padding: 2rem;
  display: flex;
  flex-direction: column;
  flex: 1;
}

.info h1 {
  color: var(--text-dark);
  margin: 0;
}

.price {
  color: var(--accent);
  font-size: 2rem;
  margin: 1rem 0;
}

.desc {
  flex: 1;
  color: var(--text-dark);
  margin-bottom: 2rem;
}

button {
  align-self: start;
  background: var(--accent);
  color: var(--text-light);
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.2s;
}

button:hover {
  background: var(--bg-muted);
}
</style>
