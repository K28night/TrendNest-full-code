<script setup>

import { ref } from 'vue'
import { useForm, useField } from 'vee-validate'
import * as yup from 'yup'
import axios from 'axios'
import { useToast } from 'vue-toastification'
import { UploadCloud } from 'lucide-vue-next'

const toast = useToast()
const open = ref(true)
const imageFile = ref(null)
const imageError = ref('')
const imagePreview = ref('')

const productSchema = yup.object({
  name: yup.string().required('Product name is required'),
  price: yup
    .number()
    .typeError('Price must be a number')
    .required('Price is required')
    .positive('Price must be positive'),
  oldPrice: yup
    .number()
    .typeError('Old price must be a number')
    .nullable()
    .transform((value, originalValue) => originalValue === '' ? null : value),
  description: yup.string().required('Description is required')
}).test(
  'price-less-than-oldPrice',
  'Price must be less than Old Price',
  function (values) {
    const { price, oldPrice } = values
    if (oldPrice == null || oldPrice === '') return true
    return parseFloat(price) < parseFloat(oldPrice)
  }
)

const { handleSubmit, errors,resetForm } = useForm({
  validationSchema: productSchema,
  validateOnInput: true
})

const { value: name } = useField('name')
const { value: price } = useField('price')
const { value: oldPrice } = useField('oldPrice')
const { value: description } = useField('description')

const loggedInUser = JSON.parse(localStorage.getItem('user')) || null

const handleFileChange = (e) => {
  const file = e.target.files[0]
  imageError.value = ''
  if (!file) {
    imageFile.value = null
    imagePreview.value = ''
    return
  }

  const validTypes = ['image/png', 'image/jpeg', 'image/jpg']
  const maxSize = 2 * 1024 * 1024 // 2MB

  if (!validTypes.includes(file.type)) {
    imageError.value = 'Only PNG, JPG, and JPEG formats are allowed'
    return
  }

  if (file.size > maxSize) {
    imageError.value = 'Image size must be less than 2MB'
    return
  }

  imageFile.value = file
  imagePreview.value = URL.createObjectURL(file)
}

const submitProduct = handleSubmit(async (values) => {
  imageError.value = ''

   if (!imageFile.value) {
      imageError.value = 'Please upload an image';
      toast.error('Image is required!');
      return;
    }

  try {
    const formData = new FormData()
    formData.append('name', values.name)
    formData.append('price', values.price)
    formData.append('oldPrice', values.oldPrice || '')
    formData.append('description', values.description)
    formData.append('image', imageFile.value)
    formData.append('userId', loggedInUser.id)

    await axios.post('http://localhost:9087/addProduct', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    toast.success('Product added successfully!')
    resetForm();
    location.reload();
    // Reset form if needed
  } catch (error) {
    toast.error(error.response?.data || 'Failed to add product')
  }
})
</script>


<template>
    <Teleport to="body">
        <div v-if="open" class="modal-overlay" @click.self="open = false">
            <div class="modal">
                <div class="modal-header">
                    <button class="close-btn" @click="open = false">×</button>
                </div>
                <div class="form">
                    <h2>Add Product</h2>
                    <input type="text" placeholder="Product Name" v-model="name" :class="{ error: errors.name }" />
                    <span class="error-text">{{ errors.name }}</span>

                    <input type="text" placeholder="Price" v-model="price" :class="{ error: errors.price }" />
                    <span class="error-text">{{ errors.price }}</span>

                    <input type="text" placeholder="Old Price" v-model="oldPrice" />

                    <textarea placeholder="Description" v-model="description"
                        :class="{ error: errors.description }"></textarea>
                    <span class="error-text">{{ errors.description }}</span>

                  <div class="upload-field">
  <UploadCloud class="upload-icon" />
<input type="file" accept="image/*" @change="handleFileChange" />
<img v-if="imagePreview" :src="imagePreview" alt="Preview" class="preview-img" />


</div>

<span class="error-text">{{ imageError }}</span>


                    <button type="submit" @click="submitProduct">Add Product</button>
                </div>
            </div>
        </div>
    </Teleport>
</template>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
}
.file-name {
  color: #eeeeee;
  font-size: 0.8rem;
  margin-top: -0.5rem;
}

.modal {
    background-color: #222831;
    color: #eee;
    width: 90%;
    max-width: 400px;
    padding: 2rem;
    border-radius: 12px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.4);
    position: relative;
}

.modal-header {
    display: flex;
    justify-content: flex-end;
}

.close-btn {
    background: transparent;
    color: #eeeeee;
    font-size: 1.5rem;
    border: none;
    cursor: pointer;
}

.form {
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
    align-items: center;
}
.preview-img {
  width: 100px;
  height: auto;
  margin-top: 0.5rem;
  border-radius: 6px;
  object-fit: cover;
  border: 1px solid #00adb5;
}

.form h2 {
    margin-bottom: 1rem;
}

input,
textarea {
    width: 100%;
    max-width: 250px;
    padding: 0.5rem;
    border: 1px solid #00adb5;
    border-radius: 4px;
    outline: none;
    background: #eeeeee;
    color: #222831;
}

.error {
    border-color: red;
}

.error-text {
    color: red;
    font-size: 0.8rem;
    margin-top: -0.5rem;
    margin-bottom: 0.5rem;
}

.upload-field {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.upload-icon {
    color: #00adb5;
    width: 24px;
    height: 24px;
}

button[type="submit"] {
    background-color: #00adb5;
    color: #eeeeee;
    border: none;
    padding: 0.5rem 1.5rem;
    border-radius: 28px;
    cursor: pointer;
    margin-top: 1rem;
}
</style>
