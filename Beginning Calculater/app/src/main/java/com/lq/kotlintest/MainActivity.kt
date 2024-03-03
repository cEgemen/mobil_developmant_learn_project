package com.lq.kotlintest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lq.kotlintest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var calculate : Calculater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root);
        calculate = Calculater();
        binding.resultTextView.text = "RESULT : 0.0";
        binding.num1EditTextView.setText("0");
        binding.num2EditTextView.setText("0");
    }

    fun add(view : View)
    {
       val res : Boolean =  checkNums();
        if(res)
        {
            val num1 : Double = binding.num1EditTextView.text.toString().toDouble();
            val num2 : Double = binding.num2EditTextView.text.toString().toDouble();
            val result :Double = calculate.calculater("+",num1,num2);
            binding.resultTextView.text = "RESULT : $result"
        }
        else{
            binding.resultTextView.text = "Girdiginiz Deger ya da Degerler Uygun Degildir!!!";

        }

    }

    fun divide(view : View)
    {
        val res : Boolean =  checkNums();
        if(res)
        {
            val num1 : Double = binding.num1EditTextView.text.toString().toDouble();
            val num2 : Double = binding.num2EditTextView.text.toString().toDouble();
            val result : Double =   calculate.calculater("/",num1,num2);
            binding.resultTextView.text = "RESULT : $result"
        }
        else{
            binding.resultTextView.text = "Girdiginiz Deger ya da Degerler Uygun Degildir!!!";

        }

    }

    fun substraction(view : View)
    {
        val res : Boolean =  checkNums();
        if(res)
        {
            val num1 : Double = binding.num1EditTextView.text.toString().toDouble();
            val num2 : Double = binding.num2EditTextView.text.toString().toDouble();
            val result : Double =  calculate.calculater("-",num1,num2);
            binding.resultTextView.text = "RESULT : $result"
        }
        else{
            binding.resultTextView.text = "Girdiginiz Deger ya da Degerler Uygun Degildir!!!";

        }

    }

    fun multiplication(view : View)
    {
        val res : Boolean =  checkNums();
        if(res)
        {
            val num1 : Double = binding.num1EditTextView.text.toString().toDouble();
            val num2 : Double = binding.num2EditTextView.text.toString().toDouble();
            val result : Double =   calculate.calculater("*",num1,num2);
            binding.resultTextView.text = "RESULT : $result"
        }
        else{
            binding.resultTextView.text = "Girdiginiz Deger ya da Degerler Uygun Degildir!!!";

        }

    }

    private fun checkNums( ) : Boolean {
           var result : Boolean = true;
           val converNum1 = binding.num1EditTextView.text.toString().toDoubleOrNull();
           val converNum2 = binding.num2EditTextView.text.toString().toDoubleOrNull();

       if(converNum1 == null || converNum2 == null)
          {
             result = false;
          }
         return result;
    }

}