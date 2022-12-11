const defaultTheme = require('tailwindcss/defaultTheme');
module.exports = {
  purge: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  plugins: [require("@tailwindcss/forms"), require("tailwind-scrollbar"),require('@tailwindcss/typography')],
  darkMode: "class", // or 'media' or 'class',
  theme: {
    extend: {
      colors: {
        white: "#FFFFFF",
        champagnePink: "#FDEAE2",
        yInMnBlue: "#355070",
        candyPink: "#E56B6F",
        shadow: "#555555",
      },
      fontFamily: {
        Alatsi: ['"Alatsi"', "sans-serif"],
        inter: ['"inter"', "sans-serif"],
        sans: ['Nunito', ...defaultTheme.fontFamily.sans],
      },
      boxShadow: {
        r: "-2px 2px 2px 0 rgb(0 0 0 / 0.15)",
        inner: "inset 2px 2px 2px 0 rgb(0 0 0 / 0.15)",
        "t-inner": "inset 0 2px 2px 0 rgb(0 0 0 / 0.15)",
      },
      rotate: {
        '270': '270deg',
      },
    },
  },
  variants: {},
};
