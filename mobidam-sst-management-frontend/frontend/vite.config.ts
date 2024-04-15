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

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue2";
import legacy from "@vitejs/plugin-legacy";
import { fileURLToPath, URL } from "url";
import { VuetifyResolver } from 'unplugin-vue-components/resolvers';
import Components from 'unplugin-vue-components/vite';
import AutoImport from 'unplugin-auto-import/vite';
import eslint from 'vite-plugin-eslint';

export default defineConfig({
    plugins: [
        vue(),
        eslint({
            fix: true
        }),
        Components({
            resolvers: [
                VuetifyResolver(), // Fängt alle Komponenten ab, die mit `V` beginnen, was zu Problemen mit anderen Dependencies führen kann
            ],
        }),
        AutoImport({
            imports: [
                'vue',
                'vitest',
            ],
            ignore: ['createApp', 'resolveComponent', 'chai']
        }),
        legacy({
            targets: ["defaults", "not IE 11"]
        })
    ],
    server: {
        port: 8081
    },
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    build: {
        //Wenn CCS Split Probleme macht
        //cssCodeSplit: false
    }
});
