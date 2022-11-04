package nl.hva.backend.server;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class CustomBanner implements Banner {
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println("______________________________________________________________________________________________________________\n" +
                "      __                                                   ____                                               \n" +
                "    /    )                          ,                      /   )                   /                         /\n" +
                "----\\----------------_--_---_/_----------__-----__--------/__ /------__-----__----/-__-----__-----__-----__-/-\n" +
                "     \\      /   /   / /  )  /     /    /   )  /   )      /    )    /   )  /   '  /(      /___)  /   )  /   /  \n" +
                "_(____/____(___(___/_/__/__(_ ___/____/___/__(___/______/____/____(___(__(___ __/___\\___(___ __/___/__(___/___\n" +
                "                                                /                                                             \n" +
                "                                            (_ /                                                              \n" +
                "Powered by team 1");
    }
}
