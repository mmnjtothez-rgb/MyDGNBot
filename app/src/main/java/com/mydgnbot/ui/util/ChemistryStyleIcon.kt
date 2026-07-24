package com.mydgnbot.ui.util

object ChemistryStyleIcon {

    fun getUrl(style: String?): String? {

        return when (style?.trim()?.lowercase()) {

            "basic" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/250.png"

            "sniper" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/251.png"

            "finisher" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/252.png"

            "deadeye" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/253.png"

            "marksman" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/254.png"

            "hawk" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/255.png"

            "artist" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/256.png"

            "architect" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/257.png"

            "powerhouse" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/258.png"

            "maestro" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/259.png"

            "engine" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/260.png"

            "sentinel" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/261.png"

            "guardian" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/262.png"

            "gladiator" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/263.png"

            "backbone" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/264.png"

            "anchor" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/265.png"

            "hunter" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/266.png"

            "catalyst" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/267.png"

            "shadow" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/268.png"

            "wall" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/269.png"

            "shield" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/270.png"

            "cat" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/271.png"

            "glove" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/272.png"

            "gk basic",
            "gk_basic",
            "gkbasic" ->
                "https://cdn.futwiz.com/assets/img/fifa20/chemstyles/273.png"

            else -> null

        }

    }

}