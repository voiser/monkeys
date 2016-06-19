package org.voiser.monkeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Events implements EventLogger {

    private final Map<Monkey, List<String>> events = new HashMap<>(); 
    
    @Override
    public void log(Monkey m, String event) {
        if (! events.containsKey(m)) {
            events.put(m, new ArrayList<>());
        }
        events.get(m).add(event);
    }
    
    public String get(Monkey m) {
        StringJoiner sj = new StringJoiner(",");
        List<String> evs = events.get(m);
        if (evs != null) {
            for (String ev : evs) {
                sj.add(ev);
            }
            return sj.toString();
        } else {
            return "";
        }
    }
}
