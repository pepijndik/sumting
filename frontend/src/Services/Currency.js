export default class Currency {
    list = [];

    constructor() {
        this.list = [
            {name: 'Euro', code: 'EUR', symbol: '€'},
            {name: 'Dollar', code: 'USD', symbol: '$'},
            {name: 'Pound', code: 'GBP', symbol: '£'},
            {name: 'Yen', code: 'JPY', symbol: '¥'},
            {name: 'Yuan', code: 'CNY', symbol: '¥'},
            {name: 'Won', code: 'KRW', symbol: '₩'},
            {name: 'Ruble', code: 'RUB', symbol: '₽'},
            {name: 'Real', code: 'BRL', symbol: 'R$'},
            {name: 'Rupee', code: 'INR', symbol: '₹'},
            {name: 'Rand', code: 'ZAR', symbol: 'R'},
            {name: 'Krona', code: 'SEK', symbol: 'kr'},
            {name: 'Krona', code: 'DKK', symbol: 'kr'},
            {name: 'Krona', code: 'NOK', symbol: 'kr'},
            {name: 'Krona', code: 'ISK', symbol: 'kr'},
            {name: 'Krona', code: 'CHF', symbol: 'CHF'},
            {name: 'Krona', code: 'AUD', symbol: 'A$'},
            {name: 'Krona', code: 'CAD', symbol: 'C$'},
            {name: 'Krona', code: 'NZD', symbol: 'NZ$'},
            {name: 'Krona', code: 'MXN', symbol: 'MX$'},
            {name: 'Krona', code: 'SGD', symbol: 'S$'},
            {name: 'Krona', code: 'HKD', symbol: 'HK$'},
            {name: 'Krona', code: 'TWD', symbol: 'NT$'},
            {name: 'Krona', code: 'THB', symbol: '฿'},
            {name: 'Krona', code: 'PHP', symbol: '₱'},
            {name: 'Krona', code: 'IDR', symbol: 'Rp'},
            {name: 'Krona', code: 'MYR', symbol: 'RM'},
            {name: 'Krona', code: 'CZK', symbol: 'Kč'},
            {name: 'Krona', code: 'HUF', symbol: 'Ft'},
            {name: 'Krona', code: 'PLN', symbol: 'zł'},
            {name: 'Krona', code: 'BGN', symbol: 'лв'},
            {name: 'Krona', code: 'RON', symbol: 'lei'},
            {name: 'Krona', code: 'TRY', symbol: '₺'},
            {name: 'Krona', code: 'HRK', symbol: 'kn'},
            {name: 'Krona', code: 'ILS', symbol: '₪'},
            {name: 'Krona', code: 'CLP', symbol: 'CLP$'},
            {name: 'Krona', code: 'COP', symbol: 'COP$'},
            {name: 'Krona', code: 'PEN', symbol: 'S/.'},
            {name: 'Krona', code: 'ARS', symbol: 'AR$'},
            {name: 'Krona', code: 'BOB', symbol: 'Bs'},
            {name: 'Krona', code: 'UYU', symbol: '$U'},
            {name: 'Krona', code: 'VND', symbol: '₫'},
            {name: 'Krona', code: 'EGP', symbol: 'E£'},
            {name: 'Krona', code: 'KWD', symbol: 'K.D.'},
            {name: 'Krona', code: 'SAR', symbol: 'SR'},
            {name: 'Krona', code: 'AED', symbol: 'AED'},
            {name: 'Krona', code: 'BHD', symbol: 'BD'},
            {name: 'Krona', code: 'OMR', symbol: 'OMR'},
            {name: 'Krona', code: 'QAR', symbol: 'QR'},
            {name: 'Krona', code: 'JOD', symbol: 'JD'},

        ];
    }

    getCurrencyByCode(code) {
        return this.list.find((currency) => currency.code === code);
    }

    getCurrencyBySymbol(symbol) {
        return this.list.find((currency) => currency.symbol === symbol);
    }

    getCurrencyByName(name) {
        return this.list.find((currency) => currency.name === name);
    }

    getCurrencyList() {
        return this.list;
    }
}