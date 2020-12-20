package com.nexters.android.pliary.analytics

class FBEvents {
    companion object {
        /* 홈 화면 */
        const val HOME_WATER_CLICK = "watercycle_click"
        const val HOME_CARD_ADD_CLICK = "card_add_click"
        const val HOME_CARD_DETAIL_CLICK = "card_detail_click"

        /* 식물카드 생성 화면 */
        const val ADD_PLANT_CHOICE_CLICK = "card_add_choice_click"
        const val ADD_PLANT_WATER_SET = "card_add_watercycle_click"
        const val ADD_PLANT_COMPLETE_CLICK = "card_add_complete_click"

        /* 식물카드 수정화면 */
        const val EDIT_PLANT_NICKNAME_CLICK = "card_edit_nickname_click"
        const val EDIT_PLANT_WATER_SET = "card_edit_watercycle_click"
    }
}