package com.itk.bank;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentBankDemo {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount[] accounts = new BankAccount[10];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = bank.createAccount(1000);
        }

        long initialTotal = bank.getTotalBalance();

        int threads = 8;
        int transfersPerThread = 10_000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        AtomicInteger successfulTransfers = new AtomicInteger();

        for (int t = 0; t < threads; t++) {
            pool.submit(() -> {
                ThreadLocalRandom rnd = ThreadLocalRandom.current();
                for (int i = 0; i < transfersPerThread; i++) {
                    BankAccount from = accounts[rnd.nextInt(accounts.length)];
                    BankAccount to = accounts[rnd.nextInt(accounts.length)];
                    long amount = rnd.nextLong(1, 50);
                    if (bank.transfer(from, to, amount)) {
                        successfulTransfers.incrementAndGet();
                    }
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        long finalTotal = bank.getTotalBalance();
        System.out.println("successful transfers: " + successfulTransfers.get());
        System.out.println("initial total: " + initialTotal);
        System.out.println("final total:   " + finalTotal);
        System.out.println("money conserved: " + (initialTotal == finalTotal));
    }
}
