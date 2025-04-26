import com.bignerdranch.android.movieapp.api.KinopoiskApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.kinopoisk.dev/"
    const val API_KEY = "PA3AHF1-1QK41QM-NDR325B-0TEDP56" // Замените на реальный ключ!

    // Логирование всех сетевых запросов
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Показывает тело запроса/ответа
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-API-KEY", API_KEY)
                .build()
            println(">>> Отправка запроса: ${request.url}")
            chain.proceed(request)
        }
        .build()

    val kinopoiskApi: KinopoiskApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApi::class.java)
    }
}