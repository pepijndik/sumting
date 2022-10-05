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
            }
        },
    },
    variants: {

    }
};