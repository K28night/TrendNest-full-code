<template>
  <div class="contact-container">
    <h2>Contact Us</h2>

    <!-- Show form before submission -->
<form v-if="!submitted" @submit.prevent="submitContact">


      <input type="text" v-model="name" placeholder="Your Name" />
      <span class="error">{{ errors.name }}</span>

      <input type="email" v-model="email" placeholder="Your Email" />
      <span class="error">{{ errors.email }}</span>

      <textarea v-model="message" placeholder="Your Message" rows="5"></textarea>
      <span class="error">{{ errors.message }}</span>

      <button type="submit">Send Message</button>
    </form>

    <!-- Show confirmation tick after submission -->
    <div v-else class="confirmation-message">
      <span class="tick">  <CheckCircleIcon class="tick" width="82" height="78"/></span>
      <p>Your request has been submitted!</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useForm, useField } from 'vee-validate'
import * as yup from 'yup'
import { useToast } from 'vue-toastification'
import { CheckCircleIcon } from 'lucide-vue-next'


const toast = useToast()
const submitted = ref(false)

// 1️⃣ Define validation schema
const contactSchema = yup.object({
  name: yup.string().required('Name is required').matches(/^[A-Za-z ]+$/, 'Only alphabets and spaces allowed'),
  email: yup.string().required('Email is required').email('Invalid email format'),
  message: yup.string().required('Message is required').min(10, 'Message must be at least 10 characters')
})

// 2️⃣ Setup form
const { handleSubmit, errors } = useForm({
  validationSchema: contactSchema,
  validateOnInput: true
})

// 3️⃣ Define fields with EXACT names from schema
const { value: name } = useField('name')
const { value: email } = useField('email')
const { value: message } = useField('message')

// 4️⃣ Submission logic
const submitContact = handleSubmit((values) => {
  toast.success('Message sent successfully!')
  submitted.value = true

  // Reset values
  name.value = ''
  email.value = ''
  message.value = ''

  // setTimeout(() => {
  //   submitted.value = false
  // }, 3000)
})

</script>



<style scoped>
.contact-container {
  max-width: 800px;
  margin: 140px auto 50px;
  padding: 2rem;
  background-color: #222831;
  color: #eeeeee;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 173, 181, 0.15);
}
.contact-container h2 {
  font-size: 2rem;
  color: #00adb5;
  margin-bottom: 1rem;
  text-align: center;
}
form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
input,
textarea {
  padding: 0.75rem;
  border-radius: 8px;
  border: none;
  font-size: 1rem;
  background: #393e46;
  color: #eeeeee;
}
button {
  padding: 0.8rem;
  background-color: #00adb5;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
button:hover {
  background-color: #028e95;
}
.error {
  color: #ff4d6d;
  font-size: 0.85rem;
  margin-top: -0.5rem;
  margin-bottom: 0.5rem;
}
.confirmation-message {
  text-align: center;
  padding: 2rem 0;
  font-size: 1.2rem;
  color: #00ffae;
  font-weight: bold;
}
.tick {

  display: flex;
  justify-content:center;
  margin-bottom: 1rem;
}
</style>
