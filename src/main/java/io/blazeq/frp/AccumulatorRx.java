package io.blazeq.frp;

import io.blazeq.rxswing.RxButton;
import io.blazeq.rxswing.RxLabel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import javax.swing.*;
import java.awt.*;


public class AccumulatorRx {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Accumulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        BehaviorSubject<Integer> value = BehaviorSubject.createDefault(0);
        RxLabel lblValue = new RxLabel(value.map(i -> i.toString()));
        RxButton btnPlus = new RxButton("+");
        RxButton btnMinus = new RxButton("-");

        Observable<Integer> plus = btnPlus.sClicked.map(u -> +1);
        Observable<Integer> minus = btnMinus.sClicked.map(u -> -1);
        Observable<Integer> delta = plus.mergeWith(minus);

        Observable<Integer> update = delta.withLatestFrom(value, (d, v) -> v + d);
        update.subscribe(i -> value.onNext(i));

        frame.add(lblValue);
        frame.add(btnPlus);
        frame.add(btnMinus);

        frame.setSize(300, 60);
        frame.setVisible(true);
    }
}
