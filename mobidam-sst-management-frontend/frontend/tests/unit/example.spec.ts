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

import { shallowMount } from "@vue/test-utils";
import { createPinia } from "pinia";
import { beforeAll, beforeEach, describe, expect, it } from "vitest";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

import TheSnackbar from "@/components/TheSnackbar.vue";

const pinia = createPinia();

describe("TheSnackbar.vue", () => {
    let vuetify: ReturnType<typeof createVuetify>;

    beforeAll(() => {
        createPinia();
        createVuetify();
    });

    beforeEach(() => {
        vuetify = createVuetify({
            components,
            directives,
        });
    });

    it("renders props.message when passed", () => {
        const message = "Hello_World";
        const wrapper = shallowMount(TheSnackbar, {
            global: {
                plugins: [pinia, vuetify],
            },
            props: { message: message },
        });

        expect(wrapper.html()).toContain(message);
    });
});
