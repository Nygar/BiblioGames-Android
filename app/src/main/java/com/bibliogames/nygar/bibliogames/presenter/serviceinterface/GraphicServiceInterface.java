package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.GraphicEntry;

import java.util.List;

public interface GraphicServiceInterface extends BaseServiceInterface {
    void graphicOk(List<GraphicEntry> graphicEntries);
    void graphicFail();
}
