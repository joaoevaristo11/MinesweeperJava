package trabs.trab3.anexos.minesweeper.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;


public class Utils {
    public static String directory = "src\\trabs\\trab3\\anexos\\minesweeperImages\\";

    public static ImageIcon getImageIcon(String path, String description ) {
        File f = new File( directory + path );
        if ( !f.exists() ) return null ;
        return new ImageIcon(directory + path, description);
    }

    /**
     * Cria um item de menu qi associar a açăo.
     * @param text - Texto do item.
     * @param action - Açăo.
     * @return o item
     */
    public static JMenuItem makeJMenuItem(String text, ActionListener action) {
        JMenuItem mi = new JMenuItem( text );
        mi.addActionListener( action );
        return mi;
    }

}
