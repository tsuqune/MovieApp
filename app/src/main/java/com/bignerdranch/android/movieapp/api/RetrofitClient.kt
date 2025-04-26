import com.bignerdranch.android.movieapp.api.KinopoiskApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.kinopoisk.dev/"
    const val API_KEY = "PA3AHF1-1QK41QM-NDR325B-0TEDP56" // Ваш ключ

    // Упрощенный клиент без интерцепторов
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            println(">>> URL запроса: ${request.url}") // Логируем полный URL
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS // Логируем заголовки
        })
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

