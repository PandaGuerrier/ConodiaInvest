package fr.pandaguerrier.conodiajob;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.TreeMap;

public class Config {
    private final File file;

    private JSONObject json;

    private final JSONParser parser = new JSONParser();

    private final HashMap<String, Object> defaults = new HashMap<>();

    public Config(File file) {
        this.file = file;
        try {
            json = (JSONObject) new JSONParser().parse(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void defaultConfig() {
        JSONObject Blocks = new JSONObject();
        Blocks.put("STONE", 5.0D);
        this.defaults.put("maxLevels", 10);
        JSONObject LevelExemple = new JSONObject();
        JSONArray CommandsArray = new JSONArray();
        JSONArray PermissionsArray = new JSONArray();
        JSONArray LoreArray = new JSONArray();
        LoreArray.add("----");
        LoreArray.add("aaa");
        LoreArray.add("bbb");
        LoreArray.add("----");
        PermissionsArray.add("permission.ici");
        CommandsArray.add("/Commande 1");
        CommandsArray.add("/Commande 2");
        LevelExemple.put("requis", 500);
        LevelExemple.put("commandes", CommandsArray);
        LevelExemple.put("permissions", PermissionsArray);
        LevelExemple.put("blocks", Blocks);
        LevelExemple.put("lore", LoreArray);
        this.defaults.put("1", LevelExemple);
    }

    public void reload() {
        try {
            if (!this.file.getParentFile().exists())
                this.file.getParentFile().mkdirs();
            if (!this.file.exists()) {
                PrintWriter pw = new PrintWriter(this.file, "UTF-8");
                pw.print("{");
                pw.print("}");
                pw.flush();
                pw.close();
                this.json = (JSONObject) this.parser.parse(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8));
                defaultConfig();
                save();
            }
            this.json = (JSONObject) this.parser.parse(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void save() {
        try {
            JSONObject toSave = new JSONObject();
            for (String s : this.defaults.keySet()) {
                Object o = this.defaults.get(s);
                if (o instanceof String) {
                    toSave.put(s, getString(s));
                    continue;
                }
                if (o instanceof Double) {
                    toSave.put(s, getDouble(s));
                    continue;
                }
                if (o instanceof Integer) {
                    toSave.put(s, getInteger(s));
                    continue;
                }
                if (o instanceof JSONObject) {
                    toSave.put(s, getObject(s));
                    continue;
                }
                if (o instanceof JSONArray)
                    toSave.put(s, getArray(s));
            }
            TreeMap<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            treeMap.putAll(toSave);
            Gson g = (new GsonBuilder()).setPrettyPrinting().create();
            String prettyJsonString = g.toJson(treeMap);
            FileWriter fw = new FileWriter(this.file);
            fw.write(prettyJsonString);
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getRawData(String key) {
        return this.json.containsKey(key) ? this.json.get(key).toString() : (
                this.defaults.containsKey(key) ? this.defaults.get(key).toString() : key);
    }

    public String getString(String key) {
        return ChatColor.translateAlternateColorCodes('&', getRawData(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getRawData(key));
    }

    public double getDouble(String key) {
        try {
            return Double.parseDouble(getRawData(key));
        } catch (Exception exception) {
            return -1.0D;
        }
    }

    public double getInteger(String key) {
        try {
            return Integer.parseInt(getRawData(key));
        } catch (Exception exception) {
            return -1.0D;
        }
    }

    public JSONObject getObject(String key) {
        return this.json.containsKey(key) ? (JSONObject) this.json.get(key) : (
                this.defaults.containsKey(key) ? (JSONObject) this.defaults.get(key) : new JSONObject());
    }

    public JSONArray getArray(String key) {
        return this.json.containsKey(key) ? (JSONArray) this.json.get(key) : (
                this.defaults.containsKey(key) ? (JSONArray) this.defaults.get(key) : new JSONArray());
    }
}
