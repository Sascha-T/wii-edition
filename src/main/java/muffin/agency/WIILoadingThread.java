package muffin.agency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryJavaSound;

class WIILoadingThread extends Thread {
    private static Logger logger = LogManager.getLogger("MC-WII-C");
    private volatile boolean shouldExit = false;
    WII parentInstance;
    //MediaPlayer mediaPlayer;
    SoundSystem mySoundSystem;

    public WIILoadingThread(WII instance) {
        this.parentInstance = instance;
    }

    public boolean run = true;

    public void run() {
        try {
            logger.info("Raffling for music...");


            try {
                SoundSystemConfig.addLibrary(LibraryJavaSound.class);
                SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
                mySoundSystem = new SoundSystem();
                double p = mySoundSystem.randomNumberGenerator.nextInt(8) / 3;
                String name = parentInstance.songs[(int) Math.round(p)];
                mySoundSystem.backgroundMusic(name, WII.class.getResource("/" + name), name, false);
                while (run)
                    if (!mySoundSystem.playing()) {
                        mySoundSystem.play(name);
                        while (!mySoundSystem.playing() && run) ;
                    }
                logger.info(name + " will be played");
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopE() {
        try {
            run = false;
            mySoundSystem.stop("ogg music");
            mySoundSystem.cleanup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
