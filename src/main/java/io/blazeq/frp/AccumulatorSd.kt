package io.blazeq.frp

import nz.sodium.CellLoop
import nz.sodium.Transaction
import swidgets.SButton
import swidgets.SLabel
import java.awt.FlowLayout
import javax.swing.JFrame

fun main() {
    val frame = JFrame("Accumulator")
    frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
    frame.layout = FlowLayout()

    Transaction.runVoid {
        val value = CellLoop<Int>()

        val lblValue = SLabel(value.map{it.toString()})
        val btnPlus = SButton("+")
        val btnMinus = SButton("-")

        val sPlus = btnPlus.sClicked.map { +1 }
        val sMinus = btnMinus.sClicked.map { -1 }
        val sDelta = sPlus.orElse(sMinus)

        val sUpdate = sDelta.snapshot(value) { d, v -> v + d }
        value.loop(sUpdate.hold(0))

        frame.add(lblValue)
        frame.add(btnPlus)
        frame.add(btnMinus)
    }

    frame.setSize(300, 60)
    frame.isVisible = true
}