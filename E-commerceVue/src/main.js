import { createApp } from 'vue'
import './style.css'
import router from './router'
import App from './App.vue'
import Toast from 'vue-toastification'
import 'vue-toastification/dist/index.css'

const app = createApp(App)
app.use(Toast, {
  position: 'top-right',
  timeout: 3000
})
app.use(router)
app.mount('#app')