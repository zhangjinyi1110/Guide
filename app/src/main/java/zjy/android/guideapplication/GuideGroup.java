package zjy.android.guideapplication;

import java.util.ArrayList;
import java.util.List;

public class GuideGroup {

    private final List<ICustomGuide> guides = new ArrayList<>();
    
    public GuideGroup(ICustomGuide guide) {
        this.guides.add(guide);
    }

    public void add(ICustomGuide guide) {
        this.guides.add(guide);
    }

    public List<ICustomGuide> getGuides() {
        return guides;
    }
}
