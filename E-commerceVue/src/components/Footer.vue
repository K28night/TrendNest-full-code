<script setup>
import { RouterView } from 'vue-router'

</script>

<template>
    <footer class="footer">
        <div class="footer-container">
            <div class="footer-column">
                <h3>About Us</h3>
                <ul>
                    <li> <router-link to="/about">About</router-link></li>
                    <li> <router-link to="/contact">Contact</router-link></li>
                    <li><a href="#">FAQ</a></li>
                </ul>
                <div class="collab">
                    <h3>Our Collaborations</h3>
                    <div class="brands">
                        <img class="zpimage zpimage-style-none zpimage-space-none "
                            src="https://us.zohocommercecdn.com/stock_images/fashion-zs200009/zcstock-images-13.svg?storefront_domain=twice-demo.zohoecommerce.com"
                            width="46" height="16" loading="lazy" size="original">
                        <img class="zpimage zpimage-style-none zpimage-space-none "
                            src="https://us.zohocommercecdn.com/stock_images/fashion-zs200009/zcstock-images-14.svg?storefront_domain=twice-demo.zohoecommerce.com"
                            width="31" height="30" loading="lazy" size="original">
                        <img class="zpimage zpimage-style-none zpimage-space-none "
                            src="https://us.zohocommercecdn.com/stock_images/fashion-zs200009/zcstock-images-15.svg?storefront_domain=twice-demo.zohoecommerce.com"
                            width="57" height="28" loading="lazy" size="original">

                    </div>
                </div>
            </div>

            <div class="footer-column">
                <h3>Categories</h3>
                <ul>
                    <li><a href="#">T-shirts</a></li>
                    <li><a href="#">Vests</a></li>
                    <li><a href="#">Pants</a></li>
                    <li><a href="#">Jacket</a></li>
                </ul>
            </div>

            <div class="footer-column">
                <h3>Customer Support</h3>
                <p>support@trendnest.com</p>
                <p>+91 9876543210</p>

                <h3>Subscribe to Us</h3>
                <p class="sub-text">
                    Sign up using email to get special offers, better discounts,<br />
                    exclusive access, and much more.
                </p>
                <!-- <form class="subscribe-form" @submit.prevent>
                   <input type="email" placeholder="Email" />
                    <button type="submit">Subscribe</button>
                </form> -->
                
            </div>
        </div>

        <div class="footer-bottom">
            <p>COUNTRY: <strong>INDIA</strong></p>
            <p>&copy; 2025 TrendNest. All rights reserved.</p>
        </div>
    </footer>
</template>

<style scoped>
.footer {
    background-color: #222831;
    color: #EEEEEE;
    padding: 3rem 1rem 1rem;

    /* position: fixed;
    bottom: 0;
    left: 0; */
      /* height: 100%;
  width: 100%; */
  font-family: system-ui, Avenir, Helvetica, Arial, sans-serif;
  letter-spacing:1px
}

.footer-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 2rem;
    max-width: 1200px;

}

.footer-column {
    flex: 1;
    min-width: 220px;
}

.footer-column h3 {
    color: #00ADB5;
           font-family: 'Playfair Display', serif;
         /* font-family: "Space Grotesk", sans-serif; */
    font-optical-sizing: auto;
    letter-spacing: 3px;
    margin-bottom: 1rem;
}

.footer-column ul {
    list-style: none;
    padding: 0;
}

.footer-column li {
    
    margin-bottom: 0.5rem;
}

.footer-column a {
    color: #EEEEEE;
    text-decoration: none;
}

.footer-column a:hover {
   
    color: #00ADB5;
}

.collab {
    margin-top: 1rem;
}

.brands {
    display: flex;
    gap: 10px;
    margin-left: 7rem;
    margin-top: 0.5rem;
}

.brands img {
    width: 40px;
        background: none;
    margin-right: 1rem;
    height: auto;
    filter: brightness(0) invert(1);
}

.sub-text {
    font-size: 0.9rem;
    margin: 0.5rem 0;
}

.subscribe-form {
    display: flex;
    gap: 0.5rem;
    margin-top: 0.5rem;
}

.subscribe-form input {
    padding: 0.5rem;
    border: none;
    border-radius: 4px;
    outline: none;
    flex: 1;
    background-color: #EEEEEE;
    color: #222831;
}

.subscribe-form button {
    padding: 0.5rem 1rem;
    background-color: #00ADB5;
    border: none;
    color: #EEEEEE;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
}

.footer-bottom {
    text-align: center;
    margin-top: 2rem;
    border-top: 1px solid #393E46;
    padding-top: 1rem;
    font-size: 0.9rem;
    color: #BBBBBB;
}
</style>
