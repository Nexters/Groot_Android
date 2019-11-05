package com.nexters.android.pliary.data

import com.nexters.android.pliary.R
import java.io.Serializable


data class PlantSpecies(val id: Int = -1,
                          var name: String,
                          var nameKr: String = "",
                          val posUrl: String,
                          val nagUrl: String,
                          val info: String? = "",
                          val tip: String?) : Serializable {

    companion object{
        const val PLANT_STUKI = 0
        const val PLANT_EUCALYPTUS = 1
        const val PLANT_SANSEVIERIA = 2
        const val PLANT_MONSTERA = 3
        const val PLANT_PARLOUR_PALM = 4
        const val PLANT_ELASTICA = 5
        const val PLANT_TRAVELERS_PALM = 6
        const val PLANT_SCHEFFLERA = 7
        const val PLANT_USERS = 8
        const val PLANT_HANGING = 9

        fun makePlantArray(): ArrayList<PlantSpecies> = arrayListOf(
            PlantSpecies(
                PLANT_STUKI,
                "Stuki",
                "스투키",
                "And_Posi_Stuki",
                "And_Nega_Stuki",
                "스투키는 대부분 한 달에 1번 물을 주는 것을 추천합니다.",
                "스투키의 잎이 갈라지거나 슬림 해진다면 흙의 습도를 확인하고 물을 주세요. 스투키는 햇빛, 물, 통풍에 유의하여 키워주세요."
            ),
            PlantSpecies(
                PLANT_EUCALYPTUS,
                "Eucalyptus",
                "유칼립투스",
                "And_Posi_Eucalyptus",
                "And_Nega_Eucalyptus",
                "유칼립투스는 대부분 3-4일에 1번 물을 주는 것을 추천합니다.",
                "유칼립투스는 직사광선보단 햇볕과 바람이 잘 드는 곳에서 키워주세요. 잎이 건조해 보인다면 분무기로 물을 주세요."
            ),
            PlantSpecies(
                PLANT_SANSEVIERIA,
                "Sansevieria",
                "산세베리아",
                "And_Posi_Sansevieria",
                "And_Nega_Sansevieria",
                "산세베리아는 대부분 한 달에 1번 물을 주는 것을 추천합니다.",
                "산세베리아의 잎이 노랗게 변한다면 흙의 습도를 확인하고 물을 주세요. 산세베리아는 따뜻한 곳에서 다소 건조하게 키워주세요."
            ),
            PlantSpecies(
                PLANT_MONSTERA,
                "Monstera",
                "몬스테라",
                "And_Posi_Monstera",
                "And_Nega_Monstera",
                "몬스테라는 대부분 3-5일에 1번 물을 주는 것을 추천합니다.",
                "몬스테라는 빛이 없는 곳에서도 잘 자랍니다. 직사광선을 받으면 잎이 타버려 갈색으로 변할 수 있습니다."
            ),
            PlantSpecies(
                PLANT_PARLOUR_PALM,
                "Parlour palm",
                "테이블 야자",
                "And_Posi_Chamaedorea_elegans",
                "And_Nega_Chamaedorea-elegans",
                "테이블야자는 대부분 7일에 1번 물을 주는 것을 추천합니다.",
                "테이블야자는 NASA에서 선정한 공기정화 식물입니다. 미세먼지를 제거하고, 전자파를 차단해줍니다. 따뜻한 곳에서 키우길 권장합니다."
            ),
            PlantSpecies(
                PLANT_ELASTICA,
                "Elastica",
                "고무나무",
                "And_Posi_gomu",
                "And_Nega_gomu",
                "고무나무는 대부분 7일에 1번 물을 주는 것을 추천합니다.",
                "고무나무는 실내 밝은 그늘에서 관리하는 것이 좋습니다. 암모니아 제거에 탁월한 효능이 있습니다. 겉흙이 말라있다면 물을 주세요."
            ),
            PlantSpecies(
                PLANT_TRAVELERS_PALM,
                "Traveler’s palm",
                "여인초",
                "And_Posi_traveler_s_palm",
                "And_Nega_Traveler’s-palm",
                "여인초는 대부분 10일에 1번 물을 주는 것을 추천합니다.",
                "여인초는 통풍이 잘 되는 곳을 선호합니다. 잦은 장소 이동은 스트레스를 줄 수 있습니다."
            ),
            PlantSpecies(
                PLANT_SCHEFFLERA,
                "Schefflera",
                "홍콩야자",
                "And_Posi_hongkong",
                "And_Nega_hongkong",
                "홍콩야자는 대부분 5일에 1번 물을 주는 것을 추천합니다.",
                "홍콩야자는 증산작용이 뛰어나 가습기 역할을 합니다. 새집증후군을 없애는데 탁월합니다. 홍콩야자의 겉흙이 마르면 물을 주세요."
            ),
            PlantSpecies(
                PLANT_HANGING,
                "Hanging Plant",
                "",
                "and_posi_hanging",
                "and_nega_hanging",
                "행잉플랜트는 물을 주는 것보다 물의 흡수를 위해 통풍을 잘 해주는 것이 중요합니다.",
                "행잉플랜트는 물을 주는 것보다 물의 흡수를 위해 통풍을 잘 해주는 것이 중요합니다. 바람이 잘 통하는 곳에서 키워주세요."
            ),
            PlantSpecies(
                PLANT_USERS,
                "My Plant",
                "",
                "And_Posi_UserMakePlant",
                "And_Nega_UserMakePlant",
                null,
                "물을 줄때 겉흙이 말랐는지 참고해주세요. 햇빛과 통풍 모두 신경써주는게 좋습니다."
            )
        )
    }

    /*class Empty : PlantSpecies(0, "", "", "", "", "", "")*/

    /*class Stuki : PlantSpecies(
        PLANT_STUKI,
        "Stuki",
        "스투키",
        "And_Posi_Stuki",
        "And_Nega_Stuki",
        "스투키는 대부분 한 달에 1번 물을 주는 것을 추천합니다.",
        "스투키의 잎이 갈라지거나 슬림 해진다면 흙의 습도를 확인하고 물을 주세요. 스투키는 햇빛, 물, 통풍에 유의하여 키워주세요.")*/

    /*class Eucalyptus : PlantSpecies(
        PLANT_EUCALYPTUS,
        "Eucalyptus",
        "유칼립투스",
        "And_Posi_Eucalyptus",
        "And_Nega_Eucalyptus",
        "유칼립투스는 대부분 3-4일에 1번 물을 주는 것을 추천합니다.",
        "유칼립투스는 직사광선보단 햇볕과 바람이 잘 드는 곳에서 키워주세요. 잎이 건조해 보인다면 분무기로 물을 주세요.")*/

    /*class Sansevieria : PlantSpecies(
        PLANT_SANSEVIERIA,
        "Sansevieria",
        "산세베리아",
        "And_Posi_Sansevieria",
        "And_Nega_Sansevieria",
        "산세베리아는 대부분 한 달에 1번 물을 주는 것을 추천합니다.",
        "산세베리아의 잎이 노랗게 변한다면 흙의 습도를 확인하고 물을 주세요. 산세베리아는 따뜻한 곳에서 다소 건조하게 키워주세요.")*/

    /*class Monstera : PlantSpecies(
        PLANT_MONSTERA,
        "Monstera",
        "몬스테라",
        "And_Posi_Monstera",
        "And_Nega_Monstera",
        "몬스테라는 대부분 3-5일에 1번 물을 주는 것을 추천합니다.",
        "몬스테라는 빛이 없는 곳에서도 잘 자랍니다. 직사광선을 받으면 잎이 타버려 갈색으로 변할 수 있습니다.")*/

    /*class ParlourPalm : PlantSpecies(
        PLANT_PARLOUR_PALM,
        "Parlour palm",
        "테이블 야자",
        "And_Posi_Chamaedorea_elegans",
        "And_Nega_Chamaedorea-elegans",
        "테이블야자는 대부분 7일에 1번 물을 주는 것을 추천합니다.",
        "테이블야자는 NASA에서 선정한 공기정화 식물입니다. 미세먼지를 제거하고, 전자파를 차단해줍니다. 따뜻한 곳에서 키우길 권장합니다.")*/

    /*class Elastica : PlantSpecies(
        PLANT_ELASTICA,
        "Elastica",
        "고무나무",
        "And_Posi_gomu",
        "And_Nega_gomu",
        "고무나무는 대부분 7일에 1번 물을 주는 것을 추천합니다.",
        "고무나무는 실내 밝은 그늘에서 관리하는 것이 좋습니다. 암모니아 제거에 탁월한 효능이 있습니다. 겉흙이 말라있다면 물을 주세요.")*/

    /*class TravelersPalm : PlantSpecies(
        PLANT_TRAVELERS_PALM,
        "Traveler’s palm",
        "여인초",
        "And_Posi_traveler's palm",
        "And_Nega_Traveler’s-palm",
        "여인초는 대부분 10일에 1번 물을 주는 것을 추천합니다.",
        "여인초는 통풍이 잘 되는 곳을 선호합니다. 잦은 장소 이동은 스트레스를 줄 수 있습니다.")*/

    /*class Schefflera : PlantSpecies(
        PLANT_SCHEFFLERA,
        "Schefflera",
        "홍콩야자",
        "And_Posi_hongkong",
        "And_Nega_hongkong",
        "홍콩야자는 대부분 5일에 1번 물을 주는 것을 추천합니다.",
        "홍콩야자는 증산작용이 뛰어나 가습기 역할을 합니다. 새집증후군을 없애는데 탁월합니다. 홍콩야자의 겉흙이 마르면 물을 주세요.")

    class Users : PlantSpecies(
        PLANT_USERS,
        "",
        null,
        "And_Posi_UserMakePlant",
        "And_Nega_UserMakePlant",
        null,
        "물을 줄때 겉흙이 말랐는지 참고해주세요. 햇빛과 통풍 모두 신경써주는게 좋습니다.")*/

}

