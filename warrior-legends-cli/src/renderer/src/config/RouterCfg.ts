import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    component: () => import("../pages/LoginPage.vue")
  },
  {
    path: "/home",
    component: () => import("../pages/Home.vue")
  }
];
export const router = createRouter({
  history: createWebHashHistory(),
  routes
});
