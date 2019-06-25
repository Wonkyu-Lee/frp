package io.blazeq.rxswing;

import io.reactivex.Observable;

import javax.swing.*;

public class RxLabel extends JLabel {
    public RxLabel(Observable<String> sLabel) {
        sLabel.subscribe(this::setText);
    }
}
