<script setup>
import { ref, onMounted, onUnmounted, Teleport } from 'vue'
import { useForm, useField } from 'vee-validate'
import * as yup from 'yup'
import axios from 'axios'
import addProduct from './addProduct.vue'
import { useToast } from 'vue-toastification'
import { BookMarkedIcon } from 'lucide-vue-next'
// import { useRouter } from 'vue-router'



// const router = useRouter()
const toast = useToast()
const open = ref(false)
const addform = ref(false)
const toggleForm = (formType) => {
    activeForm.value = formType
}
const vFocus = {
  mounted: (el) => el.focus()
}

const activeForm = ref('login')

const showProfile = ref(false)

// 🔹 Schema with detailed rules
const registerSchema = yup.object({
    name: yup
        .string()
        .required('Full Name is required')
        .matches(/^[A-Za-z ]+$/, 'Only alphabets and spaces allowed'),
    email: yup
        .string()
        .required('Email is required')
        .email('Invalid email format'),
    password: yup
        .string()
        .required('Password is required')
        .min(8, 'Password must be at least 8 characters'),
    confirmPassword: yup
        .string()
        .required('Confirm Password is required')
        .oneOf([yup.ref('password')], 'Passwords must match'),
    phone: yup
        .string()
        .required('Phone number is required')
        .matches(/^\d{10}$/, 'Phone must be 10 digits'),
})

// 🔹 useForm setup with real-time validation
const { handleSubmit, errors, resetForm } = useForm({
    validationSchema: registerSchema,
    validateOnInput: true, // Real-time feedback
})

// 🔹 Fields
const { value: name } = useField('name')
const { value: email } = useField('email')
const { value: password } = useField('password')
const { value: confirmPassword } = useField('confirmPassword')
const { value: phone } = useField('phone')
const logout = () => {
    localStorage.removeItem('user')
    localStorage.removeItem('token')
    loggedInUser.value = null
    toast.info('Logged out successfully')
}
const loggedInUser = ref(null)

onMounted(() => {
    const userData = localStorage.getItem('user')
    if (userData) {
        loggedInUser.value = JSON.parse(userData)
    }
})
const submitForm = handleSubmit(async (values) => {
    try {
        const response = await axios.post('http://localhost:9087/registration', {
            fullName: values.name,
            email: values.email,
            password: values.password,
            mobileNumber: values.phone
        })
        toast.success('Registration successful!')

        resetForm();
    } catch (error) {
        toast.error(error.response?.data?.message || ' Registration failed.')
    }
})

const username = ref('')

const emit = defineEmits(['watchlists'])

// const goToWatchlist = () => {
//     emit('watchlists', true)
// }
const loginUser = async () => {
    if (!username.value || !password.value) {
        toast.error('Please fill in both email and password')
        return
    }
    try {
        const response = await axios.post('http://localhost:9087/login', {
            email: username.value,
            password: password.value
        })
        if (response.data.message === 'Login successful') {
            toast.success('Login successful!')
            localStorage.setItem('user', JSON.stringify(response.data.user))
            open.value = false
            location.reload()
            location.reload()

        }
        else {
            toast.error('Login failed. Invalid credentials')
        }
    } catch (error) {
        toast.error(error.response?.data || 'Login failed. Invalid credentials')
    }
}
// function goHome(){
//     location.reload();
// }



const hideHeader = ref(false)
let lastScrollTop = 0

const handleScroll = () => {
    const scrollTop = window.scrollY
    hideHeader.value = scrollTop > lastScrollTop && scrollTop > 80
    lastScrollTop = scrollTop <= 0 ? 0 : scrollTop
}

onMounted(() => {
    window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
    window.removeEventListener('scroll', handleScroll)
})
</script>

