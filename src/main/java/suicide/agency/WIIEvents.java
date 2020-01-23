package suicide.agency;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import suicide.agency.net.VerifyMSG;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class WIIEvents {
    public HashMap<String, Boolean> map = new HashMap<>();
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
    private static Logger logger = LogManager.getLogger("MC-WII-C");
    WII lKMS;

    public WIIEvents(WII lKMS) {
        this.lKMS = lKMS;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGui(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu) {
            try {
                String[] texts = Resources.toString(WII.class.getResource("/script.txt"), Charsets.UTF_8).split("\n");
                String splash = texts[new Random().nextInt(texts.length)];
                WII.setFinalStatic(event.getGui(), GuiMainMenu.class.getDeclaredFields()[3], splash);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            logger.info("Stopping Music");
            if (lKMS.thread.isAlive()) {
                lKMS.thread.stopE();
                lKMS.thread.stop();
            }
            GuiMainMenu menu = (GuiMainMenu) event.getGui();
            event.setGui(menu);
        }
    }

}
