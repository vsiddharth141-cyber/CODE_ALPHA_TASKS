import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    String ticker;
    String name;
    double price;

    public Stock(String ticker, String name, double price) {
        this.ticker = ticker;
        this.name = name;
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private double balance;

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() { return balance; }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (balance >= cost) {
            balance -= cost;
            holdings.put(stock.ticker, holdings.getOrDefault(stock.ticker, 0) + quantity);
            System.out.printf("Successfully bought %d shares of %s.\n", quantity, stock.ticker);
        } else {
            System.out.println("Error: Insufficient funds.");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        int currentShares = holdings.getOrDefault(stock.ticker, 0);
        if (currentShares >= quantity) {
            balance += stock.price * quantity;
            if (currentShares == quantity) {
                holdings.remove(stock.ticker);
            } else {
                holdings.put(stock.ticker, currentShares - quantity);
            }
            System.out.printf("Successfully sold %d shares of %s.\n", quantity, stock.ticker);
        } else {
            System.out.println("Error: Not enough shares to sell.");
        }
    }

    public void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n=== Your Portfolio ===");
        System.out.printf("Cash Balance: $%.2f\n", balance);
        if (holdings.isEmpty()) {
            System.out.println("No stock holdings.");
            return;
        }
        double totalValue = balance;
        System.out.printf("%-10s %-10s %-15s %-10s\n", "Ticker", "Shares", "Current Price", "Total Value");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            Stock stock = market.get(entry.getKey());
            double currentVal = entry.getValue() * stock.price;
            totalValue += currentVal;
            System.out.printf("%-10s %-10d $%-14.2f $%-10.2f\n", entry.getKey(), entry.getValue(), stock.price, currentVal);
        }
        System.out.printf("Total Portfolio Value: $%.2f\n", totalValue);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 175.50));
        market.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", 150.25));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 180.10));
        market.put("AMZN", new Stock("AMZN", "Amazon.com Inc.", 178.00));

        Portfolio portfolio = new Portfolio(10000.00);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Stock Trading Menu ---");
            System.out.println("1. View Market Prices");
            System.out.println("2. View Portfolio");
            System.out.println("3. Buy Stock");
            System.out.println("4. Sell Stock");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("\n=== Live Market Prices ===");
                    System.out.printf("%-10s %-20s %-10s\n", "Ticker", "Company Name", "Price");
                    for (Stock s : market.values()) {
                        System.out.printf("%-10s %-20s $%-10.2f\n", s.ticker, s.name, s.price);
                    }
                    break;
                case "2":
                    portfolio.displayPortfolio(market);
                    break;
                case "3":
                    System.out.print("Enter stock ticker to buy: ");
                    String buyTicker = scanner.nextLine().toUpperCase();
                    if (!market.containsKey(buyTicker)) {
                        System.out.println("Invalid ticker symbol.");
                        break;
                    }
                    System.out.print("Enter quantity to buy: ");
                    try {
                        int qty = Integer.parseInt(scanner.nextLine());
                        if (qty <= 0) throw new NumberFormatException();
                        portfolio.buyStock(market.get(buyTicker), qty);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity.");
                    }
                    break;
                case "4":
                    System.out.print("Enter stock ticker to sell: ");
                    String sellTicker = scanner.nextLine().toUpperCase();
                    if (!market.containsKey(sellTicker)) {
                        System.out.println("Invalid ticker symbol.");
                        break;
                    }
                    System.out.print("Enter quantity to sell: ");
                    try {
                        int qty = Integer.parseInt(scanner.nextLine());
                        if (qty <= 0) throw new NumberFormatException();
                        portfolio.sellStock(market.get(sellTicker), qty);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity.");
                    }
                    break;
                case "5":
                    System.out.println("Exiting trading simulator. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
