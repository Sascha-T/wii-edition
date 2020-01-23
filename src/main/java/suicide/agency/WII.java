package suicide.agency;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mod(modid = WII.MODID, name = WII.NAME, version = WII.VERSION, dependencies = "before:*")
public class WII {
    public static final String MODID = "mcwii";
    public static final String NAME = "mcwii";
    public static final String VERSION = "1.0";
    public WIILoadingThread thread = new WIILoadingThread(this);
    private static Logger logger = LogManager.getLogger("MCWII");
    public WIIEvents WIIEvents = new WIIEvents(this);
    String[] songs = {"miichannel.ogg", "wiishoptheme.ogg", "wiisportsresort.ogg"};

    public static void setFinalStatic(Object a, Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(a, newValue);
    }
    public WII() {
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            logger.info("Music started. Resuming load.");
            thread.start();
            Display.setTitle("Minecraft Wii Edition");
            logger.info("Starting thread for music...");
            logger.info("Music started. Resuming load.");
        } else {
            logger.info("MCWII Server mod for client experience enhancements is installed!");
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            try {
                ResourceLocation resloc = new ResourceLocation("kms", "loading.png");
                setFinalStatic(null, GuiMainMenu.class.getDeclaredField("field_194400_H"), resloc);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        logger = event.getModLog();
        logger.info("Event Listener registering...");
        MinecraftForge.EVENT_BUS.register(WIIEvents);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
    }


}