fun String.getLocalImage(isPositive: Boolean) : Int {
    val name = this.toLowerCase()
    return if(name.contains("stuki")) {
        if(isPositive) R.drawable.and_posi_stuki else R.drawable.and_nega_stuki
    } else if(name.contains("eucalyptus")) {
        if(isPositive) R.drawable.and_posi_eucalyptus else R.drawable.and_nega_eucalyptus
    } else if(name.contains("sansevieria")) {
        if(isPositive) R.drawable.and_posi_sansevieria else R.drawable.and_nega_sansevieria
    } else if(name.contains("monstera")) {
        if(isPositive) R.drawable.and_posi_monstera else R.drawable.and_nega_monstera
    } else if(name.contains("chamaedorea")) {
        if(isPositive) R.drawable.and_posi_chamaedorea_elegans else R.drawable.and_nega_chamaedorea_elegans
    } else if(name.contains("gomu")) {
        if(isPositive) R.drawable.and_posi_gomu else R.drawable.and_nega_gomu
    } else if(name.contains("traveler")) {
        if(isPositive) R.drawable.and_posi_travelers_palm else R.drawable.and_nega_travelers_palm
    } else if(name.contains("hongkong")) {
        if(isPositive) R.drawable.and_posi_hongkong else R.drawable.and_nega_hongkong
    } else if(name.contains("hanging")) {
        if(isPositive) R.drawable.and_posi_hanging else R.drawable.and_nega_hanging
    } else {
        if(isPositive) R.drawable.and_posi_usermakeplant else R.drawable.and_nega_usermakeplant
    }
}

/*
fun makePlantArray(): ArrayList<PlantSpecies> = arrayListOf(
    PlantSpecies.Empty(),
    PlantSpecies.Stuki(),
    PlantSpecies.Eucalyptus(),
    PlantSpecies.Sansevieria(),
    PlantSpecies.Monstera(),
    PlantSpecies.ParlourPalm(),
    PlantSpecies.Elastica(),
    PlantSpecies.TravelersPalm(),
    PlantSpecies.Schefflera(),
    PlantSpecies.Users()
)*/
