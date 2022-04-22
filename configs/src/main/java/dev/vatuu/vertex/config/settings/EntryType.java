package dev.vatuu.vertex.config.settings;

import com.google.gson.JsonObject;

public interface EntryType<E> {
    E parse(String key, JsonObject element);
    void serialize(String key, E object, JsonObject json);

    EntryType<Boolean> BOOLEAN = new EntryType<>() {
        public Boolean parse(String key, JsonObject element) { return element.get(key).getAsBoolean(); }
        public void serialize(String key, Boolean object, JsonObject json) { json.addProperty(key, object); }
    };
}
