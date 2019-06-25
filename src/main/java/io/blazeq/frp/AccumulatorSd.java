package io.blazeq.frp;

import nz.sodium.CellLoop;
import nz.sodium.Stream;
import nz.sodium.Transaction;
import swidgets.SButton;
import swidgets.SLabel;

import javax.swing.*;
import java.awt.*;

public class AccumulatorSd {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Accumulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        Transaction.runVoid(() -> {
            CellLoop<Integer> value = new CellLoop<>();

            SLabel lblValue = new SLabel(value.map(i -> Integer.toString(i)));
            SButton btnPlus = new SButton("+");
            SButton btnMinus = new SButton("-");

            Stream<Integer> sPlus = btnPlus.sClicked.map(u -> +1);
            Stream<Integer> sMinus = btnMinus.sClicked.map(u -> -1);
            Stream<Integer> sDelta = sPlus.orElse(sMinus);

            Stream<Integer> sUpdate = sDelta.snapshot(value, (d, v) -> v + d);
            value.loop(sUpdate.hold(0));

            frame.add(lblValue);
            frame.add(btnPlus);
            frame.add(btnMinus);
        });

        frame.setSize(300, 60);
        frame.setVisible(true);
    }
}
