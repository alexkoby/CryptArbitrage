# CryptArbitrage
MIT License

Copyright (c) [2018] [Alexander Koby]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.



CryptArbitrage finds the best arbitrage opprotunities across various crypto-currency exchanges. 

When you open CryptArbitrage, it will first check an external file to see if you have any preferences saved. If not, you may select your exchanges and cryptocurrencies by selecting the buttons on your home screen. After your selection, these preferences will be saved in an external file on your device. Now, you can click the View Arbitrage Opportunities button, which will start the data retrieval process. If the external server is running, it will retrieve data from it, else it will make API requests to the exchanges themselves. Afterwards, it will do some analytics from the ArbitrageFinder class, before going to the ViewCryptoOpportunities class, where the data is displayed.

Note the APIs it uses are always being changed, and I will try to make modifications as soon as it comes to my attention. 
