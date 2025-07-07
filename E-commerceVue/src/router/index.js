import { createRouter, createWebHistory } from 'vue-router'
import ItemPage from '../views/ItemPage.vue'
import Watchlist from '../views/watchlist.vue'
import About from '../views/About.vue'
import Contact from '../views/Contact.vue'

const routes = [
  { path: '/', component: ItemPage },
  { path: '/watchlist', component: Watchlist },
  { path: '/about', component: About },
  { path: '/contact', component: Contact }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
