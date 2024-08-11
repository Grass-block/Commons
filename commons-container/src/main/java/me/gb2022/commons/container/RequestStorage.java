package me.gb2022.commons.container;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class RequestStorage {
    private final HashMap<String, Set<String>> requests = new HashMap<>();

    public Set<String> getRequestList(String name) {
        Set<String> list = requests.get(name);
        if (list == null) {
            list = new HashSet<>();
            this.requests.put(name, list);
        }
        return list;
    }

    public void addRequest(String holder, String sender) {
        getRequestList(holder).add(sender);
    }

    public void removeRequest(String holder, String sender) {
        getRequestList(holder).remove(sender);
    }

    public boolean containsRequest(String holder, String sender) {
        return getRequestList(holder).contains(sender);
    }
}