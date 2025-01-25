/* eslint-env node */
require("@rushstack/eslint-patch/modern-module-resolution");

module.exports = {
  extends: [
    "eslint:recommended",
    "plugin:vue/vue3-recommended",
    "@electron-toolkit",
    "@electron-toolkit/eslint-config-ts/eslint-recommended",
    "@vue/eslint-config-typescript/recommended",
    "@vue/eslint-config-prettier"
  ],
  rules: {
    "vue/require-default-prop": "off",
    "vue/multi-word-component-names": "off",
    //----自定义规则----
    quotes: ["error", "double"], // 使用双引号
    semi: ["error", "always"], // 使用分号
    "no-unused-vars": "off",
    "@typescript-eslint/no-unused-vars": "off",
    "@typescript-eslint/no-explicit-any": "off"
  }
};
