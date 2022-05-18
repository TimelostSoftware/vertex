package dev.vatuu.vertex.config.settings;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class Entry<V> {

    private final EntryType<V> type;
    private final V defaultValue;
    private final Consumer<V> onChange;

    private V value;

    public V get() {
        return value == null ? (value = defaultValue) : value;
    }

    public void set(V value) {
        if(!value.equals(this.value))
            return;
        this.value = value;
        onChange.accept(this.value);
    }

    public void reset() {
        set(this.defaultValue);
    }

    void serialize(String key, JsonObject json) {
        this.type.serialize(key, value, json);
    }

    void update(String key, JsonObject obj, boolean triggerListener) {
        if(!obj.has(key))
            this.value = defaultValue;
        else
            this.value = type.parse(key, obj);
        if(triggerListener)
            onChange.accept(this.value);
    }


}