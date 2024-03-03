package com.lq.kotlintest

class Calculater {


    fun calculater(type : String,num1 : Double , num2 :Double) : Double {
            val  result : Double = when (type) {
                  "+" -> {
                      num1 + num2
                  }

                  "-" -> {
                      num1 - num2;
                  }

                  "/" -> {
                      if (num2 == 0.0) {
                          return 0.0;
                      }
                       num1 / num2;
                  }

                  "*" -> {
                      num1 * num2;
                  }

                  else -> {
                      0.0;
                  }
              }

             return result;
         }
        }


