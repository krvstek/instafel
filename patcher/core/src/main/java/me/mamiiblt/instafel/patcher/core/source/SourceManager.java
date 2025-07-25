package me.mamiiblt.instafel.patcher.core.source;

import org.apache.commons.io.FileUtils;

import brut.androlib.ApkBuilder;
import brut.androlib.ApkDecoder;
import brut.androlib.Config;
import brut.androlib.exceptions.AndrolibException;
import brut.directory.ExtFile;
import me.mamiiblt.instafel.patcher.core.utils.Env;
import me.mamiiblt.instafel.patcher.core.utils.Log;
import me.mamiiblt.instafel.patcher.core.utils.Utils;

import java.io.*;

public class SourceManager {

    private Config config;

    public SourceManager() {
        this.config = new Config();
    }

    public void decompile(ExtFile apkFile) throws IOException, AndrolibException {
        Log.info("Decompiling Instagram APK...");        
        ApkDecoder apkDecoder = new ApkDecoder(apkFile, config);
        apkDecoder.decode(new File(Utils.mergePaths(Env.PROJECT_DIR, "sources")));
        Log.info("APK decompiled succesfully");
    }

    public void decompileIflBaseAPK(ExtFile apkFile) throws IOException, AndrolibException {
        Log.info("Decompiling Instafel Base APK...");        
        ApkDecoder apkDecoder = new ApkDecoder(apkFile, config);
        apkDecoder.decode(new File(Utils.mergePaths(Env.PROJECT_DIR, "sources")));
        Log.info("APK decompiled succesfully");
    }

    public void build(String fname) throws AndrolibException, IOException {
        Log.info("Building APK");
        File buildDir = new File(Utils.mergePaths(Env.PROJECT_DIR, "build"));
        if (!buildDir.exists()) {
            FileUtils.forceMkdir(buildDir);
        }
        ApkBuilder apkBuilder = new ApkBuilder(
            new ExtFile(
                Utils.mergePaths(Env.PROJECT_DIR, "sources")
            ), config);
        apkBuilder.build(new File(Utils.mergePaths(buildDir.getAbsolutePath(), fname)));
        Log.info("APK builded succesfully");
    }

    public void createConfigAndEnvFile() throws IOException {
        File pEnvFile = new File(Utils.mergePaths(Env.PROJECT_DIR, "env.properties"));
        pEnvFile.createNewFile();
        Env.Project.setupEnv();
        Env.Project.createDefaultEnvFile();
        Env.Project.saveProperties();

        File pConfigFile = new File(Utils.mergePaths(Env.PROJECT_DIR, "config.properties"));
        pConfigFile.createNewFile();
        Env.Config.setupConfig();
        Env.Config.createDefaultConfigFile();
        Env.Config.saveProperties();
    }
    
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