<template>

    <header :class="['site-header', { 'hide-header': hideHeader }]">
        <div class="container">
            <h1 class="logo">TrendNest</h1>

            <nav class="main-nav">
                <router-link to="/">Home</router-link>

                <router-link to="/about">About</router-link>
                <router-link to="/contact">Contact</router-link>
            </nav>


            <div v-if="loggedInUser" style="display: flex; gap: 0.5rem;">
                <button class="lrbtn" @click="addform = true">Add Product</button>


                <img @click="showProfile = !showProfile" src="../assets/icon-7797704_640.png" alt="Profile"
                    class="profile-pic" />

                <!-- Profile Info Popup -->
                <div v-if="showProfile" class="profile-popup">
                    <p><strong>Name:</strong> {{ loggedInUser.fullName }}</p>
                    <p><strong>Email:</strong> {{ loggedInUser.email }}</p>
                    <p><strong>Phone:</strong> {{ loggedInUser.mobileNumber }}</p>
                    <button class="logout" @click="logout">Logout</button>
                    <button class="icon-btn" title="Watchlist">
                        <router-link to="/watchlist">
                            <BookMarkedIcon class="watch-icon" />
                        </router-link>
                    </button>
                </div>

            </div>
            <div v-else> <button class="lrbtn" @click="open = true">Login / Register</button></div>

        </div>
    </header>

    <Teleport to="body">
        <div v-if="open" class="modal-overlay" @click.self="open = false">
            <div class="modal">
                <div class="modal-header">
                    <button class="close-btn" @click="open = false">×</button>
                </div>

                <div class="tab-buttons">
                    <button :class="['tab', activeForm === 'login' ? 'active' : '']" @click="toggleForm('login')">
                        Login
                    </button>
                    <button :class="['tab', activeForm === 'register' ? 'active' : '']" @click="toggleForm('register')">
                        Register
                    </button>
                </div>

                <div class="form-section">
                    <div v-if="activeForm === 'login'" class="form">
                        <h2>Login</h2>
                        <input type="text" placeholder="username" v-focus v-model="username" />
                        <input type="password" placeholder="password" v-model="password" />
                        <button type="submit" @click.enter="loginUser">Login</button>
                    </div>

                    <div v-else class="form">
                        <h2>Register</h2>
                        <input type="text" placeholder="Full Name" v-focus v-model="name" required />
                        <span style="color:red">{{ errors.name }}</span>
                        <input type="email" placeholder="Email" v-model="email" required />
                        <span style="color:red">{{ errors.email }}</span>
                        <input type="password" placeholder="Password" v-model="password" required />
                        <span style="color:red">{{ errors.password }}</span>
                        <input type="password" placeholder="Confirm Password" v-model="confirmPassword" required />
                        <span style="color:red">{{ errors.confirmPassword }}</span>
                        <input type="tel" name="phone" id="" placeholder="Phone Number" v-model="phone" required>
                        <span style="color:red">{{ errors.phone }}</span>
                        <button type="submit" id="regSubmit" @click="submitForm">Register</button>
                    </div>
                </div>
            </div>
        </div>
    </Teleport>

    <Teleport to="body">
        <div v-if="addform">
            <addProduct></addProduct>
        </div>
    </Teleport>

</template>

<style scoped>
.site-header {
    width: 100%;
    background-color: #222831;
    color: #EEEEEE;
    padding: 1rem 0;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
    position: fixed;
    /* Optional: stick to top */
    z-index: 99;
    top: 0;
    left: 0;

}

.container {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 1rem;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    /* dark semi-transparent */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
    backdrop-filter: blur(2px);
    /* optional blur effect */
}

.logo {
    font-size: 1.9rem;
    /* font-family: "Space Grotesk", sans-serif; */
    font-family: 'Playfair Display', serif;
    font-optical-sizing: auto;
    letter-spacing: 8px;
    font-style: oblique;
    font-weight: bold;
}

.main-nav {
    display: flex;
    gap: 1.5rem;
}

.search-bar {
    display: flex;
    align-items: center;
    background-color: #EEEEEE;
    border-radius: 999px;
    padding: 0rem 0rem;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}

.search-bar input {
    border: none;
    background: transparent;
    /* padding: 0.5rem 0.75rem; */
    flex: 1;
    font-size: 0.9rem;
    outline: none;
    color: #222831;
}

.search-bar button {
    background: none;
    border: none;
    cursor: pointer;
    color: #393E46;
    font-size: 1.4rem;
    padding: 0.3rem;
    border-radius: 25px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.search-bar button:hover {
    background-color: #00acb5c7;
}

.main-nav a {
    color: #EEEEEE;
    letter-spacing: 2px;
    text-decoration: none;
    transition: color 0.3s ease;
}

.main-nav a:hover {
    color: #00ADB5;
}

.lrbtn {
    background: #00ADB5;
    color: #EEEEEE;
    padding: 0.6rem 1.2rem;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 500;
}

.modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #222831;
    color: #EEEEEE;
    width: 90%;
    max-width: 400px;
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.4);
    z-index: 1000;
}

