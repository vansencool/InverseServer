package net.vansen.util;

import me.lucko.spark.minestom.SparkMinestom;
import net.luckperms.api.LuckPerms;
import net.minestom.server.instance.InstanceContainer;

@SuppressWarnings("unused")
public class Variables {

    /**
     * Instance container, main world.
     */
    public static InstanceContainer instanceContainer;

    /**
     * Is teleporting randomly players?
     */
    public static boolean teleporting = false;

    /**
     * Spark, really not used for now.
     */
    public static SparkMinestom sparkPlatform;

    /**
     * Luckperms.
     */
    public static LuckPerms luckperms;
}
