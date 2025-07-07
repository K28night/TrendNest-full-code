<template>
  <div class="product-list">
    <h2>New Collections</h2>
    <p>
     Experience the finest in fashion. Explore our collection crafted with elegance, detail, and modern trends — where every piece tells a story of style, comfort, and confidence.
    </p>

    <div class="product-grid">
      <div  v-for="product in products"
        :key="product.id"
        class="product-card"
        @click="select(product)">
        
        <div class="product-image">
          <img :src="`http://localhost:9087${product.image}`" :alt="product.name" />
          <span v-if="product.sale" class="sale-tag">Sale</span>
        </div>
        <div class="product-info">
          <h3>{{ product.name }}</h3>
          <div class="price">
            <span class="current">Rs.{{ product.price }}</span>
            <span v-if="product.oldPrice" class="old">Rs.{{ product.oldPrice }}</span>
          </div>
          <p class="variants">{{ product.description }} </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineEmits,onMounted,ref } from 'vue'
const emit = defineEmits(['select'])
import axios from 'axios'

const products = ref([])

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:9087/products')
    products.value = response.data
    console.log(products.value[0].user)
  } catch (error) {
    console.error('Failed to load products:', error)
  }
})
function select(item) {
  emit('select', item)
}
</script>
<style scoped>
.product-list {
    display: flex;
    /* width: 97vw; */
    gap: 2rem;
    padding-top: 95px;
    flex-direction: column;
    padding-bottom: 35px;
    padding-left: 63px;
    background-color: #f2f2f2;
}

.product-list {
    padding-top: 110px;
    padding-bottom: 50px;
    background-color: #f2f2f2;
    text-align: center;
    padding-left: 1rem;
    padding-right: 1rem;
}
h3{
         font-family: 'Playfair Display', serif;
}
.product-list h2 {
    font-size: 3rem;
   font-family: 'Playfair Display', serif;
    font-size: 3.2rem;
    font-weight: 700;
    font-weight: 600;
    margin-bottom: 0.5rem;
    color: #222831;
    letter-spacing: 1px;
}

.product-list p {
    max-width: 800px;
    margin: 0 auto 3rem;
    font-size: 1rem;
    color: #444;
    line-height: 1.6;
    font-family: "Marcellus", Roboto, sans-serif;
}

.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
    gap: 2rem;
    max-width: 1200px;
    margin: 0 44px;
}

.product-card {
    background-color: #fff;
    border-radius: 12px;
    overflow: hidden;
    padding: 1rem;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    transition: transform 0.3s ease;
}

.product-card:hover {
    transform: scale(1.05);
}

.product-image {
    position: relative;
    height: 260px;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.sale-tag {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: #00ADB5;
    color: #EEEEEE;
    padding: 4px 10px;
    font-size: 0.75rem;
    border-radius: 0 8px 0 8px;
    font-weight: bold;
}

.product-info {
    padding: 1rem 0 0;
}

.product-info h3 {
    margin: 0 0 0.5rem;
    font-size: 1.1rem;
    color: #222831;
}

.price {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0.5rem;
}

.price .current {
    color: #00ADB5;
    font-weight: bold;
}

.price .old {
    color: gray;
    text-decoration: line-through;
    font-size: 0.9rem;
}

.variants {
    margin-top: 0.5rem;
    font-size: 0.8rem;
    color: #393E46;
}

</style>