.modal-header {
    display: flex;
    justify-content: flex-end;
}

.close-btn {
    background: transparent;
    color: #EEEEEE;
    font-size: 1.5rem;
    border: none;
    outline: none;
    position: relative;
    top: -25px;
    right: -1.5rem;
    cursor: pointer;

}

.tab-buttons {
    display: flex;
    justify-content: center;
    gap: 1rem;
    margin-top: -3rem;
}

.tab {
    flex: 1;
    max-width: 120px;
    padding: 0.6rem 1rem;
    border: 2px solid transparent;
    background-color: #393E46;
    color: #EEEEEE;
    border-radius: 30px;
    font-size: 0.95rem;
    font-weight: 500;
    outline: none;
    transition: all 0.3s ease;
    cursor: pointer;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
}

.tab:hover {
    background-color: #00ADB5;
    color: #EEEEEE;
    transform: scale(1.05);
}

.tab.active {
    background-color: #00ADB5;
    color: #EEEEEE;
    border-color: #00ADB5;
    box-shadow: 0 4px 12px rgba(0, 173, 181, 0.4);
    transform: scale(1.05);
}



.form-section {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.form {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.form h2 {
    margin-bottom: 1rem;
}

input {
    width: 100%;
    max-width: 250px;
    margin-bottom: 1rem;
    padding: 0.5rem;
    border: 1px solid #00ADB5;
    border-radius: 4px;
    outline: none;
    background: #EEEEEE;
    color: #222831;
}

button[type="submit"] {
    background-color: #00ADB5;
    color: #EEEEEE;
    border: none;
    padding: 0.5rem 1.5rem;
    margin-right: 0.3rem;
    border-radius: 28px;
    cursor: pointer;
}

.btn-2 {
    color: #fff;
    cursor: pointer;
    font-size: 16px;
    font-weight: 400;
    line-height: 45px;
    max-width: 160px;
    width: 100%;
    text-transform: uppercase;
    background: transparent;
    border: none;
    position: relative;
    margin: 1rem auto;
    display: block;
    text-align: center;
    letter-spacing: 0;
    transition: all 0.3s ease;
}

.btn-2:hover,
.btn-2:active {
    letter-spacing: 5px;
}

.btn-2::before,
.btn-2::after {
    content: "";
    position: relative;
    display: block;
    width: 0;
    border: 1px solid transparent;
    transition: all 280ms ease-in-out;
    margin: 0 auto;
}

.btn-2:hover::before,
.btn-2:hover::after {
    border-color: #fff;
    width: 70%;
}

.btn-2::before {
    top: 0;
}

.btn-2::after {
    bottom: 0;
}

.logout {
    background-color: #393E46;
    color: white;
    border: thin solid #00ADB5;
        transition: all 0.3s ease;
}
.logout:hover{
    background-color: #00ADB5;
}
.profile-pic {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    cursor: pointer;
    margin-left: 1rem;
    object-fit: cover;
    border: 2px solid #00ADB5;
}

.profile-popup {
    position: absolute;
    top: 70px;
    /* adjust based on your header height */
    right: 20px;
    background-color: #222831;
    color: #EEEEEE;
    padding: 1rem;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 173, 181, 0.2);
    z-index: 999;
    min-width: 200px;
}

.icon-btn {
    background-color: #393E46;
    border: 1px solid #00ADB5;
    padding: 0.4rem 0.6rem;
    border-radius: 6px;
    cursor: pointer;
    display: flex;
    margin-top: -2.66rem;
    align-items: center;
    justify-content: center;
    height: 41px;
    /* same height as .lrbtn */
    transition: background 0.3s ease;
}

.icon-btn:hover {
    background-color: #00ADB5;
}

.watch-icon {
    width: 18px;
    height: 18px;
    color: #EEEEEE;
}

/* .site-header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    transition: transform 0.4s ease;
    z-index: 1000;
} */

.hide-header {
    transform: translateY(-100%);
}
</style>