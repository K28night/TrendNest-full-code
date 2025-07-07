<template>

  <div class="watchlist-page">
    <!-- <button @click="emit('watchlists', false)">Back</button> -->

    <h2>Your Watchlist</h2>
    <div class="noitems" v-if="watchlist.length === 0">No items in watchlist</div>
    <div v-else class="product-grid">
      <div v-for="item in watchlist" :key="item.id" class="product-card">
        <img :src="'http://localhost:9087' + item.image" :alt="item.name" />
        <h3>{{ item.name }}</h3>
        <p>Price: ₹{{ item.price }}</p>
        <p><strong>Seller:</strong> {{ item.user.fullName }}</p>
        <button @click="removeFromWatchlist(item.id)">Remove</button>

      </div>
    </div>
  </div>

</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useToast } from 'vue-toastification'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
const toast = useToast()
const watchlist = ref([])
const user = JSON.parse(localStorage.getItem('user'))

const fetchWatchlist = async () => {
  const response = await axios.get('http://localhost:9087/watchlist', {
    params: { userId: user.id }
  })
  watchlist.value = response.data
}

const removeFromWatchlist = async (productId) => {
  try {
    await axios.delete('http://localhost:9087/watchlist', {
      params: {
        userId: user.id,
        productId
      }
    })
    toast.success('Removed from watchlist')
    // Refresh the list
    watchlist.value = watchlist.value.filter(item => item.id !== productId)
  } catch (err) {
    toast.error('Failed to remove')
  }
}

onMounted(fetchWatchlist)
</script>
<style scoped>
.watchlist-page {
  font-family: 'Playfair Display', serif;
  padding-top: 140px;
}

.product-grid {
    display: flex;
}

.product-card {
  background: #f9f9f9;
  padding: 1rem;
  border-radius: 12px;
  width: 14rem;
  margin: 20px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}


.product-card img {
  max-width: 100%;
  height: 260px;
  object-fit: cover;
  border-radius: 6px;
}

button {
  background: slategray;
  color: darkred;
}

.noitems {
    padding-top: 2rem;
    font-size: 19px;
    padding-bottom: 5rem;
}
</style>
