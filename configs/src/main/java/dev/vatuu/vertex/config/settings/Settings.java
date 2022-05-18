package dev.vatuu.vertex.config.settings;

import com.google.common.collect.Maps;
import com.google.gson.*;
import dev.vatuu.vertex.config.file.FileWatcher;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public abstract class Settings {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, Entry<?>> fields = Maps.newHashMap();

    private final File file;
    private final FileWatcher fileWatcher;

    public Settings(File file) {
        this.file = file;

        registerEntries();

        if(!file.exists() || file.isDirectory()) {
            try {
                //HoloUI.log(Level.INFO, "Settings file missing, generating new default file.");
                if(file.isDirectory())
                    FileUtils.deleteQuietly(file);
                else
                    file.getParentFile().mkdirs();
                file.createNewFile();
                writeJson();
            } catch(IOException e) {
                /* HoloUI.log(Level.WARNING, "An error occurred while writing the settings default settings file:");
                if(e.getMessage() != null)
                    HoloUI.log(Level.WARNING, "\t%s: %s", e.getClass().getSimpleName(), e.getMessage());
                else
                    HoloUI.log(Level.WARNING, "\t%s", e.getClass().getSimpleName());*/
            }
        } else
            doReload(false);

        this.fileWatcher = new FileWatcher(file);
    }

    public void update() {
        if(fileWatcher.checkModified())
            doReload(true);
    }

    public void write() {
        writeJson();
    }

    protected abstract void registerEntries();

    protected void registerField(String field, Entry<?> entry) {
        this.fields.putIfAbsent(field, entry);
    }

    private void doReload(boolean triggerListeners) {
        try(FileReader reader = new FileReader(file)) {
            JsonElement element = JsonParser.parseReader(reader);
            JsonObject obj = element.getAsJsonObject();
            fields.forEach((f, e) -> e.update(f, obj, triggerListeners));
        } catch(IOException | JsonParseException e) {
            /*HoloUI.log(Level.WARNING, "An error occurred while reloading the settings file:");
            if(e.getMessage() != null)
                HoloUI.log(Level.WARNING, "\t%s: %s", e.getClass().getSimpleName(), e.getMessage());
            else
                HoloUI.log(Level.WARNING, "\t%s", e.getClass().getSimpleName());*/
        }
    }

    private void writeJson() {
        try(FileWriter writer = new FileWriter(file)) {
            JsonObject obj = new JsonObject();
            fields.forEach((name, field) -> field.serialize(name, obj));
            GSON.toJson(obj, writer);
        } catch(IOException e) {
            /*HoloUI.log(Level.WARNING, "An error occurred while writing the settings file:");
            if(e.getMessage() != null)
                HoloUI.log(Level.WARNING, "\t%s: %s", e.getClass().getSimpleName(), e.getMessage());
            else
                HoloUI.log(Level.WARNING, "\t%s", e.getClass().getSimpleName());*/
        }
    }
}
