package zjy.android.guideapplication;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuidePage implements Serializable {

    private final List<GuideItem> items = new ArrayList<>();

    public GuidePage(View view) {
        this(new GuideItem(view));
    }

    public GuidePage(GuideItem item) {
        this.items.add(item);
    }

    public GuidePage add(GuideItem item) {
        this.items.add(item);
        return this;
    }

    public List<GuideItem> getItems() {
        return items;
    }
}
