package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.repository.FutGgImageRepository
import com.mydgnbot.data.repository.FutGgRepository
import com.mydgnbot.data.repository.PlayerEnrichmentRepository
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import java.io.File


class HomeViewModelFactory(

    private val playerRepository: PlayerRepository,

    private val settingsRepository: SettingsRepository,

    private val connectivityObserver: ConnectivityObserver,

    private val cacheDir: File

) : ViewModelProvider.Factory {



    override fun <T : ViewModel> create(

        modelClass: Class<T>

    ): T {


        if (

            modelClass.isAssignableFrom(
                HomeViewModel::class.java
            )

        ) {


            val playerEnrichmentRepository =

                PlayerEnrichmentRepository(

                    cacheFolder =

                        File(
                            cacheDir,
                            "futgg"
                        ),

                    futGgRepository =

                        FutGgRepository(),

                    futGgImageRepository =

                        FutGgImageRepository()

                )



            @Suppress("UNCHECKED_CAST")

            return HomeViewModel(

                playerRepository,

                settingsRepository,

                playerEnrichmentRepository,

                connectivityObserver

            ) as T


        }



        throw IllegalArgumentException(

            "Unknown ViewModel class"

        )

    }

}