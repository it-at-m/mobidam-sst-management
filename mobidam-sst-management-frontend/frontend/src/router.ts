import Vue from "vue";
import Router from "vue-router";
import Main from "./views/MainView.vue";
import { ROUTER_BASE } from "@/Constants";
import SchnittstelleDetail from "@/views/SchnittstelleDetail.vue";

Vue.use(Router);

/*
 * Preventing "NavigationDuplicated" errors in console in Vue-router >= 3.1.0
 * https://github.com/vuejs/vue-router/issues/2881#issuecomment-520554378
 * */
/* eslint-disable @typescript-eslint/no-explicit-any */
const routerMethods = ["push", "replace"];
routerMethods.forEach((method: string) => {
    const originalCall = (Router.prototype as any)[method];
    (Router.prototype as any)[method] = function (
        location: any,
        onResolve: any,
        onReject: any
    ): Promise<any> {
        if (onResolve || onReject) {
            return originalCall.call(this, location, onResolve, onReject);
        }
        return originalCall.call(this, location).catch((err: any) => err);
    };
});
/* eslint-enable @typescript-eslint/no-explicit-any */

export default new Router({
    base: ROUTER_BASE,
    routes: [
        {
            path: "/",
            name: "home",
            component: Main,
            meta: {},
        },
        {
            path: "/schnittstelleDetailView/:id",
            name: "schnittstelleDetail",
            component: SchnittstelleDetail,
        },
        { path: "*", redirect: "/" }, //Fallback 2
    ],
});
