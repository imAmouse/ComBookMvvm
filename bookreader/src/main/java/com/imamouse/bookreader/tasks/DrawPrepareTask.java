package com.imamouse.bookreader.tasks;

import android.graphics.Color;

import com.imamouse.bookreader.interfaces.ILoadListener;
import com.imamouse.bookreader.interfaces.ITxtTask;
import com.imamouse.bookreader.main.PaintContext;
import com.imamouse.bookreader.main.TxtConfig;
import com.imamouse.bookreader.main.TxtReaderContext;
import com.imamouse.bookreader.utils.ELogger;

/**
 * Created by bifan-wei
 * on 2017/11/27.
 */

public class DrawPrepareTask implements ITxtTask {
    private String tag = "DrawPrepareTask";

    @Override
    public void Run(ILoadListener callBack, TxtReaderContext readerContext) {
        callBack.onMessage("start do DrawPrepare");
        ELogger.log(tag, "do DrawPrepare");
        initPainContext(readerContext.getPaintContext(), readerContext.getTxtConfig());
        readerContext.getPaintContext().textPaint.setColor(Color.WHITE);
        ITxtTask txtTask = new BitmapProduceTask();
        txtTask.Run(callBack, readerContext);
    }

    private void initPainContext(PaintContext paintContext, TxtConfig txtConfig) {
        TxtConfigInitTask.initPainContext(paintContext, txtConfig);
    }
}
