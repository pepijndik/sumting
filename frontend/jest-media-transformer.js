module.exports = {
    process(src, sourcePath, _options) {
        return `module.exports = ${JSON.stringify(sourcePath)};`
    }
};


