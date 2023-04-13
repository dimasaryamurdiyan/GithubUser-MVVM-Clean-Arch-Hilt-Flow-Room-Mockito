package com.testcase.githubapp.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.testcase.githubapp.data.local.AppDatabase
import com.testcase.githubapp.data.local.LocalDataSource
import com.testcase.githubapp.data.local.dao.UserDao
import com.testcase.githubapp.data.remote.RemoteDataSource
import com.testcase.githubapp.data.remote.network.ApiService
import com.testcase.githubapp.data.remote.network.RequestInterceptor
import com.testcase.githubapp.domain.repository.IUserRepository
import com.testcase.githubapp.domain.repository.UserRepository
import com.testcase.githubapp.domain.usecase.UserInteractor
import com.testcase.githubapp.domain.usecase.UserUseCase
import com.testcase.githubapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    /* Network Module*/
    @Provides
    @Singleton
    fun providekhttpClient(requestInterceptor: RequestInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(240, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient, gson: Gson): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource = RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideLocalDataSource(userDao: UserDao): LocalDataSource = LocalDataSource(userDao)

    /* Database Module*/
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    /* Domain Module*/
    @Singleton
    @Provides
    fun provideUserRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): IUserRepository = UserRepository(localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase = UserInteractor(userRepository)
}