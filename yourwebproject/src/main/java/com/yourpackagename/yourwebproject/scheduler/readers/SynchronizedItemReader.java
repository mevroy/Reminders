package com.yourpackagename.yourwebproject.scheduler.readers;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public final class SynchronizedItemReader<T> implements ItemStream, ItemReader<T> {

    private final ItemReader<T> delegate;

    public SynchronizedItemReader(ItemReader<T> delegate) {
        this.delegate = delegate;
    }

    
    public synchronized T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return delegate.read();
    }

    
    public synchronized void open(ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).open(executionContext);
        }
    }

    
    public synchronized void update(ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).update(executionContext);
        }
    }

    
    public synchronized void close() throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).close();
        }
    }
}