package io.blazeq.rxswing;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import javax.swing.*;

public class RxButton extends JButton {
    public final Observable<Integer> sClicked;

    public RxButton(String label) {
        super(label);
        BehaviorSubject<Integer> clicked = BehaviorSubject.createDefault(1);
        sClicked = clicked;
        addActionListener(e -> clicked.onNext(1));
    }
}
