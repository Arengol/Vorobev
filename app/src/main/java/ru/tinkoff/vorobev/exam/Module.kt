package ru.tinkoff.vorobev.exam

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.tinkoff.vorobev.exam.data.MainRepository
import ru.tinkoff.vorobev.exam.data.MainRepositoryImpl
import ru.tinkoff.vorobev.exam.data.database.DatabaseRepository
import ru.tinkoff.vorobev.exam.data.database.DatabaseRepositoryImpl
import ru.tinkoff.vorobev.exam.data.network.NetworkRepository
import ru.tinkoff.vorobev.exam.data.network.NetworkRepositoryImpl
import ru.tinkoff.vorobev.exam.data.network.ServerAPI

@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    fun provideDatabaseRepository (@ApplicationContext context: Context): DatabaseRepository = DatabaseRepositoryImpl(context)
    @Provides
    fun provideHttpClient (): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()
    @Provides
    fun provideRetrofit (okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/films/")
            .client(okHttpClient)
            .build()

    @Provides
    fun provideServerAPI(retrofit: Retrofit): ServerAPI = retrofit.create(ServerAPI::class.java)

    @Provides
    fun provideNetworkRepository(serverApi: ServerAPI): NetworkRepository = NetworkRepositoryImpl(serverApi, "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")

    @Provides
    fun provideMainRepository(networkRepository: NetworkRepository, databaseRepository: DatabaseRepository): MainRepository = MainRepositoryImpl(networkRepository, databaseRepository)
}