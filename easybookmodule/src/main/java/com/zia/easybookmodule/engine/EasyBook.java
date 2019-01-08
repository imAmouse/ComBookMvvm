package com.zia.easybookmodule.engine;

import com.zia.easybookmodule.bean.Book;
import com.zia.easybookmodule.bean.Catalog;
import com.zia.easybookmodule.engine.parser.CatalogObserver;
import com.zia.easybookmodule.engine.parser.ContentObserver;
import com.zia.easybookmodule.engine.parser.DownloadObserver;
import com.zia.easybookmodule.engine.parser.SearchObserver;
import com.zia.easybookmodule.rx.Observer;

import java.util.List;

/**
 * Created by zia on 2018/11/13.
 * Site包装类
 * search: 并发搜索 {@link SearchObserver}
 * getCatalog: 通过book获取目录 {@link CatalogObserver}
 * getContents: 通过book，catalog获取内容 {@link ContentObserver}
 * download: 下载书籍 {@link DownloadObserver}
 * <p>
 * 使用SiteCollection添加自定义解析站点: {@link SiteCollection#addSite(Site)}
 */
public class EasyBook {

    private EasyBook() {
    }

    public static Observer<List<Book>> search(String bookName) {
        return search(bookName, SiteCollection.getInstance().getAllSites());
    }

    public static Observer<List<Book>> search(final String bookName, final List<Site> sites) {
        return new SearchObserver(bookName, sites);
    }

    public static DownloadObserver download(Book book) {
        return new DownloadObserver(book);
    }

    public static Observer<List<Catalog>> getCatalog(Book book) {
        return new CatalogObserver(book);
    }

    public static Observer<List<String>> getContent(Book book, Catalog catalog) {
        return new ContentObserver(book, catalog);
    }
}
