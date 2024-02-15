package com.lq.informationcities;

public class SelectModel {
     private  CityModel model;
     private SelectModel(){

     }
     private static SelectModel _init = null;
     static SelectModel init()
     {
           if(_init == null)
           {
               _init = new SelectModel();
           }
           return  _init;
     }

    public void setModel(CityModel model) {
        this.model = model;
    }

    public CityModel getModel() {
        return model;
    }
}
