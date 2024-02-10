package com.synrgy.presentation.constant

import com.synrgy.domain.local.BulletList

object DangerousGoodsConstant {
    fun getData(): ArrayList<BulletList> {
        return arrayListOf(
            BulletList("Compressed gas"),
            BulletList("Flammable substances"),
            BulletList("Poisonous substances"),
            BulletList("Corrosive materials"),
            BulletList("Dry and wet batteries"),
            BulletList("Biohazards (e.g. bacteria, viruses)"),
            BulletList("Explosives and fireworks"),
            BulletList("Radioactive substances"),
            BulletList("Other hazardous materials")
        )
    }
}