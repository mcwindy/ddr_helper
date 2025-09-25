package com.mcwindy.ddrhelper.ui.binding

import androidx.lifecycle.ViewModel
import com.mcwindy.ddrhelper.model.ClassifiedServerList

class BindingViewModel : ViewModel() {
    val bindingData: List<ClassifiedServerList<BindingData>> = listOf(
        ClassifiedServerList(
            "飞行", listOf(
                BindingData("pf", "exp1"),
                BindingData("df", "bind [按键] +toggle cl_dummy_hammer 1 0"),
                BindingData("hdf", "exp3"),
                BindingData("hookfly", "exp4")
            )
        ), ClassifiedServerList(
            "皮肤", listOf(
                BindingData(
                    "变色",
                    "exp1",
                    "详情查看 https://wiki.ddnet.org/index.php?title=Binds/zh&variant=zh-hans#%E5%8F%98%E8%89%B2Tee"
                ),
                BindingData("一键设置身体颜色", "bind [按键] player_color_body 5635840"),
                BindingData("一键设置脚颜色", "bind [按键] player_color_feet 5635840"),
                BindingData("com4", "exp4")
            )
        ), ClassifiedServerList(
            "聊天", listOf(
                BindingData("com1", "exp1"),
                BindingData("com2", "exp2"),
                BindingData("com3", "exp3"),
                BindingData("com4", "exp4")
            )
        ), ClassifiedServerList(
            "其他", listOf(
                BindingData("隐身", "bind [按键] say /spec", "所有KOG地图和部分ddr地图支持"),
                BindingData("旁观", "bind [按键] say /pause"),
                BindingData("加入队伍", "bind [按键] say /team 1"),
                BindingData(
                    "发送特定表情",
                    "bind [按键] emote 2",
                    "表情编号可查看 https://wiki.ddnet.org/index.php?title=Binds/zh&variant=zh-hans"
                ),
                BindingData(
                    "八向射击",
                    "bind x \"+toggle cl_mouse_max_distance 2 400; +toggle inp_mousesens 2 164; +showhookcoll\""
                ),
                BindingData(
                    "显示所有玩家辅助线",
                    "bind s \"+showhookcoll; +toggle cl_show_hook_coll_other 2 1\""
                )
            )
        )
    )
}
