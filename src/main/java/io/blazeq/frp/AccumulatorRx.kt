package io.blazeq.frp

import io.blazeq.rxswing.RxButton
import io.blazeq.rxswing.RxLabel
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import java.awt.FlowLayout
import javax.swing.JFrame

fun main() {
    val frame = JFrame("Accumulator")
    frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
    frame.layout = FlowLayout()

    val value = BehaviorSubject.createDefault(0)
    val lblValue = RxLabel(value.map { it.toString() })
    val btnPlus = RxButton("+")
    val btnMinus = RxButton("-")

    val plus = btnPlus.sClicked.map { +1 }
    val minus = btnMinus.sClicked.map { -1 }
    val delta = plus.mergeWith(minus)

    val update = delta.withLatestFrom<Int, Int>(value, BiFunction { d, v -> v + d })
    update.subscribe { value.onNext(it) }

    frame.add(lblValue)
    frame.add(btnPlus)
    frame.add(btnMinus)

    frame.setSize(300, 60)
    frame.isVisible = true
}