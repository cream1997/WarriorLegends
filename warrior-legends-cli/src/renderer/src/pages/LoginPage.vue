<script setup lang="ts">
import { ref } from "vue";
import { post } from "@/net/AxiosCfg";
import { useRouter } from "vue-router";

const router = useRouter();

const username = ref("test");
const password = ref("test");

const register = (event) => {
  event.preventDefault();
  post("/account/register", {
    username: username.value,
    password: password.value
  }).then((res) => {});
};

const login = (event) => {
  event.preventDefault();
  post("/account/login", {
    username: username.value,
    password: password.value
  }).then((res: any) => {
    // 带着token去建立ws连接
    window.electron.ipcRenderer
      .invoke("wsConnect", { token: res.token, id: res.id })
      .then((res) => {
        if (res === "success") {
          console.log("ws连接成功");
          // 跳转页面
          router.push("/home");
        } else {
          console.log("ws连接失败");
        }
      })
      .catch((err) => {
        console.error(err);
      });
  });
};
</script>

<template>
  <form>
    <div>
      <label for="username">用户名</label>
      <input id="username" v-model="username" type="text" name="username" />
    </div>
    <div>
      <label for="password">密码</label>
      <input id="password" v-model="password" type="password" name="password" />
    </div>

    <button @click="login">登录</button>
    <button @click="register">注册</button>
  </form>
</template>

<style scoped></style>
