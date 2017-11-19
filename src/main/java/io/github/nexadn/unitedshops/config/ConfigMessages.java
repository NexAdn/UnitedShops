package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.util.HashMap;

public class ConfigMessages extends ConfigBase {

    private HashMap<String, String> messages;

    public ConfigMessages(File configFile)
    {
        super(configFile, "messages");
        this.messages = new HashMap<String, String>();
    }

    @Override
    public void parseConfig ()
    {
        for (String key : this.getSubKeys())
        {
            messages.put(key, this.getConf().getString(this.getWorkKey() + "." + key));
        }
    }

    public HashMap<String, String> getMessages ()
    {
        return this.messages;
    }
}
