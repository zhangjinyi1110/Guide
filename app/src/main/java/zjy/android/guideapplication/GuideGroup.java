package zjy.android.guideapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuideGroup implements Serializable {

    private final List<GuideItem> items = new ArrayList<>();
    
    public GuideGroup(GuideItem item) {
        this.items.add(item);
    }

    public void add(GuideItem item) {
        this.items.add(item);
    }

    public List<GuideItem> getItems() {
        return items;
    }
}
