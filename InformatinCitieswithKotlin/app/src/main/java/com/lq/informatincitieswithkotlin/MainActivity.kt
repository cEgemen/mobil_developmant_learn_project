package com.lq.informatincitieswithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lq.informatincitieswithkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding ;
    val cities : ArrayList<CityModel> = ArrayList<CityModel>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

         cities.add(CityModel("Istanbul","İstanbul, yerleşim tarihi son yapılan Yenikapı'daki kazılarla bulunan liman doğrultusunda" +
                " 8500 yıl, kentsel tarihi yaklaşık 3.000, başkentlik tarihi 1600 yıla kadar uzanan Avrupa ile" +
                " Asya kıtalarının kesiştiği noktada bulunan bir dünya kentidir. " +
                "İstanbul Roma, Bizans ve Osmanlı döneminde başkent olarak kullanılmıştır.",R.drawable.istanbul));
        cities.add(CityModel("Roma","Roma, İtalya yarımadasının ortasında yedi tepe üzerinde kurulmuştur." +
                " İtalya'nın başkenti olan Roma tarihsel olarak büyük öneme sahiptir. " +
                "Tiber ve Aniane nehirleri arasında bulunmaktadır ve Lazio bölgesinin başkentidir. " +
                "Bağımsız devlet Vatikan'ın da burada bulunmasından dolayı iki devletin başkenti de denilmektedir.",R.drawable.roma));
        cities.add( CityModel("Berlin","Berlin, Almanya'nın başkenti ve en büyük şehridir, aynı zamanda bir eyalettir." +
                " II. Dünya Savaşı öncesinde 4,3 milyon kişinin yaşadığı şehirde 2021 itibarıyla 3.655 milyon kişi yaşamaktadır." +
                " Berlin, Kuzey Almanya'da, Spree ve Havel nehirlerinin arasındaki kumluk bölgeye kuruludur",R.drawable.berlin));
        cities.add( CityModel("Londra","Londra 2021 yılı itibarıyla İstanbul, Moskova ve Paris'ten sonra " +
                "Avrupa'nın en kalabalık dördüncü şehridir. Londra, 1731'den 1925'e kadar dünyanın en kalabalık şehriydi." +
                " Londra dört Dünya Miras Alanı içerir:" +
                " Londra Kalesi, Kew Bahçeleri, Westminster Sarayı, Westminster Abbey ve Azize Margaret Kilisesi ve Greenwich.",R.drawable.londra));
        cities.add(CityModel("Brüksel","Brüksel veya resmî adıyla Brüksel Başkent Bölgesi, Belçika'nın başkenti ve üç federal bölgesinden biridir. " +
                "Birkaç yüzyıl önce bataklığın kurutulması sonucu ortaya çıkmış bir şehirdir. " +
                "Adı bataklığın içindeki yerleşim yeri anlamına gelir. ",R.drawable.bruksel));
        binding.recyclerView.layoutManager=LinearLayoutManager(this);
        val adapter = RecyclerAdapter(cities);
        binding.recyclerView.adapter = adapter;

    }
}