package com.imamouse.bookmodule.rx;

public interface Observer<T> {
    Disposable subscribe(Subscriber<T> subscriber);
}