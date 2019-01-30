package com.imamouse.bookmodule.engine.parser;

import com.imamouse.bookmodule.bean.Book;
import com.imamouse.bookmodule.bean.Catalog;
import com.imamouse.bookmodule.engine.Platform;
import com.imamouse.bookmodule.engine.Site;
import com.imamouse.bookmodule.net.NetUtil;
import com.imamouse.bookmodule.rx.Disposable;
import com.imamouse.bookmodule.rx.Observer;
import com.imamouse.bookmodule.rx.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zia on 2018/11/15.
 */
public class ContentObserver implements Observer<List<String>>, Disposable {

    private Book book;
    private Catalog catalog;

    private Platform platform = Platform.get();
    volatile private boolean attachView = true;
    private ExecutorService service = Executors.newCachedThreadPool();

    public ContentObserver(Book book, Catalog catalog) {
        this.book = book;
        this.catalog = catalog;
    }

    @Override
    public void dispose() {
        attachView = false;
    }

    @Override
    public Disposable subscribe(final Subscriber<List<String>> subscriber) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Site site = book.getSite();
                    String html = NetUtil.getHtml(catalog.getUrl(), site.getEncodeType());
                    final List<String> list = site.parseContent(html);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onFinish(list);
                        }
                    });
                } catch (final Exception e) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onError(e);
                        }
                    });
                }
            }
        });
        return this;
    }

    private void post(Runnable runnable) {
        service.shutdownNow();
        if (attachView) {
            platform.defaultCallbackExecutor().execute(runnable);
        }
    }
}
