package com.itk.bank;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private final long id;
    private final AtomicLong balance;
    private final ReentrantLock lock = new ReentrantLock();

    BankAccount(long id, long initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("initial balance must be non-negative");
        }
        this.id = id;
        this.balance = new AtomicLong(initialBalance);
    }

    public long getId() {
        return id;
    }

    ReentrantLock getLock() {
        return lock;
    }

    public boolean deposit(long amount) {
        if (amount <= 0) {
            return false;
        }
        balance.addAndGet(amount);
        return true;
    }

    public boolean withdraw(long amount) {
        if (amount <= 0) {
            return false;
        }
        long current;
        do {
            current = balance.get();
            if (current < amount) {
                return false;
            }
        } while (!balance.compareAndSet(current, current - amount));
        return true;
    }

    public long getBalance() {
        return balance.get();
    }
}
