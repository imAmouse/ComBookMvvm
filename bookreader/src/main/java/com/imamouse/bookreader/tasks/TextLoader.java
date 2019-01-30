package com.imamouse.bookreader.tasks;

import com.imamouse.bookreader.interfaces.IChapter;
import com.imamouse.bookreader.interfaces.ILoadListener;
import com.imamouse.bookreader.interfaces.IParagraphData;
import com.imamouse.bookreader.interfaces.ITxtTask;
import com.imamouse.bookreader.main.ParagraphData;
import com.imamouse.bookreader.main.TxtReaderContext;
import com.imamouse.bookreader.utils.ELogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bifan-wei
 * on 2018/1/28.
 */

public class TextLoader  {
    private String tag = "FileDataLoadTask";
    public void load(String text, TxtReaderContext readerContext, ILoadListener callBack) {
        IParagraphData paragraphData = new ParagraphData();
        List<IChapter> chapter = new ArrayList<>();
        callBack.onMessage("start read text");
        ELogger.log(tag, "start read text");
        paragraphData.addParagraph(text + "");
        readerContext.setParagraphData(paragraphData);
        readerContext.setChapters(chapter);
        ITxtTask txtTask = new TxtConfigInitTask();
        txtTask.Run(callBack, readerContext);
    }
}
