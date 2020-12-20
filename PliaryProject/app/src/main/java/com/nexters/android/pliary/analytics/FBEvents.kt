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

        /* 식물카드 상세 화면*/
        const val DETAIL_MENU_EDIT = "card_detail_edit"
        const val DETAIL_MENU_DELETE = "card_detail_delete"
        const val DETAIL_WATER_CLICK = "card_detail_watercycle_click"
        const val DETAIL_WRITE_DIARY_CLICK = "card_detail_diary_write_click"
        const val DETAIL_DIARY_CLICK = "card_detail_diary_click"
        const val DETAIL_DIARY_DELETE_CLICK = "card_detail_diary_delete_click"
        const val DETAIL_CALENDAR_SWIPE = "card_detail_calendar_swipe"

        /* 식물카드 수정 화면 */
        const val EDIT_PLANT_NICKNAME_CLICK = "card_edit_nickname_click"
        const val EDIT_PLANT_WATER_SET = "card_edit_watercycle_click"
        const val EDIT_PLANT_CLOSE_CLICK = "card_edit_close_click"
        const val EDIT_PLANT_COMPLETE_CLICK = "card_edit_complete_click"

        /* 식물카드 물주기 팝업 */
        const val WATER_POPUP_POSTPONE_CLICK = "watercycle_popup_postpone_click"
        const val WATER_POPUP_WATERING_CLICK = "watercycle_popup_water_click"
        const val WATER_POPUP_CLOSE_CLICK = "watercycle_popup_close_click"

        /* 다이어리 상세 화면 */
        const val DIARY_DETAIL_PHOTO_CLICK = "diary_detail_photo_click"
        const val DIARY_DETAIL_MORE_CLICK = "diary_detail_more_click"
        const val DIARY_DETAIL_MENU_EDIT_CLICK = "diary_detail_edit_click"
        const val DIARY_DETAIL_MENU_DELETE_CLICK = "diary_detail_delete_click"
    }
}