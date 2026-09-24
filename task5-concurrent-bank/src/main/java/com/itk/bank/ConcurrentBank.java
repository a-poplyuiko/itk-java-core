package com.itk.bank;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentBank {

    private static final long LOCK_TIMEOUT_MS = 1000;

    private final Map<Long, BankAccount> accounts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BankAccount createAccount(long initialBalance) {
        long id = idGenerator.getAndIncrement();
        BankAccount account = new BankAccount(id, initialBalance);
        accounts.put(id, account);
        return account;
    }

    public boolean transfer(BankAccount from, BankAccount to, long amount) {
        if (from == null || to == null || from == to || amount <= 0) {
            return false;
        }
        if (!accounts.containsKey(from.getId()) || !accounts.containsKey(to.getId())) {
            return false;
        }

        BankAccount first = from.getId() < to.getId() ? from : to;
        BankAccount second = from.getId() < to.getId() ? to : from;

        ReentrantLock firstLock = first.getLock();
        ReentrantLock secondLock = second.getLock();

        boolean firstAcquired = false;
        boolean secondAcquired = false;
        try {
            firstAcquired = firstLock.tryLock(LOCK_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (!firstAcquired) {
                return false;
            }
            secondAcquired = secondLock.tryLock(LOCK_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (!secondAcquired) {
                return false;
            }

            if (!from.withdraw(amount)) {
                return false;
            }
            to.deposit(amount);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (secondAcquired) {
                secondLock.unlock();
            }
            if (firstAcquired) {
                firstLock.unlock();
            }
        }
    }

    public long getTotalBalance() {
        LongAdder total = new LongAdder();
        accounts.values().forEach(account -> total.add(account.getBalance()));
        return total.sum();
    }

    public int getAccountCount() {
        return accounts.size();
    }
}
