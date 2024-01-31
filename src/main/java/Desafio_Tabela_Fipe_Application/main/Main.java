package Desafio_Tabela_Fipe_Application.main;

import Desafio_Tabela_Fipe_Application.model.*;
import Desafio_Tabela_Fipe_Application.service.ConsumeApi;
import Desafio_Tabela_Fipe_Application.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    Scanner input  = new Scanner(System.in);
    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/";
    ConsumeApi consume = new ConsumeApi();
    ConvertData converter = new ConvertData();

    public void showMenu() {
        System.out.println("What type of vehicle would you like to search? Cars, Trucks or Bikes");
        String option = input.nextLine();
        String address;
        if (option.toLowerCase().contains("car")) {
            address = BASE_URL + "carros/marcas";
        } else if (option.toLowerCase().contains("bik")) {
            address = BASE_URL + "motos/marcas";
        } else {
            address = BASE_URL + "caminhoes/marcas";
        }
        String json = consume.obtainInfo(address);
        List<Data> data = converter.getList(json, Data.class);
        data.stream()
                        .sorted(Comparator.comparing(Data::code))
                                .forEach(System.out::println);

        System.out.println("\nPlease choose a brand by code.");
        int modelCode = input.nextInt();
        input.nextLine();
        address = address + "/" + modelCode + "/modelos";
        json = consume.obtainInfo(address);
        Models modelsList = converter.getData(json, Models.class);
        System.out.println(modelsList);

        System.out.println("Please enter the name of the model");
        String model = input.nextLine();
        List<Data> filteredModels = modelsList.models().stream()
                .filter(m -> m.name().contains(model))
                        .collect(Collectors.toList());
        System.out.println(filteredModels);
        System.out.println("\nPlease choose the model's code");
        modelCode = input.nextInt();
        input.nextLine();
        address = address + "/" + modelCode + "/anos";
        json = consume.obtainInfo(address);
        List<Data> years = converter.getList(json, Data.class);
        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < years.size(); i++) {
            String addressYear = address + "/" + years.get(i).code();
            json = consume.obtainInfo(addressYear);
            Vehicle vehicle = converter.getData(json, Vehicle.class);
            vehicles.add(vehicle);
        }
        System.out.println("All vehicles filtered");
        vehicles.forEach(System.out::println);



//List<VehicleInfo> vehicleInfo = converter.getList(json, VehicleInfo.class);
//        System.out.println(vehicleInfo);
//        System.out.println("Printing out all the prices.\n");
//        List<Vehicle> vehicles = vehicleName.stream()
//                .map(v -> new Vehicle(v.))
//                .forEach();
//        System.out.println("\nFrom what year you would like to check the prices?");
//        String year = input.nextLine();
//        address = address + "/" + year;
//
//        json = consume.obtainInfo(address);
//        VehicleInfo info = converter.getData(json, VehicleInfo.class);
//        System.out.println("\n" + info);

    }
}