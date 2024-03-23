@file:OptIn(ExperimentalMaterial3Api::class)

package com.lq.cryptoappwithcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lq.cryptoappwithcompose.modal.CryptoModel
import com.lq.cryptoappwithcompose.service.ApiService
import com.lq.cryptoappwithcompose.ui.theme.CryptoAppWithComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                 CryptoAppWithComposeTheme {
                     MainScreen()
                 }
        }
    }
}

@Composable
fun MainScreen(){
    val cryptoItems = remember { mutableStateListOf<List<CryptoModel>>() }
    val retrofit = Retrofit.Builder()
                   .baseUrl("https://raw.githubusercontent.com/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
                   .create(ApiService::class.java);

   val data = retrofit.getAllCrypto();
    data.enqueue(object : Callback<List<CryptoModel>> {
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
             if(response.isSuccessful)
             {

                 val datas = response.body();
                 println("datas.size => "+datas?.size)
                 if(datas != null) {
                     cryptoItems.addAll(listOf(datas))
                 }
             }
        }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            print("err = $t")
        }

    })

    Scaffold(
        modifier = Modifier.background(Color.DarkGray),
        topBar = { AppBar()}) {
              CryptoList(cryptoItems,it.calculateTopPadding())
    }
}

@Composable
fun CryptoList(snapItems : SnapshotStateList<List<CryptoModel>>,topPadding : Dp)
{
     val items =fromSnapToList<CryptoModel>(snapItems);
     LazyColumn(modifier = Modifier.padding(top = topPadding),content = {
          items(items){
              Crypto(it)
          }
     })

}
fun <E>fromSnapToList(snapItem: SnapshotStateList<List<E>>) : List<E>{
       for (list in snapItem)
       {
           return list;
       }
    return  listOf()
}

@Composable
fun Crypto(item : CryptoModel)
{
     Column(modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(5.dp)) {
         Text(text ="Name : ${item.currency}", fontWeight = FontWeight.Bold, fontSize = 30.sp)
         Text(text = "Price : ${item.price}", fontWeight = FontWeight.Bold, fontSize = 25.sp)
     }
}

@Composable
fun AppBar(){
    TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("CRYPTO APP")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
      MainScreen()
}