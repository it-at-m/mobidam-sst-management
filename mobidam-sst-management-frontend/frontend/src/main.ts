///
/// The MIT License
/// Copyright © 2023 Landeshauptstadt München | it@M
///
/// Permission is hereby granted, free of charge, to any person obtaining a copy
/// of this software and associated documentation files (the "Software"), to deal
/// in the Software without restriction, including without limitation the rights
/// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
/// copies of the Software, and to permit persons to whom the Software is
/// furnished to do so, subject to the following conditions:
///
/// The above copyright notice and this permission notice shall be included in
/// all copies or substantial portions of the Software.
///
/// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
/// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
/// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
/// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
/// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
/// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
/// THE SOFTWARE.
///

import Vue, { VNode } from "vue";
import Vuetify from "./plugins/vuetify";
import App from "./App.vue";
import router from "./router";
import moment from "moment";
import { createPinia, PiniaVuePlugin } from "pinia";

Vue.config.productionTip = false;
Vue.use(PiniaVuePlugin);

const pinia = createPinia();

moment.locale(window.navigator.language);

new Vue({
    router,
    pinia,
    vuetify: Vuetify,
    render: (h): VNode => h(App),
}).$mount("#app");
