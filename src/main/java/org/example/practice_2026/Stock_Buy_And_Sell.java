
/*
[Expected Approach] Accumulate Profit - O(n) Time and O(1) Space
    Instead of selling at local maxima, we keep selling while the prices are going up. This way we accumulate the same profit and avoid some condition checks required for computing local minima and maxima.

    Traverse price[] from i = 1 to price.size() - 1

        * res = 0
        * if price[i] > price[i - 1]
            res = res + price[i] - price[i - 1]
*/

public class Stock_Buy_And_Sell {
    static int maximumProfit(int[] prices) {
        int res = 0;

        // Keep on adding the difference between
        // adjacent when the prices a
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                res += prices[i] - prices[i - 1];
        }

        return res;
    }

    public static void main(String[] args) {
        int[] prices = { 100, 180, 260, 310, 40, 535, 695 };
        System.out.println(maximumProfit(prices));
    }
}
