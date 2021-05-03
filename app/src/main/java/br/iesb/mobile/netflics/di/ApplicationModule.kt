package br.iesb.mobile.netflics.di

import android.content.Context
import br.iesb.mobile.netflics.service.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun networkMonitor(@ApplicationContext context: Context) = NetworkMonitor(context)

}