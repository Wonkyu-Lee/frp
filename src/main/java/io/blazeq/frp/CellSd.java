package io.blazeq.frp;

import nz.sodium.CellLoop;
import nz.sodium.Transaction;
import swidgets.SButton;
import swidgets.SLabel;

import javax.swing.*;
import java.awt.*;

public class CellSd {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cell");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        Transaction.runVoid(() -> {
            CellLoop<Integer> value = new CellLoop<>();
            SLabel label = new SLabel(value.map(i -> Integer.toString(i)));

            SButton button = new SButton("signal");
            value.loop(button.sClicked.map(u -> 1).hold(0));

            frame.add(button);
            frame.add(label);
        });

        frame.setSize(200, 60);
        frame.setVisible(true);
    }
}
