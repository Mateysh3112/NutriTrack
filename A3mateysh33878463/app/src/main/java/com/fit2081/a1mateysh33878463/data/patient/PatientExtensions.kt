package com.fit2081.a1mateysh33878463.data.patient

import com.fit2081.a1mateysh33878463.User

fun Patient.toUser(): User {
    return User(
        userId = this.userId,
        phoneNumber = this.phoneNumber,
        sex = this.sex,
        heifaScoreMale = this.heifaScoreMale,
        heifaScoreFemale = this.heifaScoreFemale,
        heifaVegFemale = this.heifaVegFemale,
        heifaVegMale = this.heifaVegMale,
        heifafruitsFemale = this.heifafruitsFemale,
        heifafruitsMale = this.heifafruitsMale,
        heifaGrainCerealFemale = this.heifaGrainCerealFemale,
        heifaGrainCerealMale = this.heifaGrainCerealMale,
        heifaWholeGrainFemale = this.heifaWholeGrainFemale,
        heifaWholeGrainMale = this.heifaWholeGrainMale,
        heifaMeatFemale = this.heifaMeatFemale,
        heifaMeatMale = this.heifaMeatMale,
        heifaDairyFemale = this.heifaDairyFemale,
        heifaDairyMale = this.heifaDairyMale,
        heifaWaterFemale = this.heifaWaterFemale,
        heifaWaterMale = this.heifaWaterMale,
        heifaUnsFatsFemale = this.heifaUnsFatsFemale,
        heifaUnsFatsMale = this.heifaUnsFatsMale,
        heifaSodiumFemale = this.heifaSodiumFemale,
        heifaSodiumMale = this.heifaSodiumMale,
        heifaSugarFemale = this.heifaSugarFemale,
        heifaSugarMale = this.heifaSugarMale,
        heifaAlcoholFemale = this.heifaAlcoholFemale,
        heifaAlcoholMale = this.heifaAlcoholMale,
        heifaDiscretionaryFemale = this.heifaDiscretionaryFemale,
        heifaDiscretionaryMale = this.heifaDiscretionaryMale,
        heifaSatFatsMale = this.heifaSatFatsMale,
        heifaSatFatsFemale = this.heifaSatFatsFemale
    )


}