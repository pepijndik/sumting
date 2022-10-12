module.exports = {
    purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
    darkMode: "class", // or 'media' or 'class',
    theme:{
        extend: {
            colors: {
                "white": "#FFFFFF",
                "champagnePink": "#FDEAE2",
                "yInMnBlue": "#355070",
                "candyPink": "#E56B6F",
                "shadow": "#555555"
            },
            fontFamily: {
                'Alatsi': ['"Alatsi"', 'sans-serif'],
                'inter': ['"inter"', 'sans-serif'],
            },
            boxShadow: {
                'inner': 'inset 2px 2px 2px 0 rgb(0 0 0 / 0.15)',
            }
        },
    },
    variants: {

    }
};